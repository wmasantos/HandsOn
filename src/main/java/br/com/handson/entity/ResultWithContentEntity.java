package br.com.handson.entity;

import java.io.Serializable;
import java.util.List;

public class ResultWithContentEntity<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private int code;
    private String message;
    private List<String> errors;
    private T content;

    public ResultWithContentEntity() {
    }

    public ResultWithContentEntity(int code, String message, List<String> errors, T content) {
        this.code = code;
        this.message = message;
        this.errors = errors;
        this.content = content;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
