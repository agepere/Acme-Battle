
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.TroopRepository;
import domain.Troop;

@Component
@Transactional
public class StringToTroopConverter implements Converter<String, Troop> {

	@Autowired
	TroopRepository	repository;


	@Override
	public Troop convert(String text) {
		Troop result;
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
