package com.lgmshare.base.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.lgmshare.base.R;
import com.lgmshare.base.model.Order;
import com.lgmshare.component.utils.StringUtil;

import java.text.DecimalFormat;
import java.util.List;


/**
 * @author lim
 * @Description: TODO
 * @mail lgmshare@gmail.com
 * @date 2016/1/6
 */
public class OrderListViewItem extends RelativeLayout {

    /**
     * 抢单
     */
    public static final int STATE_FIGHT = 1;
    /**
     * 等待取件
     */
    public static final int STATE_WAITTING = 2;
    /**
     * 配送中
     */
    public static final int STATE_DELIVERING = 3;

    protected Context mContext;

    private FrameLayout ll_new;
    private LinearLayout llException;
    private TextView tvException;
    private ImageView ivNight;
    private TextView iv_jiajia;
    private TextView tv_order_source;
    private TextView tvPrice;
    private LinearLayout ll_distance;
    private LinearLayout ll_send_distance;
    private TextView tv_send_distance;
    private ImageView iv_wo;
    private ImageView iv_fa;
    private ImageView iv_see_map;
    private TextView tv_receive_distance;
    private TextView tv_start;
    private TextView tv_send_address;
    private LinearLayout ll_receive_address;
    private FrameLayout fl_receive_address;
    private TextView tv_receive_address;
    private TextView tvImportantText;
    private LinearLayout ll_important_voice;
    private ImageView iv_important_icon;
    private TextView tvRbtips;
    private LinearLayout llDesignated;
    private LinearLayout ll_important;
    private Button btnCancel;
    private Button btnAccept;
    private Button btnSubmit;

    private TextView tv_ordertime;
    private TextView tv_ordertime_title;

    private Order mOrder;
    private int mState;
    private TextView tv_item_order_arrive_pay;

    public OrderListViewItem(Context context) {
        super(context);
        init(context);
    }

    public OrderListViewItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public OrderListViewItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;

        LayoutInflater.from(mContext).inflate(R.layout.adapter_order_item, this);

        ll_new = (FrameLayout) findViewById(R.id.ll_new);
        llException = (LinearLayout) findViewById(R.id.ll_exception);
        tvException = (TextView) findViewById(R.id.tv_exception);
        tv_order_source = (TextView) findViewById(R.id.tv_order_source);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        iv_jiajia = (TextView) findViewById(R.id.iv_jiajia);
        ll_distance = (LinearLayout) findViewById(R.id.ll_distance);
        ll_send_distance = (LinearLayout) findViewById(R.id.ll_send_distance);
        tv_send_distance = (TextView) findViewById(R.id.tv_send_distance);
        iv_wo = (ImageView) findViewById(R.id.iv_wo);
        iv_fa = (ImageView) findViewById(R.id.iv_fa);
        tv_receive_distance = (TextView) findViewById(R.id.tv_receive_distance);

        tv_start = (TextView) findViewById(R.id.tv_start);
        tv_send_address = (TextView) findViewById(R.id.tv_send_address);
        ll_receive_address = (LinearLayout) findViewById(R.id.ll_receive_address);
        fl_receive_address = (FrameLayout) findViewById(R.id.fl_receive_address);
        tv_receive_address = (TextView) findViewById(R.id.tv_receive_address);
        iv_see_map = (ImageView) findViewById(R.id.iv_see_map);
        ll_important = (LinearLayout) findViewById(R.id.ll_voice);
        tvImportantText = (TextView) findViewById(R.id.tv_important_text);
        ll_important_voice = (LinearLayout) findViewById(R.id.ll_important_voice);
        iv_important_icon = (ImageView) findViewById(R.id.iv_important_icon);
        tvRbtips = (TextView) findViewById(R.id.tv_rbtips);
        llDesignated = (LinearLayout) findViewById(R.id.ll_designated);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        btnAccept = (Button) findViewById(R.id.btn_accept);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
        ivNight = (ImageView) findViewById(R.id.iv_night);

