package droiddevelopers254.droidconke.models;

public class UserModel {

    private String user_id;
    private String refresh_token;
    private String email;


    public UserModel(){


    }

    public UserModel(String user_id, String email) {
        this.user_id = user_id;
        this.email = email;
    }

    public UserModel(String user_id, String refresh_token, String email) {
        this.user_id = user_id;
        this.refresh_token = refresh_token;
        this.email = email;
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
}
