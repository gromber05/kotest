package es.prog2425.taskmanager.datos

import es.prog2425.taskmanager.modelo.Usuario

class UsuarioRepository : IUsuarioRepository {
    private val usuarios: MutableList<Usuario> = mutableListOf()

    override fun agregarUsuario(usuario: Usuario) {
        usuarios.add(usuario)
    }

    override fun obtenerUsuarios(): List<Usuario> = usuarios

    override fun obtenerUsuarioPorNombre(nombre: String): Usuario? {
        return usuarios.find { it.nombre == nombre }
    }
}