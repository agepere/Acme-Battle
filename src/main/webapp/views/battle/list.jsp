<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<display:table pagesize="5" class="displaytag" 
	name="players" requestURI="profile/actor/list.do" id="row">

	<spring:message code="username" var="usernameH" />
	<display:column title="${usernameH}" >
	<img src="${row.getAvatarImage()}" width="32px" height="32px" alt="${row.userAccount.username}"/> <jstl:out value="${row.userAccount.username}"/>
	</display:column>

	<spring:message code="nickname" var="nicknameH" />
	<display:column property="nickname" title="${nicknameH}" />

	<spring:message code="email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}" />
	
	<spring:message code="master.page.actions" var="actionsH" />
	<display:column title="${actionsH}">
	<a href="battle/recruited.do?nickname=${row.userAccount.username}"><spring:message code="master.page.attack"/></a>
	</display:column>
	

</display:table>