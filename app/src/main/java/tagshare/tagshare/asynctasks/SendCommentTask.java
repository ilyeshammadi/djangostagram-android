package tagshare.tagshare.asynctasks;

import android.os.AsyncTask;
import android.speech.tts.Voice;

import tagshare.tagshare.utils.Http;

import static tagshare.tagshare.utils.Constants.LOGGED_IN_USER_ID;

/**
 * Created by ilyes on 5/16/17.
 */

public class SendCommentTask extends AsyncTask<String, Void, Boolean> {
    @Override
    protected Boolean doInBackground(String... params) {
        Integer articleId = Integer.valueOf(params[0]);
        String commentText = params[1];

        Http.addComment(LOGGED_IN_USER_ID, articleId, commentText);

        return null;
    }
}
