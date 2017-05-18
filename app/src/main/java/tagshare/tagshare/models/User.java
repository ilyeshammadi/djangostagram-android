package tagshare.tagshare.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ilyes on 4/25/17.
 */

public class User {
    private int id;
    private String username;
    private String email;
    private String profileImage;


    public User(String username, String profileImage) {
        this.username = username;
        this.profileImage = profileImage;
    }

    public User(String username, String email, String profileImage) {
        this.username = username;
        this.email = email;
        this.profileImage = profileImage;
    }


    public User(int id, String username, String profileImage) {
        this.id = id;
        this.username = username;
        this.profileImage = profileImage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", profileImage='" + profileImage + '\'' +
                '}';
    }

    public static User fromJSON(JSONObject userNode) {
        try {
            int id = userNode.getInt("id");
            String username = userNode.getString("username");
            String profileImage = userNode.getString("profile_image");

            return new User(id, username, profileImage);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }
}
