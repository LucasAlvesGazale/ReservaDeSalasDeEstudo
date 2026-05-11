package src.classes.user;

public class User {
    private final int id;
    private String name;
    private String role;
    private String document;
    private String email;

    public User(int id, String name, String role, String document, String email) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.document = document;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    
}
