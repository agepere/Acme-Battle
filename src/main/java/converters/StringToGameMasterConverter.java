
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.GameMasterRepository;
import domain.GameMaster;

@Component
@Transactional
public class StringToGameMasterConverter implements Converter<String, GameMaster> {

	@Autowired
	GameMasterRepository	repository;


	@Override
	public GameMaster convert(String text) {
		GameMaster result;
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
