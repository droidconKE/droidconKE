package droiddevelopers254.droidconke.datastates;

public class UpdateTokenState {
    private boolean success;
    private String error;

    public UpdateTokenState(boolean success) {
        this.success = success;
        this.error=null;
    }

    public UpdateTokenState(String error) {
        this.error = error;
        this.success=false;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getError() {
        return error;
    }
}
