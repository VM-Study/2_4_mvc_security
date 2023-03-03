package org.example.web.dto;

// объект DTO (data transfer object)
// требует:
// - пустой конструктор
// - геттеры, сеттеры и toString
public class LoginForm {
    private String username;
    private String password;

    // конструктор со всеми аргументами
    public LoginForm(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginForm() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginForm{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
