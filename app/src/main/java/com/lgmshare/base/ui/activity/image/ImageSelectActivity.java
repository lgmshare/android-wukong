package com.lgmshare.base.ui.activity.image;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;

import com.lgmshare.base.R;
import com.lgmshare.base.helper.ImageHelper;
import com.lgmshare.base.helper.PathConfigruationHelper;
import com.lgmshare.base.ui.activity.base.BaseActivity;
import com.lgmshare.component.util.FileUtils;
import com.lgmshare.component.utils.ImageUtil;

import java.io.File;
import java.io.IOException;

/**
 * @author LGM
 * @ClassName: ImageSelectActivity
 * @description: 获取图片
 * @email: lgmshare@gmail.com
 * @datetime 2014-6-10 下午10:43:30
 */
public class ImageSelectActivity extends BaseActivity implements OnClickListener {

    public static final String EXTRA_IMAGE_PATH = "image_path";
    public static final String EXTRA_IMAGE_CROP = "image_crop";
    public static final String EXTRA_IMAGE_HIDE_PICK_PHTOT = "image_hide_pick";

    private static final int REQ_RESULT_PHOTO_CODE = 500;
    private static final int REQ_RESULT_CAPTURE_CODE = 600;
    private static final int REQ_RESULT_CORP_CODE = 666;

    private String mSaveImagePath;// 图片缓存路径
    private String mTempImagePath;//照相机缓存路径
    private boolean mIsCrop = false;
    private boolean mHidePickPhoto = false;

    protected boolean initActionBar() {
        return false;
    }

    @Override
    protected boolean isHideActionbar() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_select);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = LayoutParams.MATCH_PARENT;
        lp.height = LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        getWindow().setAttributes(lp);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mIsCrop = extras.getBoolean(EXTRA_IMAGE_CROP);
            mHidePickPhoto = extras.getBoolean(EXTRA_IMAGE_HIDE_PICK_PHTOT, false);
            mSaveImagePath = extras.getString(EXTRA_IMAGE_PATH);
        }
        if (TextUtils.isEmpty(mSaveImagePath)) {
            mSaveImagePath = PathConfigruationHelper.getFilePathOfExternal(this, "default", "png");
        }
        mTempImagePath = PathConfigruationHelper.getFilePathOfExternal(this, "default_temp", "png");
        if (!FileUtils.isExist(mTempImagePath)) {
            File file = new File(mTempImagePath);
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (mHidePickPhoto) {
            findViewById(R.id.btn_pick_photo).setVisibility(View.GONE);
        }

        findViewById(R.id.btn_take_photo).setOnClickListener(this);
        findViewById(R.id.btn_pick_photo).setOnClickListener(this);
        findViewById(R.id.btn_cancel).setOnClickListener(this);

        overridePendingTransition(R.anim.none, R.anim.none);
    }

    /**
     * 打开手机相册
     */
    private void openMediaStore() {
        if (!android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            showToastMessage("存储卡已取出，相册功能暂不可用");
            return;
        }

        Intent intent = new Intent();
        /* 开启Pictures画面Type设定为image */
        intent.setType("image/*");
        /* 使用Intent.ACTION_GET_CONTENT这个Action */
        intent.setAction(Intent.ACTION_GET_CONTENT);
        /* 取得相片后返回本画面 */
        startActivityForResult(intent, REQ_RESULT_PHOTO_CODE);
    }

    /**
     * 打开照相机
     */

    private void openCamera() {
        if (!android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            showToastMessage("存储卡已取出，拍照功能暂不可用");
            return;
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getImageUri(mTempImagePath));
        startActivityForResult(intent, REQ_RESULT_CAPTURE_CODE);
    }

    private Uri getImageUri(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file = FileUtils.createSDFile(path);
        }
        return Uri.fromFile(file);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel:
                setResult(Activity.RESULT_CANCELED);
                finish();
                break;
            case R.id.btn_pick_photo:
                openMediaStore();
                break;
            case R.id.btn_take_photo:
                openCamera();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_RESULT_PHOTO_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                crop(data.getData());
            }
        } else if (requestCode == REQ_RESULT_CAPTURE_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                crop(Uri.fromFile(new File(mTempImagePath)));
            }
        } else if (requestCode == REQ_RESULT_CORP_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                backResult();
            }
        }
        if (resultCode == Activity.RESULT_CANCELED) {
            finish();
        }
    }

    private void crop(Uri imageUri) {
        if (mIsCrop) {
            Intent intent = new Intent(ImageSelectActivity.this, ImageCropActivity.class);
            intent.putExtra("image-path", mSaveImagePath);
            intent.putExtra("image-path-uri", imageUri);
            intent.putExtra("rotate", true);
            //固定缩放比
            intent.putExtra("scale", false);
            startActivityForResult(intent, REQ_RESULT_CORP_CODE);
        } else {
            if (imageUri != null) {
                Bitmap selectedImage = null;
                try {
                    selectedImage = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    //图片压缩
                    selectedImage = ImageUtil.compressForQuality(selectedImage);
                    ImageHelper.save(selectedImage, mSaveImagePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            backResult();
        }
    }

    private void backResult() {
        Bundle bd = new Bundle();
        bd.putString("image-path", mSaveImagePath);
        bd.putParcelable("image-path-uri", getImageUri(mSaveImagePath));
        setResult(Activity.RESULT_OK, getIntent().putExtras(bd));
        finish();
    }

    @Override
    protected void onDestroy() {
        overridePendingTransition(R.anim.none, R.anim.none);
        super.onDestroy();
    }
}
