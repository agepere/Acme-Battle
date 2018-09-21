
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Purchase;

@Component
@Transactional
public class PurchaseToStringConverter implements Converter<Purchase, String> {

	@Override
	public String convert( Purchase o) {
		String result;

		if (o == null)
			result = null;
		else
			result = String.valueOf(o.getId());

		return result;
	}

}
