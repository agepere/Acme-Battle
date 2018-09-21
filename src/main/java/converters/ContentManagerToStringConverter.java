
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.ContentManager;

@Component
@Transactional
public class ContentManagerToStringConverter implements Converter<ContentManager, String> {

	@Override
	public String convert( ContentManager o) {
		String result;

		if (o == null)
			result = null;
		else
			result = String.valueOf(o.getId());

		return result;
	}

}
