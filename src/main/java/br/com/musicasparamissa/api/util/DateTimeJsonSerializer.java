package br.com.musicasparamissa.api.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * serialize Date to Json
 */
public class DateTimeJsonSerializer extends JsonSerializer<Date> {

	@Override
	public void serialize(final Date date, final JsonGenerator gen, final SerializerProvider prov)
			throws IOException, JsonProcessingException {

        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		final String formattedDate = formatter.format(date);
		gen.writeString(formattedDate);
		
	}

}
