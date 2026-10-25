package gt.com.nomina.aplicacion;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

import gt.com.nomina.modelo.Empleado;
import gt.com.nomina.modelo.EmpleadoFijo;
import gt.com.nomina.modelo.EmpleadoPorHora;

/**
 * Practica 11 - Nomina mensual
 * Aqui se maneja el mes, los menus, la captura de datos
 * y el reporte de la nomina.
 */
public class Main {

	// Arreglo simple con los 12 meses. Es fijo, el usuario no los escribe.
	private static String[] meses = {
		"Enero", "Febrero", "Marzo", "Abril",
		"Mayo", "Junio", "Julio", "Agosto",
		"Septiembre", "Octubre", "Noviembre", "Diciembre"
	};

	// Mes con el que se trabaja toda la ejecucion (del 1 al 12)
	private static int numeroMes = 0;

	// Aqui se guardan todos los empleados, fijos y por hora juntos
	private static ArrayList<Empleado> planillaDelMes = new ArrayList<Empleado>();

	private static Scanner entrada = new Scanner(System.in);

	public static void main(String[] args) {

		int opcionMenuIngresada = 0;

		seleccionarMes();

		do {
			limpiarConsolaEclipse();
			mostrarTituloMes();
			System.out.println("1 - Ingreso de nómina");
			System.out.println("2 - Impresión de nómina");
			System.out.println("3 - Salir");
			opcionMenuIngresada = pedirOpcion("Seleccione una opción: ", 1, 3);

			switch (opcionMenuIngresada) {
			case 1:
				ingresoDeNomina();
				break;
			case 2:
				imprimirNomina();
				break;
			}
		} while (opcionMenuIngresada != 3);

		limpiarConsolaEclipse();
		System.out.println("-------- GRACIAS POR UTILIZAR EL SISTEMA DE NÓMINA --------");
		System.out.println("--------------------------- ADIOS --------------------------");

		entrada.close();
	}

	public static void limpiarConsolaEclipse() {
		for (int i = 0; i < 50; i++) {
			System.out.println();
		}
	}

	// Pantalla inicial: se pide el mes una sola vez
	public static void seleccionarMes() {
		limpiarConsolaEclipse();
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("++++++++++++++ CÁLCULO DE NÓMINA +++++++++++++++++");
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++");
		numeroMes = pedirOpcion("Ingrese el mes a trabajar (1-12): ", 1, 12);
	}

	// El nombre del mes SIEMPRE sale de meses[numeroMes - 1]
	public static void mostrarTituloMes() {
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("      NÓMINA DEL MES: " + meses[numeroMes - 1].toUpperCase());
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++");
	}

	// ------------------------- OPCION 1 -------------------------
	public static void ingresoDeNomina() {
		int opcionOtroEmpleado = 0;

		do {
			limpiarConsolaEclipse();
			mostrarTituloMes();
			System.out.println("INGRESO DE EMPLEADO A NÓMINA");
			System.out.println("Trabajadores actuales = " + planillaDelMes.size());
			System.out.println();
			System.out.println("1 - Empleado fijo");
			System.out.println("2 - Empleado por hora");
			int opcionTipoEmpleado = pedirOpcion("Seleccione el tipo de empleado: ", 1, 2);

			limpiarConsolaEclipse();
			mostrarTituloMes();

			// Aqui si se vale el if: solo decide que objeto se va a crear
			Empleado empleadoNuevo;
			if (opcionTipoEmpleado == 1)
				empleadoNuevo = registrarEmpleadoFijo();
			else
				empleadoNuevo = registrarEmpleadoPorHora();

			// Se guarda hasta que todos los datos ya estan bien
			planillaDelMes.add(empleadoNuevo);

			System.out.println();
			System.out.println("Empleado agregado correctamente.");
			System.out.println("Trabajadores actuales = " + planillaDelMes.size());
			System.out.println();
			System.out.println("¿Desea ingresar otro empleado?");
			System.out.println("1 - Sí, ingresar otro empleado");
			System.out.println("2 - Volver al menú principal");
			opcionOtroEmpleado = pedirOpcion("Seleccione una opción: ", 1, 2);

		} while (opcionOtroEmpleado == 1);
	}

	public static EmpleadoFijo registrarEmpleadoFijo() {
		System.out.println("INGRESO DE EMPLEADO FIJO");
		System.out.println();

		String dpiCapturado     = pedirDpi();
		String nombreCapturado  = pedirNombre();
		double sueldoCapturado  = pedirMonto("Sueldo mensual (Q): ");
		double bonoCapturado    = pedirMonto("Bonificación de este mes (Q): ");

		// Los valores capturados se mandan como ARGUMENTOS al constructor
		EmpleadoFijo fijoNuevo = new EmpleadoFijo(dpiCapturado, nombreCapturado, sueldoCapturado, bonoCapturado);

		System.out.println();
		System.out.println("Pago del mes = sueldo mensual + bonificación");
		System.out.println("Pago de " + fijoNuevo.getNombre() + " = Q " + formatoQuetzales(fijoNuevo.calcularPago()));
		return fijoNuevo;
	}

