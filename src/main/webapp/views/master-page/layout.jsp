<%--
 * layout.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html>
<html>
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

	<base
	href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/" />

	

 <link rel="apple-touch-icon" sizes="57x57" href="./images/favicons/apple-icon-57x57.png">
    <link rel="apple-touch-icon" sizes="60x60" href="./images/favicons/apple-icon-60x60.png">
    <link rel="apple-touch-icon" sizes="72x72" href="./images/favicons/apple-icon-72x72.png">
    <link rel="apple-touch-icon" sizes="76x76" href="./images/favicons/apple-icon-76x76.png">
    <link rel="apple-touch-icon" sizes="114x114" href="./images/favicons/apple-icon-114x114.png">
    <link rel="apple-touch-icon" sizes="120x120" href="./images/favicons/apple-icon-120x120.png">
    <link rel="apple-touch-icon" sizes="144x144" href="./images/favicons/apple-icon-144x144.png">
    <link rel="apple-touch-icon" sizes="152x152" href="./images/favicons/apple-icon-152x152.png">
    <link rel="apple-touch-icon" sizes="180x180" href="./images/favicons/apple-icon-180x180.png">
    <link rel="icon" type="image/png" sizes="192x192" href="./images/favicons/android-icon-192x192.png">
    <link rel="icon" type="image/png" sizes="96x96" href="./images/favicons/favicon-96x96.png">
    <link rel="icon" type="image/png" sizes="16x16" href="./images/favicons/favicon-16x16.png">
    
<script type="text/javascript" src="scripts/jquery-3.3.1.min.js"></script>
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<link rel="stylesheet" href="styles/bootstrap.min.css" type="text/css">

<!-- Custom styles for this template -->
<link href="https://getbootstrap.com/docs/4.0/examples/jumbotron/jumbotron.css" rel="stylesheet">
<link rel="stylesheet" href="styles/common.css" type="text/css">
<link rel="stylesheet" href="styles/prizes.css" type="text/css">
<link rel="stylesheet" href="styles/jmenu.css" media="screen" type="text/css" />
<link rel="stylesheet" href="styles/displaytag.css" type="text/css">

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<title><tiles:insertAttribute name="title" ignore="true" /></title>


<script type="text/javascript">
function getUrlParameter(name) {
    name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
    var regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
    var results = regex.exec(location.search);
    return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
};

function showErrorMessage(){
	var msg = getUrlParameter("message");
	msg = atob(msg);
	msg =  msg.replace(/[\|&;\$%@"<>\(\)\+,]/g, "");
	var div = document.getElementById("alertMessage");
	
	var videodiv = document.getElementById("src");
	if('<tiles:insertAttribute name="title" ignore="true" />' == 'Welcome!' || '<tiles:insertAttribute name="title" ignore="true" />' == '¡Bienvenidos!')
	videodiv.innerHTML = '<video id="src" autoplay="" muted="" loop="" poster="./images/kingdom-hearts-header-still.jpg"><source src="https://cdn.sqexeu.com/sea/kh/KH_Franchise_Video_Header-2.mp4" type="video/mp4"> Your browser does not support the video tag.</video>';
	
	if(msg != ""){
		div.style.display = '';
		div.getElementsByTagName("span")[0].innerHTML = msg;
	}
}
 

	$(document).ready(function() {
	});

	function askSubmission(msg, form) {
		if (confirm(msg))
			form.submit();
	}
	
	function relativeRedir(loc) {	
		var b = document.getElementsByTagName('base');
		if (b && b[0] && b[0].href) {
  			if (b[0].href.substr(b[0].href.length - 1) == '/' && loc.charAt(0) == '/')
    		loc = loc.substr(1);
  			loc = b[0].href + loc;
		}
		window.location.replace(loc);
	}
	
	$(function () {
		  $('[data-toggle="popover"]').popover()
		})
</script>

<link rel="stylesheet" type="text/css" href="//cdnjs.cloudflare.com/ajax/libs/cookieconsent2/3.0.3/cookieconsent.min.css" />
      <script src="//cdnjs.cloudflare.com/ajax/libs/cookieconsent2/3.0.3/cookieconsent.min.js"></script>
      <script>
         window.addEventListener("load", function(){
         window.cookieconsent.initialise({
           "palette": {
             "popup": {
               "background":"rgba(0, 0, 0, 0.8)",
               "text": "#ffffff"
             },
             "button": {
               "background": "#b8d8ff"
             }
           },
           "theme": "classic",
           "content": {
             "message": "<spring:message code='master.page.cookiesMessage'/>",
             "dismiss": "<spring:message code='master.page.cookiesOk'/>",
             "link": "<spring:message code='master.page.cookiesPage'/>",
             "href":"legaltext/index.do"
           }
         })});
      </script>

</head>

<body>
<script>
document.body.onload = function() {showErrorMessage()};
</script>
<div class="alert alert-danger alert-dismissible fade show" id="alertMessage" style="display:none;" role="alert" >
  <span></span>
  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
    <span aria-hidden="true">&times;</span>
  </button>
</div>
	<tiles:insertAttribute name="header" />
	
    <main role="main">
		
      <!-- Main jumbotron for a primary marketing message or call to action -->
       

      <div class="container layout">
        <h1 class="title-header text-center">
			<tiles:insertAttribute name="title" />
			<hr />
		</h1>
		<div  class="body-center">
		<security:authorize access="hasRole('PLAYER')">
				<div class="materials-panel">
					<div class="btn btn-material" data-trigger="hover" data-toggle="popover" data-trigger="focus" data-placement="bottom" data-content="<spring:message code='munny'/>">
						<img src="./images/materials/munny.png" width="50px"
							height="50px" /> <span class="badge badge-warning">${playerFromAbstract.materials.munny}/${maxMaterialsFromAbstract.munny}</span>
					</div>

					<div class="btn  btn-material" data-trigger="hover" data-toggle="popover" data-trigger="focus" data-placement="bottom" data-content="<spring:message code='mythril'/>">
						<img src="./images/materials/mythril.png"
							width="50px" height="50px" /> <span class="badge badge-info">${playerFromAbstract.materials.mytrhil}/${maxMaterialsFromAbstract.mytrhil}</span>
					</div>

					<div class="btn btn-material" data-trigger="hover" data-toggle="popover" data-trigger="focus" data-placement="bottom" data-content="<spring:message code='gummiCoal'/>">
						<img src="./images/materials/gummiCoal.png"
							width="50px" height="50px" /> <span class="badge badge-dark">${playerFromAbstract.materials.gummiCoal}/${maxMaterialsFromAbstract.gummiCoal}</span>
					</div>
				</div>
			</security:authorize>
		<tiles:insertAttribute name="body"/>	
		<jstl:if test="${message != null}">
			<br />
			<span class="message"><spring:message code="${message}" /></span>
		</jstl:if>	
		</div>
      </div> <!-- /container -->

    </main>

    <footer >
    
      <tiles:insertAttribute name="footer" />
    </footer>
    
<!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
   <!--  <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script> -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</body>
</html>