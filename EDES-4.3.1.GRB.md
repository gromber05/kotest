# EDES-4.3.1 - Analizador de código estático (Ktlint)

## 1. Proceso de instalación y puesta en marcha

La instalación de Ktlint ha sido sencilla. Desde el marketplace de plugins de IntelliJ IDEA busqué "Ktlint" y lo instalé, quedando operativo inmediatamente sobre mi código.

![img_1.png](assets/img_1.png)  
*Captura: Ktlint encontrado en el marketplace de plugins de IntelliJ.*

![img_2.png](assets/img_2.png)  
*Captura: Ktlint funcionando e identificando errores en el proyecto.*

Como alternativa, también es posible integrarlo mediante Gradle con:

```gradle
plugins {
    id("org.jlleitschuh.gradle.ktlint") version "12.1.0"
}
```

y ejecutar el chequeo de formato con:

```sh
./gradlew ktlintCheck
```

---

## 2. Integración y análisis en el proyecto

Ktlint se ha integrado y ejecutado en la rama `feature/linters` del repositorio.

Enlace a la rama:  
[https://github.com/gromber05/kotest/tree/feature/ktlint-linters](https://github.com/gromber05/kotest/tree/feature/ktlint-linters)

---

## 3. Errores detectados, clasificados y solucionados

A continuación se documentan algunos tipos de errores detectados por Ktlint y la solución aplicada, enlazando a los commits relevantes:

### 1. Importaciones no utilizadas

**Descripción:**  
Ktlint señala importaciones que no están en uso.

**Solución aplicada:**  
Se eliminaron las líneas de import innecesarias en los archivos afectados.

---

### 2. Funciones declaradas pero nunca utilizadas

**Descripción:**  
Detección de funciones privadas no utilizadas (código muerto).

**Solución aplicada:**  
Eliminé las funciones que no se usaban en ningún lugar del proyecto.

---

### 3. Uso de println en vez de sistema de salida centralizado

**Descripción:**  
Se detectó el uso de `println` en vez de la función de salida estándar del proyecto.

**Solución aplicada:**  
Cambié las llamadas a `println` por `salida.mostrar()`, mejorando la coherencia dentro del código del proyecto.

---

### 4. Espacios en blanco innecesarios o líneas demasiado largas

**Descripción:**  
Ktlint reportó líneas con espacios finales o líneas que excedían el límite de longitud estándar.

**Solución aplicada:**  
Eliminé espacios extra y adapté las líneas para cumplir los estándares.

---

### 5. Nombres de funciones y variables poco descriptivos

**Descripción:**  
Ktlint sugirió mejorar nombres de funciones para hacerlos más descriptivos, genéricos o específicos.

**Solución aplicada:**  
Renombré funciones y variables siguiendo las convenciones y buenas prácticas de Kotlin.

---

## Ejemplo de antes y después (enlace a commits)

- **Antes:**  
  [Código original](https://github.com/gromber05/kotest/blob/0cdc4cbd909930a95d0adef5e326a5ac95583e22/src/main/kotlin/utils/Utilidades.kt#L15-L33)
- **Después:**  
  [Código tras aplicar Ktlint](https://github.com/gromber05/kotest/blob/2b9daaaceedd393ac82275bdae93572963f34a34/src/main/kotlin/utils/Utilidades.kt#L7-L30)

---

## 4. Configuración personalizada de Ktlint

Ktlint permite configurarse a través del archivo `.editorconfig`. Por ejemplo, para permitir archivos sin salto de línea final:

```ini
[*.kt]
insert_final_newline = false
```

**Impacto:**  
Antes, Ktlint reportaba error si no había salto de línea al final de cada archivo. Tras la modificación, ya no lo reporta.

---

## 5. Preguntas

### [1]

#### 1.a ¿Qué herramienta has usado y para qué sirve?

He usado **Ktlint**, un formateador y analizador de código para Kotlin que ayuda a mantener un estilo y formato uniforme en el código.

#### 1.b ¿Cuáles son sus características principales?

- Aplicación automática de formato.
- Detección y corrección de errores de estilo.
- Integración con IDE y herramientas de CI.
- Es configurable a través del archivo `.editorconfig`

#### 1.c ¿Qué beneficios obtengo al utilizar dicha herramienta?

- Código más limpio y legible.
- Facilita las revisiones y el mantenimiento.

---

### [2]

#### 2.a ¿Cuál error te ha parecido que ha mejorado más tu código?

La detección de nombres poco descriptivos en funciones, ya que ahora el código es más fácil de entender y buscar.

#### 2.b ¿La solución que se le ha dado al error/problema la has entendido y te ha parecido correcta?

Sí, las sugerencias han sido claras y he podido aplicar los cambios fácilmente.

#### 2.c ¿Por qué se ha producido ese error/problema?

Por falta de atención al nombrar funciones durante el desarrollo inicial.

---

### [3]

#### 3.a ¿Qué posibilidades de configuración tiene la herramienta?

Permite personalizar reglas de formato, longitud de línea, espacios, indentación, salto de línea final, etc., mediante `.editorconfig` o ajustes en el plugin.

#### 3.b ¿Cuál has cambiado respecto a la configuración por defecto?

He cambiado la regla de salto de línea final (`insert_final_newline = false`) y el modo de ejecución a manual.

#### 3.c Ejemplo del impacto

Ver los enlaces de antes y después en el apartado de ejemplo.

---

### [4]

#### 4. ¿Qué conclusiones sacas después del uso de estas herramientas?

Ktlint ha mejorado la calidad y homogeneidad de mi código, pero no sustituye la revisión humana ni la comprensión lógica, simplemente ayuda a mantener el código ordenado y profesional.

---

## Lista de errores detectados

1. **Unused variable**  
   En este for, hay una variable que no se está usando, lo que hace que el código esté menos limpio. Cambie la variable contador por una "_":
````kotlin
private fun comprobarFechaContador(fecha: String): Boolean {
    for ((fechaMapa, contador /*He cambiado esta variable por _*/) in mapaIdEventos) {
        if (fechaMapa == fecha) return true
    }
    return false
}
````
````
    De esta manera, nos ahorramos el cargar en memoria más datos de los necesarios.

2. **e**