
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Building;

@Component
@Transactional
public class BuildingToStringConverter implements Converter<Building, String> {

	@Override
	public String convert( Building o) {
		String result;

		if (o == null)
			result = null;
		else
			result = String.valueOf(o.getId());

		return result;
	}

}
