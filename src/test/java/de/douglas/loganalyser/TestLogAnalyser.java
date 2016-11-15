package de.douglas.loganalyser;

public class TestLogAnalyser {
	public static void main(String[] args) {
		LogAnalyser.main(new String[] {
//				"application_log_data.log",
//				"application_log_data48.log",	//250MB, 5seconds
				"application_log_data192.log"	//1GB, 12seconds
				});
	}
}
