package com.example.pet.my;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.pet.R;

public class EditInfo extends AppCompatActivity {
    private TextView btnSex;
    private PopupWindow popupWindow;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);
        //设置控件内容
        setView();
        btnSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow();
            }
        });
    }

    private void showPopupWindow() {
        //创建PopupWindow
        popupWindow = new PopupWindow(this);
        //设置弹出窗口的宽度
        popupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        //设置视图
        View view = getLayoutInflater().inflate(R.layout.popupwindow, null);
        //设置控件属性和监听器
        EditListener listener = new EditListener();
        Button btnMan = view.findViewById(R.id.edit_info_sex_man);
        Button btnWoman = view.findViewById(R.id.edit_info_sex_woman);
        Button btnCancel = view.findViewById(R.id.edit_info_sex_cancel);
        btnMan.setOnClickListener(listener);
        btnWoman.setOnClickListener(listener);
        btnCancel.setOnClickListener(listener);
        popupWindow.setContentView(view);
        //必须指定显示位置
        LinearLayout root = findViewById(R.id.edit_info_root);
        popupWindow.showAtLocation(root, Gravity.BOTTOM, 0, 0);
    }

    private void setView() {
        btnSex = findViewById(R.id.edit_info_sex);
        toolbar = findViewById(R.id.edit_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditInfo.this.finish();
            }
        });
    }

    private class EditListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.edit_info_sex_man:
                    btnSex.setText("男");
                    popupWindow.dismiss();
                    break;
                case R.id.edit_info_sex_woman:
                    btnSex.setText("女");
                    popupWindow.dismiss();
                    break;
                case R.id.edit_info_sex_cancel:
                    popupWindow.dismiss();
                    break;
            }
        }
    }
}