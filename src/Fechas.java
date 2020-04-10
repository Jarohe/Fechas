
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Fechas {
	
	private Calendar calendar = null;

	public Fechas() {
		calendar = new GregorianCalendar();
	}
	
	public Fechas(Fechas fecha) {
		calendar = new GregorianCalendar();
		setTime(fecha.getDate());
	}
	
	public Fechas(int year, int mounth, int day) {
		calendar = new GregorianCalendar(year, (mounth -1), day);
	}

	public void setTime(Date date) {
		calendar.setTime(date);
	}

	public Date getDate() {
		return calendar.getTime();
	}
	public boolean isMenor(Fechas fecha) {
		return (compare(fecha) < 0);
	}
	
	public boolean isMenorIgual(Fechas fecha) {
		return (compare(fecha) <= 0);
	}
	
	public boolean isMayor(Fechas fecha) {
		return (compare(fecha) > 0);
	}
	
	public boolean isMayorIgual(Fechas fecha) {
		return (compare(fecha) >= 0);
	}
	
	public long compare(Fechas fecha) {
		long fecha1 = this.getDate().getTime();
		long fecha2 = fecha.getDate().getTime();
		return (fecha1 - fecha2);
	}
	
	public static Fechas getFechaSistema() {
		Fechas fecha = new Fechas();
		int year = fecha.getYear();
		int mounth = fecha.getMounth();
		int day = fecha.getDay();
		return new Fechas(year,mounth,day);
		
	}

	private int getDay() {
		return calendar.get(Calendar.DATE);
	}
	
	private int getMounth() {
		return calendar.get(Calendar.MONTH);
	}

	private int getYear() {
		return calendar.get(Calendar.YEAR);
	}

}
