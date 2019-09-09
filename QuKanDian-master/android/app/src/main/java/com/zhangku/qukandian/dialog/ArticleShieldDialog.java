package com.zhangku.qukandian.dialog;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.adapter.ArticleShieldAdapter;
import com.zhangku.qukandian.base.BaseDialog;
import com.zhangku.qukandian.bean.UserPostBehaviorDto;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.DisLikeNewsProtocol;
import com.zhangku.qukandian.protocol.DisLikeReasonProtocol;
import com.zhangku.qukandian.utils.DeviceUtil;
import com.zhangku.qukandian.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者          xuzhida
 * 创建日期        2018/6/12
 * 分享
 */
public class ArticleShieldDialog extends BaseDialog implements View.OnClickListener {

    private RecyclerView recyclerview;
    private ArticleShieldAdapter adapter;
    private int mPostId;//文章id
    private String zyId;
    private ProgressBar progress;

    private OnClickDelete clickLestener;

    public ArticleShieldDialog(Context context, int mPostId,String zyId) {
        super(context, R.style.zhangku_dialog);
        this.mPostId = mPostId;
        this.zyId = zyId;
    }

    @Override
    protected int centerViewId() {
        return R.layout.dialog_article_shield;
    }

    @Override
    protected void initView() {
//        findViewById(R.id.dialog_out_view).setOnClickListener(this);
        findViewById(R.id.submitTV).setOnClickListener(this);
        progress = findViewById(R.id.progress);
        setToBottom();

        recyclerview = findViewById(R.id.recyclerview);
        GridLayoutManager manager = new GridLayoutManager(getContext(),2);
        recyclerview.setLayoutManager(manager);
        adapter = new ArticleShieldAdapter(new ArrayList<String>());
        recyclerview.setAdapter(adapter);
        getData();
    }

    @Override
    public void show() {
        super.show();
        getData();
    }

    @Override
    protected void release() {

    }

    @Override
    protected void setPosition() {
        Window window = this.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.width = Config.SCREEN_WIDTH;
        window.setWindowAnimations(R.style.popupAnimation);
        window.setAttributes(params);
    }
    private DisLikeReasonProtocol disLikeReasonProtocol;
    private void getData(){
        if(disLikeReasonProtocol == null) {
             disLikeReasonProtocol = new DisLikeReasonProtocol(getContext(), new BaseModel.OnResultListener<List<String>>() {
                @Override
                public void onResultListener(List<String> response) {
                    progress.setVisibility(View.GONE);
                    recyclerview.setVisibility(View.VISIBLE);
                    adapter.setmList(response);
                }

                @Override
                public void onFailureListener(int code, String error) {
                    disLikeReasonProtocol = null;
                }
            });
            disLikeReasonProtocol.postRequest();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.dialog_out_view:
//                dismiss();
//                break;
            case R.id.submitTV:
                List<String> chooseList = adapter.getmChooseList();
                if(chooseList.size() != 0){
                    String msg = "";
                    for(String s:chooseList){
                        msg += ("," + s);
                    }
                    msg = msg.substring(1);

                    UserPostBehaviorDto bean = new UserPostBehaviorDto();
                    bean.setActionType(9);
                    bean.setImei(DeviceUtil.getDeviceInfo(getContext()).imei);
                    bean.setPostId(mPostId);
                    bean.setReason(msg);
                    bean.setUserId(UserManager.getInst().getUserBeam().getId());
                    bean.setZyId(zyId);

                    new DisLikeNewsProtocol(getContext(), bean, new BaseModel.OnResultListener<Object>() {
                        @Override
                        public void onResultListener(Object response) {
                            ToastUtils.showLongToast(mContext,mContext.getString(R.string.dislike_setting));
                            if(clickLestener != null)
                                clickLestener.clickDelete(mPostId);
                            dismiss();
                        }

                        @Override
                        public void onFailureListener(int code, String error) {
                            ToastUtils.showLongToast(mContext,"设置失败:"+error);
                            dismiss();
                        }
                    }).postRequest();
                }else {
                    dismiss();
                }
                break;
        }
    }

//    public void setmPostId(int mPostId, OnClickDelete clickLestener) {
//        this.mPostId = mPostId;
//        this.clickLestener = clickLestener;
//    }

    /**
     *
     * @param mPostId  文章id
     */
    public void setmPostId(int mPostId) {
        this.mPostId = mPostId;
    }


    public interface OnClickDelete{
        void clickDelete(int pos);
    }

    public void setClickLestener(OnClickDelete clickLestener) {
        this.clickLestener = clickLestener;
    }
}
