package com.me.mseotsanyana.mande.Util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by mseotsanyana on 2018/01/31.
 */

public class cBitmap{
    private static final String TAG = "cBitmap";


    public cBitmap(Bitmap bitmap) {

    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
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

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    // Custom method to get assets folder image as bitmap
    static public byte[] getBitmapFromAssets(Context context, String fileName){

        AssetManager assetManager = context.getAssets();
        InputStream is = null;

        try{
            is = assetManager.open(fileName);
        }catch(IOException e){
            e.printStackTrace();
        }

        // resize the image
        Bitmap bitmap = resizeBitmap(BitmapFactory.decodeStream(is),180);

        // make a circular image
        //Bitmap circularBitmap = getRoundedCornerBitmap(bitmap, 100);

        return getUserPhoto(bitmap);
    }


    //Convert and resize our image to 400dp for faster uploading our images to DB
    static public Bitmap decodeByteArray(byte[] outImage) {
        try {

            BitmapFactory.Options options = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeByteArray(outImage, 0, outImage.length, options);

            return bitmap;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //Convert and resize our image to 400dp for faster uploading our images to DB
    static public Bitmap resizeBitmap(Bitmap inImage, int REQUIRED_SIZE) {
        try {
            Bitmap outImage = null;
            if (inImage != null) {
                //Bitmap tmpImage = makeRound(inImage);
                // scaling the bitmap in respect to the width of the device to get

                // variant height for different android //devices
                int heightofBitMap = inImage.getHeight();
                int widthofBitMap  = inImage.getWidth();

                heightofBitMap = REQUIRED_SIZE * heightofBitMap / widthofBitMap;
                widthofBitMap  = REQUIRED_SIZE;

                // Scaling the bitmap according to new height and width
                outImage = Bitmap.createScaledBitmap(inImage, widthofBitMap, heightofBitMap, true);
            }

            return outImage;

            //return makeRound(resizedBitmap);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //Convert bitmap to bytes
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    static private byte[] getUserPhoto(Bitmap b){

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 0, bos);
        return bos.toByteArray();
    }
}
