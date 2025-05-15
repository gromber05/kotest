package es.prog2425.taskmanager.datos

import es.prog2425.taskmanager.modelo.Actividad
import es.prog2425.taskmanager.modelo.Evento
import es.prog2425.taskmanager.modelo.Tarea

class ActividadRepository: IActividadRepository {
    private val listaActividades: MutableList<Actividad> = mutableListOf()

    // Agrega un evento
    override fun agregarEvento(evento: Evento) {
        listaActividades.add(evento)
    }

    // Agrega una tarea
    override fun agregarTarea(tarea: Tarea) {
        listaActividades.add(tarea)
    }

    // Devuelve una lista con todas las actividades
    override fun obtenerActividades(): List<Actividad> = listaActividades
}