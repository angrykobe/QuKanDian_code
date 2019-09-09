package com.zhangku.qukandian.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseDialog;

/**
 * Created by yuzuoning on 2017/8/23.
 */

public class PushDialog extends BaseDialog implements View.OnClickListener {
    private TextView mTvTitle;
    private TextView mTvToDetails;
    private TextView mTvIgnore;
    private int mPostId =-1;
    private int mType = -1;

    public PushDialog(Context context) {
        super(context, R.style.zhangku_dialog);
    }

    @Override
    protected int centerViewId() {
        return R.layout.dialog_push_layout;
    }

    @Override
    protected void initView() {
        mTvTitle = (TextView) findViewById(R.id.dlialog_push_titile);
        mTvToDetails = (TextView) findViewById(R.id.dlialog_push_to_details);
        mTvIgnore = (TextView) findViewById(R.id.dlialog_push_ignore);

        mTvToDetails.setOnClickListener(this);
        mTvIgnore.setOnClickListener(this);
    }

    @Override
    protected void release() {

    }

    @Override
    protected void setPosition() {

    }

    public void setContent(int postId, String title,int type) {
        mType = type;
        mPostId = postId;
        mTvTitle.setText(title);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dlialog_push_to_details:
//                if(mType == MiPushBrocaoadcastReceiver.TYPE_ARTICL){
//                    ActivityUtils.startToInformationDetailsActivity(getContext(),mPostId,0);
//                }else if(mType == MiPushBrocaoadcastReceiver.TYPE_VIDEO){
//                    ActivityUtils.startToVideoDetailsActivity(getContext(),mPostId,0);
//                }
                dismiss();
                break;
            case R.id.dlialog_push_ignore:
                dismiss();
                break;
        }
    }
}
