
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Banned;

@Component
@Transactional
public class BannedToStringConverter implements Converter<Banned, String> {

	@Override
	public String convert( Banned o) {
		String result;

		if (o == null)
			result = null;
		else
			result = String.valueOf(o.getId());

		return result;
	}

}
