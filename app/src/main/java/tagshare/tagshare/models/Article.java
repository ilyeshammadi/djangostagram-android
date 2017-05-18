package tagshare.tagshare.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static tagshare.tagshare.utils.Constants.BASE_URL;

/**
 * Created by ilyes on 4/11/17.
 */

public class Article{
    private int id;
    private User user;
    private String title, content;
    private String imageUrl;
    private float lat, lng;
    private int likes;
    private List<Comment> comments;

    public Article() {

    }

    public Article(User user, String title) {
        this.user = user;
        this.title = title;
    }

    public Article(int id, User user, String title, String content, String imageUrl, int likes, List<Comment> comments) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.likes = likes;
        this.comments = comments;
    }

    public Article(String title, String content, String imageUrl, float lat, float lng) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.lat = lat;
        this.lng = lng;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getComments() {
        String output = "";
        for (int i = 0; i < comments.size(); i++) {

            if(i <= 2) {
                Comment comment = comments.get(i);
                output += comment.getUser().getUsername() + " -- " + comment.getText() + '\n';
            } else {
                output += "...";
                break;
            }


        }
        return output;
    }


    public List<Comment> getCommentsAsList() {
        return this.comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public static Article fromJSON(JSONObject articleNode) {

        try {

            User user  = User.fromJSON((JSONObject) articleNode.getJSONObject("user"));

            JSONArray commentsNode = articleNode.getJSONArray("comments");


            ArrayList<Comment> comments = new ArrayList<>();

            for (int i = 0; i < commentsNode.length(); i++) {
                comments.add(Comment.fromJSON(commentsNode.getJSONObject(i)));
            }


            int id = articleNode.getInt("id");
            String title = articleNode.getString("title");
            String imageUrl = articleNode.getString("image");
            String content = articleNode.getString("content");
            int likes = articleNode.getInt("likes");

            String dateCreated = articleNode.getString("created").split("T")[1];


            DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
            Date inputDate = dateFormat.parse(dateCreated);

            Log.i("DJANGO", "fromJSON: date created" + inputDate.getTime());



            return new Article(id, user, title, content, imageUrl, likes, comments);



        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }


        return null;
    }

}
