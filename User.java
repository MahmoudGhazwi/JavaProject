public abstract class User {
    
    private String id = "";
    private String username = "";
    private String password = "";

    User(String id, String username, String password){
        this.id = id;
        this.username = username;
        this.password = password;
    }
    public String getId(){
        return id;
    }
    public String getUsername() {
        return username;
    }

    public boolean login(String id, String password){
        return this.id.equals(id) && this.password.equals(password);
    }
    
    public void logout(){
        System.out.println(username + " logged out.");
    }
}
