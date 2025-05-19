package es.prog2425.taskmanager.servicios

import es.prog2425.taskmanager.datos.ActividadRepository
import es.prog2425.taskmanager.modelo.Actividad
import es.prog2425.taskmanager.presentacion.Consola
import es.prog2425.taskmanager.presentacion.Interfaz
import es.prog2425.taskmanager.utils.Utilidades
import es.prog2425.taskmanager.modelo.Tarea
import es.prog2425.taskmanager.modelo.Estado
import es.prog2425.taskmanager.modelo.Evento

/**
 * Clase principal encargada de gestionar todas las operaciones relacionadas con actividades (tareas y eventos).
 * Proporciona un menú para interactuar con el usuario y delega las operaciones a servicios y repositorios correspondientes.
 *
 * Funcionalidades principales:
 * - Crear, listar, modificar y cerrar tareas y eventos.
 * - Asociar subtareas a tareas principales.
 * - Filtrar actividades por tipo, estado, etiquetas o usuario.
 * - Consultar historial de una tarea.
 * - Gestión de usuarios (crear, asignar tareas, consultar tareas asignadas).
 *
 * Utiliza la interfaz [Interfaz] para la interacción con el usuario y el servicio [ActividadService] para la lógica de negocio.
 */
class GestorActividades {

    private val salida: Interfaz = Consola()
    private val servicio = ActividadService(ActividadRepository())
    private val servicioUsuario: IUsuarioService = UsuarioService()

    /**
     * Muestra el menú principal y gestiona la navegación entre opciones hasta que el usuario decida salir.
     */
    fun menu() {
        var salir = false
        do {
            try {
                salida.mostrarMenu()
                when (salida.leerNum()) {
                    -1 -> salida.mostrar("\nOpcion no valida.")
                    1 -> servicio.crearEvento(pedirDescripcion(), pedirFecha(), pedirUbicacion())
                    2 -> crearTarea()
                    3 -> listarActividades()
                    4 -> asociarSubtarea()
                    5 -> cambiarEstadoTarea()
                    6 -> cerrarTarea()
                    7 -> crearUsuario()
                    8 -> asignarTareaAUsuario()
                    9 -> consultarTareasUsuario()
                    10 -> filtrarActividades()
                    11 -> consultarHistorialTarea()
                    12 -> salir = true
                }
            } catch (e: java.lang.IllegalStateException) {
                salida.mostrar("$e")
            }

        } while (!salir)
    }

    /**
     * Permite consultar el historial de cambios de una tarea seleccionada por el usuario.
     */
    private fun consultarHistorialTarea() {
        salida.mostrar("\nSelecciona la tarea para ver su historial:")
        val tarea = obtenerTarea()
        val historial = tarea.obtenerHistorial()
        if (historial.isEmpty()) {
            salida.mostrar("\nLa tarea no tiene historial.")
        } else {
            salida.mostrar("\nHistorial de la tarea:")
            historial.forEach { (fecha, accion) ->
                salida.mostrar("[$fecha] $accion")
            }
        }
    }

