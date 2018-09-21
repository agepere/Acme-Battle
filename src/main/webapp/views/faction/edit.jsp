<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="faction/manager/edit.do" modelAttribute="faction" class="form-signin">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<acme:textbox code="name" path="name"/>
	<acme:textbox  code="powerUpDescription" path="powerUpDescription"/>
	<acme:textbox code="extraResources" path="extraResources"/>
	<acme:textbox  code="extraAttack" path="extraAttack" />
	<acme:textbox  code="extraDefense" path="extraDefense"/>
	<acme:textbox code="galaxy" path="galaxy"/>
	
	
	<acme:submit code="master.page.save"  name="save"/>
	
</form:form>