package com.lgmshare.component.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 图片工具
 *
 * @author: lim
 * @description: 图片工具
 * @email: lgmshare@gmail.com
 * @datetime: 2014年6月3日 下午4:02:29
 */
public class ImageUtils {

    /**
     * 以最省内存的方式读取本地资源的图片
     *
     * @param context
     * @param resId
     * @return
     */
    public static Bitmap getImageFromRaw(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        // 获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    /**
     * 以最省内存的方式读取本地资源的图片 或者SDCard中的图片
     *
     * @param imagePath  图片在SDCard中的路径
     * @return
     */
    public static Bitmap getImageFromSDCard(String imagePath) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        // 获取资源图片
        return BitmapFactory.decodeFile(imagePath, opt);
    }

    /**
     * 获取本地图片并指定高度和宽度
     *
     * @param imagePath
     * @return
     */
    public static Bitmap getImageFromSDCard2(String imagePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // 获取这个图片的宽和高
        Bitmap myBitmap = BitmapFactory.decodeFile(imagePath, options); // 此时返回myBitmap为空
        // 计算缩放比
        int be = (int) (options.outHeight / (float) 200);
        int ys = options.outHeight % 200;// 求余数
        float fe = ys / (float) 200;
        if (fe >= 0.5)
            be = be + 1;
        if (be <= 0)
            be = 1;
        options.inSampleSize = be;
        // 重新读入图片，注意这次要把options.inJustDecodeBounds 设为 false
        options.inJustDecodeBounds = false;
        myBitmap = BitmapFactory.decodeFile(imagePath, options);
        return myBitmap;
    }

