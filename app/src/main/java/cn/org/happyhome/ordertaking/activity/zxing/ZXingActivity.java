package cn.org.happyhome.ordertaking.activity.zxing;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Hashtable;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.org.happyhome.library.BuildConfig;
import cn.org.happyhome.library.utils.PhotoUtils;
import cn.org.happyhome.ordertaking.R;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class ZXingActivity extends Activity
        implements DecoratedBarcodeView.TorchListener, CompoundButton.OnCheckedChangeListener {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.cb_light)
    CheckBox cbLight;
    //    @BindView(R.id.surface)
//    SurfaceView surface;
    @BindView(R.id.db_view)
    DecoratedBarcodeView dbView;


    public static final int PHOTO_CODE = 1;

    private CaptureManager captureManager;
    private boolean isLightOn = false;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
             /*设置状态栏的颜色*/
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(android.R.color.black));
        setContentView(R.layout.activity_zxing);
        ButterKnife.bind(this);
        dbView.setTorchListener(this);
        if (!hasFlash()) {
            cbLight.setVisibility(View.GONE);
        }
        cbLight.setOnCheckedChangeListener(this);

        captureManager = new CaptureManager(this, dbView);
        captureManager.initializeFromIntent(getIntent(), savedInstanceState);
        captureManager.decode();
    }

    @Override
    protected void onResume() {
        super.onResume();
        captureManager.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        captureManager.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        captureManager.onDestroy();
    }

    // 判断是否有闪光灯功能
    private boolean hasFlash() {
        return getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    @OnClick({R.id.img_back, R.id.txt_map_depot})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_map_depot:
                PhotoUtils.pickImage(this, PHOTO_CODE);
                break;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        captureManager.onSaveInstanceState(outState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return dbView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }



    @Override
    public void onTorchOn() {
        Toast.makeText(this, "torch on", Toast.LENGTH_LONG).show();
        isLightOn = true;
    }

    @Override
    public void onTorchOff() {
        Toast.makeText(this, "torch off", Toast.LENGTH_LONG).show();
        isLightOn = false;
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            dbView.setTorchOn();
            cbLight.setText("关闭");
        } else {
            dbView.setTorchOff();
            cbLight.setText("开启");
        }

    }
    String photo_path = null;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
//        String contents = intentResult.getContents();
//        Log.d("ZXingActivity", "contents=====" + contents);
//        String code = data.getStringExtra("LOCAL_PHOTO_RESULT");
//        Log.d("ZXingActivity", code);


        String[] proj = {MediaStore.Images.Media.DATA};
        // 获取选中图片的路径
        Cursor cursor = getContentResolver().query(data.getData(),
                proj, null, null, null);

        if (cursor.moveToFirst()) {

            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            photo_path = cursor.getString(column_index);
            if (photo_path == null) {
                photo_path = PhotoUtils.getPath(getApplicationContext(),
                        data.getData());
            }
            if (BuildConfig.DEBUG) Log.d("ZXingActivity","photo_path=="+ photo_path);
        }

        cursor.close();

        new Thread(new Runnable() {
            @Override
            public void run() {

                Result result = scanningImage(photo_path);

                if (BuildConfig.DEBUG) Log.d("ZXingActivity", "result:" + result);

                if (result == null) {
                    Looper.prepare();
                    Toast.makeText(getApplicationContext(), "图片格式有误", Toast.LENGTH_SHORT)
                            .show();
                    Looper.loop();

                } else {
                    String recode = recode(result.toString());
                    if (BuildConfig.DEBUG) Log.d("ZXingActivity", "code=="+recode);
                    Intent data = new Intent();
                    data.putExtra("result", recode);
                    setResult(300, data);

                    finish();
                }


            }
        }).start();


    }


    protected Result scanningImage(String path) {
        if (TextUtils.isEmpty(path)) {

            return null;

        }
        if (BuildConfig.DEBUG) Log.d("ZXingActivity", "1211");
        // DecodeHintType 和EncodeHintType
        Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
        hints.put(DecodeHintType.CHARACTER_SET, "utf-8"); // 设置二维码内容的编码
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 先获取原大小
        Bitmap scanBitmap = BitmapFactory.decodeFile(path, options);
        options.inJustDecodeBounds = false; // 获取新的大小

        int sampleSize = (int) (options.outHeight / (float) 200);

        if (sampleSize <= 0)
            sampleSize = 1;
        options.inSampleSize = sampleSize;
        scanBitmap = BitmapFactory.decodeFile(path, options);

        if (sampleSize <= 0)
            sampleSize = 1;
        options.inSampleSize = sampleSize;

        int[] data = new int[scanBitmap.getWidth() * scanBitmap.getHeight()];
        RGBLuminanceSource source = new RGBLuminanceSource(scanBitmap.getWidth(), scanBitmap.getHeight(), data);
        BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
        QRCodeReader reader = new QRCodeReader();
        try {


            return reader.decode(bitmap1, hints);

        } catch (NotFoundException e) {

            e.printStackTrace();

        } catch (ChecksumException e) {

            e.printStackTrace();

        } catch (FormatException e) {

            e.printStackTrace();

        }

        return null;

    }

    private String recode(String str) {
        String formart = "";

        try {
            boolean ISO = Charset.forName("ISO-8859-1").newEncoder()
                    .canEncode(str);
            if (ISO) {
                formart = new String(str.getBytes("ISO-8859-1"), "GB2312");
                Log.i("1234      ISO8859-1", formart);
            } else {
                formart = str;
                Log.i("1234      stringExtra", str);
            }
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (BuildConfig.DEBUG) Log.d("ZXingActivity", "formart=="+formart);
        return formart;
    }

}
