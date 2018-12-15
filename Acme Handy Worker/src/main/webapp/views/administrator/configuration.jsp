<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- 
Recieves: 
	Configuration configuration- the configuration object of the system.
-->

<security:authorize access="hasRole('ADMIN')">
	<form:form action="administrator/admin/configuration.do" modelAttribute="configuration">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<form:hidden path="spamWords" />
	<form:hidden path="creditCardMakes" />
		
	
	<form:label path="systemName">
		<spring:message code="admin.systemName" />:
	</form:label>
	
	<form:input path="systemName" />
	<form:errors cssClass="error" path="systemName" />
	<br />
	
	<!--  -->
	<form:label path="banner">
		<spring:message code="admin.banner" />:
	</form:label>
	
	<form:input path="banner" />
	<form:errors cssClass="error" path="banner" />
	<br />
	
	<!--  -->
	<form:label path="vatPercentage">
		<spring:message code="admin.vatPercentage" />:
	</form:label>
	
	<form:input path="vatPercentage" />
	<form:errors cssClass="error" path="vatPercentage" />
	<br />
	
	<!--  -->
	<form:label path="finderCacheTime">
		<spring:message code="admin.finderCacheTime" />:
	</form:label>
	
	<form:input path="finderCacheTime" />
	<form:errors cssClass="error" path="finderCacheTime" />
	<br />
	
	<!--  -->
	<form:label path="defaultPhoneCode">
		<spring:message code="admin.defaultPhoneCode" />:
	</form:label>
	
	<form:input path="defaultPhoneCode" />
	<form:errors cssClass="error" path="defaultPhoneCode" />
	<br />
	
	<!--  -->
	<form:label path="finderQueryResults">
		<spring:message code="admin.finderQueryResults" />:
	</form:label>
	
	<form:input path="finderQueryResults" />
	<form:errors cssClass="error" path="finderQueryResults" />
	<br />
	
	<!--  -->
	<form:label path="welcomeTextEnglish">
		<spring:message code="admin.welcomeTextEnglish" />:
	</form:label>
	
	<form:input path="welcomeTextEnglish" />
	<form:errors cssClass="error" path="welcomeTextEnglish" />
	<br />
	
	<!--  -->
	<form:label path="welcomeTextSpanish">
		<spring:message code="admin.welcomeTextSpanish" />:
	</form:label>
	
	<form:input path="welcomeTextSpanish" />
	<form:errors cssClass="error" path="welcomeTextSpanish" />
	<br />
	
	
	
	<input type="submit" name="save" value="<spring:message code="admin.save" />" />
				
	<input type="button" name="cancel"
		value="<spring:message code="admin.cancel" />"
		onclick="javascript: window.location.replace('')" />
	<br />
</form:form>

<h2><spring:message code=admin.manageSpamWords/></h2>
<spring:message code=admin.currentSpamWords/>:
<jstl:forEach var="i" items="${configuration.spamWords}">
<jstl:out value="${i.word}"></jstl:out>
</jstl:forEach>

<form:form action="administrator/admin/configuration.do" modelAttribute="word">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="type" value="SPAM"/>
	
	<form:label path="word">
		<spring:message code="admin.word" />:
	</form:label>
	
	<form:input path="word" />
	<form:errors cssClass="error" path="word" />
	<br />
	
	<input type="submit" name="addSpam" value="<spring:message code="admin.add" />" />
</form:form>

<form:form action="administrator/admin/configuration.do" modelAttribute="word">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="type"/>
	
	<form:label path="word">
		<spring:message code="admin.word" />:
	</form:label>
	
	<form:select path="word">
		<form:options items="${configuration.spamWords}" itemLabel="word" itemValue="id"/>
	</form:select>
	<form:errors cssClass="error" path="word" />
	<br />
	
	<input type="submit" name="removeSpam" value="<spring:message code="admin.remove" />" />
</form:form>

<h2><spring:message code=admin.manageCreditCardMakes/></h2>
<spring:message code=admin.currentCreditCardMakes/>:
<jstl:forEach var="i" items="${configuration.spamWords}">
<jstl:out value="${i.word}"></jstl:out>
</jstl:forEach>

<form:form action="administrator/admin/configuration.do" modelAttribute="creditCardMake">
	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<form:label path="make">
		<spring:message code="admin.make" />:
	</form:label>
	
	<form:input path="make" />
	<form:errors cssClass="error" path="make" />
	<br />
	
	<input type="submit" name="addMake" value="<spring:message code="admin.add" />" />
</form:form>

<form:form action="administrator/admin/configuration.do" modelAttribute="creditCardMake">
	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<form:label path="make">
		<spring:message code="admin.make" />:
	</form:label>
	
	<form:select path="make">
		<form:options items="${configuration.creditCardMakes}" itemLabel="make" itemValue="id"/>
	</form:select>
	<form:errors cssClass="error" path="make" />
	<br />
	
	<input type="submit" name="removeMake" value="<spring:message code="admin.remove" />" />
</form:form>


</security:authorize>