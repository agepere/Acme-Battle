<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table pagesize="5" class="displaytag" 
	name="battles" requestURI="battle/listBattles.do" id="row">


	<spring:message code="battle.attacker" var="attackerH" />
	<display:column title="${attackerH}" class="${row.isWon}">
			<jstl:out value="${row.attacker.userAccount.username}"></jstl:out>
	</display:column>
	
		<spring:message code="battle.deffender" var="deffenderH" />
	<display:column title="${deffenderH}" class="${row.isWon}">
			<jstl:out value="${row.deffender.userAccount.username}"></jstl:out>
	</display:column>
	

	<spring:message code="battle.isWon" var="isWonHeader" />
	<display:column property="isWon" title="${isWonHeader}" class="${row.isWon}"/>


	<spring:message code="master.page.actions" var="actionsH" />
	<display:column title="${actionsH}" class="${row.isWon}">
	<a href="battle/display.do?battleId=${row.id}"><spring:message code="master.page.view"/></a>
	</display:column>
	

</display:table>