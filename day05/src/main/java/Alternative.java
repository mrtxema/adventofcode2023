import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Alternative {
    
    public void part2(Almanac any) {
        long t = 0;
        
        String x;
        try {
            x = Files.readString(Path.of(getClass().getResource("input.txt").toURI()));
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }

        String[] parts = x.split("\n\n");
        String a = parts[0];
        String[] xLines = Arrays.stream(parts).skip(1).toArray(String[]::new);

        String[] aValues = a.split(": ")[1].split(" ");
        List<Long> aList = new ArrayList<>();
        for (String value : aValues) {
            aList.add(Long.parseLong(value));
        }

        t = Long.MAX_VALUE;
        for (int i = 0; i < aList.size(); i += 2) {
            Map<String, List<long[]>> y = new HashMap<>();
            List<long[]> seedList = new ArrayList<>();
            seedList.add(new long[]{aList.get(i), aList.get(i) + aList.get(i + 1)});
            y.put("seed", seedList);

            for (String ii : xLines) {
                String[] iiLines = ii.split("\n");
                String q = iiLines[0].split(" ")[0];
                String[] qValues = q.split("-to-");
                q = qValues[0];
                String qq = qValues[1];
                List<long[]> c = y.containsKey(q) ? new ArrayList<>(y.get(q)) : new ArrayList<>();
                while (!c.isEmpty()) {
                    long[] k = c.remove(c.size() - 1);
                    for (int iii = 1; iii < iiLines.length; iii++) {
                        String[] iiiValues = iiLines[iii].split(" ");
                        long[] n = Arrays.copyOf(k, k.length);
                        long iii1 = Long.parseLong(iiiValues[1]);
                        long iiiLast = Long.parseLong(iiiValues[iiiValues.length - 1]);
                        if (iii1 < k[1] && iii1 + iiiLast > k[0]) {
                            if (iii1 <= k[0]) {
                                n[0] = k[0] + Long.parseLong(iiiValues[0]) - iii1;
                            } else {
                                n[0] = Long.parseLong(iiiValues[0]);
                            }
                            if (k[1] <= iii1 + iiiLast) {
                                n[1] = k[1] + Long.parseLong(iiiValues[0]) - iii1;
                            } else {
                                n[1] = iii1 + iiiLast;
                            }
                        }
                        if (!Arrays.equals(n, k)) {
                            if (n[0] != k[0] + Long.parseLong(iiiValues[0]) - iii1) {
                                c.add(new long[]{k[0], iii1});
                            }
                            if (n[1] != k[1] + Long.parseLong(iiiValues[0]) - iii1) {
                                c.add(new long[]{iii1 + iiiLast, k[1]});
                            }
                            List<long[]> qqList = y.getOrDefault(qq, new ArrayList<>());
                            qqList.add(n);
                            y.put(qq, qqList);
                            break;
                        }
                    }
                }
            }

            List<long[]> locationList = y.get("location");
            if (locationList != null) {
                locationList.sort(Comparator.comparingLong(g -> g[0]));
                t = Math.min(t, locationList.get(0)[0]);
            }
        }

        System.out.println(t);
    }
}
