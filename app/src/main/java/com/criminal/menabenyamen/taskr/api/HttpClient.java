package com.criminal.menabenyamen.taskr.api;

import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public final class HttpClient {
    private static final String TAG = HttpClient.class.getSimpleName();

    public HttpResponse get(String url, RequestConfiguration requestConfiguration) {
        return doRequest(url, "GET", requestConfiguration);
    }

    public HttpResponse post(String url, RequestConfiguration requestConfiguration) {
        return doRequest(url, "POST", requestConfiguration);
    }

    public HttpResponse put(String url, RequestConfiguration requestConfiguration) {
        return doRequest(url, "PUT", requestConfiguration);
    }

    public HttpResponse delete(String url, RequestConfiguration requestConfiguration) {
        return doRequest(url, "DELETE", requestConfiguration);
    }

    private HttpResponse doRequest(String url, String method, RequestConfiguration requestConfiguration) {
        HttpURLConnection connection = null;
        try {
            URL requestUrl = new URL(url);
            connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod(method);

            if(requestConfiguration == null) {
                requestConfiguration = getDefaultRequestConfiguration();
            }

            setRequestHeaders(requestConfiguration, connection);

            String body = requestConfiguration.getBody();

            if(body != null) {
                connection.setDoOutput(true);
                ByteArrayInputStream inputStream = new ByteArrayInputStream(body.getBytes());
                writeToOutputStream(inputStream, connection.getOutputStream());
            }

            InputStream in = connection.getInputStream();
            return getAsHttpResponse(in, connection);
        } catch (IOException e) {
            Log.e(TAG, "doRequest: Error", e);
        } finally {
            if(connection != null) {
                connection.disconnect();
            }
        }

        return null;
    }

    private void setRequestHeaders(RequestConfiguration requestConfiguration, HttpURLConnection connection) {
        Map<String, String> headers = requestConfiguration.getHeaders();

        for(Map.Entry<String, String> entry : headers.entrySet()) {
            connection.setRequestProperty(entry.getKey(), entry.getValue());
        }
    }

    private HttpResponse getAsHttpResponse(InputStream inputStream, HttpURLConnection connection) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        writeToOutputStream(inputStream, outputStream);

        final int statusCode = connection.getResponseCode();
        final String responseMessage = connection.getResponseMessage();
        final Map<String, List<String>> headers = connection.getHeaderFields();
        final byte[] response = outputStream.toByteArray();

        outputStream.close();

        return new HttpResponse(responseMessage, statusCode, headers, response);
    }

    private void writeToOutputStream(InputStream is, OutputStream os) throws IOException {
        int bytesRead;
        byte[] buffer = new byte[1024];

        try {
            while((bytesRead = is.read(buffer)) > 0) {
                os.write(buffer, 0, bytesRead);
            }
        } finally {
            os.close();
        }
    }

    private RequestConfiguration getDefaultRequestConfiguration() {
        return new RequestConfiguration.Builder()
                .setHeader("Content-type", "application/json")
                .setHeader("accept", "application/json")
                .build();
    }
}
