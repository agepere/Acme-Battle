
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.KeybladeWielderRepository;
import domain.KeybladeWielder;


@Component
@Transactional
public class StringToKeybladeWielderConverter implements Converter<String, KeybladeWielder> {

	@Autowired
	KeybladeWielderRepository	repository;


	@Override
	public KeybladeWielder convert(String text) {
		KeybladeWielder result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.repository.findOne(id);
		} catch ( Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
