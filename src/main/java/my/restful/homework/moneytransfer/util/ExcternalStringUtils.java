package my.restful.homework.moneytransfer.util;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExcternalStringUtils {

    static Map<Character, Integer> process(String s) throws IllegalArgumentException {
        if (s == null) throw new IllegalArgumentException("String can not be null");

        //Using default iteration
        Map<Character, Integer> result = new HashMap<>();
        char[] chars = s.toCharArray();
        for (char c : chars) {
            result.merge(c, 1, (prev, current) -> prev + current);
        }
        return result;

        //Using Java 8 Stream API
//        return s.chars()
//                .peek(System.out::println)
//                .mapToObj(i -> (char) i)
//                .collect(Collectors.groupingBy(i -> i, Collectors.counting()));
    }
}
