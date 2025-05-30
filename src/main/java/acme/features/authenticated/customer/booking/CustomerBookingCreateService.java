
package acme.features.authenticated.customer.booking;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.views.SelectChoices;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.booking.Booking;
import acme.entities.booking.TravelClass;
import acme.entities.flights.Flight;
import acme.realms.Customer;

@GuiService
public class CustomerBookingCreateService extends AbstractGuiService<Customer, Booking> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private CustomerBookingRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		boolean status = super.getRequest().getPrincipal().hasRealmOfType(Customer.class);

		if (super.getRequest().getMethod().equals("POST")) {
			Integer flightId = super.getRequest().getData("flight", int.class);
			//FLIGHT
			if (flightId != 0) {
				Flight flight = this.repository.findFlightById(flightId);

				if (flight == null || flight.getIsDraft())
					status = false;
			}
		}
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Booking booking;
		int userAccountId;
		Customer customer;
		Date currentMoment;

		userAccountId = super.getRequest().getPrincipal().getAccountId();
		customer = this.repository.findCustomerByuserAccountId(userAccountId);
		currentMoment = MomentHelper.getCurrentMoment();

		booking = new Booking();
		booking.setLocatorCode("");
		booking.setPurchaseMoment(currentMoment);
		booking.getPrice();
		booking.setLastNibble(null);
		booking.setIsDraft(true);
		booking.setCustomer(customer);

		super.getBuffer().addData(booking);
	}

	@Override
	public void bind(final Booking booking) {
		int flightId;
		Flight flight;

		flightId = super.getRequest().getData("flight", int.class);
		flight = this.repository.findFlightById(flightId);
		booking.setFlight(flight);

		super.bindObject(booking, "locatorCode", "travelClass", "lastNibble");
	}

	@Override
	public void validate(final Booking booking) {

		// Verificar que el locatorCode es único
		boolean locatorCodeStatus = this.repository.findBookingsByLocatorCode(booking.getLocatorCode()).size() == 0;
		super.state(locatorCodeStatus, "locatorCode", "acme.validation.booking.repeated-locatorCode.message");

		// Verificar que el flight no es null
		boolean flightNullStatus = this.repository.findNotDraftFlights().contains(booking.getFlight());
		super.state(flightNullStatus, "flight", "acme.validation.booking.notExisting-flight.message");

	}

	@Override
	public void perform(final Booking booking) {
		booking.setFlight(booking.getFlight());
		this.repository.save(booking);
	}

	@Override
	public void unbind(final Booking booking) {

		List<Flight> nonDraftFlights = this.repository.findNotDraftFlights().stream().toList();

		List<Flight> validFlights = nonDraftFlights.stream().filter(f -> f.getScheduledDeparture().after(booking.getPurchaseMoment())).toList();

		SelectChoices travelClasses = SelectChoices.from(TravelClass.class, booking.getTravelClass());
		SelectChoices flights = SelectChoices.from(validFlights, "flightDistinction", booking.getFlight());
		Dataset dataset;
		dataset = super.unbindObject(booking, "locatorCode", "flight", "purchaseMoment", "travelClass", "lastNibble", "isDraft");
		dataset.put("travelClass", travelClasses);
		dataset.put("flight", flights);
		dataset.put("price", booking.getPrice());
		super.getResponse().addData(dataset);
	}

}
