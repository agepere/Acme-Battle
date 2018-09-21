
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Built;

@Component
@Transactional
public class BuiltToStringConverter implements Converter<Built, String> {

	@Override
	public String convert( Built o) {
		String result;

		if (o == null)
			result = null;
		else
			result = String.valueOf(o.getId());

		return result;
	}

}
