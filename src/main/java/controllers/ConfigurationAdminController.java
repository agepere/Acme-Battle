package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import controllers.AbstractController;
import domain.Configuration;

@Controller()
@RequestMapping("/configuration/administrator")
public class ConfigurationAdminController extends AbstractController {

	@Autowired
	private ConfigurationService	configurationService;


	@RequestMapping("/edit")
	public ModelAndView edit() {
		ModelAndView result;
		Configuration configuration;

		configuration = this.configurationService.getConfiguration();
		Assert.notNull(configuration);
		result = this.createEditModelAndView(configuration);
		result.addObject("uri", "configuration/administrator/edit.do");
		return result;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Configuration configuration, BindingResult binding) {
		ModelAndView result;

		configuration = this.configurationService.reconstruct(configuration, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(configuration);
		else
			try {
				this.configurationService.save(configuration);
				result = new ModelAndView("redirect:edit.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(configuration, this.getErrorMessage(oops));
			}
		return result;
	}
	

	protected ModelAndView createEditModelAndView(Configuration configuration) {
		return this.createEditModelAndView(configuration, null);
	}

	protected ModelAndView createEditModelAndView(final Configuration configuration, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("configuration/administrator/edit");
		result.addObject("configuration", configuration);
		result.addObject("message", messageCode);
		result.addObject("uri", "configuration/administrator/save.do");

		return result;
	}

}