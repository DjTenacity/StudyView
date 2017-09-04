//package com.gdj.myview.goodthinking;
//
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.graphics.Paint;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.orhanobut.dialogplus.DialogPlus;
//import com.orhanobut.dialogplus.OnItemClickListener;
//import com.tencent.mm.opensdk.modelpay.PayReq;
//import com.tencent.mm.opensdk.openapi.IWXAPI;
//import com.tencent.mm.opensdk.openapi.WXAPIFactory;
//import com.vipxfx.android.dumbo.R;
//import com.vipxfx.android.dumbo.dao.DatabaseManger;
//import com.vipxfx.android.dumbo.entity.AliOrder;
//import com.vipxfx.android.dumbo.entity.AliOrderItem;
//import com.vipxfx.android.dumbo.entity.Card;
//import com.vipxfx.android.dumbo.entity.Coupon;
//import com.vipxfx.android.dumbo.entity.PayData;
//import com.vipxfx.android.dumbo.entity.PaySeats;
//import com.vipxfx.android.dumbo.entity.PaySeatsDetail;
//import com.vipxfx.android.dumbo.entity.Point;
//import com.vipxfx.android.dumbo.entity.Show;
//import com.vipxfx.android.dumbo.entity.ShowTheater;
//import com.vipxfx.android.dumbo.entity.WxPayData;
//import com.vipxfx.android.dumbo.plugin.pay.alipay.AliPayAPI;
//import com.vipxfx.android.dumbo.plugin.pay.alipay.AliPayRequest;
//import com.vipxfx.android.dumbo.plugin.pay.wxpay.WxEntryData;
//import com.vipxfx.android.dumbo.service.ShowService;
//import com.vipxfx.android.dumbo.ui.view.PayTypeView;
//import com.vipxfx.android.dumbo.util.Constant;
//import com.vipxfx.android.dumbo.util.MathUtils;
//import com.vipxfx.android.dumbo.util.ProgressDialogUtils;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import cn.iwgang.countdownview.CountdownView;
//import cn.zhimadi.android.common.lib.App;
//import cn.zhimadi.android.common.lib.entity.ResponseData;
//import cn.zhimadi.android.common.lib.http.MySubscriber;
//import cn.zhimadi.android.common.lib.ui.widget.RoundedCornersTransformation;
//import cn.zhimadi.android.common.lib.util.DialogUtils;
//import cn.zhimadi.android.common.lib.util.DisplayUtils;
//import cn.zhimadi.android.common.lib.util.GlideUtils;
//import cn.zhimadi.android.common.lib.util.GsonUtils;
//import cn.zhimadi.android.common.lib.util.ListUtils;
//import cn.zhimadi.android.common.lib.util.StringUtils;
//import cn.zhimadi.android.common.lib.util.ToastUtils;
//import io.reactivex.annotations.NonNull;
//import io.reactivex.functions.Consumer;
//
//import static com.vipxfx.android.dumbo.util.Constant.CONTENT_TYPE_SHOW;
//import static com.vipxfx.android.dumbo.util.Constant.INTENT_PLAY_ID;
//import static com.vipxfx.android.dumbo.util.Constant.INTENT_THEATRE_LETF_TIME;
//import static com.vipxfx.android.dumbo.util.Constant.INTENT_TIME_ID;
//
///**
// * Comment:  提交买座订单   对两个集合的操作很好
// *
// * @author : dianjiegao / gaodianjie@zhimadi.cn
// * @version : 1.0
// * @date : 2017-03-17
// */
//public class ShowSeatBuyActivity extends BaseToolbarActivity implements AliPayRequest.OnAliPayListener {
//
//    static final int TYPE_CARD = 1;
//    static final int TYPE_COUPON = 2;
//    static final int TYPE_TIME_CARD = 3;
//
//    private ViewGroup llSeatBuy;
//    private TextView tvOperation, tvOldSpace, tvNowSpace, tvSeatAddress;
//    private TextView tv_seat_coupon, tvSeatCard, tvSeatTimeCard;
//    private CheckBox cb_use_card, cb_has_read;
//    private Map<Integer, TextView> mapCb, mapNewCb;
//
//    private CountdownView count_down_view;
//
//    private String time_id;
//    /**
//     * 观演券列表
//     */
//    private List<Coupon> couponList;
//    /**
//     * 阳光卡列表
//     */
//    private List<Card> cardList, cardTimeList;
//    private List<Point> pointsSeat;
//    private TextView tv_paint_size, tv_seats_price, tv_real_pay;
//    private PaySeats paySeats;
//    /**
//     * 优惠
//     */
//    private float couponUse = 0.00f;
//    private String play_id, wxOrderId;
//    private TextView tv_not_enough;
//    private long lefttime;
//    private ShowTheater showTheater;
//    private Button btn_buy_now;
//    //默认选择
//    private List<String> cardDefaultSeatIdList = new ArrayList<String>();
//
//    //选用的观演券
//    Coupon chosedCoupon = null;
//    //选用的阳光卡
//    Card chosedCard = null;
//    //选用的阳光E卡
//    Card chosedTimeCard = null;
//
//    //阳光卡选中的座位
//    Set<String> cardSelectedIds = new HashSet<>();
//    //阳光E卡选中的座位
//    Set<String> timeCardSelectedIds = new HashSet<>();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_show_seat_buy);
//        setToolBarTitle("确认订单");
//
//        paySeats = new PaySeats();
//        Bundle bundle = getIntent().getExtras();
//
//        lefttime = bundle.getLong(INTENT_THEATRE_LETF_TIME, 900000);
//
//        couponList = (List<Coupon>) bundle.getSerializable("seatCoupon");
//
//        cardList = (List<Card>) bundle.getSerializable("seatCard");
//        cardTimeList = (List<Card>) bundle.getSerializable("seatTimeCard");
//
//        cardDefaultSeatIdList.addAll((List<String>) bundle.getSerializable("cardSeatList"));
//        pointsSeat = (List<Point>) bundle.getSerializable("pointsSeat");
//
//        time_id = bundle.getString(INTENT_TIME_ID);
//        play_id = bundle.getString(INTENT_PLAY_ID);
//
//        ininview();
//        getCardCoupon();
//        initData();
//    }
//
//    private void getCardCoupon() {
//        tv_paint_size.setText(pointsSeat.size() + "张");
//        paySeats.setTime_id(time_id);
//
//        if (ListUtils.isEmpty(couponList)) {
//            tv_seat_coupon.setText(getString(R.string.str_no_coupon));
//        }
//        if (ListUtils.isEmpty(cardList)) {
//            tvSeatCard.setText(getString(R.string.str_no_card));
//        }
//        if (ListUtils.isEmpty(cardTimeList)) {
//            tvSeatTimeCard.setText(getString(R.string.str_no_card_time));
//        }
//    }
//
//    private void initData() {
//
//        ImageView ivDetailImage = (ImageView) findViewById(R.id.iv_detail_image);
//        TextView tv_detail_describe = (TextView) findViewById(R.id.tv_detail_describe);
//        TextView tv_detail_address = (TextView) findViewById(R.id.tv_detail_address);
//        TextView tvDetailTitle = (TextView) findViewById(R.id.tv_detail_title);
//
//        showTheater = DatabaseManger.getSession().getShowTheaterDao().load(time_id);
//        Show play_info = DatabaseManger.getSession().getShowDao().load(play_id);
//
//        //网络图片设置圆角
//        GlideUtils.getInstance().loadTransformImage(play_info.getPicurl(), ivDetailImage,
//                new RoundedCornersTransformation(App.context, DisplayUtils.dp2px(8), 0, RoundedCornersTransformation.CornerType.ALL));
//        tv_detail_address.setText(showTheater.getAddress());
//        tv_detail_describe.setText(showTheater.getPlay_time());
//        tvDetailTitle.setText(play_info.getPlay_name());
//
//        mapCb = new HashMap<Integer, TextView>();
//        mapNewCb = new HashMap<Integer, TextView>();
//
//        seatSeat();
//    }
//
//    //总计
//    float allPrices = 0.00f;
//    //需补差价
//    float allCardPrices = 0.00f;
//    //根据isUseM判断是否使用了阳光卡，显示checkbox,根据list集合判断使用阳光卡的座位,num为可使用卡的座位的个数
//    int num = 2;
//
//    //提交给服务端的座位信息
//    List<PaySeatsDetail> seatsSendList = new ArrayList<PaySeatsDetail>();
//
//    public void seatSeat() {
//
//        if (chosedCard != null || chosedTimeCard != null) {
//
//            if (chosedCard != null && chosedTimeCard != null) {
//                num = 4;
//            } else {
//                num = 2;
//            }
//            tvOperation.setVisibility(View.VISIBLE);
//        } else {
//            tvOperation.setVisibility(View.GONE);
//        }
//
//
//        if (chosedCoupon == null)
//            tv_seat_coupon.setText(getResources().getString(R.string.str_choose_coupon));
//
//        mapCb.clear();
//        mapNewCb.clear();
//        llSeatBuy.removeAllViews();
//
//        //开始根据选择的座位循环
//        for (int i = 0; i < pointsSeat.size(); i++) {
//
//            final int position = i;
//            final String seat_id = pointsSeat.get(position).ps_id + "";
//
//            View ItemSeat = LayoutInflater.from(this).inflate(R.layout.layout_seat_buy_item_des, null);
//            tvSeatAddress = (TextView) ItemSeat.findViewById(R.id.tv_seat_address);
//            tvNowSpace = (TextView) ItemSeat.findViewById(R.id.tv_now_space);
//            tvOldSpace = (TextView) ItemSeat.findViewById(R.id.tv_old_space);
//            tvOldSpace.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
//            cb_use_card = (CheckBox) ItemSeat.findViewById(R.id.cb_use_card);
//
//            mapCb.put(i, tvOldSpace); //使用mapCb来保存textview，防止错乱
//            mapNewCb.put(i, tvNowSpace);
//
//            tvSeatAddress.setText(pointsSeat.get(i).columnRow);
//            tvNowSpace.setText("￥" + pointsSeat.get(i).price);
//
//            //是否使用 卡
//            if (chosedCard != null || chosedTimeCard != null) {
//                ItemSeat.findViewById(R.id.view_padding_12).setVisibility(View.VISIBLE);
//                cb_use_card.setVisibility(View.VISIBLE);
//                if (cardSelectedIds.contains(seat_id) || timeCardSelectedIds.contains(seat_id)) {
//                    cb_use_card.setChecked(true);
//                    tvOldSpace.setVisibility(View.VISIBLE);
//                    tvOldSpace.setText("￥" + pointsSeat.get(position).price);
//                    tvNowSpace.setText("￥" + pointsSeat.get(position).card_price);
//                } else {
//                    cb_use_card.setChecked(false);
//                }
//            } else {
//                ItemSeat.findViewById(R.id.view_padding_12).setVisibility(View.GONE);
//                cb_use_card.setVisibility(View.GONE);
//                tvOldSpace.setVisibility(View.GONE);
//            }
//
//            //座位的单选
//            cb_use_card.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                    tvOldSpace = mapCb.get(position);
//                    tvNowSpace = mapNewCb.get(position);
//                    if (isChecked) {
//                        //是否到达最多数量
//                        if (cardSelectedIds.size() + timeCardSelectedIds.size() >= num) {
//                            ToastUtils.normal("最多" + num + "个座位使用阳光卡/阳光E卡").show();
//                            buttonView.setChecked(false);
//                        } else {
//                            onSeatCheck(seat_id);
//
//                            if (chosedCard != null || chosedTimeCard != null) {
//                                if (cardSelectedIds.contains(seat_id) || timeCardSelectedIds.contains(seat_id)) {
//                                    tvOldSpace.setVisibility(View.VISIBLE);
//                                    tvOldSpace.setText("￥" + pointsSeat.get(position).price);
//                                    tvNowSpace.setText("￥" + pointsSeat.get(position).card_price);
//                                }
//                            }
//                            totalPrice();
//                        }
//                    } else {
//                        //取消
//                        unSeatCheck(seat_id);
//
//                        tvOldSpace.setVisibility(View.GONE);
//                        tvNowSpace.setText("￥" + pointsSeat.get(position).price + "");
//                        totalPrice();
//                    }
//                }
//            });
//            llSeatBuy.addView(ItemSeat, new LinearLayout.LayoutParams(-2, -2));
//        }
//
//        totalPrice();
//    }
//
//    //根据所有座位计算价格，并编辑向后台发送的座位的数据的调整
//    public float totalPrice() {
//        allPrices = 0.00f;
//        allCardPrices = 0.00f;
//
//        if (seatsSendList.size() > 0) seatsSendList.clear();
//
////       1.遍历两个list，计算总费用；
////       2.判断是否用使用观演券，使用再减少优惠金额
////          遍历全部座位
//        for (int i = 0; i < pointsSeat.size(); i++) {
//
//            PaySeatsDetail paySeatsDetail = new PaySeatsDetail();
//
//            //必须
//            paySeatsDetail.setSeat_name(pointsSeat.get(i).columnRow);
//            paySeatsDetail.setPs_id(pointsSeat.get(i).ps_id);
//
//            //默认
//            float seatPrice = pointsSeat.get(i).price;
//            paySeatsDetail.setIs_usecard(0);//默认为0，即没有选用阳光卡
//            if (chosedCard != null || chosedTimeCard != null) {
//                if (cardSelectedIds.contains(pointsSeat.get(i).ps_id + "") || timeCardSelectedIds.contains(pointsSeat.get(i).ps_id + "")) {
//                    paySeatsDetail.setIs_usecard(1);// 用了卡
//                    //allCardPrices 需补差价
//                    allCardPrices = allCardPrices + pointsSeat.get(i).card_price;
//                    seatPrice = pointsSeat.get(i).card_price;
//                }
//            }
//
//            allPrices = allPrices + seatPrice;
//            seatsSendList.add(paySeatsDetail);
//            if (i == pointsSeat.size() - 1) {
//                //循环结束
//                totalCoupon();
//            }
//        }
//        return 0.0f;
//    }
//
//    //最后对总价格和向后台发送数据的操作，
//    private void totalCoupon() {
//
//        if (chosedCoupon != null && couponUse != 0) {
//
//            if (allPrices >= Float.parseFloat(chosedCoupon.getUsed_amount())) {
//                //减去优惠值了
//                allPrices = allPrices - couponUse;
//                //  ifUseCoupon = true;
//            } else {
//                ToastUtils.normal(getResources().getString(R.string.str_coupon_cannot)).show();
//                //ifUseCoupon = false;
//                chosedCoupon = null;
//                tv_seat_coupon.setText(getResources().getString(R.string.str_choose_coupon));
//            }
//        }
//        allPrices = MathUtils.getFloat(allPrices);
//        //需补差价  allCardPrices = Math.round(allCardPrices * 100) / 100;
//        allCardPrices = MathUtils.getFloat(allCardPrices);
//
//        tv_not_enough.setText("￥" + allCardPrices);
//        tv_seats_price.setText("￥" + allPrices);//总计
//        tv_real_pay.setText("￥" + allPrices);//总计实付
//
//        paySeats.setSeats(seatsSendList);
//        if (chosedCoupon != null) {//&& ifUseCoupon
//            paySeats.setCoupon_sn(chosedCoupon.getCoupon_sn());
//        }
//        if (null != chosedCard) {
//            paySeats.setCard_sn(chosedCard.getCard_sn());
//        }
//        if (null != chosedTimeCard) {
//            paySeats.setTimescard_sn(chosedTimeCard.getCard_sn());
//        }
//
//    }
//
//
//    private void ininview() {
//        btn_buy_now = (Button) findViewById(R.id.btn_buy_now);
//        //操作 两个字的视图
//        tvOperation = (TextView) findViewById(R.id.tv_operation);
//
//        llSeatBuy = (ViewGroup) findViewById(R.id.ll_seat_buy);
////座位数
//        tv_paint_size = (TextView) findViewById(R.id.tv_paint_size);
////总计
//        tv_seats_price = (TextView) findViewById(R.id.tv_seats_price);
////实付
//        tv_real_pay = (TextView) findViewById(R.id.tv_real_pay);
//
//        view_pay_type = (PayTypeView) findViewById(R.id.view_pay_type);
//        //选择观演券
//        tv_seat_coupon = (TextView) findViewById(R.id.tv_seat_coupon);
//
//        //选择阳光卡
//        tvSeatCard = (TextView) findViewById(R.id.tv_seat_card);
//
//        tvSeatTimeCard = (TextView) findViewById(R.id.tv_seat_time_card);
//        //需补差价
//        tv_not_enough = (TextView) findViewById(R.id.tv_not_enough);
//
//        cb_has_read = (CheckBox) findViewById(R.id.cb_has_read);
//
//        count_down_view = (CountdownView) findViewById(R.id.count_down_view);
//
//        // LinearLayout ll_seat_pay = (LinearLayout) ;
//
//        if (lefttime != 900000) {
//            lefttime = lefttime * 1000;
//        }
//        count_down_view.start(lefttime);
//
//        count_down_view.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
//            @Override
//            public void onEnd(CountdownView countdownView) {
//                count_down_view.setVisibility(View.GONE);
//                findViewById(R.id.ll_seat_pay).setVisibility(View.GONE);
//
//            }
//        });
//
//        cb_has_read.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    btn_buy_now.setBackground(getResources().getDrawable(R.drawable.shape_magenta_corner));
//                } else {
//                    btn_buy_now.setBackground(getResources().getDrawable(R.drawable.shape_gray_border_dddddd));
//                }
//            }
//        });
//    }
//
//    // boolean has_read = false;
//    PayTypeView view_pay_type;
//    String payType;
//
//    String dialogStr0 = "您当前订单同时使用了“阳光卡”和“阳光E卡”，可选4个座位，如继续操作阳光卡及阳光E卡次数将扣除且无法恢复，请确认是否继续？";
//    String dialogStr1 = "您当前订单使用了“阳光卡”，可选2个座位，如继续操作阳光卡次数将扣除且无法恢复，请确认是否继续？";
//    String dialogStr2 = "您当前订单使用了“阳光E卡”，可选2个座位，如继续操作阳光E卡次数将扣除且无法恢复，请确认是否继续？";
//
//    String dialogStr = dialogStr1;
//
//    public String submitCheck() {
//        if (null != chosedCard && null != chosedTimeCard && (cardSelectedIds.size() + timeCardSelectedIds.size()) < 4) {
//            return dialogStr0;
//        }
//
//        if (null != chosedCard && cardSelectedIds.size() < 2) {
//            return dialogStr1;
//        }
//
//        if (null != chosedTimeCard && timeCardSelectedIds.size() < 2) {
//            return dialogStr2;
//        }
//
//        return "";
//    }
//
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btn_buy_now:
//                if (!cb_has_read.isChecked()) {
//                    ToastUtils.error(getString(R.string.str_buy_need_read)).show();
//                    return;
//                }
//
//                if (chosedTimeCard != null || chosedCard != null) {
//
//                    dialogStr = submitCheck();
//                    if (StringUtils.isNotBlank(dialogStr)) {
//                        DialogUtils.dialogBuilder(this, null, dialogStr)
//                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                    }
//                                })
//                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        payShowNow();
//                                    }
//                                })
//                                .create()
//                                .show();
//                    } else {
//                        payShowNow();
//                    }
//
//                } else {
//                    payShowNow();
//                }
//                break;
//            case R.id.tv_seat_coupon:
//                //把可供选择的观演券和阳光卡，搞到集合里面，然后根据选择的坐标获得角标，提取数据
//                if (!ListUtils.isEmpty(couponList)) {
//                    adapterCoupon = new DialogsAdapter(this, R.layout.item_card_list, TYPE_COUPON);
//                    showDialogs(TYPE_COUPON, adapterCoupon);
//                }
//                break;
//            case R.id.tv_seat_card:
//                if (!ListUtils.isEmpty(cardList)) {
//                    adapterCard = new DialogsAdapter(this, R.layout.item_card_list, TYPE_CARD);
//                    showDialogs(TYPE_CARD, adapterCard);
//                }
//                break;
//            case R.id.tv_seat_time_card:
//                if (!ListUtils.isEmpty(cardTimeList)) {
//                    adapterTimeCard = new DialogsAdapter(this, R.layout.item_card_list, TYPE_TIME_CARD);
//                    showDialogs(TYPE_TIME_CARD, adapterTimeCard);
//                }
//                break;
//
//            case R.id.tv_refund_tips:
//                WebActivity.start(this, getString(R.string.str_refund_tips), Constant.URL_REFUND, Constant.CONTENT_TYPE_OTHER);
//                break;
//
//        }
//    }
//
//    void payShowNow() {
//        //价格为0时支付PayType为0（ 多为使用阳光卡时）
//        if (allPrices == 0) {
//            view_pay_type.setPayType("0");
//        }
//        payType = view_pay_type.getPayType();
//
//        if (StringUtils.isNotBlank(payType)) {
//            paySeats.setPay_type(payType);
//
//            ProgressDialogUtils.showProgressDialog(this, "正在获取订单信息");
//
//            if (payType.equals(Constant.PAY_TYPE_ALIPAY) || payType.equals("0")) {//支付宝支付或者价格为0
//
//                ShowService.aliOrderSeat(paySeats).subscribe(new MySubscriber<ResponseData<PayData<String>>>(new Consumer<ResponseData<PayData<String>>>() {
//                    @Override
//                    public void accept(@NonNull ResponseData<PayData<String>> responseData) throws Exception {
//                        if (responseData.isSuccess()) {
//
//                            PayData<String> payData = responseData.getData();
//                            String pay_data = payData.getPay_data();
//
//                            Constant.PAY_TYPE_FININAL_PAY = 1;
//                            ProgressDialogUtils.dismiss();
//                            if (StringUtils.isNotBlank(pay_data)) {
//                                AliPayRequest aliPayReq = new AliPayRequest.Builder()
//                                        .with(ShowSeatBuyActivity.this)
//                                        .setSignedAliPayOrderInfo(pay_data)
//                                        .create()
//                                        .setOnAliPayListener(ShowSeatBuyActivity.this);//
//                                AliPayAPI.getInstance().sendPayReq(aliPayReq);
//                            } else {
//                                Intent intent = new Intent(ShowSeatBuyActivity.this, ResultTipActivity.class);
//                                intent.putExtra(Constant.INTENT_ORDER_ID, payData.getOrder_id());
//                                intent.putExtra(Constant.INTENT_OUT_TRADE_NO, payData.getPay_id());
//                                intent.putExtra(Constant.INTENT_TIP_TYPE, Constant.TIP_TYPE_PAY_SUCCESS);
//                                intent.putExtra(CONTENT_TYPE_SHOW, CONTENT_TYPE_SHOW);
//                                startActivity(intent);
//                                finish();
//                            }
//                        } else {
//                            ProgressDialogUtils.dismiss();
//                            ToastUtils.normal(responseData.getMsg()).show();
//                        }
//                    }
//                }, null));
//
//            } else if (payType.equals(Constant.PAY_TYPE_WXPAY)) {
//                ShowService.wxOrderSeat(paySeats).subscribe(new MySubscriber<ResponseData<PayData<WxPayData>>>(new Consumer<ResponseData<PayData<WxPayData>>>() {
//                    @Override
//                    public void accept(@NonNull ResponseData<PayData<WxPayData>> responseData) throws Exception {
//                        if (responseData.isSuccess()) {
//                            PayData<WxPayData> payData = responseData.getData();
//                            WxPayData wxPayData = payData.getPay_data();
//                            if (wxPayData != null) {
//                                ProgressDialogUtils.dismiss();
//                                Constant.PAY_TYPE_FININAL_PAY = 1;
//
//                                String wxPartnerid = wxPayData.getPrepayid();
//
//                                WxEntryData.setAppid(wxPayData.getAppid());
//                                WxEntryData.setPrepayid(wxPartnerid);
//                                WxEntryData.setType(CONTENT_TYPE_SHOW);
//                                IWXAPI mWXApi = WXAPIFactory.createWXAPI(ShowSeatBuyActivity.this, null);
//                                PayReq req = new PayReq();
//                                req.appId = wxPayData.getAppid();
//                                req.prepayId = wxPayData.getPrepayid();
//                                req.nonceStr = wxPayData.getNoncestr();
//                                req.sign = wxPayData.getSign();
//                                req.timeStamp = wxPayData.getTimestamp();
//                                req.partnerId = wxPayData.getPartnerid();
//                                req.packageValue = "Sign=WXPay";
//                                mWXApi.sendReq(req);
//                            }
//                        } else {
//                            ProgressDialogUtils.dismiss();
//                            ToastUtils.normal(responseData.getMsg()).show();
//                        }
//                    }
//                }, null));
//                //  final String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
//            }
//
//        } else {
//            ToastUtils.normal("请选择支付方式！").show();
//        }
//    }
//
//    /**
//     * 支付宝支付结果接口  start
//     */
//    @Override
//    public void onPaySuccess(String resultInfo) {
//        AliOrder aliOrder = GsonUtils.parseJSON(resultInfo, AliOrder.class);
//        AliOrderItem aliOrderItem = aliOrder.getAlipay_trade_app_pay_response();
//
//        Intent intent = new Intent(this, ResultTipActivity.class);
//        intent.putExtra(Constant.INTENT_OUT_TRADE_NO, aliOrderItem.getOut_trade_no());
//        intent.putExtra(Constant.INTENT_TIP_TYPE, Constant.TIP_TYPE_PAY_SUCCESS);
//        intent.putExtra(CONTENT_TYPE_SHOW, CONTENT_TYPE_SHOW);
//        startActivity(intent);
//        finish();
//    }
//
//    @Override
//    public void onPayFailure(String resultInfo) {
//
//    }
//
//    @Override
//    public void onPayConfirmimg(String resultInfo) {
//
//    }
//
//    @Override
//    public void onPayCheck(String status) {
//
//    }
//
//    /**
//     * 支付宝支付结果接口  end   1阳光卡 2观演券  3阳光E卡
//     */
//
//    private DialogPlus dialog;
//    private DialogsAdapter adapterCard, adapterCoupon, adapterTimeCard;
//
//    private void showDialogs(final int ifCard, DialogsAdapter adapter) {
//
//
//        View view = LayoutInflater.from(this).inflate(R.layout.view_type_select_dialog, null);
//        TextView tv = (TextView) view.findViewById(R.id.tv_title_dia);
//        TextView tv_card_dia = (TextView) view.findViewById(R.id.tv_card_dia);
//        if (ifCard == TYPE_CARD) {
//            tv.setText("选择阳光卡");
//            tv_card_dia.setVisibility(View.VISIBLE);
//        } else if (ifCard == TYPE_COUPON) {
//            tv.setText("选择观演券");
//            tv_card_dia.setVisibility(View.INVISIBLE);
//        } else if (ifCard == TYPE_TIME_CARD) {
//            tv.setText("选择阳光E卡");
//            tv_card_dia.setText("(阳光E卡一次各可抵消两张票,请选择两个座位)");
//            tv_card_dia.setVisibility(View.VISIBLE);
//        }
//
//        if (adapter == null) return;
//
//        dialog = DialogPlus.newDialog(this)
//                .setAdapter(adapter)
//                .setHeader(view)
//                .setGravity(Gravity.BOTTOM)
//                .setOnItemClickListener(new OnItemClickListener() {
//                    @Override
//                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
//                        Log.w("faile", "没有相应");
//                    }
//                })
//                .setExpanded(true)  // This will enable the expand feature, (similar to android L share dialog)
//                .create();
//        dialog.show();
//    }
//
//
//    class DialogsAdapter extends BaseAdapter {
//        View returnView;
//        Context context;
//        int resourceId;
//        int ifCardType = TYPE_CARD;
//
//        public DialogsAdapter(Context context, int resourceId, int ifCardType) {
//            this.context = context;
//            this.resourceId = resourceId;
//            this.ifCardType = ifCardType;
//        }
//
//
//        @Override
//        public int getCount() {
//            //如果是阳光E卡，就用阳光E卡
//            if (ifCardType == TYPE_TIME_CARD) {
//                return cardTimeList.size();
//            } else if (ifCardType == TYPE_COUPON) {
//                return couponList.size();
//            } else {
//                return cardList.size();
//            }
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return 0;
//        }
//
//        @Override
//        public View getView(final int position, View convertView, ViewGroup parent) {
//
//            returnView = LayoutInflater.from(context).inflate(resourceId, null);
//
//            RelativeLayout rl_list_buy = (RelativeLayout) returnView.findViewById(R.id.rl_list_buy);// 背景
////阳光卡
//            TextView tv_card_des = (TextView) returnView.findViewById(R.id.tv_card_des);//阳光卡描述
//            TextView tv_card_num = (TextView) returnView.findViewById(R.id.tv_card_num);//卡号，不能gone
//
////观演券
//            TextView tv_coupon_utils = (TextView) returnView.findViewById(R.id.tv_coupon_utils);//元
//            TextView tv_coupon_money = (TextView) returnView.findViewById(R.id.tv_coupon_money);//最低消费
////公共
//            final CheckBox cb_card_choose = (CheckBox) returnView.findViewById(R.id.cb_card_choose);
//
//            LinearLayout ll_crad_list = (LinearLayout) returnView.findViewById(R.id.ll_crad_list);// 背景
//
//            TextView tv_card_times = (TextView) returnView.findViewById(R.id.tv_card_times);//钱数或者次数
//
//            TextView tv_card_title = (TextView) returnView.findViewById(R.id.tv_card_title);//卡名或者观演券名
//
//            TextView tv_card_time = (TextView) returnView.findViewById(R.id.tv_card_time);//有效期
//
//            rl_list_buy.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (dialog != null) {
//                        dialog.dismiss();
//                        if (ifCardType == TYPE_CARD) {
//                            if (chosedCard == cardList.get(position)) {
//                                chosedCard = null;
//                                cb_card_choose.setChecked(false);
//                            } else {
//                                cb_card_choose.setChecked(true);
//                            }
//                        } else if (ifCardType == TYPE_COUPON) {
//                            if (chosedCoupon == couponList.get(position)) {
//                                chosedCoupon = null;
//                                cb_card_choose.setChecked(false);
//                            } else {
//                                chosedCoupon = couponList.get(position);
//                                cb_card_choose.setChecked(true);
//                            }
//                        } else {
//                            if (chosedTimeCard == cardTimeList.get(position)) {
//                                chosedTimeCard = null;
//                                cb_card_choose.setChecked(false);
//                            } else {
//                                chosedTimeCard = cardTimeList.get(position);
//                                cb_card_choose.setChecked(true);
//                            }
//                        }
//                    }
//                    notifyDataSetChanged();
//                }
//            });
//
//            if (ifCardType != TYPE_COUPON) {// 卡
//                ll_crad_list.setBackground(context.getResources().getDrawable(R.drawable.shape_blue_corner));
//                tv_card_des.setVisibility(View.VISIBLE);
//                tv_card_num.setVisibility(View.VISIBLE);
//                tv_coupon_utils.setVisibility(View.GONE);
//                tv_coupon_money.setVisibility(View.GONE);
//                //context.getResources().getString(R.string.str_validity_time) +
//                if (ifCardType == TYPE_CARD) {
//                    tv_card_times.setText(cardList.get(position).getLeft_times());
//                    tv_card_num.setText(context.getString(R.string.str_card_code) + cardList.get(position).getCard_sn());
//                    tv_card_title.setText(cardList.get(position).getCard_name());
//                    tv_card_time.setText("有效期至：" + cardList.get(position).getExpire_time());
//
//                    if (chosedCard == cardList.get(position)) {
//                        cb_card_choose.setChecked(true);
//                    } else {
//                        cb_card_choose.setChecked(false);
//                    }
//                } else {
//                    tv_card_times.setText(cardTimeList.get(position).getLeft_times());
//                    tv_card_num.setText(context.getString(R.string.str_card_code) + cardTimeList.get(position).getCard_sn());
//                    tv_card_title.setText(cardTimeList.get(position).getCard_name());
//                    tv_card_time.setText("有效期至：" + cardTimeList.get(position).getExpire_time());
//
//                    if (chosedTimeCard == cardTimeList.get(position)) {
//                        cb_card_choose.setChecked(true);
//                    } else {
//                        cb_card_choose.setChecked(false);
//                    }
//                }
//
//
//            } else {//观演券
//                ll_crad_list.setBackground(context.getResources().getDrawable(R.drawable.shape_orange_corner));
//                tv_card_des.setVisibility(View.GONE);
//                tv_card_num.setVisibility(View.INVISIBLE);
//                tv_coupon_utils.setVisibility(View.VISIBLE);
//                tv_coupon_money.setVisibility(View.VISIBLE);
//
//                tv_coupon_money.setText("满" + couponList.get(position).getUsed_amount() + "元使用");
//                tv_card_times.setText(couponList.get(position).getCoupon_amount());
//                tv_card_title.setText(couponList.get(position).getCoupon_name());
//                tv_card_time.setText("有效期至：" + couponList.get(position).getExpire_time());
//
//                if (chosedCoupon == couponList.get(position)) {
//                    cb_card_choose.setChecked(true);
//                } else {
//                    cb_card_choose.setChecked(false);
//                }
//            }
//
//
//            cb_card_choose.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    if (dialog != null) {
//                        dialog.dismiss();
//
//                        if (ifCardType == TYPE_CARD) {
//                            if (isChecked) {
//
//                                if (cardList.get(position).getIs_used().equals("0")) {
//                                    chosedCard = cardList.get(position);
//                                    tvSeatCard.setText("已选:" + chosedCard.getCard_name());//被选中的阳光卡
//                                } else {
//                                    buttonView.setChecked(false);
//                                    ToastUtils.normal("本场次已经使用过此阳光卡").show();
//                                    return;
//                                }
//                            } else {
//                                chosedCard = null;
//                                tvSeatCard.setText(context.getString(R.string.str_choose_card));
//                            }
//                            cardSelect(isChecked);
//                        }
//
//                        if (ifCardType == TYPE_TIME_CARD) {
//                            if (isChecked) {
//                                chosedTimeCard = cardTimeList.get(position);
//                                tvSeatTimeCard.setText("已选:" + chosedTimeCard.getCard_name());//被选中的阳光E卡
//                            } else {
//                                chosedTimeCard = null;
//                                tvSeatTimeCard.setText(context.getString(R.string.str_choose_card_time));
//                            }
//                            timeCardSelect(isChecked);
//                        }
//
//                        if (ifCardType == TYPE_COUPON) {//   观演券
//                            if (isChecked) {
//                                chosedCoupon = couponList.get(position);
//                                tv_seat_coupon.setText("已选:" + chosedCoupon.getCoupon_name());//被选中的观演券 coupon_amount
//                                couponUse = Float.parseFloat(couponList.get(position).getCoupon_amount());
//                            } else {// 不选
//                                couponUse = 0.00f;
//                                tv_seat_coupon.setText(context.getString(R.string.str_choose_coupon));
//                                chosedCoupon = null;
//                            }
//                        }
//
//                        notifyDataSetChanged();
//                    }
//                    seatSeat();
//                }
//            });
//            return returnView;
//        }
//    }
//
//
//    /**
//     * 阳光卡选择或切换
//     *
//     * @param selected the selected
//     * @author : mingweigao / 2017-07-20
//     */
//    public void cardSelect(boolean selected) {
//        if (selected) {//选择阳光卡或切换阳光卡
//            timeCardSelectedIds.removeAll(cardDefaultSeatIdList);//清空体验阳光卡中所有的默认勾选
//            for (String tempId : cardSelectedIds) {
//                if (!cardDefaultSeatIdList.contains(tempId)) {//如果不在默认勾选列表，添加到阳光E卡列表
//                    timeCardSelectedIds.add(tempId);
//                }
//            }
//            cardSelectedIds.clear();//清空列表
//            cardSelectedIds.addAll(cardDefaultSeatIdList);//添加所有默认列表
//        } else {//取消阳光卡
//            cardSelectedIds.clear();//清空列表
//        }
//    }
//
//
//    //            1.判断seatid是否默认勾选，如果是，对阳光E卡list执行remove，判断阳光卡list非空，遍历阳光卡list
////            2.判断阳光卡list里面的元素是否在defaltid里面，如果不在阳光卡列表remove，阳光E卡list add，
////            3.如果seatid不在默认勾选，判断阳光卡列表是否满，不在add进去，满了add到阳光E卡列表
//    public boolean onSeatCheck(String seatId) {
//        if (cardSelectedIds.size() < 2) {//默认优先添加到阳光卡
//            cardSelectedIds.add(seatId);
//            return true;
//        }
//        if (cardDefaultSeatIdList.contains(seatId)) {//否包含在默认勾选列表
//            timeCardSelectedIds.remove(seatId);
//            String temp = "";
//            for (String tempId : cardSelectedIds) {
//                if (!cardDefaultSeatIdList.contains(tempId)) {//判断是否包含在默认勾选列表
//                    temp = tempId;
//                    break;
//                }
//            }
//            if (StringUtils.isNotBlank(temp)) {
//                timeCardSelectedIds.add(temp);//如果不在默认勾选列表，添加到阳光E卡列表
//                cardSelectedIds.remove(temp);//阳光卡列表删除
//            }
//            cardSelectedIds.add(seatId);//添加到阳光卡列表
//            return true;
//        } else {//不在默认阳光卡列表
//            if (timeCardSelectedIds.size() < 2) {
//                timeCardSelectedIds.add(seatId);
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public boolean unSeatCheck(String seatId) {
//        cardSelectedIds.remove(seatId);
//        timeCardSelectedIds.remove(seatId);
//        return true;
//    }
//
//    /**
//     * 阳光E卡选择或取消
//     *
//     * @param selected the selected
//     * @author : mingweigao / 2017-07-20
//     */
//    public void timeCardSelect(boolean selected) {
//        if (!selected) {
//            timeCardSelectedIds.clear();
//        }
//    }
//
//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        setIntent(intent);
//    }
//
//}
