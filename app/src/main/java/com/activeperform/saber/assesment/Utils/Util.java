package com.activeperform.saber.assesment.Utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.activeperform.saber.assesment.Models.BikeStation;

import java.util.ArrayList;
import java.util.List;

public class Util {

    public static Bitmap scaleImage(Resources res, int id, int lessSideSize) {
        Bitmap b = null;
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;

        BitmapFactory.decodeResource(res, id, o);

        float sc = 0.0f;
        int scale = 1;
        // if image height is greater than width
        if (o.outHeight > o.outWidth) {
            sc = o.outHeight / lessSideSize;
            scale = Math.round(sc);
        }
        // if image width is greater than height
        else {
            sc = o.outWidth / lessSideSize;
            scale = Math.round(sc);
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        b = BitmapFactory.decodeResource(res, id, o2);
        return b;
    }

    public static List<BikeStation> cloneList(List<BikeStation> list) {
        List<BikeStation> clone = new ArrayList<BikeStation>(list.size());
        for (BikeStation item : list) clone.add(item);
        return clone;
    }
}
