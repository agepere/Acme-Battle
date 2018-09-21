package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Configuration;
import domain.Materials;

import utilities.AbstractTest;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ConfigurationServiceTest extends AbstractTest {

	@Autowired
	private ConfigurationService configurationService;

	// In this test we edit the Configuration
	@Test
	public void ConfigurationEditTest() {

		super.authenticate("admin");
		Configuration configuration = this.configurationService.getConfiguration();

		Materials materials = new Materials();
		materials.setGummiCoal(300);
		materials.setMunny(300);
		materials.setMytrhil(300);
		
		configuration.setBaseMaterials(materials);
		configuration.setDailyMaterials(materials);
		
		configuration.setOrgMessages(20);
		configuration.setPercentageWinAttacker(0.1);
		configuration.setPercentageWinDefender(0.1);
		configuration.setWorldSlots(5);

		this.configurationService.save(configuration);
		this.configurationService.flush();

		super.unauthenticate();
	}

	// Negative test: Only the Admin can edit the configuration
	@Test(expected = IllegalArgumentException.class)
	public void ConfigurationWrongUserEditTest() {

		super.authenticate("player1");
		Configuration configuration = this.configurationService.getConfiguration();

		Materials materials = new Materials();
		materials.setGummiCoal(200);
		materials.setMunny(200);
		materials.setMytrhil(200);
		configuration.setBaseMaterials(materials);

		configuration.setDailyMaterials(materials);
		configuration.setOrgMessages(20);
		configuration.setPercentageWinAttacker(0.1);
		configuration.setPercentageWinDefender(0.1);
		configuration.setWorldSlots(5);

		this.configurationService.save(configuration);
		this.configurationService.flush();
		

		super.unauthenticate();
	}

	// Negative test: Only the Admin can edit the configuration
	@Test(expected = IllegalArgumentException.class)
	public void ConfigurationWrongUserEditTest2() {

		super.authenticate("manager1");
		Configuration configuration = this.configurationService.getConfiguration();

		Materials materials = new Materials();
		materials.setGummiCoal(200);
		materials.setMunny(200);
		materials.setMytrhil(200);
		configuration.setBaseMaterials(materials);

		configuration.setDailyMaterials(materials);
		configuration.setOrgMessages(20);
		configuration.setPercentageWinAttacker(0.1);
		configuration.setPercentageWinDefender(0.1);
		configuration.setWorldSlots(5);

		this.configurationService.save(configuration);
		this.configurationService.flush();

		super.unauthenticate();
	}

	// Negative test: We don't match some restrictions like the percentages
	@Test(expected = ConstraintViolationException.class)
	public void ConfigurationWrongDataEditTest() {

		super.authenticate("admin");
		Configuration configuration = this.configurationService.getConfiguration();

		Materials materials = new Materials();
		materials.setGummiCoal(200);
		materials.setMunny(200);
		materials.setMytrhil(200);
		configuration.setBaseMaterials(materials);

		configuration.setDailyMaterials(materials);
		configuration.setOrgMessages(20);
		configuration.setPercentageWinAttacker(-0.2);
		configuration.setPercentageWinDefender(2.5);
		configuration.setWorldSlots(5);

		this.configurationService.save(configuration);
		this.configurationService.flush();

		super.unauthenticate();
	}
	
	// Negative test: Base and daily materials must be always higher than the before value
		@Test(expected = ConstraintViolationException.class)
		public void ConfigurationLessMaterialThanBeofreEditTest() {

			super.authenticate("admin");
			Configuration configuration = this.configurationService.getConfiguration();

			Materials materials = new Materials();
			materials.setGummiCoal(10);
			materials.setMunny(10);
			materials.setMytrhil(10);
			configuration.setBaseMaterials(materials);

			configuration.setDailyMaterials(materials);
			configuration.setOrgMessages(20);
			configuration.setPercentageWinAttacker(-0.2);
			configuration.setPercentageWinDefender(2.5);
			configuration.setWorldSlots(5);

			this.configurationService.save(configuration);
			this.configurationService.flush();

			super.unauthenticate();
		}

}
