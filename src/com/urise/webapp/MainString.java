package com.urise.webapp;

public class MainString {
    public static void main(String[] args) {

        int[] mas1 = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] mas2 = new int[15];
        for (int i = 13; i < 16; i++) {
            mas2[i - 13] = i;
        }

        System.arraycopy(mas1, 3, mas2, 2, 4);
        System.out.println(mas2.toString());

    }
}
