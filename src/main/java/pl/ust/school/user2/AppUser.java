package pl.ust.school.user2;

public class AppUser {  // from security1 project
	 
    private Long userId;
    private String username;
    private String encrytedPassword;
 
    public AppUser() { 
    }
 
    public AppUser(Long userId, String userName, String encrytedPassword) {
        this.userId = userId;
        this.username = userName;
        this.encrytedPassword = encrytedPassword;
    }
 
    public Long getUserId() {
        return userId;
    }
 
    public void setUserId(Long userId) {
        this.userId = userId;
    }
 
    public String getUserName() {
        return username;
    }
 
    public void setUserName(String userName) {
        this.username = userName;
    }
 
    public String getEncrytedPassword() {
        return encrytedPassword;
    }
 
    public void setEncrytedPassword(String encrytedPassword) {
        this.encrytedPassword = encrytedPassword;
    }
 
    @Override
    public String toString() {
        return this.username + "/" + this.encrytedPassword;
    }
 
}
