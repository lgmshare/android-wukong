package com.lgmshare.base.http;

import android.text.TextUtils;
import android.util.Base64;

import com.lgmshare.base.AppApplication;
import com.lgmshare.base.AppContext;
import com.lgmshare.base.model.User;
import com.lgmshare.component.utils.SecurityUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: lim
 * @description: TODO
 * @email: lgmshare@gmail.com
 * @datetime 2015/12/30
 */
public class HttpRequestURL {

    public static final String BASE_URL = "http://fm.rrkd.cn/";
    /*接口版本**/
    public static final String INTERFACE_VERSION = "1.0.0";
    /*接口相关**/
    public static final String INTERFACE_PLATFORM = "Android";
    public static final String INTERFACE_PDATYPE = "2";//设备类型:Android
    public static final String INTERFACE_CLIENTTYPE = "courier";//设备类型
    /**
     * A接口
     **/
    public static final String URL_A1 = BASE_URL + "RRKDInterface/Interface/loginInterface.php";
    public static final String A1 = "login"; // 用户登录 //A-1、用户登录
    public static final String A2 = "getMobileCode"; // 注册时获取手机验证码
    // //A-2、注册时获取手机验证码
    public static final String A3 = "reg"; // 用户注册 //A-3、用户注册
    public static final String A4 = "pdaLoginOut"; // 终端用户退出登录 //A-4、终端用户退出登录
    public static final String A5 = "userLocate"; // 循环定位用户经纬度(只定位需要定位的用户)
    public static final String A6 = "pushList";// 推送消息信息
    public static final String A7 = "dataConfig"; // 配置信息
    public static final String A8 = "otherReg"; // 第三方注册(新)
    public static final String A9 = "otherLogin"; // 第三方登录(新) /
    public static final String A10 = "androidPushList"; // 第三方登录(新) /
    public static final String A11 = "OrderConfig";// 订单配置信息
    public static final String A12 = "getui";
    public static final String A13 = "login_quick";
    public static final String C28 = "upusergoods"; //
    public static final String C26 = "usergoodslist"; //
    public static final String C29 = "delalusergoods"; // 删除常发货
    public static final String reportPushState = "external/phone/receiptCollect"; // java
    public static final String reportClientId = "external/phone/setUserClient"; // java

    /**
     * B接口
     **/
    public static final String URL_B = BASE_URL + "RRKDInterface/Interface/courierInterface.php";
    public static final String URL_B_UPLOAD = BASE_URL + "RRKDInterface/Interface/Upload/courierUploadInterface.php";
    public static final String URL_B_UPLOAD_daigou = BASE_URL + "RRKDInterface/Interface/agentInterface.php";// 代购重发
    public static final String URL_B_UPLOAD_kuaijian = BASE_URL + "RRKDInterface/Interface/fastInterface.php";// 快件重发

