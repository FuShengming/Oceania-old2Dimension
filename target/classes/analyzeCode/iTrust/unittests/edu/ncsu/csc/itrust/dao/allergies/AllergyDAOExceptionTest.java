package edu.ncsu.csc.itrust.dao.allergies;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.dao.mysql.AllergyDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.testutils.EvilDAOFactory;

public class AllergyDAOExceptionTest extends TestCase {
	private AllergyDAO evilDAO = EvilDAOFactory.getEvilInstance().getAllergyDAO();

	@Override
	protected void setUp() throws Exception {
	}

	public void testAddAllergyException() throws Exception {
		try {
			evilDAO.addAllergy(0, "");
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testGetAllergyException() throws Exception {
		try {
			evilDAO.getAllergies(0);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}
}
