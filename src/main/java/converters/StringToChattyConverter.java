
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.ChattyRepository;
import domain.Chatty;

@Component
@Transactional
public class StringToChattyConverter implements Converter<String, Chatty> {

	@Autowired
	ChattyRepository	repository;


	@Override
	public Chatty convert(String text) {
		Chatty result;
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
