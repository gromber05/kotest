package es.prog2425.taskmanager.presentacion

import es.prog2425.taskmanager.modelo.Actividad
import es.prog2425.taskmanager.modelo.Tarea

interface Interfaz {
    fun mostrarMenu()
    fun leerString(): String
    fun leerNum(): Int
    fun mostrar(x: Any)
    fun mostrarActividades(x: List<Actividad>)
    fun mostrarInput(x: Any)
}