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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<spring:message var="dateFormat" code="master.page.dateFormat"/>

<script>
$(function () {
	  $('[data-toggle="popover"]').popover()
	})
</script>
<div class="row row-width-prize prize-container">
<jstl:forEach items="${prizes}" var="row">
	<div class="all" data-trigger="hover"
		data-toggle="popover" data-trigger="focus" data-placement="top" data-content="${row.description}">
		<img class="shadowfilter" src="./images/chests/${row.getPrizeImage()}.png"/><div id="div${row.id}" onclick='javascript: openChest(${row.id})' class="centered-prize heart"></div>
		<div class="prize-info" id="info${row.id}">
		<spring:message code="master.page.munny"/>:
		${row.materials.munny}<br/>
		<spring:message code="master.page.mytrhil"/>:
		${row.materials.mytrhil}<br/>
		<spring:message code="master.page.gummyCoal"/>:
		${row.materials.gummiCoal}
		</div>
		<span id="badge${row.id}" class="badge-prize badge badge-info"><fmt:formatDate value="${row.date}" pattern='${dateFormat}' /></span>
	</div>
</jstl:forEach>
</div>