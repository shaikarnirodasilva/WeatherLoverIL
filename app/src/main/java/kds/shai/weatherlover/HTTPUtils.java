package kds.shai.weatherlover;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by master on 05/07/17.
 */
public class HTTPUtils {

    public static String read(String url) throws Exception {
        URL address = new URL(url);

        //WHY the casting?
        HttpURLConnection con = (HttpURLConnection) address.openConnection();
        int responseCode = con.getResponseCode(); //we want it to be OK == 200

        InputStream in = con.getInputStream();

        //con.disconnect();
        return StreamIO.read(in);
    }
}
