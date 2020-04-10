

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;

import javax.xml.crypto.dsig.Transform;


public class Fecha implements Cloneable {

	public static final double milisegundosDia = 86400000.0;

	public static final int ORDEN_FECHA = 0;
	public static final int ORDEN_FECHA_DESC = 1;

	public static final Fecha fInicioMesActual = getFechaSistema().getInicioMes();
	public static final Fecha fInicioAnyoActual = fInicioMesActual.getInicioAnyo();
	/**
	 * Constante en desuso. Utilizar {@link Fecha#fInicioAnyoActual}
	 */
	@Deprecated
	public static final Fecha fInicioAñoActual = fInicioAnyoActual;

	private static String formatoFecha;

	public static Fecha getFechaNull() {
		return new Fecha(1900, 1, 1);
	}

	public static Fecha getFechaFinalDLosTiempos() {
		return new Fecha(2999, 12, 31);
	}

	/**
	 * Devuelve la fecha del día sin horas, minutos ni segundos.
	 */
	public static Fecha getFechaSistema() {
		Fecha fecha = new Fecha();
		int ann = fecha.getYear();
		int mes = fecha.getMes();
		int dia = fecha.getDia();
		return new Fecha(ann, mes, dia);
	}

	public static Fecha getInstanceFecha(String _fecha) throws ParseException {
		return new Fecha(_fecha);
	}

	private Calendar calendar = null;
	private int[] dias_mes = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	// private static final String formatoFechaOracle = "dd/MM/yyyy";
	public static final String formatoFechaInvertida = "yyyyMMdd";
	public static final String formatoFechaInvertidaConBarras = "yyyy/MM/dd";
	private static final String[] meses_romanos = { "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII" };

	// Castellano
//	private static final String[] mesesEspa = { Idioma.t("__NTJ[17926]", "Enero"), Idioma.t("__NTJ[17927]", "Febrero"), Idioma.t("__NTJ[17928]", "Marzo"), Idioma.t("__NTJ[17929]", "Abril"), Idioma.t("__NTJ[17930]", "Mayo"), Idioma.t("__NTJ[17931]", "Junio"), Idioma.t("__NTJ[17932]", "Julio"),
//			Idioma.t("__NTJ[17933]", "Agosto"), Idioma.t("__NTJ[17934]", "Septiembre"), Idioma.t("__NTJ[17935]", "Octubre"), Idioma.t("__NTJ[17936]", "Noviembre"), Idioma.t("__NTJ[17985]", "Diciembre") };
//	private static final String[] diasEspa = { Idioma.t("__NTJ[20998]", "Uno"), Idioma.t("__NTJ[20999]", "Dos"), Idioma.t("__NTJ[21000]", "Tres"), Idioma.t("__NTJ[21001]", "Cuatro"), Idioma.t("__NTJ[21002]", "Cinco"), Idioma.t("__NTJ[21003]", "Seis"), Idioma.t("__NTJ[21004]", "Siete"),
//			Idioma.t("__NTJ[21005]", "Ocho"), Idioma.t("__NTJ[21006]", "Nueve"), Idioma.t("__NTJ[21007]", "Diez"), Idioma.t("__NTJ[21008]", "Once"), Idioma.t("__NTJ[21009]", "Doce"), Idioma.t("__NTJ[21010]", "Trece"), Idioma.t("__NTJ[21011]", "Catorce"), Idioma.t("__NTJ[21012]", "Quince"),
//			Idioma.t("__NTJ[21013]", "Dieciséis"), Idioma.t("__NTJ[21014]", "Diecisiete"), Idioma.t("__NTJ[21015]", "Dieciocho"), Idioma.t("__NTJ[21016]", "Diecinueve"), Idioma.t("__NTJ[21017]", "Veinte"), Idioma.t("__NTJ[21018]", "Veintiuno"), Idioma.t("__NTJ[21019]", "Veintidós"),
//			Idioma.t("__NTJ[21020]", "Veintitrés"), Idioma.t("__NTJ[21021]", "Veinticuatro"), Idioma.t("__NTJ[21022]", "Veinticinco"), Idioma.t("__NTJ[21023]", "Veintiséis"), Idioma.t("__NTJ[21024]", "Veintisiete"), Idioma.t("__NTJ[21025]", "Veintiocho"), Idioma.t("__NTJ[21026]", "Veintinueve"),
//			Idioma.t("__NTJ[21027]", "Treinta"), Idioma.t("__NTJ[21028]", "Treinta y uno") };

//	private static final String[] diasSemanaEspa = { Idioma.t("__NTJ[17950]", "Lunes"), Idioma.t("__NTJ[17951]", "Martes"), Idioma.t("__NTJ[17952]", "Miércoles"), Idioma.t("__NTJ[17953]", "Jueves"), Idioma.t("__NTJ[17954]", "Viernes"), Idioma.t("__NTJ[17955]", "Sábado"),
//			Idioma.t("__NTJ[17949]", "Domingo") };
	// Catalán
	private static final String[] mesesCata = { "Gener", "Febrer", "Març", "Abril", "Maig", "Juny", "Juliol", "Agost", "Setembre", "Octubre", "Novembre", "Desembre" };
	private static final String[] diasCata = { "Un", "Dos", "Tres", "Quatre", "Cinc", "Sis", "Set", "Vuit", "Nou", "Deu", "Onze", "Dotze", "Tretze", "Catorze", "Quinze", "Setze", "Disset", "Divuit", "Dinou", "Vint", "Vint-i-u", "Vint-i-dos", "Vint-i-tres", "Vint-i-quatre", "Vint-i-cinc",
			"Vint-i-sis", "Vint-i-set", "Vint-i-vuit", "Vint-i-nou", "Trenta", "Trenta-u" };
	private static final String[] diasSemanaCata = { "Dilluns", "Dimarts", "Dimecres", "Dijous", "Divendres", "Dissabte", "Diumenge" };

