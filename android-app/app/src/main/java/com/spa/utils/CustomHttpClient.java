//
//  
// Created by Diwakar on 06-01-15.
//
package com.spa.utils;

import android.support.v4.util.Pair;
import android.util.Base64;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class CustomHttpClient {
    public static final int CONNECT_TIMEOUT = 600 * 1000; // milliseconds

    private static String getQuery(List<Pair<String, String>> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (Pair pair : params) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode((String) pair.first, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode((String) pair.second, "UTF-8"));
        }

        return result.toString();
    }

    public static String executeHttpPost(String url, List<Pair<String, String>> postParameters) throws Exception {
        String response = "";
        BufferedReader in = null;
        HttpURLConnection connection;
        connection = null;
        try {
           // final String basicAuth = "Basic " + Base64.encodeToString("user:password".getBytes(), Base64.NO_WRAP);
            URL mUrl = new URL(url);
            connection = (HttpURLConnection) mUrl.openConnection();
           connection.setRequestProperty(
                    "Content-Type", "application/x-www-form-urlencoded");
            connection.setReadTimeout(100000);
            connection.setConnectTimeout(CONNECT_TIMEOUT);
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
           // connection.setRequestProperty("Authorization",basicAuth);
            connection.setDoOutput(true);
            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getQuery(postParameters));
            writer.flush();
            writer.close();
            os.close();
            connection.connect();
            int responseCode = connection.getResponseCode();

            String line;
            if (responseCode == HttpsURLConnection.HTTP_OK) {

                in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = in.readLine()) != null) {
                    response += line;
                }
            } else if (responseCode != HttpURLConnection.HTTP_OK) {
                in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = in.readLine()) != null) {
                    response += line;
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response;
    }

    public static String executeHttpPostJsonObject(String urlstring,
                                                   String postParameters) throws Exception {
        HttpURLConnection conn = null;
        try {
            StringBuffer response = null;
            URL url = new URL(urlstring);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(100000);
            conn.setConnectTimeout(150000);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            OutputStream out = new BufferedOutputStream(conn.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
            writer.write(postParameters);
            writer.close();
            out.close();
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode" + responseCode);
            switch (responseCode) {
                case 200:
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String inputLine;
                    response = new StringBuffer();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                    return response.toString();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.disconnect();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        return null;

    }

    public static String executeHttpGet(String url) throws Exception {
        String response = "";
        BufferedReader in = null;
        HttpURLConnection connection;
        try {
            URL mUrl = new URL(url);
            connection = (HttpURLConnection) mUrl.openConnection();
            connection.setConnectTimeout(CONNECT_TIMEOUT);
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();
            String line;
            if (responseCode == HttpsURLConnection.HTTP_OK) {

                in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = in.readLine()) != null) {
                    response += line;
                }
            } else {
                in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = in.readLine()) != null) {
                    response += line;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response;
    }

    public static String executeHttpDelete(String url) throws Exception,
            IOException {
        String response = "";
        BufferedReader in = null;
        HttpURLConnection connection;
        try {
            URL mUrl = new URL(url);
            connection = (HttpURLConnection) mUrl.openConnection();
            connection.setConnectTimeout(CONNECT_TIMEOUT);
            connection.setRequestMethod("DELETE");
            connection.setRequestProperty(
                    "Content-Type", "application/x-www-form-urlencoded");
            connection.connect();
            int responseCode = connection.getResponseCode();
            String line;
            if (responseCode == HttpsURLConnection.HTTP_OK) {

                in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = in.readLine()) != null) {
                    response += line;
                }
            } else {
                in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = in.readLine()) != null) {
                    response += line;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return response;
    }

}
