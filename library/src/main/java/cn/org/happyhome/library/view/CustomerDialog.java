package cn.org.happyhome.library.view;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import cn.org.happyhome.library.R;
import cn.org.happyhome.library.utils.ScreenUtils;

public class CustomerDialog extends AlertDialog implements View.OnClickListener {

    TextView txtCancle;
    TextView txtSure;
    TextView txtDescription;
    Context context;
    String description;

    public CustomerDialog(Context context, String description) {
        super(context);
        this.context = context;
        this.description = description;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = this.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = ScreenUtils.getScreenWidth(context) - 200;
        window.setAttributes(attributes);
        window.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.circle_box_oval));
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.dialog_sure, null);
        this.setContentView(view);
        txtDescription = view.findViewById(R.id.txt_description);
        txtCancle = view.findViewById(R.id.txt_cancle);
        txtSure = view.findViewById(R.id.txt_sure);
        txtCancle.setOnClickListener(this);
        txtSure.setOnClickListener(this);
        this.txtDescription.setText(description);

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.txt_cancle) {
            onDialogChildViewClickListener.onCancle();
        }
        if (v.getId() == R.id.txt_sure) {
            onDialogChildViewClickListener.onSure();
        }

    }

    OnDialogChildViewClickListener onDialogChildViewClickListener;

    public OnDialogChildViewClickListener getOnDialogChildViewClickListener() {
        return onDialogChildViewClickListener;
    }

    public void setOnDialogChildViewClickListener(OnDialogChildViewClickListener onDialogChildViewClickListener) {
        this.onDialogChildViewClickListener = onDialogChildViewClickListener;
    }

    public interface OnDialogChildViewClickListener {
        void onCancle();

        void onSure();
    }
}
