<%@page import="edu.ncsu.csc.itrust.beans.PatientBean"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="edu.ncsu.csc.itrust.exception.iTrustException"%>
<%@page import="edu.ncsu.csc.itrust.action.ProfilePhotoAction"%>
<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>

<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>

<%@include file="/global.jsp"%>

<%
	pageTitle = "iTrust - Patient Photo";
%>

<%@include file="/header.jsp"%>


<%
	/* Require a Patient ID first */
	String pidString = (String) session.getAttribute("pid");
	if (pidString == null || 1 > pidString.length()) {
		if (pidString == null || 1 > pidString.length()) {
			response.sendRedirect("/iTrust/auth/getPatientID.jsp?forward=hcp-uap/editPatientPhoto.jsp");
			return;
		}
	}

	long patientMID = Long.parseLong(pidString);
	String message = "";
	PatientBean patient = DAOFactory.getProductionInstance().getPatientDAO().getPatient(patientMID);

	ProfilePhotoAction action = new ProfilePhotoAction(DAOFactory.getProductionInstance(), patientMID);
	if (ServletFileUpload.isMultipartContent(request)) {
		try
		{
			message = action.storePicture(request);
			loggingAction.logEvent(TransactionType.PATIENT_PHOTO_UPLOAD, loggedInMID.longValue(), patient.getMID(), "");
		} catch (Exception e)
		{
			message = "There was an error uploading the photo. Please try again.";
		}
	}
	
	if(request.getParameter("remove") != null)
	{
		try{
			message = action.removePhoto(patientMID);
			loggingAction.logEvent(TransactionType.PATIENT_PHOTO_REMOVE, loggedInMID.longValue(), patient.getMID(), "");
		} catch (Exception e)
		{
			message = "There was an error retrieving the photo. Please try again.";
		}
	}

%>
<span class="iTrustMessage"><%=message %></span>
<table class="fancyTable">
	<tr>
		<th colspan="2"><%=patient.getFullName() %>'s Photo</th>
	</tr>
	<tr>
		<td rowspan="2">
			<img style="width:100px;height:100px;" src="<%=request.getContextPath()%>/auth/hcp-uap/profilephoto" alt="<%= userName%>">
		</td>
		<td>Update the patient photo.
			<form action="" method="post" enctype="multipart/form-data">
				<input type="file" name="photo"> 
	 			<input type="submit" value="Upload">
	 		</form>
	 	</td>
	 	</tr>
	 	<tr>
	 	<td>
	 		<form action="editPatientPhoto.jsp" method="post">
	 			Remove existing photo and replace with default photo.
	 			<input type="submit" name="remove" value="Remove" />
	 		</form>
		</td>
	</tr>
</table>

<br>
This is an example page for future assignments. Eventually, we would
like to have full-featured, secure file uploading capabilities for
things like medical imaging and document management (e.g. scanned PDFs).
For now, this example is your basic profile picture. Here are some
things to note about this example
<ul>
	<li>All photos are stored in the MySQL database - <em>not</em> on
	the file system. This is desirable for things like security, platform
	independence, and simpler deployment. We use the BLOB database type to
	store the image's bytes.</li>
	<li>Currently, we only support JPEG, although we could easily
	support other formats by adding a column to the database for the file
	type.</li>
	<li>Note the file upload limit is enforced at the database level.
	This is not as desirable as in the upload stage, because one could
	upload a 100 GB file and crash the server before it ever gets to the
	database. We will need some kind of validation for the file size.</li>
</ul>
<br>
To browse through this example, look through the following files:
<ul>
	<li>WebRoot/auth/examplephotoupload.jsp</li>
	<li>sql/createTables.sql - the ProfilePhotos table</li>
	<li>ProfilePhotoAction - this is the upload part where we use an
	external library to do our uploads</li>
	<li>ProfilePhotoDAO - this is the storage part</li>
	<li>ProfilePhotoServlet - this is the Java servlet used for actually generating the image</li>
	<li>WebRoot/WEB-INF/web.xml - this is where the URL for PhotoProfileServlet is defined</li>
</ul>
<br>

</div>

<%@include file="/footer.jsp"%>