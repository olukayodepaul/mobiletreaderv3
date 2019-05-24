package com.mobile.mtrader.util;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import java.net.InetAddress;

public class AppUtil {

    static Toast mToast;

    public static void showToast(Context context, String statusMsg) {
        if (mToast != null) mToast.cancel();
        mToast = Toast.makeText(context, statusMsg, Toast.LENGTH_SHORT);
        mToast.show();
    }

    public static void showAlertDialog(Context context, String title, String msg, String buttons) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(title)
                .setCancelable(false)
                .setMessage(msg)
                .setNegativeButton(buttons, (paramDialogInterface, paramInt) -> {
                    paramDialogInterface.dismiss();
                });
        dialog.show();
    }

    public static void showAlertDialogWithIntent(Context context, String title, String msg, String buttons, Class object) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(title)
                .setMessage(msg)
                .setCancelable(false)
                .setNegativeButton(buttons, (DialogInterface paramDialogInterface, int paramInt) -> {
                    Intent intent = new Intent(context, object);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                });
        dialog.show();
    }

    public static boolean checkConnection(Context context) {
        return ((ConnectivityManager) context.getSystemService (Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }

    public static boolean checkConnections(Context context) {

        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

        if (activeNetworkInfo != null) { // connected to the internet
            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
                return true;
            } else if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                // connected to the mobile provider's data plan
                return true;
            }
        }
        return false;
    }

    public static boolean insideRadius(double custLat, double custLng, double curLat, double curLng) { 
        int ky = 40000000/360;//40000/360;
        double kx = Math.cos(Math.PI*custLat/180) * ky;
        double dx = Math.abs(custLng-curLng)*kx;
        double dy = Math.abs(custLat-curLat)*ky;
        return Math.sqrt(dx*dx+dy*dy)<=5;
    }

}