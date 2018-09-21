
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.PurchaseRepository;
import domain.Purchase;

@Component
@Transactional
public class StringToPurchaseConverter implements Converter<String, Purchase> {

	@Autowired
	PurchaseRepository	repository;


	@Override
	public Purchase convert(String text) {
		Purchase result;
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
