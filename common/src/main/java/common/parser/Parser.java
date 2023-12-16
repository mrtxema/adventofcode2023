package common.parser;

import java.util.Optional;

@FunctionalInterface
public interface Parser<T> {

    Optional<T> parse(String line);
}
