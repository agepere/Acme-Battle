<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="profile/actor/edit.do" modelAttribute="actorForm" class="form-signin">

	<form:hidden path="username" />
	
	<acme:textbox code="name" path="name"/>
	<acme:textbox  code="surname" path="surname"/>
	<acme:textbox  code="email" path="email" />
	<acme:textbox  code="phone" path="phone"/>
	<acme:textbox code="avatar" path="avatar"/>
	
	<acme:submit code="master.page.save"  name="save"/>
	
</form:form>