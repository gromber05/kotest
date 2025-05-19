package es.prog2425.taskmanager.servicios

import es.prog2425.taskmanager.datos.ActividadRepository
import es.prog2425.taskmanager.modelo.Estado
import es.prog2425.taskmanager.modelo.Evento
import es.prog2425.taskmanager.modelo.Tarea
import es.prog2425.taskmanager.presentacion.Consola
import es.prog2425.taskmanager.presentacion.Interfaz
import es.prog2425.taskmanager.utils.Utilidades

class GestorActividades {
    private val salida: Interfaz = Consola()
    private val servicio = ActividadService(ActividadRepository())
    private val servicioUsuario: IUsuarioService = UsuarioService()

    fun menu() {
        var salir = false
        do {
            try {
                salida.mostrarMenu()
                val opcion = salida.leerNum()
                when (opcion) {
                    -1 -> salida.mostrar("\nOpcion no valida.")
                    1 -> {
                        // Crear Evento
                        var descripcionEvento: String
                        do {
                            salida.mostrar("\nIntroduce la descripcion")
                            salida.mostrarInput("> ")
                            descripcionEvento = salida.leerString()
                            if (descripcionEvento.isEmpty()) salida.mostrar("\nLa descripcion debe contener algo.")
                        } while (descripcionEvento.isEmpty())

                        var fechaEvento: String
                        do {
                            salida.mostrar("\nIntroduce la fecha con el siguiente formato (dd-MM-yyyy)")
                            salida.mostrarInput("> ")
                            fechaEvento = salida.leerString()
                            if (!Utilidades().esFechaValida(fechaEvento)) salida.mostrar("\nFecha invalida.")
                        } while (!Utilidades().esFechaValida(fechaEvento))

                        var ubicacionEvento: String
                        do {
                            salida.mostrar("\nIntroduce la ubicacion")
                            salida.mostrarInput("> ")
                            ubicacionEvento = salida.leerString()
                            if (ubicacionEvento.isEmpty()) salida.mostrar("\nLa ubicacion debe contener algo.")
                        } while (ubicacionEvento.isEmpty())

                        servicio.crearEvento(descripcionEvento, fechaEvento, ubicacionEvento)
                    }
                    2 -> {
                        // Crear Tarea
                        var descripcionTarea: String
                        do {
                            salida.mostrar("\nIntroduce la descripcion")
                            salida.mostrarInput("> ")
                            descripcionTarea = salida.leerString()
                            if (descripcionTarea.isEmpty()) salida.mostrar("\nLa descripcion debe contener algo.")
                        } while (descripcionTarea.isEmpty())

                        servicio.crearTarea(descripcionTarea)
                        salida.mostrar("\nTarea creada con éxito y etiquetas asignadas.")
                    }
                    3 -> {
                        // Listar actividades
                        val actividades = servicio.listarActividades()
                        if (actividades.isEmpty()) {
                            salida.mostrar("\nNo hay actividades registradas.")
                        } else {
                            salida.mostrar("\nListado de actividades:")
                            actividades.forEach { actividad ->
                                salida.mostrar(actividad.obtenerDetalle())
                            }
                        }
                    }
                    4 -> {
                        // Asociar subtarea
                        val tareas = servicio.listarActividades().filterIsInstance<Tarea>()
                        if (tareas.isEmpty()) {
                            salida.mostrar("\nNo hay tareas disponibles.")
                            break
                        }
                        salida.mostrar("\nSelecciona la tarea principal:")
                        tareas.forEachIndexed { index, tarea -> salida.mostrar("${index + 1}. ${tarea.obtenerDetalle()}") }

                        var tareaPrincipal: Tarea? = null
                        while (tareaPrincipal == null) {
                            salida.mostrar("\nIntroduce el número de la tarea principal:")
                            val num = salida.leerNum()
                            if (num in 1..tareas.size) {
                                tareaPrincipal = tareas[num - 1]
                            } else {
                                salida.mostrar("\nOpción inválida.")
                            }
                        }

                        var descripcionSubtarea: String
                        do {
                            salida.mostrar("\nDescribe la subtarea a asociar:")
                            salida.mostrarInput("> ")
                            descripcionSubtarea = salida.leerString()
                            if (descripcionSubtarea.isEmpty()) salida.mostrar("\nLa descripcion debe contener algo.")
                        } while (descripcionSubtarea.isEmpty())

                        val subtarea = Tarea.crearInstancia(descripcionSubtarea)
                        servicio.asociarSubtarea(tareaPrincipal, subtarea)
                        salida.mostrar("\nSubtarea asociada a la tarea principal.")
                    }
                    5 -> {
                        // Cambiar estado tarea
                        val tareas = servicio.listarActividades().filterIsInstance<Tarea>()
                        if (tareas.isEmpty()) {
                            salida.mostrar("\nNo hay tareas disponibles.")
                            break
                        }
                        salida.mostrar("\nSelecciona la tarea cuyo estado deseas cambiar:")
                        tareas.forEachIndexed { index, tarea -> salida.mostrar("${index + 1}. ${tarea.obtenerDetalle()}") }

                        var tareaSeleccionada: Tarea? = null
                        while (tareaSeleccionada == null) {
                            salida.mostrar("\nIntroduce el número de la tarea:")
                            val num = salida.leerNum()
                            if (num in 1..tareas.size) {
                                tareaSeleccionada = tareas[num - 1]
                            } else {
                                salida.mostrar("\nOpción inválida.")
                            }
                        }

                        salida.mostrar("\nElige el nuevo estado para la tarea:")
                        salida.mostrar("1. ABIERTA")
                        salida.mostrar("2. EN PROGRESO")
                        salida.mostrar("3. FINALIZADA")
                        val estadoSeleccionado = salida.leerNum()

                        val nuevoEstado =
                            when (estadoSeleccionado) {
                                1 -> Estado.ABIERTA
                                2 -> Estado.EN_PROGRESO
                                3 -> Estado.FINALIZADA
                                else -> {
                                    salida.mostrar("\nOpción no válida. El estado no ha sido cambiado.")
                                    null
                                }
                            }

                        if (nuevoEstado != null) {
                            try {
                                servicio.cambiarEstadoTarea(tareaSeleccionada, nuevoEstado)
                                salida.mostrar("\nEstado de la tarea cambiado exitosamente a ${nuevoEstado.name}.")
                            } catch (e: IllegalStateException) {
                                salida.mostrar("\nError: ${e.message}")
                            }
                        }
                    }
                    6 -> {
                        // Cerrar tarea
                        val tareas = servicio.listarActividades().filterIsInstance<Tarea>()
                        if (tareas.isEmpty()) {
                            salida.mostrar("\nNo hay tareas disponibles.")
                            break
                        }
                        salida.mostrar("\nSelecciona la tarea a cerrar:")
                        tareas.forEachIndexed { index, tarea -> salida.mostrar("${index + 1}. ${tarea.obtenerDetalle()}") }

                        var tareaSeleccionada: Tarea? = null
                        while (tareaSeleccionada == null) {
                            salida.mostrar("\nIntroduce el número de la tarea:")
                            val num = salida.leerNum()
                            if (num in 1..tareas.size) {
                                tareaSeleccionada = tareas[num - 1]
                            } else {
                                salida.mostrar("\nOpción inválida.")
                            }
                        }

                        try {
                            tareaSeleccionada.cerrar()
                            salida.mostrar("\nTarea cerrada exitosamente.")
                        } catch (e: IllegalStateException) {
                            salida.mostrar("\nNo se puede cerrar la tarea porque tiene subtareas abiertas.")
                        }
                    }
                    7 -> {
                        // Crear usuario
                        salida.mostrar("\nIntroduce el nombre del nuevo usuario: ")
                        val nombre = salida.leerString()
                        servicioUsuario.crearUsuario(nombre)
                        salida.mostrar("\nUsuario '$nombre' creado con éxito.")
                    }
                    8 -> {
                        // Asignar tarea a usuario
                        val tareas = servicio.listarActividades().filterIsInstance<Tarea>()
                        if (tareas.isEmpty()) {
                            salida.mostrar("\nNo hay tareas disponibles.")
                            break
                        }
                        salida.mostrar("\nSelecciona la tarea a asignar: ")
                        tareas.forEachIndexed { index, tarea -> salida.mostrar("${index + 1}. ${tarea.obtenerDetalle()}") }

                        var tareaSeleccionada: Tarea? = null
                        while (tareaSeleccionada == null) {
                            salida.mostrar("\nIntroduce el número de la tarea:")
                            val num = salida.leerNum()
                            if (num in 1..tareas.size) {
                                tareaSeleccionada = tareas[num - 1]
                            } else {
                                salida.mostrar("\nOpción inválida.")
                            }
                        }

                        salida.mostrar("\nIntroduce el nombre del usuario al que asignar la tarea: ")
                        val nombreUsuario = salida.leerString()

                        val usuario = servicioUsuario.obtenerUsuarioPorNombre(nombreUsuario)
                        if (usuario != null) {
                            servicioUsuario.asignarTareaAUsuario(usuario, tareaSeleccionada)
                            salida.mostrar("\nTarea asignada correctamente a $nombreUsuario.")
                        } else {
                            salida.mostrar("\nNo se encontró un usuario con ese nombre.")
                        }
                    }
                    9 -> {
                        // Consultar tareas usuario
                        salida.mostrar("\nIntroduce el nombre del usuario para consultar sus tareas: ")
                        val nombreUsuario = salida.leerString()
                        val usuario = servicioUsuario.obtenerUsuarioPorNombre(nombreUsuario)
                        if (usuario == null) {
                            salida.mostrar("\nNo se encontró el usuario.")
                            break
                        }

                        val tareasUsuario =
                            servicio
                                .listarActividades()
                                .filterIsInstance<Tarea>()
                                .filter { it.obtenerUsuarioAsignado() == usuario }

                        if (tareasUsuario.isEmpty()) {
                            salida.mostrar("\nEl usuario no tiene tareas asignadas.")
                        } else {
                            salida.mostrar("\nTareas asignadas a $nombreUsuario:")
                            tareasUsuario.forEach { tarea ->
                                salida.mostrar(tarea.obtenerDetalle())
                            }
                        }
                    }
                    10 -> {
                        // Filtrar actividades
                        salida.mostrar("\nFiltrar actividades por:")
                        salida.mostrar("1. Tipo (Tarea o Evento)")
                        salida.mostrar("2. Estado (ABIERTA, EN_PROGRESO, FINALIZADA)")
                        salida.mostrar("3. Etiquetas")
                        salida.mostrar("4. Usuario")
                        val opcionFiltro = salida.leerNum()

                        when (opcionFiltro) {
                            1 -> {
                                salida.mostrar("\nSelecciona el tipo de actividad a filtrar:")
                                salida.mostrar("1. Tarea")
                                salida.mostrar("2. Evento")
                                val tipoSeleccionado = salida.leerNum()

                                val actividadesFiltradas =
                                    when (tipoSeleccionado) {
                                        1 -> servicio.listarActividades().filterIsInstance<Tarea>()
                                        2 -> servicio.listarActividades().filterIsInstance<Evento>()
                                        else -> {
                                            salida.mostrar("\nOpción no válida.")
                                            null
                                        }
                                    }

                                if (actividadesFiltradas != null) {
                                    if (actividadesFiltradas.isEmpty()) {
                                        salida.mostrar("\nNo se encontraron actividades con ese criterio.")
                                    } else {
                                        salida.mostrar("\nActividades encontradas:")
                                        actividadesFiltradas.forEach { salida.mostrar(it.obtenerDetalle()) }
                                    }
                                }
                            }
                            2 -> {
                                salida.mostrar("\nSelecciona el estado de la actividad a filtrar:")
                                salida.mostrar("1. ABIERTA")
                                salida.mostrar("2. EN PROGRESO")
                                salida.mostrar("3. FINALIZADA")
                                val estadoSeleccionado = salida.leerNum()

                                val estado =
                                    when (estadoSeleccionado) {
                                        1 -> Estado.ABIERTA
                                        2 -> Estado.EN_PROGRESO
                                        3 -> Estado.FINALIZADA
                                        else -> {
                                            salida.mostrar("\nOpción no válida.")
                                            null
                                        }
                                    }

                                if (estado != null) {
                                    val actividadesFiltradas =
                                        servicio
                                            .listarActividades()
                                            .filter { it is Tarea && it.estado == estado }
                                    if (actividadesFiltradas.isEmpty()) {
                                        salida.mostrar("\nNo se encontraron actividades con ese criterio.")
                                    } else {
                                        salida.mostrar("\nActividades encontradas:")
                                        actividadesFiltradas.forEach { salida.mostrar(it.obtenerDetalle()) }
                                    }
                                }
                            }
                            3 -> {
                                salida.mostrar("\nIntroduce la etiqueta a filtrar: ")
                                val etiqueta = salida.leerString()

                                val actividadesFiltradas =
                                    servicio.listarActividades().filter {
                                        (it is Tarea || it is Evento) && it.obtenerEtiquetas().contains(etiqueta)
                                    }

                                if (actividadesFiltradas.isEmpty()) {
                                    salida.mostrar("\nNo se encontraron actividades con ese criterio.")
                                } else {
                                    salida.mostrar("\nActividades encontradas:")
                                    actividadesFiltradas.forEach { salida.mostrar(it.obtenerDetalle()) }
                                }
                            }
                            4 -> {
                                salida.mostrar("\nIntroduce el nombre del usuario a filtrar:")
                                val nombreUsuario = salida.leerString()
                                val usuario = servicioUsuario.obtenerUsuarioPorNombre(nombreUsuario)
                                if (usuario == null) {
                                    salida.mostrar("\nNo se encontró un usuario con ese nombre.")
                                    break
                                }
                                val filtradas =
                                    servicio
                                        .listarActividades()
                                        .filterIsInstance<Tarea>()
                                        .filter { it.obtenerUsuarioAsignado() == usuario }
                                if (filtradas.isEmpty()) {
                                    salida.mostrar("\nNo se encontraron actividades con ese criterio.")
                                } else {
                                    salida.mostrar("\nActividades encontradas:")
                                    filtradas.forEach { salida.mostrar(it.obtenerDetalle()) }
                                }
                            }
                            else -> salida.mostrar("\nOpción no válida.")
                        }
                    }
                    11 -> {
                        // Consultar historial tarea
                        val tareas = servicio.listarActividades().filterIsInstance<Tarea>()
                        if (tareas.isEmpty()) {
                            salida.mostrar("\nNo hay tareas disponibles.")
                            break
                        }
                        salida.mostrar("\nSelecciona la tarea para ver su historial:")
                        tareas.forEachIndexed { index, tarea -> salida.mostrar("${index + 1}. ${tarea.obtenerDetalle()}") }

                        var tareaSeleccionada: Tarea? = null
                        while (tareaSeleccionada == null) {
                            salida.mostrar("\nIntroduce el número de la tarea:")
                            val num = salida.leerNum()
                            if (num in 1..tareas.size) {
                                tareaSeleccionada = tareas[num - 1]
                            } else {
                                salida.mostrar("\nOpción inválida.")
                            }
                        }

                        val historial = tareaSeleccionada.obtenerHistorial()
                        if (historial.isEmpty()) {
                            salida.mostrar("\nLa tarea no tiene historial.")
                        } else {
                            salida.mostrar("\nHistorial de la tarea:")
                            historial.forEach { (fecha, accion) ->
                                salida.mostrar("[$fecha] $accion")
                            }
                        }
                    }
                    12 -> salir = true
                    else -> salida.mostrar("\nOpción no válida.")
                }
            } catch (e: IllegalStateException) {
                salida.mostrar("$e")
            }
        } while (!salir)
    }
}
