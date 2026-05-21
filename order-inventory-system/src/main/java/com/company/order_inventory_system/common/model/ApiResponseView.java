package com.company.order_inventory_system.common.model;

public class ApiResponseView {

    private int status;

    private Object responseBody;

    public ApiResponseView() {
    }

    public ApiResponseView(
            int status,
            Object responseBody
    ) {
        this.status = status;
        this.responseBody = responseBody;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(Object responseBody) {
        this.responseBody = responseBody;
    }
}