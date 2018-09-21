<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<script>
$(function () {
	  $('[data-toggle="popover"]').popover()
	})
</script>
<div class="materials-panel">
	<div class="btn btn-material" data-trigger="hover"
		data-toggle="popover" data-trigger="focus" data-placement="bottom"
		data-content="<spring:message code='munny'/>">
		<img src="./images/materials/munny.png" width="50px" height="50px" />
		<span class="badge badge-warning">${playerFromAbstract.materials.munny}/${maxMaterialsFromAbstract.munny}</span>
	</div>

	<div class="btn  btn-material" data-trigger="hover"
		data-toggle="popover" data-trigger="focus" data-placement="bottom"
		data-content="<spring:message code='mythril'/>">
		<img src="./images/materials/mythril.png" width="50px" height="50px" />
		<span class="badge badge-info">${playerFromAbstract.materials.mytrhil}/${maxMaterialsFromAbstract.mytrhil}</span>
	</div>

	<div class="btn btn-material" data-trigger="hover"
		data-toggle="popover" data-trigger="focus" data-placement="bottom"
		data-content="<spring:message code='gummiCoal'/>">
		<img src="./images/materials/gummiCoal.png" width="50px" height="50px" />
		<span class="badge badge-dark">${playerFromAbstract.materials.gummiCoal}/${maxMaterialsFromAbstract.gummiCoal}</span>
	</div>
</div>