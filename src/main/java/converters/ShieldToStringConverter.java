
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Shield;

@Component
@Transactional
public class ShieldToStringConverter implements Converter<Shield, String> {

	@Override
	public String convert( Shield o) {
		String result;

		if (o == null)
			result = null;
		else
			result = String.valueOf(o.getId());

		return result;
	}

}
