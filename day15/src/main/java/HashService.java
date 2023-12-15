public class HashService {

    public int hash(String s) {
        int value = 0;
        for (int i = 0; i < s.length(); i++) {
            value = ((value + s.charAt(i)) * 17) % 256;
        }
        return value;
    }
}
