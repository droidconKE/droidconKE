package droiddevelopers254.droidconke.models;

public class UserModel {

    private String user_id;
    private String refresh_token;
    private String email;
    private String user_name;
    private String photo_url;


    public UserModel(){


    }

    public UserModel(String user_id, String refresh_token, String email, String user_name, String photo_url) {
        this.user_id = user_id;
        this.refresh_token = refresh_token;
        this.email = email;
        this.user_name = user_name;
        this.photo_url = photo_url;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }
}
