package com.dw.math;

import java.util.Arrays;

public class OrderUtils {

    public static void bubbleSort(int a[]){
        if ( a == null ) return;
        for (int i = a.length - 1 ; i > 0 ; i -- ){
            for (int j = 0 ; j < i ; j ++){
                if (a[j] > a[j+1]) {
                    int t = a[j];
                    a[j] = a[j+1];
                    a[j+1] = t;
                }
            }
        }
        System.out.println(Arrays.toString(a));
    }


    public static void selectSort(int a[]){
        if ( a == null ) return;
        for (int i = a.length - 1 ; i > 0 ; i -- ){
            int maxIndex = 0;
            for (int j = 1 ; j <= i ; j ++){
                if (a[maxIndex] < a[j]) {
                    maxIndex = j;
                }
            }
            if (maxIndex != i){
                int t = a[maxIndex];
                a[maxIndex] = a[i];
                a[i] = t;
            }
        }
        System.out.println(Arrays.toString(a));
    }

    public static void quickSort(int a[]){
        if ( a == null ) return;
        quickSort1(a ,0 , a.length - 1);
        System.out.println(Arrays.toString(a));
    }

    private static void quickSort1(int a[] ,int left ,int right){
        if (a == null || left >= right) return;
        int mid = a[left];
        int l = left;
        int r = right;
        while (l < r){
            while (a[l] <= mid && l < r){
                l ++;
            }
            while (a[r] > mid && l < r){
                r --;
            }
            if (l < r) {
                int t = a[l];
                a[l] = a[r];
                a[r] = t;
            }
        }
        int c;
        if(a[l] > mid){
            c = l - 1;
        } else {
            c = l;
        }
        a[left] = a[c];
        a[c] = mid;
        quickSort1(a ,left ,c - 1);
        quickSort1(a ,c + 1 ,right);
    }

    public static void mergeSort(int a[]){
        if ( a == null ) return;
        int tmp[] = new int[a.length];
        mergeSort1(a ,tmp ,0 ,a.length - 1);
        System.out.println(Arrays.toString(a));
    }

    private static void mergeSort1(int a[] , int tmp[] , int s ,int e){
        if (s >= e) return;
        int m = (e + s) / 2;
        mergeSort1(a ,tmp ,s ,m);
        mergeSort1(a ,tmp ,m + 1 ,e);
        int index1 = s;
        int index2 = m + 1;
        for (int i = s; i <= e ;i++){
            if (index1 <= m && index2 <= e && a[index1] < a[index2]){
                tmp[i] = a[index1];
                index1 ++;
            } else if (index2 > e){
                tmp[i] = a[index1];
                index1 ++;
            } else {
                tmp[i] = a[index2];
                index2 ++;
            }
        }
        System.arraycopy(tmp ,s ,a ,s ,e - s + 1);
    }

    public static void insertSort(int a[]){
        if ( a == null ) return;
        for (int i = 1 ; i < a.length ; i ++){
            for (int j = i ; j > 0 ; j --){
                if (a[j] < a[j - 1]){
                    int t = a[j];
                    a[j] = a[j - 1];
                    a[j - 1] = t;
                } else {
                    break;
                }
            }
        }
        System.out.println(Arrays.toString(a));
    }

    public static void heapSort(int a[]){
        if ( a == null ) return;
        int s = (a.length - 2) / 2;
        for (int i = s ; i > 0 ; i --){
            heapSort1(a, a.length ,i);
        }
        for (int i = a.length ;i > 1 ; i--){
            heapSort1(a, i ,0);
            int t = a[0];
            a[0] = a[i - 1];
            a[i - 1] = t;
        }
        System.out.println(Arrays.toString(a));
    }

    private static void heapSort1(int a[] ,int length, int index){
        int l = index * 2 + 1;
        int r = index * 2 + 2;
        if (l > length - 1) return;
        if (r <= length - 1){
            int max = a[l] > a[r] ? l : r;
            if (a[max] > a[index]){
                int t = a[max];
                a[max] = a[index];
                a[index] = t;
                heapSort1(a ,length ,max);
            }
        } else {
            if (a[l] > a[index]){
                int t = a[l];
                a[l] = a[index];
                a[index] = t;
            }
        }
    }

    public static void main(String[] args) {
        int[] a = new int[]{3,6,8,1,3,4,9,1,10};
        bubbleSort(a);
        selectSort(a);
        quickSort(a);
        insertSort(a);
        heapSort(a);
        mergeSort(a);
    }

}