	public static EmpleadoPorHora registrarEmpleadoPorHora() {
		System.out.println("INGRESO DE EMPLEADO POR HORA");
		System.out.println();

		String dpiCapturado     = pedirDpi();
		String nombreCapturado  = pedirNombre();
		double tarifaCapturada  = pedirMonto("Pago por hora (Q): ");
		double horasCapturadas  = pedirMonto("Horas trabajadas este mes: ");

		EmpleadoPorHora porHoraNuevo = new EmpleadoPorHora(dpiCapturado, nombreCapturado, tarifaCapturada, horasCapturadas);

		System.out.println();
		System.out.println("Pago del mes = pago por hora x horas trabajadas");
		System.out.println("Pago de " + porHoraNuevo.getNombre() + " = Q " + formatoQuetzales(porHoraNuevo.calcularPago()));
		return porHoraNuevo;
	}

	// ------------------------- OPCION 2 -------------------------
	public static void imprimirNomina() {
		double totalNomina = 0;

		limpiarConsolaEclipse();
		mostrarTituloMes();

		if (planillaDelMes.isEmpty()) {
			System.out.println("No hay empleados registrados en la nómina de este mes");
		} else {
			System.out.printf("%-15s %-25s %-10s %12s%n", "DPI", "Nombre", "Tipo", "Pago (Q)");
			System.out.println("-------------------------------------------------------------------");

			// Recorrido del ArrayList: cada objeto sabe calcular su propio pago
			for (Empleado trabajador : planillaDelMes) {
				double pagoDelTrabajador = trabajador.calcularPago();
				totalNomina = totalNomina + pagoDelTrabajador;
				System.out.printf(Locale.US, "%-15s %-25s %-10s %12.2f%n",
						trabajador.getDpi(), trabajador.getNombre(), trabajador.obtenerTipo(), pagoDelTrabajador);
			}
		}

		System.out.println();
		System.out.println("Total de trabajadores: " + planillaDelMes.size());
		System.out.println("Total de nómina: Q " + formatoQuetzales(totalNomina));
		System.out.println();
		System.out.print("Presione Enter para volver al menú principal...");
		entrada.nextLine();
	}

	// --------------------- LECTURA Y VALIDACION ---------------------

	// Pide un numero entero entre opcionMinima y opcionMaxima, y no suelta hasta que sea valido
	public static int pedirOpcion(String textoPregunta, int opcionMinima, int opcionMaxima) {
		int opcionValida = 0;
		boolean datoCorrecto = false;

		while (!datoCorrecto) {
			System.out.print(textoPregunta);
			String textoIngresado = entrada.nextLine().trim();
			try {
				opcionValida = Integer.parseInt(textoIngresado);
				if (opcionValida >= opcionMinima && opcionValida <= opcionMaxima)
					datoCorrecto = true;
				else
					System.out.println("  >> Opción inválida, debe ser un número del " + opcionMinima + " al " + opcionMaxima + ".");
			} catch (NumberFormatException error) {
				System.out.println("  >> Eso no es un número entero, intente de nuevo.");
			}
		}
		return opcionValida;
	}

	// Pide montos en quetzales u horas: tiene que ser numero y no negativo
	public static double pedirMonto(String textoPregunta) {
		double montoValido = 0;
		boolean datoCorrecto = false;

		while (!datoCorrecto) {
			System.out.print(textoPregunta);
			String textoIngresado = entrada.nextLine().trim();
			try {
				montoValido = Double.parseDouble(textoIngresado);
				if (!Double.isFinite(montoValido))
					System.out.println("  >> Ese valor no se puede usar, ingrese un número normal.");
				else if (montoValido < 0)
					System.out.println("  >> No se aceptan valores negativos.");
				else
					datoCorrecto = true;
			} catch (NumberFormatException error) {
				System.out.println("  >> Debe ingresar un número (ejemplo: 4000 o 25.50, con punto).");
			}
		}
		return montoValido;
	}

	public static String pedirDpi() {
		String dpiIngresado = "";
		boolean datoCorrecto = false;

		while (!datoCorrecto) {
			System.out.print("DPI: ");
			dpiIngresado = entrada.nextLine().trim();
			if (Empleado.esDpiValido(dpiIngresado))
				datoCorrecto = true;
			else
				System.out.println("  >> El DPI debe tener exactamente 13 dígitos, sin espacios ni guiones.");
		}
		return dpiIngresado;
	}

	public static String pedirNombre() {
		String nombreIngresado = "";
		boolean datoCorrecto = false;

		while (!datoCorrecto) {
			System.out.print("Nombre: ");
			nombreIngresado = entrada.nextLine();
			if (Empleado.esNombreValido(nombreIngresado))
				datoCorrecto = true;
			else
				System.out.println("  >> El nombre no puede ir vacío.");
		}
		return nombreIngresado.trim();
	}

	// Devuelve el monto con 2 decimales, ejemplo 4300.00
	public static String formatoQuetzales(double monto) {
		return String.format(Locale.US, "%.2f", monto);
	}
}