package edu.ncsu.csc.itrust.dao.auth;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.enums.Role;
import edu.ncsu.csc.itrust.exception.iTrustException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class AddUserTest extends TestCase {
	AuthDAO authDAO = TestDAOFactory.getTestInstance().getAuthDAO();
	private TestDataGenerator gen = new TestDataGenerator();

	@Override
	protected void setUp() throws Exception {
		gen.clearAllTables();
	}

	public void test500Gone() throws Exception {
		try {
			authDAO.getUserRole(500);
			fail("exception should have been thrown");
		} catch (iTrustException e) {
			assertEquals("User does not exist", e.getMessage());
		}
	}

	public void testAdd500WithHCP() throws Exception {
		authDAO.addUser(500L, Role.HCP, "password");
		assertEquals(Role.HCP, authDAO.getUserRole(500L));
	}

	public void testAddWithPatient() throws Exception {
		authDAO.addUser(500L, Role.PATIENT, "password");
		assertEquals(Role.PATIENT, authDAO.getUserRole(500L));
	}
}