    // =============================================================================================================
    /**
     * 餐送端接口
     */
    public static final String URL_B_UPLOAD_cansong = BASE_URL + "RRKDInterface/Interface/cs_Interface.php";// 餐送附近订单详情
    public static final String URL_B_UPLOAD_QD = BASE_URL + "RRKDInterface/Interface/cs_Interface.php";// 餐送附近订单详情抢单
    public static final String URL_B_UPLOAD_SM = BASE_URL + "RRKDInterface/Interface/cs_Interface.php";// 快递员上门取货
    public static final String URL_B_UPLOAD_CODE = BASE_URL + "RRKDInterface/Interface/cs_Interface.php";//
    public static final String URL_B_UPLOAD_WRITECODE = BASE_URL + "RRKDInterface/Interface/cs_Interface.php";// 快递员填写签收码
    public static final String URL_B_UPLOAD_NEARBAY = BASE_URL + "RRKDInterface/Interface/ohterInterface.php";// 附近信息有效性验证接口
    public static final String F9 = "pushverification";
    public static final String URL_B_UPLOAD_CAN = BASE_URL + "RRKDInterface/Interface/fastInterface.php";// 餐送接单人
    // 对应的商家列表
    public static final String D30 = "getCSBusinessList";
    public static final String URL_B_UPLOAD_CANDEAL = BASE_URL + "RRKDInterface/Interface/fastInterface.php";
    public static final String D32 = "getCSOrderList";
    public static final String URL_B_UPLOAD_CONMIT = BASE_URL + "RRKDInterface/Interface/fastInterface.php";
    public static final String D35 = "OneKeyPickup";
    public static final String D36 = "privilegeList";
    public static final Object D37 = "evaluateBusiness";
    public static final Object D38 = "privilegeDetails";
    public static final String URL_B_UPLOAD_NEBARY_DISCOUNT = BASE_URL + "RRKDInterface/Interface/fastInterface.php";// 附近快递人优惠
    public static final String E8 = "NearbyDiscount";
    public static final String URL_B_UPLOAD_NEBARY_DISCOUNT_DETAIL = BASE_URL + "RRKDInterface/Interface/fastInterface.php";// 附近快递人优惠商家详情
    public static final String E9 = "DiscountDetails";
    public static final String URL_B_UPLOAD_NEBARY_DISCOUNT_SHARE = BASE_URL + "RRKDInterface/Interface/ohterInterface.php";// 分享积分
    public static final String F11 = "share";
    public static final String URL_B_UPLOAD_NEBARY_DISCOUNT_CITY = BASE_URL + "RRKDInterface/Interface/fastInterface.php";// 优惠城市
    public static final String D39 = "getDiscountCityList";
    public final static String URL_B_UPLOAD_EXPRESS = BASE_URL + "RRKDInterface/Interface/courierInterface.php";// 优惠城市
    public static final String D40 = "courierEvaluate";

    //我的接单
    public static final String MY_MAKE_ORDER=BASE_URL+"RRKDInterface/Interface/fastInterface.php";
    public static final String M1="myOrdersList";

    // 2.0 new interface
    public static final String D41 = "nearbyList"; // 接新单---(原推送订单，附近订单)
    public static final String D42 = "takeDeliveryList"; // 待取件，送货中订单列表
    public static final String D43 = "myFastAgentList";
    public static final String D44 = "myOrdersList";
    public static final String D45 = "checkSphereAreaNew";
    public static final String D46 = "courierEvaluatedetail";
    public static final String D47 = "onlinePaydetail";
    public static final String D48 = "selcost_pay";
    public static final String D50 = "packsDetail";
    public static final String D52 = "feesremind";
    public static final String D53 = "inviteReceiver";
    public static final String D54 = "excellentAccept";
    public static final String D55 = "freight_collect";
    public static final String D56 = "MapPoint";
    public static final String D57 = "SignQRCode";//签收二维码
    public static final String D58 = "signcodeValidate";//验证验证码

    /**
     * header
     */
    public static final String L9 = "lookOrder"; // 删除常发货
    public static final String L15 = "vicinageGrabGoods"; // 删除常发货
    public static final String L13 = "getReceivePhone"; // 快递员上门取货
    public static final String L11 = "courierPushSignCode";
    public static final String L12 = "fillSignCode";

    // =============================================================================================================
    /**
     * 获取自由快递人资料
     */
    public static final String URL_B_UPLOAD_KD_INFO = BASE_URL + "RRKDInterface/Interface/courierInterface.php";// 自由快递人信息
    public static final String D31 = "courierAccounts"; // 删除常发货
    public static final String URL_B_UPLOAD_ADDRESS = BASE_URL + "RRKDInterface/Interface/ohterInterface.php";// 快件地址验证
    public static final String F10 = "addressverifica";

