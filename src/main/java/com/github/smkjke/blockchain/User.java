package com.github.smkjke.blockchain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

    @JsonProperty("Message")
    private String message;

    @JsonProperty("Name")
    private String name;

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
