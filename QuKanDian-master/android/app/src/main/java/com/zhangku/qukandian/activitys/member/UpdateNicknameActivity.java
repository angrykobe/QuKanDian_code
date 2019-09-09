package com.zhangku.qukandian.activitys.member;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseTitleActivity;
import com.zhangku.qukandian.bean.UserBean;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.PutUserInfoProtocol;
import com.zhangku.qukandian.utils.ToastUtils;

/**
 * Created by yuzuoning on 2017/3/31.
 * 修改昵称
 */

public class UpdateNicknameActivity extends BaseTitleActivity {
    private String mNickName;
    private EditText mEtNickname;
    private ImageView mIvClear;

    @Override
    protected void initActionBarData() {
        setTitle("用户名");
        addMenuItem(R.string.save);
    }

    @Override
    protected void actionMenuOnClick(int menuId) {
        super.actionMenuOnClick(menuId);
        String name = mEtNickname.getText().toString().trim();
        if (!TextUtils.isEmpty(name)) {
            if (name.length() >= 2 && name.length() <= 20) {
                mDialogPrograss.show();
                UserBean user = UserManager.getInst().getUserBeam();
                user.setNickName(name);
                new PutUserInfoProtocol(this, new BaseModel.OnResultListener() {
                    @Override
                    public void onResultListener(Object response) {
                        mDialogPrograss.dismiss();
                        finish();
                    }

                    @Override
                    public void onFailureListener(int code, String error) {

                    }
                }).putUserInfo(user);
            } else {
                ToastUtils.showLongToast(this, "昵称的长度为2-20");
            }
        } else {
            ToastUtils.showLongToast(this, "昵称不能为空噢");
        }
    }

    @Override
    protected void initViews() {
        if (null != getIntent()) {
            mNickName = getIntent().getExtras().getString(Constants.NICKNAME, "");
        }
        mEtNickname =  findViewById(R.id.activity_update_nickname_layout_edit);
        mIvClear = (ImageView) findViewById(R.id.activity_update_nickname_layout_clear);

        if (TextUtils.isEmpty(mNickName)) {
            mIvClear.setVisibility(View.GONE);
        } else {
            mIvClear.setVisibility(View.VISIBLE);
        }
        mEtNickname.setText(mNickName);
        mEtNickname.setSelection(mNickName.length());

        mEtNickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    mIvClear.setVisibility(View.VISIBLE);
                } else {
                    mIvClear.setVisibility(View.GONE);
                }
            }
        });
        mIvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEtNickname.setText("");
            }
        });
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_update_nickname_layout;
    }

    @Override
    public String setPagerName() {
        return "修改昵称";
    }

    @Override
    protected void releaseRes() {
        super.releaseRes();
        mEtNickname = null;
        mIvClear = null;
    }
}
