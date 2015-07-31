package util;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.io.File;
import java.io.BufferedOutputStream;
import java.io.BufferedInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;


import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;


public class DocumentUtil {
 
	/**
	 * Search for and replace a single occurrence of a string of text within a
	 * Word document.
	 *
	 * Note that no checks are made on the parameter's values; that is to say
	 * that the file named in the InputFilename parameter will not be checked
	 * to ensure the file exists and neither of the searchTerm nor
	 * replacementTerm parameters will be checked to ensure they are not null.
	 * Also, note that I have never tested passing the same String to the
	 * inputFilename and outputFilename parameters but cannot see why that
	 * should not be possible.
	 *
	 * @param inputFilename An instance of the String class that encapsulates
	 *                      the name of and path to a Word document which is
	 *                      in the binary (OLE2CDF) format. The contents of
this
	 *                      document will be searched for occurrences of the
	 *                      search term.
	 * @param outputFilename An instance of the String class that encapsulates
	 *                       the name of and path to a Word document which is
	 *                       in the binary (OLE2CDF) format. This document will
	 *                       contain the results of the search and replace
	 *                       operation.
	 * @param searchTerm An instance of the String class that encapsulates a
	 *                   series of characters, a word or words. The document
	 *                   will be searched for occurrences of this String.
	 * @param replacementTerm An instance of the String class that contains a
	 *                        series of characters, a word or words. The String
	 *                        encapsulated by the searchTerm parameter will be
	 *                        replaced by the 'contents' of this parameter.
	 *
	 */
	public static void searchAndReplace(String inputFilename,
                                 String outputFilename,
                                 String searchTerm,
                                 String replacementText) {
        
        File inputFile = null;
        File outputFile = null;
        FileInputStream fileIStream = null;
        FileOutputStream fileOStream = null;
        BufferedInputStream bufIStream = null;
        BufferedOutputStream bufOStream = null;
        POIFSFileSystem fileSystem = null;
        HWPFDocument document = null;
        Range docRange = null;
        Paragraph paragraph = null;
        CharacterRun charRun = null;
        int numParagraphs = 0;
        int numCharRuns = 0;
        String text = null;
        
        try {
            // Create an instance of the POIFSFileSystem class and
            // attach it to the Word document using an InputStream.
            inputFile = new File(inputFilename);
            fileIStream = new FileInputStream(inputFile);
            bufIStream = new BufferedInputStream(fileIStream);
            fileSystem = new POIFSFileSystem(bufIStream);
            document = new HWPFDocument(fileSystem);
            
            // Get the overall Range object for the document. Note the
            // use of the getRange() method and not the getOverallRange()
            // method, this is just historic - when the code was originally
            // written, I do not believe the latter method was part of the API.
            docRange = document.getRange();
            
            // Get the number of Paragraph(s) in the overall range and terate
            // through them
            numParagraphs = docRange.numParagraphs();
            for(int i = 0; i < numParagraphs; i++) {
            	
            	// Get a Paragraph and recover the text from it. This step is far from
            	// necessary and I think I only got the text so that I could print
            	// it to screen as a diagnostic check to ensure that the Paragraph
            	// contained the text I was searching for. Experiment with this.
                paragraph = docRange.getParagraph(i);
                text = paragraph.text();
                
                // Get the number of CharacterRuns in the Paragraph
                numCharRuns = paragraph.numCharacterRuns();
                for(int j = 0; j < numCharRuns; j++) {
                	
                	// Get a character run and recover it's text - note that
                	// the same text variable is used as for the Paragraph above.
                	// So, it MUST be safe to remove the text = paragraph.text()
                	// line above.
                    charRun = paragraph.getCharacterRun(j);
                    text = charRun.text();
                    
                    // Check to see if the text of the CharacterRun contains the
                    // search term. If it does, find out where that term starts
                    // and call the replaceText() method passing the index.
                    // Maybe this is the key difference between what we are
                    // doing.
                    if(text.contains(searchTerm)) {
                        int start = text.indexOf(searchTerm);
                        charRun.replaceText(searchTerm, replacementText,
start);
                    }
                }
            }
            
            // Close the InputStream
            bufIStream.close();
            bufIStream = null;
            
            // Open an OutputStream and write the document away.
            outputFile = new File(outputFilename);
            fileOStream = new FileOutputStream(outputFile);
            bufOStream = new BufferedOutputStream(fileOStream);
            
            document.write(bufOStream);
        
        }
        catch(Exception ex) {
            System.out.println("Caught an: " + ex.getClass().getName());
            System.out.println("Message: " + ex.getMessage());
            System.out.println("Stacktrace follows.............");
            ex.printStackTrace(System.out);
        }
        finally {
            if(bufOStream != null) {
                try {
                    //bufOStream.flush();
                    bufOStream.close();
                    bufOStream = null;
                }
                catch(Exception ex) {
                    
                }
            }
            if(bufIStream != null) {
                try {
                    bufIStream.close();
                    bufIStream = null;
                }
                catch(Exception ex) {
                    // I G N O R E //
                }
            }
        }
        
    }
    