    /**
     * Lista todas las actividades (tareas y eventos) registradas.
     */
    private fun listarActividades() {
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

    /**
     * Permite cambiar el estado de una tarea seleccionada por el usuario.
     */
    private fun cambiarEstadoTarea() {
        salida.mostrar("\nSelecciona la tarea cuyo estado deseas cambiar:")
        val tarea = obtenerTarea()

        salida.mostrar("\nElige el nuevo estado para la tarea:")
        salida.mostrar("1. ABIERTA")
        salida.mostrar("2. EN PROGRESO")
        salida.mostrar("3. FINALIZADA")

        val estadoSeleccionado = salida.leerNum()
        val nuevoEstado = when (estadoSeleccionado) {
            1 -> Estado.ABIERTA
            2 -> Estado.EN_PROGRESO
            3 -> Estado.FINALIZADA
            else -> {
                salida.mostrar("\nOpción no válida. El estado no ha sido cambiado.")
                return
            }
        }

        try {
            servicio.cambiarEstadoTarea(tarea, nuevoEstado)
            salida.mostrar("\nEstado de la tarea cambiado exitosamente a ${nuevoEstado.name}.")
        } catch (e: IllegalStateException) {
            salida.mostrar("\nError: ${e.message}")
        }
    }

    /**
     * Permite cerrar una tarea solo si todas sus subtareas están cerradas.
     */
    private fun cerrarTarea() {
        salida.mostrar("\nSelecciona la tarea a cerrar:")
        val tarea = obtenerTarea()
        try {
            tarea.cerrar()
            salida.mostrar("\nTarea cerrada exitosamente.")
        } catch (e: IllegalStateException) {
            salida.mostrar("\nNo se puede cerrar la tarea porque tiene subtareas abiertas.")
        }
    }

    /**
     * Solicita los datos necesarios y crea una nueva tarea.
     */
    private fun crearTarea() {
        val descripcion = pedirDescripcion()
        servicio.crearTarea(descripcion)
        salida.mostrar("\nTarea creada con éxito y etiquetas asignadas.")
    }

    /**
     * Solicita al usuario introducir etiquetas separadas por ';'.
     * @return Lista de etiquetas proporcionadas.
     */
    private fun pedirEtiquetas(): List<String> {
        salida.mostrarInput("Introduce las etiquetas (separadas por ';'):")
        return salida.leerString().split(';').map { it.trim() }.filter { it.isNotEmpty() }
    }

    /**
     * Permite asociar una nueva subtarea a una tarea principal seleccionada.
     */
    private fun asociarSubtarea() {
        salida.mostrar("\nSelecciona la tarea principal:")
        val tareaPrincipal = obtenerTarea()
        salida.mostrar("\nDescribe la subtarea a asociar:")
        val descripcionSubtarea = pedirDescripcion()
        val subtarea = Tarea.crearInstancia(descripcionSubtarea)
        servicio.asociarSubtarea(tareaPrincipal, subtarea)
        salida.mostrar("\nSubtarea asociada a la tarea principal.")
    }

    /**
     * Permite al usuario seleccionar una tarea de una lista de tareas disponibles.
     * @return La tarea seleccionada.
     * @throws IllegalStateException Si no hay tareas disponibles.
     */
    private fun obtenerTarea(): Tarea {
        val tareas = servicio.listarActividades().filterIsInstance<Tarea>()

        if (tareas.isEmpty()) {
            salida.mostrar("\nNo hay tareas disponibles.")
            println("Error no controlado si no hay try-catch")
            throw IllegalStateException("No hay tareas disponibles para seleccionar.")
        }

        salida.mostrar("\nSelecciona una tarea de la lista:")
        tareas.forEachIndexed { index, tarea ->
            salida.mostrar("${index + 1}. ${tarea.obtenerDetalle()}")
        }

        var tareaSeleccionada: Tarea? = null
        while (tareaSeleccionada == null) {
            salida.mostrar("\nIntroduce el número de la tarea:")
            val numeroTarea = salida.leerNum()

            if (numeroTarea in 1..tareas.size) {
                tareaSeleccionada = tareas[numeroTarea - 1]
            } else {
                salida.mostrar("\nOpción inválida. Por favor, elige un número válido.")
            }
        }

        return tareaSeleccionada
    }

    /**
     * Solicita una descripción al usuario.
     * @return Descripción introducida.
     */
    private fun pedirDescripcion(): String {
        while (true) {
            salida.mostrar("\nIntroduce la descripcion")
            salida.mostrarInput("> ")
            val descripcion = salida.leerString()

            if (descripcion != "") return descripcion else salida.mostrar("\nLa descripcion debe contener algo.")
        }
    }

    /**
     * Solicita una fecha válida al usuario.
     * @return Fecha válida en formato dd-MM-yyyy.
     */
    private fun pedirFecha(): String {
        while (true) {
            salida.mostrar("\nIntroduce la fecha con el siguiente formato (dd-MM-yyyy)")
            salida.mostrarInput("> ")
            val fecha = salida.leerString()

            if (Utilidades().esFechaValida(fecha)) {
                return fecha
            } else salida.mostrar("\nFecha invalida.")
        }
    }

    /**
     * Solicita una ubicación al usuario.
     * @return Ubicación introducida.
     */
    private fun pedirUbicacion(): String {
        while (true) {
            salida.mostrar("\nIntroduce la ubicacion")
            salida.mostrarInput("> ")
            val ubicacion = salida.leerString()

            if (ubicacion != "") return ubicacion else salida.mostrar("\nLa ubicacion debe contener algo.")
        }
    }

    /**
     * Solicita el nombre de un nuevo usuario y lo crea.
     */
    private fun crearUsuario() {
        salida.mostrar("\nIntroduce el nombre del nuevo usuario: ")
        val nombre = salida.leerString()
        servicioUsuario.crearUsuario(nombre)
        salida.mostrar("\nUsuario '$nombre' creado con éxito.")
    }

    /**
     * Permite asignar una tarea seleccionada a un usuario existente.
     */
    private fun asignarTareaAUsuario() {
        salida.mostrar("\nSelecciona la tarea a asignar: ")
        val tarea = obtenerTarea()
        salida.mostrar("\nIntroduce el nombre del usuario al que asignar la tarea: ")
        val nombreUsuario = salida.leerString()

        val usuario = servicioUsuario.obtenerUsuarioPorNombre(nombreUsuario)
        if (usuario != null) {
            servicioUsuario.asignarTareaAUsuario(usuario, tarea)
            salida.mostrar("\nTarea asignada correctamente a $nombreUsuario.")
        } else {
            salida.mostrar("\nNo se encontró un usuario con ese nombre.")
        }
    }

    /**
     * Permite aplicar diferentes filtros sobre las actividades.
     */
    private fun filtrarActividades() {
        salida.mostrar("\nFiltrar actividades por:")
        salida.mostrar("1. Tipo (Tarea o Evento)")
        salida.mostrar("2. Estado (ABIERTA, EN_PROGRESO, FINALIZADA)")
        salida.mostrar("3. Etiquetas")
        salida.mostrar("4. Usuario")
        val opcionFiltro = salida.leerNum()

        when (opcionFiltro) {
            1 -> filtrarPorTipo()
            2 -> filtrarPorEstado()
            3 -> filtrarPorEtiquetas()
            4 -> filtrarPorUsuario()
            else -> salida.mostrar("\nOpción no válida.")
        }
    }

    /**
     * Filtra las actividades asignadas a un usuario.
     */
    private fun filtrarPorUsuario() {
        salida.mostrar("\nIntroduce el nombre del usuario a filtrar:")
        val nombreUsuario = salida.leerString()
        val usuario = servicioUsuario.obtenerUsuarioPorNombre(nombreUsuario) ?: run {
            salida.mostrar("\nNo se encontró un usuario con ese nombre.")
            return
        }
        val filtradas = servicio.listarActividades()
            .filterIsInstance<Tarea>()
            .filter { it.obtenerUsuarioAsignado() == usuario }
        mostrarActividades(filtradas)
    }

    /**
     * Filtra las actividades por tipo (Tarea o Evento).
     */
    private fun filtrarPorTipo() {
        salida.mostrar("\nSelecciona el tipo de actividad a filtrar:")
        salida.mostrar("1. Tarea")
        salida.mostrar("2. Evento")
        val tipoSeleccionado = salida.leerNum()

        val actividadesFiltradas = when (tipoSeleccionado) {
            1 -> servicio.listarActividades().filterIsInstance<Tarea>()
            2 -> servicio.listarActividades().filterIsInstance<Evento>()
            else -> {
                salida.mostrar("\nOpción no válida.")
                return
            }
        }

        mostrarActividades(actividadesFiltradas)
    }

    /**
     * Filtra las actividades por su estado.
     */
    private fun filtrarPorEstado() {
        salida.mostrar("\nSelecciona el estado de la actividad a filtrar:")
        salida.mostrar("1. ABIERTA")
        salida.mostrar("2. EN PROGRESO")
        salida.mostrar("3. FINALIZADA")
        val estadoSeleccionado = salida.leerNum()

        val estado = when (estadoSeleccionado) {
            1 -> Estado.ABIERTA
            2 -> Estado.EN_PROGRESO
            3 -> Estado.FINALIZADA
            else -> {
                salida.mostrar("\nOpción no válida.")
                return
            }
        }

        val actividadesFiltradas = servicio.listarActividades().filter { it is Tarea && it.estado == estado }
        mostrarActividades(actividadesFiltradas)
    }

    /**
     * Filtra las actividades por una etiqueta proporcionada por el usuario.
     */
    private fun filtrarPorEtiquetas() {
        salida.mostrar("\nIntroduce la etiqueta a filtrar: ")
        val etiqueta = salida.leerString()

        val actividadesFiltradas = servicio.listarActividades().filter {
            (it is Tarea || it is Evento) && it.obtenerEtiquetas().contains(etiqueta)
        }

        mostrarActividades(actividadesFiltradas)
    }

    /**
     * Muestra por pantalla el detalle de una lista de actividades.
     * @param actividades Lista de actividades a mostrar.
     */
    private fun mostrarActividades(actividades: List<Actividad>) {
        if (actividades.isEmpty()) {
            salida.mostrar("\nNo se encontraron actividades con los filtros seleccionados.")
        } else {
            salida.mostrar("\nActividades filtradas:")
            actividades.forEach { actividad ->
                salida.mostrar(actividad.obtenerDetalle())
            }
        }
    }

    /**
     * Permite consultar las tareas asignadas a un usuario.
     */
    private fun consultarTareasUsuario() {
        salida.mostrar("\nIntroduce el nombre del usuario para consultar sus tareas: ")
        val nombreUsuario = salida.leerString()

        val usuario = servicioUsuario.obtenerUsuarioPorNombre(nombreUsuario)
        if (usuario != null) {
            val tareas = servicioUsuario.obtenerTareasPorUsuario(usuario)
            if (tareas.isEmpty()) {
                salida.mostrar("\nEl usuario no tiene tareas asignadas.")
            } else {
                salida.mostrar("\nTareas asignadas a $nombreUsuario:")
                tareas.forEach { tarea ->
                    salida.mostrar(tarea.obtenerDetalle())
                }
            }
        } else {
            salida.mostrar("\nNo se encontró un usuario con ese nombre.")
        }
    }
}