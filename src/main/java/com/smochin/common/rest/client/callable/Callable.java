package com.smochin.common.rest.client.callable;

import com.smochin.common.rest.client.callable.handler.ChunkHandler;
import com.smochin.common.rest.client.response.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.ResponseProcessingException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.ChunkedInput;

public abstract class Callable<T> {

    private WebTarget target;
    private List<Cookie> cookies;
    private Map<String, Object> headers;
    private ChunkHandler chunkHandler;

    public Callable(WebTarget target) {
        this.target = target;
    }

    public HttpResponse call() {
        Invocation.Builder builder = target.request();
        
        builder = fillCookies(builder);
        builder = fillHeaders(builder);
        
        Response r = null;
        HttpResponse response = null;
        try {
			r = onCall(builder);
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
	        response = new HttpResponse(status, entity, h);
	        
		} catch (ResponseProcessingException e) {
			//Erro de
			
		} catch (ProcessingException e) {
			//Erro de I/O
			
		} catch (WebApplicationException e) {
			//Erro de 
			
		} catch (Exception e) {
			//Erro generico
		}
        
        
        return response;
    }

    protected abstract Response onCall(Invocation.Builder builder) throws ResponseProcessingException, ProcessingException, WebApplicationException, Exception;

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
        cookies().add(new Cookie(name, value, path, domain));
        return (T) this;
    }

    public final T cookie(String name, String value) {
        cookies().add(new Cookie(name, value));
        return (T) this;
    }
    
    private List<Cookie> cookies() {
        if(Objects.isNull(cookies)) {
            cookies = new ArrayList<>();
        }
        return cookies;
    }
    
    public final T header(String name, String value) {
        headers().put(name, value);
        return (T) this;
    }
    
    public final T headers(final Map<String, String[]> headers) {
        headers().putAll(headers);
        return (T) this;
    }
    
    private Map headers() {
        if(Objects.isNull(headers)) {
            headers = new HashMap<>();
        }
        return headers;
    }

    private Invocation.Builder fillCookies(final Invocation.Builder builder) {
        Invocation.Builder newBuilder = builder;
        for(Cookie cookie: cookies()) {
            newBuilder = newBuilder.cookie(cookie);
        }
        return newBuilder;
    }

    private Invocation.Builder fillHeaders(final Invocation.Builder builder) {
        Invocation.Builder newBuilder = builder;
        Set<String> keys = headers().keySet();
        for(String key: keys) {
            newBuilder = newBuilder.header(key, headers().get(key));
        }
        return newBuilder;
    }
    
}
