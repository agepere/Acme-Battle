
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.ReportUpdate;

@Component
@Transactional
public class ReportUpdateToStringConverter implements Converter<ReportUpdate, String> {

	@Override
	public String convert( ReportUpdate o) {
		String result;

		if (o == null)
			result = null;
		else
			result = String.valueOf(o.getId());

		return result;
	}

}
