package edu.ncsu.csc.itrust.action;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.iTrustException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class EditPersonnelActionTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private EditPersonnelAction personnelEditor;

	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
	}

	public void testNonExistent() throws Exception {
		try {
			personnelEditor = new EditPersonnelAction(factory, 0L, "8999999999");
			fail("exception should have been thrown");
		} catch (iTrustException e) {
			assertEquals("Personnel does not exist", e.getMessage());
		}
	}

	public void testWrongFormat() throws Exception {
		try {
			gen.hcp0();
			personnelEditor = new EditPersonnelAction(factory, 0L, "hello!");
			fail("exception should have been thrown");
		} catch (iTrustException e) {
			assertEquals("Personnel ID is not a number: For input string: \"hello!\"", e.getMessage());
		}
	}

	public void testNull() throws Exception {
		try {
			gen.hcp0();
			personnelEditor = new EditPersonnelAction(factory, 0L, null);
			fail("exception should have been thrown");
		} catch (iTrustException e) {
			assertEquals("Personnel ID is not a number: null", e.getMessage());
		}
	}
	
	public void testUpdateInformation() throws Exception {
		gen.uap1();
		personnelEditor = new EditPersonnelAction(factory, 8000000009L, "8000000009");
		PersonnelBean j = factory.getPersonnelDAO().getPersonnel(8000000009l);
//		j.setPassword("isntRetrieved");
//		j.setConfirmPassword("isntRetrieved");
		j.setStreetAddress2("second line");
		personnelEditor.updateInformation(j);
		j = factory.getPersonnelDAO().getPersonnel(8000000009l);
		assertEquals("second line", j.getStreetAddress2());
	}
	
	public void testEditMessageFilter() throws Exception {
		gen.uap1();
		personnelEditor = new EditPersonnelAction(factory, 8000000009L, "8000000009");
		PersonnelBean j = factory.getPersonnelDAO().getPersonnel(8000000009L);
		String testFilter = "Andy Programmer,,,,,";
		
		personnelEditor.editMessageFilter(testFilter);
		
		j = factory.getPersonnelDAO().getPersonnel(8000000009L);
		assertEquals(testFilter, j.getMessageFilter());
		
	}
	
}
