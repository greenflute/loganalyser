package de.douglas.loganalyser.lucene;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import de.douglas.loganalyser.LogEntry;

/** wrapper for Lucene RAM/FSDirectory */
public class LogEntryRepo {
	/** singleton */
	private static final LogEntryRepo instance = new LogEntryRepo();

	public static LogEntryRepo getInstance() {
		return instance;
	}

	/** lucene related */
	Directory directory;
	Analyzer analyzer;
	IndexWriter writer;
	IndexReader reader;
	IndexSearcher searcher;
	
	/** init Lucene */
	public LogEntryRepo() {
		try {
			directory = new RAMDirectory();
			analyzer = new StandardAnalyzer();
			IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
			iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
			writer = new IndexWriter(directory, iwc);
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/** add document to index*/
	public void writeIndex(final LogEntry logEntry){
		try {
			Document doc = new Document();			
			doc.add(new StringField("file", logEntry.file.getAbsolutePath(), Field.Store.YES));
			doc.add(new StringField("logline", logEntry.logline, Field.Store.YES));
			doc.add(new StringField("line", String.valueOf(logEntry.line), Field.Store.YES));
			
			writer.addDocument(doc);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void closeIndex(){
		try {
			writer.commit();
//			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** search */
	public List<LogEntry> searchIndex(final String query){
		List<LogEntry> entries = new ArrayList<>();
		try {
			reader = DirectoryReader.open(directory);
			searcher = new IndexSearcher(reader);
			
			// long startTime = System.currentTimeMillis();
			Query pquery = new WildcardQuery(new Term("logline", query));
			TopDocs hits = searcher.search(pquery, Integer.MAX_VALUE);
//			TopDocs hits = searcher.search(pquery, Integer.MAX_VALUE, new Sort(new SortField("file", Type.STRING)));
			// long endTime = System.currentTimeMillis();
			// System.out.println(String.format("Found in %s log entries: ", hits.totalHits));
			
			for (ScoreDoc scoreDoc : hits.scoreDocs) {
				Document doc = searcher.doc(scoreDoc.doc);
				entries.add(new LogEntry(new File(doc.get("file")), Long.valueOf(doc.get("line")), doc.get("logline")));
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return entries;
	}
}
