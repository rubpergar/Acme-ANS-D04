
package acme.features.any.weatherDashboard;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.principals.Any;
import acme.client.controllers.AbstractGuiController;
import acme.client.controllers.GuiController;
import acme.forms.WeatherDashboard;

@GuiController
public class AnyWeatherDashboardController extends AbstractGuiController<Any, WeatherDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyWeatherDashboardListService	anyWeatherDashboardListService;

	@Autowired
	private AnyWeatherDashboardShowService	anyWeatherDashboardShowService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.anyWeatherDashboardListService);
		super.addBasicCommand("show", this.anyWeatherDashboardShowService);
	}

}
