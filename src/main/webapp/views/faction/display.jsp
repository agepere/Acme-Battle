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
  <div class="card-body">
  <div class="row">
  <div class="col-3 text-center">
  	  	<jstl:choose>
				<jstl:when test="${fn:toLowerCase(faction.name) == 'light' or fn:toLowerCase(faction.name) == 'darkness'}">
				<img data-toggle="popover" data-trigger="hover" data-trigger="focus" data-placement="bottom" title="${faction.name}" data-content="${faction.powerUpDescription}" src="./images/factions/${fn:toLowerCase(faction.name)}.png"  width="150px" height="150px"/>
				</jstl:when>
				<jstl:otherwise>
				<img data-toggle="popover" data-trigger="hover" data-trigger="focus" data-placement="bottom" title="${faction.name}" data-content="${faction.powerUpDescription}" src="./images/factions/other.png"   width="150px" height="150px"/>
				</jstl:otherwise>
	</jstl:choose>	
  </div>
  <div class="col-9">
  	<ul class="list-group list-group-flush">
  	  <li class="list-group-item"><div class="row"><div class="col-6"><b><spring:message code="name"/>:</b></div>	<div class="col-6"><jstl:out value="${faction.name}"/></div></div></li>
  	  <li class="list-group-item"><div class="row"><div class="col-6"><b><spring:message code="powerUpDescription"/>:</b></div>	<div class="col-6"><jstl:out value="${faction.powerUpDescription}"/></div></div></li>
  	  <li class="list-group-item"><div class="row"><div class="col-6"><b><spring:message code="extraResources"/>:</b></div>		<div class="col-6"><jstl:out value="${faction.extraResources}"/></div></div></li>
  	  <li class="list-group-item"><div class="row"><div class="col-6"><b><spring:message code="extraAttack"/>:</b></div>		<div class="col-6"><jstl:out value="${faction.extraAttack}"/></div></div></li>
	  <li class="list-group-item"><div class="row"><div class="col-6"><b><spring:message code="extraDefense"/>:</b></div>	<div class="col-6"><jstl:out value="${faction.extraDefense}"/></div></div></li>
	  <li class="list-group-item"><div class="row"><div class="col-6"><b><spring:message code="galaxy"/>:</b></div>	<div class="col-6"><jstl:out value="${faction.galaxy}"/></div></div></li>
	</ul>
  
  </div>
  </div>
  </div>
  <div class="card-footer text-muted">
   <div style="float:right">
	<acme:action code="master.page.edit" color="outline-primary"  url="/faction/manager/edit.do?factionId=${faction.id}"/>
	</div>
  </div>
</div>
