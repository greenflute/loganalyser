package de.douglas.loganalyser.cmd;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import de.douglas.loganalyser.LogEntry;
import de.douglas.loganalyser.LogProcessor;
import de.douglas.loganalyser.lucene.LogEntryRepo;

/** cmd mode */
public class CmdLogAnalyser extends LogProcessor {
	/** lucene repo */
	private LogEntryRepo repo = LogEntryRepo.getInstance();
	
	public CmdLogAnalyser() {
	}
	
	@Override
	public void doLogEntry(LogEntry logEntry) {
		repo.writeIndex(logEntry);
	}

	/** output total found results*/
	@Override
	public void doAfterIndexed() {
		repo.closeIndex();
		
		try(Scanner scanner = new Scanner(System.in)){
			while(true){
				System.out.println("Enter word to search in logs, just Enter to quit:\n");
				
				String query = scanner.nextLine();
				if(query.isEmpty()){
					break;
					
				}else{
					
					//wildcard query
					if(!query.startsWith("*")) query="*"+query;
					if(!query.endsWith("*")) query=query+"*";
					
					//result
					List<LogEntry> entries = repo.searchIndex(query);
					
					//output
					File foundFile = null;
					for(final LogEntry logEntry : entries){
						if(foundFile==null || !logEntry.file.getAbsolutePath().equals(foundFile.getAbsolutePath())){
							foundFile = logEntry.file;
							//encounter new file 
							System.out.println(String.format("Found result in file: %s.", foundFile.getAbsolutePath()));
						}
						System.out.println(String.format("\tat line: %s", logEntry.line));
					}
					System.out.println(String.format("Found in %s log entries: ", entries.size()));
				}
			}
		}
	}
}
