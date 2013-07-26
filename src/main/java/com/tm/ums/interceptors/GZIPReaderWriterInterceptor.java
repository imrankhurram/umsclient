/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tm.ums.interceptors;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;

/**
 *
 * @author Imran
 */
@Provider
public class GZIPReaderWriterInterceptor implements ReaderInterceptor, WriterInterceptor {

    @Override
    public Object aroundReadFrom(ReaderInterceptorContext context) throws IOException {
        System.out.println("reader interceptor called");
        final InputStream originalInputStream = context.getInputStream();
        context.setInputStream(new GZIPInputStream(originalInputStream));
        return context.proceed();
    }

    @Override
    public void aroundWriteTo(WriterInterceptorContext responseContext) throws IOException {
        System.out.println("writer interceptor called");
        final OutputStream outputStream = responseContext.getOutputStream();
        responseContext.setOutputStream(new GZIPOutputStream(outputStream));
        responseContext.proceed();
    }
}
