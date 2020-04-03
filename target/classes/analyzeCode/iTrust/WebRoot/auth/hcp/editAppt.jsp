<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="edu.ncsu.csc.itrust.action.EditApptAction"%>
<%@page import="edu.ncsu.csc.itrust.action.EditApptTypeAction"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewMyApptsAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.ApptBean"%>
<%@page import="edu.ncsu.csc.itrust.beans.ApptTypeBean"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>

<%@include file="/global.jsp" %>

<%
pageTitle = "iTrust - Edit Appointment";
%>

<%@include file="/header.jsp" %>

<%
	EditApptTypeAction types = new EditApptTypeAction(prodDAO, loggedInMID.longValue());
	EditApptAction action = new EditApptAction(prodDAO, loggedInMID.longValue());
	ApptBean original = null;
	List<ApptTypeBean> apptTypes = types.getApptTypes();
	String aptParameter = "";
	if (request.getParameter("apt") != null) {
		aptParameter = request.getParameter("apt");
		try {
			int apptID = Integer.parseInt(aptParameter);
			original = action.getAppt(apptID);
			if (original == null){
				response.sendRedirect("viewMyAppts.jsp");
			}
		} catch (NullPointerException npe) {
			response.sendRedirect("viewMyAppts.jsp");
		} catch (NumberFormatException e) {
			// Handle Exception
			response.sendRedirect("viewMyAppts.jsp");
		}
	} else {
		response.sendRedirect("viewMyAppts.jsp");
	}
	// Handle form submit here
	if (request.getParameter("editAppt") != null && request.getParameter("apptID") != null) {
		String headerMessage = "";
		if (request.getParameter("editAppt").equals("Change")) {
			// Change the appointment
			if(!request.getParameter("schedDate").equals("")) {	
				ApptBean appt = new ApptBean();
				appt.setApptID(Integer.parseInt(request.getParameter("apptID")));
				DateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
				Date date = format.parse(request.getParameter("schedDate")+" "+request.getParameter("time1")+":"+request.getParameter("time2")+" "+request.getParameter("time3"));
				appt.setApptType(request.getParameter("apptType"));
				appt.setDate(new Timestamp(date.getTime()));
				String comment = "";
				if(request.getParameter("comment").equals("") || request.getParameter("comment").equals("No Comment"))
					comment = null;
				else 
					comment = request.getParameter("comment");
				appt.setComment(comment);
				try {
					headerMessage = action.editAppt(appt);
					if(headerMessage.startsWith("Success")) {
						session.removeAttribute("pid");
						loggingAction.logEvent(TransactionType.APPOINTMENT_EDIT, loggedInMID.longValue(), original.getPatient(), ""+original.getApptID());
						%>
							<div align=center>
								<span class="iTrustMessage"><%=StringEscapeUtils.escapeHtml(headerMessage)%></span>
							</div>
						<%
					} else {
						%>
							<div align=center>
								<span class="iTrustError"><%=StringEscapeUtils.escapeHtml(headerMessage)%></span>
							</div>
						<%
					}
				} catch (FormValidationException e){
				%>
				<div align=center><span class="iTrustError"><%=StringEscapeUtils.escapeHtml(e.getMessage())%></span></div>
				<%	
				}
			} else {
				headerMessage = "Please input a date for the appointment.";
			}
		} else if (request.getParameter("editAppt").equals("Remove")) {
			// Delete the appointment
			ApptBean appt = new ApptBean();
			appt.setApptID(Integer.parseInt(request.getParameter("apptID")));

			headerMessage = action.removeAppt(appt);
			if(headerMessage.startsWith("Success")) {
				session.removeAttribute("pid");
				loggingAction.logEvent(TransactionType.APPOINTMENT_REMOVE, loggedInMID.longValue(), original.getPatient(), ""+original.getApptID());
				%>
				<div align=center>
					<span class="iTrustMessage"><%=StringEscapeUtils.escapeHtml(headerMessage)%></span>
				</div>
				<%
			} else {
				%>
					<div align=center>
						<span class="iTrustError"><%=StringEscapeUtils.escapeHtml(headerMessage)%></span>
					</div>
				<%
			}
		}
	} else if (original != null) {
		Date d = new Date(original.getDate().getTime());
		DateFormat dFormat = new SimpleDateFormat("MM/dd/yyyy");
		DateFormat tFormat = new SimpleDateFormat("hhmma");
		boolean selected = false;
		String hPart = tFormat.format(d).substring(0,2);
		String mPart = tFormat.format(d).substring(2,4);
		String aPart = tFormat.format(d).substring(5);
		
		loggingAction.logEvent(TransactionType.APPOINTMENT_VIEW, loggedInMID, original.getPatient(), "" + original.getApptID());
	%>
	<form id="mainForm" method="post" action="editAppt.jsp?apt=<%=aptParameter %>&apptID=<%=original.getApptID() %>">
		<div>
			<table width="99%">
				<tr>
					<th>Appointment Info</th>
				</tr>
				<tr>
					<td><b>Patient:</b> <%= StringEscapeUtils.escapeHtml("" + ( action.getName(original.getPatient()) )) %></td>
				</tr>
				<tr>
					<td>
						<b>Type:</b>
						<select name="apptType">
					<%
						for(ApptTypeBean b : apptTypes) {
					%>
							<option <%=StringEscapeUtils.escapeHtml((b.getName().equals(original.getApptType())?"selected ":"")) %>value="<%= StringEscapeUtils.escapeHtml(b.getName()) %>">
								<%= StringEscapeUtils.escapeHtml(b.getName() + " - " + b.getDuration()+ " minutes") %>
							</option>
					<%
						}
					%>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						<b>Date:</b>
						<input type="text" name="schedDate" value="<%=dFormat.format(d) %>" />
						<input type="button" value="Select Date" onclick="displayDatePicker('schedDate');" />
					</td>
				</tr>
				<tr>
					<td>
						<b>Time:</b>
						<select name="time1">
							<%
								String hour = "";
								for(int i = 1; i <= 12; i++) {
									if(i < 10) hour = "0"+i;
									else hour = i+"";
									selected = hour.equals(hPart);
									%>
										<option <%=selected?"selected ":"" %>value="<%=hour%>"><%= StringEscapeUtils.escapeHtml("" + (hour)) %></option>
									<%
								}
							%>
						</select>:
						<select name="time2">
							<%
								String min = "";
								for(int i = 0; i < 60; i+=5) {
									if(i < 10) min = "0"+i;
									else min = i+"";
									selected = min.equals(mPart);
									%>
										<option <%=selected?"selected ":"" %>value="<%=min%>"><%= StringEscapeUtils.escapeHtml("" + (min)) %></option>
									<%
								}
								selected = "AM".equals(aPart);
							%>
						</select>
						<select name="time3">
							<option <%=selected?"selected ":"" %>value="AM">AM</option>
							<option <%=selected?"":"selected " %>value="PM">PM</option>
						</select>
					</td>
				</tr>
			</table>
		</div>
		
		<table>
			<tr>
				<td colspan="2"><b>Comments:</b></td>
			</tr>
			<tr>
				<td>
					<textarea name="comment" cols="100" rows="10"><%=StringEscapeUtils.escapeHtml("" + ((original.getComment()== null)?"No Comment":original.getComment())) %></textarea>
				</td>
			</tr>
		</table>
		<input type="submit" value="Change" name="editAppt"/>
		<input type="submit" value="Remove" name="editAppt"/>
	</form>
<%
	}
%>

<%@include file="/footer.jsp" %>
