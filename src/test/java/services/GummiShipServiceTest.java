package services;


import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import domain.Materials;

import domain.GummiShip;
import domain.Recruiter;
import domain.Troop;

import utilities.AbstractTest;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class GummiShipServiceTest extends AbstractTest {

	@Autowired
	private GummiShipService gummiShipService;
	
	@Autowired
	private RecruiterService recruiterService;
	
	
	@Test
	public void saveDriver() {
		final Object testingData[][] = {

			{
				"manager1", "GummiName", 100, 1, 1, 1, "recruiter1", null, "crear"
			},	//Crear y borrar una nave correctamente
			{
				"manager1", "GummiName", 100, 1, 1, 100, "recruiter1", IllegalArgumentException.class, "crear"
			},	//Crear nave con RequitedRecruiterLvl > recruiterLevel
			
			
			

		};
		for (int i = 0; i < testingData.length; i++) {

			super.startTransaction();
			super.flushTransaction();
			this.template((String) testingData[i][0], (String) testingData[i][1], (Integer) testingData[i][2], (Integer) testingData[i][3], 
					(Integer) testingData[i][4], (Integer) testingData[i][5], (String) testingData[i][6], (Class<?>) testingData[i][7], (String) testingData[i][8]);
		}
	}
	// In this test we edit the Configuration
	
	protected void template(String username1, String name, Integer slots, Integer cost,
			Integer timeTR, Integer recruiterRL, String recruiter, final Class<?> expected, String operation) {
		Class<?> caught;

		caught = null;
		try {
			if(operation.equals("crear")){
				super.authenticate(username1);
				Recruiter r = this.recruiterService.findOne(this.getEntityId(recruiter));
				Materials m = new Materials();
				m.setGummiCoal(cost);
				m.setMunny(cost);
				m.setMytrhil(cost);
				
				GummiShip g = this.gummiShipService.create(r);
				g.setSlots(slots);
				g.setCost(m);
				g.setTimeToRecruit(timeTR);
				g.setRecruiterRequiredLvl(recruiterRL);
				
				this.recruiterService.addGummiShip(r, g);
				
				super.unauthenticate();
			
			}
				
				
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
	
	}
	

}
