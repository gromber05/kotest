import es.prog2425.taskmanager.datos.ActividadRepository
import es.prog2425.taskmanager.modelo.Actividad
import es.prog2425.taskmanager.modelo.Estado
import es.prog2425.taskmanager.modelo.Tarea
import es.prog2425.taskmanager.servicios.ActividadService
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class ActividadServiceTest :
    DescribeSpec({

        val mockRepositorio = mockk<ActividadRepository>(relaxed = true)
        val servicio = ActividadService(mockRepositorio)

        describe("crearEvento") {

            it("debería crear un evento correctamente y almacenarlo en el repositorio") {
                val descripcion = "Evento de prueba"
                val fecha = "12-05-2025"
                val ubicacion = "Ubicación de prueba"

                servicio.crearEvento(descripcion, fecha, ubicacion)

                verify { mockRepositorio.agregarEvento(any()) }
            }

            it("debería lanzar una excepción si el formato de la fecha es incorrecto") {
                val descripcion = "Evento de prueba"
                val fecha = "2025-05-12" // Formato incorrecto esperado
                val ubicacion = "Ubicación de prueba"

                shouldThrow<IllegalArgumentException> {
                    servicio.crearEvento(descripcion, fecha, ubicacion)
                }
            }
        }

        describe("crearTarea") {

            it("debería crear una tarea válida y guardarla en el repositorio") {
                val descripcion = "Tarea de ejemplo"

                servicio.crearTarea(descripcion)

                verify { mockRepositorio.agregarTarea(any()) }
            }

            it("debería lanzar una excepción si la descripción está vacía") {
                val descripcion = ""

                shouldThrow<IllegalArgumentException> {
                    servicio.crearTarea(descripcion)
                }
            }
        }

        describe("asociarSubtarea") {

            it("debería asociar correctamente una subtarea a una tarea principal") {
                val tareaPrincipal = mockk<Tarea>(relaxed = true)
                val subtarea = mockk<Tarea>()

                servicio.asociarSubtarea(tareaPrincipal, subtarea)

                verify { tareaPrincipal.agregarSubtarea(subtarea) }
            }

            it("debería lanzar una excepción si la subtarea es nula") {
                val tareaPrincipal = mockk<Tarea>(relaxed = true)
                val subtarea: Tarea? = null

                shouldThrow<NullPointerException> {
                    servicio.asociarSubtarea(tareaPrincipal, subtarea!!)
                }
            }
        }

        describe("cambiarEstadoTarea") {

            it("debería cambiar correctamente el estado de una tarea") {
                val tarea = mockk<Tarea>(relaxed = true)
                val nuevoEstado = mockk<Estado>()

                servicio.cambiarEstadoTarea(tarea, nuevoEstado)

                verify { tarea.cambiarEstadoConHistorial(nuevoEstado) }
            }

            it("debería lanzar una excepción si la tarea es nula") {
                val tarea: Tarea? = null
                val nuevoEstado = mockk<Estado>()

                shouldThrow<NullPointerException> {
                    servicio.cambiarEstadoTarea(tarea!!, nuevoEstado)
                }
            }
        }

        describe("listarActividades") {

            it("debería devolver una lista de actividades si hay datos en el repositorio") {
                val actividadesEsperadas = listOf(mockk<Actividad>(), mockk<Actividad>())
                every { mockRepositorio.obtenerActividades() } returns actividadesEsperadas

                val resultado = servicio.listarActividades()

                resultado shouldBe actividadesEsperadas
            }

            it("debería devolver una lista vacía si no hay actividades en el repositorio") {
                every { mockRepositorio.obtenerActividades() } returns emptyList()

                val resultado = servicio.listarActividades()

                resultado shouldBe emptyList()
            }
        }
    })
