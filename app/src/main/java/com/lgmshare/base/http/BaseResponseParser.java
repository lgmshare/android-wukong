package com.lgmshare.base.http;

/**
 * @author lim
 * @description: TODO
 * @email lgmshare@gmail.com
 * @datetime 2016/6/8 14:36
 */
public interface BaseResponseParser<T> {

    T parse(String data);

}
