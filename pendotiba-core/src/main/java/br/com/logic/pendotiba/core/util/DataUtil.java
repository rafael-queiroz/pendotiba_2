package br.com.logic.pendotiba.core.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DataUtil {
	
	static ZoneId fusoHorarioDaqui = ZoneId.systemDefault();
	static ZoneId utc = ZoneId.of("Z");
	static ZoneId utcMais3 = ZoneId.of("+03:00");
	static ZoneId fusoDeSaoPaulo = ZoneId.of("America/Sao_Paulo");
	
	static final SimpleDateFormat DATE_INVERSE_HIFEM_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
	
	
	 public static String getHora(Date d) {
	    	if(d != null) 
	    		return new SimpleDateFormat("HH").format(d);
	    	return null;
	    }
	 
    public static String getHoraMinuto(Date d) {
    	if(d != null) 
    		return new SimpleDateFormat("HH:mm").format(d);
    	return null;
    }
    
    public static String getHoraMinutoSegundo(Date d) {
    	if(d != null) 
    		return new SimpleDateFormat("HH:mm:ss").format(d);
    	return null;
	}
	
    
    public static String getDataStringDDMMYYYY(Date d) {
    	if(d != null) 
    		return new SimpleDateFormat("dd/MM/yyyy").format(d);
    	return null;
    }
    
    public static String getDataStringYYYYMMDD(Date d) {
    	if(d != null) 
    		return new SimpleDateFormat("yyyyMMdd").format(d);
    	return null;
    }
    
    public static String getDataStringYYYYMMDDHHMM(Date d) {
    	if(d != null) 
    		return new SimpleDateFormat("yyyyMMddHHmm").format(d);
    	return null;
    }


	public static Date getTime(String data, String hora) {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		if(data != null && hora != null) {
			try {
				data = data.replaceAll("-", "");
				hora = hora.replaceAll("-", "");
				return formato.parse(data.substring(6, 8).concat("/").concat(data.substring(4, 6)).concat("/").concat(data.substring(0, 4)).concat(" ").concat(hora.substring(0, 2)).concat(":").concat(hora.substring(3, 5)));
			} catch (ParseException e) {
				return null;
			}
		}
		return null;
	}
	
	
	public static Date getTime(Date data, String hora) {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		if(data != null && hora != null && !hora.equals("null")) {
			try {
				String dataStr = getDataStringYYYYMMDD(data);
				hora = hora.replaceAll("-", "");
				return formato.parse(dataStr.substring(6, 8).concat("/").concat(dataStr.substring(4, 6)).concat("/").concat(dataStr.substring(0, 4)).concat(" ").concat(hora.substring(0, 2)).concat(":").concat(hora.substring(3, 5)));
			} catch (ParseException e) {
				return null;
			}
		}
		return null;
	}
	
	
	public static Date getDate(Date data1, Date data2) {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		if(data1 != null && data2 != null) {
			try {
				String dataStr = getDataStringYYYYMMDD(data1);
				String horaStr = getHoraMinuto(data2);
				return formato.parse(dataStr.substring(6, 8).concat("/").concat(dataStr.substring(4, 6)).concat("/").concat(dataStr.substring(0, 4)).concat(" ").concat(horaStr.substring(0, 2)).concat(":").concat(horaStr.substring(3, 5)));
			} catch (ParseException e) {
				return null;
			}
		}
		return null;
	}
	

	//yyyyMMdd return yyyy/MM/dd
	public static Date getDateDDMMYYYY(String d) {
		try {
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			d = d.replaceAll("-", "");
			return formato.parse(d.substring(6, 8).concat("/").concat(d.substring(4, 6)).concat("/").concat(d.substring(0, 4)));
		} catch (ParseException e) {
			return null;
		}
	}
	
	
	//yyyyMMdd return dd/MM/yyyy
	public static Date getDateDDMMYYYYReturnDDMMYYYY(String d) {
		try {
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			d = d.replaceAll("/", "");
			return formato.parse(d.substring(0, 2).concat("/").concat(d.substring(2, 4)).concat("/").concat(d.substring(4, 8)));
		} catch (ParseException e) {
			return null;
		}
	}
	
	//yyyyMMdd
	public static Date getDateDDMMYY(String d) {
		try {
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yy");
			d = d.replaceAll("/", "");
			return formato.parse(d.substring(0, 2).concat("/").concat(d.substring(2, 4)).concat("/").concat(d.substring(4, 6)));
		} catch (ParseException e) {
			return null;
		}
	}
    

	public static Date startOfDay(Date date) {
		Calendar dCal = Calendar.getInstance();
		dCal.setTime(date);
		dCal.set(Calendar.HOUR_OF_DAY, 0);
		dCal.set(Calendar.MINUTE, 0);
		dCal.set(Calendar.SECOND, 0);
		dCal.set(Calendar.MILLISECOND, 0);

		return dCal.getTime();
	}
	

	public static Date endOfDay(Date date) {
		Calendar dCal = Calendar.getInstance();
		dCal.setTime(date);
		dCal.set(Calendar.HOUR_OF_DAY, 23);
		dCal.set(Calendar.MINUTE, 59);
		dCal.set(Calendar.SECOND, 59);
		dCal.set(Calendar.MILLISECOND, 999);
		return dCal.getTime();
	}


	public static Date getTime(String hora) {
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm");
		if(hora != null) {
			try {
				hora = hora.replaceAll("-", "");
				return formato.parse((hora.substring(0, 2)).concat(":").concat(hora.substring(3, 5)));
			} catch (ParseException e) {
				return null;
			}
		}
		return null;
	}
	
	
	public static Date getDataAnteriorEmDias(Date d, int dias) {
		if (d == null) 
			return null;

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(d);
		calendar.add(Calendar.DAY_OF_YEAR, -dias);
		return calendar.getTime();
	}
	
	public static Date getDataPosteriorEmDias(Date d, int dias) {
		if (d == null) 
			return null;

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(d);
		calendar.add(Calendar.DAY_OF_YEAR, +dias);
		return calendar.getTime();
	}
	
	
	public static Boolean ehDomingo(Date data) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);
		return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
	}
	
	
	public static Boolean ehSabado(Date data) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);
		return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY;
	}
	
	
	public static Boolean ehHoje(Date data) {
		String data1 = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
		String data2 = new SimpleDateFormat("dd/MM/yyyy").format(data);
		return data1.equals(data2);
	}
	
	
	public static String toStringTimestampFormatadaParaSQL(Date data) {
		return DATE_INVERSE_HIFEM_FORMATTER.format(data);
	}
	
	
	public static Date getDataAppAbastecimentoOdometroRoleta() {
		DateFormat dateFormat = new SimpleDateFormat("HH");
        int horaMaxima = 8;
        int horaAtual = new Integer(dateFormat.format(new Date())).intValue();
        if (horaAtual >= horaMaxima) 
            return new Date();
        else
        	return getDataAnteriorEmDias(new Date(), 1);
	}
	
	public static Date getDataAppMapaBombaAbastecimento() {
		DateFormat dateFormat = new SimpleDateFormat("HH");
        int horaMaxima = 8;
        int horaAtual = new Integer(dateFormat.format(new Date())).intValue();
        if (horaAtual < horaMaxima) 
            return new Date();
        else
        	return getDataAnteriorEmDias(new Date(), 1);
	}
	
	
	public static Date getPrimeiroDiaDoMes(Date d) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(d);
		calendar.set(Calendar.DAY_OF_MONTH,1);
		return calendar.getTime();
	}
	

	public static int getMes(Date d) {
		GregorianCalendar dataCal = new GregorianCalendar();
		dataCal.setTime(d);
		return dataCal.get(Calendar.MONTH);
	}

}
