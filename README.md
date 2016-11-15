Douglas Test: LogAnalyser
=========================

#	Considerations
1.	Actually this is just a toy program, as for searching through logs.
2.	We have better tools like grep, especially with the "-A" "-B" "-E" parameter,
	and with cut/awk/sed command we can precisely define the search result. 
3.	And for multiple live log files, we have tmux, tail with "-f".
	For massive DevOps we do need then log aggregators, or something like
	centralized syslog or log database.		 

#	Requirement
1.	Large/huge file -> java 8 stream
2.	Given bunch of files and folders ?
	-> Later as TODO for parallel processing.
3.	Recursive folder with logs ?
	-> Later as TODO, for now just one level, since not much log file layouts have this.
4.  Multiple log files for same day/instance due to debug/kill/restart ?
	-> time unit may span across multiple files
5.	For 1-4, we may use different modes:
	-Dmode=cli (just output and quit), 
	-Dmode=cmd (wait for search), 
	-Dmode=cui (ncurses alike console interface, so not gui).
	And it doesn't make sense for a gui.
	-> Later as TODO
6.	For cui mode, maybe behave like watch dog using java WatchService ?
	-> Later as TODO
7.	Check file type: text or binary ?
	-> Later as TODO

#	Search possibility
1.	Lucene or simple text match ?
3.	If using Lucene, RAMDirectory or FSDirectory (large number of files) ?
3.	Pre-configured exceptions or keywords ?
	-> Using properties, and dynamically configurable with -D environment options
	(-Dmode=cmd -Dkeywords=NullPointerException,RuntimeException etc...)

#	Visual presentation as TODO
1.	D3.js
2.	CasperJS for headless image capture
	-> capture.js came from previous self-made utility, just needs minor modification.
	-> vibration* came from internet and modified for previous mentioned utility, needs modification too.
	
#	TODO
1.	log4j instead of console output ?