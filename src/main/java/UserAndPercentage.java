public class UserAndPercentage {
    private String userName;
    private float percentage;

    public UserAndPercentage(String username, float percentage) {
        this.userName = username;
        this.percentage = percentage;
    }

    public String getUserName() {
        return userName;
    }
    public float getPercentage() {
        return percentage;
    }
    public void setPercentage(float percentage){this.percentage=percentage;}
}