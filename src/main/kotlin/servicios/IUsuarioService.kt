package es.prog2425.taskmanager.servicios

import es.prog2425.taskmanager.modelo.Tarea
import es.prog2425.taskmanager.modelo.Usuario

interface IUsuarioService {
    fun crearUsuario(nombre: String): Usuario
    fun asignarTareaAUsuario(usuario: Usuario, tarea: Tarea)
    fun obtenerTareasPorUsuario(usuario: Usuario): List<Tarea>
    fun obtenerUsuarioPorNombre(nombre: String): Usuario?

}