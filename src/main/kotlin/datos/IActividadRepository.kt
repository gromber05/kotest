package es.prog2425.taskmanager.datos

import es.prog2425.taskmanager.modelo.Actividad
import es.prog2425.taskmanager.modelo.Evento
import es.prog2425.taskmanager.modelo.Tarea

interface IActividadRepository {
    fun agregarEvento(evento: Evento)
    fun agregarTarea(tarea: Tarea)
    fun obtenerActividades(): List<Actividad>
}