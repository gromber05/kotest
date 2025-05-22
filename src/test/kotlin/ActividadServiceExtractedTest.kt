import es.prog2425.taskmanager.datos.ActividadRepository
import es.prog2425.taskmanager.servicios.ActividadService
import es.prog2425.taskmanager.servicios.GestorActividades
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.mockk
import io.mockk.verify

class ActividadServiceExtractedTest: DescribeSpec
    ({
        describe("cerrarTarea") {

            val mockRepositorio = mockk<ActividadRepository>(relaxed = true)
            val servicio = ActividadService(mockRepositorio)

            it("debería cerrar una tarea con la función extraída para las pruebas unitarias"){

                val descripcion = "Tarea de ejemplo"

                servicio.crearTarea(descripcion)

                verify { mockRepositorio.agregarTarea( any()) }
            }

        }
})