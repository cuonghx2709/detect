package com.gemvietnam.timekeeping.others;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.zxing.Result;
import com.welcu.android.zxingfragmentlib.BarCodeScannerFragment;


public class ScannerQRCodeFragment extends BarCodeScannerFragment {


    private static final String TAG = ScannerQRCodeFragment.class.getSimpleName();
    private QrCodeResultCallback mCallBack;
    private boolean isSendQrCode = false;

    public static ScannerQRCodeFragment getNewInstance(QrCodeResultCallback callback) {
        ScannerQRCodeFragment frm = new ScannerQRCodeFragment();
        frm.mCallBack = callback;
        return frm;
    }


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setmCallBack(new IResultCallback() {
            @Override
            public void result(Result lastResult) {
                Log.i(TAG, "=========>" + lastResult.toString());
                searchByQrCode(lastResult.toString());
            }
        });

    }

    private void searchByQrCode(String value) {
        if (mCallBack != null) {
            if (!isSendQrCode) {
                isSendQrCode = true;
                mCallBack.onSuccessScanQrCode(value);
            }

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public int getRequestedCameraId() {
        return -1;
    }


    public void setIsRequest(boolean value) {
        isSendQrCode = value;
    }
}
