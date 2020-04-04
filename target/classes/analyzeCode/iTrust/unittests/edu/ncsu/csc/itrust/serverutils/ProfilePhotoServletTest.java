package edu.ncsu.csc.itrust.serverutils;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createControl;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import junit.framework.TestCase;
import org.easymock.classextension.IMocksControl;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.ProfilePhotoDAO;
import edu.ncsu.csc.itrust.server.ProfilePhotoServlet;

/**
 * 
 * @author andy
 * 
 */
public class ProfilePhotoServletTest extends TestCase {

	// This class uses an advanced concept not taught in CSC326 at NCSU called Mock Objects.
	// Feel free to take a look at how this works, but know that you will not need to know mock objects
	// to do nearly everything in iTrust. Unless your assignment mentions mock objects somewhere, you should
	// not need them.
	//
	// But, if you are interested in a cool unit testing concept, feel free to look at this code as an
	// example.
	//
	// This class uses the EasyMock library to manage the mock objects. http://easymock.org/

	private IMocksControl ctrl;
	private HttpServletRequest req;
	private HttpServletResponse resp;
	private HttpSession session;
	private DAOFactory mockDAOFactory;
	private ProfilePhotoDAO mockDAO;

	@Override
	protected void setUp() throws Exception {
		ctrl = createControl();
		req = ctrl.createMock(HttpServletRequest.class);
		resp = ctrl.createMock(HttpServletResponse.class);
		session = ctrl.createMock(HttpSession.class);
		mockDAOFactory = ctrl.createMock(DAOFactory.class);
		mockDAO = ctrl.createMock(ProfilePhotoDAO.class);
	}

	public void testProfileServlet() throws Exception {
		LittleDelegatorServlet servlet = new LittleDelegatorServlet();
		servlet.setDaoFactory(mockDAOFactory);
		resp.setContentType("image/jpg");
		expectLastCall().once();
		expect(req.getSession()).andReturn(session).once();
		expect(session.getAttribute("pid")).andReturn("2").once();
		expect(mockDAOFactory.getProfilePhotoDAO()).andReturn(mockDAO).once();
		expect(mockDAO.get(2L)).andReturn(new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB));
		expect(resp.getOutputStream()).andReturn(new ServletOutputStream() {
			@Override
			public void write(int b) throws IOException {
				// dump into nothingness
			}
		});
		ctrl.replay();

		servlet.doGet(req, resp);

		ctrl.verify();
	}

	public void testServletException() throws Exception {
		LittleDelegatorServlet servlet = new LittleDelegatorServlet();

		resp.setContentType("image/jpg");
		expectLastCall().once();
		expect(req.getSession()).andReturn(session).once();
		expect(session.getAttribute("pid")).andReturn("2").once();
		ctrl.replay();

		servlet.doGet(req, resp);// just swallows the exception

		ctrl.verify();
	}

	/**
	 * A little delegator that exposes our protected method
	 * 
	 * @author andy
	 */
	private class LittleDelegatorServlet extends ProfilePhotoServlet {
		private static final long serialVersionUID = -1256537436505857390L;

		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
				IOException {
			super.doGet(req, resp);
		}
	}

}
