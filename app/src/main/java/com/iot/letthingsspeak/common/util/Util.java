package com.iot.letthingsspeak.common.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

import java.util.Random;

public class Util {
    public static String generateRandomChars() {
        int length = 24;
        String candidateChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(candidateChars.charAt(random.nextInt(candidateChars
                    .length())));
        }
        return sb.toString();
    }

    public static int dpToPx(Context mCtx, int dp) {
        Resources r = mCtx.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
