
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.RecruiterRepository;
import domain.Recruiter;

@Component
@Transactional
public class StringToRecruiterConverter implements Converter<String, Recruiter> {

	@Autowired
	RecruiterRepository	repository;


	@Override
	public Recruiter convert(String text) {
		Recruiter result;
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
