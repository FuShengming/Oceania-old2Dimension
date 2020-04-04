package edu.ncsu.csc.itrust.bean;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.PrescriptionBean;

public class PrescriptionBeanTest extends TestCase {

	private PrescriptionBean pres;
	@Override
	protected void setUp() throws Exception {
		pres = new PrescriptionBean();
		pres.setDosage(50);
		pres.setStartDateStr("2007/05/19");
		pres.setEndDateStr("2010/05/19");
		pres.setVisitID(1L);
	}

	public void testPrescriptionEquals() throws Exception {
		pres.setId(5);
		PrescriptionBean pres2 = new PrescriptionBean();
		pres2.setId(5);
		pres2.setDosage(50);
		pres2.setStartDateStr("2007/05/19");
		pres2.setEndDateStr("2010/05/19");
		pres2.setVisitID(1L);
		assertEquals(pres2, pres);
		assertEquals("2010/05/19",pres2.getEndDateStr());
	}
}
