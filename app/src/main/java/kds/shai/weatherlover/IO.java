package kds.shai.weatherlover;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Ramiz on 5/17/2017.
 */
public class IO {
    //NextInt()
    public static int nextInt(String message){
        Scanner in = new Scanner(System.in);
        System.out.println(message);
        int number = in.nextInt();
        in.nextLine();
        return  number;
    }

    //NextDouble()
    public static double nextDouble(String message){
        Scanner in = new Scanner(System.in);
        System.out.println(message);
        double number = in.nextDouble();
        in.nextLine();
        return  number;
    }

    //NextBoolean()
    public static boolean nextBoolean(String message){
        Scanner in = new Scanner(System.in);
        System.out.println(message);
        boolean val = in.nextBoolean();
        in.nextLine();
        return  val;
    }

    //Next()
    public static String next(String message){
        Scanner in = new Scanner(System.in);
        System.out.println(message);
        String str = in.next();
        in.nextLine();
        return  str;
    }

    //NextLine()
    public static String nextLine(String message){
        Scanner in = new Scanner(System.in);
        System.out.println(message);
        String str = in.nextLine();
        return  str;
    }

    //NextString()
    public static String nextString(String message){
        Scanner in = new Scanner(System.in);
        System.out.println(message);
        String str = in.nextLine();
        return  str;
    }

    //PrintIntArray
    public static void prtIntArr(int[] arr, String msg){
        for (int i = 0; i < arr.length; i++) {
            System.out.printf("%s %d ) %d\n",msg,i+1,arr[i]);
        }
    }
    //PrintDoubleArray
    public static void prtDoubleArr(double[] arr, String msg){
        for (int i = 0; i < arr.length; i++) {
            System.out.printf("%s %d ) %f\n",msg,i+1,arr[i]);
        }
    }
    //PrintStringArray
    public static void prtStringArr(String[] arr, String msg){
        for (int i = 0; i < arr.length; i++) {
            System.out.printf("%s %d ) %s\n",msg,i+1,arr[i] );
        }
    }

    public static char nextChar(String message){
        Scanner in = new Scanner(System.in);
        System.out.println(message);
        return  in.next().charAt(0);
    }

    public static char nextCharOf(String message, char[] charArr){
        Scanner in = new Scanner(System.in);
        boolean isInArr = false;
        char str;
        String arrToString = "" ;

        do {
            arrToString += ("" + charArr[0]) ;
            for (int i = 1; i < charArr.length; i++) {
                arrToString += (","+ charArr[i]) ;
            }

            System.out.println(message + " (" + arrToString + ") :");
            str = in.next().charAt(0);

            isInArr = false;
            for (char c : charArr) {
                if (Character.toUpperCase(str) == Character.toUpperCase(c)) {
                    isInArr = true;
                }
            }
        }while (!isInArr);

        return  str;
    }

}