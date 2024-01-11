package utils;

public class TempStore {
    private String password;
    private String email;

    public TempStore(String password, String email) {
        this.password = password;
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
