package tagshare.tagshare.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import tagshare.tagshare.R;
import tagshare.tagshare.adapters.ArticleAdapter;
import tagshare.tagshare.asynctasks.GetArticlesListTask;
import tagshare.tagshare.models.Article;
import tagshare.tagshare.utils.Http;

public class MainActivity extends AppCompatActivity {

    private List<Article> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ArticleAdapter mAdapter;

    SwipeRefreshLayout mSwipeRefreshLayout;
    RelativeLayout noNetworkIcon;
    TextView tryToConnectTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        noNetworkIcon = (RelativeLayout) findViewById(R.id.no_network);
        tryToConnectTV = (TextView) findViewById(R.id.try_to_connect_tv);

        tryToConnectTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStart();
            }
        });


        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // If there are internet connection
                if (Http.isConnectingToInternet(MainActivity.this)) {
                    // Refresh items
                    new GetArticlesListTask(MainActivity.this, mAdapter, mSwipeRefreshLayout).execute();
                } else {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mSwipeRefreshLayout.setVisibility(View.GONE);
                    noNetworkIcon.setVisibility(View.VISIBLE);
                }
            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();

        // If there are internet connection
        if (Http.isConnectingToInternet(this)) {
            noNetworkIcon.setVisibility(View.GONE);

            // Our stuff
            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
            recyclerView = (RecyclerView) findViewById(R.id.recycler_view);


            mAdapter = new ArticleAdapter(this, new ArrayList<Article>());

            // async task test
            new GetArticlesListTask(this, mAdapter).execute();

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
