package es.prog2425.taskmanager.modelo

import es.prog2425.taskmanager.utils.Utilidades

abstract class Actividad(protected val descripcion: String) {
    protected val id: Int
    protected val fechaCreacion: String
    var etiquetas: MutableList<String> = mutableListOf()

    init {
        require(descripcion.isNotEmpty()) { "La descripcion debe contener texto." }
        fechaCreacion = Utilidades().obtenerFechaActual()
        id = GeneradorID().generarId(fechaCreacion)
    }

    fun agregarEtiqueta(etiqueta: String) {
        if (etiqueta.isNotBlank()) {
            etiquetas.add(etiqueta.trim())
        }
    }

    open fun obtenerDetalle(): String {
        val etiquetasStr = if (etiquetas.isNotEmpty()) "Etiquetas: ${etiquetas.joinToString(", ")}" else "Sin etiquetas"
        return "Descripción: $descripcion\n$etiquetasStr"
    }

    fun obtenerEtiquetas(): List<String> = etiquetas
}