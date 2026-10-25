package gt.com.nomina.modelo;
/**
* Empleado fijo: gana un sueldo mensual y, si le toca,
* una bonificacion en el mes que se esta trabajando.
*/
public class EmpleadoFijo extends Empleado {
private double sueldoMensual = 0.0;
private double bonificacion = 0.0;
public EmpleadoFijo(String dpi, String nombre, double sueldoMensual, double bonificacion) {
// El DPI y el nombre se los mandamos al papa (Empleado)
super(dpi, nombre);
this.setSueldoMensual(sueldoMensual);
this.setBonificacion(bonificacion);
}
// Pago del mes = sueldo mensual + bonificacion
@Override
public double calcularPago() {
return sueldoMensual + bonificacion;
}
@Override
public String obtenerTipo() {
return "Fijo";
}
public double getSueldoMensual() {
return sueldoMensual;
}
private void setSueldoMensual(double sueldoMensual) {
if (sueldoMensual < 0 || !Double.isFinite(sueldoMensual))
this.sueldoMensual = 0;
else
this.sueldoMensual = sueldoMensual;
}
public double getBonificacion() {
return bonificacion;
}
private void setBonificacion(double bonificacion) {
	if (bonificacion < 0 || !Double.isFinite(bonificacion))
		this.bonificacion = 0;
		else
		this.bonificacion = bonificacion;
		}
		}