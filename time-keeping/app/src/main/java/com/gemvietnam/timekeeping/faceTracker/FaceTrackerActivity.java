/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gemvietnam.timekeeping.faceTracker;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.gemvietnam.timekeeping.Constant;
import com.gemvietnam.timekeeping.Main2Activity;
import com.gemvietnam.timekeeping.R;
import com.gemvietnam.timekeeping.classify.TensorFlowImageClassifier;
import com.gemvietnam.timekeeping.data.remote.ServiceBuilder;
import com.gemvietnam.timekeeping.data.remote.callback.CommonCallBack;
import com.gemvietnam.timekeeping.data.remote.dto.ResponseDTO;
import com.gemvietnam.timekeeping.data.remote.dto.User;
import com.gemvietnam.timekeeping.env.ImageUtils;
import com.gemvietnam.timekeeping.faceTracker.camera.CameraSourcePreview;
import com.gemvietnam.timekeeping.faceTracker.camera.GraphicOverlay;
import com.gemvietnam.timekeeping.interfaces.Classifier;
import com.gemvietnam.timekeeping.pref.PrefWrapper;
import com.gemvietnam.utils.DialogUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Activity for the face tracker app.  This app detects faces with the rear facing camera, and draws
 * overlay graphics to indicate the position, size, and ID of each face.
 */
public final class FaceTrackerActivity extends AppCompatActivity implements CameraSource.PictureCallback {
    private static final String TAG = "FaceTracker";

    ImageView takePhoto;

    private CameraSource mCameraSource = null;

    private CameraSourcePreview mPreview;
    private GraphicOverlay mGraphicOverlay;

    private static final int RC_HANDLE_GMS = 9001;
    // permission request codes need to be < 256
    private static final int RC_HANDLE_CAMERA_PERM = 2;

    private static final int INPUT_SIZE = 160;
    private static final int IMAGE_MEAN = 117;
    private static final float IMAGE_STD = 1;
    private static final String INPUT_NAME = "input";
    private static final String OUTPUT_NAME = "embeddings";


    private static final String MODEL_FILE = "file:///android_asset/facenet.pb";
    private static final String LABEL_FILE =
            "file:///android_asset/imagenet_comp_graph_label_strings.txt";

    private Classifier classifier;


