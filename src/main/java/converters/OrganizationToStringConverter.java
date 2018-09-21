
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Organization;

@Component
@Transactional
public class OrganizationToStringConverter implements Converter<Organization, String> {

	@Override
	public String convert( Organization o) {
		String result;

		if (o == null)
			result = null;
		else
			result = String.valueOf(o.getId());

		return result;
	}

}
