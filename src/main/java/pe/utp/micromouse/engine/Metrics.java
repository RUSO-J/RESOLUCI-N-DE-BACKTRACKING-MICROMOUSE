package pe.utp.micromouse.engine;

import pe.utp.micromouse.model.Position;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class Metrics {
    private int steps;
    private int backtracks;
    private int invalidMoves;
    private long decisionNanos;
    private long startedNanos;
    private long finishedNanos;
    private boolean reachedGoal;
    private final Set<Position> explored = new HashSet<Position>();

    public void reset(Position start) {
        steps = 0;
        backtracks = 0;
        invalidMoves = 0;
        decisionNanos = 0L;
        startedNanos = System.nanoTime();
        finishedNanos = 0L;
        reachedGoal = false;
        explored.clear();
        explored.add(start);
    }

    public void addDecisionTime(long nanos) { decisionNanos += nanos; }
    public void incrementSteps() { steps++; }
    public void incrementBacktracks() { backtracks++; }
    public void incrementInvalidMoves() { invalidMoves++; }
    public void markExplored(Position position) { explored.add(position); }

    public void markGoal() {
        reachedGoal = true;
        finishedNanos = System.nanoTime();
    }

    public void stop() {
        if (finishedNanos == 0L) finishedNanos = System.nanoTime();
    }

    public int getSteps() { return steps; }
    public int getBacktracks() { return backtracks; }
    public int getInvalidMoves() { return invalidMoves; }
    public long getDecisionNanos() { return decisionNanos; }
    public boolean isReachedGoal() { return reachedGoal; }
    public int getExploredCount() { return explored.size(); }
    public Set<Position> getExplored() { return Collections.unmodifiableSet(explored); }

    public long getWallClockNanos() {
        long end = finishedNanos == 0L ? System.nanoTime() : finishedNanos;
        return Math.max(0L, end - startedNanos);
    }
}
