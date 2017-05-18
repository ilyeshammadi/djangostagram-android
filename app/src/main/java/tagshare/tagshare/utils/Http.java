package tagshare.tagshare.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static tagshare.tagshare.utils.Constants.BASE_URL;

/**
 * Created by ilyes on 5/2/17.
 */

public class Http {

    public static String get(String u) {
        URL url = null;
        try {


            url = new URL(u);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();

            String inputString;
            while ((inputString = bufferedReader.readLine()) != null) {
                builder.append(inputString);
            }
            urlConnection.disconnect();

            return builder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "ERROR";

    }


    public static String addComment(Integer userId, Integer articleId, String commentText) {
        String API_URL = null;
        URL url = null;

        try {
            API_URL = BASE_URL + "/comment/add/?article_id=" + articleId + "&user_id=" + userId + "&comment_text=" + URLEncoder.encode(commentText, "utf-8");
            Log.i("DJANGO", "addComment: " + API_URL);


            url = new URL(API_URL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();

            String inputString;
            while ((inputString = bufferedReader.readLine()) != null) {
                builder.append(inputString);
            }
            urlConnection.disconnect();

            return builder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "ERROR";

    }


    public static boolean isServerAvailable() {
        URL url = null;
        try {

            String u = BASE_URL + "/api/articles/1/?format=json";
            url = new URL(u);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();

            String inputString;
            while ((inputString = bufferedReader.readLine()) != null) {
                builder.append(inputString);
            }
            urlConnection.disconnect();

            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }



    }


    public static boolean isConnectingToInternet(Context context) {

        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

}
