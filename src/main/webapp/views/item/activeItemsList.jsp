<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Listing grid -->

<display:table name="purchases" id="row" 
    requestURI="item/player/ownedItemsList.do" 
    pagesize="5" class="displaytag">
    
	<spring:message code="item.name" var="nameHeader" />
	<display:column property="item.name" title="${nameHeader}" />

	<spring:message code="item.description" var="descriptionHeader" />
	<display:column property="item.description" title="${descriptionHeader}" />

	<spring:message code="item.type" var="typeHeader" />
	<display:column property="item.type" title="${typeHeader}" />
	
	<spring:message code="item.duration" var="durationHeader" />
	<display:column property="item.duration" title="${durationHeader}" />
	
	<spring:message code="item.expiration" var="expirationHeader" />
	<display:column property="item.expiration" title="${expirationHeader}" />
	
	<spring:message code="item.extra" var="extraHeader" />
	<display:column property="item.extra" title="${extraHeader}" />
	
	<spring:message code="item.munnyCost" var="munnyCostHeader" />
	<display:column property="item.munnyCost" title="${munnyCostHeader}" />
	
	<spring:message code="item.activationDate" var="activationDateHeader" />
	<display:column property="activationDate" title="${activationDateHeader}" />
	

</display:table>