    /**
     * Alei 随意购
     */
    public static final String URL_B_UPLOAD_inf = BASE_URL + "RRKDInterface/Interface/Upload/agentUploadInterface.php";
    public static final String B1 = "applyCourier"; // 申请自由快递人
    public static final String B2 = "getApplyCourier"; // 返回快递员信息
    public static final String B4 = "logoutCourier"; // 注销快递员
    public static final String B5 = "applyCredit"; // 申请授信额度
    public static final String B6 = "revokeCredit"; // 撤销申请授信额度
    public static final String B8 = "answer"; // 答题
    public static final String B9 = "logoutApplyCourier"; // 撤销申请
    public static final String B10 = "creditrecord";
    public static final String B11 = "getCourierTotalInfo";
    public static final String B12 = "clock";
    public static final String B13 = "preference_order_operation";

    /**
     * C接口
     **/
    public static final String URL_C_UPLOAD = BASE_URL + "RRKDInterface/Interface/Upload/userUploadInterface.php";
    public static final String UserUploadInterface = BASE_URL + "RRKDInterface/Interface/Upload/userUploadInterface.php";// 常发货物
    public static final String UserGetInterface = BASE_URL + "RRKDInterface/Interface/userInterface.php";// 常发货物列表
    public static final String UserdeleteInterface = BASE_URL + "RRKDInterface/Interface/userInterface.php";// 删除常发货

    /**
     * RRKDInterface/Interface/userInterface.php
     */
    public static final String URL_C = BASE_URL + "RRKDInterface/Interface/userInterface.php";
    public static final String URL_C30 = BASE_URL + "RRKDInterface/Interface/userInterface.php";
    public static final String C1 = "upLoadHeadImg"; // 用户上传头像
    public static final String C2 = "upLoadalbumimg"; // 用户上传相册
    public static final String C3 = "modifymymeans"; // 修改我的资料
    public static final String C4 = "modifypassword"; // C-4修改登录密码
    public static final String C5 = "consumption"; // C-5账户充值，支出，收入记录查询
    public static final String C6 = "getpwdmobilecode"; // C-6获取修改密码的手机验证码
    public static final String C8 = "delalbumimg"; // 删除个人相册照片
    public static final String C7 = "modifyuserpwd"; // 删除个人相册照片
    public static final String C9 = "accountindex"; // 获取个人中心主资料信息
    public static final String URL_C10 = BASE_URL + "RRKDInterface/Interface/courierInterface.php"; // C-10快递员财务分析
    public static final String C10 = "accountsCount"; // C-10快递员财务分析
    public static final String C11 = "courierevalist"; // C-11看快递员历史评价(所有)
    public static final String C12 = "userInfoState"; // 用户隐藏/显示年龄
    public static final String C14 = "userscorelist"; // C-14提取用户积分账户流水
    public static final String C15 = "getoldmobilecode"; // C-15用户获取旧手机号验证码
    public static final String C16 = "getnewmobilecode"; // C-16
    public static final String C17 = "validateoldmobilecode"; // C-17
    public static final String C18 = "upmobile"; // C-18
    public static final String C19 = "userranking"; // C-19
    public static final String C20 = "userinfo"; // C-20 获取用户头像和昵称(新)
    public static final String C21 = "invitelist"; // C-21获取注册用户已建立的邀请关系
    public static final String C22 = "inviteuser"; // C-22向通讯录联系人发出邀请
    public static final String C24 = "applyWithdraw"; // 用户和自由快递人提现申请
    public static final String C25 = "adStatistics"; // 广告点击率统计
    public static final String C30 = "userQRcode"; // 二维码
    public static final String C33 = "userSincerityList"; // 二维码
    public static final String C34 = "myIncomeExpendDetail";
    public static final String C35 = "declineFast";
    public static final String C36 = "certification";
    public static final String C37 = "userTodayTotal";

