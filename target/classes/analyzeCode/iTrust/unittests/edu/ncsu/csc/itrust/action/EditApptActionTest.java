package edu.ncsu.csc.itrust.action;

import java.util.Date;
import java.util.List;
import edu.ncsu.csc.itrust.beans.ApptBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.iTrustException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class EditApptActionTest extends TestCase {
	private EditApptAction editAction;
	private ViewMyApptsAction viewAction;
	private DAOFactory factory;
	private long hcpId = 9000000000L;
	
	@Override
	protected void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.hcp0();
		gen.patient42();
		gen.appointment();
		
		this.factory = TestDAOFactory.getTestInstance();
		this.editAction = new EditApptAction(this.factory, this.hcpId);
		this.viewAction = new ViewMyApptsAction(this.factory, this.hcpId);
	}
	
	public void testRemoveAppt() throws Exception {
		List<ApptBean> appts = viewAction.getMyAppointments();
		assertEquals(15, appts.size());
		assertEquals("Success: Appointment removed", editAction.removeAppt(appts.get(0)));
		assertEquals(14, viewAction.getMyAppointments().size());
		editAction.removeAppt(appts.get(0));
	}
	
	public void testGetAppt() throws Exception {
		List<ApptBean> appts = viewAction.getMyAppointments();
		ApptBean b1 = appts.get(0);
		ApptBean b2 = editAction.getAppt(b1.getApptID());
		
		assertEquals(b1.getApptID(), b2.getApptID());
		assertEquals(b1.getApptType(), b2.getApptType());
		assertEquals(b1.getComment(), b2.getComment());
		assertEquals(b1.getHcp(), b2.getHcp());
		assertEquals(b1.getPatient(), b2.getPatient());
		assertEquals(b1.getClass(), b2.getClass());
		assertEquals(b1.getDate(), b2.getDate());
	}
	
	public void testGetName() throws iTrustException {
		assertEquals("Kelly Doctor", editAction.getName(hcpId));
		assertEquals("Bad Horse", editAction.getName(42));
	}
	
	public void testEditAppt() throws Exception {
		List<ApptBean> appts = viewAction.getMyAppointments();
		ApptBean orig = appts.get(0);
		ApptBean b = new ApptBean();
		b.setApptID(orig.getApptID());
		b.setDate(orig.getDate());
		b.setApptType(orig.getApptType());
		b.setHcp(orig.getHcp());
		b.setPatient(orig.getPatient());
		b.setComment("New comment!");
		
		String s = editAction.editAppt(b);
		assertTrue(s.contains("The scheduled date of this appointment"));
		assertTrue(s.contains("has already passed"));
		
		Date d = new Date();
		boolean changed = false;
		for (ApptBean aBean : appts) {
			b = new ApptBean();
			b.setApptID(aBean.getApptID());
			b.setDate(aBean.getDate());
			b.setApptType(aBean.getApptType());
			b.setHcp(aBean.getHcp());
			b.setPatient(aBean.getPatient());
			b.setComment("New comment!");
			d.setTime(aBean.getDate().getTime());
			if (d.after(new Date())) {
				s = editAction.editAppt(b);
				//assertTrue(s.contains("Success: Appointment changed"));
				assertEquals("Success: Appointment changed", s);
				changed = true;
				break;
			}
		}
		
		if (!changed)
			fail();
	}
}
