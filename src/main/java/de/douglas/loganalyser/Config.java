package de.douglas.loganalyser;

import java.io.File;
import java.io.IOError;
import java.io.StringReader;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/** holder for mode and keywords */
public class Config {

	private Mode mode = Mode.cli;
	private String keywords = "Exception";
	private Set<File> files = new HashSet<>();

	/** read default configuration entries from config.properties */
	public Config readFromResource() {
		Scanner scanner = null;
		try {
			scanner = new Scanner(Config.class.getResourceAsStream("/config.properties"), "UTF-8");

			final Properties configs = new Properties();
			configs.load(new StringReader(scanner.useDelimiter("\\Z").next()));

			final String mode_ = configs.getProperty("mode", "cmd");
			try {
				mode = Mode.valueOf(mode_);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			keywords = configs.getProperty("keywords", "Exception");

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}
		return this;
	}

	/** merge from entries given through command line */
	public Config mergeFromEnvironment() {
		// final String configProp = System.getenv("config");
		final String modeProp = System.getProperty("mode");
		final String keywordsProp = System.getProperty("keywords");

		// if(configProp!=null && !configProp.isEmpty()){
		//
		// }

		if (modeProp != null && !modeProp.isEmpty()) {
			try {
				mode = Mode.valueOf(modeProp);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		if (keywordsProp != null && !keywordsProp.isEmpty()) {
			keywords = keywordsProp;
		}

		return this;
	}

	/** retrieve file and files in folders, maybe with different handling when we use WatcherService  
	 * TODO: how to check if the given file is text or binary? 
	 * TODO: only one level directory for now.
	 */
	public Config prepareFiles(final String[] args) throws InvalidPathException, IOError{
		final String basePath = Paths.get("").toAbsolutePath().toString();
				
		for(final String arg : args){
			final File file = arg.startsWith(File.separator) ? new File(arg) : new File(basePath + File.separator + arg);
			if(file.exists()){
				if(file.isDirectory()){
					for(File subFile : file.listFiles()){
						if(subFile.isFile()){
							this.files.add(subFile);
						}
					}
				}else if(file.isFile()){
					this.files.add(file);
				}
			}
		}
		
		return this;
	}

	/** each time return a new Pattern object. */
	public Pattern getKeywordsAsPattern() throws PatternSyntaxException{
		return Pattern.compile(this.keywords.replaceAll("\\.", "\\.").replaceAll(",", "|"));
	}
	
	public Mode getMode() {
		return mode;
	}

	public String getKeywords() {
		return keywords;
	}

	public Set<File> getFiles() {
		return files;
	}
}
