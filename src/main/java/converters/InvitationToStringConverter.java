
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Invitation;

@Component
@Transactional
public class InvitationToStringConverter implements Converter<Invitation, String> {

	@Override
	public String convert( Invitation o) {
		String result;

		if (o == null)
			result = null;
		else
			result = String.valueOf(o.getId());

		return result;
	}

}
