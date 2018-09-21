
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Warehouse;

@Component
@Transactional
public class WarehouseToStringConverter implements Converter<Warehouse, String> {

	@Override
	public String convert( Warehouse o) {
		String result;

		if (o == null)
			result = null;
		else
			result = String.valueOf(o.getId());

		return result;
	}

}
