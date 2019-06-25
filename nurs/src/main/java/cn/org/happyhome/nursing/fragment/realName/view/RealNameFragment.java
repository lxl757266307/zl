package cn.org.happyhome.nursing.fragment.realName.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import cn.org.happyhome.library.BuildConfig;
import cn.org.happyhome.library.base.BaseMvpFragment;
import cn.org.happyhome.library.utils.PhotoUtils;
import cn.org.happyhome.nursing.R;
import cn.org.happyhome.nursing.bean.EventBusMessage;
import cn.org.happyhome.nursing.fragment.realName.contract.IRealNameContract;
import cn.org.happyhome.nursing.fragment.realName.presenter.RealNamePresenter;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2019/3/25  18:45
 * - generate by MvpAutoCodePlus plugin.
 */

public class RealNameFragment extends BaseMvpFragment<IRealNameContract.View, IRealNameContract.Presenter> implements IRealNameContract.View, View.OnClickListener {


    private TextView txttitleone;
    private ImageView imgone;
    private TextView txttitletwo;
    private ImageView imgtwo;
    //table 选择位置
    private int type;
    public static final String TYPE = "TYPE";

    public static final int PICK_PHOTO_ONE = 1;
    public static final int PICK_PHOTO_TWO = 2;

    public static RealNameFragment getInstance(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, type);
        RealNameFragment realNameFragment = new RealNameFragment();
        realNameFragment.setArguments(bundle);
        return realNameFragment;
    }


    @Override
    public void viewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        type = getArguments().getInt(TYPE);
        if (type == 1) {
            txttitleone.setText("身份证正面:");
            txttitletwo.setText("身份证反面:");
        } else if (type == 2) {
            txttitleone.setText("健康证:");
            txttitletwo.setText("护士证:");
        }
    }


    @Override
    public View createView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_real_name, container, false);
        this.imgtwo = (ImageView) view.findViewById(R.id.img_two);
        this.txttitletwo = (TextView) view.findViewById(R.id.txt_title_two);
        this.imgone = (ImageView) view.findViewById(R.id.img_one);
        this.txttitleone = (TextView) view.findViewById(R.id.txt_title_one);
        this.imgone.setOnClickListener(this);
        this.imgtwo.setOnClickListener(this);
        return view;
    }


    @Override
    public IRealNameContract.Presenter createPresenter() {
        return new RealNamePresenter();
    }

    @Override
    public IRealNameContract.View createView() {
        return this;
    }

    private String ps1;
    private String ps2;
    public static final String ONPHOTOPICK = "ONPHOTOPICK";

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            String path = PhotoUtils.getPathFromUri(data, getActivity());
            String cachePath = Environment.getExternalStorageDirectory().getAbsolutePath();
            Luban.with(getActivity())
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

                            if (requestCode == PICK_PHOTO_ONE) {
                                ps1 = file.getAbsolutePath();
                                Glide.with(getActivity()).load(ps1).into(imgone);
                            } else if (requestCode == PICK_PHOTO_TWO) {
                                ps2 = file.getAbsolutePath();
                                Glide.with(getActivity()).load(ps2).into(imgtwo);
                            }


                            Intent intent = new Intent();
                            intent.putExtra("img1", ps1);
                            intent.putExtra("img2", ps2);
                            intent.setAction(ONPHOTOPICK);
                            getActivity().sendBroadcast(intent);

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
    public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (v.getId() == R.id.img_one) {
            startActivityForResult(intent, PICK_PHOTO_ONE);
        }

        if (v.getId() == R.id.img_two) {
            startActivityForResult(intent, PICK_PHOTO_TWO);
        }
    }
}

