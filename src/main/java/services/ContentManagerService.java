
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ContentManagerRepository;
import domain.Actor;
import domain.ContentManager;

@Service
@Transactional
public class ContentManagerService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ContentManagerRepository	contentManagerRepository;

	@Autowired
	private ActorService				actorService;


	// CRUD methods

	public ContentManager create() {
		ContentManager contentManager;

		contentManager = new ContentManager();

		return contentManager;
	}

	public ContentManager save(ContentManager contentManager) {
		Assert.notNull(contentManager);

		ContentManager saved;

		saved = this.contentManagerRepository.save(contentManager);

		return saved;
	}

	public ContentManager saveFromCreate(Actor user) {
		ContentManager result;

		Assert.isTrue(this.actorService.findByUserAccountUsername(user.getUserAccount().getUsername()) == null, "error.message.duplicatedUsername");
		Assert.isTrue(this.actorService.findByNickname(user.getNickname()) == null, "error.message.duplicatedNickname");

		Md5PasswordEncoder encoder;
		String hash;

		encoder = new Md5PasswordEncoder();
		hash = encoder.encodePassword(user.getUserAccount().getPassword(), null);

		user.getUserAccount().setPassword(hash);

		user.setConfirmMoment(new Date(System.currentTimeMillis() - 1000));
		user.setHasConfirmedTerms(true);

		ContentManager cm = new ContentManager();
		cm.setAvatar(user.getAvatar());
		cm.setConfirmMoment(user.getConfirmMoment());
		cm.setEmail(user.getEmail());
		cm.setHasConfirmedTerms(user.getHasConfirmedTerms());
		cm.setName(user.getName());
		cm.setNickname(user.getNickname());
		cm.setPhone(user.getPhone());
		cm.setSurname(user.getSurname());
		cm.setUserAccount(user.getUserAccount());
		cm.setVersion(user.getVersion());
		cm.setId(user.getId());

		result = this.save(cm);
		return result;
	}

	public ContentManager findOne(int contentManagerId) {
		Assert.notNull(contentManagerId);

		ContentManager contentManager;

		contentManager = this.contentManagerRepository.findOne(contentManagerId);

		return contentManager;
	}

	public Collection<ContentManager> findAll() {
		Collection<ContentManager> contentManagers;

		contentManagers = this.contentManagerRepository.findAll();

		return contentManagers;
	}

	public void delete(ContentManager contentManager) {
		Assert.notNull(contentManager);

		this.contentManagerRepository.delete(contentManager);
	}

}
