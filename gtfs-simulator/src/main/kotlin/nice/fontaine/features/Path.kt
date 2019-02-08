package nice.fontaine.features

import nice.fontaine.extensions.length
import nice.fontaine.models.csv.CsvShapePoint
import nice.fontaine.models.csv.CsvStopTime
import nice.fontaine.extensions.toDuration
import nice.fontaine.extensions.toShapePositions
import nice.fontaine.extensions.toSegments
import nice.fontaine.models.additions.Segment
import nice.fontaine.models.base.TimeRange
import org.jxmapviewer.viewer.GeoPosition

/** Full geometry of a trip **/
class Path(val times: List<CsvStopTime>, val points: List<CsvShapePoint>) {
    private val segments = points.toSegments()
    private val state = State(segments)
    private val stepSecs = segments.length() / times.toDuration()
    private var currentPosition = segments[0].position()
    val timeRange = TimeRange(times[0], times[times.size - 1])
    val positions: MutableList<GeoPosition> by lazy { points.toShapePositions() }

    fun step() {
        state.section += stepSecs
        currentPosition = computePosition(state)
    }

    fun currentPosition() = currentPosition

    private fun computePosition(state: State): GeoPosition {
        state.segment.update(state.section)
        return if (state.isOnSegment()) state.position() else findSegment(state)
    }

    private fun findSegment(state: State): GeoPosition {
        state.section = state.segment.surplus()
        state.counter++
        if (isLastSegment(state)) return takeNextSegment(state)
        else return state.segment.destination()
    }

    private fun takeNextSegment(state: State): GeoPosition {
        state.segment = segments[state.counter]
        return computePosition(state)
    }

    private fun isLastSegment(state: State) = state.counter < segments.size

    inner class State(segments: List<Segment>) {
        var counter = 0
        var section = 0.0
        var segment = segments[counter]

        fun isOnSegment() = segment.isOnSegment()

        fun position() = segment.position()
    }
}

