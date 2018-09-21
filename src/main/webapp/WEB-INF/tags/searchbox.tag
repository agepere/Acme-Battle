<%--
 * cancel.tag
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>
 
<%@ tag language="java" body-content="empty" %>
 
 <%-- Taglibs --%>

<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<%-- Attributes --%> 
<%@ attribute name="action" required="true" %>

<%-- Definition --%>
<spring:message code="master.page.search" var="search"/>
<spring:message code="username" var="username"/>
<form class="form-inline my-2 my-sm-0" action="${action}" method="GET">
    <input class="form-control form-control-sm input-group-sm mr-sm-2" style="margin-bottom: 0px;" type="search" name="username" placeholder="${username}" aria-label="Search">
    <button class="btn btn-sm btn-outline-primary my-2 my-sm-0" type="submit">${search}</button>
</form>

