package tagshare.tagshare.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ilyes on 5/6/17.
 */

public class Comment {
    private User user;
    private String text;


    public Comment(User user, String text) {
        this.user = user;
        this.text = text;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public static Comment fromJSON(JSONObject commentNode) {

        try {

            User user = User.fromJSON(commentNode.getJSONObject("user"));
            String text = commentNode.getString("text");

            return new Comment(user, text);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }
}
