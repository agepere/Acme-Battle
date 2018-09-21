/*
 * AbstractController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.ActorService;
import services.BuiltService;
import services.ConfigurationService;
import services.KeybladeWielderService;
import services.PrizeService;
import domain.KeybladeWielder;

@Controller
public class AbstractController {

	@Autowired
	private MessageSource			messageSource;
	@Autowired
	private PrizeService			prizeService;
	@Autowired
	private ConfigurationService	configurationService;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private BuiltService			builtService;
	@Autowired
	private KeybladeWielderService	keybladeWielderService;


	// Panic handler ----------------------------------------------------------

	@ExceptionHandler(Throwable.class)
	public ModelAndView panic(final Throwable oops) {
		ModelAndView result;

		result = new ModelAndView("misc/panic");
		result.addObject("name", ClassUtils.getShortName(oops.getClass()));
		result.addObject("exception", oops.getMessage());
		result.addObject("stackTrace", ExceptionUtils.getStackTrace(oops));

		return result;
	}

	public String getErrorMessage(final Throwable oops) {
		String res = oops.getMessage();

		if (res == null || !res.startsWith("error.message."))
			res = "error.message.commit";

		return res;
	}

	@ModelAttribute
	public void addPlayerToModel(Model model) {
		SecurityContext context;
		Boolean anonymous;

		try {
			context = SecurityContextHolder.getContext();
			anonymous = context.getAuthentication().getPrincipal().equals("anonymousUser");

			if (!anonymous && this.actorService.getPrincipalAuthority().equals(Authority.PLAYER)) {
				KeybladeWielder player;

				player = (KeybladeWielder) this.actorService.findByPrincipal();

				Date today = new Date(System.currentTimeMillis());
				if (today.getDate() != player.getLastConnection().getDate() || today.getMonth() != player.getLastConnection().getMonth() || today.getYear() != player.getLastConnection().getYear())
					this.prizeService.createDailyPrizeForPrincipal();

				if (this.sumarRestarHorasFecha(player.getLastConnection(), 1).before(new Date())) {
					player.setLastConnection(new Date(System.currentTimeMillis() - 1000));
					player = this.keybladeWielderService.save(player);

				}

				model.addAttribute("playerFromAbstract", player);
				model.addAttribute("maxMaterialsFromAbstract", this.builtService.maxMaterials());
			}
		} catch (Throwable e) {
			System.out.println("oh baia");
		}
	}
	public String showDetails(Locale locale, String code) {
		return new String(org.springframework.security.crypto.codec.Base64.encode(this.messageSource.getMessage(code, null, locale).getBytes()));
	}
	public Date sumarRestarHorasFecha(Date fecha, int horas) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha); // Configuramos la fecha que se recibe
		calendar.add(Calendar.HOUR, horas);  // numero de horas a añadir, o restar en caso de horas<0
		return calendar.getTime(); // Devuelve el objeto Date con las nuevas horas añadidas

	}
}