	private static final Hashtable<String, String[]> mesesIdioma = new Hashtable<String, String[]>();
	private static final Hashtable<String, String[]> diasIdioma = new Hashtable<String, String[]>();
	private static final Hashtable<String, String[]> diasSemanaIdioma = new Hashtable<String, String[]>();
	static {
//		mesesIdioma.put(Idioma.IDIOMA_ESPANYOL, mesesEspa);
//		mesesIdioma.put(Idioma.IDIOMA_CATALAN, mesesCata);
//
//		diasIdioma.put(Idioma.IDIOMA_ESPANYOL, diasEspa);
//		diasIdioma.put(Idioma.IDIOMA_CATALAN, diasCata);
//
//		diasSemanaIdioma.put(Idioma.IDIOMA_ESPANYOL, diasSemanaEspa);
//		diasSemanaIdioma.put(Idioma.IDIOMA_CATALAN, diasSemanaCata);
	}

	public Fecha() {
		// Construye un calendario con la fecha actual
		calendar = new GregorianCalendar();
	}

	public Fecha(Date date) {
		calendar = new GregorianCalendar();
		if (date == null)
			setTime(1900, 01, 01);
		else
			setTime(date);
	}

	public Fecha(Fecha fecha) {
		calendar = new GregorianCalendar();
		setTime(fecha.getDate());
	}

	public Fecha(int year, int month, int day) {
		calendar = new GregorianCalendar(year, (month - 1), day);
	}

	public Fecha(int year, int month, int day, int hour, int minute) {
		calendar = new GregorianCalendar(year, (month - 1), day, hour, minute);
	}

	public Fecha(int year, int month, int day, int hour, int minute, int second) {
		calendar = new GregorianCalendar(year, (month - 1), day, hour, minute, second);
	}

	public Fecha(String fecha) {
		this(fecha, getFormatoFecha());
	}

	public Fecha(String fecha, String formato) {
		try {
			Date fech;
			SimpleDateFormat sdf = new SimpleDateFormat(formato);
			calendar = new GregorianCalendar();
			if (fecha == null) {
				fecha = "";
			}
			fech = sdf.parse(fecha);
			setTime(fech);

			if (!fecha.equals(toChar(formato))) {
				setTime(1900, 01, 01);
			}

		} catch (ParseException pe) {
			if (fecha != null && fecha.startsWith("TIME_")) {
				try {
					long datemiles = Long.parseLong(fecha.substring(5));
					Date date = new Date(datemiles);
					calendar = new GregorianCalendar();
					setTime(date);
				} catch (Exception e) {
					calendar = new GregorianCalendar();
					setTime(1900, 01, 01);
				}
			} else if (fecha != null && fecha.length() == 4) {
				int year = Integer.parseInt(fecha);
				if (year > 1900 && year < 2200) {
					calendar = new GregorianCalendar();
					setTime(year, 01, 01);
				} else {
					calendar = new GregorianCalendar();
					setTime(1900, 01, 01);
				}

			} else {
				calendar = new GregorianCalendar();
				setTime(1900, 01, 01);
			}
		}
	}

