package de.douglas.loganalyser;

import java.io.File;

/**
 * simple structure for log entries. When using with Lucene, only the content
 * field will be indexed and searched.
 */
public class LogEntry {
	public File file;
	public long line;
	public String logline;
	
	public LogEntry(final File file, final long line, final String logline) {
		this.file = file;
		this.line = line;
		this.logline = logline;
	}
}
