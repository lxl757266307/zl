package cn.org.happyhome.nursing.activity.appeal.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.org.happyhome.library.BuildConfig;
import cn.org.happyhome.library.base.BaseMvpActivity;
import cn.org.happyhome.library.base.Const;
import cn.org.happyhome.library.utils.ActivityStack;
import cn.org.happyhome.library.utils.PhotoUtils;
import cn.org.happyhome.library.utils.ToolUitls;
import cn.org.happyhome.nursing.R;
import cn.org.happyhome.nursing.activity.appeal.contract.IAppealContract;
import cn.org.happyhome.nursing.activity.appeal.presenter.AppealPresenter;
import cn.org.happyhome.nursing.adadpter.ImageAdapter;
import cn.org.happyhome.nursing.bean.ResultBean;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/10/18  9:28
 * - generate by MvpAutoCodePlus plugin.
 */

public class AppealActivity extends BaseMvpActivity<IAppealContract.View, IAppealContract.Presenter> implements IAppealContract.View, View.OnClickListener, BaseQuickAdapter.OnItemChildClickListener, TextWatcher {

    private TextView txtOrderNumber;
    private EditText editAppealContent;
    private TextView txtContentNumber;
    private RecyclerView recycleview;
    private Button btnSubmit;
    private ImageView imgBack;
    private ImageView imgAddPhoto;
    private TextView txtTitle;
    private ProgressBar progressBar;

    ImageAdapter imageAdapter;
    TextView txtBack;
    ArrayList<String> list;
    List<String> filePaths;

    public static final int PICK_PHOTO = 1;
    boolean canPick = true;
    Bitmap addbitmap;

    SharedPreferences sharedPreferences;
    String userId;
    String orderId;
    String token;

    @Override
    public void initListener() {
        ActivityStack.getInstance().pushActivity(this);
        setContentView(R.layout.activity_appeal);
        sharedPreferences = getSharedPreferences(Const.USER_INFO, MODE_PRIVATE);
        userId = sharedPreferences.getString(Const.USER_ID, null);
        orderId = getIntent().getStringExtra("orderId");
        this.btnSubmit = (Button) findViewById(R.id.btn_submit);
        this.progressBar = (ProgressBar) findViewById(R.id.progress);
        this.recycleview = (RecyclerView) findViewById(R.id.recycle_view);
        this.txtContentNumber = (TextView) findViewById(R.id.txt_content_number);
        this.editAppealContent = (EditText) findViewById(R.id.edit_appeal_content);
        this.txtOrderNumber = (TextView) findViewById(R.id.txt_order_number);
        this.imgBack = findViewById(R.id.img_back);
        this.txtBack = findViewById(R.id.txt_back);
        this.imgAddPhoto = findViewById(R.id.img_add_photo);
        this.txtTitle = findViewById(R.id.txt_title);
        this.txtTitle.setText("订单申诉");
        this.imgAddPhoto.setOnClickListener(this);
        this.txtBack.setOnClickListener(this);
        this.imgBack.setOnClickListener(this);
        this.btnSubmit.setOnClickListener(this);
        this.editAppealContent.addTextChangedListener(this);
        this.recycleview.setLayoutManager(new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false));
        this.recycleview.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                if (parent.getChildAdapterPosition(view) % 2 != 0) {
                    outRect.left = 1;
                }
            }
        });
        this.token = sharedPreferences.getString(Const.QN_TOKEN, null);
        if (BuildConfig.DEBUG) Log.d("AppealActivity", "token==" + token);
        this.txtOrderNumber.setText(orderId);
        filePaths = new ArrayList<>();
        list = new ArrayList<>();
        imageAdapter = new ImageAdapter(R.layout.item_appeal, list);
        this.recycleview.setAdapter(imageAdapter);
        imageAdapter.setOnItemChildClickListener(this);


    }

    @Override
    public IAppealContract.Presenter createPresenter() {
        return new AppealPresenter();
    }

    @Override
    public IAppealContract.View createView() {
        return this;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_submit) {
            String content = editAppealContent.getText().toString();
            if (BuildConfig.DEBUG) Log.d("AppealActivity", content);
            if ("".equals(content)) {
                ToolUitls.toast(this, "请输入申诉内容");
                return;
            }
            if (!(list.size() > 0)) {
                ToolUitls.toast(this, "请上传相应的图片");
                return;
            }

//            for (int i = 0; i < list.size(); i++) {
//                filePaths.add(new File(list.get(i)));
//            }

            if (BuildConfig.DEBUG) Log.d("AppealActivity", "1111111");
            this.progressBar.setVisibility(View.VISIBLE);
            mPresenter.appealOrder(userId, orderId, content, token, list);
        }
        if (v.getId() == R.id.img_back) {
            finish();
        }
        if (v.getId() == R.id.txt_back) {
            finish();
        }

        if (v.getId() == R.id.img_add_photo) {
            if (list.size() < 4) {
                PhotoUtils.pickImage(this, PICK_PHOTO);
            }

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_PHOTO) {
            String path = PhotoUtils.getPathFromUri(data, this);
            String cachePath = Environment.getExternalStorageDirectory().getAbsolutePath();
            Luban.with(this)
                    .load(path)
                    .ignoreBy(100)
                    .setTargetDir(cachePath)
                    .filter(new CompressionPredicate() {
                        @Override
                        public boolean apply(String path) {
                            return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                        }
                    })
                    .setCompressListener(new OnCompressListener() {
                        @Override
                        public void onStart() {
                            // TODO 压缩开始前调用，可以在方法内启动 loading UI
                        }

                        @Override
                        public void onSuccess(File file) {
                            list.add(file.getAbsolutePath());
                            imageAdapter.notifyDataSetChanged();
                            if (list.size() >= 3) {
                                imgAddPhoto.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            // TODO 当压缩过程出现问题时调用
                            if (BuildConfig.DEBUG) Log.d("AppealActivity", "e:" + e);
                        }
                    }).launch();


        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        list.remove(position);
        imageAdapter.notifyDataSetChanged();
        if (list.size() < 3) {
            imgAddPhoto.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        int length = editAppealContent.getText().toString().length();
        if (length > 500) {
            ToolUitls.toast(AppealActivity.this, "最多可输入500字符");
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        int length = editAppealContent.getText().toString().length();
        txtContentNumber.setText(length + "/500");
    }

    @Override
    public void showAppeal(ResultBean resultBean) {
        if (BuildConfig.DEBUG) Log.d("AppealActivity", "resultBean:" + resultBean);
        this.progressBar.setVisibility(View.GONE);
        if (resultBean.getCode() == Const.RESULT_OK) {
            ToolUitls.toast(this, "申诉成功");
            finish();
        } else if (resultBean.getCode() == Const.RESULT_9985) {
            ToolUitls.toast(this, "申诉中，请勿重复提交！");
        } else {
            ToolUitls.toast(this, "服务器繁忙，请稍后再试！");
        }
    }

    @Override
    public void showErr() {
        this.progressBar.setVisibility(View.GONE);
        ToolUitls.toast(this, "服务器繁忙，请稍后再试！");
    }
}

