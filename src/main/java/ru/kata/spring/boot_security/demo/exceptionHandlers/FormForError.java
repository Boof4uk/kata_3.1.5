package ru.kata.spring.boot_security.demo.exceptionHandlers;

public class FormForError {
    private String info;
    private Long timestamp;

    public FormForError(String info, Long timestamp) {
        this.info = info;
        this.timestamp = timestamp;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
