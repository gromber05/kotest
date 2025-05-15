# **Pruebas de ActividadService**

## Resumen de Casos de Prueba

| Método                    | Escenario                                    | Estado inicial del mock                         | Acción ejecutada                                      | Resultado esperado                      |
| ------------------------- | -------------------------------------------- | ----------------------------------------------- | ----------------------------------------------------- | --------------------------------------- |
| `crearEvento(...)`        | Entrada válida                               | Sin interacciones previas                       | Ejecutar `crearEvento(descripción, fecha, ubicación)` | Se guarda el evento en el repositorio   |
| `crearEvento(...)`        | Formato de fecha incorrecto                  | Sin interacciones previas                       | Ejecutar `crearEvento(descripción, fecha, ubicación)` | Se lanza una `IllegalArgumentException` |
| `crearTarea(...)`         | Descripción no vacía                         | Sin interacciones previas                       | Ejecutar `crearTarea(descripción)`                    | Se almacena la tarea en el repositorio  |
| `crearTarea(...)`         | Descripción vacía                            | Sin interacciones previas                       | Ejecutar `crearTarea("")`                             | Se lanza una `IllegalArgumentException` |
| `asociarSubtarea(...)`    | Subtarea válida                              | `tareaPrincipal` y `subtarea` son mocks válidos | Ejecutar `asociarSubtarea(tareaPrincipal, subtarea)`  | La subtarea se asocia correctamente     |
| `asociarSubtarea(...)`    | Subtarea es `null`                           | `tareaPrincipal` es mock válido                 | Ejecutar `asociarSubtarea(tareaPrincipal, null)`      | Se lanza `NullPointerException`         |
| `cambiarEstadoTarea(...)` | Cambiar el estado de una tarea correctamente | `tarea` es un mock válido                       | Ejecutar `cambiarEstadoTarea(tarea, nuevoEstado)`     | El estado se actualiza con historial    |
| `cambiarEstadoTarea(...)` | Tarea nula                                   | `tarea` es `null`                               | Ejecutar `cambiarEstadoTarea(null, nuevoEstado)`      | Se lanza `NullPointerException`         |
| `listarActividades()`     | Repositorio con elementos                    | Devuelve lista no vacía                         | Ejecutar `listarActividades()`                        | Se obtiene la lista de actividades      |
| `listarActividades()`     | Repositorio vacío                            | Devuelve lista vacía                            | Ejecutar `listarActividades()`                        | Se obtiene una lista vacía              |

---

## Detalles por Método

### `crearEvento(descripcion: String, fecha: String, ubicacion: String)`

* **Prueba: Datos correctos**

  * **Mock inicial**: El repositorio no ha sido usado previamente.
  * **Acción**: Se llama al método pasando valores válidos.
  * **Esperado**: El evento es construido y enviado al repositorio. Se comprueba la invocación de `agregarEvento`.

* **Prueba: Fecha en formato incorrecto**

  * **Mock inicial**: Repositorio sin actividad.
  * **Acción**: Se utiliza una fecha con un formato no admitido.
  * **Esperado**: Lanza una excepción del tipo `IllegalArgumentException`.

---

### `crearTarea(descripcion: String)`

* **Prueba: Descripción válida**

  * **Mock inicial**: El repositorio aún no ha sido invocado.
  * **Acción**: Se pasa una cadena con contenido válido.
  * **Esperado**: La tarea se registra correctamente y se invoca `agregarTarea`.

* **Prueba: Descripción vacía**

  * **Mock inicial**: El mock está limpio.
  * **Acción**: Se pasa una cadena vacía como argumento.
  * **Esperado**: Lanza `IllegalArgumentException` porque la descripción no puede estar vacía.

---

### `asociarSubtarea(tareaPrincipal: Tarea, subtarea: Tarea)`

* **Prueba: Subtarea válida**

  * **Mock inicial**: Ambas tareas (principal y subtarea) están mockeadas.
  * **Acción**: Se llama al método con objetos válidos.
  * **Esperado**: La subtarea se vincula correctamente. Se comprueba que se llamó a `agregarSubtarea`.

* **Prueba: Subtarea nula**

  * **Mock inicial**: Solo `tareaPrincipal` es un mock válido.
  * **Acción**: Se intenta asociar `null` como subtarea.
  * **Esperado**: El método lanza una `NullPointerException`.

---

### `cambiarEstadoTarea(tarea: Tarea, nuevoEstado: Estado)`

* **Prueba: Cambio exitoso**

  * **Mock inicial**: La tarea está correctamente mockeada.
  * **Acción**: Se invoca el cambio de estado.
  * **Esperado**: Se ejecuta `cambiarEstadoConHistorial` en la tarea.

* **Prueba: Tarea inexistente**

  * **Mock inicial**: La tarea es `null`.
  * **Acción**: Se intenta cambiar el estado a una tarea nula.
  * **Esperado**: Lanza `NullPointerException`.

---

### `listarActividades()`

* **Prueba: Con datos disponibles**

  * **Mock inicial**: El repositorio retorna una lista con elementos.
  * **Acción**: Se llama a `listarActividades`.
  * **Esperado**: Devuelve la misma lista que contiene el repositorio.

* **Prueba: Sin datos**

  * **Mock inicial**: El mock devuelve una lista vacía.
  * **Acción**: Se ejecuta `listarActividades`.
  * **Esperado**: Se obtiene una lista vacía sin errores.

