package de.douglas.loganalyser.lucene;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.douglas.loganalyser.LogEntry;

public class TestLogEntryRepo {

	private LogEntryRepo repo;
	
	@Before
	public void setUp(){
		repo = LogEntryRepo.getInstance();
	}
	
	@Test
	public void testSearch(){
		repo.writeIndex(new LogEntry(new File("/temp/a.log"), 1, "abc.NullPointerException def"));
		repo.closeIndex();
		List<LogEntry> result = repo.searchIndex("*NullPointerException*");
		assertEquals("We should have one result for NullPointerException.", 1, result.size());
	}
	
	
	@After
	public void tearDown(){
		repo.closeIndex();
		repo=null;
	}
	
}
