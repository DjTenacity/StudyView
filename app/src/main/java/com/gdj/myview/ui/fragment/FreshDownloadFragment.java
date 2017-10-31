package com.gdj.myview.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gdj.myview.R;
import com.gdj.myview.view.FreshDownloadView;

/**
 * Comment:
 *
 * @author : DJ鼎尔东/ 1757286697@qq.com
 * @version : ${user} 1.0
 * @date : 2017/10/31 15:46
 */
public class FreshDownloadFragment extends Fragment implements View.OnClickListener {


    private FreshDownloadView freshDownloadView;
    private Button btDownloaded;
    private TextView btReset;
    private TextView btDownloadError;
    private final int FLAG_SHOW_OK = 10;
    private final int FLAG_SHOW_ERROR = 11;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int progress = (int) msg.obj;
            freshDownloadView.upDateProgress(progress);
            switch (msg.what) {
                case FLAG_SHOW_OK:
                    break;
                case FLAG_SHOW_ERROR:
                    freshDownloadView.showDownloadError();
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.from(getContext()).inflate(R.layout.fragment_freshdownload,null);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        freshDownloadView = (FreshDownloadView) view.findViewById(R.id.pitt);
        btDownloaded = (Button) view.findViewById(R.id.bt_downloaded);
        btReset = (Button) view.findViewById(R.id.bt_reset);
        btDownloadError = (Button) view.findViewById(R.id.bt_download_error);
        btDownloaded.setOnClickListener(this);
        btReset.setOnClickListener(this);
        btDownloadError.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_downloaded:
                if (freshDownloadView.using()) return;
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        for (int i = 0; i <= 100; i++) {
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Message message = Message.obtain();
                            message.obj = i;
                            handler.sendMessage(message);
                        }
                    }
                }).start();
                break;
            case R.id.bt_reset:
                freshDownloadView.reset();
                break;
            case R.id.bt_download_error:
                if (freshDownloadView.using()) return;
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        for (int i = 0; i <= 30; i++) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Message message = Message.obtain();
                            if (i == 30) {
                                message.what = FLAG_SHOW_ERROR;
                            }
                            message.obj = i;
                            handler.sendMessage(message);
                        }
                    }
                }).start();
                break;
        }
    }
}