    public int[] LIST_IMAGE_TEST = {R.drawable.bui_minh_phong_moola, R.drawable.bui_ngoc_truong_java,
            R.drawable.dang_thanh_linh_java, R.drawable.dang_thanh_tuan_android, R.drawable.dao_thai_son_php,
            R.drawable.dinh_thi_thuong_thuong_web, R.drawable.do_minh_hiep, R.drawable.do_nam_khanh_web, R.drawable.do_thi_giang_qa,
            R.drawable.do_van_quang_ios, R.drawable.duong_thanh_tung_ios, R.drawable.duong_xuan_hinh_dotnet, R.drawable.ha_nguyen_anh_android,
            R.drawable.hoang_thi_huyen_qa, R.drawable.img, R.drawable.le_duc_anh, R.drawable.le_huyen_thanh_accountant,
            R.drawable.le_ngoc_phuong_php, R.drawable.le_ngoc_quyen_admin, R.drawable.le_thi_hang_qa, R.drawable.le_van_hung, R.drawable.le_van_hung_java,
            R.drawable.luong_van_tuong_php, R.drawable.mai_dieu_linh_mkt, R.drawable.ngo_thi_xuan_moola, R.drawable.ngoc_thi_anh_aata,
            R.drawable.nguyen_ba_dung_dotnet, R.drawable.nguyen_dieu_hoa_data, R.drawable.nguyen_dinh_manh_php, R.drawable.nguyen_dieu_hoa_data,
            R.drawable.nguyen_dinh_manh_php, R.drawable.nguyen_dinh_tuan_php, R.drawable.nguyen_duc_thuan_ios, R.drawable.nguyen_hai_yen_data,
            R.drawable.nguyen_hoang_hai_php, R.drawable.nguyen_hong_viet_php, R.drawable.nguyen_huu_tu_java, R.drawable.nguyen_le_duan_moola,
            R.drawable.nguyen_ngoc_linh_java, R.drawable.nguyen_quang_anh, R.drawable.nguyen_quang_huy_java, R.drawable.nguyen_quang_huy_java,
            R.drawable.nguyen_the_anh, R.drawable.nguyen_the_hiep_data,
            R.drawable.nguyen_thi_dinh_moola, R.drawable.nguyen_thi_hai_yen_data, R.drawable.nguyen_thi_hai_yen_hr, R.drawable.nguyen_thi_hanh_qa,
            R.drawable.nguyen_thi_hong_hai_qa, R.drawable.nguyen_thi_ngoc_bich_data, R.drawable.nguyen_thi_quyen_data, R.drawable.nguyen_th_thu_nga_aata,
            R.drawable.nguyen_thi_thuy_qa, R.drawable.nguyen_thi_xuyen_data, R.drawable.nguyen_thu_thuy, R.drawable.nguyen_tien_khiem_moola,
            R.drawable.nguyen_trung_kien_java, R.drawable.nguyen_tuan_anh_moola, R.drawable.nguyen_van_linh_data, R.drawable.nguyen_van_thai_php,
            R.drawable.nguyen_van_tien, R.drawable.nguyen_van_toi_web, R.drawable.nguyen_van_vu, R.drawable.nguyen_viet_vinh_php,
            R.drawable.pham_duc_viet_ios, R.drawable.pham_quang_hung_android, R.drawable.pham_thi_dinh_qa, R.drawable.pham_thi_hang_moola, R.drawable.pham_thi_xuan_data,
            R.drawable.pham_tuan_viet_java, R.drawable.pham_tuan_vu_ios, R.drawable.pham_van_cau_moola, R.drawable.pham_van_luong_java, R.drawable.pham_van_quy_web,
            R.drawable.phan_van_khang, R.drawable.phung_thi_thu_data, R.drawable.tran_thi_diem_phuong_qa, R.drawable.tran_thi_le_web, R.drawable.tran_thi_ngoan_data,
            R.drawable.tran_van_binh_moola, R.drawable.tran_van_chi_java, R.drawable.tran_van_nhi_data, R.drawable.tran_xuan_tung_dotnet, R.drawable.trinh_quoc_dat_android,
            R.drawable.trinh_thi_minh_tam_data, R.drawable.trinh_thi_xuan_qa, R.drawable.truong_thi_hanh_qa, R.drawable.vu_quang_son_php,
            R.drawable.vu_xuan_huong_java, R.drawable.vu_xuan_khiem_moola};

    //==============================================================================================
    // Activity Methods
    //==============================================================================================

    /**
     * Initializes the UI and initiates the creation of a face detector.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.main);

        mPreview = findViewById(R.id.preview);
        mGraphicOverlay = findViewById(R.id.faceOverlay);

        // Check for the camera permission before accessing the camera.  If the
        // permission is not granted yet, request permission.
        int rc = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (rc == PackageManager.PERMISSION_GRANTED) {
            createCameraSource();
        } else {
            requestCameraPermission();
        }

        takePhoto = findViewById(R.id.takePhoto);
        takePhoto.setOnClickListener(v -> {
            DialogUtils.showProgressDialog(FaceTrackerActivity.this);
            mCameraSource.takePicture(null, FaceTrackerActivity.this);
        });
    }

    /**
     * Handles the requesting of the camera permission.  This includes
     * showing a "Snackbar" message of why the permission is needed then
     * sending the request.
     */
    private void requestCameraPermission() {
        Log.w(TAG, "Camera permission is not granted. Requesting permission");

        final String[] permissions = new String[]{Manifest.permission.CAMERA};

        if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(this, permissions, RC_HANDLE_CAMERA_PERM);
            return;
        }

        final Activity thisActivity = this;

        View.OnClickListener listener = view -> ActivityCompat.requestPermissions(thisActivity, permissions,
                RC_HANDLE_CAMERA_PERM);

