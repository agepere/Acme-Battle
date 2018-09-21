
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="configuration/administrator/save.do" modelAttribute="configuration">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<acme:textbox code="dailyMunny" path="dailyMaterials.munny"/>
	<acme:textbox code="dailyMytrhil" path="dailyMaterials.mytrhil"/>
	<acme:textbox code="dailyGummiCoal" path="dailyMaterials.gummiCoal"/>
	
	<acme:textbox code="baseMunny" path="baseMaterials.munny"/>
	<acme:textbox code="baseMytrhil" path="baseMaterials.mytrhil"/>
	<acme:textbox code="baseGummiCoal" path="baseMaterials.gummiCoal"/>
	
	<acme:textbox code="orgMessages" path="orgMessages"/>
	
	<spring:message code="percentageWinAttacker" />
	<form:select path="percentageWinAttacker">
                <form:option value="0.1">10%</form:option>
                <form:option value="0.2">20%</form:option>
                <form:option value="0.3">30%</form:option>
                <form:option value="0.4">40%</form:option>
                <form:option value="0.5">50%</form:option>
                <form:option value="0.6">60%</form:option>
                <form:option value="0.7">70%</form:option>
                <form:option value="0.8">80%</form:option>
                <form:option value="0.9">90%</form:option>
    </form:select>
    
    <spring:message code="percentageWinDeffender" />
	<form:select path="percentageWinDefender">
                <form:option value="0.1">10%</form:option>
                <form:option value="0.2">20%</form:option>
                <form:option value="0.3">30%</form:option>
                <form:option value="0.4">40%</form:option>
                <form:option value="0.5">50%</form:option>
                <form:option value="0.6">60%</form:option>
                <form:option value="0.7">70%</form:option>
                <form:option value="0.8">80%</form:option>
                <form:option value="0.9">90%</form:option>
    </form:select>
	
	<acme:textbox code="WorldSlots" path="worldSlots"/>
	
	
	<acme:submit code="master.page.save"  name="save" />
	<acme:cancel code="master.page.return" url="/" />
	

</form:form>