        tv_ordertime = (TextView) findViewById(R.id.tv_ordertime);
        tv_ordertime_title = (TextView) findViewById(R.id.tv_ordertime_title);
        tv_item_order_arrive_pay = (TextView) findViewById(R.id.tv_item_order_arrive_pay);
    }

    public void setData(Order entry, int state) {
        mOrder = entry;
        mState = state;
        displayView(mOrder);
    }

    private void displayView(Order ne) {
        //是否是新订单
        boolean ispublish = ne.ispush();
        if (ispublish) {
            ll_new.setVisibility(VISIBLE);
        } else {
            ll_new.setVisibility(GONE);
        }

        //是否是夜间件
        if (ne.isnight()) {
            ivNight.setVisibility(View.VISIBLE);
        } else {
            ivNight.setVisibility(View.GONE);
        }

        //异常申报信息
        if (TextUtils.isEmpty(ne.getAbnormalremark())) {
            llException.setVisibility(View.GONE);
        } else {
            llException.setVisibility(View.VISIBLE);
            tvException.setText("异常：" + ne.getAbnormalremark());
        }

        //订单类型
        switch (ne.getDatatype()) {
            case Order.TYPE_EXPRESS: // 快件
                orderTypeExpress(ne);
                break;
            case Order.TYPE_SHOP:// 代购
                orderTypeShop(ne);
                break;
            case Order.TYPE_CANSONG:// 餐送
                orderTypeCang(ne);
                break;
            case Order.TYPE_PINDAN:// 拼单
                orderTypePindan(ne);
                break;
            default:
                break;
        }

        //订单金额
        tvPrice.setText(ne.getGoodsmoney().replace("元", ""));
        double jiajia = 0;
        try {
            if (TextUtils.isEmpty(ne.getAddmoney())) {
                jiajia = 0.00d;
            } else {
                jiajia = Double.valueOf(ne.getAddmoney()).doubleValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (jiajia == 0) {
            iv_jiajia.setVisibility(View.GONE);
        } else {
            iv_jiajia.setVisibility(View.VISIBLE);
        }

        // 判断是否是预约件
        if (STATE_DELIVERING != mState && !TextUtils.isEmpty(ne.getClaimpickupdate())) {
            tv_ordertime.setText(ne.getClaimpickupdate());
            tv_ordertime.setVisibility(VISIBLE);
            tv_ordertime_title.setVisibility(VISIBLE);
        } else {
            tv_ordertime.setVisibility(GONE);
            tv_ordertime_title.setVisibility(GONE);
        }
        //是否到付
        if (Order.TYPE_EXPRESS == ne.getDatatype()) {
            if ("0".equals(ne.getIspay())) {
                //未支付  显示到付标志
                tv_item_order_arrive_pay.setVisibility(View.VISIBLE);
            } else {
                //已支付  不显示到付标志
                tv_item_order_arrive_pay.setVisibility(View.GONE);
            }
        } else {
            tv_item_order_arrive_pay.setVisibility(View.GONE);
        }
        //授信接单
        if ("1".equalsIgnoreCase(ne.getIscp())) {
            tvRbtips.setVisibility(View.GONE);
        } else if ("2".equalsIgnoreCase(ne.getIscp())) {
            tvRbtips.setVisibility(View.VISIBLE);
        } else if ("3".equalsIgnoreCase(ne.getIscp())) {
            tvRbtips.setVisibility(View.VISIBLE);
        } else {
            tvRbtips.setVisibility(View.GONE);
        }

        //收发货距离
        displayDistance(ne);
        //收发货地址
        displayAddress(ne);
        displayImportInfo(ne);
        displayButton(ne);
    }

    /**
     * 快件
     * 注：只有客户端发的快件才显示为“急件”
     *
     * @param ne
     */
    private void orderTypeExpress(Order ne) {
        tv_order_source.setText(ne.getGoodsname());
        tv_order_source.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_password, 0, 0, 0);
    }

    /**
     * 代购
     *
     * @param ne
     */
    private void orderTypeShop(Order ne) {
        if (!TextUtils.isEmpty(ne.getGoodsname())) {
            tv_order_source.setText(ne.getGoodsname());
        } else {
            tv_order_source.setText("帮买订单");
        }
        tv_order_source.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_password, 0, 0, 0);
    }

    /**
     * 餐送
     *
     * @param ne
     */
    private void orderTypeCang(Order ne) {
        tv_order_source.setText("餐送订单");
        tv_order_source.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_password, 0, 0, 0);
    }

    /**
     * 拼单
     *
     * @param ne
     */
    private void orderTypePindan(Order ne) {
        tv_order_source.setText(ne.getGoodsname());
        tv_order_source.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_password, 0, 0, 0);
    }

    /**
     * 收发货距离
     * TODO 2.5改版。2016/1/28又要求去掉
     *
     * @param ne
     */
    public void displayDistanceTv(Order ne) {
        DecimalFormat df = new DecimalFormat("######0.00");
        String sendDistance = ne.getSenddistance();
        String receiveDistance = ne.getReceivedistance();
        String deliverydistance = ne.getDeliverydistance();
        int datatype = ne.getDatatype();

        double d1 = 0.0d;
        double d2 = 0.0d;
        double d3 = 0.0d;
        if (!TextUtils.isEmpty(sendDistance)) {
            d1 = StringUtil.toDouble(sendDistance.toUpperCase().replace("千米", "").replace("KM", ""));
        }
        if (!TextUtils.isEmpty(receiveDistance)) {
            d2 = StringUtil.toDouble(receiveDistance.toUpperCase().replace("千米", "").replace("KM", ""));
        }
        if (!TextUtils.isEmpty(deliverydistance)) {
            d3 = StringUtil.toDouble(deliverydistance.toUpperCase().replace("千米", "").replace("KM", ""));
        }
        sendDistance = df.format(d1) + "km";//  我-发(动态)
        receiveDistance = df.format(d2) + "km";//我-收(动态)
        deliverydistance = df.format(d3) + "km";//发-收（固定）

        StringBuilder sb = new StringBuilder();

        // 判断是否是预约件
        if (STATE_DELIVERING != mState && !TextUtils.isEmpty(ne.getClaimpickupdate())) {
            sb.append("预约:").append(ne.getClaimpickupdate());
            sb.append("  ");
        }

        if (datatype == Order.TYPE_EXPRESS) {
            //快件订单
            if (STATE_DELIVERING == mState) {
                sb.append("配送").append(deliverydistance);
            } else {
                sb.append("距您").append(sendDistance);
                sb.append("\\");
                sb.append("配送").append(deliverydistance);
            }
        } else if (datatype == Order.TYPE_PINDAN) {
            //拼单订单
            if (STATE_DELIVERING == mState) {
                sb.append("配送").append(deliverydistance);
            } else {
                sb.append("距您").append(sendDistance);
                sb.append("\\");
                sb.append("配送").append(deliverydistance);
            }
        } else if (datatype == Order.TYPE_SHOP) {
            //代购订单
            if (STATE_DELIVERING == mState) {
                sb.append("距您").append(receiveDistance);
            } else {
                //代购无购买地址
                if (TextUtils.isEmpty(ne.getSendaddress())) {
                    sb.append("距您").append(receiveDistance);
                    sb.append("\\");
                    sb.append("配送").append(deliverydistance);
                } else {
                    sb.append("距您").append(sendDistance);
                    sb.append("\\");
                    sb.append("配送").append(deliverydistance);
                }
            }
        } else if (datatype == Order.TYPE_CANSONG) {
            //餐送订单
            if (STATE_DELIVERING == mState) {
                sb.append("配送").append(deliverydistance);
            } else {
                sb.append("距您").append(sendDistance);
                sb.append("\\");
                sb.append("配送").append(deliverydistance);
            }
        }
        // tv_distance.setText(sb.toString());
    }

    /**
     * 收发货距离
     *
     * @param ne
     */
    public void displayDistance(Order ne) {
        String sendDistance = ne.getSenddistance();
        String receiveDistance = ne.getReceivedistance();
        String deliverydistance = ne.getDeliverydistance();
        int datatype = ne.getDatatype();

        /*double d1 = 0.0d;
        double d2 = 0.0d;
        double d3 = 0.0d;
         DecimalFormat df = new DecimalFormat("######0.00");
        if (!TextUtils.isEmpty(sendDistance)) {
            d1 = Tools.parseDouble(sendDistance.toUpperCase().replace("千米", "").replace("KM", ""));
        }
        if (!TextUtils.isEmpty(receiveDistance)) {
            d2 = Tools.parseDouble(receiveDistance.toUpperCase().replace("千米", "").replace("KM", ""));
        }
        if (!TextUtils.isEmpty(deliverydistance)) {
            d3 = Tools.parseDouble(deliverydistance.toUpperCase().replace("千米", "").replace("KM", ""));
        }
        sendDistance = df.format(d1) + "km";//  我-发(动态)
        receiveDistance = df.format(d2) + "km";//我-收(动态)
        deliverydistance = df.format(d3) + "km";//发-收（固定）*/

        ll_distance.setVisibility(VISIBLE);
        if (datatype == Order.TYPE_EXPRESS) {
            //快件订单
            if (STATE_DELIVERING == mState) {
                tv_receive_distance.setText(receiveDistance);
                iv_fa.setVisibility(GONE);
                ll_send_distance.setVisibility(GONE);
            } else {
                tv_send_distance.setText(sendDistance);
                tv_receive_distance.setText(deliverydistance);
                iv_fa.setVisibility(VISIBLE);
                ll_send_distance.setVisibility(VISIBLE);
            }
        } else if (datatype == Order.TYPE_PINDAN) {
            //拼单订单
            if (STATE_DELIVERING == mState) {
                tv_receive_distance.setText(deliverydistance);
                iv_fa.setVisibility(GONE);
                ll_send_distance.setVisibility(GONE);
            } else {
                tv_send_distance.setText(sendDistance);
                tv_receive_distance.setText(deliverydistance);
                iv_fa.setVisibility(VISIBLE);
                ll_send_distance.setVisibility(VISIBLE);
            }
        } else if (datatype == Order.TYPE_SHOP) {
            //代购订单
            if (STATE_DELIVERING == mState) {
                tv_receive_distance.setText(receiveDistance);
                iv_fa.setVisibility(GONE);
                ll_send_distance.setVisibility(GONE);
            } else {
                //代购无购买地址
                if (TextUtils.isEmpty(ne.getSendaddress())) {
                    tv_receive_distance.setText(receiveDistance);
                    iv_fa.setVisibility(GONE);
                    ll_send_distance.setVisibility(GONE);
                } else {
                    tv_send_distance.setText(sendDistance);
                    tv_receive_distance.setText(deliverydistance);
                    iv_fa.setVisibility(VISIBLE);
                    ll_send_distance.setVisibility(VISIBLE);
                }
            }
        } else if (datatype == Order.TYPE_CANSONG) {
            ll_distance.setVisibility(GONE);
            //餐送订单
            if (STATE_DELIVERING == mState) {
                tv_receive_distance.setText(receiveDistance);
                iv_fa.setVisibility(GONE);
                ll_send_distance.setVisibility(GONE);
            } else {
                tv_send_distance.setText(sendDistance);
                tv_receive_distance.setText(deliverydistance);
                iv_fa.setVisibility(VISIBLE);
                ll_send_distance.setVisibility(VISIBLE);
            }
        }

        if (TextUtils.isEmpty(ne.getReceivedistance()) && TextUtils.isEmpty(ne.getSenddistance())) {
            ll_distance.setVisibility(GONE);
        }
    }

    /**
     * 收发货地址
     *
     * @param ne
     */
    private void displayAddress(Order ne) {
        if (ne.getDatatype() == Order.TYPE_SHOP) {
            //代购订单
            tv_start.setText("买：");
        } else {
            tv_start.setText("起：");
        }
        ///////////////////发货地址///////////////////////////
        if (!TextUtils.isEmpty(ne.getSendaddress())) {
            tv_send_address.setVisibility(VISIBLE);
            tv_send_address.setText(ne.getSendaddress());
        } else if (!TextUtils.isEmpty(ne.getVoiceurl())) {
            tv_send_address.setVisibility(GONE);
        } else {
            tv_send_address.setVisibility(VISIBLE);
            tv_send_address.setText(ne.getGoodsname());
        }
        ///////////////////////收货地址//////////////////////////
        if (ne.getDatatype() == Order.TYPE_PINDAN) {
            //拼单地址
            fl_receive_address.setVisibility(View.GONE);
            ll_receive_address.setVisibility(View.VISIBLE);
            List<String> packsreceiveaddress = ne.getPacksreceiveaddress();
            if (packsreceiveaddress != null && packsreceiveaddress.size() > 0) {
                ll_receive_address.removeAllViews();
                for (int i = 0; i < packsreceiveaddress.size(); i++) {

                }
            }
        } else {
            fl_receive_address.setVisibility(View.VISIBLE);
            ll_receive_address.setVisibility(View.GONE);
            if (TextUtils.isEmpty(ne.getReceiveaddress())) {
                tv_receive_address.setVisibility(GONE);
            } else {
                tv_receive_address.setVisibility(VISIBLE);
                tv_receive_address.setText(ne.getReceiveaddress());
            }
        }
    }

    /**
     * 重要说明
     *
     * @param ne
     */
    private void displayImportInfo(Order ne) {
        StringBuilder sbMessage = new StringBuilder();
       /* // 是否需要驾车
        if (ne.isneedcar()) {
            sbMessage.append("驾车；");
        }
        // 判断是否是预约件
        if (!TextUtils.isEmpty(ne.getClaimpickupdate())) {
            sbMessage.append("预约时间" + ne.getClaimpickupdate() + "；");
        }*/

        if (!TextUtils.isEmpty(ne.getOther())) {
            sbMessage.append(ne.getOther());
        }

        if (sbMessage.length() > 0) {
            sbMessage.insert(0, "重要说明：  ");
            tvImportantText.setText(sbMessage.toString());
            tvImportantText.setVisibility(View.VISIBLE);
            ll_important.setVisibility(View.VISIBLE);
        } else {
            tvImportantText.setVisibility(GONE);
        }

        if (!TextUtils.isEmpty(ne.getVoiceurl())) {
            //代购没有购买地址，语音重复显示问题，代购只有在有购买地址的情况下，才会显示语音
            //餐送没有收货地址，语音不在重要说明里面展示
            if (ne.getDatatype() == Order.TYPE_SHOP || ne.getDatatype() == Order.TYPE_CANSONG) {
                if (TextUtils.isEmpty(ne.getSendaddress()) || TextUtils.isEmpty(ne.getReceiveaddress())) {
                    ll_important_voice.setVisibility(View.GONE);
                    if (sbMessage.length() == 0) {
                        ll_important.setVisibility(View.GONE);
                    }
                    return;
                }
            }

            ll_important_voice.setVisibility(VISIBLE);
            ll_important.setVisibility(View.VISIBLE);
        } else {
            ll_important_voice.setVisibility(GONE);
        }

        if (sbMessage.length() > 0) {
            iv_important_icon.setVisibility(GONE);
        } else {
            iv_important_icon.setVisibility(VISIBLE);
        }

        if (sbMessage.length() == 0 && TextUtils.isEmpty(ne.getVoiceurl())) {
            ll_important.setVisibility(View.GONE);
        }
    }

    /**
     * 列表按钮
     *
     * @param ne
     */
    private void displayButton(Order ne) {
        if (STATE_FIGHT == mState) {
            if (ne.isDesignated()) {
                //指定单
                btnSubmit.setVisibility(View.GONE);
                llDesignated.setVisibility(View.VISIBLE);

                btnAccept.setText(getIscpButtonName(ne.getIscp()));

                btnAccept.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
                btnCancel.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
            } else {
                btnSubmit.setVisibility(View.VISIBLE);
                llDesignated.setVisibility(View.GONE);

                if (ne.getCountdown() <= ne.getMaxCountdown() && ne.getCountdown() >= 1) {
                    btnSubmit.setText(ne.getCountdown() + "秒后确认接单");
                } else if (ne.getMaxCountdown() > 0 && ne.getCountdown() == 0) {
                    btnSubmit.setText("确认接单");
                } else {
                    btnSubmit.setText(getIscpButtonName(ne.getIscp()));
                }
            }
        } else if (STATE_WAITTING == mState) {
            llDesignated.setVisibility(View.GONE);
            if (ne.getDatatype() == Order.TYPE_EXPRESS) {
                if ("12".equalsIgnoreCase(ne.getStatus())) {
                    btnSubmit.setText("信用授权");
                } else {
                    btnSubmit.setText("确认取货(剩余时间" + parseSecToHour(ne.getResidualtime()) + ")");
                }
                btnSubmit.setVisibility(View.VISIBLE);
            } else if (ne.getDatatype() == Order.TYPE_SHOP) {
                if (Order.STATE_SHOP_PENDING_GRANT_PERMISSION.equals(ne.getStatus())) {
                    btnSubmit.setText("信用授权");
                } else {
                    btnSubmit.setText("已购买商品(剩余时间" + parseSecToHour(ne.getResidualtime()) + ")");
                }
                btnSubmit.setVisibility(View.VISIBLE);
            } else if (ne.getDatatype() == Order.TYPE_PINDAN) {
                if ("12".equalsIgnoreCase(ne.getStatus())) {
                    btnSubmit.setText("信用授权");
                    btnSubmit.setVisibility(View.VISIBLE);
                } else {
                    btnSubmit.setVisibility(View.GONE);
                }
            }
        } else if (STATE_DELIVERING == mState) {
            llDesignated.setVisibility(View.GONE);
            switch (ne.getDatatype()) {
                case Order.TYPE_EXPRESS://帮送
                    if (ne.isReached()) {
                        //已签收
                        if ("0".equals(ne.getIspay())) {
                            //未支付
                            btnSubmit.setText("扫码签收(剩余时间" + parseSecToHour(ne.getResidualtime()) + ")");
                            btnSubmit.setVisibility(View.VISIBLE);
                        } else {
                            //已支付
                            btnSubmit.setText("等待签收(剩余时间" + parseSecToHour(ne.getResidualtime()) + ")");
                            btnSubmit.setVisibility(View.VISIBLE);
                        }
                    } else {
                        //未签收
                        if ("0".equals(ne.getIspay())) {
                            //未支付
                            btnSubmit.setText("扫码签收(剩余时间" + parseSecToHour(ne.getResidualtime()) + ")");
                            btnSubmit.setVisibility(View.VISIBLE);
                        } else {
                            //已支付
                            btnSubmit.setText("请求签收(剩余时间" + parseSecToHour(ne.getResidualtime()) + ")");
                            btnSubmit.setVisibility(View.VISIBLE);
                        }

                    }
                    break;
                case Order.TYPE_SHOP://帮买
                    if (ne.isReached()) {
                        //已签收
                        btnSubmit.setVisibility(View.GONE);
                    } else {
                        //未签收
                        btnSubmit.setText("请求签收(剩余时间" + parseSecToHour(ne.getResidualtime()) + ")");
                        btnSubmit.setVisibility(View.VISIBLE);
                    }
                    break;
                case Order.TYPE_CANSONG://餐送
                    if (ne.isReached()) {
                        btnSubmit.setText("等待签收(剩余时间" + parseSecToHour(ne.getResidualtime()) + ")");
                        btnSubmit.setVisibility(View.VISIBLE);
                    } else {
                        btnSubmit.setText("请求签收(剩余时间" + parseSecToHour(ne.getResidualtime()) + ")");
                        btnSubmit.setVisibility(View.VISIBLE);
                    }
                    break;
                case Order.TYPE_PINDAN://拼单
                    btnSubmit.setVisibility(View.GONE);
                    break;
            }
        }
        btnSubmit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
            }
        });
    }


    private String getIscpButtonName(String iscp) {
        // 0 普通接单 1有足够授信额度 2没有足够授信额度,银行卡接件
        String name;
        if ("1".equalsIgnoreCase(iscp)) {
            name = "信用接单";
        } else if ("2".equalsIgnoreCase(iscp)) {
            name = "银行卡接单";
        } else if ("3".equalsIgnoreCase(iscp)) {
            name = "余额接单";
        } else {
            name = "接单";
        }
        return name;
    }

    private String parseSecToHour(int residualtime) {
        String orderTime;
        int sec = residualtime;
        int hour = sec / 3600;
        int minute = (sec % 3600) / 60;
        if (hour > 0) {
            orderTime = hour + "小时" + minute + "分";
        } else if (minute > 0) {
            orderTime = minute + "分";
        } else if (hour == 0 && minute == 0) {
            orderTime = "0分";
        } else {
            orderTime = "0分";
        }

        return orderTime;
    }

    private String parseDeliverTime(String time) {
        if (TextUtils.isEmpty(time)) {
            return "";
        }
        StringBuilder orderTime = new StringBuilder();
        int sec = Integer.parseInt(time);
        int hour = sec / 3600;
        int minute = (sec % 3600) / 60;

        if (hour > 0) {
            orderTime.append(hour + "小时");
        }

        if (minute > 0) {
            orderTime.append(minute + "分");
        }

        if (orderTime.length() == 0) {
            orderTime.append("0分");
        }
        return orderTime.toString();
    }

    private void imageReplaceText(TextView mSubjectDetailView) {

    }




}
