package tagshare.tagshare.asynctasks;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import tagshare.tagshare.utils.Http;
import tagshare.tagshare.adapters.ArticleAdapter;
import tagshare.tagshare.models.Article;
import tagshare.tagshare.models.User;

import static tagshare.tagshare.utils.Constants.BASE_URL;


/**
 * Created by ilyes on 4/18/17.
 */

public class GetArticlesListTask extends AsyncTask<Void, Void, ArrayList<Article>> {

    public static final String TAG = "DJANGO";

    public static final String API_URL = BASE_URL + "/api/articles/?format=json";

    private ArticleAdapter adapter;
    private Context context;

    private ArrayList<Article> articleList;
    private SwipeRefreshLayout swipeRefreshLayout;

    public GetArticlesListTask(Context context, ArticleAdapter adapter) {
        this.adapter = adapter;
        this.context = context;
        articleList = new ArrayList<>();
    }

    public GetArticlesListTask(Context context, ArticleAdapter adapter, SwipeRefreshLayout swipeRefreshLayout) {
        this.adapter = adapter;
        this.context = context;
        articleList = new ArrayList<>();
        this.swipeRefreshLayout = swipeRefreshLayout;
    }

    @Override
    protected ArrayList<Article> doInBackground(Void... voids) {

        try {
            String data = Http.get(API_URL);

            JSONObject topLevel = new JSONObject(data);
            JSONArray results = topLevel.getJSONArray("results");

            Article article = null;

            for (int i = 0; i < results.length(); i++) {
                JSONObject articleNode = (JSONObject) results.get(i);


                article = Article.fromJSON(articleNode);

                articleList.add(article);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return articleList;
    }

    @Override
    protected void onPostExecute(ArrayList<Article> articles) {
        this.adapter.swap(articles);

        // If refrech using a swipe then stop the swipe
        if (this.swipeRefreshLayout != null) {
            this.swipeRefreshLayout.setRefreshing(false);
        }

    }

}
