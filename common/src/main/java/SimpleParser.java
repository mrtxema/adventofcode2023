import java.util.Optional;

public abstract class SimpleParser<T> implements Parser<T> {

    @Override
    public Optional<T> parse(String line) {
        return Optional.of(nonNullParse(line));
    }

    protected abstract T nonNullParse(String line);
}
