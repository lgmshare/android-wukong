package com.lgmshare.component.http;

/**
 * @author lim
 * @description: TODO
 * @email lgmshare@gmail.com
 * @datetime 2016/6/1 10:06
 */
public final class HttpConfiguration {

    private boolean debug;
    private boolean followRedirects;
    private boolean followSslRedirects;
    private boolean retryOnConnectionFailure;

    private long connectTimeout = 0;
    private long maxCacheSize;

    public boolean isDebug() {
        return debug;
    }

    public boolean isFollowRedirects() {
        return followRedirects;
    }

    public boolean isFollowSslRedirects() {
        return followSslRedirects;
    }

    public boolean isRetryOnConnectionFailure() {
        return retryOnConnectionFailure;
    }

    public long getConnectTimeout() {
        return connectTimeout;
    }

    public long getMaxCacheSize() {
        return maxCacheSize;
    }

    private HttpConfiguration(final Builder builder) {
        this.debug = builder.debug;
        this.followRedirects = builder.followRedirects;
        this.followSslRedirects = builder.followSslRedirects;
        this.retryOnConnectionFailure = builder.retryOnConnectionFailure;
        this.connectTimeout = builder.connectTimeout;
        this.maxCacheSize = builder.maxCacheSize;
    }

    public static class Builder {
        private boolean debug;
        private boolean followRedirects;
        private boolean followSslRedirects;
        private boolean retryOnConnectionFailure;

        private long connectTimeout = 0;
        private long maxCacheSize;

        public Builder() {
            debug = false;
            followSslRedirects = true;
            followRedirects = true;
            retryOnConnectionFailure = true;
            connectTimeout = 0;
            maxCacheSize = 0;
        }

        public Builder setDebug(boolean debug) {
            this.debug = debug;
            return this;
        }

        public Builder setFollowRedirects(boolean followRedirects) {
            this.followRedirects = followRedirects;
            return this;
        }

        public Builder setFollowSslRedirects(boolean followSslRedirects) {
            this.followSslRedirects = followSslRedirects;
            return this;
        }

        public Builder setRetryOnConnectionFailure(boolean retryOnConnectionFailure) {
            this.retryOnConnectionFailure = retryOnConnectionFailure;
            return this;
        }

        public Builder setConnectTimeout(long connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        public Builder setMaxCacheSize(long maxCacheSize) {
            this.maxCacheSize = maxCacheSize;
            return this;
        }

        public HttpConfiguration build() {
            initEmptyFieldsWithDefaultValues();
            return new HttpConfiguration(this);
        }

        private void initEmptyFieldsWithDefaultValues() {
            connectTimeout = HttpRequestClient.CONNECT_TIMEOUT;
        }
    }
}
