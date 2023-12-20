package common.math;

import java.math.BigInteger;

public final class MathUtils {

    private MathUtils() {
    }

    public static long lcm(long la, long lb) {
        BigInteger a = BigInteger.valueOf(la);
        BigInteger b = BigInteger.valueOf(lb);
        return a.multiply(b.divide(b.gcd(a))).longValue();
    }
}
