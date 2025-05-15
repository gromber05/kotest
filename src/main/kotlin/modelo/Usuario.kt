package es.prog2425.taskmanager.modelo

class Usuario(val nombre: String) {
    val tareasAsignadas: MutableList<Tarea> = mutableListOf()

    // Metodo para asignar una tarea a este usuario
    fun asignarTarea(tarea: Tarea) {
        tareasAsignadas.add(tarea)
    }

    // Metodo para obtener las tareas asignadas
    fun obtenerTareasAsignadas(): List<Tarea> = tareasAsignadas
}