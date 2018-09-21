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



<spring:message code="battle.attackerOwner"></spring:message>: 
<jstl:out value="${battle.attackerOwner }"></jstl:out><br>

<spring:message code="battle.isWon"></spring:message>: 
<jstl:out value="${battle.isWon}"></jstl:out><br>

<spring:message code="battle.wonOrLostMaterials"></spring:message>: 
<jstl:out value="${battle.wonOrLostMaterials }"></jstl:out>/
<br/>
<spring:message code="battle.balance"></spring:message>: 
<jstl:out value="${battle.balance }"></jstl:out>/
