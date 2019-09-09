package com.zhangku.qukandian.dialog;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseDialog;
import com.zhangku.qukandian.bean.UpLevelResBean;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.PutUpLevelProtocol;
import com.zhangku.qukandian.utils.GlideUtils;
import com.zhangku.qukandian.widght.UserLevelView;

/**
 * 创建者          xuzhida
 * 创建日期        2018/12/19z
 * 你不注释一下？
 */
public class DialogUpLevel extends BaseDialog {

    private UpLevelResBean bean;
    private PutUpLevelProtocol putUpLevelProtocol;

    public DialogUpLevel(Context context, UpLevelResBean response) {
        super(context);
        bean = response;
    }

    @Override
    protected int centerViewId() {
        return R.layout.dialog_up_level;
    }

    @Override
    protected void initView() {
        this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);//设置Dialog背景透明效果

        ImageView mLevelImg = findViewById(R.id.mLevelImg);
        TextView getGoldTV = findViewById(R.id.getGoldTV);
        TextView subtitleTV = findViewById(R.id.subtitleTV);

        UserLevelView.setLevelBigImg(bean.getLevel(),mLevelImg);
        if( bean.getLevelAwardGold() ==0){
            getGoldTV.setVisibility(View.GONE);
        }else{
            getGoldTV.setVisibility(View.VISIBLE);
            getGoldTV.setText("升级奖励："+bean.getLevelAwardGold() + "金币");
        }
        String content = "解锁权限：";
        for (String s : bean.getTqList()) {
            content += s + "\n";
        }
        if (content.length() == 0) {
            subtitleTV.setVisibility(View.GONE);
        } else {
            subtitleTV.setVisibility(View.VISIBLE);
            subtitleTV.setText(content);
        }
        findViewById(R.id.rootView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public void dismiss() {
        if (bean.getLevel() < bean.getTLevel() && putUpLevelProtocol == null) {
            putUpLevelProtocol = new PutUpLevelProtocol(mContext, new BaseModel.OnResultListener<UpLevelResBean>() {
                @Override
                public void onResultListener(UpLevelResBean response) {
                    putUpLevelProtocol = null;
                    if (mContext != null && mContext instanceof Activity) {
                        Activity ac = (Activity) mContext;
                        if (Build.VERSION.SDK_INT >= 17) {
                            if (ac.isDestroyed()) {
                                return;
                            }
                        } else {
                            if (ac.isFinishing()) {
                                return;
                            }
                        }
                    }
                    DialogUpLevel dialogUpLevel = new DialogUpLevel(mContext, response);
                    dialogUpLevel.show();
                }

                @Override
                public void onFailureListener(int code, String error) {
                    putUpLevelProtocol = null;
                }
            });
            putUpLevelProtocol.postRequest();
        } else {
            UserManager.getInst().goldChangeNofity(0);
        }
        super.dismiss();
    }

    @Override
    protected void release() {

    }

    @Override
    protected void setPosition() {

    }
}
