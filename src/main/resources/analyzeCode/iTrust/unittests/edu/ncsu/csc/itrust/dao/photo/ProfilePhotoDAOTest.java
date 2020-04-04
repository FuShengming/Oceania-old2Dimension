package edu.ncsu.csc.itrust.dao.photo;

import java.awt.image.BufferedImage;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.dao.mysql.ProfilePhotoDAO;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class ProfilePhotoDAOTest extends TestCase {
	private TestDataGenerator gen;
	private ProfilePhotoDAO mydao;

	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearProfilePhotos();
		mydao = TestDAOFactory.getTestInstance().getProfilePhotoDAO();
	}

	public void testStoreAndGetPicture() throws Exception {
		gen.patient1();
		BufferedImage bi = new BufferedImage(900, 500, BufferedImage.TYPE_3BYTE_BGR);
		assertEquals(1, mydao.store(1l, bi));

		BufferedImage returnedimage = mydao.get(1l);
		assertEquals(bi.getWidth(), returnedimage.getWidth());
		assertEquals(bi.getHeight(), returnedimage.getHeight());
	}

	public void testStoreDifferentPictureOverTop() throws Exception {
		gen.patient1();
		BufferedImage bi = new BufferedImage(500, 600, BufferedImage.TYPE_3BYTE_BGR);
		BufferedImage bi2 = new BufferedImage(300, 400, BufferedImage.TYPE_3BYTE_BGR);
		assertEquals(1, mydao.store(1l, bi));
		mydao.store(1l, bi2); // should overwrite automatically - b/c of "ON DUPLICATE UPDATE"
		BufferedImage returnedimage = mydao.get(1l);
		assertEquals(bi2.getWidth(), returnedimage.getWidth());
		assertEquals(bi2.getHeight(), returnedimage.getHeight());
	}

	public void testGetDefaultNoPicture() throws Exception {
		gen.patient1();
		assertSame(ProfilePhotoDAO.DEFAULT_PROFILE_PHOTO, TestDAOFactory.getTestInstance()
				.getProfilePhotoDAO().get(7l));
	}
	
	public void testRemovePicture() throws Exception
	{
		gen.patient1();
		BufferedImage bi = new BufferedImage(500, 600, BufferedImage.TYPE_3BYTE_BGR);
		assertEquals(1, mydao.store(1l, bi));
		assertEquals(1, mydao.removePhoto(1));
		assertSame(ProfilePhotoDAO.DEFAULT_PROFILE_PHOTO, TestDAOFactory.getTestInstance()
				.getProfilePhotoDAO().get(1l));
	}
}
