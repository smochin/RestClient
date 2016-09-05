package com.smochin.common.rest.client.callable;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

public class Put extends Callable<Put> {

    public Put(WebTarget target) {
        super(target);
    }

    @Override
    public Response onCall(Invocation.Builder builder) {
        return null;
    }
    
}
