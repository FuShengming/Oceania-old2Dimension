package edu.ncsu.csc.itrust.http;

import junit.framework.Test;
import junitx.util.DirectorySuiteBuilder;
import junitx.util.SimpleTestFilter;
import edu.ncsu.csc.itrust.DBBuilder;

public class AllHTTPTests {

	@SuppressWarnings("rawtypes")
	public static Test suite() throws Exception {
		DBBuilder.rebuildAll();
		DirectorySuiteBuilder builder = new DirectorySuiteBuilder();
		builder.setFilter(new SimpleTestFilter() {
			@Override
			public boolean include(Class clazz) {
				// Only include the HTTP tests in this suite
				return clazz.getPackage().getName().contains("http")
						&& !clazz.getSimpleName().equals("iTrustHTTPTest");
			}
		});
		return builder.suite("build/classes");
	}
}
