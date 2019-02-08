package nice.fontaine.features

import assertk.assert
import assertk.assertions.isEqualTo
import nice.fontaine.models.csv.CsvShapePoint
import nice.fontaine.models.csv.CsvStop
import nice.fontaine.models.csv.CsvStopTime
import org.junit.Before
import org.junit.Test
import org.jxmapviewer.viewer.GeoPosition

class PathTest {
    private lateinit var path: Path

    @Before fun setUp() {
        path = Path(stopTimes(), shapePoints())
    }

    @Test fun `should get amount of stop times`() {
        assert(path.times.size).isEqualTo(4)
    }

    @Test fun `should get amount of shape points`() {
        assert(path.points.size).isEqualTo(9)
    }

    @Test fun `should be at half a degree after one steps`() {
        val times = stopTimes("06:00:00", "06:00:06")
        val points = shapePoints(0.0, 1.0, 2.0, 3.0)
        path = Path(times, points)

        path.step()

        assert(path.currentPosition()).isEqualTo(GeoPosition(0.5, 0.0))
    }

    @Test fun `should be at one degree after two steps`() {
        val times = stopTimes("06:00:00", "06:00:06")
        val points = shapePoints(0.0, 1.0, 2.0, 3.0)
        path = Path(times, points)

        path.step()
        path.step()

        assert(path.currentPosition()).isEqualTo(GeoPosition(1.0, 0.0))
    }

    @Test fun `should be at two and a half degree after five steps`() {
        val times = stopTimes("06:00:00", "06:00:06")
        val points = shapePoints(0.0, 1.0, 2.0, 3.0)
        path = Path(times, points)

        (1..5).forEach { _ -> path.step() }

        assert(path.currentPosition()).isEqualTo(GeoPosition(2.5, 0.0))
    }

    @Test fun `should be at three degree after six steps`() {
        val times = stopTimes("06:00:00", "06:00:06")
        val points = shapePoints(0.0, 1.0, 2.0, 3.0)
        path = Path(times, points)

        (1..6).forEach { _ -> path.step() }

        assert(path.currentPosition()).isEqualTo(GeoPosition(3.0, 0.0))
    }

    @Test fun `should be still at three degree after ten steps`() {
        val times = stopTimes("06:00:00", "06:00:06")
        val points = shapePoints(0.0, 1.0, 2.0, 3.0)
        path = Path(times, points)

        (1..10).forEach { _ -> path.step() }

        assert(path.currentPosition()).isEqualTo(GeoPosition(3.0, 0.0))
    }

    private fun shapePoints() = shapePoints(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)

    private fun stopTimes(): List<CsvStopTime> {
        val times = stopTimes("06:00:00", "08:00:00", "10:00:00", "12:00:00")
        val stops = stops(0.0, 3.0, 6.0, 8.0)
        times.forEachIndexed { index, stopTime -> stopTime.stop = stops[index] }
        return times
    }

    private fun shapePoints(vararg lats: Double) = lats.map { lat -> shapePoint(lat) }

    private fun shapePoint(value: Double): CsvShapePoint {
        val point = CsvShapePoint()
        point.lat = value
        return point
    }

    private fun stopTimes(vararg times: String) = times.map { time -> time(time) }

    private fun time(value: String): CsvStopTime {
        val time = CsvStopTime()
        time.arrivalTime = value
        time.departureTime = value
        return time
    }

    private fun stops(vararg lats: Double) = lats.map { lat -> stop(lat) }

    private fun stop(value: Double): CsvStop {
        val stop = CsvStop()
        stop.lat = value
        return stop
    }
}
