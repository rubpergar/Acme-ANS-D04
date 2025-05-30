<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="customer.passenger.list.label.fullName" path="fullName"  width="20%"/>
	<acme:list-column code="customer.passenger.list.label.email" path="email" width="20%" />
	<acme:list-column code="customer.passenger.list.label.passportNumber" path="passportNumber" width="20%" />
	<acme:list-column code="customer.passenger.list.label.dateOfBirth" path="dateOfBirth" width="20%" />
</acme:list>
<jstl:choose> 
	<jstl:when test="${bookingIsDraft || bookingIsDraft == null}">
	    <acme:button code="customer.bookingPassenger.form.button.addPassenger" action="/customer/booking-passenger/create?masterId=${masterId}"/>
	</jstl:when>
</jstl:choose>
