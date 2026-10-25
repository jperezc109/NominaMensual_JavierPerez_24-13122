package gt.com.nomina.modelo;
/**
* Empleado por hora: se le paga una tarifa por cada hora
* que trabajo en el mes (las horas pueden llevar decimales).
*/
public class EmpleadoPorHora extends Empleado {
private double pagoPorHora = 0.0;
private double horasTrabajadas = 0.0;
public EmpleadoPorHora(String dpi, String nombre, double pagoPorHora, double horasTrabajadas) {
super(dpi, nombre);
this.setPagoPorHora(pagoPorHora);
this.setHorasTrabajadas(horasTrabajadas);
}
// Pago del mes = pago por hora x horas trabajadas
@Override
public double calcularPago() {
return pagoPorHora * horasTrabajadas;
}
@Override
public String obtenerTipo() {
return "Por hora";
}
public double getPagoPorHora() {
return pagoPorHora;
}
private void setPagoPorHora(double pagoPorHora) {
if (pagoPorHora < 0 || !Double.isFinite(pagoPorHora))
this.pagoPorHora = 0;
else
this.pagoPorHora = pagoPorHora;
}
public double getHorasTrabajadas() {
return horasTrabajadas;
}
private void setHorasTrabajadas(double horasTrabajadas) {
if (horasTrabajadas < 0 || !Double.isFinite(horasTrabajadas))
this.horasTrabajadas = 0;
else
this.horasTrabajadas = horasTrabajadas;
}
}