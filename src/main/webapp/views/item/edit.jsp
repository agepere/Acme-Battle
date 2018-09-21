
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

<form:form action="item/manager/edit.do" modelAttribute="item">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<acme:textbox code="item.name" path="name"/>
	<acme:textarea code="item.description" path="description"/>
	<acme:textbox code="item.duration" path="duration"/>
	<acme:textbox code="item.expiration" path="expiration"/>
	<spring:message code="item.extra" />
	<form:select path="extra">
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
	<acme:textbox code="item.munnyCost" path="munnyCost"/>
	
	<form:select path="type">
                <form:option value="ATTACKBOOST"><spring:message code="item.attackBoost" /></form:option>
                <form:option value="DEFENSEBOOST"><spring:message code="item.defenseBoost" /></form:option>
                <form:option value="RESOURCEBOOST"><spring:message code="item.resourceBoost" /></form:option>
                <form:option value="SHIELD"><spring:message code="item.shield" /></form:option>
    </form:select>
	
	<form:checkbox path="onSell" value="true" />
	<spring:message code="item.onSell" />
	
	
	<acme:submit code="master.page.save"  name="save" />
	<acme:cancel code="master.page.return" url="/item/manager/createdItems.do" />
	
	<jstl:if test="${contiene == false}">
		<input type="submit" name="delete"
			value="<spring:message code="master.page.delete" />" />
	</jstl:if>

</form:form>
