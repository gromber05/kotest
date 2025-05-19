package es.prog2425.taskmanager.servicios

import es.prog2425.taskmanager.datos.IUsuarioRepository
import es.prog2425.taskmanager.datos.UsuarioRepository
import es.prog2425.taskmanager.modelo.Tarea
import es.prog2425.taskmanager.modelo.Usuario

/**
 * Servicio encargado de gestionar las operaciones relacionadas con usuarios.
 *
 * @property repositorio Repositorio de usuarios utilizado para el almacenamiento y recuperación.
 */
class UsuarioService(
    private val repositorio: IUsuarioRepository = UsuarioRepository()
) : IUsuarioService {

    /**
     * Crea un nuevo usuario y lo agrega al repositorio.
     *
     * @param nombre Nombre del usuario a crear.
     * @return El usuario creado.
     */
    override fun crearUsuario(nombre: String): Usuario {
        val usuario = Usuario(nombre)
        repositorio.agregarUsuario(usuario)
        return usuario
    }

    /**
     * Asigna una tarea a un usuario.
     *
     * @param usuario Usuario al que se asigna la tarea.
     * @param tarea Tarea a asignar.
     */
    override fun asignarTareaAUsuario(usuario: Usuario, tarea: Tarea) {
        tarea.asignarUsuario(usuario)
        usuario.asignarTarea(tarea)
    }

    /**
     * Obtiene la lista de tareas asignadas a un usuario.
     *
     * @param usuario Usuario del que se consultan las tareas.
     * @return Lista de tareas asignadas al usuario.
     */
    override fun obtenerTareasPorUsuario(usuario: Usuario): List<Tarea> {
        return usuario.obtenerTareasAsignadas()
    }

    /**
     * Busca un usuario por su nombre.
     *
     * @param nombre Nombre del usuario a buscar.
     * @return El usuario encontrado o null si no existe.
     */
    override fun obtenerUsuarioPorNombre(nombre: String): Usuario? {
        return repositorio.obtenerUsuarioPorNombre(nombre)
    }
}