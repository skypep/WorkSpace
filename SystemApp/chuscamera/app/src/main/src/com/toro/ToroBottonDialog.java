package com.toro;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.toro.camera.R;

public class ToroBottonDialog extends Dialog {
    private Context context;
    private String title,content;
    private String actionText;
    private View.OnClickListener actionListener;

    public ToroBottonDialog(Context context) {
        super(context, R.style.ToroBottonDialog);
        this.context = context;
        this.title = title;
        this.content = content;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.toro_scan_result_dialog);
        setCancelable(true);
        //点击外部不可dismiss
        setCanceledOnTouchOutside(false);
        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);

        ((TextView) findViewById(R.id.title)).setText(title);
        ((TextView) findViewById(R.id.result)).setText(content);
        ((Button) findViewById(R.id.cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
        ((Button) findViewById(R.id.action)).setText(actionText);
        if(actionListener != null) {
            ((Button) findViewById(R.id.action)).setOnClickListener(actionListener);
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAction(String actionText, View.OnClickListener listener) {
        this.actionText = actionText;
        this.actionListener = listener;
    }
}
