package de.douglas.loganalyser.cli;

import java.io.File;

import de.douglas.loganalyser.LogEntry;
import de.douglas.loganalyser.LogProcessor;

/** cli mode */
public class CliLogAnalyser extends LogProcessor {
	private File foundFile;
	private long foundLogs = 0;
	
	public CliLogAnalyser() {
		
	}
	
	@Override
	public void doLogEntry(LogEntry logEntry) {
		if(logEntry.file!=foundFile){
			foundFile = logEntry.file;
			//encounter new file 
			System.out.println(String.format("Found result in file: %s.", foundFile.getAbsolutePath()));
		}
		
		foundLogs++;
		System.out.println(String.format("\tat line: %s", logEntry.line));
	}

	/** output total found results*/
	@Override
	public void doAfterIndexed() {
		System.out.println(String.format("Found total results: %s", foundLogs));
	}
}
