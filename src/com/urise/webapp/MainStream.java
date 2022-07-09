package com.urise.webapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainStream {
    public static void main(String[] args) {
        int[] values = new int[]{1,2,3,3,2,3};
        System.out.println(minValue(values));

        List<Integer> integers = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            integers.add(i);
        }

        List<Integer> list = oddOrEven(integers);
        System.out.println(list);
    }

    static int minValue(int[] values) {
        int[] array =  Arrays.stream(values).distinct().sorted().toArray();
        for (int i = 0; i < array.length - 1; i++) {
            array[i] = (int) (array[i] * Math.pow(10, array.length - i - 1));
        };
        return Arrays.stream(array).sum();
    }

    static List<Integer> oddOrEven(List<Integer> integers) {
        if (integers.stream().mapToDouble(a -> a).sum() % 2 == 0) {
            return integers.stream().filter(a -> a % 2 == 0).collect(Collectors.toList());
        } else return integers.stream().filter(a -> a % 2 == 1).collect(Collectors.toList());
    }

}
