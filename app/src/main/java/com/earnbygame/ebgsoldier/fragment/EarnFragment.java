package com.earnbygame.ebgsoldier.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.earnbygame.ebgsoldier.R;
import com.earnbygame.ebgsoldier.activity.DashActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EarnFragment extends Fragment {

    private View viewLayout;
    private DashActivity activity;
    private FragmentManager fragmentManager;
    private Context context;

    @BindView(R.id.tv_point)
    public TextView mPoints;
    @BindView(R.id.tv_ref_code)
    public TextView mRefCode;
    @BindView(R.id.tv_ref_code_below)
    public TextView mRefCodeBelow;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewLayout = inflater.inflate(R.layout.frag_earn, container, false);
        ButterKnife.bind(this, viewLayout);
        activity = (DashActivity) getActivity();
        fragmentManager = activity.getSupportFragmentManager();

        mPoints.setText("₹ 0");
        mRefCode.setText("DAN6CS");
        mRefCodeBelow.setText("DAN6CS");

        return viewLayout;
    }

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            if (getView() != null) {
                getView().setFocusableInTouchMode(true);
                getView().requestFocus();
                getView().setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
                            fragmentManager.popBackStack();
                        }
                        return true;
                    }
                });
            }
        } catch (Exception e) {
            Log.e("error",""+e);
        }
    }

    @OnClick({R.id.iv_fb,R.id.iv_whatsApp,R.id.iv_more})
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.iv_fb:
                    shareWithFb(mRefCode.getText().toString());
                    break;
                case R.id.iv_whatsApp:
                    shareWithWhatsApp(mRefCode.getText().toString());
                    break;
                case R.id.iv_more:
                    shareAll(mRefCode.getText().toString());
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void shareWithFb(String shareUrl) {
        Intent intent = null;
        intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, shareUrl);

        boolean facebookAppFound = false;
        List<ResolveInfo> matches = context.getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo info : matches) {
            if (info.activityInfo.packageName.toLowerCase().startsWith("com.facebook.katana")) {
                assert intent != null;
                intent.setPackage(info.activityInfo.packageName);
                facebookAppFound = true;
                break;
            }
        }
        if (!facebookAppFound) {
            String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=" + shareUrl;
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));

        }
        startActivity(intent);
    }

    private void shareWithWhatsApp(String shareUrl) {
        Intent intent = null;
        intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, shareUrl);

        boolean facebookAppFound = false;
        List<ResolveInfo> matches = context.getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo info : matches) {
            if (info.activityInfo.packageName.toLowerCase().startsWith("com.whatsapp")) {
                assert intent != null;
                intent.setPackage(info.activityInfo.packageName);
                facebookAppFound = true;
                break;
            }
        }
        // As fallback, launch sharer.php in a browser
        if (!facebookAppFound) {
            Toast.makeText(context,"Sorry you don't have WhatsApp application",Toast.LENGTH_SHORT).show();
        }
        startActivity(intent);
    }

    private void shareAll(String shareUrl){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "CourseKaro");
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareUrl);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }
}
