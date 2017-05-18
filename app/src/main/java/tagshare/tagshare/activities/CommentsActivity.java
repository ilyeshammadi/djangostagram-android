package tagshare.tagshare.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tagshare.tagshare.R;
import tagshare.tagshare.adapters.CommentAdapter;
import tagshare.tagshare.asynctasks.GetArticleTask;
import tagshare.tagshare.asynctasks.SendCommentTask;
import tagshare.tagshare.models.Article;
import tagshare.tagshare.models.Comment;
import tagshare.tagshare.models.User;
import tagshare.tagshare.utils.Http;

import static tagshare.tagshare.utils.Constants.LOGGED_IN_USER_ID;

public class CommentsActivity extends AppCompatActivity {

    RecyclerView commentsListRV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get the views
        final EditText addCommentET = (EditText) findViewById(R.id.add_comment_et);
        ImageButton sendCommentBtn = (ImageButton) findViewById(R.id.send_comment_btn);


        // Get the article id from the intent
        Intent intent = getIntent();
        final Integer id = intent.getIntExtra("article-id", 0);


        // Get views
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list_comments_rv);
        final CommentAdapter adapter = new CommentAdapter(this, new ArrayList<Comment>());

        // Get the article data from the server
        new GetArticleTask(this, adapter).execute(id);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


        // On click send comment btn
        sendCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the comment from the input
                String commentText = String.valueOf(addCommentET.getText());

                // Clean the input
                addCommentET.setText("");

                // Send the comment to the server
                new SendCommentTask().execute(String.valueOf(id), commentText);

                // Get the article data from the server
                new GetArticleTask(CommentsActivity.this, adapter).execute(id);

            }
        });


    }

}
