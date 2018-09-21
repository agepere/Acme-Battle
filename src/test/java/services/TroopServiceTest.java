package services;


import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import domain.Materials;

import domain.Recruiter;
import domain.Troop;

import utilities.AbstractTest;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class TroopServiceTest extends AbstractTest {

	@Autowired
	private TroopService troopService;
	
	@Autowired
	private RecruiterService recruiterService;
	
	
	@Test
	public void saveDriver() {
		final Object testingData[][] = {

			{
				"manager1", null ,"TroopName", 100, 100, 1, 1, 1, "recruiter1", null, "crear y borrar"
			},	//Crear y borrar una tropa correctamente
			{
				"manager1", null, "TroopName", 100, 100, 1, 1, 100, "recruiter1", IllegalArgumentException.class, "crear"
			},	//Crear tropa con RequitedRecruiterLvl > recruiterLevel
			{
				"manager1", "troop2", null, null, null, null, null, null, null, null, "borrar"
			},	//Borrar tropa con recruiteds
			{
				"manager1", "troop1", null, null, null, null, null, null, null, IllegalArgumentException.class, "borrar"
			}	//Intentar borrar tropa con recruiter.getTroops().size =1
			
			

		};
		for (int i = 0; i < testingData.length; i++) {

			super.startTransaction();
			super.flushTransaction();
			this.template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Integer) testingData[i][3], (Integer) testingData[i][4], 
					(Integer) testingData[i][5], (Integer) testingData[i][6], (Integer) testingData[i][7], (String) testingData[i][8], (Class<?>) testingData[i][9], (String) testingData[i][10]);
		}
	}
	// In this test we edit the Configuration
	
	protected void template(String username1, String troop, String name, Integer attack, Integer defense, Integer cost,
			Integer timeTR, Integer recruiterRL, String recruiter, final Class<?> expected, String operation) {
		Class<?> caught;

		caught = null;
		try {
			if(operation.equals("crear y borrar")){
				super.authenticate(username1);
				Recruiter r = this.recruiterService.findOne(this.getEntityId(recruiter));
				Materials m = new Materials();
				m.setGummiCoal(cost);
				m.setMunny(cost);
				m.setMytrhil(cost);
				
				Troop t = this.troopService.create(r);
				t.setAttack(attack);
				t.setDefense(defense);
				t.setCost(m);
				t.setTimeToRecruit(timeTR);
				t.setRecruiterRequiredLvl(recruiterRL);
				
				this.recruiterService.addTroop(r, t);
				
				this.troopService.delete(t);
				super.unauthenticate();
			
			}
			if(operation.equals("crear")){
				super.authenticate(username1);
				Recruiter r = this.recruiterService.findOne(this.getEntityId(recruiter));
				Materials m = new Materials();
				m.setGummiCoal(cost);
				m.setMunny(cost);
				m.setMytrhil(cost);
				
				Troop t = this.troopService.create(r);
				t.setAttack(attack);
				t.setDefense(defense);
				t.setCost(m);
				t.setTimeToRecruit(timeTR);
				t.setRecruiterRequiredLvl(recruiterRL);
				
				this.recruiterService.addTroop(r, t);
				
				super.unauthenticate();
			}
			
			if(operation.equals("borrar")){
				
				super.authenticate(username1);
				
				Troop t = this.troopService.findOne(this.getEntityId(troop));
				this.troopService.deleteCompleto(t);
				super.unauthenticate();
			}
				
				
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		
		this.checkExceptions(expected, caught);
	
	}
	

}