	public Fecha(int year, int month) {
		this(year == 0 ? Fecha.getFechaNull() : month == 0 ? Fecha.getFechaNull() : new Fecha(year, month, 1).getFinMes());
	}

	@Deprecated
	public void addAños(int numeroAnn) {
		addYears(numeroAnn);
	}

	public void addYears(int numberOffYearsToAdd) {
		calendar.add(Calendar.YEAR, numberOffYearsToAdd);
	}

	public static Fecha addYears(Fecha fecha, int numberOffYearsToAdd) {
		Fecha aux = (Fecha) fecha.clone();
		aux.addYears(numberOffYearsToAdd);
		return aux;
	}

	public void addDias(int numeroDias) {
		calendar.add(Calendar.DATE, numeroDias);
	}

	public void addMinutos(int numeroMinutos) {
		calendar.add(Calendar.MINUTE, numeroMinutos);
	}

	public static Fecha addDias(Fecha fecha, int nDias) {
		Fecha aux = (Fecha) fecha.clone();
		aux.addDias(nDias);
		return aux;
	}

	public void addDiasHabiles(int numeroDias) {
		for (int aux = 0; aux < numeroDias; aux++) {
			addDias(1);
			if (isSabado()) {
				addDias(1);
			}
			if (isDomingo()) {
				addDias(1);
			}
		}
	}

	public static Fecha addDiasHabiles(Fecha fecha, int numeroDias) {
		Fecha auxFecha = (Fecha) fecha.clone();
		for (int aux = 0; aux < numeroDias; aux++) {
			auxFecha.addDias(1);
			if (auxFecha.isSabado()) {
				auxFecha.addDias(1);
			}
			if (auxFecha.isDomingo()) {
				auxFecha.addDias(1);
			}
		}
		return auxFecha;
	}

	public void addMeses(int numeroMeses) {
		calendar.add(Calendar.MONTH, numeroMeses);
	}

	public static Fecha addMeses(Fecha fecha, int numeroMeses) {
		Fecha aux = (Fecha) fecha.clone();
		aux.addMeses(numeroMeses);
		return aux;
	}

	public Fecha siguienteFinMes() {
		Fecha f = getInicioMes();
		f.addMeses(1);
		f = f.getFinMes();
		return f;
	}

	@Override
	public Object clone() {
		Fecha fecha;
		fecha = new Fecha(this.getDate());
		return fecha;
	}

