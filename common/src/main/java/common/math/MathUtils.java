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

    public static int mod(int n, int modulo) {
        int result = n % modulo;
        return result < 0 ? modulo + result : result;
    }
}
