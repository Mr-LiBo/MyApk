/*******************************************************************************
 * Copyright 2011-2015 Sergey Tarasevich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.myApk.framework.utils;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import android.content.Context;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * .
 *
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
public class OkHttpImageDownloader extends BaseImageDownloader {

    static final int DEFAULT_READ_TIMEOUT_MILLIS = 20 * 1000; // 20s
    static final int DEFAULT_WRITE_TIMEOUT_MILLIS = 20 * 1000; // 20s
    static final int DEFAULT_CONNECT_TIMEOUT_MILLIS = 15 * 1000; // 15s
    private OkHttpClient client;

    private static OkHttpClient defaultOkHttpClient() {
        X509TrustManager xtm = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                X509Certificate[] x509Certificates = new X509Certificate[0];
                return x509Certificates;
            }
        };

        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("SSL");

            sslContext.init(null, new TrustManager[]{xtm}, new SecureRandom());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }


        OkHttpClient.Builder builder= new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
                .readTimeout(DEFAULT_READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
                .writeTimeout(DEFAULT_WRITE_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
                .followRedirects(true)
                .followSslRedirects(true)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });
        if(sslContext!=null){
            builder.sslSocketFactory(sslContext.getSocketFactory(),xtm);
        }
        OkHttpClient client = builder.build();
        return client;
    }

    public OkHttpImageDownloader(Context context) {
        super(context);
        this.client = defaultOkHttpClient();
    }

    @Override
    protected InputStream getStreamFromNetwork(String imageUri, Object extra) throws IOException {
        Request request = new Request.Builder().url(imageUri).build();
        return client.newCall(request).execute().body().byteStream();
    }
}
