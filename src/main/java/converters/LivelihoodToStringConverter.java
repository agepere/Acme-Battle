
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Livelihood;

@Component
@Transactional
public class LivelihoodToStringConverter implements Converter<Livelihood, String> {

	@Override
	public String convert( Livelihood o) {
		String result;

		if (o == null)
			result = null;
		else
			result = String.valueOf(o.getId());

		return result;
	}

}
