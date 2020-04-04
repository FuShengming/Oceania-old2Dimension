package edu.ncsu.csc.itrust.action;

import java.util.List;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;


public class SearchUsersActionTest extends TestCase {
	private TestDataGenerator gen = new TestDataGenerator();
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private DAOFactory evil = EvilDAOFactory.getEvilInstance();
	@Override
	public void setUp() throws Exception {
		gen.clearAllTables();
		gen.standardData();
	}
	
	public void testSearchForPatientsWithName(){
		SearchUsersAction act = new SearchUsersAction(factory, 9000000000L);
		List<PatientBean> patients = act.searchForPatientsWithName("Random", "Person");
		assertEquals("Random Person",patients.get(0).getFullName());
	}
	
	public void testSearchForPatientsWithName2(){
		SearchUsersAction act = new SearchUsersAction(factory, 9000000003L);
		List<PatientBean> patient = act.searchForPatientsWithName("Andy", "Programmer");
		assertEquals("Andy Programmer", patient.get(0).getFullName());
	}
	
	public void testSearchForPatientsWithName3(){
		SearchUsersAction act = new SearchUsersAction(factory, 9000000003L);
		List<PatientBean> patient = act.searchForPatientsWithName("", "");
		assertEquals(0, patient.size());
	}
	
	public void testSearchForPersonnelWithName(){
		SearchUsersAction act = new SearchUsersAction(factory, 9000000000L);
		List<PersonnelBean> personnel = act.searchForPersonnelWithName("Kelly", "Doctor");
		assertEquals("Kelly Doctor",personnel.get(0).getFullName());
	}
	
	public void testSearchForPersonnelWithName2(){
		SearchUsersAction act = new SearchUsersAction(factory, 9000000003L);
		List<PersonnelBean> personnel = act.searchForPersonnelWithName("", "");
		assertEquals(0, personnel.size());
	}
	
	public void testSearchForPersonnelWithName3(){
		SearchUsersAction act = new SearchUsersAction(evil, 2L);
		List<PersonnelBean> personnel = act.searchForPersonnelWithName("null", "null");
		assertEquals(null, personnel);
	}
	
	@Override
	public void tearDown() throws Exception {
	}
	
	public void testZeroPatients() throws Exception {
		SearchUsersAction action = new SearchUsersAction(factory, 9990000000L);
		List<PatientBean> patients = action.searchForPatientsWithName("A","B");
		assertNotNull(patients);
		assertEquals(0, patients.size());
	}
	

}
