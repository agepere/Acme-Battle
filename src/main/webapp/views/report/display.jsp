<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<div class="card" style="color: #212529;">
  <div class="card-header font-weight-bold text-primary" style="background-color:#D8ECF6;">
    <jstl:out value="${report.title}"/>
  </div>
  <div class="card-body">
  <div class="row">
  <div class="col-9">
  	<ul class="list-group list-group-flush">
  	  <li class="list-group-item"><div class="row"><div class="col-6"><b><spring:message code="report.type"/>:</b></div>	<div class="col-6">
  	  <jstl:if test="${report.isBug eq true}">
  	  <jstl:out value="Bug"/>
  	  </jstl:if>
  	   <jstl:if test="${report.isBug eq false}">
  	   <spring:message code="report.asistence" />
  	  </jstl:if>
  	  </div></div></li>
  	  <li class="list-group-item"><div class="row"><div class="col-6"><b><spring:message code="content"/>:</b></div>	<div class="col-6"><jstl:out value="${report.content}"/></div></div></li>
  	  <li class="list-group-item"><div class="row"><div class="col-6"><b><spring:message code="date"/>:</b></div>		<div class="col-6"><jstl:out value="${report.date}"/></div></div></li>
  	  <li class="list-group-item"><div class="row"><div class="col-6"><b><spring:message code="report.creator"/>:</b></div>		<div class="col-6"><jstl:out value="${report.keybladeWielder.userAccount.username}"/></div></div></li>
      <li class="list-group-item"><div class="row"><div class="col-6"><b><spring:message code="status"/>:</b></div>
  	 <div class="col-6"> <jstl:if test="${report.status eq 'ONHOLD'}">
			<div class="row"><div class="col-6"><spring:message code="report.onhold" /></div></div></li>
		</jstl:if> 
		<jstl:if test="${report.status eq 'RESOLVED'}">
			<div class="row"><div class="col-6"><spring:message code="report.resolved" />
		</jstl:if> 
		<jstl:if test="${report.status eq 'IRRESOLVABLE'}">
			<div class="row"><div class="col-6"><spring:message code="report.irresolvable" />
		</jstl:if> 
		<jstl:if test="${report.status eq 'WORKING'}">
			<div class="row"><div class="col-6"><spring:message code="report.working" />
		</jstl:if> <jstl:if test="${report.status eq 'SUSPICIOUS'}">
			<div class="row"><div class="col-6"><spring:message code="report.suspicious" />
		</jstl:if>
		</div></div></li>
	</ul>
	<div class="row">
	<jstl:forEach items="${report.photos}" var="photo">
		<img src="${photo}" />
		<br />
	</jstl:forEach>
	</div>
  
  </div>
  </div>
  </div>
  <div class="card-footer text-muted">
  <acme:goback />
  <jstl:if test="${not empty report.reportUpdates}">
		<acme:action code="report.seeUpdates" color="pink" url="reportUpdate/list.do?reportId=${report.id}"/>
	</jstl:if>
  <security:authorize access="hasRole('ADMIN')">
	<jstl:if test="${report.status ne 'RESOLVED' or suspicious}">
	<acme:action color="success" url="reportUpdate/create.do?reportId=${report.id}" code="reportUpdate.create"/>
    </jstl:if>
  </security:authorize>
   <security:authorize access="hasRole('GM')">
	<jstl:if test="${report.status ne 'RESOLVED' and report.status ne 'IRRESOLVABLE'}">
	<acme:action color="success" url="reportUpdate/create.do?reportId=${report.id}" code="reportUpdate.create"/>
    </jstl:if>
  </security:authorize>
  <jstl:if test="${suspicious}">
	&nbsp;&nbsp;<spring:message code="reportUpdate.marked.suspicious" />
	<br />
</jstl:if>
	
  </div>
</div>
