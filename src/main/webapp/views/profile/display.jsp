<%--
 * action-1.jsp
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
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<div class="card" style="color: #212529;">
  <div class="card-header font-weight-bold text-primary" style="background-color:#D8ECF6;">
    <jstl:out value="${user.name}"/> <jstl:out value="${user.surname}"/>
  </div>
  <div class="card-body">
  <div class="row">
  <div class="col-3 text-center">
  <p  class="badge badge-primary" style="width:150px"><jstl:out value="${user.getActorTypeName()}"/></p>
  	<img src="${user.getAvatarImage()}" width="150px" height="150px" class="circular" />
  	<jstl:if test="${user.getClass().name  eq 'domain.KeybladeWielder'}">
  	<br />
  	  	<jstl:choose>
				<jstl:when test="${fn:toLowerCase(user.faction.name) == 'light' or fn:toLowerCase(user.faction.name) == 'darkness'}">
				<img data-toggle="popover" data-trigger="hover" data-trigger="focus" data-placement="bottom" title="${user.faction.name}" data-content="${user.faction.powerUpDescription}" src="./images/factions/${fn:toLowerCase(user.faction.name)}.png" class="faction-profile" width="50px" height="50px"/>
				</jstl:when>
				<jstl:otherwise>
				<img data-toggle="popover" data-trigger="hover" data-trigger="focus" data-placement="bottom" title="${user.faction.name}" data-content="${user.faction.powerUpDescription}" src="./images/factions/other.png" class="faction-profile"  width="50px" height="50px"/>
				</jstl:otherwise>
	</jstl:choose>	
  	</jstl:if>
  </div>
  <div class="col-9">
  	<ul class="list-group list-group-flush">
  	  <li class="list-group-item"><div class="row"><div class="col-6"><b><spring:message code="username"/>:</b></div>	<div class="col-6"><jstl:out value="${user.userAccount.username}"/></div></div></li>
  	  <li class="list-group-item"><div class="row"><div class="col-6"><b><spring:message code="email"/>:</b></div>		<div class="col-6"><jstl:out value="${user.email}"/></div></div></li>
  	  <li class="list-group-item"><div class="row"><div class="col-6"><b><spring:message code="phone"/>:</b></div>		<div class="col-6"><jstl:out value="${user.phone}"/></div></div></li>
	  <li class="list-group-item"><div class="row"><div class="col-6"><b><spring:message code="nickname"/>:</b></div>	<div class="col-6"><jstl:out value="${user.nickname}"/></div></div></li>
	  
	  <jstl:if test="${user.getClass().name  eq 'domain.KeybladeWielder'}">
		  <li class="list-group-item"><div class="row"><div class="col-6"><b><spring:message code="wins"/>:</b></div><div class="col-6"><jstl:out value="${user.wins}"/></div></div></li>
		  <li class="list-group-item"><div class="row"><div class="col-6"><b><spring:message code="loses"/>:</b></div><div class="col-6"><jstl:out value="${user.loses}"/></div></div></li>
		  <li class="list-group-item"><div class="row"><div class="col-6"><b><spring:message code="world"/>:</b></div><div class="col-6"><jstl:out value="${user.worldName}"/> (<jstl:out value="${user.worldCoordinates.x}"/>, <jstl:out value="${user.worldCoordinates.y}"/>, <jstl:out value="${user.worldCoordinates.z}"/>)</div></div></li>
	  	  <li class="list-group-item"><div class="row"><div class="col-6"><b><spring:message code="lastConnection"/>:</b></div><div class="col-6"><jstl:out value="${user.lastConnection}"/></div></div></li>
	  </jstl:if>
	</ul>
  
  </div>
  </div>
  </div>
  <div class="card-footer text-muted">
  <jstl:if test="${user.getClass().name  eq 'domain.KeybladeWielder'}">
  <acme:action code="world" color="success"  url="/keybladewielder/world.do?username=${user.userAccount.username}"/>
  
  	<jstl:if test="${puedoEnviarInvitation and not hasOrganization}">
	<acme:action code="invitation.send" color="secondary"  url="/organization/invitation/edit.do?username=${user.userAccount.username}"/>
	</jstl:if>
  </jstl:if>
  
  <div style="float:right">
   <jstl:if test="${user.userAccount.getOwner()}">
   
	<acme:action code="master.page.edit" color="outline-primary"  url="/profile/actor/edit.do"/>
	
	</jstl:if>
	<security:authorize access="hasRole('ADMIN')">
		<jstl:if test="${not user.userAccount.getOwner()}">
			<jstl:choose>
						<jstl:when test="${user.userAccount.isEnabled()}">
						<acme:action code="ban" color="outline-danger"  url="/administrator/banned/create.do?username=${user.userAccount.username}"/>
						</jstl:when>
						<jstl:otherwise>
						<acme:action code="unban" color="outline-warning"  url="/administrator/banned/unban.do?username=${user.userAccount.username}"/>
						</jstl:otherwise>
			</jstl:choose>	
		</jstl:if>
	</security:authorize>
	</div>
  </div>
</div>
