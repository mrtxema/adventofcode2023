import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public final class IOUtils {

    private IOUtils() {
    }

    public static List<String> readLines(URL resource) throws IOException {
        return readLines(resource, Function.identity(), Optional::of);
    }

    public static List<String> readTrimmedLines(URL resource) throws IOException {
        return readLines(resource, String::trim, Optional::of);
    }

    public static <T> List<T> readLines(URL resource, Parser<T> parser) throws IOException {
        return readLines(resource, Function.identity(), parser);
    }

    public static <T> List<T> readTrimmedLines(URL resource, Parser<T> parser) throws IOException {
        return readLines(resource, String::trim, parser);
    }

    private static <T> List<T> readLines(URL resource, Function<String, String> transformer, Parser<T> parser) throws IOException {
        try (Stream<String> lines = Files.lines(getFilePath(resource))) {
            return lines.map(transformer).map(parser::parse).flatMap(Optional::stream).toList();
        } catch (java.io.IOException e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    private static Path getFilePath(URL resource) {
        if (resource == null) {
            throw new IOException("Missing file: " + resource);
        }
        try {
            return Paths.get(resource.toURI());
        } catch (URISyntaxException e) {
            throw new IOException(e.getMessage(), e);
        }
    }
}
