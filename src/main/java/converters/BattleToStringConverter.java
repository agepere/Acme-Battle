
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Battle;

@Component
@Transactional
public class BattleToStringConverter implements Converter<Battle, String> {

	@Override
	public String convert( Battle o) {
		String result;

		if (o == null)
			result = null;
		else
			result = String.valueOf(o.getId());

		return result;
	}

}
