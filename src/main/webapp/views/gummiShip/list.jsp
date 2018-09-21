<%--
 * action-2.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>



<jstl:forEach var="gummi" items="${gummis}" >
	<fieldset>
		<legend title="${gummi.name }"><jstl:out value="${gummi.name }"/></legend>
		
		<spring:message code="cost" var="materialCosts" />
		<jstl:out value="${materialCosts}: ${gummi.cost }"></jstl:out>
		<br>
		
		<spring:message code="timeToRecruit" var="timeToRecruit" />
		<jstl:out value="${timeToRecruit}: ${gummi.timeToRecruit }"></jstl:out>
		<br>
		
		<spring:message code="recruiterRequiredLvl" var="level" />
		<jstl:out value="${level}: ${gummi.recruiterRequiredLvl }"></jstl:out>
		<br>
		
		<spring:message code="gummiSlots" var="slots" />
		<jstl:out value="${slots}: ${gummi.slots}"></jstl:out>
		<br>
		<jstl:if test="${gummi.soyDueno()}">
			<acme:action code="gummiShip.edit"  url="gummiShip/contentManager/edit.do?recruiterId=${gummi.recruiter.id}&gummiShipId=${gummi.id}"/>
		</jstl:if>
		<br>
		<br>
	</fieldset>
</jstl:forEach>
<br><br>
