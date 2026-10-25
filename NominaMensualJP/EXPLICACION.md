# Práctica 11 - Nómina mensual

Javier Rodrigo Pérez Cabrera - 7690-24-13122

---

## Cómo está organizado

El proyecto se llama `NominaMensual` y tiene dos paquetes:

- `gt.com.nomina.modelo`: aquí van las clases de los empleados (`Empleado`, `EmpleadoFijo` y `EmpleadoPorHora`).
- `gt.com.nomina.aplicacion`: aquí está `Main`, que es la que maneja los menús y lo que se ve en pantalla.

Lo separé así para que la parte de "datos" quede aparte de la parte de "pantalla". Cada clase está en su propio archivo `.java`.

Como `Main` está en otro paquete, tuve que usar `import` para poder usar las clases de los empleados. También importé `ArrayList`, `Scanner` y `Locale` (este último solo para que los montos salgan con punto decimal, por ejemplo 4300.00).

---

## Clases, objetos e instancias

`Empleado`, `EmpleadoFijo` y `EmpleadoPorHora` son clases, o sea las "plantillas". Cada vez que alguien registra un trabajador se crea un objeto nuevo con `new`, por ejemplo en `registrarEmpleadoFijo()`:

```java
EmpleadoFijo fijoNuevo = new EmpleadoFijo(dpiCapturado, nombreCapturado, sueldoCapturado, bonoCapturado);
```

Ese `new` es la instanciación, y `fijoNuevo` es la instancia (el objeto) de ese empleado. Cada trabajador es un objeto distinto, aunque sean del mismo tipo.

---

## Constructores, parámetros, argumentos y this

Todas las clases de empleados tienen constructor con parámetros y ninguno lleva tipo de retorno.

La diferencia entre parámetro y argumento la veo así en `EmpleadoFijo`:

- **Parámetros**: son las variables que el constructor dice que va a recibir: `String dpi, String nombre, double sueldoMensual, double bonificacion`.
- **Argumentos**: son los valores reales que se le mandan cuando se llama. En `Main` le mando `dpiCapturado, nombreCapturado, sueldoCapturado, bonoCapturado`, que es lo que el usuario escribió en consola. Con el caso de Ana, los argumentos serían `"1234567890101", "Ana López", 4000.0, 300.0`.

El `this` lo uso para no confundir el atributo con el parámetro cuando se llaman igual, por ejemplo en el setter: `this.nombre = nombre;`. El de la izquierda es el atributo del objeto y el de la derecha es el parámetro.

Igual que hicimos en clase con la Bodeguita, el constructor llama a los setters (`this.setNombre(nombre)`) para que las validaciones queden en un solo lugar.

---

## Encapsulamiento y modificadores de acceso

Todos los atributos son `private`, en todas las clases (también el arreglo `meses` y el `ArrayList` que están en `Main`). Desde afuera los datos solo se pueden consultar con los getters (`getNombre()`, `getDpi()`, etc.).

Los setters los dejé `private` porque el programa no necesita cambiar un empleado después de crearlo; solo los uso adentro de la clase para validar. Si el sueldo, la bonificación, la tarifa o las horas vienen negativos, el setter guarda 0, y si el DPI o el nombre no son válidos guarda un texto vacío. Igual en `Main` los datos ya se validan antes de crear el objeto, así que eso es como un segundo filtro.

Los parámetros y las variables locales no llevan `private`, eso es solo para atributos.

---

## Abstracción

`Empleado` es una clase `abstract`. Ahí solo quedaron el DPI y el nombre, porque son los datos que tienen los dos tipos de trabajador. También declara dos métodos abstractos, sin cuerpo:

```java
public abstract double calcularPago();
public abstract String obtenerTipo();
```

Como es abstracta no se puede hacer `new Empleado(...)` (Eclipse da error). La idea es que `Empleado` diga *qué* tiene que hacer cualquier empleado, y cada hijo diga *cómo* lo hace.

---

## Herencia y sobrescritura

`EmpleadoFijo` y `EmpleadoPorHora` usan `extends Empleado`. En su constructor lo primero es `super(dpi, nombre);`, que le manda esos dos datos a la clase papá. Por eso los hijos no tienen atributo de DPI ni de nombre; si los necesitan, usan `getNombre()`.

Cada hijo sobrescribe los métodos con `@Override`:

- `EmpleadoFijo.calcularPago()` devuelve `sueldoMensual + bonificacion`.
- `EmpleadoPorHora.calcularPago()` devuelve `pagoPorHora * horasTrabajadas`.
- `obtenerTipo()` devuelve `"Fijo"` o `"Por hora"`, para el reporte.

---

## Polimorfismo

Los dos tipos de empleado se guardan juntos en una sola lista:

```java
private static ArrayList<Empleado> planillaDelMes = new ArrayList<Empleado>();
```

En `imprimirNomina()` recorro la lista con una variable de tipo `Empleado`:

```java
for (Empleado trabajador : planillaDelMes) {
    double pagoDelTrabajador = trabajador.calcularPago();
    ...
}
```

Aquí no hay ningún `if`, `switch` ni `instanceof` para ver qué fórmula usar. Java solo llama `calcularPago()` y cada objeto usa la suya: si es fijo suma, si es por hora multiplica. Eso es el polimorfismo.

El único `if` que hay con el tipo está en `ingresoDeNomina()`, y solo sirve para decidir qué objeto crear (fijo o por hora), que la práctica dice que sí se permite.

