import common.IOUtils;

import java.util.List;

public class Day2 {
    private static final String INPUT_FILE_NAME = "input.txt";

    public static void main(String[] args) {
        try {
            new Day2().run();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        List<Game> games = IOUtils.readTrimmedLines(getClass().getResource(INPUT_FILE_NAME), new GameParser());
        part1(games);
        part2(games);
    }

    private void part1(List<Game> games) {
        CubeSubset bagContent = new CubeSubset(12, 13, 14);
        int sum = games.stream().filter(game -> isPossible(game, bagContent)).mapToInt(Game::id).sum();
        System.out.println("[Part 1] Possible games sum: " + sum);
    }

    private boolean isPossible(Game game, CubeSubset bagContent) {
        return game.subsets().stream().noneMatch(bagContent::isLowerThan);
    }

    private void part2(List<Game> games) {
        int sum = games.stream().map(this::minimumSet).mapToInt(CubeSubset::power).sum();
        System.out.println("[Part 2] Minimum sets power sum: " + sum);
    }

    private CubeSubset minimumSet(Game game) {
        return game.subsets().stream().reduce(new CubeSubset(0, 0, 0), CubeSubset::max);
    }
}
