//package com.gdj.myview.goodthinking;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RatingBar;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.bumptech.glide.Glide;
//import com.ms.square.android.expandabletextview.ExpandableTextView;
//import com.vipxfx.android.dumbo.R;
//import com.vipxfx.android.dumbo.entity.Comment;
//import com.vipxfx.android.dumbo.entity.PlayDetail;
//import com.vipxfx.android.dumbo.entity.Show;
//import com.vipxfx.android.dumbo.ui.activity.ShowDetailActivity;
//import com.vipxfx.android.dumbo.ui.activity.ShowImageActivity;
//import com.vipxfx.android.dumbo.ui.activity.ShowImgDetailActivity;
//import com.vipxfx.android.dumbo.ui.activity.UserCommentActivity;
//import com.vipxfx.android.dumbo.util.HtmlUtils;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.List;
//
//import cn.zhimadi.android.common.lib.ui.widget.CropCircleTransformation;
//import cn.zhimadi.android.common.lib.ui.widget.RoundedCornersTransformation;
//import cn.zhimadi.android.common.lib.util.DisplayUtils;
//import cn.zhimadi.android.common.lib.util.GlideUtils;
//import cn.zhimadi.android.common.lib.util.ListUtils;
//import cn.zhimadi.android.common.lib.util.StringUtils;
//
//import static com.vipxfx.android.dumbo.util.Constant.INTENT_COMMENT_TYPE;
//import static com.vipxfx.android.dumbo.util.Constant.INTENT_IMG_DETAIL;
//import static com.vipxfx.android.dumbo.util.Constant.INTENT_IMG_POSITION;
//import static com.vipxfx.android.dumbo.util.Constant.INTENT_OBJECT_ID;
//import static com.vipxfx.android.dumbo.util.Constant.INTENT_PLAY_ID;
//
///**
// * Comment: 剧详情适配器         化繁为简
// *
// * @author : dianjiegao / gaodianjie@zhimadi.cn
// * @version : 1.0
// * @date : 2017-05-03
// */
//public class ShowDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//    public int currentType = 0;
//    private final static int TYPE_PLAYDETAIL_DES = 0;//描述
//    private final static int TYPE_PLAYDETAIL_SHOWIMAGE = 1;//剧照
//    private final static int TYPE_PLAYDETAIL_COMMENT = 2;//讨论
//    private final static int TYPE_PLAYDETAIL_COMMENDS = 3;//推荐
//
//    private Context mContext;
//    private PlayDetail playDetail;
//    private final LayoutInflater mLayoutInflater;
//
//    String play_id;
//
//    private LinearLayout.LayoutParams paramsWrap = new LinearLayout.LayoutParams(-1, -1);
//
//    public ShowDetailAdapter(Context mContext, PlayDetail playDetail, String play_id) {
//        this.mContext = mContext;
//        this.play_id = play_id;
//        this.playDetail = playDetail;
//        mLayoutInflater = LayoutInflater.from(mContext);
//    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if (viewType == TYPE_PLAYDETAIL_DES) {//描述
//            return new DesViewHolder(mLayoutInflater.inflate(R.layout.view_show_detail_des, null), mContext);
//
//        } else if (viewType == TYPE_PLAYDETAIL_SHOWIMAGE) {// 剧照
//            return new ShowImageViewHolder(mLayoutInflater.inflate(R.layout.view_show_detail_image, null), mContext);
//
//        } else if (viewType == TYPE_PLAYDETAIL_COMMENT) {//讨论
//            return new CommentViewHolder(mLayoutInflater.inflate(R.layout.view_show_detail_comment, null), mContext);
//
//        } else if (viewType == TYPE_PLAYDETAIL_COMMENDS) {//推荐
//            return new CommendsViewHolder(mLayoutInflater.inflate(R.layout.view_show_detail_commend, null), mContext);
//        }
//        return null;
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        switch (getItemViewType(position)) {
//            case TYPE_PLAYDETAIL_DES:
//                DesViewHolder desViewHolder = (DesViewHolder) holder;
//                desViewHolder.setData
//                        (playDetail.getPlay_info().getDescription());
//                break;
//            case TYPE_PLAYDETAIL_SHOWIMAGE:
//                ShowImageViewHolder showImageViewHolder = (ShowImageViewHolder) holder;
//                showImageViewHolder.setData(playDetail.getMovie_image());
//                break;
//            case TYPE_PLAYDETAIL_COMMENT:
//                CommentViewHolder commentViewHolder = (CommentViewHolder) holder;
//                commentViewHolder.setData(playDetail.getComment_list(), playDetail.getPlay_info().getComment_count());
//                break;
//            case TYPE_PLAYDETAIL_COMMENDS:
//                CommendsViewHolder commendHolder = (CommendsViewHolder) holder;
//                commendHolder.setData(playDetail.getCommend_list());
//                break;
//        }
//    }
//   这这里化繁为简!!!!!!!!!!!!!!!!!
//    @Override
//    public int getItemViewType(int position) {
//        currentType = position;
//        if (ListUtils.isEmpty(playDetail.getMovie_image())) {
//            if (currentType == TYPE_PLAYDETAIL_SHOWIMAGE) {
//                currentType = TYPE_PLAYDETAIL_COMMENT;
//            }
//        }
//        if (ListUtils.isEmpty(playDetail.getComment_list())) {
//            if (currentType == TYPE_PLAYDETAIL_COMMENT) {
//                currentType = TYPE_PLAYDETAIL_COMMENDS;
//            }
//        }
//        return currentType;
//    }
//
//    @Override
//    public int getItemCount() {
//        int size = 4;
//        if (ListUtils.isEmpty(playDetail.getMovie_image())) {
//            size--;
//        }
//        if (ListUtils.isEmpty(playDetail.getComment_list())) {
//            size--;
//        }
//        //Log.e("size", size + "ListUtils"); getItemCount方法会调用很多次,size只能在方法里面
//
//        return size;
//    }
//
//
//    class DesViewHolder extends RecyclerView.ViewHolder {
//        ExpandableTextView tv_card_content;
//        private Context mContext;
//
//        public DesViewHolder(View itemView, Context mContext) {
//            super(itemView);
//            tv_card_content = (ExpandableTextView) itemView.findViewById(R.id.tv_card_content);
//            this.mContext = mContext;
//
//        }
//
//        public void setData(final String data1) {
//            tv_card_content.setText(HtmlUtils.creatSpanned(data1));
//        }
//    }
//
//    List<String> showImgUrlList = new ArrayList<>();
//
//    class ShowImageViewHolder extends RecyclerView.ViewHolder {
//
//        private View ll_show_img;
//        private ViewGroup llMyHsvShow;
//        private Context mContext;
//        TextView tv_showimg_num;
//
//        public ShowImageViewHolder(View itemView, Context mContext) {
//            super(itemView);
//            ll_show_img = itemView.findViewById(R.id.ll_show_img);
//            llMyHsvShow = (ViewGroup) itemView.findViewById(R.id.ll_my_hsv_show);
//            tv_showimg_num = (TextView) itemView.findViewById(R.id.tv_showimg_num);
//            this.mContext = mContext;
//        }
//
//        public void setData(final List<PlayDetail.ShowImag> showImagList) {
//
//
//            if (!ListUtils.isEmpty(showImagList)) {
//                if (!ListUtils.isEmpty(showImgUrlList)) showImgUrlList.clear();
//                for (PlayDetail.ShowImag showImag : showImagList) {
//                    showImgUrlList.add(showImag.getThumb_url());
//                }
//                tv_showimg_num.setText("(" + showImgUrlList.size() + ")");
//
//                for (int i = 0; i < showImagList.size(); i++) {
//                    ImageView imageView = new ImageView(mContext);
//
//                    final int padding = mContext.getResources().getDimensionPixelSize(R.dimen.dp_4);
//                    imageView.setMinimumHeight(mContext.getResources().getDimensionPixelSize(R.dimen.dp_90));
//                    imageView.setMinimumWidth(mContext.getResources().getDimensionPixelSize(R.dimen.dp_90));
//                    imageView.setMaxHeight(mContext.getResources().getDimensionPixelSize(R.dimen.dp_90));
//                    imageView.setMaxWidth(mContext.getResources().getDimensionPixelSize(R.dimen.dp_90));
//                    imageView.setPadding(padding, padding, padding, padding);
//                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                    imageView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent intent = new Intent(mContext, ShowImageActivity.class);
//                            Bundle bundle = new Bundle();
//                            bundle.putSerializable(INTENT_IMG_DETAIL, (Serializable) showImgUrlList);
//                            intent.putExtras(bundle);
//                            mContext.startActivity(intent);
//                        }
//                    });
//                    GlideUtils.getInstance().loadImage(showImagList.get(i).getThumb_url(), imageView);
//                    llMyHsvShow.addView(imageView, LinearLayout.LayoutParams.WRAP_CONTENT);
//
//                }
//
//                itemView.findViewById(R.id.tv_more).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(mContext, ShowImageActivity.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putSerializable(INTENT_IMG_DETAIL, (Serializable) showImgUrlList);
//                        intent.putExtras(bundle);
//                        mContext.startActivity(intent);
//                    }
//                });
//
//            } else {
//                //  ll_show_img.setVisibility(View.GONE);
//            }
//        }
//    }
//
//    class CommentViewHolder extends RecyclerView.ViewHolder {
//        private ViewGroup llUserComment;
//        private Context mContext;
//        private LinearLayout ll_comment_heard;
//        private TextView tv_comment_num;
//
//
//        public CommentViewHolder(View itemView, Context mContext) {
//            super(itemView);
//            //相关评论
//            ll_comment_heard = (LinearLayout) itemView.findViewById(R.id.ll_user_comment_heard);
//            tv_comment_num = (TextView) itemView.findViewById(R.id.tv_comment_num);
//            llUserComment = (ViewGroup) itemView.findViewById(R.id.ll_user_comment);
//            this.mContext = mContext;
//        }
//
//        //评论列表和评论总数目
//        public void setData(final List<Comment> comment_list, String comment_count) {
//            if (ListUtils.isEmpty(comment_list)) {
//                ll_comment_heard.setVisibility(View.GONE);
//                return;
//            }
//
//            if (StringUtils.isNotBlank(comment_count)) {
//                tv_comment_num.setText("(" + comment_count + ")");
//            }
//
//            itemView.findViewById(R.id.tv_more).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    Intent intent = new Intent(mContext, UserCommentActivity.class);
//                    intent.putExtra(INTENT_COMMENT_TYPE, true);
//                    intent.putExtra(INTENT_OBJECT_ID, play_id);
//
//                    mContext.startActivity(intent);
//                }
//            });
//
//            for (int i = 0; i < comment_list.size(); i++) {
//
//                Comment comment = comment_list.get(i);
//                View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_play_detail_user_comment, null);
//                ImageView iv_user_info = (ImageView) itemView.findViewById(R.id.iv_user_info);
//                TextView tv_user_name = (TextView) itemView.findViewById(R.id.tv_user_name);
//                TextView tv_time_comment = (TextView) itemView.findViewById(R.id.tv_time_comment);
//                TextView tv_comment = (TextView) itemView.findViewById(R.id.tv_comment);
//                RatingBar ratingbar_info = (RatingBar) itemView.findViewById(R.id.ratingbar_info_stars);
//                ViewGroup ll_img_comment = (ViewGroup) itemView.findViewById(R.id.ll_img_comment);
//
//                //官方回复
//                RelativeLayout rl_article = (RelativeLayout) itemView.findViewById(R.id.rl_article);
//                TextView tv_article_content = (TextView) itemView.findViewById(R.id.tv_article_content);
//
//                tv_comment.setText(comment.getContent());
//                tv_time_comment.setText(comment.getCreate_time());
//                tv_user_name.setText(comment.getUser_name());
//                //评价的用户的头像为圆型
//                Glide.with(mContext).load(comment.getHeader_img())
//                        .placeholder(R.mipmap.ic_user)
//                        .bitmapTransform(new CropCircleTransformation(mContext)).crossFade(1000).into(iv_user_info);
//
//                ratingbar_info.setRating(comment.getScore() / 2);
//
//                if (StringUtils.isNotBlank(comment.getRecomment())) {
//                    tv_article_content.setText(comment.getRecomment());
//                    rl_article.setVisibility(View.VISIBLE);
//                }
//
//                final List<String> show_img = comment.getShow_img();
//
//                if (!ListUtils.isEmpty(show_img)) {
//                    for (int j = 0; j < show_img.size(); j++) {
//                        ImageView imageView = new ImageView(mContext);
//                        final int padding = mContext.getResources().getDimensionPixelSize(R.dimen.dp_4);
//                        imageView.setMinimumHeight(mContext.getResources().getDimensionPixelSize(R.dimen.dp_90));
//                        imageView.setMinimumWidth(mContext.getResources().getDimensionPixelSize(R.dimen.dp_90));
//                        imageView.setMaxHeight(mContext.getResources().getDimensionPixelSize(R.dimen.dp_90));
//                        imageView.setMaxWidth(mContext.getResources().getDimensionPixelSize(R.dimen.dp_90));
//                        imageView.setPadding(padding, padding, padding, padding);
//                        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                        final int finalJ = j;
//                        imageView.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                Intent intent = new Intent(mContext, ShowImgDetailActivity.class);
//                                Bundle bundle = new Bundle();
//                                bundle.putSerializable(INTENT_IMG_DETAIL, (Serializable) show_img);
//                                intent.putExtras(bundle);
//                                intent.putExtra(INTENT_IMG_POSITION, finalJ);
//                                mContext.startActivity(intent);
//                            }
//                        });
//                        GlideUtils.getInstance().loadImage(show_img.get(j), imageView);
//                        ll_img_comment.addView(imageView, LinearLayout.LayoutParams.WRAP_CONTENT);
//
//                    }
//                } else {
////recomment
//                    //   itemView.findViewById(R.id.view_line_bottom).setVisibility(View.GONE);
//                    ll_img_comment.setVisibility(View.GONE);
//                }
//                llUserComment.addView(itemView, new LinearLayout.LayoutParams(-2, -2));
//            }
//        }
//    }
//
//    class CommendsViewHolder extends RecyclerView.ViewHolder {
//        private ViewGroup ll_my_show_commend;
//        private Context mContext;
//        private List<Show> commend_list;
//
//        public CommendsViewHolder(View itemView, Context mContext) {
//            super(itemView);
//
//
//            ll_my_show_commend = (LinearLayout) itemView.findViewById(R.id.ll_my_show_commend);
//
//
//            this.mContext = mContext;
//
//        }
//
//        public void setData(final List<Show> commend_list) {
//            if (ListUtils.isEmpty(commend_list)) {
//                ll_my_show_commend.setVisibility(View.GONE);
//                return;
//            } else {
//                this.commend_list = commend_list;
//
//                for (int i = 0; i < commend_list.size(); i++) {
//                    View view = LayoutInflater.from(mContext).inflate(R.layout.item_show_commend, ll_my_show_commend, false);
//
//                    ImageView iv_recommend = (ImageView) view.findViewById(R.id.iv_recommend);
//                    TextView tv_recommend = (TextView) view.findViewById(R.id.tv_recommend);
//                    TextView tv_time = (TextView) view.findViewById(R.id.tv_time);
//
//                    GlideUtils.getInstance().loadTransformImage(commend_list.get(i).getPicurl(), iv_recommend,
//                            new RoundedCornersTransformation(mContext, DisplayUtils.dp2px(8), 0, RoundedCornersTransformation.CornerType.TOP));
//                    tv_recommend.setText(commend_list.get(i).getSub_title() + commend_list.get(i).getPlay_name());
//                    tv_time.setText(commend_list.get(i).getSydate());
//
//                    final int finalI = i;
//                    view.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent intent = new Intent(mContext, ShowDetailActivity.class);
//                            intent.putExtra(INTENT_PLAY_ID, commend_list.get(finalI).getPlay_id());
//
//                            mContext.startActivity(intent);
//                        }
//                    });
//                    ll_my_show_commend.addView(view, new LinearLayout.LayoutParams(-2, -2));
//
//                }
//            }
//        }
//    }
//}
