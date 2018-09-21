
package converters;

import java.net.URLEncoder;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Materials;

@Component
@Transactional
public class MaterialsToStringConverter implements Converter<Materials, String> {

	@Override
	public String convert( Materials o) {
		String result;
		StringBuilder builder;
		if (o == null)
			result = null;
		else
			try {
				builder = new StringBuilder();
				builder.append(URLEncoder.encode(Integer.toString(o.getGummiCoal()), "UTF-8"));
				builder.append("|");
				builder.append(URLEncoder.encode(Integer.toString(o.getMunny()), "UTF-8"));
				builder.append("|");
				builder.append(URLEncoder.encode(Integer.toString(o.getMytrhil()), "UTF-8"));
				result = builder.toString();
			} catch ( Throwable oops) {
				throw new RuntimeException(oops);
			}

		return result;
	}

}
