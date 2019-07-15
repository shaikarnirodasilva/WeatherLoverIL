package kds.shai.weatherlover;

import java.io.*;

/**
 * Created by master on 02/07/17.
 */
public class StreamIO {

    public final static int x = 1;
    //Utility methods:
    //Declare the error-> the user must Handle the Checked exception.
/*
    public static String read(String fileName) throws IOException {
       */
    /* String result = "";//new String()/ new Object*//*
        //A Mutable String:
        StringBuilder builder = new StringBuilder();
        //read->Input
        //File->FileInputStream
        InputStream in = new FileInputStream(fileName);
        //We need a character Stream from the binary->
        //convert the binary stream -> to a character stream:
        //give the binary to the reader-> so we can get characters:
        InputStreamReader reader = new InputStreamReader(in);
        int data;
        while ((data = reader.read()) != -1) {
            char c = (char) data;//casting is not dangerous any more.
            //Reader has this covered.
            System.out.println(c);
            builder.append(c);//append the character to the builder.
        }
        return builder.toString();//convert to an immutable (Regular String)
    }
*/

/*
    public static String readF(String fileName) throws IOException {
        InputStreamReader reader = null;
        //the result
        //try-catch = Ctrl+alt+t
        StringBuilder result;
        try {
            result = new StringBuilder();
            reader = new InputStreamReader(new FileInputStream(fileName));
            int data;
            while ((data = reader.read()) != -1) {
                result.append((char) data);
            }
        } finally {
            if (reader != null)
                reader.close(); //closing the file (Must get rid of resources)
        }
        return result.toString();
    }
    */

    public static String read(String fileName) throws IOException {
        FileInputStream in = new FileInputStream(fileName);
        return read(in);
    }

    public static String read(InputStream in) throws IOException {
        //the result
        //try with resources
        //try(define the resource as usual){}
        StringBuilder result = new StringBuilder();

        //Buffered...
        try (
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(in))) {
            String data = null;
            while ((data = reader.readLine()) != null)
                result.append(data).append(System.lineSeparator());
        }
        return result.toString();
    }

    public static void write(String fileName, String data) throws IOException {


        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(fileName, false)))) {
            writer.write(data);
            writer.newLine();
            writer.close(); //flush writes the last bytes to the file
            //and than closes.
        }
        //flush
    }

    public static void writeF(String fileName, String data) throws IOException {


        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(fileName, true)))) {
            writer.write(data);
            writer.newLine();
            writer.close(); //flush writes the last bytes to the file
            //and than closes.
        }
        //flush
    }

}