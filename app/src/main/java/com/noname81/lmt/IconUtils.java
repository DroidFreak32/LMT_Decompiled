package com.noname81.lmt;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageView;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

class IconUtils {
    IconUtils() {
    }

    static String convertDrawableToBase64String(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return Base64.encodeToString(baos.toByteArray(), 0);
    }

    static BitmapDrawable convertBase64StringToDrawable(Context context, String string) {
        if (string == null || string.length() <= 0) {
            return null;
        }
        try {
            byte[] encodeByte = Base64.decode(string, 0);
            return new BitmapDrawable(context.getResources(), BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length));
        } catch (Exception e) {
            return null;
        }
    }

    static Drawable resizeImage(Context context, Drawable image, int size) {
        Resources res = context.getResources();

        boolean noFilter = size == 1;
        if (size > 0 && size <= 2) {
            size = (int) TypedValue.applyDimension(1, 35.0f, res.getDisplayMetrics());
        }
        if (size > 0) {
            Bitmap bitmapimage = Bitmap.createScaledBitmap(getBitmapFromDrawable(image), size, size, true);
            BitmapDrawable bd = new BitmapDrawable(res, bitmapimage);
            if (noFilter) {
                bd.setChangingConfigurations(ActivityInfo.CONFIG_MCC);
            }
            return bd;
        }
        if (noFilter) {
            image.setLevel(1);
        }
        return image;
    }

    static void saveImageToFile(Bitmap bitmap, String path, String fileName) {
        try {
            FileUtils.createFolder(path);
            FileOutputStream outputStream = new FileOutputStream(new File(path + fileName));
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static Bitmap getScaledBitmapFromUri(Context context, Uri uri, int requiredSize) {
        try {
            BitmapFactory.Options optionsJustDecodeBounds = new BitmapFactory.Options();
            int scale = 1;
            optionsJustDecodeBounds.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, optionsJustDecodeBounds);
            int tmpWidth = optionsJustDecodeBounds.outWidth;
            int tmpHeight = optionsJustDecodeBounds.outHeight;
            while (tmpWidth / 2 >= requiredSize && tmpHeight / 2 >= requiredSize) {
                tmpWidth /= 2;
                tmpHeight /= 2;
                scale *= 2;
            }
            BitmapFactory.Options optionsScaledImage = new BitmapFactory.Options();
            optionsScaledImage.inSampleSize = scale;
            return BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, optionsScaledImage);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    static Drawable getDrawable(Context context, int ResID) {
        if (Build.VERSION.SDK_INT >= 21) {
            return context.getResources().getDrawable(ResID, context.getTheme());
        }
        return context.getResources().getDrawable(ResID);
    }

    static Drawable getIconForPieShortPress(Context context) {
        return getDrawable(context, R.drawable.pie_s);
    }

    static Drawable getIconForPieLongPress(Context context) {
        return getDrawable(context, R.drawable.pielp_s);
    }

    static Drawable getIconForGesture(Context context, String gestureName) {
        Resources resources = context.getResources();
        return getDrawable(context, resources.getIdentifier(gestureName + "_s", "drawable", context.getPackageName()));
    }

    static Drawable getIconForISA(Context context) {
        return getDrawable(context, R.drawable.isa_s);
    }

    static Drawable getIconForOK(Context context) {
        return getDrawable(context, R.drawable.ok);
    }

    static Drawable getIconForAction(Context context, Action action, String namePie) {
        return action.getDrawable(context, namePie, 0, 1, true);
    }

    static void setMaxSizeForImageView(Context context, ImageView imageView) {
        Resources res = context.getResources();
        imageView.setAdjustViewBounds(true);
        imageView.setMaxHeight((int) TypedValue.applyDimension(1, 70.0f, res.getDisplayMetrics()));
        imageView.setMaxWidth((int) TypedValue.applyDimension(1, 70.0f, res.getDisplayMetrics()));
    }

    static String getNamePie(int position) {
        return "pie" + position + ".png";
    }

    private static Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (Build.VERSION.SDK_INT >= 26) {
            return getBitmapFromDrawableV26(drawable);
        }
        return ((BitmapDrawable) drawable).getBitmap();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static Bitmap getBitmapFromDrawableV26(Drawable drawable) {
        try {
            if (drawable instanceof BitmapDrawable) {
                return ((BitmapDrawable) drawable).getBitmap();
            }
            if (!(drawable instanceof AdaptiveIconDrawable)) {
                return null;
            }
            AdaptiveIconDrawable adaptiveIconDrawable = (AdaptiveIconDrawable) drawable;
            Bitmap bitmap = Bitmap.createBitmap(adaptiveIconDrawable.getIntrinsicWidth(), adaptiveIconDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            adaptiveIconDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            adaptiveIconDrawable.draw(canvas);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}