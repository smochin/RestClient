package com.smochin.common.rest.client.callable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.ws.rs.client.Entity;
import static javax.ws.rs.client.Entity.form;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

public class Post extends Callable<Post> {
    private Map<String, String> params;

    
    public Post(WebTarget target) {
        super(target);
        params = new HashMap<>();
    }
    
    public void param(String key, String value) {
        params.put(key, value);
    }
    
    @Override
    public Response onCall(Invocation.Builder builder) {
        Form form = null;
        if(Objects.nonNull(params)) {
            Set<String> keys = params.keySet();
            form = new Form();
            for(String key: keys) {
                form = form.param(key, params.get(key));
            }
        }
        
        Entity entity = Entity.form(form);
        return builder.post(entity);
    }
    
}