	public long compare(Fecha fecha) {
		long fecha1 = this.getDate().getTime();
		long fecha2 = fecha.getDate().getTime();
		return (fecha1 - fecha2);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof Fecha) {
			Fecha aux = (Fecha) obj;
			return toString().equals(aux.toString());
		}
		return false;
	}

	public boolean isMismaFecha(Fecha fecha) {
		if (this == fecha)
			return true;
		if (this.getDia() != fecha.getDia()) {
			return false;
		}
		if (this.getMes() != fecha.getMes()) {
			return false;
		}
		if (this.getYear() != fecha.getYear()) {
			return false;
		}
		return true;
	}

	public Date getDate() {
		return calendar.getTime();
	}

	public long getDateInMillis() {
		return calendar.getTimeInMillis();
	}

	public int getDia() {
		return calendar.get(Calendar.DATE);
	}

	public int getDiasDiferencia(Fecha fecha) {
		double fecha1 = this.getDate().getTime();
		double fecha2 = fecha.getDate().getTime();
		double dif = (fecha1 - fecha2) / milisegundosDia;
		int dias = (int) Math.round(dif);
		return dias;
	}

	public int getDiaAnyo() {
		return getDiasDiferencia(getInicioAnyo());
	}

	public int getDiasHabilesDiferencia(Fecha fecha) {
		int difDias = 0;
		if (this.compare(fecha) == 0)
			return difDias;
		Fecha fechaOrigen;
		Fecha fechaDestino;
		if (this.isMenor(fecha)) {
			fechaOrigen = (Fecha) this.clone();
			fechaDestino = fecha;
		} else {
			fechaOrigen = fecha;
			fechaDestino = this;
		}
		do {
			fechaOrigen.addDias(1);
			if (!fechaOrigen.isDomingo())
				difDias++;
		} while (fechaOrigen.isMenor(fechaDestino));
		return difDias;
	}

	public int getMesesDiferencia(Fecha fecha) {
		int anyos = this.getYear() - fecha.getYear();
		int meses = this.getMes() - fecha.getMes();
		boolean finMesHasta = this.isFinMes();
		boolean finMesDesde = fecha.isFinMes();
		boolean diaUnoDesde = fecha.getDia() == 1;
		int exceso = 0;
		if (finMesHasta && diaUnoDesde) {
			exceso = 1;
		} else if (finMesDesde && finMesHasta) {
			exceso = 0;
		} else if (fecha.getDia() > this.getDia()) {
			exceso = -1;
		}
		int nMeses = (anyos * 12) + meses + exceso;
		return nMeses;
	}

	public boolean isLunes() {
		return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY;
	}

	public boolean isMartes() {
		return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY;
	}

	public boolean isMiercoles() {
		return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY;
	}

	public boolean isJueves() {
		return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY;
	}

	public boolean isViernes() {
		return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY;
	}

	public boolean isSabado() {
		return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY;
	}

	public boolean isDomingo() {
		return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
	}

	public int getDiaSemana() {
		int dayInt = calendar.get(Calendar.DAY_OF_WEEK);
		switch (dayInt) {
		case Calendar.MONDAY:
			return 0;
		case Calendar.TUESDAY:
			return 1;
		case Calendar.WEDNESDAY:
			return 2;
		case Calendar.THURSDAY:
			return 3;
		case Calendar.FRIDAY:
			return 4;
		case Calendar.SATURDAY:
			return 5;
		case Calendar.SUNDAY:
			return 6;
		}
		return -1;
	}

	public int getSemanaAnyo() {
//		setDiasMinimosEnPrimeraSemana(1);
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}

	public int getSemanaMes() {
		setDiasMinimosEnPrimeraSemana(1);
		return calendar.get(Calendar.WEEK_OF_MONTH);
	}

	public int getSemanasMes() {
		return getFinMes().getSemanaMes();
	}

	public int getDiasMes() {
		int mes = getMes();
		int ann = getYear();
		int dia = dias_mes[mes - 1];
		if (mes == 2 && ann % 4 == 0) {
			dia++;
		}
		return dia;
	}

	public Fecha getFinMes() {
		int mes = getMes();
		int ann = getYear();
		int dia = dias_mes[mes - 1];
		if (mes == 2 && ann % 4 == 0) {
			dia++;
		}
		return new Fecha(ann, mes, dia);
	}

	public String getFormatoCorto() {
		return this.toChar(getFormatoFecha().replaceAll("yyyy", "yy"));
	}

	public String getFormatoInvertido() {
		return this.toChar(formatoFechaInvertida);
	}

	public static String getFormatoFecha() {
		String formato = formatoFecha;
		if (formato.length() == 0) {
			formato = "";
		}
		return formato;
	}

	@Deprecated
	public static String getFormatoFechaOracle() {
		return getFormatoFecha();
	}

	public int getHora() {
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	@Deprecated
	public Fecha getInicioAño() {
		return getInicioAnyo();
	}

	@Deprecated
	public Fecha getFinalAño() {
		return getFinalAnyo();
	}

	public Fecha getInicioAnyo() {
		int ann = getYear();
		int mes = 1;
		int dia = 1;
		return new Fecha(ann, mes, dia);
	}

	public Fecha getFinalAnyo() {
		int ann = getYear();
		int mes = 12;
		int dia = 31;
		return new Fecha(ann, mes, dia);
	}

	public Fecha getInicioMes() {
		int mes = getMes();
		int ann = getYear();
		int dia = 1;
		return new Fecha(ann, mes, dia);
	}

	public int getMes() {
		return (calendar.get(Calendar.MONTH)) + 1;
	}

	public String getMes(int mes) {
		return getNombreMes(mes);
	}

	public String getNombreMes() {
		return getStringMes(getMes());
	}

	public static String getNombreMes(int mes) {
		return getNombreMes(mes);
	}

	public static String getStringMes(int mes) {
		return getNombreMes(mes);
	}

	public int getMinuto() {
		return calendar.get(Calendar.MINUTE);
	}

	public int getSegundo() {
		return calendar.get(Calendar.SECOND);
	}

	/**
	 * Devuelve la diferencia entre el numero 99999999 y el número formado por
	 * la AAAAMMDD. (Se utiliza para ordenación descendente);
	 * 
	 * @return
	 */
	public String getValorInverso() {
		long fecha = Long.parseLong(this.toChar("yyyyMMdd"));
		return Long.toString(99999999 - fecha);
	}

	public int getYear() {
		return calendar.get(Calendar.YEAR);
	}

	public boolean isFinMes() {
		return getFinMes().getDia() == this.getDia();
	}

	public boolean isHoy() {
		return toChar(getFormatoFecha()).equals(getFechaSistema().toChar());
	}

	public boolean isMayorIgual(Fecha fecha) {
		return (compare(fecha) >= 0);
	}

	public boolean isMayor(Fecha fecha) {
		return (compare(fecha) > 0);
	}

	public boolean isMayorIgualQueHoy() {
		return isMayorIgual(getFechaSistema());
	}

	public boolean isMenorQueHoy() {
		return isMenor(getFechaSistema());
	}

	public boolean isMenorIgualQueHoy() {
		return isMenorIgual(getFechaSistema());
	}

	public boolean isMayorQueHoy() {
		return isMayor(getFechaSistema());
	}

	public boolean isMenorIgual(Fecha fecha) {
		return (compare(fecha) <= 0);
	}

	public boolean isMenor(Fecha fecha) {
		return (compare(fecha) < 0);
	}

	public boolean isNull() {
		return toChar().equals(getFechaNull().toChar());
	}

	public boolean isFinalDLosTiempos() {
		String aux = toChar();
		if (aux.equals((new Fecha(2099, 12, 31)).toChar())) {
			return true;
		}
		return toChar().equals(getFechaFinalDLosTiempos().toChar());
	}

	public Date getDuracion(Fecha fecha) {
		long fecha1 = this.getDate().getTime();
		long fecha2 = fecha.getDate().getTime();
		long duracion = Math.abs(fecha1 - fecha2);
		return new Date(duracion);
	}

	public void setYear(int year) {
		calendar.set(Calendar.YEAR, year);
	}

	public void setHora(int hour) {
		calendar.set(Calendar.HOUR_OF_DAY, hour);
	}

	public void setMiliSegundo(int milliSecond) {
		calendar.set(Calendar.MILLISECOND, milliSecond);
	}

	public void setMinuto(int minute) {
		calendar.set(Calendar.MINUTE, minute);
	}

	public void setSegundo(int second) {
		calendar.set(Calendar.SECOND, second);
	}

	public void setTime(Date date) {
		calendar.setTime(date);
	}

	public void setTime(int year, int month, int day) {
		calendar.set(year, (month - 1), day);
	}

	public void setTime(int year, int month, int day, int hour, int minute) {
		calendar.set(year, (month - 1), day, hour, minute);
	}

	public void setTime(int year, int month, int day, int hour, int minute, int second) {
		calendar.set(year, (month - 1), day, hour, minute, second);
	}

	public String toChar() {
		SimpleDateFormat sdf = new SimpleDateFormat(getFormatoFecha());
		return sdf.format(getDate());
	}

	public String toChar(String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(getDate());
	}

	@Override
	public String toString() {
		if (isNull())
			return "";
		return toChar();
	}

	public String getKeyOrder(int orden) {
		if (orden == ORDEN_FECHA)
			return toChar("yyyyMMdd");
		if (orden == ORDEN_FECHA_DESC)
			return getValorInverso();
		return toChar("yyyyMMdd");
	}

	public int getTrimestre() {
		int mes = getMes();
		return mes < 4 ? 1 : mes < 7 ? 2 : mes < 10 ? 3 : 4;
	}

	public Fecha getInicioTrimestre() {
		int numTrimestre = getTrimestre();
		return getInicioTrimestre(numTrimestre, getYear());
	}

	public Fecha getFinTrimestre() {
		int numTrimestre = getTrimestre();
		return getFinTrimestre(numTrimestre, getYear());
	}

	public boolean isFinTrimestre() {
		Fecha fecha = getFinTrimestre();
		return fecha.getDia() == this.getDia() && fecha.getMes() == this.getMes();
	}

	public static Fecha getInicioTrimestre(int numTrimestre, int year) {
		if (numTrimestre < 0 || numTrimestre > 4)
			throw new RuntimeException("Número de Trimestre desconocido");
		int dia = 1;
		int mes = (numTrimestre * 3) - 2;
		Fecha fecha = new Fecha(year, mes, dia);
		return fecha;
	}

	public static Fecha getFinTrimestre(int numTrimestre, int year) {
		if (numTrimestre < 0 || numTrimestre > 4)
			throw new RuntimeException("Número de Trimestre desconocido");
		int mes = numTrimestre * 3;
		Fecha fecha = new Fecha(year, mes);
		return fecha.getFinMes();
	}

	public static int getYearToday() {
		return new Fecha().getYear();
	}

	public static int getNumeroMesRomano(String mes) {
		for (int i = 0; i < meses_romanos.length; i++) {
			if (meses_romanos[i].equals(mes))
				return i + 1;
		}
		return 0;
	}

	public static String getStringMesRomano(int mes) {
		if (mes < 1 || mes > 12)
			return "";
		return meses_romanos[mes - 1];
	}






	public static Fecha getTomorrow() {
		Fecha tomorrow = getFechaSistema();
		tomorrow.addDias(1);
		return tomorrow;
	}

	public boolean isPrimeroDEnero() {
		return (getDia() + getMes()) == 2;
	}

	public boolean isPrimeroDMes() {
		return (getDia()) == 1;
	}

	public boolean isFinAnyo() {
		return getMes() == 12 && getDia() == 31;
	}

	public boolean isFechaVencimientoCompatible(Fecha fecha) {
		int diasAnyo1 = getDiaAnyo();
		int diasAnyo2 = fecha.getDiaAnyo();
		int diferencia = Math.abs(diasAnyo1 - diasAnyo2);
		return diferencia < 2;
	}

	public int getDiferenciaEnDias(Fecha fecha) {
		return getDiasDiferencia(fecha);
	}

	public static boolean isNull(Fecha fecha) {
		if (fecha == null) {
			return true;
		}
		if (fecha.isNull()) {
			return true;
		}
		return false;
	}

	public void trunc() {
		this.calendar = new GregorianCalendar(getYear(), (getMes() - 1), getDia());
	}

	public Fecha truncNewFecha() {
		Fecha fecha = new Fecha(getYear(), getMes(), getDia());
		return fecha;
	}

	public static final void main(String[] args) {
		Fecha fechaDesde = new Fecha("17/01/2011");
		Fecha fechahasta = new Fecha("29/09/2011");
		int diferencia = fechahasta.getDiasDiferencia(fechaDesde);
		System.out.println(diferencia);
	}


	public static void checkFormato(String formato) {
		synchronized (Fecha.class) {
			String formatoClase = formatoFecha;
			if (formatoClase == null) {
				formatoFecha = formato;
			} else {
				if (!formatoFecha.equalsIgnoreCase(formato)) {
					formatoFecha = "";
				}
			}
		}
	}

	/**
	 * @see Calendar#setMinimalDaysInFirstWeek(int)
	 * @param value
	 */
	private void setDiasMinimosEnPrimeraSemana(int value) {
		calendar.setMinimalDaysInFirstWeek(value);
	}

	/**
	 * Comprueba que el rango de fechas es válido dentro de los límites de la
	 * base de datos.
	 * 
	 * @return
	 */
	public boolean checkRangoDB() {
		// TODO incluir validaciones para otras bases de datos.
		int minYear = -4712; // Año mínimo de ORACLE
		int maxYear = 9999; // Año máxiom de ORACLE
		int year = getYear();

		return year > minYear && year < maxYear;
	}
}