    /**
	 * Search for and replace a single occurrence of a string of text within a
	 * Word document.
	 *
	 * Note that no checks are made on the parameter's values; that is to say
	 * that the file named in the InputFilename parameter will not be checked
	 * to ensure the file exists and neither of the searchTerm nor
	 * replacementTerm pare,eters will be checked to ensure they are not null.
	 * Also, note that I have never tested passing the same String to the
	 * inputFilename and outputFilename parameters but cannot see why that
	 * should not be possible.
	 *
	 * @param inputFilename An instance of the String class that encapsulates
	 *                      the name of and path to a Word document which is
	 *                      in the binary (OLE2CDF) format. The contents of
this
	 *                      document will be searched for occurrences of the
	 *                      search term.
	 * @param outputFilename An instance of the String class that encapsulates
	 *                       the name of and path to a Word document which is
	 *                       in the binary (OLE2CDF) format. This document will
	 *                       contain the results of the search and replace
	 *                       operation.
	 * @param replacements An instance of the java.util.HashMap class that
	 *                     contains a series of key, value pairs. Each key
	 *                     is an instance of the String class that encapsulates
	 *                     a series of characters, a word or words that the
	 *                     code will search for and the accompanying value is
	 *                     also an instance of the String class that likewise
	 *                     encapsulates a series of characters, a word or
words.
	 *                     The 'contents' of the value's String will be used to
	 *                     replace the contents of the key's String if an
	 *                     occurrence of the latter is found.
	 */
    public static void searchAndReplace(String inputFilename,
                                 String outputFilename,
                                 HashMap<String, String> replacements) {
        
        File inputFile = null;
        File outputFile = null;
        FileInputStream fileIStream = null;
        FileOutputStream fileOStream = null;
        BufferedInputStream bufIStream = null;
        BufferedOutputStream bufOStream = null;
        POIFSFileSystem fileSystem = null;
        HWPFDocument document = null;
        Range docRange = null;
        Paragraph paragraph = null;
        CharacterRun charRun = null;
        Set<String> keySet = null;
        Iterator<String> keySetIterator = null;
        int numParagraphs = 0;
        int numCharRuns = 0;
        String text = null;
        String key = null;
        String value = null;
        
        try {
            // Create an instance of the POIFSFileSystem class and
            // attach it to the Word document using an InputStream.
            inputFile = new File(inputFilename);
            fileIStream = new FileInputStream(inputFile);
            bufIStream = new BufferedInputStream(fileIStream);
            fileSystem = new POIFSFileSystem(bufIStream);
            document = new HWPFDocument(fileSystem);
			
			// Get a reference to the overall Range for the document
			// and discover how many Paragraphs objects there are
			// in the document.
            docRange = document.getRange();
            numParagraphs = docRange.numParagraphs();
            
            // Recover a Set of the keys in the HashMap
            keySet = replacements.keySet();
            
            // Step through each Paragraph
            for(int i = 0; i < numParagraphs; i++) {
                paragraph = docRange.getParagraph(i);
                // This line can almost certainly be removed - see
                // the comments in the method above.
                text = paragraph.text();
                
                // Get the number of CharacterRuns in the Paragraph
                // and step through each one.
                numCharRuns = paragraph.numCharacterRuns();
                for(int j = 0; j < numCharRuns; j++) {
                    charRun = paragraph.getCharacterRun(j);
                    
                    // Get the text from the CharacterRun and recover an
                    // Iterator to step through the Set of keys.
                    text = charRun.text();
                    keySetIterator = keySet.iterator();
                    while(keySetIterator.hasNext()) {
                    	
                    	// Get the key - which is also the search term - and
                    	// check to see if it can be found within the 
                    	// CharacterRuns text.
                        key = keySetIterator.next();
                        if(text.contains(key)) {
                        	
                        	// If the search term was found in the text, get the
                        	// matching value from the HashMap, find out whereabouts
                        	// in the CharacterRuns text the search term is
                        	// and call the replaceText() method to substitute
                        	// the replacement term for the search term.
                            value = replacements.get(key);
                            int start = text.indexOf(key);
                            charRun.replaceText(key, value, start);
                            
                            // Note that this code was added to test whether
                            // it was possible to replace multiple occurrences
                            // of the search term. I cannot remember if I tested
                            // it but believe that it did work; either way,
                            // it could be tested now and if succeeds, then the
                            // searchAndReplace() method above could be modified
                            // to include this.
                            docRange = document.getRange();
                            paragraph = docRange.getParagraph(i);
                            charRun = paragraph.getCharacterRun(j);
                            text = charRun.text();
                        }
                    }
                }
            }
            
            // Close the InputStream
            bufIStream.close();
            bufIStream = null;
            
            // Open an OutputStream and save the modified document away.
            outputFile = new File(outputFilename);
            fileOStream = new FileOutputStream(outputFile);
            bufOStream = new BufferedOutputStream(fileOStream);
            document.write(bufOStream);
            //fileOStream.flush();
            //fileOStream.close();
        }
        catch(Exception ex) {
            System.out.println("Caught an: " + ex.getClass().getName());
            System.out.println("Message: " + ex.getMessage());
            System.out.println("Stacktrace follows.............");
            ex.printStackTrace(System.out);
        }
        finally {
        	
            if(bufIStream != null) {
                try {
                    bufIStream.close();
                    bufIStream = null;
                }
                catch(Exception ex) {
                    // I G N O R E //
                }
            }
            if(bufOStream != null) {
                try {
                    bufOStream.flush();
                    bufOStream.close();
                    bufOStream = null;
                }
                catch(Exception ex) {
                    
                }
            }
        }
        
    }
}
	/**
	 * The main entry point to the program demonstrating how the code may
	 * be utilised.
	 *
	 * @param args An array of type String containing argumnets passed to the
	 *             program on execution.
	 */
	/*public static void main(String[] args) {
		DocumentUtil replacer = new DocumentUtil();
		
		// To serach for and replace single items. Note, the code has not, at
		// least as far as I can remember, been tested by passing the same
		// file to both the searchTerm and replacementTerm parameters. It ought
		// to work but has NOT been tested I believe.
		replacer.searchAndReplace("C:\\vod_karta.dot",            // Source Document
			"C:\\2.rtf",                         // Result Document
			"<NAME>",                                   // Search term
			"fuck");                             // Replacement term
			
		// To search for and replace a series of items
		/*HashMap<String, String> searchTerms = new HashMap<String, String>();
		searchTerms.put("search term 1", "replacement term 1");
		searchTerms.put("search term 2", "replacement term 2");
		searchTerms.put("search term 3", "replacement term 3");
		searchTerms.put("search term 4", "replacement term 4");
		
		replacer.searchAndReplace("Document.doc",    // Source Document
			"Replaced Document.doc",                 // Result Document
			searchTerms)                             // Search/replacement items
	}

 
}*/