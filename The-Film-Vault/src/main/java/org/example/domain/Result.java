package org.example.domain;

import java.util.ArrayList;
import java.util.List;

public class Result<T> {
    private T payload;
    private final List<String> messages = new ArrayList<>();
    private ResultType resultType = ResultType.SUCCESS;

    public boolean isSuccess() {
        return resultType == ResultType.SUCCESS;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public void addMessage(String message, ResultType type) {
        messages.add(message);
        this.resultType = type;
    }

    public List<String> getMessages() {
        return messages;
    }

    public ResultType getResultType() {
        return resultType;
    }
}
