package tagshare.tagshare.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tagshare.tagshare.R;
import tagshare.tagshare.models.Comment;

/**
 * Created by ilyes on 5/7/17.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {

    private List<Comment> comments;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView username;
        public TextView text;

        public MyViewHolder(View view) {
            super(view);
            username = (TextView) view.findViewById(R.id.username);
            text = (TextView) view.findViewById(R.id.text);
        }
    }


    public CommentAdapter(Context context, List<Comment> comments) {
        this.context = context;
        this.comments = comments;
    }

    @Override
    public CommentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_list_row, parent, false);

        return new CommentAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Comment comment = comments.get(position);

        holder.username.setText(comment.getUser().getUsername());

        holder.text.setText(comment.getText());
    }


    @Override
    public int getItemCount() {
        return comments.size();
    }


    public void swap(ArrayList<Comment> datas){
        comments.clear();
        comments.addAll(datas);
        notifyDataSetChanged();
    }
}
