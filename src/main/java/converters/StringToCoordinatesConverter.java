
package converters;

import java.net.URLDecoder;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Coordinates;

@Component
@Transactional
public class StringToCoordinatesConverter implements Converter<String, Coordinates> {

	@Override
	public Coordinates convert(String text) {
		Coordinates result;
		String parts[];

		if (text == null)
			result = null;
		else
			try {
				parts = text.split("\\|");
				result = new Coordinates();
				result.setX(Integer.valueOf(URLDecoder.decode(parts[0], "UFT-8")));
				result.setY(Integer.valueOf(URLDecoder.decode(parts[1], "UFT-8")));
				result.setZ(Integer.valueOf(URLDecoder.decode(parts[2], "UFT-8")));
			} catch (Throwable oops) {
				throw new RuntimeException(oops);
			}

		return result;
	}
}
