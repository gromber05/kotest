package es.prog2425.taskmanager.modelo

class GeneradorID {
    companion object {
        private val mapaIdEventos: MutableMap<String, Int> = mutableMapOf()
    }

    /*
     * Genera un identificador único basado en:
     * - La fecha convertida a `YYYYMMDD` (año, mes y día).
     * - Un contador incremental que asegura un valor único en caso de múltiples eventos en la misma fecha.
     *
     * El contador se almacena en `mapaIdEventos`, que mantiene el número de eventos generados para cada día.
     *
     * @param fecha La fecha en formato `dd-MM-yyyy` utilizada para generar el identificador único.
     * @return Un identificador único en formato `YYYYMMDDN`, donde `N` es el contador del evento en ese día.
     */

    fun generarId(fecha: String): Int {
        val identificador: Int

        if (comprobarFechaContador(fecha)) {
            identificador = mapaIdEventos[fecha]!! + 1
        } else {
            identificador = 1
            mapaIdEventos.put(fecha, identificador)
        }

        val fechaSeparada = fecha.split("-")

        return ("${fechaSeparada[2]}${fechaSeparada[1]}${fechaSeparada[0]}${identificador}").toInt()
    }

    private fun comprobarFechaContador(fecha: String): Boolean {
        for ((fechaMapa, contador) in mapaIdEventos) {
            if (fechaMapa == fecha) return true
        }
        return false
    }
}