package tagshare.tagshare.adapters;

/**
 * Created by ilyes on 4/11/17.
 */


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import tagshare.tagshare.activities.CommentsActivity;
import tagshare.tagshare.asynctasks.ArticleLikeTask;
import tagshare.tagshare.R;
import tagshare.tagshare.models.Article;
import tagshare.tagshare.models.Comment;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.MyViewHolder> {

    private List<Article> articleList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView username;
        public ImageView profileImage;

        public TextView title;
        public TextView content;
        public ImageView image;
        public LikeButton likeBtn;
        public ImageButton commentBtn;
        public TextView likesCounterTV;
        public TextView comments;
        public TextView commentsCountTV;

        public RelativeLayout loadingPanel;

        public MyViewHolder(View view) {
            super(view);
            username = (TextView) view.findViewById(R.id.username);
            profileImage = (ImageView) view.findViewById(R.id.profile_img);

            title = (TextView) view.findViewById(R.id.title);
            content = (TextView) view.findViewById(R.id.content);
            comments = (TextView) view.findViewById(R.id.comments);
            image = (ImageView) view.findViewById(R.id.image);
            likeBtn = (LikeButton) view.findViewById(R.id.like_btn);
            commentBtn = (ImageButton) view.findViewById(R.id.comment_btn);
            commentsCountTV = (TextView) view.findViewById(R.id.comments_count_tv);
            likesCounterTV = (TextView) view.findViewById(R.id.likes_counter_tv);

            loadingPanel = (RelativeLayout) view.findViewById(R.id.loadingPanel);
        }
    }


    public ArticleAdapter(Context context, List<Article> articleList) {
        this.context = context;
        this.articleList = articleList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Article article = articleList.get(position);

        // Set the user data
        // Set the username
        holder.username.setText(article.getUser().getUsername());

        // Set the profile image
        Picasso.with(context)
                .load(article.getUser()
                .getProfileImage())
                .into(holder.profileImage);

        // Set the title
        holder.title.setText(article.getTitle());

        // Set the text
        holder.content.setText(article.getContent());

        // Set the comments
        holder.comments.setText(article.getComments());

        // Set the image
        Picasso.with(context).load(article.getImageUrl()).into(holder.image, new Callback() {
            @Override
            public void onSuccess() {
                // Hide the loading after image is loaded
                holder.loadingPanel.setVisibility(View.GONE);
                holder.image.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError() {
                Toast.makeText(context, "Can't load image", Toast.LENGTH_SHORT).show();
            }
        });


        // Set the likes counter
        holder.likesCounterTV.setText(article.getLikes() + "");

        // Comments count
        holder.commentsCountTV.setText("View all " + article.getCommentsAsList().size() + " comments");

        holder.commentsCountTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCommentActivity(article);
            }
        });


        OnLikeListener incrementLikes = new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                new ArticleLikeTask(holder.likesCounterTV).execute(article.getId());
            }

            @Override
            public void unLiked(LikeButton likeButton) {

            }
        };

        // Like on article
        holder.likeBtn.setOnLikeListener(incrementLikes);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.likeBtn.performClick();
            }
        });

        // Comment on article
        holder.commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCommentActivity(article);
                //Toast.makeText(context, "Comment", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void startCommentActivity(Article article) {
        // Send the article ID to get the according comments
        Intent intent = new Intent(context, CommentsActivity.class);
        intent.putExtra("article-id", article.getId());

        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }


    public void swap(ArrayList<Article> datas) {
        articleList.clear();
        articleList.addAll(datas);
        notifyDataSetChanged();
    }
}