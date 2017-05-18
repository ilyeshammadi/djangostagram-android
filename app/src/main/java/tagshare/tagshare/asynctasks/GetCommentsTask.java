package tagshare.tagshare.asynctasks;

import android.os.AsyncTask;

import java.util.List;

import tagshare.tagshare.models.Comment;
import tagshare.tagshare.utils.Http;

import static tagshare.tagshare.utils.Constants.BASE_URL;

/**
 * Created by ilyes on 5/9/17.
 */

public class GetCommentsTask extends AsyncTask<Integer, Void, List<Comment>> {
    private String API_URL = BASE_URL + "/comments/";
    @Override
    protected List<Comment> doInBackground(Integer... params) {

        Http.get(API_URL);

        return null;
    }
}
