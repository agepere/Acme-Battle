
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.GameMaster;

@Component
@Transactional
public class GameMasterToStringConverter implements Converter<GameMaster, String> {

	@Override
	public String convert( GameMaster o) {
		String result;

		if (o == null)
			result = null;
		else
			result = String.valueOf(o.getId());

		return result;
	}

}
