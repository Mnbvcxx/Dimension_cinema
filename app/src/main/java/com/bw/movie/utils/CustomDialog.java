package com.bw.movie.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.TextView;

import com.bw.movie.R;

/**
 * @author : FangShiKang
 * @date : 2019/01/28.
 * email : fangshikang@outlook.com
 * desc :       自定义加载框
 */
public class CustomDialog extends Dialog {
    private String content;

    public CustomDialog(Context context) {
        super(context, R.style.CustomDialog);
        initView();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                if(CustomDialog.this.isShowing()) {
                    CustomDialog.this.dismiss();
                }
                break;
        }
        return true;
    }

    private void initView(){
        setContentView(R.layout.dialog_view);
        setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.alpha=0.8f;
        getWindow().setAttributes(attributes);
        setCancelable(false);
    }
}