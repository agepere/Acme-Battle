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

<spring:message code="building.name"></spring:message>: 
<jstl:out value="${building.name }"></jstl:out><br>

<spring:message code="building.descritpion"></spring:message>: 
<jstl:out value="${building.description }"></jstl:out><br>

<spring:message code="building.cost"></spring:message>: 
<jstl:out value="${building.cost }"></jstl:out><br>

<spring:message code="building.maxLvL"></spring:message>: 
<jstl:out value="${building.maxLvl }"></jstl:out><br>

<spring:message code="building.timeToConstruct"></spring:message>: 
<jstl:out value="${building.timeToConstruct }"></jstl:out><br>


<jstl:if test="${defense}">
	<spring:message code="building.defense.defense"></spring:message>: 
	<jstl:out value="${building.defense }"></jstl:out><br>
	
</jstl:if>

<jstl:if test="${recruiter}">
	<security:authorize access="hasRole('MANAGER')">
			<acme:action code="troop.create"  url="troop/contentManager/edit.do?recruiterId=${building.id}"/>
	</security:authorize>
		
	<jstl:if test="${troops != null && troops.size()>0 }">
	
		<display:table name="troops" id="row" pagesize="5" requestURI="building/display.do">
	
				<!-- TODO: tabla de troops -->
			<spring:message code="name" var="nameH" />
			<display:column title="${nameH}">
				<jstl:out value="${row.name}"/> 
			</display:column>
			
			<spring:message code="recruiterRequiredLvl" var="minLevelH" />
			<display:column title="${minLevelnameH}">
				<jstl:out value="${row.recruiterRequiredLvl}"/> 
			</display:column>
			
			<spring:message code="attack" var="attackH" />
			<display:column title="${attackH}">
				<jstl:out value="${row.attack}"/> 
			</display:column>
			
			<spring:message code="defense" var="defenseH" />
			<display:column title="${defenseH}">
				<jstl:out value="${row.defense}"/> 
			</display:column>
			
			<spring:message code="cost" var="costH" />
			<display:column title="${costH}">
				<jstl:out value="${row.cost}"/> 
			</display:column>
			
			<spring:message code="timeToRecruit" var="timeH" />
			<display:column title="${timeH}">
				<jstl:out value="${row.timeToRecruit}"/> 
			</display:column>
		
		</display:table>
		
	</jstl:if>
	
	
	<security:authorize access="hasRole('MANAGER')">
			<acme:action code="gummiShip.create"  url="gummiShip/contentManager/edit.do?recruiterId=${building.id}"/>
	</security:authorize>
	
	<jstl:if test="${gummiShips != null && gummiShips.size()>0 }">
	
		<display:table name="gummiShips" id="row" pagesize="5" requestURI="building/display.do">
	
				<!-- TODO: tabla de gummi ships -->
			<spring:message code="name" var="nameH" />
			<display:column title="${nameH}">
				<jstl:out value="${row.name}"/> 
			</display:column>
			
			<spring:message code="recruiterRequiredLvl" var="minLevelH" />
			<display:column title="${minLevelnameH}">
				<jstl:out value="${row.recruiterRequiredLvl}"/> 
			</display:column>
			
			<spring:message code="cost" var="costH" />
			<display:column title="${costH}">
				<jstl:out value="${row.cost}"/> 
			</display:column>
			
			<spring:message code="slots" var="slotsH" />
			<display:column title="${slotsH}">
				<jstl:out value="${row.slots}"/> 
			</display:column>
			
			<spring:message code="timeToRecruit" var="timeH" />
			<display:column title="${timeH}">
				<jstl:out value="${row.timeToRecruit}"/> 
			</display:column>
		
		</display:table>
	
	</jstl:if>
	
</jstl:if>

<jstl:if test="${warehouse}">
	
	<spring:message code="building.warehouse.materialsSlots"></spring:message>: 
	<jstl:out value="${building.materialsSlots }"></jstl:out><br>
	
	<spring:message code="building.warehouse.troopSlots"></spring:message>: 
	<jstl:out value="${building.troopSlots }"></jstl:out><br>
	
	<spring:message code="building.warehouse.gummiSlots"></spring:message>: 
	<jstl:out value="${building.gummiSlots }"></jstl:out><br>


</jstl:if>

<jstl:if test="${livelihood}">
	
	<spring:message code="building.livelihood.timeToRecollect"></spring:message>: 
	<jstl:out value="${building.timeToRecollect }"></jstl:out><br>
	
	<spring:message code="building.livelihood.materials"></spring:message>: 
	<jstl:out value="${building.materials }"></jstl:out><br>

</jstl:if>


<jstl:if test="${requirements != null}">
<div id="container">

<acme:pagination page="${page}" pageNum="${pageNum}" requestURI="${requestURI}"/>
	<spring:message code="building.requirements"></spring:message>
	<display:table name="requirements" id="row" pagesize="5" requestURI="${requestURI}">
	
		<spring:message code="building.building" var="buildingHeader"></spring:message>
		<display:column title="${buildingHeader}">
			<a href="requirement/contentManager/edit.do?requirementId=${row.id }"><jstl:out value="${row.requiredBuilding.name }"></jstl:out></a>
		</display:column>
		
		<spring:message code="building.lvl" var="lvlHeader"></spring:message>
		<display:column property="lvl" title="${lvlHeader}"></display:column>
		
		<!-- TODO: ¿SE CUMPLE EL REQUISITO? -->
	
	</display:table>
	</div>
</jstl:if>

