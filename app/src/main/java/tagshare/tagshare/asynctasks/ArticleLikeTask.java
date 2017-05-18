package tagshare.tagshare.asynctasks;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import tagshare.tagshare.utils.Http;

import static tagshare.tagshare.utils.Constants.BASE_URL;

/**
 * Created by ilyes on 5/2/17.
 */

public class ArticleLikeTask extends AsyncTask<Integer, Void, Boolean> {
    private static final String TAG = "DJANGO";
    private String API_URL = BASE_URL + "/like/";
    private int likes = 0;
    private TextView likesCounterTV;

    public ArticleLikeTask(TextView likeCounterTV) {
        this.likesCounterTV = likeCounterTV;
    }

    @Override
    protected Boolean doInBackground(Integer... params) {
        API_URL += String.valueOf(params[0]);
        String response = Http.get(API_URL);

        try {
            // Extract the data from the json response
            JSONObject topLevel = new JSONObject(response);
            String message = topLevel.getString("message");

            // Test if the message is success
            if (Objects.equals(message, "success")) {
                likes = topLevel.getInt("likes");
                return true;
            } else {
                return false;
            }


        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean response) {
        //super.onPostExecute(response);

        this.likesCounterTV.setText("" + likes);

    }
}
