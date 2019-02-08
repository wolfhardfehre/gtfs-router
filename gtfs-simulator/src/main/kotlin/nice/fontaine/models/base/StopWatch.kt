package nice.fontaine.models.base

class StopWatch {
    var currentTime: Long = 0

    fun start(task: String) {
        print("*** TASK: $task")
        currentTime = System.currentTimeMillis()
    }

    fun stop() {
        val tmp = System.currentTimeMillis()
        println(" --> ${(tmp - currentTime) / 1000.0} sec")
        currentTime = tmp
    }
}
