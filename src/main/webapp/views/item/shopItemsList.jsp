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

<!-- Listing grid -->

<b>Munny:${playerMunny}</b>
<br/>

<acme:pagination page="${page}" pageNum="${pageNum}" requestURI="${requestURI}"/>

<display:table name="items" id="row" 
    requestURI="item/player/shopItemsList.do" 
    pagesize="5" class="displaytag">
    
    <jstl:if test="${row.onSell == true}">
	
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
	<jstl:if test="${row.munnyCost <= playerMunny}">
	<a href="item/player/buy.do?itemId=${row.id}"><spring:message code="item.buy"/></a>
	</jstl:if>
	</display:column>
	
	</jstl:if>

</display:table>