    /**
     * D接口
     **/
    public static final String URL_D = BASE_URL + "RRKDInterface/Interface/fastInterface.php";
    public static final String URL_D_UPLOAD = BASE_URL + "RRKDInterface/Interface/Upload/fastUploadInterface.php";
    public static final String D1 = "recipientAddress"; // D-1、常用收/取货地薄 列表
    public static final String D2 = "WaitMapPoint"; // 接单列表地图
    public static final String D3 = "nearbyListMapPoint"; // 接单列表地图
    public static final String D4 = "deleteAddress"; // 删除常用收/取货地
    public static final String D5 = "ordertrack"; // 快件跟踪
    public static final String D6 = "getSendInfo"; // 获得常用收寄件信息
    public static final String D7 = "sendOrder"; // D-7、寄件人寄件
    public static final String D8 = "selcost"; // D-8、寄件人确认下单前费用计算（注意未登录用户）
    public static final String D10 = "lookOrder"; // 寄件人、收件人、快递员在各模块下查看快件详情(正常快件)（快件详情只开2个接口，附近详情，管理详情）
    public static final String D12 = "scanningGoods"; // 快递员上门扫描收件(可上传货物照片,图片批量上传)
    public static final String D11 = "recallGoods"; // 快递员在待取件过程中请求取消派件
    public static final String D13 = "courierarrivegoods"; // 快递员请求签收
    public static final String D14 = "getevaluatedetail"; // 发件人、收件人、快递员查看评价详情
    public static final String D15 = "fillSignCode"; // 快递员得到签收码后在终端上回填签收码
    public static final String D16 = "senderRecallGoods"; // 寄件人在待接收、待取件模块下取消寄件
    public static final String D17 = "senderSignGoods"; // 收件人正常签收快件
    public static final String D18 = "modifyFast"; // 发件人修改快件
    public static final String D19 = "courieraddabnormal"; // 快递员在派送过程中上报异常件
    public static final String D20 = "courierPushSignCode"; // 快递员在待签收模块中请求发送短信签收码
    public static final String D21 = "mygoodslist"; // 发件人、收件人、快递员管理快件
    public static final String D22 = "evaluate"; // 发件人、收件人评价快件
    public static final String D23 = "getSendCode"; // 未登录寄件时发送验证码
    public static final String D25 = "reSendOrder"; // 重新发布快件
    public static final String URL_D24 = BASE_URL + "RRKDInterface/Interface/courierInterface.php";
    public static final String D24 = "addStroke"; // 未登录寄件时发送验证码
    public static final String D26 = "orderdetail";//B1-查看订单明细

    /**
     * E接口** 附近+消息业务等接口模块
     */
    public static final String URL_E = BASE_URL + "RRKDInterface/Interface/fastInterface.php";
    public static final String URL_E_DETAIL = BASE_URL + "RRKDInterface/Interface/courierInterface.php";
    public static final String E1 = "vicinageGoodsDetail"; // 附近---快件详细内容(正常)
    public static final String E2 = "vicinageGrabGoods"; // 快递员在附近快件中进行确认抢单
    /**
     * RRKDInterface/Interface/Upload/vicinageUploadInterface.php
     */
    public static final String URL_E3 = BASE_URL + "RRKDInterface/Interface/Upload/vicinageUploadInterface.php"; // 和朋友聊天（快递员联系收寄件人）
    public static final String E3 = "vicinagechat"; // 和朋友聊天（快递员联系收寄件人）
    public static final String E4 = "vicinagegetchatcon"; // 获取聊天内容(主要用于IOS获取聊天内容)
    public static final String E5 = "nearbyList"; // 附近---(快件,代购)

    /**
     * F．其他业务等接口模块 RRKDInterface/Interface/ohterInterface.php
     */
    public static final String URL_F = BASE_URL + "RRKDInterface/Interface/ohterInterface.php";
    public static final String F1 = "addmessage"; // F-1、用户建议反馈
    public static final String F3 = "androidversion"; // F-3、android版本检测
    public static final String F4 = "mysystemsettings"; // F-4终端用户系统设置操作开关
    public static final String F5 = "getmysystemsettings"; // F-5获取系统设置开关
    public static final String F6 = "getSameCity"; // F-6获取同城范围
    public static final String F7 = "complaints"; // F-7 投诉
    public static final String F8 = "locationanaly"; // F-8 经纬度坐标转换成物理地址

