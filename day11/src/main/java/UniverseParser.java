import java.util.ArrayList;
import java.util.List;

public class UniverseParser {

    public Universe parse(List<String> lines) {
        List<Galaxy> galaxies = new ArrayList<>();
        int counter = 1;
        int width = 0;
        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            width = Math.max(width, line.length());
            for (int x = 0; x < line.length(); x++) {
                if (line.charAt(x) == '#') {
                    galaxies.add(new Galaxy(counter++, new Position(x, y)));
                }
            }
        }
        return new Universe(galaxies, width, lines.size());
    }
}
