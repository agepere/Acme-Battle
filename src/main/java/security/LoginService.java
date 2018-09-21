/*
 * LoginService.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package security;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import services.ActorService;
import services.BannedService;
import domain.Actor;
import domain.Banned;

@Service
@Transactional
public class LoginService implements UserDetailsService {

	// Managed repository -----------------------------------------------------

	@Autowired
	UserAccountRepository	userRepository;

	@Autowired
	BannedService			bannedService;

	@Autowired
	ActorService			actorService;


	// Business methods -------------------------------------------------------

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		Assert.notNull(username);

		UserDetails result;

		result = this.userRepository.findByUsername(username);
		Assert.notNull(result);
		Actor actor = this.actorService.findByUserAccountUsername(result.getUsername());
		Banned banned = this.bannedService.findToUnbanByActor(actor.getId());

		if (banned != null) {
			Date releaseDate = this.sumarRestarHorasFecha(banned.getBanDate(), banned.getDuration() * 24);

			if (releaseDate.before(new Date()))
				this.bannedService.unban(banned);
		}

		// WARNING: The following sentences prevent lazy initialisation problems!
		Assert.notNull(result.getAuthorities());
		result.getAuthorities().size();

		return result;
	}
	public static UserAccount getPrincipal() {
		UserAccount result;
		SecurityContext context;
		Authentication authentication;
		Object principal;

		// If the asserts in this method fail, then you're
		// likely to have your Tomcat's working directory
		// corrupt. Please, clear your browser's cache, stop
		// Tomcat, update your Maven's project configuration,
		// clean your project, clean Tomcat's working directory,
		// republish your project, and start it over.

		context = SecurityContextHolder.getContext();
		Assert.notNull(context);
		authentication = context.getAuthentication();
		Assert.notNull(authentication);
		principal = authentication.getPrincipal();
		Assert.isTrue(principal instanceof UserAccount);
		result = (UserAccount) principal;
		Assert.notNull(result);
		Assert.isTrue(result.getId() != 0);

		return result;
	}

	private Date sumarRestarHorasFecha(Date fecha, int horas) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha); // Configuramos la fecha que se recibe
		calendar.add(Calendar.HOUR, horas);  // numero de horas a añadir, o restar en caso de horas<0
		return calendar.getTime(); // Devuelve el objeto Date con las nuevas horas añadidas

	}

}
