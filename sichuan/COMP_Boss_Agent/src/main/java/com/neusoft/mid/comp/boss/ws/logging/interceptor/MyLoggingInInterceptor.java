/**
 * Copyright 2013 Neusoft. All Right Reserved
 *
 * This file is owned by Neusoft and you may not use, modify, copy,
 * redistribute this file without written permissions.
 *
 * These Terms of Use define legal use of this file, all updates, revisions,
 * substitutions for you. All rights not expressly granted to you are reserved
 * by Neusoft.
 */
package com.neusoft.mid.comp.boss.ws.logging.interceptor;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.transport.http.AbstractHTTPDestination;

public class MyLoggingInInterceptor extends AbstractPhaseInterceptor {
    private final transient Log logger = LogFactory.getLog(getClass());

    public MyLoggingInInterceptor() {
        super("receive");
        limit = 102400;
    }

    public MyLoggingInInterceptor(String phase) {
        super(phase);
        limit = 102400;
    }

    public MyLoggingInInterceptor(int lim) {
        this();
        limit = lim;
    }

    public MyLoggingInInterceptor(PrintWriter w) {
        this();
        writer = w;
    }

    public void setPrintWriter(PrintWriter w) {
        writer = w;
    }

    public PrintWriter getPrintWriter() {
        return writer;
    }

    public void setLimit(int lim) {
        limit = lim;
    }

    public int getLimit() {
        return limit;
    }

    public void handleMessage(Message message) throws Fault {
        if (logger != null) logging(message);
    }

    protected String transform(String originalLogString) {
        return originalLogString;
    }

    private void logging(Message message) throws Fault {
        if (message.containsKey(MyLoggingMessage.ID_KEY)) return;
        String id = (String) message.getExchange().get(MyLoggingMessage.ID_KEY);
        if (id == null) {
            id = MyLoggingMessage.nextId();
            message.getExchange().put(MyLoggingMessage.ID_KEY, id);
        }
        message.put(MyLoggingMessage.ID_KEY, id);
        MyLoggingMessage buffer = new MyLoggingMessage(
                "Inbound Message\n----------------------------", id);
        Integer responseCode = (Integer) message.get(Message.RESPONSE_CODE);
        if (responseCode != null) buffer.getResponseCode().append(responseCode);

        HttpServletRequest request = (HttpServletRequest) message
                .get(AbstractHTTPDestination.HTTP_REQUEST);
        if (request != null) {
            String ipAddress = request.getRemoteAddr();
            if (ipAddress != null) buffer.getRemoteAddr().append(ipAddress);
        }

        String encoding = (String) message.get(Message.ENCODING);
        if (encoding != null) buffer.getEncoding().append(encoding);
        String ct = (String) message.get("Content-Type");
        if (ct != null) buffer.getContentType().append(ct);
        Object headers = message.get(Message.PROTOCOL_HEADERS);
        if (headers != null) buffer.getHeader().append(headers);
        String uri = (String) message.get("org.apache.cxf.request.uri");
        if (uri != null) buffer.getAddress().append(uri);
        InputStream is = (InputStream) message.getContent(java.io.InputStream.class);
        if (is != null) {
            CachedOutputStream bos = new CachedOutputStream();
            try {
                IOUtils.copy(is, bos);
                bos.flush();
                is.close();
                message.setContent(java.io.InputStream.class, bos.getInputStream());
                if (bos.getTempFile() != null) {
                    buffer.getMessage().append("\nMessage (saved to tmp file):\n");
                    buffer.getMessage().append(
                            (new StringBuilder()).append("Filename: ")
                                    .append(bos.getTempFile().getAbsolutePath()).append("\n")
                                    .toString());
                }
                if (bos.size() > limit)
                    buffer.getMessage().append(
                            (new StringBuilder()).append("(message truncated to ").append(limit)
                                    .append(" bytes)\n").toString());
                bos.writeCacheTo(buffer.getPayload(), limit);
                bos.close();
            } catch (IOException e) {
                throw new Fault(e);
            }
        }
        if (writer != null)
            writer.println(transform(buffer.toString()));
        else
            logger.info(transform(buffer.toString()));
    }

    private int limit;

    private PrintWriter writer;

}
