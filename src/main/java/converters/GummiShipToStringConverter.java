
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.GummiShip;

@Component
@Transactional
public class GummiShipToStringConverter implements Converter<GummiShip, String> {

	@Override
	public String convert( GummiShip o) {
		String result;

		if (o == null)
			result = null;
		else
			result = String.valueOf(o.getId());

		return result;
	}

}
