package uk.co.joshjordan.tika;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.xml.XMLParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "XML EXTRACT" );
        XMLExtract();
        

        //System.out.println( "PDF EXTRACT" );
        //PDFExtract();
    }
    
    private static void XMLExtract(){

        BodyContentHandler contentHandler = new BodyContentHandler();
        Metadata tikaMetaData = new Metadata();
        
        FileInputStream inputStream = null;
        try{
        	inputStream = new FileInputStream(new File("src/main/resources/Books.xml"));
        }catch(Exception e){
    		System.out.println("Issue Loading XML File: " + e.getMessage());
    		System.exit(1);
        }
                

        XMLParser xmlParser = new XMLParser(); 
        try {
        	xmlParser.parse(inputStream, contentHandler, tikaMetaData, new ParseContext());
		} catch (Exception e) {
			// TODO Auto-generated catch block
    		System.out.println("Issue Parsing XML: " + e.getMessage());
    		System.exit(1);
		}
       
        
        for(String name :  tikaMetaData.names()) {
           System.out.println(name + " : " + tikaMetaData.get(name));
        }

        
        //Print contents of file
        //System.out.println(contentHandler.toString());
    }
    
    private static void PDFExtract(){
    	File file = new File("src/main/resources/Raven.pdf");
    	
    	InputStream inputStream = null;
    	try{
    		inputStream = new FileInputStream(file);
    	}catch(Exception e){
    		System.out.println("Issue Loading PDF: " + e.getMessage());
    		System.exit(1);
    	}
    	
    	Metadata tikaMetaData = new Metadata();
    	BodyContentHandler contentHandler = new BodyContentHandler();
    	AutoDetectParser tikaParser = new AutoDetectParser();
    	
    	String mimeType = null;
    	
    	try{
    		mimeType = new Tika().detect(file);
    	}catch(Exception e){
    		System.out.println("Issue Obtaining Mime Type: " + e.getMessage());
    		System.exit(1);
    	}
    	
    	tikaMetaData.set(Metadata.CONTENT_TYPE, mimeType);
    	
    	try{
    		tikaParser.parse(inputStream, contentHandler, tikaMetaData, new ParseContext());
    	}catch(Exception e){
    		System.out.println("Issue Parsing File: " + e.getMessage());
    		System.exit(1);
    	}
    	
    	try{
    		inputStream.close();
    	}catch(Exception e){
    		System.out.println("Issue Closing Reader: " + e.getMessage());
    		System.exit(1);
    	}
    	
    	for(String name : tikaMetaData.names()){
    		System.out.println(name + " : " + tikaMetaData.get(name));
    	}
    	
    	//Print the file contents
    	 //System.out.println(contentHandler.toString());
    }
}
