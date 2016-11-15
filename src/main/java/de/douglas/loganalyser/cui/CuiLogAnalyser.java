package de.douglas.loganalyser.cui;

import de.douglas.loganalyser.LogEntry;
import de.douglas.loganalyser.LogProcessor;
import de.douglas.loganalyser.lucene.LogEntryRepo;

/** cui mode */
public class CuiLogAnalyser extends LogProcessor {
	
	/** lucene repo */
	private LogEntryRepo repo = LogEntryRepo.getInstance();
	
	public CuiLogAnalyser() {
	}
	
	@Override
	public void doLogEntry(LogEntry logEntry) {
		repo.writeIndex(logEntry);
	}

	/** output total found results*/
	@Override
	public void doAfterIndexed() {
		repo.closeIndex();
		
		//TODO init lanterna console cui, accept query, output result.
	}
}
