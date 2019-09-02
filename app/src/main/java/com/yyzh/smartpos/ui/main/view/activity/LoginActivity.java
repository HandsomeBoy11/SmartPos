package com.yyzh.smartpos.ui.main.view.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yyzh.commonlibrary.base.BaseActivity;
import com.yyzh.commonlibrary.base.NoViewModel;
import com.yyzh.commonlibrary.utils.SPUtils;
import com.yyzh.smartpos.R;
import com.yyzh.smartpos.utils.SpConst;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by WJ on 2019/8/22.
 */

public class LoginActivity extends BaseActivity<NoViewModel> {
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.usename_input_layout)
    TextInputLayout usenameInputLayout;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.password_input_layout)
    TextInputLayout passwordInputLayout;
    @BindView(R.id.tv_login)
    TextView tvLogin;

    @Override
    protected int getLayoutId() {
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void loadData(Bundle savedInstanceState) {
        String userName = SPUtils.getSharedStringData(this, SpConst.USERNAME);
        String passWord = SPUtils.getSharedStringData(this, SpConst.PASSWORD);
        etUsername.setText(userName);
        etPassword.setText(passWord);

    }


    @OnClick(R.id.tv_login)
    public void onViewClicked() {
        if (checkParams()) {
            SPUtils.setSharedStringData(this, SpConst.USERNAME, etUsername.getText().toString().trim());
            SPUtils.setSharedStringData(this, SpConst.PASSWORD, etPassword.getText().toString().trim());
            SPUtils.setSharedBooleanData(this, SpConst.ISLOGIN, true);
            startAct(MainActivity.class, true);
        }

    }

    /**
     * 校验
     *
     * @return
     */
    private boolean checkParams() {
        String userName = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
            etUsername.setFocusable(true);
            etUsername.setFocusableInTouchMode(true);
            etUsername.requestFocus();
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            etPassword.setFocusable(true);
            etPassword.setFocusableInTouchMode(true);
            etPassword.requestFocus();
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            return false;
        }
        return true;
    }
}
