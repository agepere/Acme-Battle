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


<jstl:if test="${wantRecruit!='troop' && wantRecruit!='ship' }">

<form:form action="built/startRecruit.do" modelAttribute="recruitedForm">
	<form:hidden path="built"/>
	
	<spring:message code="built.want.recruit"></spring:message>	
	<select id="wantRecruit" name="wantRecruit">
		  <option value="troop"><spring:message code="built.recruit.troop"></spring:message></option>
		  <option value="ship"><spring:message code="built.recruit.ship"></spring:message></option>
	</select>
	
	<acme:submit name="next" code="master.page.next"/>
	<acme:cancel url="built/list.do" code="master.page.cancel"/>

</form:form>

</jstl:if>

<jstl:if test="${wantRecruit=='troop' || wantRecruit=='ship' }">

<form:form action="built/startRecruit.do" modelAttribute="recruitedForm">

	<form:hidden path="built"/>
	<input type="hidden" name="wantRecruit" value="${wantRecruit}"/>
	
	<jstl:if test="${wantRecruit=='troop' }">
	<jstl:choose>
		<jstl:when test="${troops!=null && troops.size()>0 }">
		<form:select path="troop">
			<form:options itemLabel="name" itemValue="id"  items="${troops}"  />
		</form:select>
		<form:errors path="troop" cssClass="error"></form:errors>
	<br>
	<acme:submit name="save" code="master.page.save"/>
	</jstl:when>
		<jstl:otherwise>
			<spring:message code="built.recruit.noTroop"></spring:message>
		</jstl:otherwise>
	
	</jstl:choose>
	</jstl:if>
	
	<jstl:if test="${wantRecruit=='ship' }">
	<jstl:choose>
		<jstl:when test="${ships!=null && ships.size()>0 }">
			<form:select path="gummiship">
				<form:options itemLabel="name" itemValue="id"  items="${ships}"  />
			</form:select>
			<form:errors path="gummiship" cssClass="error"></form:errors>
		<br>
		<acme:submit name="save" code="master.page.save"/>
		</jstl:when>
		<jstl:otherwise>
			<spring:message code="built.recruit.noShip"></spring:message>
		</jstl:otherwise>
	
	</jstl:choose>
	</jstl:if>
	
	
	
	<acme:cancel url="built/list.do" code="master.page.cancel"/>

</form:form>

</jstl:if>