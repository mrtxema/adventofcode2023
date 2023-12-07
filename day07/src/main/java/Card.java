import java.util.Arrays;
import java.util.Optional;

public enum Card {
    A("A", 14),
    K("K", 13),
    Q("Q", 12),
    J("J", 11, 1),
    T("T", 10),
    N9("9", 9),
    N8("8", 8),
    N7("7", 7),
    N6("6", 6),
    N5("5", 5),
    N4("4", 4),
    N3("3", 3),
    N2("2", 2);

    private final String code;
    private final int value;
    private final int alternativeValue;

    Card(String code, int value) {
        this(code, value, value);
    }

    Card(String code, int value, int alternativeValue) {
        this.code = code;
        this.value = value;
        this.alternativeValue = alternativeValue;
    }

    public static Optional<Card> parse(String code) {
        return Arrays.stream(Card.values()).filter(card -> card.code.equals(code)).findAny();
    }

    public int getValue() {
        return value;
    }

    public int getAlternativeValue() {
        return alternativeValue;
    }

    @Override
    public String toString() {
        return code;
    }
}
