package com.hipermidia.model;

import lombok.Getter;

@Getter
public class Response {
    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}