package utils;

import model.LCSResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringUtils {

    private static final String DELIMITER_REGEX = "[\\s(),.;\\-]+";

    private static final List<String> MISCELLANEOUS_WORDS = Arrays.asList(
            "tái bản", "bản đặc biệt", "bìa mềm", "tặng kèm", "số lượng có hạn", "2017", "2018");

    // https://en.wikipedia.org/wiki/Longest_common_subsequence_problem
    public static LCSResult calculateLCSubsequence(String sentence1, String sentence2) {
        sentence1 = "pre " + preProcessString(sentence1);
        sentence2 = "pre " + preProcessString(sentence2);

        String[] arr1 = sentence1.split(DELIMITER_REGEX);
        String[] arr2 = sentence2.split(DELIMITER_REGEX);

        int m[][] = new int[arr1.length][arr2.length];

        for (int i = 1; i < arr1.length; i++) {
            for (int j = 1; j < arr2.length; j++) {
                if (arr1[i].equals(arr2[j])) {
                    m[i][j] = m[i - 1][j - 1] + 1;
                } else {
                    m[i][j] = Integer.max(m[i][j - 1], m[i - 1][j]);
                }
            }
        }

        double commonLength =  m[arr1.length - 1][arr2.length - 1];

        return new LCSResult(arr1.length - 1, arr2.length - 1, commonLength);
    }

    //https://en.wikipedia.org/wiki/Longest_common_substring_problem
    public static LCSResult calculateLCSubstring(String sentence1, String sentence2) {
        sentence1 = "pre " + preProcessString(sentence1);
        sentence2 = "pre " + preProcessString(sentence2);

        String[] S = sentence1.split(DELIMITER_REGEX);
        String[] T = sentence2.split(DELIMITER_REGEX);

        int L[][] = new int[S.length][T.length];
        int z = 0;

        List<String> ret = new ArrayList<>();

        for (int i = 1; i < S.length; i++) {
            for (int j = 1; j < T.length; j++) {
                if (S[i].equals(T[j])) {
                    if (i == 1 || j == 1) {
                        L[i][j] = 1;
                    } else {
                        L[i][j] = L[i - 1][j - 1] + 1;
                    }

                    if (L[i][j] > z) {
                        z = L[i][j];
                        ret.clear();
                        ret.addAll(Arrays.asList(S).subList(i - z + 1, i + 1));
                    } else if (L[i][j] == z) {
                        ret.addAll(Arrays.asList(S).subList(i - z + 1, i + 1));
                    }
                } else {
                    L[i][j] = 0;
                }
            }
        }

//        System.out.println(ret.stream().collect(Collectors.joining(" ")));

        return new LCSResult(S.length - 1, T.length - 1, z);
    }

    private static String preProcessString(String s) {
        s = s.trim().toLowerCase();
        for (String miscellaneousWord : MISCELLANEOUS_WORDS) {
            s = s.replace(miscellaneousWord, "");
        }
        return s;
    }

    public static void main(String[] args) {
        String a = "Dep (so thang)";
        String b = "Elle - Dep (so thang)";

        double rs = calculateLCSubsequence(a, b).printOutInfo().calculateSimilarity();
        System.out.println(rs);
    }
}
