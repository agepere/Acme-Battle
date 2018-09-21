
package converters;

import java.net.URLEncoder;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.InvitationStatus;

@Component
@Transactional
public class InvitationStatusToStringConverter implements Converter<InvitationStatus, String> {

	@Override
	public String convert( InvitationStatus inv) {
		String result;
		StringBuilder builder;

		if (inv == null)
			result = null;
		else
			try {
				builder = new StringBuilder();
				builder.append(URLEncoder.encode(inv.getDeclaringClass().toString(), "UTF-8"));

				result = builder.toString();
			} catch ( Throwable oops) {
				throw new RuntimeException(oops);
			}
		return result;
	}

}
