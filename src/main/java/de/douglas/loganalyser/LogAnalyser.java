/** Douglas Test */
package de.douglas.loganalyser;

import java.io.IOError;
import java.nio.file.InvalidPathException;
import java.util.regex.PatternSyntaxException;

import de.douglas.loganalyser.cli.CliLogAnalyser;
import de.douglas.loganalyser.cmd.CmdLogAnalyser;
import de.douglas.loganalyser.cui.CuiLogAnalyser;

/** main class for this simple program */
public class LogAnalyser {

	/** command line entry */
	public static void main(String[] args) {
		//check arguments
		if(args.length==0){
			usage();
			System.exit(0);
		}
		
		//get configuration
		Config config = new Config().readFromResource().mergeFromEnvironment();
		//check keywords
		try{
			config.getKeywordsAsPattern();
		}catch(PatternSyntaxException ex){
			ex.printStackTrace();
			usage();
			//exit with error
			System.exit(1);
		}
		
		//read files
		try{
			  config = config.prepareFiles(args);
		}catch(InvalidPathException|IOError ex){
			ex.printStackTrace();
			usage();
			//exit with error
			System.exit(1);
		}
		
		//create class instance
		LogProcessor processor = null;
		switch (config.getMode()) {		
		case cmd:
			processor = new CmdLogAnalyser();
			break;
		case cui:
			processor = new CuiLogAnalyser();
			break;
		case cli:			
		default:
			processor = new CliLogAnalyser();
			break;
		}
		
		//go
		processor.doFiles(config.getFiles(), config.getKeywordsAsPattern());
		//has own logic?
		processor.doAfterIndexed();
	}

	
	/** example for command line invocation */
	public static void usage(){
		System.out.println("Usage: ");
//		System.out.println("java [-Dconfig=config.properties] [-Dmode=cmd|cli|gui] [-Dkeywords=Exception,] -jar loganalyser.jar log.1 log.2 log_folder.1 log folder.2");
		System.out.println("java [-Dmode=cli|cmd|cui] [-Dkeywords=IOException,NullPointerException] -jar loganalyser.jar log.1 log.2 log_folder.1 log log_folder.2");
	}
}
