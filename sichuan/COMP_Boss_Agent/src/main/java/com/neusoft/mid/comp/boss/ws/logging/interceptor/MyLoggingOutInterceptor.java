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

import java.io.OutputStream;
import java.io.PrintWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.LoggingMessage;
import org.apache.cxf.io.CacheAndWriteOutputStream;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.io.CachedOutputStreamCallback;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;

public class MyLoggingOutInterceptor extends AbstractPhaseInterceptor {
    class LoggingCallback implements CachedOutputStreamCallback {
        private final transient Log logger = LogFactory.getLog(getClass());

        public void onFlush(CachedOutputStream cachedoutputstream) {
        }

        public void onClose(CachedOutputStream cos) {
            String id = (String) message.getExchange().get(LoggingMessage.ID_KEY);
            if (id == null) {
                id = LoggingMessage.nextId();
                message.getExchange().put(LoggingMessage.ID_KEY, id);
            }
            LoggingMessage buffer = new LoggingMessage(
                    "Outbound Message\n---------------------------", id);
            Integer responseCode = (Integer) message.get(Message.RESPONSE_CODE);
            if (responseCode != null) buffer.getResponseCode().append(responseCode);
            String encoding = (String) message.get(Message.ENCODING);
            if (encoding != null) buffer.getEncoding().append(encoding);
            String address = (String) message.get(Message.ENDPOINT_ADDRESS);
            if (address != null) buffer.getAddress().append(address);
            String ct = (String) message.get("Content-Type");
            if (ct != null) buffer.getContentType().append(ct);
            Object headers = message.get(Message.PROTOCOL_HEADERS);
            if (headers != null) buffer.getHeader().append(headers);
            if (cos.getTempFile() == null) {
                if (cos.size() > limit)
                    buffer.getMessage().append(
                            (new StringBuilder()).append("(message truncated to ").append(limit)
                                    .append(" bytes)\n").toString());
            } else {
                buffer.getMessage().append("Outbound Message (saved to tmp file):\n");
                buffer.getMessage().append(
                        (new StringBuilder()).append("Filename: ")
                                .append(cos.getTempFile().getAbsolutePath()).append("\n")
                                .toString());
                if (cos.size() > limit)
                    buffer.getMessage().append(
                            (new StringBuilder()).append("(message truncated to ").append(limit)
                                    .append(" bytes)\n").toString());
            }
            try {
                cos.writeCacheTo(buffer.getPayload(), limit);
            } catch (Exception ex) {
            }
            if (writer != null)
                writer.println(transform(buffer.toString()));
            else
                logger.info(transform(buffer.toString()));
            try {
                cos.lockOutputStream();
                cos.resetOut(null, false);
            } catch (Exception ex) {
            }
            message.setContent(java.io.OutputStream.class, origStream);
        }

        private final Message message;

        private final OutputStream origStream;

        final MyLoggingOutInterceptor this$0;

        public LoggingCallback(Message msg, OutputStream os) {
            super();
            this$0 = MyLoggingOutInterceptor.this;
            message = msg;
            origStream = os;
        }
    }

    public MyLoggingOutInterceptor(String phase) {
        super(phase);
        limit = 102400;
        addBefore(org.apache.cxf.interceptor.StaxOutInterceptor.class.getName());
    }

    public MyLoggingOutInterceptor() {
        this("pre-stream");
    }

    public MyLoggingOutInterceptor(int lim) {
        this();
        limit = lim;
    }

    public MyLoggingOutInterceptor(PrintWriter w) {
        this();
        writer = w;
    }

    public void setLimit(int lim) {
        limit = lim;
    }

    public int getLimit() {
        return limit;
    }

    public void handleMessage(Message message) throws Fault {
        OutputStream os = (OutputStream) message.getContent(java.io.OutputStream.class);
        if (os == null) return;

        boolean hasLogged = message.containsKey(LOG_SETUP);
        if (!hasLogged) {
            message.put(LOG_SETUP, Boolean.TRUE);
            CacheAndWriteOutputStream newOut = new CacheAndWriteOutputStream(os);
            message.setContent(java.io.OutputStream.class, newOut);
            newOut.registerCallback(new LoggingCallback(message, os));
        }

    }

    protected String transform(String originalLogString) {
        return originalLogString;
    }

    private static final String LOG_SETUP = (new StringBuilder())
            .append(MyLoggingOutInterceptor.class.getName()).append(".log-setup").toString();

    private int limit;

    private PrintWriter writer;

}
