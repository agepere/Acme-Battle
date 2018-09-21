<%--
 * action-1.jsp
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


<script>
	    function getBorderColor(lvl, maxLvl,id){
	    	
	    	start = 180;
	    	end = 60;
	    	var a = (lvl/maxLvl);
	        b = (end - start) * a,
	        c = b + start;

		    // Return a CSS HSL string
		    var hsl = 'hsl('+c+', 100%, 50%)';
	    	document.getElementById("card"+id).style.boxShadow ="inset 0 0 1em " + hsl + ", 0 0 1em " +hsl;
	    }
		function comparaFechaCreacion(year,month,day,hour,minutes,seconds,time,id){
			var hoy= new Date();
			var anho= parseInt(year) + 1900;
			var min= parseInt(minutes)+ parseInt(time);
			var fechaFin= new Date(anho,month,day,hour,min,seconds);
			
			if(hoy<=fechaFin){
				document.getElementById("card"+id).className += " disabledCard";
		   		document.getElementById("card"+id).style.position = "relative";
			}
			
			var x = setInterval(function() {
				  var now = new Date().getTime();
				  var distance = fechaFin - now;
				  var days = Math.floor(distance / (1000 * 60 * 60 * 24));
				  var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
				  var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
				  var seconds = Math.floor((distance % (1000 * 60)) / 1000);
				  
				  if(seconds < 10){
					  seconds = "0" + seconds;
				  }
				  
				  document.getElementById("timer"+id).innerHTML = "0" + minutes + ":" + seconds;
				  document.getElementById("card"+id).style.pointerEvents = "none";

				  if (distance < 0) {
				    clearInterval(x);
				    document.getElementById("timer"+id).innerHTML = '<a href="built/upgrade.do?builtId='+id+'"><spring:message code="built.finish.cosntruct"></spring:message></a>';
				    document.getElementById("card"+id).className += " disabledCard";
				    document.getElementById("card"+id).style.position = "relative";
				  }
				}, 1000);
		}
		
</script>

<spring:message code="master.page.actions" var="actionHeader"></spring:message>
<spring:message code="built.recruit" var="recruit"></spring:message>
<spring:message code="built.working" var="working"></spring:message>
<spring:message code="built.start.recruit" var="startRecruit"></spring:message>
<spring:message code="built.collect" var="collect"></spring:message>
<spring:message code="built.start.collect" var="startCollect"></spring:message>
<spring:message code="built.unbuild" var="unbuildHeader"></spring:message>
<spring:message code="building.name" var="nameHeader"></spring:message>
<spring:message code="master.page.display" var="displayHeader"></spring:message>

<acme:pagination page="${page}" pageNum="${pageNum}" requestURI="${requestURI}"/>

