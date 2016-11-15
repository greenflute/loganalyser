package de.douglas.loganalyser;

/**
 * three modes of this program:
 * <pre>  
 * cmd: hit and run, read file, output stats then quit. 
 * cli: read all files and index them, then wait for search query. 
 * gui: console gui version of cli.
 * </pre>
 */
public enum Mode {
	cmd, cli, cui
}
