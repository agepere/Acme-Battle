<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:pagination page="${page}" pageNum="${pageNum}" requestURI="${requestURI}"/>

<display:table pagesize="5" class="displaytag" 
	name="users" requestURI="administrator/banned/list.do" id="row">

	<spring:message code="username" var="usernameH" />
	<display:column property="actor.userAccount.username" title="${usernameH}" />
	
	<spring:message code="reason" var="reasonH" />
	<display:column property="reason" title="${reasonH}" />
	
	<spring:message code="banDate" var="banDateH" />
	<display:column property="banDate" title="${banDateH}" />
	
	<spring:message code="duration" var="durationH" />
	<display:column property="duration" title="${durationH}" />
	
	<spring:message code="master.page.actions" var="actionsH" />
	<display:column title="${actionsH}">
	<a href="profile/actor/display.do?username=${row.actor.userAccount.username}"><spring:message code="master.page.view"/></a>
	|
	<a href="administrator/banned/unban.do?username=${row.actor.userAccount.username}"><spring:message code="unban"/></a>
	</display:column>
</display:table>
<br><br>
<acme:action code="master.page.users"   url="/profile/actor/list.do"/>
