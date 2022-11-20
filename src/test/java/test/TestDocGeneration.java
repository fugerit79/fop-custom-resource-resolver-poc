package test;

import org.fugerit.java.poc.fop.custom.resource.resolver.SampleDocumentGenerator;
import org.junit.Assert;
import org.junit.Test;

public class TestDocGeneration {

	@Test
	public void generate() {
		SampleDocumentGenerator sdg = new SampleDocumentGenerator();
		int result = sdg.generateDoc();
		Assert.assertEquals( result , SampleDocumentGenerator.OK );
	}
	
}
