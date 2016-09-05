package com.smochin.common.rest.client;

import com.smochin.common.rest.client.callable.Post;
import com.smochin.common.rest.client.callable.Get;
import com.smochin.common.rest.client.callable.Delete;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

public class Rest {
    private Client client = null;
    private String url = null;
    
    public Rest(String url) {
        this.client =  ClientBuilder.newClient();
        this.url = url;
    }
    
    public Get get(String path) {
        WebTarget target = client.target(url).path(path);
        Get get = new Get(target);
        return get;
    }
    
    public Post post(String path) {
        WebTarget target = client.target(url).path(path);
        Post post = new Post(target);
        return post;
    }
    
    public Delete delete(String path) {
        WebTarget target = client.target(url).path(path);
        Delete delete = new Delete(target);
        return delete;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        this.client.close();
        this.client = null;
        this.url = null;
    }
    
}
