package com.gdj.myview.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.gdj.myview.R;
import com.gdj.myview.view.multichoicescirclebutton.MultiChoicesCircleButton;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：${LoveDjForever} on 2017/7/6 14:05
 *  * 邮箱： @qq.com
 */

public class MultiButtonActivity extends AppCompatActivity {
    MultiChoicesCircleButton multiChoicesCircleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_button);

        List<MultiChoicesCircleButton.Item> buttonItems = new ArrayList<>();

        MultiChoicesCircleButton.Item item1 = new MultiChoicesCircleButton.Item("Like", getResources().getDrawable(R.drawable.icon1), 30);
        buttonItems.add(item1);
        MultiChoicesCircleButton.Item item2 = new MultiChoicesCircleButton.Item("Message", getResources().getDrawable(R.drawable.icon2), 90);
        buttonItems.add(item2);
        MultiChoicesCircleButton.Item item3 = new MultiChoicesCircleButton.Item("Tag", getResources().getDrawable(R.drawable.icon3), 150);
        buttonItems.add(item3);

        multiChoicesCircleButton = (MultiChoicesCircleButton) findViewById(R.id.multiChoicesCircleButton);
        multiChoicesCircleButton.setButtonItems(buttonItems);
        multiChoicesCircleButton.setOnSelectedItemListener(new MultiChoicesCircleButton.OnSelectedItemListener() {
            @Override
            public void onSelected(MultiChoicesCircleButton.Item item, int index) {


                startActivity(new Intent(MultiButtonActivity.this, AnimationActivity.class));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);

                Toast.makeText(MultiButtonActivity.this, item.getText(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}