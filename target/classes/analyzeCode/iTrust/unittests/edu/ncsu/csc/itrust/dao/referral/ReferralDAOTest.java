package edu.ncsu.csc.itrust.dao.referral;

import edu.ncsu.csc.itrust.dao.mysql.ReferralDAO;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class ReferralDAOTest extends TestCase {
	
	private ReferralDAO dao = TestDAOFactory.getTestInstance().getReferralDAO();
	private TestDataGenerator gen;

	protected void setUp() throws Exception {
		super.setUp();
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
	}
	
	public void testGetReferralsFromOV() throws Exception {
		assertEquals(4, dao.getReferralsFromOV(955).size());
		assertEquals(0, dao.getReferralsFromOV(952).size());
	}
	
	public void testGetReferralsSendFrom() throws Exception {
		assertEquals(8, dao.getReferralsSentFrom(9000000000L).size());
		assertEquals(9, dao.getReferralsSentFrom(9000000003L).size());
	}



}
