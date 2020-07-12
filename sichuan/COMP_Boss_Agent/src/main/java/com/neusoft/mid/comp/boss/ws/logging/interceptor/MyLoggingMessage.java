/**
 * @(#)MyLoggingMessage.java 2013-9-23
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

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.cxf.interceptor.LoggingMessage;

/**
 * @author <a href="mailto:shen.di@neusoft.com"> shendi </a>
 * @version $Revision 1.1 $ 2013-9-23 下午1:59:02
 */
public final class MyLoggingMessage {
    public static final String ID_KEY = LoggingMessage.class.getName() + ".ID";

    private static final AtomicInteger ID = new AtomicInteger();

    private final String heading;

    private final StringBuilder address;

    private final StringBuilder remoteAddr;

    private final StringBuilder contentType;

    private final StringBuilder encoding;

    private final StringBuilder header;

    private final StringBuilder message;

    private final StringBuilder payload;

    private final StringBuilder responseCode;

    private final String id;

    public MyLoggingMessage(String h, String i) {
        this.heading = h;
        this.id = i;

        this.contentType = new StringBuilder();
        this.address = new StringBuilder();
        this.remoteAddr = new StringBuilder();
        this.encoding = new StringBuilder();
        this.header = new StringBuilder();
        this.message = new StringBuilder();
        this.payload = new StringBuilder();
        this.responseCode = new StringBuilder();
    }

    public static String nextId() {
        return Integer.toString(ID.incrementAndGet());
    }

    public StringBuilder getAddress() {
        return this.address;
    }

    public StringBuilder getRemoteAddr() {
        return this.remoteAddr;
    }

    public StringBuilder getEncoding() {
        return this.encoding;
    }

    public StringBuilder getHeader() {
        return this.header;
    }

    public StringBuilder getContentType() {
        return this.contentType;
    }

    public StringBuilder getMessage() {
        return this.message;
    }

    public StringBuilder getPayload() {
        return this.payload;
    }

    public StringBuilder getResponseCode() {
        return this.responseCode;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(this.heading);
        buffer.append("\nID: ").append(this.id);
        if (this.address.length() > 0) {
            buffer.append("\nAddress: ");
            buffer.append(this.address);
        }
        if (this.remoteAddr.length() > 0) {
            buffer.append("\nRemoteAddr: ");
            buffer.append(this.remoteAddr);
        }
        if (this.responseCode.length() > 0) {
            buffer.append("\nResponse-Code: ");
            buffer.append(this.responseCode);
        }
        if (this.encoding.length() > 0) {
            buffer.append("\nEncoding: ");
            buffer.append(this.encoding);
        }
        buffer.append("\nContent-Type: ");
        buffer.append(this.contentType);
        buffer.append("\nHeaders: ");
        buffer.append(this.header);
        if (this.message.length() > 0) {
            buffer.append("\nMessages: ");
            buffer.append(this.message);
        }
        if (this.payload.length() > 0) {
            buffer.append("\nPayload: ");
            buffer.append(this.payload);
        }
        buffer.append("\n--------------------------------------");
        return buffer.toString();
    }
}
