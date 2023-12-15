import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class Platform {
    private static final char ROUNDED_ROCK = 'O';
    private static final char CUBE_ROCK = '#';
    private static final char EMPTY = '.';
    private final char[][] map;
    private final char[][] work;

    public Platform(char[][] map) {
        this(map, cloneMap(map));
    }

    private Platform(char[][] map, char[][] work) {
        this.map = map;
        this.work = work;
    }

    private static char[][] cloneMap(char[][] aMap) {
        char[][] work = new char[aMap.length][];
        for (int i = 0; i < aMap.length; i++) {
            work[i] = new char[aMap[i].length];
        }
        return work;
    }

    public Platform spin(int cycles) {
        Platform result = this;
        Map<List<Position>, Integer> cycleCache = new HashMap<>();
        cycleCache.put(result.getRoundedRocksPositions(), 0);
        int i = 0;
        while (i < cycles) {
            result = result.tilt(Direction.NORTH).tilt(Direction.WEST).tilt(Direction.SOUTH).tilt(Direction.EAST);
            List<Position> key = result.getRoundedRocksPositions();
            Integer previousCycle = cycleCache.get(key);
            if (previousCycle != null) {
                int diff = i + 1 - previousCycle;
                int remaining = cycles - i - 1;
                i = cycles - (remaining % diff);
            } else {
                i++;
                cycleCache.put(key, i);
            }
        }
        return result;
    }

    public Platform tilt(Direction direction) {
        switch (direction) {
            case NORTH -> tiltNorth();
            case EAST -> tiltEast();
            case SOUTH -> tiltSouth();
            case WEST -> tiltWest();
        }
        return new Platform(work, map);
    }

    private void tiltNorth() {
        for (int x = 0; x < work[0].length; x++) {
            int spaces = 0;
            for (int y = 0; y < work.length; y++) {
                switch (map[y][x]) {
                    case ROUNDED_ROCK -> work[y - spaces][x] = ROUNDED_ROCK;
                    case CUBE_ROCK -> {
                        while (spaces > 0) {
                            work[y - spaces--][x] = EMPTY;
                        }
                        work[y][x] = CUBE_ROCK;
                        spaces = 0;
                    }
                    case EMPTY -> spaces++;
                }
            }
            while (spaces > 0) {
                work[work.length - spaces--][x] = EMPTY;
            }
        }
    }

    private void tiltEast() {
        for (int y = 0; y < work.length; y++) {
            int spaces = 0;
            for (int x = work[y].length - 1; x >= 0; x--) {
                switch (map[y][x]) {
                    case ROUNDED_ROCK -> work[y][x + spaces] = ROUNDED_ROCK;
                    case CUBE_ROCK -> {
                        while (spaces > 0) {
                            work[y][x + spaces--] = EMPTY;
                        }
                        work[y][x] = CUBE_ROCK;
                        spaces = 0;
                    }
                    case EMPTY -> spaces++;
                }
            }
            while (spaces > 0) {
                work[y][spaces-- - 1] = EMPTY;
            }
        }
    }

    private void tiltSouth() {
        for (int x = 0; x < work[0].length; x++) {
            int spaces = 0;
            for (int y = work.length - 1; y >= 0; y--) {
                switch (map[y][x]) {
                    case ROUNDED_ROCK -> work[y + spaces][x] = ROUNDED_ROCK;
                    case CUBE_ROCK -> {
                        while (spaces > 0) {
                            work[y + spaces--][x] = EMPTY;
                        }
                        work[y][x] = CUBE_ROCK;
                        spaces = 0;
                    }
                    case EMPTY -> spaces++;
                }
            }
            while (spaces > 0) {
                work[spaces-- - 1][x] = EMPTY;
            }
        }
    }

    private void tiltWest() {
        for (int y = 0; y < work.length; y++) {
            int spaces = 0;
            for (int x = 0; x < work[y].length; x++) {
                switch (map[y][x]) {
                    case ROUNDED_ROCK -> work[y][x - spaces] = ROUNDED_ROCK;
                    case CUBE_ROCK -> {
                        while (spaces > 0) {
                            work[y][x - spaces--] = EMPTY;
                        }
                        work[y][x] = CUBE_ROCK;
                        spaces = 0;
                    }
                    case EMPTY -> spaces++;
                }
            }
            while (spaces > 0) {
                work[y][work[y].length - spaces--] = EMPTY;
            }
        }
    }

    public int getRoundedRocksLoad() {
        return getRoundedRocksPositions().stream().mapToInt(Position::y).sum();
    }

    private List<Position> getRoundedRocksPositions() {
        List<Position> result = new ArrayList<>();
        for (int x = 0; x < map[0].length; x++) {
            for (int y = 0; y < map.length; y++) {
                if (map[y][x] == ROUNDED_ROCK) {
                    result.add(new Position((short) x, (short) (map.length - y)));
                }
            }
        }
        return result;
    }

    private record Position(short x, short y) {
    }
}
