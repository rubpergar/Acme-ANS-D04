<%--
- menu.jsp
-
- Copyright (C) 2012-2025 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:menu-bar>
	<acme:menu-left>
		<acme:menu-option code="master.menu.anonymous" access="isAnonymous()">
			<acme:menu-suboption code="master.menu.anonymous.favourite-link" action="http://www.example.com/"/>
			<acme:menu-suboption code="master.menu.anonymous.lauperfra-link" action="https://github.com/rubpergar/Acme-ANS-D01.git"/>
			<acme:menu-suboption code="master.menu.anonymous.cyncasjua-link" action="https://www.spotify.com/"/>
			<acme:menu-suboption code="master.menu.anonymous.macpercam-link" action="https://es.pinterest.com/"/>
			<acme:menu-suboption code="master.menu.anonymous.rubpergar-link" action="https://www.youtube.com/"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.manager" access="hasRealm('Manager')">
			<acme:menu-suboption code="master.menu.manager.list-flights" action="/manager/flight/list"/>
			<acme:menu-suboption code="master.menu.manager.dashboard" action="/manager/manager-dashboard/show"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.assistanceAgent" access="hasRealm('AssistanceAgent')">
			<acme:menu-suboption code="master.menu.assistanceAgent.completed-list-claims" action="/assistance-agent/claim/completed-list"/>	
			<acme:menu-suboption code="master.menu.assistanceAgent.pending-list-claims" action="/assistance-agent/claim/pending-list"/>					
    </acme:menu-option>
    
		<acme:menu-option code="master.menu.customer" access="hasRealm('Customer')">
			<acme:menu-suboption code="master.menu.customer.list-bookings" action="/customer/booking/list"/>
			<acme:menu-suboption code="master.menu.customer.list-passengers" action="/customer/passenger/list"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.member" access="hasRealm('FlightCrewMember')">
			<acme:menu-suboption code="master.menu.member.listCompleted-flightAssignment" action="/flight-crew-member/flight-assignment/listCompleted"/>
			<acme:menu-suboption code="master.menu.member.listUncompleted-flightAssignment" action="/flight-crew-member/flight-assignment/listUncompleted"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.administrator" access="hasRealm('Administrator')">
			<acme:menu-suboption code="master.menu.administrator.list-user-accounts" action="/administrator/user-account/list"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.populate-db-initial" action="/administrator/system/populate-initial"/>
			<acme:menu-suboption code="master.menu.administrator.populate-db-sample" action="/administrator/system/populate-sample"/>			
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.shut-system-down" action="/administrator/system/shut-down"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.list-aircrafts" action="/administrator/aircraft/list"/>
			<acme:menu-suboption code="master.menu.administrator.list-airport" action="/administrator/airport/list"/>	
			<acme:menu-suboption code="master.menu.administrator.list-airline" action="/administrator/airline/list"/>	
			<acme:menu-suboption code="master.menu.administrator.populate-weathers" action="/administrator/weather/populate"/>		
		</acme:menu-option>
		
		
		<acme:menu-option code="master.menu.any">
      		<acme:menu-suboption code="master.menu.any.flights" action="/any/flight/list"/>
      		<acme:menu-suboption code="master.menu.any.weather" action="/any/weather-dashboard/list"/>
      		<acme:menu-suboption code="master.menu.any.badWeather" action="/any/flight/list-under-bad-weather"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.provider" access="hasRealm('Provider')">
			<acme:menu-suboption code="master.menu.provider.favourite-link" action="http://www.example.com/"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.consumer" access="hasRealm('Consumer')">
			<acme:menu-suboption code="master.menu.consumer.favourite-link" action="http://www.example.com/"/>
		</acme:menu-option>
	</acme:menu-left>

	<acme:menu-right>		
		<acme:menu-option code="master.menu.user-account" access="isAuthenticated()">
			<acme:menu-suboption code="master.menu.user-account.general-profile" action="/authenticated/user-account/update"/>
			<acme:menu-suboption code="master.menu.user-account.become-provider" action="/authenticated/provider/create" access="!hasRealm('Provider')"/>
			<acme:menu-suboption code="master.menu.user-account.provider-profile" action="/authenticated/provider/update" access="hasRealm('Provider')"/>
			<acme:menu-suboption code="master.menu.user-account.become-consumer" action="/authenticated/consumer/create" access="!hasRealm('Consumer')"/>
			<acme:menu-suboption code="master.menu.user-account.consumer-profile" action="/authenticated/consumer/update" access="hasRealm('Consumer')"/>
			<acme:menu-suboption code="master.menu.user-account.become-manager" action="/authenticated/manager/create" access="!hasRealm('Manager')"/>
			<acme:menu-suboption code="master.menu.user-account.manager-profile" action="/authenticated/manager/update" access="hasRealm('Manager')"/>
		</acme:menu-option>
	</acme:menu-right>
</acme:menu-bar>

