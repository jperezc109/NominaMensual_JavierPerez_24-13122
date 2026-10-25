package gt.com.nomina.modelo;
/**
* Clase base (abstracta) de todos los trabajadores.
* Aqui solo van los datos que tienen en comun el empleado fijo
* y el empleado por hora: el DPI y el nombre.
* No se puede hacer new Empleado(...) porque es abstracta.
*/
public abstract class Empleado {
// Atributos privados, solo se tocan desde esta clase
private String dpi = "";
private String nombre = "";
// Constructor: dpi y nombre son PARAMETROS
public Empleado(String dpi, String nombre) {
this.setDpi(dpi);
this.setNombre(nombre);
}
// Cada hijo dice como se calcula su pago (por eso es abstracto)
public abstract double calcularPago();
// Cada hijo dice que tipo de empleado es, para el reporte
public abstract String obtenerTipo();
public String getDpi() {
return dpi;
}
// Los setters son privados: solo se usan aqui adentro para validar
private void setDpi(String dpi) {
if (esDpiValido(dpi))
this.dpi = dpi;
else
this.dpi = "";
}
public String getNombre() {
return nombre;
}
private void setNombre(String nombre) {
if (esNombreValido(nombre))
this.nombre = nombre.trim();
else
this.nombre = "";
}
// Revisa que el DPI tenga exactamente 13 numeros
public static boolean esDpiValido(String dpiRevisar) {
if (dpiRevisar == null || dpiRevisar.length() != 13)
return false;
for (int posicion = 0; posicion < dpiRevisar.length(); posicion++) {
char caracter = dpiRevisar.charAt(posicion);
if (caracter < '0' || caracter > '9')
return false;
}
return true;
}
// Revisa que el nombre no venga vacio ni con puros espacios
public static boolean esNombreValido(String nombreRevisar) {
return nombreRevisar != null && !nombreRevisar.trim().isEmpty();
}
}
