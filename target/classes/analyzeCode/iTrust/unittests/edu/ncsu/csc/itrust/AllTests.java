package edu.ncsu.csc.itrust;

import java.lang.reflect.Modifier;
import junit.framework.Test;
import junitx.util.DirectorySuiteBuilder;
import junitx.util.SimpleTestFilter;

public class AllTests {
	@SuppressWarnings("rawtypes")
	public static Test suite() throws Exception {
		DBBuilder.rebuildAll();
		DirectorySuiteBuilder builder = new DirectorySuiteBuilder();
		builder.setFilter(new SimpleTestFilter() {
			@Override
			public boolean include(Class clazz) {
				// Ignore the HTTP tests in this suite
				return !clazz.getPackage().getName().contains("http")
						&& !Modifier.isAbstract(clazz.getModifiers());
			}
		});
		return builder.suite("build/classes");
	}

}
