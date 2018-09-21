<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<display:table pagesize="5" class="displaytag" 
	name="factions" requestURI="faction/manager/list.do" id="row">

	<spring:message code="name" var="nameH" />
	<display:column property="name" title="${nameH}" />

	<spring:message code="powerUpDescription" var="powerUpDescriptionH" />
	<display:column property="powerUpDescription" title="${powerUpDescriptionH}" />
	
	<spring:message code="master.page.actions" var="actionsH" />
	<display:column title="${actionsH}">
	<a href="faction/manager/display.do?factionId=${row.id}"><spring:message code="master.page.view"/></a>
	</display:column>
	

</display:table>

<acme:action code="master.page.create" color="primary"  url="/faction/manager/create.do"/>