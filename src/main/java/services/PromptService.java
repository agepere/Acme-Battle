package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.Authority;

import domain.Actor;
import domain.Building;
import domain.Built;
import domain.GummiShip;
import domain.KeybladeWielder;
import domain.Materials;
import domain.Prize;
import domain.Recruited;
import domain.Recruiter;
import domain.Troop;
import domain.Warehouse;

@Service
@Transactional
public class PromptService {
	
	@Autowired
	private ActorService			actorService;
	@Autowired
	private PrizeService			prizeService;
	@Autowired
	private BuildingService			buildingService;
	@Autowired
	private BuiltService			builtService;
	@Autowired
	private WarehouseService		warehouseService;
	@Autowired
	private TroopService		    troopService;
	@Autowired
	private GummiShipService		gummiShipService;
	@Autowired
	private RecruitedService		recruitedService;
	@Autowired
	private ConfigurationService	configurationService;

	public String interpret(String command) {
		Assert.isTrue(actorService.getPrincipalAuthority().equals("GM"));
		String res = "";
		
//		set player2 -mn 999 -mt 23 -gc 23
		
		if (command.equals("help")) {
			res = "Command prompt made to send prizes, buildings or troops to any player.  <br/>" +
					"To send a prize, use <span class='helpCommand'>set [username]</span> followed by one of the next options:  <br/><br/>" +
					"<span class='helpCommand'>-mn [munnyQuantity]</span>  <br/>" +
					"<span class='helpCommand'>-mt [mythrilQuantity]</span>  <br/>" +
					"<span class='helpCommand'>-mn [gummyCoalQuantity]</span>  <br/>"+
					"<span class='helpCommand'>-dt [dd/MM/yyyy]</span>  > By default, date is set as current date plus one day<br/><br/>" +
					"To send a building, use <span class='helpCommand'>set [username]</span> followed by next option:  <br/><br/>" +
					"<span class='helpCommand'>-b [buildingName]</span>  <br/> > You can list the available building by executing <span class='helpCommand'>list buildings</span> command<br/><br/>" +
			"To send a recruiter with troops, gummiships or both, use <span class='helpCommand'>set [username]</span> followed by next option:  <br/><br/>" +
			"<span class='helpCommand'>-wh >[warehouseName]</span>  > You can list the available building by executing <span class='helpCommand'>list buildings</span> command <br/>" +
			"<span class='helpCommand'>-t >[troopName]</span>  > You can list the available troops by executing <span class='helpCommand'>list troops</span> command <br/>" +
			"<span class='helpCommand'>-gs >[gummishipName]</span>  > You can list the available gummiships by executing <span class='helpCommand'>list gumiships</span> command <br/><br/>" +
			"To remove materials from a player, use <span class='helpCommand'>rm [username]</span> followed by one of the next options:  <br/><br/>" +
			"<span class='helpCommand'>-mn [munnyQuantity]</span>  <br/>" +
			"<span class='helpCommand'>-mt [mythrilQuantity]</span>  <br/>" +
			"<span class='helpCommand'>-mn [gummyCoalQuantity]</span>  <br/>";
		} else if (command.trim().startsWith("set") && (command.indexOf("-mn")>0 || command.indexOf("-mt")>0 || command.indexOf("-gc")>0)) {
			Integer munny = 0;
			Integer mythril = 0;
			Integer gummiCoal = 0;
			String username;
			Boolean valid;
			Actor player;
			
			username = command.split("\\s+")[1];
			player = actorService.findByUserAccountUsername(username);
			valid = true;
			
			if (player != null && new ArrayList<>(player.getUserAccount().getAuthorities()).get(0).getAuthority().equals(Authority.PLAYER)) {
				if (command.indexOf("-mn") < 0) {
					munny = 0;
				} else if(command.split("-mn")[1].split("\\s+")[1].trim().matches("^[1-9]\\d*$")){
					munny = Integer.valueOf(command.split("-mn")[1].split("\\s+")[1].trim());
				}else{
					valid = false;
				}

				if (command.indexOf("-mt") < 0) {
					mythril = 0;
				} else if(command.split("-mt")[1].split("\\s+")[1].trim().matches("^[1-9]\\d*$")){
					mythril = Integer.valueOf(command.split("-mt")[1].split("\\s+")[1].trim());
				}else{
					valid = false;
				}

				if (command.indexOf("-gc") < 0) {
					gummiCoal = 0;
				} else if(command.split("-gc")[1].trim().matches("^[1-9]\\d*$")){
					gummiCoal = Integer.valueOf(command.split("-gc")[1].split("\\s+")[1].trim());
				}else{
					valid = false;
				}
				
				if(valid){
					Prize prize, saved;
					Materials materials;
					KeybladeWielder keybladeWielder;
					
					prize = prizeService.create();
					materials = new Materials();
					keybladeWielder = (KeybladeWielder) player;
					
					materials.setGummiCoal(gummiCoal);
					materials.setMunny(munny);
					materials.setMytrhil(mythril);
					prize.setDescription("prize.gm.defaultDescription");
					prize.setMaterials(materials);
					prize.setKeybladeWielder(keybladeWielder);
					
					if(command.indexOf("-dt")>=0){
						prize.setDate((LocalDate.parse(command.split("-dt")[1].trim())).toDate());
					}else{
						prize.setDate(new Date(System.currentTimeMillis()+86400000));
					}
					
					saved = prizeService.save(prize);
					res = "Prize sent";
				}else{
					res = "Command not understood";
				}
			}else{
				res = "Player doesn't exist";
			}
		}else if(command.trim().equals("list buildings")){ 
				res = buildingService.getAvailableBuildingsName().toString();
		}else if(command.trim().startsWith("set") && command.indexOf("-b")>0){ 
			String username;
			Actor player;
			Collection<String> buildingsNames;
			
			username = command.split("\\s+")[1];
			player = actorService.findByUserAccountUsername(username);
			buildingsNames = buildingService.getAvailableBuildingsName();
			
			if(builtService.getPlayerBuilts(player.getId()) >= this.configurationService.getConfiguration().getWorldSlots()){
				res = "Player doesn't have slots enough";
			}else{
			
			if (player != null && new ArrayList<>(player.getUserAccount().getAuthorities()).get(0).getAuthority().equals(Authority.PLAYER)) {
				String buildingName;
				
				buildingName = command.split("-b")[1].trim().trim();
				if(buildingsNames.contains(buildingName)){
					Building building;
					Built built;
					
					building = buildingService.getBuildingByName(buildingName);
					built = new Built();
					built.setLvl(0);
					built.setBuilding(building);
					built.setKeybladeWielder((KeybladeWielder) player);
					built.setCreationDate(new Date(System.currentTimeMillis() - 1000));
					
					builtService.saveFromGM(built);
					
					res = "Building sent";
					
				}else{
					res = "Building doesn't exist";
				}
			}else{
				res = "Player doesn't exist";
			}
		}
		}else if(command.trim().startsWith("set") && command.indexOf("-wh")>0 && (command.indexOf("-t")>0 || command.indexOf("-gs")>0)){
			String username;
			Actor player;
			Collection<String> warehouseNames;
			Collection<String> troopsNames;
			Collection<String> gummishipsNames;
			
			username = command.split("\\s+")[1];
			player = actorService.findByUserAccountUsername(username);
			warehouseNames = warehouseService.getWarehouseNames();
			troopsNames = troopService.getTroopsNames();
			gummishipsNames = gummiShipService.getGummiShipsNames();
			
			if(builtService.getPlayerBuilts(player.getId()) >= this.configurationService.getConfiguration().getWorldSlots()){
				res = "Player doesn't have slots enough";
			}else{
			
			if (player != null && new ArrayList<>(player.getUserAccount().getAuthorities()).get(0).getAuthority().equals(Authority.PLAYER)) {
				String warehouseName, troopName, gummishipName;
				
				troopName = "";
				gummishipName = "";
				
				warehouseName = command.split("-wh")[1];
				warehouseName = warehouseName.trim();
				warehouseName = warehouseName.split(">")[1];
				warehouseName = warehouseName.trim();
				
				if(warehouseName.contains("-gs")){
					warehouseName = warehouseName.substring(0, warehouseName.length()-3).trim();
				}else if(warehouseName.contains("-t")){
					warehouseName = warehouseName.substring(0, warehouseName.length()-2).trim();
				}
				
				if(command.indexOf("-t")>0){
					troopName = command.split("-t")[1].trim().split(">")[1].trim();
					if(troopName.contains("-gs")){
						troopName = troopName.substring(0, troopName.length()-3).trim();
					}
				}
				
				if(command.indexOf("-gs")>0){
					gummishipName = command.split("-gs")[1].trim().split(">")[1].trim();
					
					if(gummishipName.contains("-t")){
						gummishipName = gummishipName.substring(0, gummishipName.length()-2).trim();
					}
				}
				
				if(!warehouseNames.contains(warehouseName) ){
					res = "Warehouse doesn't exist";
				}else if(command.indexOf("-t")>0 && !troopsNames.contains(troopName)){
					res = "Troop doesn't exist";
				}else if(command.indexOf("-gs")>0 && !gummishipsNames.contains(gummishipName)){
					res = "GummiShip doesn't exist";
				}else{
					Warehouse warehouse;
					Built built;
					Boolean valid = true;
					
					warehouse = (Warehouse) buildingService.getBuildingByName(warehouseName);
					
					built = new Built();
					built.setLvl(1);
					built.setBuilding(warehouse);
					built.setKeybladeWielder((KeybladeWielder) player);
					built.setCreationDate(new Date(System.currentTimeMillis() - 1000));
					
					if(command.indexOf("-t")>0){
						if(warehouse.getTroopSlots()<0){
							valid = false;
							res = "No space for troops";
						}else{
							Troop troop;
							
							troop = troopService.getTroopByName(troopName);
							
							built.setTroop(troop);
						}
					}else if(command.indexOf("-gs")>0){
						if(warehouse.getGummiSlots()<0){
							valid = false;
							res = "No space for gummiships";
						}else{
							GummiShip gummiShip;
							
							gummiShip = gummiShipService.getGummiShipByName(gummishipName);
							
							built.setGummiShip(gummiShip);
						}
					}
					
					if(valid){
					    builtService.saveFromGM(built);
						
						res = "Warehouse sent";
					}
				}
			}
		}
		}else if(command.equals("list troops")){
			res = troopService.getTroopsNames().toString();
		}else if(command.equals("list gummiships")){
			res = gummiShipService.getGummiShipsNames().toString();
		} else if (command.trim().startsWith("rm") && (command.indexOf("-mn")>0 || command.indexOf("-mt")>0 || command.indexOf("-gc")>0)) {
			Integer munny = 0;
			Integer mythril = 0;
			Integer gummiCoal = 0;
			String username;
			Boolean valid;
			Actor player;
			
			username = command.split("\\s+")[1];
			player = actorService.findByUserAccountUsername(username);
			valid = true;
			
			if (player != null && new ArrayList<>(player.getUserAccount().getAuthorities()).get(0).getAuthority().equals(Authority.PLAYER)) {
				KeybladeWielder keybladeWielder;
				
				keybladeWielder = (KeybladeWielder) player;
				
				if (command.indexOf("-mn") < 0) {
					munny = 0;
				}else if(Integer.valueOf(command.split("-mn")[1].split("\\s+")[1].trim()) > keybladeWielder.getMaterials().getMunny()){
					munny = keybladeWielder.getMaterials().getMunny();
				}else if(command.split("-mn")[1].split("\\s+")[1].trim().matches("^[1-9]\\d*$")){
					munny = Integer.valueOf(command.split("-mn")[1].split("\\s+")[1].trim());
				}else{
					valid = false;
				}

				if (command.indexOf("-mt") < 0) {
					mythril = 0;
				}else if(Integer.valueOf(command.split("-mt")[1].split("\\s+")[1].trim()) > keybladeWielder.getMaterials().getMytrhil()){
					mythril = keybladeWielder.getMaterials().getMytrhil();
				} else if(command.split("-mt")[1].split("\\s+")[1].trim().matches("^[1-9]\\d*$")){
					mythril = Integer.valueOf(command.split("-mt")[1].split("\\s+")[1].trim());
				}else{
					valid = false;
				}

				if (command.indexOf("-gc") < 0) {
					gummiCoal = 0;
				}else if(Integer.valueOf(command.split("-gc")[1].split("\\s+")[1].trim()) > keybladeWielder.getMaterials().getGummiCoal()){
					gummiCoal = keybladeWielder.getMaterials().getGummiCoal();
				} else if(command.split("-gc")[1].trim().matches("^[1-9]\\d*$")){
					gummiCoal = Integer.valueOf(command.split("-gc")[1].split("\\s+")[1].trim());
				}else{
					valid = false;
				}
				
				if(valid){
					Materials materials;
					
					materials = new Materials();
					
					materials.setMunny(keybladeWielder.getMaterials().getMunny()-munny);
					materials.setMytrhil(keybladeWielder.getMaterials().getMytrhil() -mythril);
					materials.setGummiCoal(keybladeWielder.getMaterials().getGummiCoal()-gummiCoal);
					
					keybladeWielder.setMaterials(materials);
					
					actorService.saveFromGM(keybladeWielder);
					
					res = "Materials removed";
				}else{
					res = "Command not understood";
				}
			}
				
		}else {
		
			res = "Command not understood";
		}
		
		return res;
	}
		
}
