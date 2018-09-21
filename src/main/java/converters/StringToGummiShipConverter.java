
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.GummiShipRepository;
import domain.GummiShip;

@Component
@Transactional
public class StringToGummiShipConverter implements Converter<String, GummiShip> {

	@Autowired
	GummiShipRepository	repository;


	@Override
	public GummiShip convert(String text) {
		GummiShip result;
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
