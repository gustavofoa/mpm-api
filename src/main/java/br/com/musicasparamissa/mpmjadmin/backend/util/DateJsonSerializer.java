package br.com.musicasparamissa.mpmjadmin.backend.util;

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
public class DateJsonSerializer extends JsonSerializer<Date> {

	@Override
	public void serialize(final Date date, final JsonGenerator gen, final SerializerProvider prov)
			throws IOException, JsonProcessingException {

        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		final String formattedDate = formatter.format(date);
		gen.writeString(formattedDate);
		
	}

}
