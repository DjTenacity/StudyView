package com.gdj.videoselect.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;

import com.gdj.videoselect.R;
import com.gdj.videoselect.adapter.PictureImageGridAdapter;
import com.gdj.videoselect.config.PictureSelectionConfig;
import com.gdj.videoselect.decoration.GridSpacingItemDecoration;
import com.gdj.videoselect.entity.LocalMedia;
import com.gdj.videoselect.entity.LocalMediaFolder;
import com.gdj.videoselect.model.LocalMediaLoader;
import com.gdj.videoselect.tools.DebugUtil;
import com.gdj.videoselect.tools.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：${LoveDjForever} on 2017/7/31 16:39
 *  * 邮箱： @qq.com
 */

public class SelectActivity extends AppCompatActivity implements PictureImageGridAdapter.OnPhotoSelectChangedListener {

    RecyclerView picture_recycler;
    PictureImageGridAdapter adapter;
    protected PictureSelectionConfig config;
    protected List<LocalMedia> selectionMedias;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        inint();
    }

    private void inint() {

        picture_recycler = (RecyclerView) findViewById(R.id.picture_recycler);

        picture_recycler.setHasFixedSize(true);
        picture_recycler.addItemDecoration(new GridSpacingItemDecoration(4,
                ScreenUtils.dip2px(this, 2), false));

        picture_recycler.setLayoutManager(new GridLayoutManager(this, 4));
        // 解决调用 notifyItemChanged 闪烁问题,取消默认动画
        ((SimpleItemAnimator) picture_recycler.getItemAnimator())
                .setSupportsChangeAnimations(false);



        adapter = new PictureImageGridAdapter(this, config);
        adapter.setOnPhotoSelectChangedListener( this);
     //   adapter.bindSelectImages(selectionMedias);
        picture_recycler.setAdapter(adapter);

    }

    @Override
    public void onTakePhoto() {

    }

    @Override
    public void onChange(List<LocalMedia> selectImages) {

    }

    @Override
    public void onPictureClick(LocalMedia media, int position) {

    }

    private LocalMediaLoader mediaLoader;
    private List<LocalMedia> images = new ArrayList<>();
    private List<LocalMediaFolder> foldersList = new ArrayList<>();

    /**
     *  获取想要类型的资源
     */
    protected void readLocalMedia() {
        mediaLoader.loadAllMedia(new LocalMediaLoader.LocalMediaLoadListener() {
            @Override
            public void loadComplete(List<LocalMediaFolder> folders) {
                DebugUtil.i("loadComplete:" + folders.size());
                if (folders.size() > 0) {
                    foldersList = folders;
                    LocalMediaFolder folder = folders.get(0);
                    folder.setChecked(true);
                    List<LocalMedia> localImg = folder.getImages();
                    // 这里解决有些机型会出现拍照完，相册列表不及时刷新问题
                    // 因为onActivityResult里手动添加拍照后的照片，
                    // 如果查询出来的图片大于或等于当前adapter集合的图片则取更新后的，否则就取本地的
                    if (localImg.size() >= images.size()) {
                        images = localImg;
                //        folderWindow.bindFolder(folders);
                    }
                }
                if (adapter != null) {
                    if (images == null) {
                        images = new ArrayList<>();
                    }
                    adapter.bindImagesData(images);
//                    tv_empty.setVisibility(images.size() > 0
//                            ? View.INVISIBLE : View.VISIBLE);
                }
              //  dismissDialog();
            }
        });
    }
}
