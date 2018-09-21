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
function openChest(id){
	
	document.getElementById("div"+id).className += " heart-blast";
	document.getElementById("badge"+id).style.display = "none";
	$("[data-toggle='popover']").popover('hide');
	
	setTimeout(function(){ 
		$("#info"+id).fadeIn();
	}, 100);
	
	setTimeout(function(){ 
		$('#container').load("prize/openAJAX.do?prizeId="+id);
	}, 1500);
	
	setTimeout(function(){ 
		$(".materials-panel").load("keybladewielder/updateMaterialsPanel.do");
	}, 2000);
	
	
}

</script>   

<acme:pagination page="${page}" pageNum="${pageNum}" requestURI="${requestURI}"/>
<div id="container">
<div class="row row-width-prize prize-container">
<jstl:forEach items="${prizes}" var="row">

<jstl:if test="${row.description.contains('defaultDescription') }">
			<spring:message code="${row.description}" var="descriptionMessage"></spring:message>
		</jstl:if>
		<jstl:if test="${!row.description.contains('defaultDescription')}">
			<jstl:set value="${row.description }"  var="descriptionMessage"></jstl:set>
		
		</jstl:if>


<div id="all${row.id}" class="all" data-trigger="hover"
		data-toggle="popover" data-trigger="focus" data-placement="top" data-content="${descriptionMessage}">
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
</div>
<%-- <display:table name="prizes" id="row" pagesize="5" requestURI="prize/list.do">

	<spring:message code="prize.description" var="descriptionHeader"></spring:message>
	<display:column  title="${descriptionHeader}">
	
		<jstl:if test="${row.description.contains('defaultDescription') }">
			<spring:message code="${row.description}"></spring:message>
		</jstl:if>
		<jstl:if test="${!row.description.contains('defaultDescription')}">
			${row.description }
		
		</jstl:if>
	
	</display:column>
						  
	<spring:message code="master.page.munny" var="munnyHeader"></spring:message>
	<display:column property="materials.munny" title="${munnyHeader}"></display:column>
	
	<spring:message code="master.page.mytrhil" var="mytrhilHeader"></spring:message>
	<display:column property="materials.mytrhil" title="${mytrhilHeader}"></display:column>
	
	<spring:message code="master.page.gummyCoal" var="gummyCoalHeader"></spring:message>
	<display:column property="materials.gummiCoal" title="${gummyCoalHeader}"></display:column>
	
	<spring:message code="prize.expirationDate" var="expirationDateHeader"></spring:message>
	<display:column property="date" title="${expirationDateHeader}"></display:column>
	
	<spring:message code="prize.open" var="openHeader"></spring:message>
	<display:column  title="${openHeader}">
	
		<a href="prize/open.do?prizeId=${row.id}"><jstl:out value="${openHeader}"></jstl:out></a>
	
	</display:column>
						 
	<display:column>
		<label for="toggle-heart"><img src="./images/chests/${row.getPrizeImage()}.png"/></label>
		<img src="./images/chests/${row.getPrizeImage()}.png"/>	<div class="heart"></div>
	</display:column>

</display:table> --%>



