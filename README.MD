# **Pruebas de ActividadService (Kotest/Mock)**

## Resumen de Casos de Prueba

| Método                    | Caso de prueba                                        | Estado inicial del mock                          | Acción                                          | Resultado esperado                              |
|--------------------------|--------------------------------------------------------|--------------------------------------------------|-------------------------------------------------|-------------------------------------------------|
| crearEvento(...)         | Datos válidos                                          | mockRepositorio acepta cualquier evento          | llama crearEvento("desc", "12-05-2025", "loc")  | agregarEvento invocado correctamente            |
| crearEvento(...)         | Fecha en formato incorrecto                            | mockRepositorio no se invoca                     | llama crearEvento("desc", "2025-05-12", "loc")  | lanza IllegalArgumentException                  |
| crearTarea(...)          | Descripción válida                                     | mockRepositorio acepta caulquier tarea           | llama crearTarea("desc")                        | agregarTarea invocado correctamente             |
| crearTarea(...)          | Descripción vacía                                      | mockRepositorio no se invoca                     | llama crearTarea("")                            | lanza IllegalArgumentException                  |
| asociarSubtarea(...)     | Subtarea válida                                        | tareaPrincipal es un relaxed mock                | llama asociarSubtarea(tareaPrincipal, subtarea) | agregarSubtarea(subtarea) invocado              |
| asociarSubtarea(...)     | Subtarea nula                                          | mockRepositorio no recibe la llamada             | llama asociarSubtarea(tareaPrincipal, null)     | lanza NullPointerException                      |
| cambiarEstadoTarea(...)  | Tarea y estado válidos                                 | tarea es un relaxed mock                         | llama cambiarEstadoTarea(tarea, nuevoEstado)    | cambiarEstadoConHistorial(nuevoEstado) invocado |
| cambiarEstadoTarea(...)  | Tarea nula                                             | mockRepositorio no recibe la llamada             | llama cambiarEstadoTarea(null, nuevoEstado)     | lanza NullPointerException                      |
| listarActividades()      | Repositorio con actividades                           | mockRepositorio devuelve lista con 2 actividades | llama listarActividades()                       | retorna una lista con las 2 actividades         |
| listarActividades()      | Repositorio sin actividades                           | mockRepositorio devuelve lista vacía             | llama listarActividades()                       | retorna una lista vacía                         |

---

## Detalles por Método

| **Método**               | **Parámetros de entrada**                                   | **Efecto en el repositorio / Resultado**                                | **Correcto cuando...**                                                | **Incorrecto cuando...**                                           |
|--------------------------|--------------------------------------------------------------|-------------------------------------------------------------------------|------------------------------------------------------------------------|----------------------------------------------------------------------|
| crearEvento              | descripcion: String, fecha: String, ubicacion: String        | Llama a `agregarEvento()` si la fecha es válida                         | La fecha tiene formato `dd-MM-yyyy` y la descripción no es vacía       | La fecha tiene un formato inválido → lanza `IllegalArgumentException` |
| crearTarea               | descripcion: String                                          | Llama a `agregarTarea()` con la tarea creada                            | La descripción no está vacía                                           | La descripción está vacía → lanza `IllegalArgumentException`         |
| asociarSubtarea          | tareaPrincipal: Tarea, subtarea: Tarea                      | Llama a `agregarSubtarea()` sobre `tareaPrincipal`                      | `subtarea` no es nula                                                  | `subtarea` es nula → lanza `NullPointerException`                   |
| cambiarEstadoTarea       | tarea: Tarea, nuevoEstado: Estado                           | Llama a `cambiarEstadoConHistorial()` sobre la tarea                    | `tarea` y `nuevoEstado` no son nulos                                   | `tarea` es nula → lanza `NullPointerException`                      |
| listarActividades        | *(ninguno)*                                                  | Retorna el resultado de `obtenerActividades()` del repositorio          | El repositorio devuelve una lista válida (con o sin elementos)         | Nunca lanza error, pero puede devolver lista vacía si no hay datos   |
