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
 <%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
 <%@taglib prefix="display" uri="http://displaytag.sf.net"%><%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
 
 <jstl:if test="${ user.shield != null }">
 <script> 
 function comparaFechaCreacion(year,month,day,hour,minutes,seconds,time,id){
		var hoy= new Date();
		var anho= parseInt(year) + 1900;
		var min= parseInt(minutes)+ parseInt(time);
		var fechaFin= new Date(anho,month,day,hour,min,seconds);
		
		
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
			  
			  document.getElementById("timer"+id).innerHTML =  minutes + ":" + seconds;

			 

			  if (distance < 0) {
			    clearInterval(x);
			    document.getElementById("counter"+id).style.display =  'none';
			    document.getElementById("shield").style.display =  'none';
			  }
			}, 1000);
		
	}
 </script>
 </jstl:if>
 
<jstl:if test="${user.getClass().name  eq 'domain.KeybladeWielder'}">
   <h3 class="title-header" style="text-shadow: 0px 0px 15px rgba(0, 0, 0, 1);">
      <jstl:out value="${user.worldName}"/>
      <jstl:choose>
				<jstl:when test="${fn:toLowerCase(user.faction.name) == 'light' or fn:toLowerCase(user.faction.name) == 'darkness'}">
				<img data-toggle="popover" data-trigger="hover" data-trigger="focus" data-placement="right" title="${user.faction.name}" data-content="${user.faction.powerUpDescription}" src="./images/factions/${fn:toLowerCase(user.faction.name)}.png" width="50px" height="50px"/>
				</jstl:when>
				<jstl:otherwise>
				<img data-toggle="popover" data-trigger="hover" data-trigger="focus" data-placement="right" title="${user.faction.name}" data-content="${user.faction.powerUpDescription}" src="./images/factions/other.png" width="50px" height="50px"/>
				</jstl:otherwise>
	</jstl:choose>	
   </h3>
   <p  class="badge badge-info" style="padding: 4px 10px 4px 10px">
      <spring:message code="worldCoordinates"/>: 
      <span class="font-weight-light">
         (<jstl:out value="${user.worldCoordinates.x }"/>
         ,<jstl:out value="${user.worldCoordinates.y}"/>
         ,<jstl:out value="${user.worldCoordinates.z }"/>)
      </span>
   </p>
   <p  class="badge badge-info" style="padding: 4px 10px 4px 10px">
      <spring:message code="nickname"/>: 
      <span class="font-weight-light">
         <jstl:out value="${user.nickname}"/>
      </span>
   </p>
   <div class="row">
      <div class="col">
      <br/><br/>
         <div class="world text-center">
         <jstl:if test="${ user.shield != null }">

            <img src="./images/worlds/shield.png" alt="shield" id="shield" style="width:100%; z-index:1; position:absolute; top:0; left:0;"/>
            </jstl:if>
            <img src="./images/worlds/(${user.getWorldImage()}).png" alt="world" style="width:80%; height:auto; z-index:0"/>

         </div>
         <jstl:choose>
            <jstl:when test="${user.worldCoordinates.z%2 == 0}">
               <div class="galaxy" style="background-image:url(./images/dark-galaxy.png)"></div>
            </jstl:when>
            <jstl:otherwise>
               <div class="galaxy" style="background-image:url(./images/light-galaxy.png)"></div>
            </jstl:otherwise>
         </jstl:choose>
      </div>
      <div class="col">
         <br />
         <jstl:if test="${user.userAccount.getOwner()}">
            <acme:action code="master.page.myBuilding" color="dark"  url="/built/list.do"/>
            <br />
            <br />
         </jstl:if>
         <div class="btn bg-dark text-left">
         <spring:message code="materials"/>:
         <br/>
                  <div class="btn btn-material" data-trigger="hover" data-toggle="popover" data-trigger="focus" data-placement="bottom" data-content="<spring:message code='munny'/>">
                     <img title="Munny" src="./images/materials/munny.png" width="50px"
                        height="50px" /> <span class="badge badge-warning">${user.materials.munny}</span>
                  </div>

                  <div class="btn  btn-material" data-trigger="hover" data-toggle="popover" data-trigger="focus" data-placement="bottom" data-content="<spring:message code='mythril'/>">
                     <img title="Mythril" src="./images/materials/mythril.png"
                        width="50px" height="50px" /> <span class="badge badge-info">${user.materials.mytrhil}</span>
                  </div>

                  <div class="btn btn-material" data-trigger="hover" data-toggle="popover" data-trigger="focus" data-placement="bottom" data-content="<spring:message code='gummiCoal'/>">
                     <img title="Gummi Coal" src="./images/materials/gummiCoal.png"
                        width="50px" height="50px" /> <span class="badge badge-dark">${user.materials.gummiCoal}</span>
                  </div>
         </div>
         <br />
               <jstl:if test="${ user.shield != null }">
               	<div id="${user.id}">
							<script>
								comparaFechaCreacion(
										'${user.shield.date.getYear()}',
										'${user.shield.date.getMonth()}',
										'${user.shield.date.getDate()}',
										'${user.shield.date.getHours()}',
										'${user.shield.date.getMinutes()}',
										'${user.shield.date.getSeconds()}',
										'${user.shield.duration}',
										'${user.id }');
							</script>
							<div id="counter${user.id}" class="centered"><i style="font-size:20px;vertical-align:middle;margin-right:5px;"class="material-icons">security</i><span id="timer${user.id}"></span></div>
						</div>
         </jstl:if>
      </div>
   </div>
</jstl:if>