    /**
     * J．告示等接口模块 RRKDInterface/Interface/advertisementInterface.php
     */
    public static final String URL_J = BASE_URL + "RRKDInterface/Interface/advertisementInterface.php";
    public static final String J2 = "rollnotice"; // F-7 投诉

    /**
     * I． 支付接口地址 RRKDInterface/Interface/payInterface.php
     */
    public static final String URL_I = BASE_URL + "RRKDInterface/Interface/payInterface.php";
    public static final String I1 = "trade"; // I-1、生成支付宝支付订单
    public static final String I2 = "unionpaypurchase"; // I-2、生成银联支付订单
    public static final String I3 = "wxtrade";


    public static final String I25 = "packsDetail";

    /**
     * H接口** 代购接口地址
     */
    public static final String URL_H_IPLOAD = BASE_URL + "RRKDInterface/Interface/Upload/agentUploadInterface.php";

    /**
     * 精品购算运费
     */
    public static final String URL_H_IP = BASE_URL + "RRKDInterface/Interface/agentInterface.php";

    /**
     * RRKDInterface/Interface/agentInterface.php
     */
    public static final String URL_H = BASE_URL + "RRKDInterface/Interface/agentInterface.php";
    public static final String H1 = "agentpublish"; // 1发布代购并冻结信用卡
    public static final String H2 = "cancelagent"; // H-2取消代购信息
    public static final String H3 = "agentPurchased"; // H-3快递员已购买操作
    public static final String H4 = "agentRequestSign"; // H-4快递员请求签收操作
    public static final String H5 = "agentSignfor"; // H-5签收评价操作发布人签收并评价操作，订单状态为已完成接口（适用于ios,android）
    public static final String H6 = "getagentevaluatedetail"; // H-6查看评价操作
    public static final String H7 = "agentDetail"; // H-7代购信息详细
    public static final String H8 = "agenttrack"; // H-8、代购跟踪
    public static final String H9 = "myAgent"; // H-9、我要为你代购
    public static final String H10 = "myAgentList"; // H-10我的代购-列表
    public static final String H11 = "agentevaluate"; // H-11 评价
    public static final String H12 = "agentpublishinordinary"; // H-12发布代购 (随意购)
    public static final String H13 = "reSendAgent"; // H-13(重新发布代购)
    public static final String H15 = "agentselcost"; // 精品购算运费
    public static final String H16 = "getDGPromotion"; // 是否享受代购活动
    public static final String H17 = "DG_courierAddAbnormal"; // 快递员在派送过程中上报异常代购
    public static final String H18 = "agentfreight"; // 快递员在派送过程中上报异常代购

    /**
     * K．商品接口地址
     */
    public static final String URL_K = BASE_URL + "RRKDInterface/Interface/commodityInterface.php";

    /**
     * K5．店铺接口地址
     */
    public static final String URL_K5 = BASE_URL + "RRKDInterface/Interface/commodityInterface.php";
    public static final String K1 = "commodityList"; // detailed.aspx
    public static final String K2 = "commodityDetail";
    public static final String K3 = "shopSummary";// 店铺介绍
    public static final String K4 = "shopDetail";// 店铺详情
    public static final String K5 = "shopList";

