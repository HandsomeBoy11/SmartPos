package com.yyzh.smartpos.ui.main.view.activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.yyzh.commonlibrary.utils.SPUtils;
import com.yyzh.smartpos.R;
import com.yyzh.smartpos.utils.SpConst;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class SplashActivity extends AppCompatActivity {
    private static final int REQUEST_PERMISSION = 1001;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_des)
    TextView txtDes;
    private ObjectAnimator alphaAnim;
    private ObjectAnimator desAnim;
    private ObjectAnimator nameAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermission();
        } else {
            onPermissionGranted();
        }
    }

    private void requestPermission() {
        if (checkPermissions()) {
            onPermissionGranted();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private boolean checkPermissions() {
        int accessCamera = checkSelfPermission(Manifest.permission.CAMERA);
        int accessCoarseLocation = checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
        int accessFineLocation = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
        int accessReadStorage = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
        int accessWriteStorge = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int accessPhone = checkSelfPermission(Manifest.permission.CALL_PHONE);
        int accessReadPhoneState = checkSelfPermission(Manifest.permission.READ_PHONE_STATE);

        List<String> pendingPermissions = new ArrayList<>();

        if (accessCamera != PackageManager.PERMISSION_GRANTED) {
            pendingPermissions.add(Manifest.permission.CAMERA);
        }
        if (accessCoarseLocation != PackageManager.PERMISSION_GRANTED) {
            pendingPermissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (accessFineLocation != PackageManager.PERMISSION_GRANTED) {
            pendingPermissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (accessReadStorage != PackageManager.PERMISSION_GRANTED) {
            pendingPermissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (accessWriteStorge != PackageManager.PERMISSION_GRANTED) {
            pendingPermissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (accessPhone != PackageManager.PERMISSION_GRANTED) {
            pendingPermissions.add(Manifest.permission.CALL_PHONE);
        }
        if (accessReadPhoneState != PackageManager.PERMISSION_GRANTED) {
            pendingPermissions.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (!pendingPermissions.isEmpty()) {
            String[] strRequestPermission = pendingPermissions.toArray(new String[0]);
            requestPermissions(strRequestPermission, REQUEST_PERMISSION);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            Log.e("permision",
                    "onRequestPermissionsResult(): permissions = ${permissions.contentToString()}, " +
                            "grantResults = ${grantResults.contentToString()}"
            );
        }
        onPermissionGranted();
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void onPermissionGranted() {
        doStart();
    }

    private void doStart() {
        alphaAnim = ObjectAnimator.ofFloat(txtDes, "alpha", 1f);
        desAnim = ObjectAnimator.ofFloat(txtDes, "translationX", -500f, 0f);
        desAnim.setDuration(700);
        desAnim.setInterpolator(new DecelerateInterpolator());
        desAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                txtName.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                txtName.setVisibility(View.INVISIBLE);
            }


        });

        nameAnim = ObjectAnimator.ofFloat(txtName, "translationY", -500f, 0f);
        nameAnim.setDuration(2000);
        nameAnim.setInterpolator(new BounceInterpolator());
        nameAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Observable.timer(500, TimeUnit.MILLISECONDS).subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Boolean isLogin = SPUtils.getSharedBooleanData(SplashActivity.this, SpConst.ISLOGIN);
                        if (isLogin) {//判断是否登录
                            goMain();
                        } else {
                            goLogin();
                        }
                    }
                });
            }
        });

        AnimatorSet animSet = new AnimatorSet();
        animSet.play(desAnim).with(alphaAnim).before(nameAnim);
        animSet.start();
    }

    /**
     * 去主页
     */
    private void goMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    /**
     * 去登录页面
     */
    private void goLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