<div class="row row-width">
<jstl:forEach items="${builts}" var="row">
	<div class="card card-built" style="width: 19rem;color:black;border:0;border-radius:10px;">
	<div id="card${row.id}" style="border-radius:10px;">
		<img class="card-img-top" src="${row.building.photo}" alt="Card image cap">
		<div class="card-body">
			<div style="padding:10px;"class="row"><h5 class="card-title-body card-title">${row.building.name}</h5>
			</div>
			<p class="card-text">${row.building.description}</p>
				<p>
					<spring:message code="built.state" var="stateHeader"></spring:message>
					<jstl:if test="${row.lvl==0 }">
						<div id="${row.id}">
							<script>
								comparaFechaCreacion(
										'${row.creationDate.getYear()}',
										'${row.creationDate.getMonth()}',
										'${row.creationDate.getDate()}',
										'${row.creationDate.getHours()}',
										'${row.creationDate.getMinutes()}',
										'${row.creationDate.getSeconds()}',
										'${row.building.timeToConstruct}',
										'${row.id }');
							</script>
							<div id="counter${row.id}" class="centered"><i style="font-size:20px;vertical-align:middle;margin-right:5px;"class="material-icons">timer</i><span id="timer${row.id}"></span></div>
						</div>
					</jstl:if>
				</p>
				
				<div class="btn-group pagination-centered">
				<jstl:if test="${row.lvl ne row.building.maxLvl}">
				<div data-trigger="hover"
		data-toggle="popover" data-trigger="focus" data-placement="top" data-content="${row.building.getTotalMaterials(row.lvl) }">
							<acme:icon-button color="#83f52c" icon="arrow_upward" href="built/upgrade.do?builtId=${row.id }" title="built.upgrade"/>
				</div>
				</jstl:if>
					<jstl:if test="${row.lvl>0 }">
			<%-- <a class="btn-sm btn btn-warning" href="built/display.do?builtId=${row.id}"><jstl:out value="${displayHeader}"></jstl:out></a> --%>
			<acme:icon-button color="#f3f315" icon="visibility" href="built/display.do?builtId=${row.id}" title="master.page.display"/>
		</jstl:if>
		<jstl:choose>
					<jstl:when test="${row.building.getClass()=='class domain.Livelihood' && row.activationDate!=null && row.haTerminado(row.building.getTotalTime(row.lvl))}">
						<a class="btn-sm btn btn-primary" href="built/collect.do?builtId=${row.id}"><jstl:out value="${collect}"></jstl:out></a>
					</jstl:when>
					<jstl:when test="${row.building.getClass()=='class domain.Livelihood' && row.activationDate!=null && !row.haTerminado(row.building.getTotalTime(row.lvl)) }">
						<button title="${working}" type="button" class="btn-sm btn btn-primary" disabled><i style="font-size:20px;vertical-align:middle;" class="fa fa-refresh fa-spin"></i></button>
					</jstl:when>																										
					<jstl:when test="${row.building.getClass()=='class domain.Livelihood' && row.activationDate==null }">
						<%-- <a class="collect btn-sm btn btn-primary" href="built/startCollect.do?builtId=${row.id}"><jstl:out value="${startCollect}"></jstl:out></a> --%>
						<acme:icon-button color="#1bbee3" icon="toys" href="built/startCollect.do?builtId=${row.id}" title="built.start.collect"/>
					</jstl:when>
					<jstl:when test="${row.building.getClass()=='class domain.Recruiter' && row.activationDate!=null && row.troop!=null && row.haTerminado(row.troop.timeToRecruit)}">
						<%-- <a class="btn-sm btn btn-primary" href="built/recruit.do?builtId=${row.id }"><jstl:out value="${recruit}"></jstl:out></a> --%>
						<acme:icon-button color="#00abb3" icon="domain" href="built/recruit.do?builtId=${row.id }" title="built.recruit"/>
					</jstl:when>
					<jstl:when test="${row.building.getClass()=='class domain.Recruiter' && row.activationDate!=null && row.troop!=null && !row.haTerminado(row.troop.timeToRecruit) }">
						<%-- <button type="button" class="btn-sm btn btn-primary" disabled><jstl:out value="${working }"></jstl:out></button> --%>
						<button type="button" class="btn-sm btn btn-primary" disabled><i style="font-size:20px;vertical-align:middle;" class="fa fa-gear fa-spin"></i></button>
					</jstl:when>
					<jstl:when test="${row.building.getClass()=='class domain.Recruiter' && row.activationDate!=null && row.gummiShip!=null && row.haTerminado(row.gummiShip.timeToRecruit)}">
						<a class="btn-sm btn btn-primary" href="built/recruit.do?builtId=${row.id }"><jstl:out value="${recruit}"></jstl:out></a>
					</jstl:when>
					<jstl:when test="${row.building.getClass()=='class domain.Recruiter' && row.activationDate!=null && row.gummiShip!=null && !row.haTerminado(row.gummiShip.timeToRecruit) }">
						<button type="button" class="btn-sm btn btn-primary" disabled><i style="font-size:20px;vertical-align:middle;" class="fa fa-gear fa-spin"></i>&nbsp;&nbsp;<jstl:out value="${working }"></jstl:out></button>
					</jstl:when>
					<jstl:when test="${row.building.getClass()=='class domain.Recruiter' && row.activationDate==null }">
						<%-- <a class="collect btn-sm btn btn-primary" href="built/startRecruit.do?builtId=${row.id}"><jstl:out value="${startRecruit}"></jstl:out></a> --%>
						<acme:icon-button color="blue" icon="settings" href="built/startRecruit.do?builtId=${row.id}" title="built.start.recruit"/>
					</jstl:when>
				</jstl:choose>
				<%-- <a class="btn-sm btn btn-danger" href="built/delete.do?builtId=${row.id}"><i style="font-size:20px;vertical-align:middle;"class="material-icons">clear</i><jstl:out value="${unbuildHeader }"></jstl:out></a> --%>
				<acme:icon-button color="#ff0101" icon="clear" href="built/delete.do?builtId=${row.id}" title="built.unbuild"/>
				<acme:icon-button color="blue" icon="star"  displayedMessage="Lvl ${row.lvl}"/>
			</div>
		</div>
	</div>
	<script>
	getBorderColor('${row.lvl}','${row.building.maxLvl}','${row.id}')
</script>
	</div>
</jstl:forEach>
</div>
<br/>
<acme:action url="built/create.do" code="master.page.create"/>
