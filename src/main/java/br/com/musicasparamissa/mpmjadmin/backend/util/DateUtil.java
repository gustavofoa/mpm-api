package br.com.musicasparamissa.mpmjadmin.backend.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	private static SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

	public static String format(Date data) {
		return format.format(data);
	}

	public static LocalDate toLocalDate(Date data) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		return LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DATE));
	}

	public static Date toDate(LocalDate localDate) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, localDate.getYear());
		cal.set(Calendar.MONTH, localDate.getMonthValue()-1);
		cal.set(Calendar.DAY_OF_MONTH, localDate.getDayOfMonth());
		return cal.getTime();
	}

}
