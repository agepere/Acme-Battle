
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.RequirementRepository;
import domain.Requirement;

@Component
@Transactional
public class StringToRequirementConverter implements Converter<String, Requirement> {

	@Autowired
	RequirementRepository	repository;


	@Override
	public Requirement convert(String text) {
		Requirement result;
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
