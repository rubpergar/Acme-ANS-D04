
package acme.features.authenticated.customer.bookingPassenger;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractGuiController;
import acme.entities.booking.BookingPassenger;
import acme.realms.Customer;

@Controller
public class CustomerBookingPassengerController extends AbstractGuiController<Customer, BookingPassenger> {

	@Autowired
	protected CustomerBookingPassengerCreateService	createService;

	@Autowired
	protected CustomerBookingPassengerListService	listService;

	@Autowired
	protected CustomerBookingPassengerShowService	showService;

	@Autowired
	protected CustomerBookingPassengerDeleteService	deleteService;


	@PostConstruct
	protected void initialise() {

		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("delete", this.deleteService);
	}

}
