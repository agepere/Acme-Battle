<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="profile/actor/register.do" modelAttribute="actor" class="form-signin">


	<acme:textbox code="username" path="userAccount.username" />
	<acme:password code="password" path="userAccount.password" />
	<form:hidden path="userAccount.authorities" />
	<acme:textbox code="nickname" path="nickname" />
	<acme:textbox code="name" path="name" />
	<acme:textbox  code="surname" path="surname" />
	<acme:textbox  code="email" path="email"/>
	<acme:textbox  code="phone" path="phone"/>
	
	<security:authorize access="isAnonymous()">
	
	<center>
	<hr>
	<spring:message code="worldName" var="worldNameH"/>
	<label for="${worldNameH}"><jstl:out value="${worldNameH}"/></label>
	<input type="text" name="worldName" class="form-control" placeholder="${worldNameH}"/>
	
	<spring:message code="faction" var="factionH"/>
	<label for="${factionH}"><jstl:out value="${factionH}"/></label><br/>
	<div class="btn-group btn-group-toggle" data-toggle="buttons">
	<jstl:forEach items="${factions}" var="faction">
		<label class="btn btn-light <jstl:if test="${fn:toLowerCase(faction.name) == 'light'}">active</jstl:if>"
		data-toggle="popover" data-trigger="focus" data-placement="bottom" title="${faction.name}" data-content="${faction.powerUpDescription}">
	    	<input type="radio" name="factionId" value="${faction.id}" id="option${faction.id}" autocomplete="off" <jstl:if test="${fn:toLowerCase(faction.name) == 'light'}">checked</jstl:if>> 
	    	<jstl:choose>
				<jstl:when test="${fn:toLowerCase(faction.name) == 'light' or fn:toLowerCase(faction.name) == 'darkness'}">
				<img src="./images/factions/${fn:toLowerCase(faction.name)}.png" width="50px" height="50px"/>
				</jstl:when>
				<jstl:otherwise>
				<img src="./images/factions/other.png" width="50px" height="50px"/>
				</jstl:otherwise>
			</jstl:choose>	
	  	</label>
	</jstl:forEach>
	</div>
	</center>
		</security:authorize>
		
	<br/>
	
	<form:checkbox path="hasConfirmedTerms" value="true" />
	<spring:message code="master.page.readTermsAndConditions" />
	<form:errors cssClass="error" path="hasConfirmedTerms" /><br/><br/>
	
	<%-- <acme:submit code="user.register"  name="register"/> --%>
	<input type="submit" name="register" class="btn btn-pink"
		value="<spring:message code="master.page.signup"/>" />
</form:form>