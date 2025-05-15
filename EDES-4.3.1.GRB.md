# Linters

## Preguntas

# [1]

### 1.a ¿Qué herramienta has usado, y para qué sirve?

He usado KTLint, es una herramienta de refactorización de código para Kotlin. 

### 1.b ¿Cuáles son sus características principales?

Su propósito principal es ayudara a mantener un código limpio de errores de formato o de estilo.

### 1.c ¿Qué beneficios obtengo al utilizar dicha herramienta?

Al usar KTLint se pueden mejorar las buenas prácticas que se realizarían normalmente durante la escritura de código.

---

# [2]

### 2.a De los errores/problemas que la herramienta ha detectado y te ha ayudado a solucionar, ¿cuál es el que te ha parecido que ha mejorado más tu código?

KTlint ha cambiado los nombres de las funciones, haciendolas más genéricas y específicas, facilitandome a la hora de buscarlas, por ejemplo, las utilidades, en cualquier otra parte de código que añada al proyecto.

### 2.b ¿La solución que se le ha dado al error/problema la has entendido y te ha parecido correcta?

Sí, me ha parecido bastante correcta, ya que me facilita las búsquedas de las funciones.

### 2.c ¿Por qué se ha producido ese error/problema?

Porque no se me había ocurrido la idea de especificar más en los nombres de los métodos o las funciones. 

---

# [3]

### 3.a ¿Qué posibilidades de configuración tiene la herramienta?

![img.png](img.png)

KTLint tiene una configuración bastante pobre, para mi gusto. KTLint no te permite guardar los problemas que ha encontrado en tu código en un archivo externo, como un markdown o hacer una copia de las clases originales que ha modificado.

### 3.b De esas posibilidades de configuración, ¿cuál has configurado para que sea distinta a la que viene por defecto?

He cambiado el modo de funcionamiento de KTLint a modo manual, haciendo que yo mismo tenga que ejecutarlo para que tenga efecto dentro del código de mi proyecto.

### 3.c Pon un ejemplo de cómo ha impactado en tu código, enlazando al código anterior al cambio, y al posterior al cambio.

- Parte anterior del código
https://github.com/gromber05/kotest/blob/0cdc4cbd909930a95d0adef5e326a5ac95583e22/src/main/kotlin/utils/Utilidades.kt#L15-L33
- Parte nueva del código
https://github.com/gromber05/kotest/blob/2b9daaaceedd393ac82275bdae93572963f34a34/src/main/kotlin/utils/Utilidades.kt#L7-L30

---

# [4]

### 4 ¿Qué conclusiones sacas después del uso de estas herramientas?

El uso de KTLint en mis proyectos, tanto personales como los que pertenecen a una empresa, es un gran avance gracias a que proporciona mejoras del código, fomentando el uso de las buenas prácticas, sugiriendo nombres más descriptivos o ayudando a escribir un código limpio.
Sin embargo, aunque KTLint es una gran mejora, no reemplaza el criterio humano ni puede continuar la lógica del código. Es una buena herramienta para la ayuda de escritura de código, pero no en dirigirlo completamente.