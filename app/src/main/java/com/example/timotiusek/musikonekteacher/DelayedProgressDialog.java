package com.example.timotiusek.musikonekteacher;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ProgressBar;

/**
 * Created by wilbe on 17/10/2017.
 */

public class DelayedProgressDialog extends DialogFragment {
    private static final int DELAY_MILLISECOND = 450;
    private static final int SHOW_MIN_MILLISECOND = 300;
    private static final int PROGRESS_CONTENT_SIZE_DP = 80;

    private ProgressBar mProgressBar;
    private boolean startedShowing;
    private long mStartMillisecond;
    private long mStopMillisecond;

    FragmentManager fm;

    // default constructor. Needed so rotation doesn't crash
    public DelayedProgressDialog() {
        super();
    }

    static public DelayedProgressDialog newInstance(){
        return new DelayedProgressDialog();
    }

    @NonNull
    @SuppressLint("InflateParams")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.dialog_progress, null));
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        mProgressBar = (ProgressBar) getDialog().findViewById(R.id.progress);

        if (getDialog().getWindow() != null) {
            int px = (int) (PROGRESS_CONTENT_SIZE_DP * getResources().getDisplayMetrics().density);
            getDialog().getWindow().setLayout(px, px);
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @Override
    public void show(final FragmentManager fm, final String tag) {
        mStartMillisecond = System.currentTimeMillis();
        startedShowing = false;
        mStopMillisecond = Long.MAX_VALUE;
        this.fm =fm;
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mStopMillisecond > System.currentTimeMillis())
                    showDialogAfterDelay(fm, tag);
            }
        }, DELAY_MILLISECOND);
    }

    private void showDialogAfterDelay(FragmentManager fm, String tag) {
        startedShowing = true;
        super.show(fm, tag);
    }

    public void cancel() {
        mStopMillisecond = System.currentTimeMillis();

        if (startedShowing) {
            if (mProgressBar != null) {
                cancelWhenShowing();
            } else {
                cancelWhenNotShowing();
            }
        }
    }

    private void cancelWhenShowing() {
        if (mStopMillisecond < mStartMillisecond + DELAY_MILLISECOND + SHOW_MIN_MILLISECOND) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    dismissAllowingStateLoss();
                }
            }, SHOW_MIN_MILLISECOND);
        } else {
            dismissAllowingStateLoss();
        }
    }

    private void cancelWhenNotShowing() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dismissAllowingStateLoss();
            }
        }, DELAY_MILLISECOND);
    }

    public boolean fragmentManagerIsNull(){
        if(getActivity() != null){
            Log.d("ASDF","fragment manager is there");
            return false;
        }else{
            Log.d("ASDF","fragment manager is not there");
            return true;
        }
    }

}