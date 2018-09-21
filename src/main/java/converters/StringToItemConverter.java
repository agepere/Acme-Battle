
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.ItemRepository;
import domain.Item;

@Component
@Transactional
public class StringToItemConverter implements Converter<String, Item> {

	@Autowired
	ItemRepository	repository;


	@Override
	public Item convert(String text) {
		Item result;
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
