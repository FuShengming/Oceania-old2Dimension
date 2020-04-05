package edu.ncsu.csc.itrust.action;

import java.sql.SQLException;
import java.sql.Timestamp;
import edu.ncsu.csc.itrust.beans.ApptBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.iTrustException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class AddApptActionTest extends TestCase {
	
	private AddApptAction action;
	private DAOFactory factory;
	private long mid = 1L;
	private long hcpId = 9000000000L;
	
	@Override
	protected void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
		
		this.factory = TestDAOFactory.getTestInstance();
		this.action = new AddApptAction(this.factory, this.hcpId);
	}
	
	public void testAddAppt() throws FormValidationException, SQLException {
		ApptBean b = new ApptBean();
		b.setApptType("General Checkup");
		b.setHcp(hcpId);
		b.setPatient(mid);
		b.setDate(new Timestamp(System.currentTimeMillis()+(10*60*1000)));
		b.setComment(null);
		
		assertTrue(action.addAppt(b).startsWith("Success"));
	}
	
	public void testAddAppt2() throws FormValidationException, SQLException {
		ApptBean b = new ApptBean();
		b.setApptType("General Checkup");
		b.setHcp(hcpId);
		b.setPatient(mid);
		Timestamp t = new Timestamp(System.currentTimeMillis()-(10*60*1000));
		b.setDate(t);
		b.setComment("Test Appiontment");
		
		assertEquals("The scheduled date of this Appointment ("+t+") has already passed.", action.addAppt(b));
	}
	
	public void testGetName() throws iTrustException {
		assertEquals("Kelly Doctor", action.getName(hcpId));
	}
	
	public void testGetName2() throws iTrustException {
		assertEquals("Random Person", action.getName(mid));
	}
}
