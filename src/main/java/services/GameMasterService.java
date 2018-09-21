
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.GameMasterRepository;
import domain.Actor;
import domain.GameMaster;

@Service
@Transactional
public class GameMasterService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private GameMasterRepository	GameMasterRepository;

	@Autowired
	private ActorService			actorService;


	// CRUD methods

	public GameMaster create() {
		GameMaster GameMaster;

		GameMaster = new GameMaster();

		return GameMaster;
	}

	public GameMaster save(GameMaster GameMaster) {
		Assert.notNull(GameMaster);

		GameMaster saved;

		saved = this.GameMasterRepository.save(GameMaster);

		return saved;
	}

	public GameMaster saveFromCreate(Actor user) {
		GameMaster result;

		Assert.isTrue(this.actorService.findByUserAccountUsername(user.getUserAccount().getUsername()) == null, "error.message.duplicatedUsername");
		Assert.isTrue(this.actorService.findByNickname(user.getNickname()) == null, "error.message.duplicatedNickname");

		Md5PasswordEncoder encoder;
		String hash;

		encoder = new Md5PasswordEncoder();
		hash = encoder.encodePassword(user.getUserAccount().getPassword(), null);

		user.getUserAccount().setPassword(hash);

		user.setConfirmMoment(new Date(System.currentTimeMillis() - 1000));
		user.setHasConfirmedTerms(true);

		GameMaster gm = new GameMaster();
		gm.setAvatar(user.getAvatar());
		gm.setConfirmMoment(user.getConfirmMoment());
		gm.setEmail(user.getEmail());
		gm.setHasConfirmedTerms(user.getHasConfirmedTerms());
		gm.setName(user.getName());
		gm.setNickname(user.getNickname());
		gm.setPhone(user.getPhone());
		gm.setSurname(user.getSurname());
		gm.setUserAccount(user.getUserAccount());
		gm.setVersion(user.getVersion());
		gm.setId(user.getId());

		result = this.save(gm);
		return result;
	}
	public GameMaster findOne(int GameMasterId) {
		Assert.notNull(GameMasterId);

		GameMaster GameMaster;

		GameMaster = this.GameMasterRepository.findOne(GameMasterId);

		return GameMaster;
	}

	public Collection<GameMaster> findAll() {
		Collection<GameMaster> GameMasters;

		GameMasters = this.GameMasterRepository.findAll();

		return GameMasters;
	}

	public void delete(GameMaster GameMaster) {
		Assert.notNull(GameMaster);

		this.GameMasterRepository.delete(GameMaster);
	}

}
