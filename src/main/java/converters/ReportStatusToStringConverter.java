
package converters;

import java.net.URLEncoder;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.ReportStatus;

@Component
@Transactional
public class ReportStatusToStringConverter implements Converter<ReportStatus, String> {

	@Override
	public String convert( ReportStatus o) {
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
