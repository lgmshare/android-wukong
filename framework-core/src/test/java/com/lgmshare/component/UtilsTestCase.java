package com.lgmshare.component;

import com.lgmshare.component.utils.StringUtils;
import com.lgmshare.component.utils.TranslateIntoPinYinUtils;

import junit.framework.Assert;

import org.junit.Test;

/**
 * @author lim
 * @description: TODO
 * @email lgmshare@gmail.com
 * @datetime 2016/6/8 10:35
 */
public class UtilsTestCase {

    @Test
    public void stringUtilTest() {
        Assert.assertNotNull(StringUtils.hexEncode("lgmshare".getBytes()));
        Assert.assertNotNull(StringUtils.hexEncode("".getBytes()));
        Assert.assertNotNull(StringUtils.hexEncode(new byte[3]));
        Assert.assertNull(StringUtils.hexEncode(null));

        //System.out.println(DateUtils.getNowDate().toString());
        //System.out.println(DateUtils.formatSdata("201412120000002",1));
        System.out.println(TranslateIntoPinYinUtils.getPinYin("各国和我给我后"));
        System.out.println(TranslateIntoPinYinUtils.getFirstPinYin("gegsw"));
        //Assert.assertEquals("2014-12-12", DateUtils.formatSdata("20141212000000",1));
    }

}
