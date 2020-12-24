public class UserAndPercentage {
    private String username; //User's username.
    private float percentage; //Percentage of that user.

    //UserAndPercentage constructor.
    public UserAndPercentage(String username, float percentage) {
        this.username = username;
        this.percentage = percentage;
    }

    //Getter methods.
    public String getUserInfo() {
        return username;
    }
    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float newPercentage) {
    }
}