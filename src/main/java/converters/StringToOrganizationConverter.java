
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.OrganizationRepository;
import domain.Organization;

@Component
@Transactional
public class StringToOrganizationConverter implements Converter<String, Organization> {

	@Autowired
	OrganizationRepository	repository;


	@Override
	public Organization convert(String text) {
		Organization result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.repository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
