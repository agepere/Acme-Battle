<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="administrator/banned/create.do" modelAttribute="banned" class="form-signin">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<!--  -->
	<form:hidden path="actor" />
	
	<!-- This parameters will be set default when save, there are here for validator. -->
	<form:hidden path="isValid" />
	<form:hidden path="banDate" />
	
	<acme:textbox  code="duration" path="duration"/>
	<acme:textbox code="reason" path="reason"/>
	<br/>
	<acme:submit code="master.page.save"  name="save"/>
	
</form:form>