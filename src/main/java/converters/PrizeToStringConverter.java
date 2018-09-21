
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Prize;

@Component
@Transactional
public class PrizeToStringConverter implements Converter<Prize, String> {

	@Override
	public String convert( Prize o) {
		String result;

		if (o == null)
			result = null;
		else
			result = String.valueOf(o.getId());

		return result;
	}

}
