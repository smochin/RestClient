package com.smochin.common.rest.client.response;

import java.util.Map;

public class HttpResponse {
    private int status;
    private Map<String, String> headers;
    private String entity;

    public HttpResponse(int status, String entity, Map h) {
        setStatus(status);
        setEntity(entity);
        setHeaders(headers);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }
    
    public Object getEntityAsJson() {
        return getEntity();
    }
}
