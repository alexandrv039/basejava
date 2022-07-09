package com.urise.webapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainStream {
    public static void main(String[] args) {
        int[] values = new int[]{7,1,2,3,3,2,3};
        System.out.println(minValue(values));

        List<Integer> integers = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            integers.add(i);
        }

        List<Integer> list = oddOrEven(integers);
        System.out.println(list);
    }

    static int minValue(int[] values) {
        return Arrays.stream(values).distinct().sorted().reduce(0, (a,b)->(a*10+b));
    }

    static List<Integer> oddOrEven(List<Integer> integers) {
        if (integers.stream().mapToDouble(a -> a).sum() % 2 == 0) {
            return integers.stream().filter(a -> a % 2 == 0).collect(Collectors.toList());
        } else return integers.stream().filter(a -> a % 2 == 1).collect(Collectors.toList());
    }

}
