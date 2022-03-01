package com.lmalvarez.tools.webservices.model;

import java.util.*;

public class Response {
    private int status;
    private String content;
    private Map<String, List<String>> headers;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    public Map<String, String> getHeadersU() {
        Map<String, String> map = new HashMap<>();
        Set set = headers.entrySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            Map.Entry mentry = (Map.Entry) iterator.next();
            String values = "";
            for (String s : (List<String>) mentry.getValue()) {
                values += s + ((((List<String>) mentry.getValue()).indexOf(s))+1 < ((List<String>) mentry.getValue()).size() ? "," : "");
            }
            String key = (String) mentry.getKey();
            map.put(key, values);
        }
        return map;
    }
}
