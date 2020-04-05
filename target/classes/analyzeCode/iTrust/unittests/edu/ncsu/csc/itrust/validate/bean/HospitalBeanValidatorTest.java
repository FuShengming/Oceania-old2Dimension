package edu.ncsu.csc.itrust.validate.bean;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.HospitalBean;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.validate.BeanValidator;
import edu.ncsu.csc.itrust.validate.HospitalBeanValidator;
import edu.ncsu.csc.itrust.validate.ValidationFormat;

public class HospitalBeanValidatorTest extends TestCase {
	private BeanValidator<HospitalBean> validator = new HospitalBeanValidator();

	public void testAllCorrect() throws Exception {
		HospitalBean h = new HospitalBean();
		h.setHospitalName("Sta. Maria's Children Hospital");
		h.setHospitalID("1234567890");
		validator.validate(h);
	}

	public void testHospitalAllErrors() throws Exception {
		HospitalBean h = new HospitalBean();
		h.setHospitalName("A Hospital!");
		h.setHospitalID("-1");
		try {
			validator.validate(h);
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals("Hospital ID: " + ValidationFormat.HOSPITAL_ID.getDescription(), e.getErrorList().get(0));
			assertEquals("Hospital Name: " + ValidationFormat.HOSPITAL_NAME.getDescription(), e.getErrorList().get(1));
			assertEquals("number of errors", 2, e.getErrorList().size());
		}
	}
}
