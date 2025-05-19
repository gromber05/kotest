/**
 * Paquete que contiene los servicios del gestor de tareas.
 */
package es.prog2425.taskmanager.servicios

import es.prog2425.taskmanager.datos.IActividadRepository
import es.prog2425.taskmanager.modelo.*

/**
 * Servicio que gestiona las operaciones relacionadas con actividades,
 * como la creación de tareas, eventos y la modificación de su estado.
 *
 * @property repositorio Repositorio para acceder y almacenar las actividades.
 */
class ActividadService(private val repositorio: IActividadRepository) {

    /**
     * Crea un nuevo evento con la descripción, fecha y ubicación proporcionadas
     * y lo añade al repositorio.
     *
     * @param descripcion Descripción del evento.
     * @param fecha Fecha del evento en formato String.
     * @param ubicacion Ubicación del evento.
     */
    fun crearEvento(descripcion: String, fecha: String, ubicacion: String) {
        val evento = Evento.crearInstancia(descripcion, fecha, ubicacion)
        repositorio.agregarEvento(evento)
    }

    /**
     * Crea una nueva tarea con la descripción proporcionada
     * y la añade al repositorio.
     *
     * @param descripcion Descripción de la tarea.
     */
    fun crearTarea(descripcion: String) {
        val tarea = Tarea.crearInstancia(descripcion)
        repositorio.agregarTarea(tarea)
    }

    /**
     * Asocia una subtarea a una tarea principal.
     *
     * @param tareaPrincipal Tarea a la que se le añadirá la subtarea.
     * @param subtarea Subtarea que se añadirá a la tarea principal.
     */
    fun asociarSubtarea(tareaPrincipal: Tarea, subtarea: Tarea) {
        tareaPrincipal.agregarSubtarea(subtarea)
    }

    /**
     * Cambia el estado de una tarea y guarda el cambio en su historial.
     *
     * @param tarea Tarea cuyo estado se quiere cambiar.
     * @param nuevoEstado Nuevo estado a asignar.
     */
    fun cambiarEstadoTarea(tarea: Tarea, nuevoEstado: Estado) {
        tarea.cambiarEstadoConHistorial(nuevoEstado)
    }

    /**
     * Devuelve una lista de todas las actividades almacenadas en el repositorio.
     *
     * @return Lista de actividades (tareas y eventos).
     */
    fun listarActividades(): List<Actividad> = repositorio.obtenerActividades()
}
