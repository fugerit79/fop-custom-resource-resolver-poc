package org.fugerit.java.poc.fop.custom.resource.resolver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URI;

import org.apache.fop.apps.io.ResourceResolverFactory;
import org.apache.xmlgraphics.io.Resource;
import org.apache.xmlgraphics.io.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * This simple ResourceResolver loads from the classpath any url starting with : classpath://
 * All other url are handled by a standard ResourceResolver (ResourceResolverFactory.createDefaultResourceResolver())
 * 
 */
public class ClassLoaderResourceResolver implements ResourceResolver, Serializable {

	private static final Logger logger = LoggerFactory.getLogger( ClassLoaderResourceResolver.class );
	
	public static final String CLASSPATH_SCHEMA = "classpath://";
	
	private ResourceResolver delegate;
	
	public ClassLoaderResourceResolver() {
		super();
		this.delegate = ResourceResolverFactory.createDefaultResourceResolver();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 4159394346585482675L;

	@Override
	public OutputStream getOutputStream(URI uri) throws IOException {
		logger.info( "TestClassLoaderResourceResolver.getOutputStream() -> {}", uri );
		return Thread.currentThread().getContextClassLoader().getResource(uri.toString()).openConnection().getOutputStream();
	}

	@Override
	public Resource getResource(URI uri) throws IOException {
		try {
			logger.info( "TestClassLoaderResourceResolver.getResource() -> {}", uri );
			String path = uri.toString();
			if ( path.startsWith( CLASSPATH_SCHEMA ) ) {
				path = path.substring( CLASSPATH_SCHEMA.length() );
				ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
				if ( classLoader == null ) {
					classLoader = ClassLoaderResourceResolver.class.getClassLoader();
				}
				InputStream inputStream = classLoader.getResourceAsStream( path );
				return new Resource(inputStream);
			} else {
				return this.delegate.getResource(uri);
			}
		} catch (Exception e) {
			throw new IOException( e );
		}
		
	}
	
}
