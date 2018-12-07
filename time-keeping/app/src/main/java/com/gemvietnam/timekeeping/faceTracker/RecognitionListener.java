package com.gemvietnam.timekeeping.faceTracker;

public interface RecognitionListener {
    void onDone(boolean isSuccess, String embeddings);
}
