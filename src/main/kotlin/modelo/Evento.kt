package es.prog2425.taskmanager.modelo

import es.prog2425.taskmanager.utils.Utilidades

class Evento private constructor(
    descripcion: String,
    val fecha: String,
    private val ubicacion: String
): Actividad(descripcion) {

    companion object {
        fun crearInstancia(descripcion: String, fecha: String, ubicacion: String) = Evento(descripcion, fecha, ubicacion)
    }

    init {
        require(Utilidades().esFechaValida(fecha)) { "\nLa fecha de tener el siguiente formato (dd-MM-yyyy)\n" }
        require(ubicacion.isNotEmpty())
    }

    override fun obtenerDetalle(): String = super.obtenerDetalle() + "Fecha: $fecha, Ubicación: $ubicacion"
}