
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Defense;

@Component
@Transactional
public class DefenseToStringConverter implements Converter<Defense, String> {

	@Override
	public String convert( Defense o) {
		String result;

		if (o == null)
			result = null;
		else
			result = String.valueOf(o.getId());

		return result;
	}

}
