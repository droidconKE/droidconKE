package droiddevelopers254.droidconke.datastates;

import com.google.firebase.auth.FirebaseUser;

import droiddevelopers254.droidconke.models.UserModel;

public class AuthenticateUserState {
    private boolean userExists;
    private String error;
    private UserModel userModel;

    public AuthenticateUserState(boolean userExists) {
        this.userExists = userExists;
        this.error=null;
        this.userModel=null;
    }

    public AuthenticateUserState(String error) {
        this.error = error;
        this.userExists=false;
        this.userModel=null;
    }

    public AuthenticateUserState(UserModel userModel) {
        this.userModel = userModel;
        this.error=null;
        this.userExists=false;
    }

    public boolean isUserExists() {
        return userExists;
    }

    public String getError() {
        return error;
    }

    public UserModel getUserModel() {
        return userModel;
    }
}
