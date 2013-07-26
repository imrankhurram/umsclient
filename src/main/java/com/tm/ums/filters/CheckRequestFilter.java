/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tm.ums.filters;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Imran
 */
@Provider
public class CheckRequestFilter implements ClientRequestFilter {

    @Override
    public void filter(ClientRequestContext requestContext) {
        System.out.println("request filter client");
         if (requestContext.getHeaders().get("Client-Name") == null) {
           requestContext.getHeaders().add("Client-Name", "TM Client");
        }
         
    }
    
}
