package edu.ncsu.csc.itrust.dao.photo;

import java.awt.image.BufferedImage;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.testutils.EvilDAOFactory;

public class ProfilePhotoDAOExceptionTest extends TestCase {

	public void testEvilConnectionGet() throws Exception {
		try {
			EvilDAOFactory.getEvilInstance().getProfilePhotoDAO().get(1l);
			fail("Exception should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testEvilConnectionStore() throws Exception {
		try {
			BufferedImage bi = new BufferedImage(900, 500, BufferedImage.TYPE_3BYTE_BGR);
			EvilDAOFactory.getEvilInstance().getProfilePhotoDAO().store(1l, bi);
			fail("Exception should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}
	
	public void testEvilConnectionRemove() throws Exception {
		try {
			EvilDAOFactory.getEvilInstance().getProfilePhotoDAO().removePhoto(1l);
			fail("Exception should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}
	
}
