<%--
 * textbox.tag
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@ tag language="java" body-content="empty"%>

<%-- Taglibs --%>

<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<%-- Attributes --%>

<%@ attribute name="requestURI" required="true"%>
<%@ attribute name="pageNum" required="true"%>
<%@ attribute name="page" required="true"%>


<%-- Definition --%>

<jstl:set var="user" value="player"/>
<security:authorize access="hasRole('GM') or hasRole('ADMIN')">
<jstl:set var="user" value="all"/>
</security:authorize>

<jstl:set var="previous" value="${page-2}"/>
<jstl:if test="${page-2 < 0}">
<jstl:set var="previous" value="0"/>
</jstl:if>

<jstl:set var="next" value="${page+1}"/>
<jstl:if test="${page+1 > pageNum-1}">
<jstl:set var="next" value="${pageNum-1}"/>
</jstl:if>

<jstl:if test="${pageNum!=0}">

<br/>

	<!-- Pagination -->
	<ul class="pagination">
		<li class="page-item">
	      <a  href="" class="page-link" onclick="javascript: reloadTable(event, '${user}',${previous})" aria-label="Previous">
	        <span aria-hidden="true">&laquo;</span>
	        <span class="sr-only">Previous</span>
	      </a>
	    </li>
		<jstl:forEach begin="1" end="${pageNum}" var="index">
		
		<jstl:set var="style" value="page-item"/>
		<jstl:if test="${index-1 == page}">
			<jstl:set var="style" value="active page-item"/>
		</jstl:if>

			<jstl:set var="pageEffect" value="waves-effect" />
			<jstl:if test="${index-1 == page}">
				<jstl:set var="pageEffect" value="active" />
			</jstl:if>

			<li class="${style}"><a href="" class="page-link" onclick="javascript: reloadTable(event, '${user}',${index-1})">
					<jstl:out value="${index}" />
			</a></li>
		</jstl:forEach>
		<li class="page-item">
	      <a href="" class="page-link" onclick="javascript: reloadTable(event, '${user}',${next})" aria-label="Previous">
	        <span aria-hidden="true">&raquo;</span>
	        <span class="sr-only">Next</span>
	      </a>
	    </li>
		<br />
	</ul>
</jstl:if>