package com.github.smkjke.blockchain;

public class Message {

    private User author;
    private String text;
    private final long id;

    public Message(User author, String text, long id) {
        this.author = author;
        this.text = text;
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }

    public long getId() {
        return id;
    }

    public static String format(Message message) {
        return String.format("%s: %s", message.getAuthor(), message.getText());
    }
}
