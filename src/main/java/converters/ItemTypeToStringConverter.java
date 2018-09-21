
package converters;

import java.net.URLEncoder;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.ItemType;

@Component
@Transactional
public class ItemTypeToStringConverter implements Converter<ItemType, String> {

	@Override
	public String convert( ItemType o) {
		String result;
		StringBuilder builder;
		if (o == null)
			result = null;
		else
			try {
				builder = new StringBuilder();
				builder.append(URLEncoder.encode(o.getDeclaringClass().toString(), "UTF-8"));

				result = builder.toString();
			} catch ( Throwable oops) {
				throw new RuntimeException(oops);
			}

		return result;
	}

}
