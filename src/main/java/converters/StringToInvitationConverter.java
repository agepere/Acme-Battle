
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.InvitationRepository;
import domain.Invitation;

@Component
@Transactional
public class StringToInvitationConverter implements Converter<String, Invitation> {

	@Autowired
	InvitationRepository	repository;


	@Override
	public Invitation convert(String text) {
		Invitation result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.repository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
