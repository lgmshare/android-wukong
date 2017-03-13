package com.lgmshare.base.model;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @author lim
 * @description: TODO
 * @email lgmshare@gmail.com
 * @datetime 2016/2/14 13:50
 */
public class Order implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -1906097754081065406L;

    private static final String TAG = Order.class.getSimpleName();
    /**
     * 订单类型
     */
    public static final int TYPE_EXPRESS = 1;
    public static final int TYPE_SHOP = 2;
    public static final int TYPE_CANSONG = 3;
    public static final int TYPE_PINDAN = 4;
    public static final int TYPE_RECOMM_PROD = 1;

    /**
     * 订单支付方式
     */
    public static final int PAY_TYPE_ARRIVE = 1;
    public static final int PAY_TYPE_ONLINE = 2;

    public static final String PACK_TYPE_MANNUAL_COLLECTIVE_ORDER = "0";
    public static final String PACK_TYPE_AUTO_COLLECTIVE_ORDER = "1";
    public static final String PACK_TYPE_NORMAL = "2";

    public static final String STATE_SHOP_PENDING_GRANT_PERMISSION = "7";
    public static final int STATE_SHOP_PENDING_GRANT_PREPERMISSION = 7;

    public static final String NEED_CALL_AFTER_ACCEPT = "true";

    /* 快件ID */
    private String goodsid;
    private int datatype;
    /* 快件编号 */
    private String goodsnum;
    private String goodsname;
    private String senddistance;
    private String receivedistance;
    private String deliverydistance;
    private String sendaddress;
    private String receiveaddress;
    private String sendlon;
    private String sendlat;
    private String receivelon;
    private String receivelat;
    private boolean isnight;
    private int fastType;
    private boolean isactivity;
    private String showdate;
    private boolean isclaimpickup;
    private String voicetime;
    private String voiceurl;
    private int isrecomprod;
    private String other;
    private String addmoney;
    private String expectedtime;
    private String goodsmoney;
    private String iscp;
    private String phone;
    private String goodscost;
    private String claimpickupdate;
    private String shopname;
    private int countdown = -1;
    private int maxCountdown = -1;
    private boolean designated;
    private String packsid;
    private List<String> packsreceiveaddress;
    private boolean ispush;
    private int packstype;
    private boolean isneedcar;

    private int residualtime;// 剩余配送时间(返回的秒)
    // 商品名字
    private String title;
    private String status;
    //是否请求签收（快件状态为7、代购状态为4时判断）
    private boolean reached;
    private String abnormalremark;
    private String tn;
    private String buyaddress;
    public String totalprice; //精品购商品价格
    private String isNeedsCall; //是否需要立即向发件人拨打电话

    private String freezingamount; //需要冻结的金额
    private String ordersource; //订单来源
    private String deliveryLimitValue; //配送时间

    private String ispay;//是否支付 1已支付 0未支付

    public String getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public int getDatatype() {
        return datatype;
    }

    public void setDatatype(int datatype) {
        this.datatype = datatype;
    }

    public String getGoodsnum() {
        return goodsnum;
    }

    public void setGoodsnum(String goodsnum) {
        this.goodsnum = goodsnum;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public String getSenddistance() {
        return senddistance;
    }

    public void setSenddistance(String senddistance) {
        this.senddistance = senddistance;
    }

    public String getReceivedistance() {
        return receivedistance;
    }

    public void setReceivedistance(String receivedistance) {
        this.receivedistance = receivedistance;
    }

    public String getDeliverydistance() {
        return deliverydistance;
    }

    public void setDeliverydistance(String deliverydistance) {
        this.deliverydistance = deliverydistance;
    }

    public String getSendaddress() {
        return sendaddress;
    }

    public void setSendaddress(String sendaddress) {
        this.sendaddress = sendaddress;
    }

    public String getReceiveaddress() {
        return receiveaddress;
    }

    public void setReceiveaddress(String receiveaddress) {
        this.receiveaddress = receiveaddress;
    }

    public boolean isnight() {
        return isnight;
    }

    public void setIsnight(boolean isnight) {
        this.isnight = isnight;
    }

    public int getFastType() {
        return fastType;
    }

    public void setFastType(int fastType) {
        this.fastType = fastType;
    }

    public boolean isactivity() {
        return isactivity;
    }

    public void setIsactivity(boolean isactivity) {
        this.isactivity = isactivity;
    }

    public String getShowdate() {
        return showdate;
    }

    public void setShowdate(String showdate) {
        this.showdate = showdate;
    }

    public boolean isclaimpickup() {
        return isclaimpickup;
    }

    public void setIsclaimpickup(boolean isclaimpickup) {
        this.isclaimpickup = isclaimpickup;
    }

    public String getVoicetime() {
        return voicetime;
    }

    public void setVoicetime(String voicetime) {
        this.voicetime = voicetime;
    }

    public String getVoiceurl() {
        return voiceurl;
    }

    public void setVoiceurl(String voiceurl) {
        this.voiceurl = voiceurl;
    }

    public int getIsrecomprod() {
        return isrecomprod;
    }

    public void setIsrecomprod(int isrecomprod) {
        this.isrecomprod = isrecomprod;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getAddmoney() {
        return addmoney;
    }

    public void setAddmoney(String addmoney) {
        this.addmoney = addmoney;
    }

    public String getExpectedtime() {
        return expectedtime;
    }

    public void setExpectedtime(String expectedtime) {
        this.expectedtime = expectedtime;
    }

    public String getGoodsmoney() {
        return goodsmoney;
    }

    public void setGoodsmoney(String goodsmoney) {
        this.goodsmoney = goodsmoney;
    }

    public String getIscp() {
        //fastjson解析会出现0为空
        if (TextUtils.isEmpty(iscp)) {
            return "0";
        }
        return iscp;
    }

    public void setIscp(String iscp) {
        this.iscp = iscp;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGoodscost() {
        return goodscost;
    }

    public void setGoodscost(String goodscost) {
        this.goodscost = goodscost;
    }

    public String getClaimpickupdate() {
        return claimpickupdate;
    }

    public void setClaimpickupdate(String claimpickupdate) {
        this.claimpickupdate = claimpickupdate;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public int getCountdown() {
        return countdown;
    }

    public void setCountdown(int countdown) {
        this.countdown = countdown;
    }

    public int getMaxCountdown() {
        return maxCountdown;
    }

    public void setMaxCountdown(int maxCountdown) {
        this.maxCountdown = maxCountdown;
    }

    public boolean isDesignated() {
        return designated;
    }

    public void setDesignated(boolean designated) {
        this.designated = designated;
    }

    public String getPacksid() {
        return packsid;
    }

    public void setPacksid(String packsid) {
        this.packsid = packsid;
    }

    public List<String> getPacksreceiveaddress() {
        return packsreceiveaddress;
    }

    public void setPacksreceiveaddress(List<String> packsreceiveaddress) {
        this.packsreceiveaddress = packsreceiveaddress;
    }

    public boolean ispush() {
        return ispush;
    }

    public void setIspush(boolean ispush) {
        this.ispush = ispush;
    }

    public int getPackstype() {
        return packstype;
    }

    public void setPackstype(int packstype) {
        this.packstype = packstype;
    }

    public boolean isneedcar() {
        return isneedcar;
    }

    public void setIsneedcar(boolean isneedcar) {
        this.isneedcar = isneedcar;
    }

    public int getResidualtime() {
        return residualtime;
    }

    public void setResidualtime(int residualtime) {
        this.residualtime = residualtime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isReached() {
        return reached;
    }

    public void setReached(boolean reached) {
        this.reached = reached;
    }

    public String getAbnormalremark() {
        return abnormalremark;
    }

    public void setAbnormalremark(String abnormalremark) {
        this.abnormalremark = abnormalremark;
    }

    public String getTn() {
        return tn;
    }

    public void setTn(String tn) {
        this.tn = tn;
    }

    public String getBuyaddress() {
        return buyaddress;
    }

    public void setBuyaddress(String buyaddress) {
        this.buyaddress = buyaddress;
    }

    public String getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }

    public String getIsNeedsCall() {
        return isNeedsCall;
    }

    public void setIsNeedsCall(String isNeedsCall) {
        this.isNeedsCall = isNeedsCall;
    }

    public String getFreezingamount() {
        return freezingamount;
    }

    public void setFreezingamount(String freezingamount) {
        this.freezingamount = freezingamount;
    }

    public String getOrdersource() {
        return ordersource;
    }

    public void setOrdersource(String ordersource) {
        this.ordersource = ordersource;
    }

    public String getSendlon() {
        return sendlon;
    }

    public void setSendlon(String sendlon) {
        this.sendlon = sendlon;
    }

    public String getSendlat() {
        return sendlat;
    }

    public void setSendlat(String sendlat) {
        this.sendlat = sendlat;
    }

    public String getReceivelon() {
        return receivelon;
    }

    public void setReceivelon(String receivelon) {
        this.receivelon = receivelon;
    }

    public String getReceivelat() {
        return receivelat;
    }

    public void setReceivelat(String receivelat) {
        this.receivelat = receivelat;
    }

    public String getDeliveryLimitValue() {
        return deliveryLimitValue;
    }

    public void setDeliveryLimitValue(String deliveryLimitValue) {
        this.deliveryLimitValue = deliveryLimitValue;
    }

    public String getIspay() {
        return ispay;
    }

    public void setIspay(String ispay) {
        this.ispay = ispay;
    }
}
