package edu.ncsu.csc.itrust.action;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.LOINCbean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.iTrustException;
import edu.ncsu.csc.itrust.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class UpdateLabProcListActionTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private UpdateLOINCListAction action;
	private TestDataGenerator gen;
	private static long performingAdmin = 9000000001L;

	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		action = new UpdateLOINCListAction(factory, performingAdmin);
		gen.clearAllTables();
		gen.admin1();
		gen.loincs();

	}

	public void testEvilFactory() throws Exception {
		action = new UpdateLOINCListAction(EvilDAOFactory.getEvilInstance(), 0l);
		String code = "28473-7";
		String com = "Poison Ivy";
		String kop = "VOL";
		LOINCbean db = new LOINCbean();
		db.setLabProcedureCode(code);
		db.setComponent(com);
		db.setKindOfProperty(kop);

		try {
			action.add(db);
			fail("exception should have been thrown");
		} catch (iTrustException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getExtendedMessage());
		}
		assertEquals("A database exception has occurred. Please see the log in the console for stacktrace",
				action.updateInformation(db));

	}

	private String getAddCodeSuccessString(LOINCbean proc) {
		return "Success: " + proc.getLabProcedureCode() + " added";
	}

	public void testAddICDCode() throws Exception {
		String code = "78743-7";
		String com = "Poison Oak";
		String kop = "VOL";
		LOINCbean proc = new LOINCbean();
		proc.setLabProcedureCode(code);
		proc.setComponent(com);
		proc.setKindOfProperty(kop);
		assertEquals(getAddCodeSuccessString(proc), action.add(proc));
		proc = factory.getLOINCDAO().getProcedures(code).get(0);
		assertEquals(com, proc.getComponent());
		assertEquals(kop, proc.getKindOfProperty());

	}

	public void testAddDuplicate() throws Exception {
		String code = "73823-7";
		String com = "Yellow Tooth";
		String kop = "VOL";
		LOINCbean proc = new LOINCbean();
		proc.setLabProcedureCode(code);
		proc.setComponent(com);
		proc.setKindOfProperty(kop);
		assertEquals(getAddCodeSuccessString(proc), action.add(proc));
		proc.setKindOfProperty("VIL");
		try {
			action.add(proc);
			fail("FormValidationException should have been thrown");
		} catch (Exception ex) {
			assertEquals(
					"This form has not been validated correctly. The following field are not properly filled in: [Error: Code already exists.]",
					ex.getMessage());
		}
		proc = factory.getLOINCDAO().getProcedures(code).get(0);
		assertEquals(kop, proc.getKindOfProperty());
	}

	public void testUpdateICDInformation0() throws Exception {
		String code = "98323-7";
		String com = "Malaria";
		String kop = "VOL";
		LOINCbean proc = new LOINCbean();
		proc.setLabProcedureCode(code);
		proc.setComponent(com);
		proc.setKindOfProperty(kop);
		assertEquals(getAddCodeSuccessString(proc), action.add(proc));
		proc.setKindOfProperty("Per");
		assertEquals("Success: " + proc.getLabProcedureCode() + " updated", action.updateInformation(proc));
		proc = factory.getLOINCDAO().getProcedures(code).get(0);
		assertEquals("Per", proc.getKindOfProperty());
	}

	public void testUpdateNonExistent() throws Exception {
		String code = "99999-9";
		String com = "Malaria";
		String kop = "VOL";
		LOINCbean proc = new LOINCbean();
		proc.setLabProcedureCode(code);
		proc.setComponent(com);
		proc.setKindOfProperty(kop);
		assertEquals("Error: Code not found.", action.updateInformation(proc));
		assertTrue(factory.getLOINCDAO().getProcedures(code).isEmpty());
	}

}
