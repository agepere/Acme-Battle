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
<jstl:out value="${built.building.name }"></jstl:out><br>

<spring:message code="building.descritpion"></spring:message>: 
<jstl:out value="${built.building.description }"></jstl:out><br>

<spring:message code="built.cost"></spring:message>: 
<jstl:out value="${built.building.getTotalMaterials(built.lvl)}"></jstl:out><br>

<spring:message code="built.lvl"></spring:message>: 
<jstl:out value="${built.lvl }"></jstl:out>/<jstl:out value="${built.building.maxLvl }"></jstl:out><br>

<jstl:if test="${defense}">
	<spring:message code="building.defense.defense"></spring:message>: 
	<jstl:out value="${built.building.getTotalDefense(built.lvl) }"></jstl:out><br>
	
</jstl:if>

<jstl:if test="${recruiter}">
	<jstl:if test="${troops != null && troops.size()>0 }">
	
		<display:table name="troops" id="row" pagesize="5" requestURI="built/display.do">
	
				<!-- TODO: tabla de troops disponibles para crear-->
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
	
	<jstl:if test="${gummiShips != null && gummiShips.size()>0 }">
	
		<display:table name="gummiShips" id="row" pagesize="5" requestURI="built/display.do">
	
				<!-- TODO: tabla de gummi ships  disponibles para crear-->
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
	<jstl:out value="${built.building.getTotalSlotsMaterials(built.lvl)}"></jstl:out><br>
	
	<spring:message code="building.warehouse.troopSlots"></spring:message>: 
	<jstl:out value="${built.building.getTotalTroopSlots(built.lvl)}"></jstl:out><br>
	
	<spring:message code="building.warehouse.gummiSlots"></spring:message>: 
	<jstl:out value="${built.building.getTotalGummiSlots(built.lvl) }"></jstl:out><br>
	
	
	<jstl:if test="${storagedTroops!=null && storagedTroops.size()>0 }">
	<spring:message code="built.storaged.troop"></spring:message>:
	
		<display:table name="storagedTroops" id="row" pagesize="5" requestURI="built/display.do">
	
		<spring:message code="building.name" var="nameHeader"></spring:message>
		<display:column property="name" title="${nameHeader}"></display:column>
		
		<spring:message code="troop.defense" var="defenseHeader"></spring:message>
		<display:column property="defense" title="${defenseHeader}"></display:column>
		
		<spring:message code="troop.attack" var="attackHeader"></spring:message>
		<display:column property="attack" title="${attackHeader}"></display:column>
		
		</display:table>
	</jstl:if>
	
	<jstl:if test="${storagedShips!=null && storagedShips.size()>0 }">
	<spring:message code="built.storaged.ship"></spring:message>:
	
		<display:table name="storagedShips" id="row" pagesize="5" requestURI="built/display.do">
	
		<spring:message code="building.name" var="nameHeader"></spring:message>
		<display:column property="name" title="${nameHeader}"></display:column>
		
		<spring:message code="ship.slots" var="slotsHeader"></spring:message>
		<display:column property="slots" title="${slotsHeader}"></display:column>
		
		</display:table>
	</jstl:if>

</jstl:if>


<jstl:if test="${livelihood}">
	
	<spring:message code="building.livelihood.timeToRecollect"></spring:message>: 
	<jstl:out value="${built.building.getTotalTime(built.lvl) }"></jstl:out><br>
	
	<spring:message code="building.livelihood.materials"></spring:message>: 
	<jstl:out value="${built.building.getTotalCollectMaterials(built.lvl) }"></jstl:out><br>

</jstl:if>