
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.WarehouseRepository;
import domain.Warehouse;

@Component
@Transactional
public class StringToWarehouseConverter implements Converter<String, Warehouse> {

	@Autowired
	WarehouseRepository	repository;


	@Override
	public Warehouse convert(String text) {
		Warehouse result;
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
