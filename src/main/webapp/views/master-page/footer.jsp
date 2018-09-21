<%--
 * footer.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<jsp:useBean id="date" class="java.util.Date" />

<div style="background-color: #1a1e3d; color:white; opacity: 0.5; text-align:center; margin-top:40px;" >
<b>Copyright &copy; <fmt:formatDate value="${date}" pattern="yyyy" /> Acme Battle Co., Inc.</b>
<br><p style="font-size: 14px">SQUARE ENIX logo are registered trademarks or trademarks of Square Enix Holdings Co., Ltd.
 <br>All other trademarks are properties of their respective owners.
 <br>
 <a href="legaltext/index.do"><spring:message code="master.page.terms" /></a> <b>&middot;</b> <a href="legaltext/index.do?type=cookies#cookies"><spring:message code="master.page.cookies" /></a></p>
</div>