        Snackbar.make(mGraphicOverlay, R.string.permission_camera_rationale,
                Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.ok, listener)
                .show();
    }

    /**
     * Creates and starts the camera.  Note that this uses a higher resolution in comparison
     * to other detection examples to enable the barcode detector to detect small barcodes
     * at long distances.
     */
    private void createCameraSource() {

        Context context = getApplicationContext();
        FaceDetector detector = new FaceDetector.Builder(context)
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .build();

        detector.setProcessor(
                new MultiProcessor.Builder<>(new GraphicFaceTrackerFactory())
                        .build());

        if (!detector.isOperational()) {
            // Note: The first time that an app using face API is installed on a device, GMS will
            // download a native library to the device in order to do detection.  Usually this
            // completes before the app is run for the first time.  But if that download has not yet
            // completed, then the above call will not detect any faces.
            //
            // isOperational() can be used to check if the required native library is currently
            // available.  The detector will automatically become operational once the library
            // download completes on device.
            Log.w(TAG, "Face detector dependencies are not yet available.");
        }

        mCameraSource = new CameraSource.Builder(context, detector)
                .setRequestedPreviewSize(640, 480)
                .setFacing(CameraSource.CAMERA_FACING_FRONT)
                .setRequestedFps(30.0f)
                .build();

        classifier =
                TensorFlowImageClassifier.create(
                        getAssets(),
                        MODEL_FILE,
                        LABEL_FILE,
                        INPUT_SIZE,
                        IMAGE_MEAN,
                        IMAGE_STD,
                        INPUT_NAME,
                        OUTPUT_NAME);


    }

    /**
     * Restarts the camera.
     */
    @Override
    protected void onResume() {
        super.onResume();

        startCameraSource();
    }

    /**
     * Stops the camera.
     */
    @Override
    protected void onPause() {
        super.onPause();
        mPreview.stop();
    }

    /**
     * Releases the resources associated with the camera source, the associated detector, and the
     * rest of the processing pipeline.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCameraSource != null) {
            mCameraSource.release();
        }
    }

    /**
     * Callback for the result from requesting permissions. This method
     * is invoked for every call on {@link #requestPermissions(String[], int)}.
     * <p>
     * <strong>Note:</strong> It is possible that the permissions request interaction
     * with the user is interrupted. In this case you will receive empty permissions
     * and results arrays which should be treated as a cancellation.
     * </p>
     *
     * @param requestCode  The request code passed in {@link #requestPermissions(String[], int)}.
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *                     which is either {@link PackageManager#PERMISSION_GRANTED}
     *                     or {@link PackageManager#PERMISSION_DENIED}. Never null.
     * @see #requestPermissions(String[], int)
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode != RC_HANDLE_CAMERA_PERM) {
            Log.d(TAG, "Got unexpected permission result: " + requestCode);
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Camera permission granted - initialize the camera source");
            // we have permission, so create the camerasource
            createCameraSource();
            return;
        }

        Log.e(TAG, "Permission not granted: results len = " + grantResults.length +
                " Result code = " + (grantResults.length > 0 ? grantResults[0] : "(empty)"));

        DialogInterface.OnClickListener listener = (dialog, id) -> finish();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Face Tracker sample")
                .setMessage(R.string.no_camera_permission)
                .setPositiveButton(R.string.ok, listener)
                .show();
    }

    //==============================================================================================
    // Camera Source Preview
    //==============================================================================================

    /**
     * Starts or restarts the camera source, if it exists.  If the camera source doesn't exist yet
     * (e.g., because onResume was called before the camera source was created), this will be called
     * again when the camera source is created.
     */
    private void startCameraSource() {

        // check that the device has play services available.
        int code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(
                getApplicationContext());
        if (code != ConnectionResult.SUCCESS) {
            Dialog dlg =
                    GoogleApiAvailability.getInstance().getErrorDialog(this, code, RC_HANDLE_GMS);
            dlg.show();
        }

        if (mCameraSource != null) {
            try {
                mPreview.start(mCameraSource, mGraphicOverlay);
            } catch (IOException e) {
                Log.e(TAG, "Unable to start camera source.", e);
                mCameraSource.release();
                mCameraSource = null;
            }
        }
    }

    @Override
    public void onPictureTaken(byte[] bytes) {
//        ArrayList<String> listFace = PrefWrapper.getListVector(getApplicationContext());
//        for (int i = 0 ;i < listFace.size() - 1 ;i++) {
//            for (int j = i+1 ; j < listFace.size() ; j++){
//                String [] e1 = listFace.get(i).split(",");
//                String [] e2 = listFace.get(j).split(",");
//                double dis = 0;
//                for (int k = 0; k < 128; k++){
//                    try {
//                        dis += Math.pow(Double.parseDouble(e1[k]) - Double.parseDouble(e2[k]), 2);
//                    } catch (NumberFormatException e){
//                        continue;
//                    }
////            Log.e("output", outputs[i] + "");
//                }
//                dis = Math.sqrt(dis);
//                if(dis < 0.21) {
//                    Log.e("error  ",  i + "  " + j);
//                }
//            }
//        }


////        new GetVector().execute(ImageUtils.getFaces(getApplicationContext(), bytes));
        try {
//            for (int i = 0; i < LIST_IMAGE_TEST.length; i++) {
//                Bitmap bitmap = Bitmap.createScaledBitmap(ImageUtils.getFaces(getApplicationContext(), LIST_IMAGE_TEST[i]),
//                        INPUT_SIZE, INPUT_SIZE, true);
                Bitmap bitmap = Bitmap.createScaledBitmap(ImageUtils.getFaces(getApplicationContext(), bytes),
                        INPUT_SIZE, INPUT_SIZE, true);
//                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), LIST_IMAGE_TEST[i]);

                classifier.recognizeImage(bitmap, getApplicationContext(), (isSuccess, embeddings) -> {
                    if (PrefWrapper.getFirstLogin(getApplicationContext())) {
                        if (isSuccess) {
                            Toast.makeText(getApplicationContext(), "recognition face succees", Toast.LENGTH_SHORT).show();
                            DialogUtils.dismissProgressDialog();
                            finishLoadFace();
                        } else {
                            Toast.makeText(getApplicationContext(), "recognition face fail", Toast.LENGTH_SHORT).show();
                            DialogUtils.dismissProgressDialog();
                        }

                    } else {
                        updateEmbeddings(embeddings);
                    }

                });
//            }

        } catch (Exception e) {
            DialogUtils.dismissProgressDialog();
            Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();
        }


    }

    void updateEmbeddings(String embeddings) {
        ServiceBuilder.getService().updateEmbeddings(PrefWrapper.getxAuthToken(getApplicationContext()),
                embeddings).enqueue(new CommonCallBack<ResponseDTO>(getApplicationContext()) {
            @Override
            public void onError(String errorCode) {
                super.onError(errorCode);
                DialogUtils.dismissProgressDialog();
                Toast.makeText(getApplicationContext(), errorCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(ResponseDTO data) {
                super.onSuccess(data);
                Toast.makeText(getApplicationContext(), "update success", Toast.LENGTH_SHORT).show();
                PrefWrapper.setIsFirstLogin(getApplicationContext(), true);
                PrefWrapper.setEmbeddings(getApplicationContext(), embeddings);
                DialogUtils.dismissProgressDialog();
                startActivity(new Intent(getApplicationContext(), Main2Activity.class));
                finish();
            }
        });
    }

    void finishLoadFace() {
        Intent intent = new Intent();
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    //==============================================================================================
    // Graphic Face Tracker
    //==============================================================================================

    /**
     * Factory for creating a face tracker to be associated with a new face.  The multiprocessor
     * uses this factory to create face trackers as needed -- one for each individual.
     */
    private class GraphicFaceTrackerFactory implements MultiProcessor.Factory<Face> {
        @Override
        public Tracker<Face> create(Face face) {
            return new GraphicFaceTracker(mGraphicOverlay);
        }
    }

    /**
     * Face tracker for each detected individual. This maintains a face graphic within the app's
     * associated face overlay.
     */
    private class GraphicFaceTracker extends Tracker<Face> {
        private GraphicOverlay mOverlay;
        private FaceGraphic mFaceGraphic;

        GraphicFaceTracker(GraphicOverlay overlay) {
            mOverlay = overlay;
            mFaceGraphic = new FaceGraphic(overlay, bitmap -> {
//                    new GetVector().execute(bitmap);
//                    image.setImageBitmap(bitmap);
//                    classifier.recognizeImage(bitmap, getApplicationContext());


            });
        }

        /**
         * Start tracking the detected face instance within the face overlay.
         */
        @Override
        public void onNewItem(int faceId, Face item) {
            mFaceGraphic.setId(faceId);
        }

        /**
         * Update the position/characteristics of the face within the overlay.
         */
        @Override
        public void onUpdate(FaceDetector.Detections<Face> detectionResults, Face face) {
            mOverlay.add(mFaceGraphic);
//            Bitmap bitmap = ImageUtils.getBitmapFromView(mOverlay);
            mFaceGraphic.updateFace(face);
        }

        /**
         * Hide the graphic when the corresponding face was not detected.  This can happen for
         * intermediate frames temporarily (e.g., if the face was momentarily blocked from
         * view).
         */
        @Override
        public void onMissing(FaceDetector.Detections<Face> detectionResults) {
            mOverlay.remove(mFaceGraphic);
        }

        /**
         * Called when the face is assumed to be gone for good. Remove the graphic annotation from
         * the overlay.
         */
        @Override
        public void onDone() {
            mOverlay.remove(mFaceGraphic);
        }
    }
}