    /**
     * G接口** 各种说明
     */
    public static final String URL_G1 = BASE_URL + "RRKDInterface/More/help.htm"; // 使用帮助
    public static final String URL_G2 = BASE_URL + "RRKDInterface/More/contraband.htm"; // 违禁品
    public static final String URL_G3 = BASE_URL + "RRKDInterface/More/charge.htm"; // 收费标准
    public static final String URL_G4 = BASE_URL + "RRKDInterface/More/Agreement.htm"; // 注册服务条款
    public static final String URL_G44 = BASE_URL + "RRKDInterface/More/pre-authorized.htm"; // 银行卡服务条款
    public static final String URL_G5 = BASE_URL + "RRKDInterface/More/parttimeprotocol.htm"; // 快递员兼职协议
    public static final String URL_G6 = BASE_URL + "RRKDInterface/More/summary.htm"; // 产品说明
    public static final String URL_G7 = BASE_URL + "RRKDInterface/More/cardservice.htm"; // 信用卡绑定协议public
    public static final String URL_G8 = BASE_URL + "RRKDInterface/More/sent_notice.htm"; // 寄件须知
    public static final String URL_G9 = BASE_URL + "RRKDInterface/More/goodstype.htm"; // 快件分类
    public static final String URL_G10 = BASE_URL + "RRKDInterface/More/goodscost.htm"; // 可承接货物价值解释
    public static final String URL_G11 = BASE_URL + "RRKDInterface/More/uploadpid.htm"; // 上传身份证说明
    public static final String URL_G12 = BASE_URL + "RRKDInterface/More/crules.htm"; // 积分规则
    public static final String URL_G13 = BASE_URL + "RRKDInterface/More/magazine.htm"; // 自由快递人杂志
    public static final String URL_G14 = BASE_URL + "RRKDInterface/More/activity.htm"; // 自由快递人活动
    public static final String URL_G15 = BASE_URL + "RRKDInterface/More/whyworth.htm"; // 为什么要声明货物价值
    public static final String URL_G16 = BASE_URL + "RRKDInterface/More/activity_list.htm"; // 活动列表
    public static final String URL_G17 = BASE_URL + "RRKDInterface/More/creditExtensionInfo.htm"; // 授信说明
    public static final String URL_G18 = BASE_URL + "RRKDInterface/More/goodstypes.htm"; // 物品类型
    public static final String URL_G19 = BASE_URL + "RRKDInterface/More/transporttype.htm"; // 交通工具
    public static final String URL_G20 = BASE_URL + "RRKDInterface/More/servicefees.htm"; // 愿付费用说明
    public static final String URL_G21 = BASE_URL + "RRKDInterface/More/delivery.htm"; // 配送时效
    public static final String URL_G22 = BASE_URL + "RRKDInterface/More/train.htm"; // 学习
    public static final String URL_G23 = BASE_URL + "RRKDInterface/More/answer.php"; // 答题
    public static final String URL_G24 = BASE_URL + "RRKDInterface/More/sincerity.htm"; // 诚信说明
    public static final String URL_G25 = BASE_URL + "RRKDInterface/More/prospectus.htm"; // 为什么要指定货物类型、物品价值、交通工具
    public static final String URL_ALIPAY_TRADE = "";
    public static final String GOODS_IMAGE_CACHE_DIR = "myShopImage";

    // M接口 账户 提现功能类
    public static String URL_M = BASE_URL + "RRKDInterface/Interface/accountInterface.php";
    public static String M_1 = "withdrawconfig"; // 可提现银行列表
    public static String M_2 = "modifypassword"; // 修改提现密码
    public static String M_3 = "addwdpassword";// 设置提现密码
    public static String M_4 = "getverificationcode";// 获取设置提现密码的手机验证码
    public static String M_5 = "withdrawapply";// 用户发起提现
    public static String M_6 = "myaccount";
    public static String NEW_A1 = "withdrawbanks";// 提现银行卡列表
    public static String NEW_A2 = "addwithdrawbank";// 增加提现银行卡
    public static String NEW_A3 = "withdrawpassword";// 主要用于用户账户设置/更改提现密码
    public static String NEW_A4 = "withdrawconfig";// 可提现银行列表
    public static String NEW_A5 = "accountstatistic";//接口说明：资金统计
    public static String NEW_A6 = "wdpasswordvalidate";//接口说明：主要用于用户设置提现密码时的验证码和身份证校验
    public static String NEW_A7 = "getverificationcode";//主要用于用户获取设置提现密码的手机验证码
    public static String NEW_A8 = "validateWithdrawDay";//提现日判断和获取
    public static String NEW_A10 = "balanceAndWd";//获取余额和可提现金额


