package es.prog2425.taskmanager.datos

import es.prog2425.taskmanager.modelo.Usuario

interface IUsuarioRepository {
    fun agregarUsuario(usuario: Usuario)
    fun obtenerUsuarios(): List<Usuario>
    fun obtenerUsuarioPorNombre(nombre: String): Usuario?
}