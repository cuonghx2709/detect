package com.gemvietnam.utils;


import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

import com.gemvietnam.base.log.Logger;

import java.util.Hashtable;

/**
 * Type face util
 * Created by neo on 3/24/2016.
 */
public class Typefaces {
  private static final String TAG = "Typefaces";

  private static final Hashtable<String, Typeface> CACHE = new Hashtable<>();

  public static Typeface get(Context c, String font, String type) {
    synchronized (CACHE) {
      if (!CACHE.containsKey(font)) {
        try {
          String path = "fonts/" + font + "." + type;
          Typeface t = Typeface.createFromAsset(c.getAssets(), path);
          CACHE.put(font, t);
          Logger.d(TAG, "FONT " + t.toString());
          Logger.d(TAG, "FONT " + path);
        } catch (Exception e) {
          Logger.e(e);
          return null;
        }
      }
      return CACHE.get(font);
    }
  }
}
