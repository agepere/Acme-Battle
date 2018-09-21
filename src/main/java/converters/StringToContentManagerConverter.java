
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.ContentManagerRepository;
import domain.ContentManager;

@Component
@Transactional
public class StringToContentManagerConverter implements Converter<String, ContentManager> {

	@Autowired
	ContentManagerRepository	repository;


	@Override
	public ContentManager convert(String text) {
		ContentManager result;
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
