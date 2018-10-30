package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringUtils {

    private static final String DELIMITER_REGEX = "[\\s(),.;]+";

    public static double calculateLCS(String sentence1, String sentence2, boolean...compare) {
        sentence1 = "pre " + sentence1;
        sentence2 = "pre " + sentence2;

        String[] arr1 = sentence1.toLowerCase().trim().split(DELIMITER_REGEX);
        String[] arr2 = sentence2.toLowerCase().trim().split(DELIMITER_REGEX);

//        for (int i = 1; i < arr1.length; i++) {
//            System.out.println(arr1[i]);
//        }

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
        double sampleLength = Math.min(arr1.length - 1, arr2.length - 1);

        // if the gap of number of words is relatively large, then they must be 2 different words
        // => then calculate the average lengths instead of min length
        if (Math.abs(arr1.length - arr2.length) * 1.0 / Math.min(arr1.length, arr2.length) > 0.5) {
            sampleLength = avg(arr1.length, arr2.length);
        }

        if (compare.length > 0 && compare[0]) {
            return commonLength / sampleLength;
        } else {
            return commonLength;
        }
    }

    private static double avg(int...n) {
        double rs = 0;
        for (int i : n) {
            rs += i;
        }
        return rs / n.length;
    }

    public static void main(String[] args) {
        String a = "Sách Bài Tập Theo Phương Pháp Montessori - Phát Triển Trí Tuệ Và Khả Năng Toán Học Cho Trẻ";
        String b = "Cấu Trúc Dữ Liệu Và Thuật Toán (Phân Tích Và Cài Đặt Trên C/C++) - Tập 2";

        double rs = calculateLCSubstring(a, b, true);
        System.out.println(rs);
    }

    //https://en.wikipedia.org/wiki/Longest_common_substring_problem
    public static double calculateLCSubstring(String sentence1, String sentence2, boolean...compare) {
        sentence1 = "pre " + sentence1;
        sentence2 = "pre " + sentence2;

        String[] S = sentence1.toLowerCase().trim().split(DELIMITER_REGEX);
        String[] T = sentence2.toLowerCase().trim().split(DELIMITER_REGEX);

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

        double commonLength =  ret.size();
        double sampleLength = Math.min(S.length - 1, T.length - 1);

        // if the gap of number of words is relatively large, then they must be 2 different words
        // => then calculate the average lengths instead of min length
        if (Math.abs(S.length - T.length) * 1.0 / Math.min(S.length, T.length) > 0.5) {
            sampleLength = avg(S.length, T.length);
        }

        if (compare.length > 0 && compare[0]) {
            return commonLength / sampleLength;
        } else {
            return commonLength;
        }
    }
}
