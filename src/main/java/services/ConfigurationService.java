
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ConfigurationRepository;
import security.Authority;
import security.LoginService;
import domain.Configuration;

@Service
@Transactional
public class ConfigurationService {

	// Managed repository ------------------------------ 
	@Autowired
	private ConfigurationRepository	configurationRepository;

	@Autowired
	private Validator				validator;


	// Simple CRUD methods ------------------------------ 
	public Configuration create() {
		Configuration configuration;

		configuration = new Configuration();
		
		return configuration;
	}

	public Collection<Configuration> findAll() {
		final Collection<Configuration> configurations = new ArrayList<>();

		configurations.add(this.getConfiguration());
		Assert.notNull(configurations);

		return configurations;
	}

	public Configuration findOne(final int ConfigurationId) {
		Configuration configuration;
		configuration = this.getConfiguration();
		Assert.notNull(configuration);

		return configuration;
	}

	public Configuration save(final Configuration configuration) {
		Configuration result;
		final Authority admin = new Authority();
		admin.setAuthority("ADMIN");
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(admin));

		// Only one Configuration object
		
		if (configuration.getId() == 0)
			this.configurationRepository.deleteAll();
		
		Assert.isTrue(configuration.getWorldSlots() >= this.configurationRepository.getConfiguration().getWorldSlots(),"error.message.wordSlotsError");
		Assert.isTrue(configuration.getBaseMaterials().getGummiCoal() >= this.configurationRepository.getConfiguration().getBaseMaterials().getGummiCoal(),"error.message.GummiCoalError");
		Assert.isTrue(configuration.getBaseMaterials().getMunny() >= this.configurationRepository.getConfiguration().getBaseMaterials().getMunny(),"error.message.MunnyError");
		Assert.isTrue(configuration.getBaseMaterials().getMytrhil() >= this.configurationRepository.getConfiguration().getBaseMaterials().getMytrhil(),"error.message.MytrhilError");
		
		result = this.configurationRepository.save(configuration);
		Assert.notNull(result);
		return result;
	}

	public void saveAndFlush(final Configuration configuration) {
		this.configurationRepository.saveAndFlush(configuration);
	}

	public Configuration reconstruct(Configuration configuration, BindingResult binding) {
		Configuration res;

		res = configuration;
		
		res.getBaseMaterials().setGummiCoal(configuration.getBaseMaterials().getGummiCoal());
		res.getBaseMaterials().setMunny(configuration.getBaseMaterials().getMunny());
		res.getBaseMaterials().setMytrhil(configuration.getBaseMaterials().getMytrhil());
		res.getDailyMaterials().setGummiCoal(configuration.getDailyMaterials().getGummiCoal());
		res.getDailyMaterials().setMunny(configuration.getDailyMaterials().getMunny());
		res.getDailyMaterials().setMytrhil(configuration.getDailyMaterials().getMytrhil());
		
		res.setOrgMessages(configuration.getOrgMessages());
		res.setPercentageWinAttacker(configuration.getPercentageWinAttacker());
		res.setPercentageWinDefender(configuration.getPercentageWinDefender());
		res.setWorldSlots(configuration.getWorldSlots());

		this.validator.validate(res, binding);
		return res;
	}

	// Other bussines methods ------------------------------ 
	public Configuration getConfiguration() {
		return this.configurationRepository.getConfiguration();
	}
	
	public void flush(){
		this.configurationRepository.flush();
	}

}
