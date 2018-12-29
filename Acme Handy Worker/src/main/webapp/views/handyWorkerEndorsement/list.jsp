+<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

	<!-- PARAMETERS FROM CONTROLLER:
									 handyWorkerEndorsements Collection<HandyWorkerEndorsement>
									 customer Customer
									 handyWorker HandyWorker
									 -->
									 
		<security:authorize access="hasRole('CUSTOMER')">
		
			<h1><spring:message code="handyWorkerEndorsement.handyWorkerEndorsements"/>:</h1><br/>
			<display:table name="handyWorkerEndorsements" id="row" requestURI="handyWorkerEndorsements/customer/list.do" pagesize="5">
				
				<display:column titleKey="handyWorkerEndosements.options">
					<a href="handyWorkerEndorsement/show.do?handyWorkerEndorsementId=${row.id}">
								<spring:message	code="handyWorkerEndorsement.show" />
					</a>
					<br/>
					<jstl:if test="${row.customer == customer}"> 
							<a href="handyWorkerEndorsement/customer/edit.do?handyWorkerEndorsementId=${row.id}">
								<spring:message	code="handyWorkerEndorsement.edit" />
							</a>
							<br/>	
							<a href="handyWorkerEndorsement/customer/delete.do?handyWorkerEndorsementId=${row.id}">
								<spring:message	code="handyWorkerEndorsement.delete" />
							</a>
							<br/>			
					</jstl:if>
				</display:column>
				
				<spring:message code="handyWorkerEndorsement.moment.format" var="formatMoment"/>
				<display:column property="moment" titleKey="handyWorkerEndorsement.moment" format="{0,date,${formatMoment}}" />	
			
				<display:column property="text" titleKey="handyWorkerEndorsement.text" />
			
			</display:table>
			
			<a href="handyWorkerEndorsement/customer/create.do"><spring:message code="handyWorkerEndorsement.create"/></a>
			
		</security:authorize>
			
		