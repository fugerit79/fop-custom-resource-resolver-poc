package org.fugerit.java.poc.fop.custom.resource.resolver;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.EnvironmentalProfileFactory;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopConfParser;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.FopFactoryBuilder;
import org.apache.fop.apps.MimeConstants;
import org.apache.xmlgraphics.io.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * This is a sample class for generating a pdf document with fop providing a custom resource resolver.
 * The code is take from : https://xmlgraphics.apache.org/fop/2.8/embedding.html
 * 
 * The purpose of this code is only to show the different behaviour in veesion 2.6, 2.7 and 2.8 of Apache FOP.
 * 
 */
public class SampleDocumentGenerator {

	private static final Logger logger = LoggerFactory.getLogger( SampleDocumentGenerator.class );
	
	public static final int OK = 0;		// generation OK
	
	public static final int KO = 1;		// generation failed
	
	public int generateDoc() {
		int result = OK;
		try ( InputStream fopConfigStream = new FileInputStream( "src/fop/fop-sample-config.xml") ) {
						
			// Step 1: Construct a FopFactory by specifying a reference to the configuration file
			// (reuse if you plan to render multiple documents!)
			// NOTE: this is the only step that has been changed from the sample :
			//		https://xmlgraphics.apache.org/fop/2.8/embedding.html
			//		to include a custom ResourceResolver
			ResourceResolver resolver = new ClassLoaderResourceResolver();		    
		    FopConfParser confParser =  new FopConfParser( fopConfigStream, EnvironmentalProfileFactory.createRestrictedIO(new URI("."), resolver) );
		    FopFactoryBuilder confBuilder = confParser.getFopFactoryBuilder();
		    FopFactory fopFactory = confBuilder.build();
		    
			// Step 2: Set up output stream.
			// Note: Using BufferedOutputStream for performance reasons (helpful with FileOutputStreams).
			OutputStream out = new BufferedOutputStream(new FileOutputStream(new File("target/myfile.pdf")));

			try {
			    // Step 3: Construct fop with desired output format
			    Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, out);

			    // Step 4: Setup JAXP using identity transformer
			    TransformerFactory factory = TransformerFactory.newInstance();
			    Transformer transformer = factory.newTransformer(); // identity transformer

			    // Step 5: Setup input and output for XSLT transformation
			    // Setup input stream
			    Source src = new StreamSource(new File("src/fop/fop-sample-doc.fo"));

			    // Resulting SAX events (the generated FO) must be piped through to FOP
			    Result res = new SAXResult(fop.getDefaultHandler());

			    // Step 6: Start XSLT transformation and FOP processing
			    transformer.transform(src, res);

			} finally {
			    //Clean-up
			    out.close();
			}
		} catch (Exception e) {
			result = KO;
			logger.error( "Error genrating document : "+e, e );
		}
		return result;
	}
	
}
