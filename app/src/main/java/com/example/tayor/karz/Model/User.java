package com.example.tayor.karz.Model;

import android.util.Patterns;

import java.util.regex.Pattern;

public class User {
    private String email;
    private String password;
    private String confirmPassword;

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) throws Exception {
        if(!(getPassword().equals(confirmPassword)))
            throw new Exception("password mismatch");
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws Exception {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        if (!(pattern.matcher(email).matches()))
            throw new Exception("Invalid email address");
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws Exception {
        if (password.isEmpty())
            throw new Exception("Enter your password");
        else if (password.length() < 8)
            throw new Exception("password must be of 8 characters minimum");
        else
            this.password = password;
    }
}
