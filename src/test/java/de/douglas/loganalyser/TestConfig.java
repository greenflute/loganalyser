package de.douglas.loganalyser;

import static org.junit.Assert.assertEquals;

import java.util.regex.Pattern;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;



public class TestConfig {

	private static Config config;
	
	@BeforeClass
	public static void setUp(){
		config = new Config();
	}
	
	@Test
	public void testMode(){
		System.setProperty("mode", "cui");
		config.mergeFromEnvironment();
		assertEquals("Mode should be cui now.", Mode.cui, config.getMode());
	}
	
	@Test
	public void testKeywordsPattern(){
		System.setProperty("keywords", "IOException");
		config.mergeFromEnvironment();
		Pattern pattern = config.getKeywordsAsPattern();
		assertEquals("Keywords Pattern should be IOException now.", "IOException", pattern.pattern());
	}
	
	@AfterClass
	public static void tearDown(){
		config = null;
	}
	
}
