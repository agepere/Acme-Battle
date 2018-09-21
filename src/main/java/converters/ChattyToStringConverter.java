
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Chatty;

@Component
@Transactional
public class ChattyToStringConverter implements Converter<Chatty, String> {

	@Override
	public String convert( Chatty o) {
		String result;

		if (o == null)
			result = null;
		else
			result = String.valueOf(o.getId());

		return result;
	}

}
