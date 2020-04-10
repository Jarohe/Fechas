
public class Invocador {

	public static void main(String[] args) {
		
		//19880601
		
		Fechas fechaActual = new Fechas().getFechaSistema();
		Fechas fechaEfecto = new Fechas(1988, 06, 01);
		
		System.out.println("fechaActual "+fechaActual.getDate());
		System.out.println("fechaEfecto "+fechaEfecto.getDate());
		
		boolean menor = fechaEfecto.isMenor(fechaActual);
		System.out.println(menor);

	}

}
