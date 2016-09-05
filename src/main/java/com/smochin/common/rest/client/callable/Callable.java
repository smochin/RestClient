package com.smochin.common.rest.client.callable;

import com.smochin.common.rest.client.callable.handler.ChunkHandler;
import com.smochin.common.rest.client.response.HttpResponse;
import java.util.List;
import java.util.Map;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.ChunkedInput;

public abstract class Callable<T> {

    private WebTarget target;
    private List<Cookie> cookies;
    private ChunkHandler chunkHandler;

    public Callable(WebTarget target) {
        this.target = target;
    }

    public HttpResponse call() {
        Invocation.Builder builder = target.request();
        javax.ws.rs.core.Response r = onCall(builder);
        if (null != chunkHandler) {
            GenericType<ChunkedInput<String>> genericType = new GenericType<ChunkedInput<String>>(){};
            ChunkedInput<String> input = r.readEntity(genericType);
            String chunk;
            while ((chunk = input.read()) != null) {
                chunkHandler.handle(chunk);
            }
        }
        int status = r.getStatus();
        String entity = r.readEntity(String.class);
        Map h = r.getHeaders();
        HttpResponse response = new HttpResponse(status, entity, h);
        return response;
    }

    public abstract Response onCall(Invocation.Builder builder);

    public void chunkHandler(ChunkHandler ch) {
        this.chunkHandler = ch;
    }

    public final T queries(Map<String, String[]> query) {

        for (String key : query.keySet()) {
            target = target.queryParam(key, query.get(key));
        }
        return (T) this;
    }

    public final T query(String key, Object values) {
        target = target.queryParam(key, values);
        return (T) this;
    }

    public final T cookie(String name, String value, String path, String domain) {
        cookies.add(new Cookie(name, value, path, domain));
        return (T) this;
    }

    public final T cookie(String name, String value) {
        cookies.add(new Cookie(name, value));
        return (T) this;
    }
    
    public final T header(String name, String value) {
        return (T) this;
    }
    
    public final T headers(Map<String, String[]> query) {
        return (T) this;
    }

    public void pathVar(String name, String name0) {

    }
    
}
