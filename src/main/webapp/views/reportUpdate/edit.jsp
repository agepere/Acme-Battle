<%--
 * edit.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="reportUpdate/edit.do" modelAttribute="reportUpdate">

<spring:message code="report.asistence" var="asistence"/>
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<%-- <security:authorize access="hasRole('GM')">
	<form:hidden path="administrator" value="0"/>
	<form:hidden path="gameMaster" />
	</security:authorize>
	
	<security:authorize access="hasRole('ADMIN')">
	<form:hidden path="administrator" />
	<form:hidden path="gameMaster" value="0"/>
	</security:authorize> --%>
	
	<input type="hidden" name="reportId" value="${reportId}"/>
	
	<form:label path="status">
		<spring:message code="report.status" />
	</form:label>	
	<form:select path="status">
		<form:option value="WORKING" label="WORKING" />		
		<form:option value="IRRESOLVABLE" label="IRRESOLVABLE" />	
		<jstl:if test="${reportUpdate.id == 0}">
		<form:option value="RESOLVED" label="RESOLVED" />	
		</jstl:if>		
	</form:select>
	<form:errors path="status" cssClass="error" /><br/><br/>
	
	<acme:textarea code="report.content" path="content"/><br/>
	
	<acme:submit code="master.page.save"  name="save" />
	<acme:cancel code="master.page.return" url="/report/list.do" />

</form:form>
