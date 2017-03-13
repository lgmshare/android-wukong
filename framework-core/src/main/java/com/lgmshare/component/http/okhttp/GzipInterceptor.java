package com.lgmshare.component.http.okhttp;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;
import okio.GzipSink;
import okio.Okio;

/**
 *
 * @author lim
 * @description: TODO
 * @email lgmshare@gmail.com
 * @datetime 2016/5/6 18:32
 */
class GzipInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        if (originalRequest.body() == null || originalRequest.header("Content-Encoding") != null) {
            return chain.proceed(originalRequest);
        }
        Response originalResponse = chain.proceed(chain.request());
        List<String> cookieList =  originalResponse.headers("Set-Cookie");
        if(cookieList != null) {
            for(String s:cookieList) {//Cookie的格式为:cookieName=cookieValue;path=xxx
                //保存你需要的cookie数据
            }
        }
        Request compressedRequest = originalRequest.newBuilder()
                .header("Content-Encoding", "gzip")
                .method(originalRequest.method(), gzip(originalRequest.body()))
                .build();
        return chain.proceed(compressedRequest);
    }

    private RequestBody gzip(final RequestBody body) {
        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return body.contentType();
            }

            @Override
            public long contentLength() {
                try {
                    return body.contentLength();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return -1;
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                BufferedSink gzipSink = Okio.buffer(new GzipSink(sink));
                body.writeTo(gzipSink);
                gzipSink.close();
            }
        };
    }
}