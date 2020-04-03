package edu.ncsu.csc.itrust.bean;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.HospitalBean;

public class HospitalBeanTest extends TestCase {

	public void testHospitalBean() throws Exception {
		HospitalBean h = new HospitalBean();
		h.setHospitalID("id");
		h.setHospitalName("name");
		assertEquals("id", h.getHospitalID());
		assertEquals("name", h.getHospitalName());
		assertEquals(42, h.hashCode());
	}
}
