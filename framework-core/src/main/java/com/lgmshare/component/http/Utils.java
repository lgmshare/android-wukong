package com.lgmshare.component.http;

import android.util.Log;

import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.BitSet;
import java.util.List;

/**
 * @author lim
 * @description: TODO
 * @email lgmshare@gmail.com
 * @datetime 2016/5/9 11:48
 */
public class Utils {

    private static final Charset UTF_8 = Charset.forName("UTF-8");

    private static final String PARAMETER_SEPARATOR = "&";
    private static final String NAME_VALUE_SEPARATOR = "=";

    /**
     * Will encode url, if not disabled, and adds params on the end of it
     *
     * @param url             String with URL, should be valid URL without params
     * @param params          RequestParams to be appended on the end of URL
     * @param shouldEncodeUrl whether url should be encoded (replaces spaces with %20)
     * @return encoded url if requested with params appended if any available
     */
    public static String getUrlWithQueryString(boolean shouldEncodeUrl, String url, RequestParams params) {
        if (url == null)
            return null;

        if (shouldEncodeUrl) {
            try {
                String decodedURL = URLDecoder.decode(url, "UTF-8");
                URL _url = new URL(decodedURL);
                URI _uri = new URI(_url.getProtocol(), _url.getUserInfo(), _url.getHost(), _url.getPort(), _url.getPath(), _url.getQuery(), _url.getRef());
                url = _uri.toASCIIString();
            } catch (Exception ex) {
                // Should not really happen, added just for sake of validity
                Log.e("Util", "getUrlWithQueryString encoding URL", ex);
            }
        }

        if (params != null) {
            // Construct the query string and trim it, in case it
            // includes any excessive white spaces.
            String paramString = params.getParamString().trim();

            // Only addItem the query string if it isn't empty and it
            // isn't equal to '?'.
            if (!paramString.equals("") && !paramString.equals("?")) {
                url += url.contains("?") ? "&" : "?";
                url += paramString;
            }
        }
        return url;
    }


    /**
     * Returns a String that is suitable for use as an <code>application/x-www-form-urlencoded</code>
     * list of parameters in an HTTP PUT or HTTP POST.
     *
     * @param parameters The parameters to include.
     * @param encoding   The encoding to use.
     */
    public static String format(
            final List<RequestParams.ValueWrapper> parameters,
            final String encoding) {
        final StringBuilder result = new StringBuilder();
        for (final RequestParams.ValueWrapper parameter : parameters) {
            final String encodedName = encodeFormFields(parameter.key, encoding);
            final String encodedValue = encodeFormFields(parameter.value, encoding);
            if (result.length() > 0) {
                result.append(PARAMETER_SEPARATOR);
            }
            result.append(encodedName);
            if (encodedValue != null) {
                result.append(NAME_VALUE_SEPARATOR);
                result.append(encodedValue);
            }
        }
        return result.toString();
    }

    /**
     * Unreserved characters, i.e. alphanumeric, plus: {@code _ - ! . ~ ' ( ) *}
     * <p/>
     * This list is the same as the {@code unreserved} list in
     * <a href="http://www.ietf.org/rfc/rfc2396.txt">RFC 2396</a>
     */
    private static final BitSet UNRESERVED = new BitSet(256);

    /**
     * Safe characters for x-www-form-urlencoded data, as per java.net.URLEncoder and browser behaviour,
     * i.e. alphanumeric plus {@code "-", "_", ".", "*"}
     */
    private static final BitSet URLENCODER = new BitSet(256);

    private static final int RADIX = 16;

    static {
        // unreserved chars
        // alpha characters
        for (int i = 'a'; i <= 'z'; i++) {
            UNRESERVED.set(i);
        }
        for (int i = 'A'; i <= 'Z'; i++) {
            UNRESERVED.set(i);
        }
        // numeric characters
        for (int i = '0'; i <= '9'; i++) {
            UNRESERVED.set(i);
        }
        UNRESERVED.set('_'); // these are the charactes of the "mark" list
        UNRESERVED.set('-');
        UNRESERVED.set('.');
        UNRESERVED.set('*');
        URLENCODER.or(UNRESERVED); // skip remaining unreserved characters
        UNRESERVED.set('!');
        UNRESERVED.set('~');
        UNRESERVED.set('\'');
        UNRESERVED.set('(');
        UNRESERVED.set(')');

    }

    /**
     * Encode/escape www-url-form-encoded content.
     * <p/>
     * Uses the {@link #URLENCODER} set of characters, rather than
     * the set; this is for compatibilty with previous
     * releases, URLEncoder.encode() and most browsers.
     *
     * @param content the content to encode, will convert space to '+'
     * @param charset the charset to use
     * @return
     */
    private static String encodeFormFields(final String content, final String charset) {
        if (content == null) {
            return null;
        }
        return urlencode(content, charset != null ? Charset.forName(charset) :
                UTF_8, URLENCODER, true);
    }

    /**
     * Emcode/escape a portion of a URL, to use with the query part ensure {@code plusAsBlank} is true.
     *
     * @param content     the portion to decode
     * @param charset     the charset to use
     * @param blankAsPlus if {@code true}, then convert space to '+' (e.g. for www-url-form-encoded content), otherwise leave as is.
     * @return
     */
    private static String urlencode(
            final String content,
            final Charset charset,
            final BitSet safechars,
            final boolean blankAsPlus) {
        if (content == null) {
            return null;
        }
        StringBuilder buf = new StringBuilder();
        ByteBuffer bb = charset.encode(content);
        while (bb.hasRemaining()) {
            int b = bb.get() & 0xff;
            if (safechars.get(b)) {
                buf.append((char) b);
            } else if (blankAsPlus && b == ' ') {
                buf.append('+');
            } else {
                buf.append("%");
                char hex1 = Character.toUpperCase(Character.forDigit((b >> 4) & 0xF, RADIX));
                char hex2 = Character.toUpperCase(Character.forDigit(b & 0xF, RADIX));
                buf.append(hex1);
                buf.append(hex2);
            }
        }
        return buf.toString();
    }
}
