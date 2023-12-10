import java.util.List;

public class PipeDiagramParser {


    public PipeDiagram parse(List<String> lines) {
        int numColumns = lines.stream().mapToInt(String::length).max().orElse(0);
        Pipe[][] diagram = new Pipe[lines.size()][numColumns];
        Position startingPosition = null;
        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                char symbol = line.charAt(x);
                diagram[y][x] = Pipe.parse(symbol).orElse(null);
                if (symbol == 'S') {
                    startingPosition = new Position(x, y);
                }
            }
        }
        return new PipeDiagram(diagram, startingPosition);
    }
}
