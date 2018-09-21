
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import repositories.BuiltRepository;

import domain.Built;

@Component
@Transactional
public class StringToBuiltConverter implements Converter<String, Built> {

	@Autowired
	BuiltRepository	repository;


	@Override
	public Built convert(String text) {
		Built result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.repository.findOne(id);
		} catch ( Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
