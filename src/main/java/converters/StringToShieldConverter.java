
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.ShieldRepository;
import domain.Shield;

@Component
@Transactional
public class StringToShieldConverter implements Converter<String, Shield> {

	@Autowired
	ShieldRepository	repository;


	@Override
	public Shield convert(String text) {
		Shield result;
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