    // 百度接口
    public static String URL_PLACE_SEARCH = "http://api.map.baidu.com/place/v2/search";
    public static String URL_DIRECTION = "http://mTencentSearch.map.baidu.com/direction/v1";
    public static String URL_GEO = "http://mTencentSearch.map.baidu.com/geocoder/v2/";

    //腾讯接口
    public static String URL_TENCENT_DISTANCE_CALCULATOR = "http://apis.map.qq.com/ws/distance/v1";
    public static final String KEY_TENCENT_MAP_DISTANCE_CALCULATE = "keyTencentMapDistanceCalculate";
    public static final String URL_TENCENT_SUGGESTIION_INTERFACE = "http://apis.map.qq.com/ws/place/v1/suggestion";
    //数据上报接口
    public static String URL_REPOST = BASE_URL + "RRKDInterface/Interface/report/report/sendReport";
    //优惠券web列表
    public static String URL_COUPON_LIST = BASE_URL + "RRKDInterface/Interface/couponInterface.php?";
    //引导下载腾讯地图url
    public static final String URL_TENCENT_MAP_APP_INSTALL = "http://3gimg.qq.com/map_site_cms/download/index.html?cid=10012062&appid=mobilemap&logid=renren";

    public static final String URL_FEEDBACK_LINE = "http://www5.53kf.com/webCompany.php";
    public static String getSecretParams(String timestamp, String params) {
        String key = SecurityUtil.encryptMD5((timestamp + "RrkdHttpTools")) + "@_@" + params;
        String secretParams = Base64.encodeToString(key.getBytes(), Base64.DEFAULT);
        return secretParams;
    }

    public static Map<String, String> createCommonHeader() {
        Map<String, String> headers = new HashMap<String, String>();
        long timestamp = System.currentTimeMillis() / 1000;
        User user = AppApplication.getInstance().getAccountManager().getUser();
        if (user != null){
            headers.put("USERNAME", user.getUsername());
            headers.put("TOKEN", user.getToken());
        }
        headers.put("UDID", AppContext.getAppUniqueID());
        headers.put("User-Agent", getUserAgent());
        headers.put("timestamp", timestamp + "");
        headers.put("Accept", "application/json");

        return headers;
    }

    public static String getUserAgent() {
        //"1.5.8/iPhone/iOS 8.1.1/2.00/iPhone 5S";
        StringBuffer sb = new StringBuffer();
        sb.append('"');
        sb.append(HttpRequestURL.INTERFACE_VERSION);
        sb.append("/");
        sb.append("android");
        sb.append("/");
        sb.append("android");
        sb.append("2.3.1");
        sb.append("/");
        sb.append("400,800");
        sb.append("/");
        sb.append("xiaomi");
        sb.append('"');
        return sb.toString();
    }

    static String appUserAgent;
    private static String getUserAgent(AppContext appContext) {
        if(TextUtils.isEmpty(appUserAgent)) {
            StringBuilder ua = new StringBuilder("OSChina.NET");
            ua.append('/'+appContext.getPackageInfo().versionName+'_'+appContext.getPackageInfo().versionCode);//App版本
            ua.append("/Android");//手机系统平台
            ua.append("/"+android.os.Build.VERSION.RELEASE);//手机系统版本
            ua.append("/"+android.os.Build.MODEL); //手机型号
            ua.append("/"+appContext.getAppUniqueID());//客户端唯一标识
            appUserAgent = ua.toString();
        }
        return appUserAgent;
    }
}
