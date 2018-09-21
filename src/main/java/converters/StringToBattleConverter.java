
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.BattleRepository;

import domain.Battle;

@Component
@Transactional
public class StringToBattleConverter implements Converter<String, Battle> {

	@Autowired
	BattleRepository	repository;


	@Override
	public Battle convert(String text) {
		Battle result;
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
