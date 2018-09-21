
package converters;

import java.net.URLEncoder;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Coordinates;

@Component
@Transactional
public class CoordinatesToStringConverter implements Converter<Coordinates, String> {

	@Override
	public String convert( Coordinates coord) {
		String result;
		StringBuilder builder;

		if (coord == null)
			result = null;
		else
			try {
				builder = new StringBuilder();
				builder.append(URLEncoder.encode(Float.toString(coord.getX()), "UTF-8"));
				builder.append("|");
				builder.append(URLEncoder.encode(Float.toString(coord.getY()), "UTF-8"));
				builder.append("|");
				builder.append(URLEncoder.encode(Float.toString(coord.getZ()), "UTF-8"));
				result = builder.toString();
			} catch ( Throwable oops) {
				throw new RuntimeException(oops);
			}
		return result;
	}

}
