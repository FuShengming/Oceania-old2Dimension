package edu.ncsu.csc.itrust.dao.personnel;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class PersonnelExistsTest extends TestCase {
	PersonnelDAO personnelDAO = TestDAOFactory.getTestInstance().getPersonnelDAO();
	TestDataGenerator gen;

	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
	}

	public void testGetPersonnel2() throws Exception {
		gen.uap1();
		assertTrue(personnelDAO.checkPersonnelExists(8000000009l));
		assertFalse(personnelDAO.checkPersonnelExists(8999999999l));
	}
}
