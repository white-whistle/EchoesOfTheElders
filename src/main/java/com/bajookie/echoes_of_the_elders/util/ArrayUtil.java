package com.bajookie.echoes_of_the_elders.util;

import java.util.Arrays;
import java.util.Collections;

public class ArrayUtil {
    public static int[] reverseVertex(int[] arr, int chunkSize) {
        int length = arr.length;
        int[] reversedArray = new int[length];
        var numChunks = arr.length / chunkSize;


        for (int chunkIndex = 0; chunkIndex < numChunks; chunkIndex++) {
            for (int i = 0; i < chunkSize; i++) {
                var reversedIndex = ((numChunks - chunkIndex - 1) * chunkSize) + i;
                reversedArray[reversedIndex] = arr[(chunkIndex * chunkSize) + i];
            }
        }

        return reversedArray;
//        for (int i = 0; i < length; i++) {
//            reversedArray[i] = arr[length - 1 - i];
//        }
//
//        return reversedArray;
    }
}