---

## Arreglo simple y ArrayList

**`String[] meses`**: es un arreglo simple con los 12 meses ya escritos en el código. Usé arreglo porque los meses siempre son 12, no cambian.

**`meses[numeroMes - 1]`**: el usuario escribe el mes del 1 al 12, pero el arreglo empieza en 0, por eso le resto 1. Si escribe 3, `meses[2]` da "Marzo". Esto se usa en `mostrarTituloMes()`, que es el método que pone el título en todas las pantallas. No guardé el nombre del mes en otra variable ni usé `switch` para sacarlo. Antes de usar `numeroMes` como índice se valida que esté entre 1 y 12 (`pedirOpcion(..., 1, 12)`), así que nunca entra un índice malo.

**`ArrayList<Empleado>`**: lo usé para los trabajadores porque no se sabe cuántos se van a ingresar, la lista va creciendo.

- **`add()`**: está en `ingresoDeNomina()`, en `planillaDelMes.add(empleadoNuevo);`. Solo se llama cuando todos los datos ya se validaron, entonces el contador sube hasta que el empleado ya quedó guardado.
- **`size()`**: es el contador de "Trabajadores actuales" y el "Total de trabajadores" del reporte. No usé una variable aparte para contar.
- **Recorrido**: el `for` de `imprimirNomina()` que mostré arriba. Ahí también se va sumando el total de la nómina.

---

## Métodos y main

`main` es el punto de inicio, pero casi todo lo hace por medio de otros métodos:

- `seleccionarMes()`, `mostrarTituloMes()`, `limpiarConsolaEclipse()`
- `ingresoDeNomina()`, `registrarEmpleadoFijo()`, `registrarEmpleadoPorHora()`
- `imprimirNomina()`
- `pedirOpcion()`, `pedirMonto()`, `pedirDpi()`, `pedirNombre()`, `formatoQuetzales()`

Métodos con parámetros: `pedirOpcion(String textoPregunta, int opcionMinima, int opcionMaxima)`, `pedirMonto(String textoPregunta)` y `formatoQuetzales(double monto)`.

Métodos que devuelven un resultado: `pedirOpcion()` devuelve un `int`, `pedirMonto()` un `double`, `registrarEmpleadoFijo()` devuelve el objeto que creó, y `calcularPago()` devuelve el pago.

---

## Ámbito y alcance

Ejemplos que se pueden ver en el código:

- **Atributo**: `sueldoMensual` en `EmpleadoFijo`. Pertenece a cada objeto y se puede usar en cualquier método de esa clase. Como es `private`, desde `Main` no se puede tocar directo, solo con `getSueldoMensual()`.
- **Parámetro**: `textoPregunta` en `pedirMonto(String textoPregunta)`. Solo existe adentro de ese método, mientras se está ejecutando.
- **Variable local**: `totalNomina` en `imprimirNomina()`. Se crea en ese método y se pierde cuando el método termina; desde otro método no se puede usar. Lo mismo `pagoDelTrabajador`, que solo existe adentro del `for`.

En `Main` usé atributos `static` (`meses`, `numeroMes`, `planillaDelMes`, `entrada`) porque es información que necesitan varios métodos durante todo el programa. Lo que es temporal, como las opciones de cada menú, lo dejé como variable local.

---

## Validaciones

Todo se lee con `nextLine()` y después se convierte a número. Lo hice así por dos cosas: para que no pase el problema del salto de línea que queda con `nextInt()`, y para que si alguien escribe letras en un campo de número el programa no se caiga. El `try/catch` atrapa el error y vuelve a pedir el dato.

- Mes: entero del 1 al 12.
- Menús: solo las opciones que aparecen; si no, avisa y vuelve a pedir.
- DPI: se guarda como `String` y tiene que tener exactamente 13 dígitos.
- Nombre: no puede ir vacío ni con puros espacios.
- Sueldo, bonificación, pago por hora y horas: tienen que ser números, no negativos y finitos (por ejemplo "Infinity" no se acepta).
- El empleado no se guarda hasta que todos sus datos están bien.

Los números con decimales se escriben con punto (25.50).

---

## Prueba del caso de la sección 11

1. Ingresé `3` en el mes y en el título salió **MARZO** (`meses[2]`).
2. Opción 1, empleado fijo: Ana López, DPI 1234567890101, sueldo 4000, bonificación 300. Pago: **Q 4300.00**.
3. Otro empleado, por hora: Luis Pérez, DPI 9876543210101, tarifa 25, 120 horas. Pago: **Q 3000.00**.
4. Opción 2, el reporte quedó así:

```
DPI             Nombre                    Tipo           Pago (Q)
-------------------------------------------------------------------
1234567890101   Ana López                 Fijo            4300.00
9876543210101   Javier  Pérez                Por hora        3000.00

Total de trabajadores: 2
Total de nómina: Q 7300.00
```

También probé:

- Mes 1 → Enero y mes 12 → Diciembre. Con 0, 13 o letras el programa avisa y vuelve a pedir.
- Imprimir la nómina sin empleados: sale el mensaje de que no hay empleados, 0 trabajadores y Q 0.00.
- DPI con menos dígitos, nombre vacío, letras en el sueldo y un sueldo negativo: en todos avisa el error y vuelve a pedir solo ese dato.
- Imprimir el reporte dos veces seguidas: los empleados no se borran ni se duplican.
