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

<display:table name="items" id="row" 
    requestURI="item/manager/createdItems.do" 
    pagesize="5" class="displaytag">
  
	<spring:message code="item.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" />

	<spring:message code="item.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" />

	<spring:message code="item.type" var="typeHeader" />
	<display:column property="type" title="${typeHeader}" />
	
	<spring:message code="item.duration" var="durationHeader" />
	<display:column property="duration" title="${durationHeader}" />
	
	<spring:message code="item.expiration" var="expirationHeader" />
	<display:column property="expiration" title="${expirationHeader}" />
	
	<spring:message code="item.extra" var="extraHeader" />
	<display:column property="extra" title="${extraHeader}" />
	
	<spring:message code="item.munnyCost" var="munnyCostHeader" />
	<display:column property="munnyCost" title="${munnyCostHeader}" />
	
	<display:column>
	<a href="item/manager/edit.do?itemId=${row.id}"><spring:message code="master.page.edit"/></a>
	</display:column>
	
</display:table>