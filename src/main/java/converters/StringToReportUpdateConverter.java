
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.ReportUpdateRepository;

import domain.ReportUpdate;

@Component
@Transactional
public class StringToReportUpdateConverter implements Converter<String, ReportUpdate> {

	@Autowired
	ReportUpdateRepository	repository;


	@Override
	public ReportUpdate convert(String text) {
		ReportUpdate result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.repository.findOne(id);
		} catch ( Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
