package com.lmalvarez.tools.webservices;


import com.lmalvarez.tools.webservices.model.Request;
import com.lmalvarez.tools.webservices.model.Response;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Rest {
    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String CONTENT_TYPE_HTML = "text/html";

    public static Response get(Request request) throws Exception {
        if(Objects.isNull(request) || request.getUrl() == null || request.getUrl().isEmpty()){
            throw new Exception("Request object with url is required");
        }
        if(request.getUrl().toLowerCase().contains("https")){
            return getSsl(request);
        } else {
            return getNotSsl(request);
        }
    }

    public static Response post(Request request) throws Exception {
        if(Objects.isNull(request) || request.getUrl() == null || request.getUrl().isEmpty()){
            throw new Exception("Request object with url is required");
        }
        if(request.getUrl().toLowerCase().contains("https")){
            return postSsl(request);
        } else {
            return postNotSsl(request);
        }
    }

    private static Response getNotSsl(Request request) throws Exception {
        Response response = new Response();
        try {
            Set set = request.getParameters().entrySet();
            Iterator iterator = set.iterator();
            String params = "";
            while (iterator.hasNext()) {
                Map.Entry mentry = (Map.Entry) iterator.next();
                params += mentry.getKey() + "=" + mentry.getValue() + (iterator.hasNext() ? "&" : "");
            }

            String strURL = request.getUrl() + (!request.getUrl().contains("?") ? "?" : "") + params;

            URL url = new URL(strURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            set = request.getHeaders().entrySet();
            iterator = set.iterator();
            while (iterator.hasNext()) {
                Map.Entry mentry = (Map.Entry) iterator.next();
                conn.setRequestProperty(mentry.getKey().toString(), mentry.getValue().toString());
            }

            try (DataInputStream input = new DataInputStream(conn.getInputStream())) {
                response.setHeaders(conn.getHeaderFields());
                String strRes = "";
                for (int c = input.read(); c != -1; c = input.read()) {
                    strRes += (char) c;
                }
                response.setContent(strRes);
                response.setStatus(conn.getResponseCode());
            } catch (IOException iex) {
                try (DataInputStream input = new DataInputStream(conn.getErrorStream())) {
                    response.setHeaders(conn.getHeaderFields());
                    String strRes = "";
                    for (int c = input.read(); c != -1; c = input.read()) {
                        strRes += (char) c;
                    }
                    response.setContent(strRes);
                    response.setStatus(conn.getResponseCode());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return response;
    }


    private static Response getSsl(Request request) throws Exception {
        Response response = new Response();
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }

                        @Override
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
            };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            HostnameVerifier allHostsValid = new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };

            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

            Set set = request.getParameters().entrySet();
            Iterator iterator = set.iterator();
            String params = "";
            while (iterator.hasNext()) {
                Map.Entry mentry = (Map.Entry) iterator.next();
                params += mentry.getKey() + "=" + mentry.getValue() + (iterator.hasNext() ? "&" : "");
            }

            String strURL = request.getUrl() + (!request.getUrl().contains("?") ? "?" : "") + params;

            URL url = new URL(strURL);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

            set = request.getHeaders().entrySet();
            iterator = set.iterator();
            while (iterator.hasNext()) {
                Map.Entry mentry = (Map.Entry) iterator.next();
                conn.setRequestProperty(mentry.getKey().toString(), mentry.getValue().toString());
            }

            try (DataInputStream input = new DataInputStream(conn.getInputStream())) {
                response.setHeaders(conn.getHeaderFields());
                String strRes = "";
                for (int c = input.read(); c != -1; c = input.read()) {
                    strRes += (char) c;
                }
                response.setContent(strRes);
                response.setStatus(conn.getResponseCode());
            } catch (IOException iex) {
                try (DataInputStream input = new DataInputStream(conn.getErrorStream())) {
                    response.setHeaders(conn.getHeaderFields());
                    String strRes = "";
                    for (int c = input.read(); c != -1; c = input.read()) {
                        strRes += (char) c;
                    }
                    response.setContent(strRes);
                    response.setStatus(conn.getResponseCode());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return response;
    }

    private static Response postNotSsl(Request request) throws Exception {
        Response response = new Response();
        try {
            URL myurl = new URL(request.getUrl());
            HttpURLConnection con = (HttpURLConnection) myurl.openConnection();
            con.setRequestMethod("POST");

            con.setRequestProperty("User-Agent", "Mozilla/5.0");

            Set set = request.getHeaders().entrySet();
            Iterator iterator = set.iterator();
            while (iterator.hasNext()) {
                Map.Entry mentry = (Map.Entry) iterator.next();
                con.setRequestProperty(mentry.getKey().toString(), mentry.getValue().toString());
            }

            con.setDoOutput(true);
            con.setRequestProperty("Accept", request.getContentType());
            con.setRequestProperty("content-type", request.getContentType() + "; charset=utf-8");

            try (DataOutputStream output = new DataOutputStream(con.getOutputStream())) {
                byte[] input = request.getContent().getBytes("utf-8");
                output.write(input, 0, input.length);
            }

            try (DataInputStream input = new DataInputStream(con.getInputStream())) {
                response.setHeaders(con.getHeaderFields());
                String strRes = "";
                for (int c = input.read(); c != -1; c = input.read()) {
                    strRes += (char) c;
                }
                response.setContent(strRes);
                response.setStatus(con.getResponseCode());
            } catch (IOException iex) {
                try (DataInputStream input = new DataInputStream(con.getErrorStream())) {
                    response.setHeaders(con.getHeaderFields());
                    String strRes = "";
                    for (int c = input.read(); c != -1; c = input.read()) {
                        strRes += (char) c;
                    }
                    response.setContent(strRes);
                    response.setStatus(con.getResponseCode());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return response;
    }

    private static Response postSsl(Request request) throws Exception {
        Response response = new Response();
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }

                        @Override
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
            };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            HostnameVerifier allHostsValid = new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };

            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

            URL myurl = new URL(request.getUrl());
            HttpsURLConnection con = (HttpsURLConnection) myurl.openConnection();
            con.setRequestMethod("POST");

            con.setRequestProperty("User-Agent", "Mozilla/5.0");

            Set set = request.getHeaders().entrySet();
            Iterator iterator = set.iterator();
            while (iterator.hasNext()) {
                Map.Entry mentry = (Map.Entry) iterator.next();
                con.setRequestProperty(mentry.getKey().toString(), mentry.getValue().toString());
            }

            con.setDoOutput(true);
            con.setRequestProperty("Accept", request.getContentType());
            con.setRequestProperty("content-type", request.getContentType() + "; charset=utf-8");

            try (DataOutputStream output = new DataOutputStream(con.getOutputStream())) {
                byte[] input = request.getContent().getBytes("utf-8");
                output.write(input, 0, input.length);
            }

            try (DataInputStream input = new DataInputStream(con.getInputStream())) {
                response.setHeaders(con.getHeaderFields());
                String strRes = "";
                for (int c = input.read(); c != -1; c = input.read()) {
                    strRes += (char) c;
                }
                response.setContent(strRes);
                response.setStatus(con.getResponseCode());
            } catch (IOException iex) {
                try (DataInputStream input = new DataInputStream(con.getErrorStream())) {
                    response.setHeaders(con.getHeaderFields());
                    String strRes = "";
                    for (int c = input.read(); c != -1; c = input.read()) {
                        strRes += (char) c;
                    }
                    response.setContent(strRes);
                    response.setStatus(con.getResponseCode());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return response;
    }
}
