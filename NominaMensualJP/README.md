# Nómina mensual en Java

Es un programa de consola para registrar a los empleados de una empresa pequeña y calcular cuánto se les paga en un mes. Hay dos tipos de empleado: fijos (sueldo + bonificación) y por hora (tarifa x horas).

## Qué tiene

- `Empleado`: clase abstracta con DPI y nombre.
- `EmpleadoFijo` y `EmpleadoPorHora`: heredan de `Empleado` y cada uno calcula su pago.
- `Main`: el menú, la captura de datos y el reporte.

Los meses están en un `String[]` y los empleados en un `ArrayList<Empleado>`.

## Cómo correrlo

1. Importar o abrir el proyecto en Eclipse.
2. Abrir `src/gt/com/nomina/aplicacion/Main.java`.
3. Click derecho → Run As → Java Application.

La explicación de los conceptos está en [EXPLICACION.md](EXPLICACION.md).

Javier Rodrigo Pérez Cabrera - 7690-24-13122