package de.douglas.loganalyser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Pattern;

/** abstract class to process log files, leave action to *LogAnalyser */
public abstract class LogProcessor {

	/**TODO parallel processing, 
	 * TODO Recursive using Files.walkFileTree()
	 */
	public void doFiles(final Set<File> files, final Pattern pattern){
		for(final File file : files){
			
			long startTime = System.currentTimeMillis();
			System.out.print(String.format("Procssing file: %s", file.getAbsolutePath()));
			try {
				final BufferedReader reader = new BufferedReader(new FileReader(file));
				
				//loop variables
				long linenumber = 0;
				StringBuilder logline = new StringBuilder();
				
				for (Iterator<String> it = reader.lines().iterator(); it.hasNext();) {
					linenumber ++;
					
					String line = it.next();
					if(line.isEmpty() && logline.length() > 0){
						//we have a record
						line = logline.toString(); 
						
						//do something if matches						
						if(pattern.matcher(line).find()){
							doLogEntry(new LogEntry(file, linenumber, line));
						}
						
						//clearup
						logline = new StringBuilder();
					}else{
						//record continues
						logline.append("\n").append(line);
					}
				}
				
				//close this file
				reader.close();
				long endTime = System.currentTimeMillis();
				System.out.println(String.format(" , duration: %s secondes.", (endTime-startTime)/1000));
				
			}catch(IOException ex){
				ex.printStackTrace();
				System.out.println(String.format("Procssing file: %s failed.", file.getAbsolutePath()));
			}
		}	
	}
	
	/** Subclass may just match, or add them to Lucene Index.*/
	public abstract void doLogEntry(final LogEntry logEntry);

	/** what to do after initial search? this will give a chance to run custom code for *LogAnalyser.*/
	public abstract void doAfterIndexed();

}
