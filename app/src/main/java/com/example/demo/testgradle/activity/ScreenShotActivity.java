package com.example.demo.testgradle.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.demo.testgradle.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;

public class ScreenShotActivity extends AppCompatActivity implements View.OnClickListener {
    Context mContext;
    private MediaProjectionManager mMediaProjectionManager;
    private WindowManager mWindowManager;
    private ImageReader mImageReader;
    private DisplayMetrics mMetrics;
    private int mWidth;
    private int mHeight;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
       /*set it to be full screen*/
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mContext = this;
        setContentView(R.layout.activity_tool_bar_demo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("TTOOLL");
        mWindowManager = getWindowManager();
        findViewById(R.id.btn_shot_btn).setOnClickListener(this);
        mMediaProjectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        mWidth = mWindowManager.getDefaultDisplay().getWidth();
        mHeight = mWindowManager.getDefaultDisplay().getHeight();

        mImageReader = ImageReader.newInstance(mWidth, mHeight, ImageFormat.RGB_565, 2);

        mMetrics = new DisplayMetrics();
        mWindowManager.getDefaultDisplay().getMetrics(mMetrics);

//        toolbar.
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {

        Intent intent = mMediaProjectionManager.createScreenCaptureIntent();
        startActivityForResult(intent, 0x66);
//        startActivityForResult(mMediaProjectionManager.createScreenCaptureIntent(), REQUEST_MEDIA_PROJECTION);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {return;}

        int mScreenDensity = mMetrics.densityDpi;
//        1440x2560
        MediaProjection mProjection = mMediaProjectionManager.getMediaProjection(resultCode, data);
        final VirtualDisplay virtualDisplay = mProjection.createVirtualDisplay("screen-mirror",
                mWidth, mHeight, mScreenDensity, DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                mImageReader.getSurface(), null, null);
        Image image = mImageReader.acquireLatestImage();
        if (image == null) {
            return;
        }
        final Image.Plane[] planes = image.getPlanes();
        final ByteBuffer buffer = planes[0].getBuffer();
        int offset = 0;
        int pixelStride = planes[0].getPixelStride();
        int rowStride = planes[0].getRowStride();
        int rowPadding = rowStride - pixelStride * mWidth;
        Bitmap bitmap = Bitmap.createBitmap(mWidth + rowPadding / pixelStride, mHeight, Bitmap.Config.RGB_565);
        bitmap.copyPixelsFromBuffer(buffer);
        image.close();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
        String strDate = dateFormat.format(new java.util.Date());
        String pathImage = Environment.getExternalStorageDirectory().getPath() + "/Pictures/";
        String nameImage = pathImage + strDate + ".png";
        if (bitmap != null) {
            try {
                File fileImage = new File(nameImage);
                if (!fileImage.exists()) {
                    fileImage.createNewFile();
                }
                FileOutputStream out = new FileOutputStream(fileImage);
                if (out != null) {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                    out.flush();
                    out.close();
                    Toast.makeText(this, "get phone's screen succeed", Toast.LENGTH_SHORT).show();
                    Intent media = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    Uri contentUri = Uri.fromFile(fileImage);
                    media.setData(contentUri);
                    getApplicationContext().sendBroadcast(media);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mProjection.stop();
        virtualDisplay.release();
    }
}
