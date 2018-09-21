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

<jstl:if test="${buildingType!='defense' && buildingType!='recruiter'  && buildingType!='livelihood' && buildingType!='warehouse' }">

<form:form action="building/contentManager/edit.do">
	<select id="buildingType" name="buildingType">
		  <option value="defense"><spring:message code="building.defense"></spring:message></option>
		  <option value="recruiter"><spring:message code="building.recruiter"></spring:message></option>
		  <option value="livelihood"><spring:message code="building.livelihood"></spring:message></option>
		  <option value="warehouse"><spring:message code="building.warehouse"></spring:message></option>
	</select>
	
	<acme:submit name="save" code="master.page.next"/>
	<acme:cancel url="building/contentManager/myList.do" code="master.page.cancel"/>

</form:form>


</jstl:if>



<jstl:if test="${buildingType=='defense' || buildingType=='recruiter' || buildingType=='livelihood' || buildingType=='warehouse' }">


<form:form action="building/contentManager/edit.do" modelAttribute="${buildingType}">
	
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<input type="hidden" value="${buildingType}" name="buildingType" id="buildingType"/>
	
	<fieldset>
		<legend title="<spring:message code='building.miscelaneous'/>"><spring:message code='building.miscelaneous'/></legend>
		<acme:textbox code="building.name" path="name"/>
		<acme:textbox code="building.descritpion" path="description"/>
		
	</fieldset>
	<br>
	<fieldset>
		<legend title="<spring:message code='building.cost'/>"><spring:message code='building.cost'/></legend>
		<acme:textbox code="master.page.munny" path="cost.munny"/>
		<acme:textbox code="master.page.mytrhil" path="cost.mytrhil"/>
		<acme:textbox code="master.page.gummyCoal" path="cost.gummiCoal"/>
		
	</fieldset>
	<fieldset>
		<legend title="<spring:message code='building.onGameOptions'/>"><spring:message code='building.onGameOptions'/></legend>
		<acme:textbox code="building.maxLvL" path="maxLvl"/>
		<acme:textbox code="building.extraCostPerLvl" path="extraCostPerLvl"/>
		<acme:textbox code="building.timeToConstruct" path="timeToConstruct"/>
	</fieldset>
	
	<jstl:if test="${buildingType=='defense'}">
	
	<fieldset>
		<legend title="<spring:message code='building.defense'/>"><spring:message code='building.defense'/></legend>
		<acme:textbox code="building.defense.defense" path="defense"/>
		<acme:textbox code="building.defense.extraDefensePerLvl" path="extraDefensePerLvl"/>
		
	</fieldset>
	
	</jstl:if>
	
	<jstl:if test="${buildingType=='recruiter'}">
	
	<fieldset>
		<legend title="<spring:message code='building.recruiter'/>"><spring:message code='building.recruiter'/></legend>
		<spring:message code="building.recruiter.info"></spring:message>
		
	</fieldset>
	
	</jstl:if>
	
	<jstl:if test="${buildingType=='warehouse'}">
	
	<fieldset>
		<legend title="<spring:message code='building.warehouse'/>"><spring:message code='building.warehouse'/></legend>
		<acme:textbox code="building.warehouse.troopSlots" path="troopSlots"/>
		<acme:textbox code="building.warehouse.gummiSlots" path="gummiSlots"/>
		<fieldset>
			
			<legend title='<spring:message code="building.warehouse.materialsSlots"></spring:message>'><spring:message code="building.warehouse.materialsSlots"></spring:message></legend>
			<acme:textbox code="master.page.munny" path="materialsSlots.munny"/>
			<acme:textbox code="master.page.mytrhil" path="materialsSlots.mytrhil"/>
			<acme:textbox code="master.page.gummyCoal" path="materialsSlots.gummiCoal"/>
			
		</fieldset>
		
		<acme:textbox code="building.warehouse.extraSlotsPerLvl" path="extraSlotsPerLvl"/>
		
	</fieldset>
	
	</jstl:if>
	
	
	<jstl:if test="${buildingType=='livelihood'}">
	
	<fieldset>
		<legend title="<spring:message code='building.livelihood'/>"><spring:message code='building.livelihood'/></legend>
		<fieldset>
			
			<legend title='<spring:message code="building.livelihood.materials"></spring:message>'><spring:message code="building.livelihood.materials"></spring:message></legend>
			<acme:textbox code="master.page.munny" path="materials.munny"/>
			<acme:textbox code="master.page.mytrhil" path="materials.mytrhil"/>
			<acme:textbox code="master.page.gummyCoal" path="materials.gummiCoal"/>
			
		</fieldset>
		
		<acme:textbox code="building.livelihood.timeToRecollect" path="timeToRecollect"/>
		<acme:textbox code="building.livelihood.lessTimePerLvl" path="lessTimePerLvl"/>
		<acme:textbox code="building.livelihood.extraMaterialsPerLvl" path="extraMaterialsPerLvl"/>
			
	</fieldset>
	
	</jstl:if>
	
	<br>
	<spring:message code="building.finalSave"></spring:message><input type="checkbox" id="saveFinal" name="saveFinal" /> <br>
	<br>

	<acme:submit name="save${buildingType}" code="master.page.save"/>
	<acme:cancel url="building/contentManager/edit.do" code="master.page.cancel"/>
</form:form>
</jstl:if>