    /**
     * 获取本地图片并指定高度和宽度 480*800
     *
     * @param imagePath
     * @return
     */
    public static Bitmap getImageFromSDCard3(String imagePath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, newOpts);// 此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float ww = 480f;// 这里设置宽度为480f
        float hh = 800f;// 这里设置高度为800f
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(imagePath, newOpts);
        return compressForQuality(bitmap);// 压缩好比例大小后再进行质量压缩
    }

    /**
     * 通过文件名从Assets中获得资源，以位图的形式返回
     *
     * @param context
     * @param fileName 文件名应为assets文件下载绝对路径
     * @return 以位图的形式返回
     */
    public static Bitmap getImageFormAssets(Context context, String fileName) {
        Bitmap bitmap = null;
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open(fileName);
            bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static Bitmap getImageFromUri(Context context, Uri uri) {
        InputStream in = null;
        try {
            BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
            bitmapOptions.inSampleSize = 2;// getPowerOfTwoForSampleRatio(ratio);
            bitmapOptions.inDither = true;// optional
            bitmapOptions.inPreferredConfig = Config.ARGB_8888;// optional
            in = context.getContentResolver().openInputStream(uri);
            return BitmapFactory.decodeStream(in, null, bitmapOptions);
        } catch (FileNotFoundException e) {
        }
        return null;
    }

    /**
     * 质量压缩方法：
     *
     * @param image
     * @return
     */
    public static Bitmap compressForQuality(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024f > 100) { // 循环判断如果压缩后图片是否大于200kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            options -= 5;//图片质量每次减少5
            if (options <= 5) options = 5;//如果图片质量小于5，为保证压缩后的图片质量，图片最底压缩质量为5
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//将压缩后的图片保存到baos中
            if (options == 5) break;//如果图片的质量已降到最低则，不再进行压缩
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    public static Bitmap compressBySize(String path) throws IOException {
        int limit = 800;
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(new File(path)));
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(in, null, options);
        in.close();
        int i = 0;
        Bitmap bitmap = null;
        while (true) {
            if ((options.outWidth >> i <= limit) && (options.outHeight >> i <= limit)) {
                in = new BufferedInputStream(new FileInputStream(new File(path)));
                options.inSampleSize = (int) Math.pow(2.0D, i);
                options.inJustDecodeBounds = false;
                options.inPreferredConfig = Config.RGB_565;
                bitmap = BitmapFactory.decodeStream(in, null, options);
                break;
            }
            i += 1;
        }
        return bitmap;
    }


    /**
     * 图片按比例大小压缩方法
     *
     * @param image
     * @return
     */
    public static Bitmap compressForScale(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        if (baos.toByteArray().length / 1024 > 1024) {// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 50, baos);// 这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float ww = 480;// 这里设置宽度为480f
        float hh = 800;// 这里设置高度为800f
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        newOpts.inJustDecodeBounds = false;
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        return compressForQuality(bitmap);// 压缩好比例大小后再进行质量压缩
    }

    private static float[] carray = new float[20];

    /**
     * 图片去色,返回灰度图片
     *
     * @param bmpOriginal 传入的图片
     * @return 去色后的图片
     */
    public static Bitmap toGrayscale(Bitmap bmpOriginal) {
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();
        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Config.RGB_565);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        paint.setColorFilter(null);
        c.drawBitmap(bmpGrayscale, 0, 0, paint);
        ColorMatrix cm = new ColorMatrix();
        getValueBlackAndWhite();
        cm.set(carray);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayscale;
    }

    private static void getValueSaturation() {
        // 高饱和度
        carray[0] = 5;
        carray[1] = 0;
        carray[2] = 0;
        carray[3] = 0;
        carray[4] = -254;
        carray[5] = 0;
        carray[6] = 5;
        carray[7] = 0;
        carray[8] = 0;
        carray[9] = -254;
        carray[10] = 0;
        carray[11] = 0;
        carray[12] = 5;
        carray[13] = 0;
        carray[14] = -254;
        carray[15] = 0;
        carray[16] = 0;
        carray[17] = 0;
        carray[18] = 5;
        carray[19] = -254;

    }

    private static void getValueBlackAndWhite() {
        // 黑白
        carray[0] = (float) 0.308;
        carray[1] = (float) 0.609;
        carray[2] = (float) 0.082;
        carray[3] = 0;
        carray[4] = 0;
        carray[5] = (float) 0.308;
        carray[6] = (float) 0.609;
        carray[7] = (float) 0.082;
        carray[8] = 0;
        carray[9] = 0;
        carray[10] = (float) 0.308;
        carray[11] = (float) 0.609;
        carray[12] = (float) 0.082;
        carray[13] = 0;
        carray[14] = 0;
        carray[15] = 0;
        carray[16] = 0;
        carray[17] = 0;
        carray[18] = 1;
        carray[19] = 0;
    }

    /**
     * 编辑图片大小，保持图片不变形。
     *
     * @param sourceBitmap
     * @param resetWidth
     * @param resetHeight
     * @return
     */
    public static Bitmap toResetImage(Bitmap sourceBitmap, int resetWidth, int resetHeight) {
        int width = sourceBitmap.getWidth();
        int height = sourceBitmap.getHeight();
        int tmpWidth;
        int tmpHeight;
        float scaleWidth = (float) resetWidth / (float) width;
        float scaleHeight = (float) resetHeight / (float) height;
        float maxTmpScale = scaleWidth >= scaleHeight ? scaleWidth : scaleHeight;
        // 保持不变形
        tmpWidth = (int) (maxTmpScale * width);
        tmpHeight = (int) (maxTmpScale * height);
        Matrix m = new Matrix();
        m.setScale(maxTmpScale, maxTmpScale, tmpWidth, tmpHeight);
        sourceBitmap = Bitmap.createBitmap(sourceBitmap, 0, 0, width, height, m, false);
        // 切图
        int x = (tmpWidth - resetWidth) / 2;
        int y = (tmpHeight - resetHeight) / 2;
        return Bitmap.createBitmap(sourceBitmap, x, y, resetWidth, resetHeight);
    }

    /**
     * 去色同时加圆角
     *
     * @param bmpOriginal 原图
     * @param pixels      圆角弧度
     * @return 修改后的图片
     */
    public static Bitmap toGrayscale(Bitmap bmpOriginal, int pixels) {
        return toRoundCorner(toGrayscale(bmpOriginal), pixels);
    }

    /**
     * 把图片变成圆角
     *
     * @param bitmap 需要修改的图片
     * @param pixels 圆角的弧度
     * @return 圆角图片
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    /**
     * 使圆角功能支持BitampDrawable
     *
     * @param bitmapDrawable
     * @param pixels
     * @return
     */
    public static BitmapDrawable toRoundCorner(BitmapDrawable bitmapDrawable, int pixels) {
        Bitmap bitmap = bitmapDrawable.getBitmap();
        bitmapDrawable = new BitmapDrawable(toRoundCorner(bitmap, pixels));
        return bitmapDrawable;
    }

    /**
     * 代码创建一个selector 代码生成会清除padding
     */
    public static Drawable createStateDrawable(Context context, int bulr, int focus) {
        Drawable bulrDrawable = context.getResources().getDrawable(bulr);
        Drawable focusDrawable = context.getResources().getDrawable(focus);
        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{android.R.attr.state_pressed}, focusDrawable);
        drawable.addState(new int[]{}, bulrDrawable);
        return drawable;
    }

    /**
     * 生成图片倒影
     *
     * @param originalImage
     * @return
     */
    public static Bitmap createReflectedImage(Bitmap originalImage) {
        final int reflectionGap = 4;
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);
        Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0, height / 2, width, height / 2, matrix, false);
        Bitmap bitmapWithReflection = Bitmap.createBitmap(width, (height + height / 2), Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawBitmap(originalImage, 0, 0, null);
        Paint defaultPaint = new Paint();
        canvas.drawRect(0, height, width, height + reflectionGap, defaultPaint);
        canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);
        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, originalImage.getHeight(), 0, bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff, 0x00ffffff, TileMode.MIRROR);
        paint.setShader(shader);
        paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight() + reflectionGap, paint);
        return bitmapWithReflection;
    }

    /**
     * bitmap保存为本地图片
     *
     * @param url
     * @param bitmap
     * @param quality 保存质量
     * @throws IOException
     */
    public static void saveImage(String url, Bitmap bitmap, int quality) throws IOException {
        File file = new File(url);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void deleteImage(String path) {
        File file = new File(path);
        file.deleteOnExit();
    }

    /**
     * 图片资源回收
     *
     * @param bitmap
     */
    public static void distoryBitmap(Bitmap bitmap) {
        if (null != bitmap && bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }

    /**
     * Try to return the absolute file path from the given Uri
     *
     * @param context
     * @param uri
     * @return the file path or null
     */
    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
}
