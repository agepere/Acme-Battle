
package services;

import java.util.Collection;
import java.util.Locale;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.LegalTextRepository;
import domain.LegalText;

@Service
@Transactional
public class LegalTextService {

	// Used repository
	@Autowired
	private LegalTextRepository	legalTextRepository;


	//Simple CRUD methods

	public LegalText create() {
		LegalText legalText;

		legalText = new LegalText();

		return legalText;
	}

	public LegalText save(LegalText legalText) {
		Assert.notNull(legalText);
		LegalText legalTextSaved;

		legalTextSaved = this.legalTextRepository.save(legalText);

		return legalTextSaved;
	}

	public LegalText findOne(int legalTextId) {
		Assert.notNull(legalTextId);
		LegalText legalText;

		legalText = this.legalTextRepository.findOne(legalTextId);

		return legalText;
	}

	public Collection<LegalText> findAll() {
		Collection<LegalText> legalTexts;

		legalTexts = this.legalTextRepository.findAll();

		return legalTexts;
	}

	//Other business methods

	public LegalText getTermsAndConditions(Locale locale) {
		LegalText termsAndConditions = null;

		if ("es".equals(locale.getLanguage()))
			termsAndConditions = this.legalTextRepository.getTermsAndConditionsES();
		else if ("en".equals(locale.getLanguage()))
			termsAndConditions = this.legalTextRepository.getTermsAndConditionsEN();

		return termsAndConditions;
	}

	public LegalText getCookies(Locale locale) {
		LegalText cookies = null;

		if ("es".equals(locale.getLanguage()))
			cookies = this.legalTextRepository.getCookiesES();
		else if ("en".equals(locale.getLanguage()))
			cookies = this.legalTextRepository.getCookiesEN();

		return cookies;
	}

}
