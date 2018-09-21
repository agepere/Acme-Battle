<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- KEYBLADEWIELDER -->
<b><spring:message code="ratioOfUserPerFaction"/></b></br>
${ratioOfUserPerFaction}</br>
<b><spring:message code="getTopWinsPlayers"/></b></br>
${getTopWinsPlayers}</br>
<b><spring:message code="getTopWinRatioPlayers"/></b></br>
${getTopWinRatioPlayers}</br>
<b><spring:message code="getTopMunnyPlayers"/></b></br>
${getTopMunnyPlayers}</br>
<b><spring:message code="getTopMytrhilPlayers"/></b></br>
${getTopMytrhilPlayers}</br>
<b><spring:message code="avgOfWinRatio"/></b></br>
${avgOfWinRatio}</br>

<!-- REPORT -->
<b><spring:message code="getAvgReportPerUser"/></b></br>
${getAvgReportPerUser}</br>
<b><spring:message code="getStddevReportPerUser"/></b></br>
${getStddevReportPerUser}</br>
<b><spring:message code="getRatioOfResolvedReport"/></b></br>
${getRatioOfResolvedReport}</br>
<b><spring:message code="getRatioOfIrresolvableReport"/></b></br>
${getRatioOfIrresolvableReport}</br>
<b><spring:message code="getRatioOfSuspiciousReport"/></b></br>
${getRatioOfSuspiciousReport}</br>

<!--REPORTUPDATE -->

<b><spring:message code="avgUpdatesFromGm"/></b></br>
${avgUpdatesFromGm}</br>
<b><spring:message code="stddevUpdatesFromGm"/></b></br>
${stddevUpdatesFromGm}</br>
<b><spring:message code="maxUpdatesFromGm"/></b></br>
${maxUpdatesFromGm}</br>
<b><spring:message code="minUpdatesFromGm"/></b></br>
${minUpdatesFromGm}</br>
<b><spring:message code="avgUpdatesFromReport"/></b></br>
${avgUpdatesFromReport}</br>
<b><spring:message code="stddevUpdatesFromReport"/></b></br>
${stddevUpdatesFromReport}</br>
<b><spring:message code="maxUpdatesFromReport"/></b></br>
${maxUpdatesFromReport}</br>
<b><spring:message code="minUpdatesFromReport"/></b></br>
${minUpdatesFromReport}</br>
<b><spring:message code="avgSuspiciousUpdatesFromGm"/></b></br>
${avgSuspiciousUpdatesFromGm}</br>

<!-- ITEM -->
<b><spring:message code="maxCreatedItem"/></b></br>
${maxCreatedItem}</br>
<b><spring:message code="minCreatedItem"/></b></br>
${minCreatedItem}</br>
<b><spring:message code="avgCreatedItem"/></b></br>
${avgCreatedItem}</br>
<b><spring:message code="stddevCreatedItem"/></b></br>
${stddevCreatedItem}</br>

