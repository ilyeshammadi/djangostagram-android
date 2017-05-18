package tagshare.tagshare.asynctasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import tagshare.tagshare.adapters.ArticleAdapter;
import tagshare.tagshare.adapters.CommentAdapter;
import tagshare.tagshare.models.Article;
import tagshare.tagshare.models.Comment;
import tagshare.tagshare.utils.Http;

import static tagshare.tagshare.utils.Constants.BASE_URL;

/**
 * Created by ilyes on 5/15/17.
 */

public class GetArticleTask extends AsyncTask<Integer, Void, ArrayList<Comment>>{
    public static final String TAG = "DJANGO";

    public static String API_URL;


    private Context context;
    private Article article;
    private CommentAdapter adapter;

    public GetArticleTask(Context context, CommentAdapter adapter) {
        this.adapter = adapter;
        this.context = context;
    }


    @Override
    protected ArrayList<Comment> doInBackground(Integer... params) {

        Integer articleId = params[0];

        API_URL = BASE_URL + "/api/articles/" + String.valueOf(articleId) + "/?format=json";

        String data = Http.get(API_URL);

        try {

            JSONObject articleNode = new JSONObject(data);
            this.article = Article.fromJSON(articleNode);


            return (ArrayList<Comment>) this.article.getCommentsAsList();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Comment> comments) {
        this.adapter.swap(comments);

    }
}
