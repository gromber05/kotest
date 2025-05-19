package es.prog2425.taskmanager.presentacion

import es.prog2425.taskmanager.modelo.Actividad

interface Interfaz {
    fun mostrarMenu()

    fun leerString(): String

    fun leerNum(): Int

    fun mostrar(x: Any)

    fun mostrarActividades(x: List<Actividad>)

    fun mostrarInput(x: Any)
}
