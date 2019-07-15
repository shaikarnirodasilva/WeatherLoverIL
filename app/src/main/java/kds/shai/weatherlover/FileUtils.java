package kds.shai.weatherlover;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by ANDROID on 26/07/2017.
 */
public class FileUtils {
    public static boolean fileExist(String filePath) {
        File f = new File(MainActivity.getAppContext().getFilesDir(), filePath);
        if (f.exists()) {
            return true;
        } else
            return false;
    }

//Do while tnai when you do it then you give tnai and if its y get in again.
    //Adds to TXT and the file.

    public static void writeCities(String filePath, List citiesList, boolean overWrite) throws IOException {
        FileOutputStream outputStream = MainActivity.getAppContext().openFileOutput(filePath, Context.MODE_PRIVATE);
        List<String> strings = readFromFile(filePath);
        if (citiesList.size() > 0) {
            try {

                for (int i = 0; i <= citiesList.size(); i++) {
                    final String city = citiesList.get(i).toString();
                    if (strings.contains(city)) {
                        return;
                    } else
                        outputStream.write(city.getBytes());
                    outputStream.write("\n".getBytes());

                }
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            outputStream.close();
        }
            /*BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            FileWriter fileWriter = new FileWriter(filePath);

                fileWriter.write(citiesList.get(i).toString());
                fileWriter.write("\n");
                writer.write(citiesList.get(i).toString());
                writer.newLine();
            }
            writer.close();*/
    }


    public static void changeCities(String filePath, Integer numOfCities, boolean overWrite) throws
            IOException {
        if (!(fileExist(filePath)) || (fileExist(filePath) && overWrite)) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            FileWriter fileWriter = new FileWriter(filePath);

            System.out.println("Please Enter a City Name :");
            for (int i = 0; i < numOfCities; i++) {
                writer.write(IO.nextString("City" + (i + 1) + ":"));
                writer.newLine();
            }
            writer.close();
        }

    }


    //Delete 1 City


    //Writes it to a txt List From OutSide.
    public static List<String> readCitiesToList(String fileName) throws IOException {

        Integer result;
        List<String> fileLines = new ArrayList<>();
        try (FileInputStream reader = new FileInputStream(fileName)) {
            while ((result = reader.read()) != -1) {
                fileLines.add(result.toString());
            }
            return fileLines;
        }
    }

    public static List<String> readFromFile(String fileName) {

        List<String> ret = new ArrayList<>();

        try {
            InputStream inputStream = MainActivity.getAppContext().openFileInput(fileName);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();
                int i = 0;
                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                    ret.add(i, stringBuilder.toString());
                    i++;
                }

                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }

    //copy file data to the list.
    public void copyListToFile(String fileName, List<String> citiesList) {
        try {
            writeCities(fileName, citiesList, true);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("FILE", "File Creation Failed. Check it out.");
        }

    }

    List<String> fileToArray(String fileName, List<String> citiesList) {
        //IF the file exists, no need to create another one.
        if (fileExist(fileName)) {
            Log.d("FILE EXISTS", "File Exists Already" + MainActivity.getAppContext().getFilesDir().getAbsolutePath());
            Toast.makeText(MainActivity.getAppContext(), "MEOW" + MainActivity.getAppContext().getFilesDir().getAbsolutePath(), Toast.LENGTH_LONG).show();
            try {
                citiesList = readFromFile(fileName);

            } catch (Exception e) {
                e.printStackTrace();
                Log.d("FILE READ", "Problem with File Reading.");
            }
        }
        return citiesList;
    }

    void deleteCity(final File file, final int lineIndex, List<String> citiesList) throws IOException {
        final List<String> lines = new LinkedList<>();
        final Scanner reader = new Scanner(new FileInputStream(file), "UTF-8");
        while (reader.hasNextLine())
            lines.add(reader.nextLine());
        reader.close();
        assert lineIndex >= 0 && lineIndex <= lines.size() - 1;
        String s = lines.get(lineIndex);
        citiesList.remove(s);
        //TODO: REmove from database.
        lines.remove(lineIndex);
        final BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
        for (final String line : lines)
            writer.write(line);
        writer.flush();
        writer.close();
    }


    //Creates a file if there is no file exists.
    void createFile(String fileName) {
        String fileContents = "";
        FileOutputStream outputStream;
        try {
            outputStream = MainActivity.getAppContext().openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(fileContents.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}