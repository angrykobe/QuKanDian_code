package com.zhangku.qukandian.activitys.me;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.adapter.UpdateWechatBindAdapter;
import com.zhangku.qukandian.base.BaseTitleActivity;
import com.zhangku.qukandian.bean.WeChatBean;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.BindingDefaultProtocol;
import com.zhangku.qukandian.protocol.LoginByWechatProtocol;

import java.util.ArrayList;

/**
 * Created by yuzuoning on 2017/9/15.
 */

public class UpdateWechatBindActivity extends BaseTitleActivity implements UpdateWechatBindAdapter.OnSelectedListener {
    private TextView mTvConfirmBtn;
    private RecyclerView mRecyclerView;
    private UpdateWechatBindAdapter mAdapter;
    private BindingDefaultProtocol mBindingDefaultProtocol;
    private ArrayList<WeChatBean> mDatas;
    private int mSelectedPostion = 0;

    @Override
    protected void initActionBarData() {
        setTitle("修改微信绑定");
    }

    @Override
    protected void initViews() {
        if (null != getIntent().getExtras()) {
            mDatas = getIntent().getExtras().getParcelableArrayList("list");
        }
        mTvConfirmBtn = (TextView) findViewById(R.id.update_wechat_bind_confirm);
        mRecyclerView = (RecyclerView) findViewById(R.id.update_wechat_bind_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new UpdateWechatBindAdapter(this, this, mDatas);
        mRecyclerView.setAdapter(mAdapter);

        mTvConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null == mBindingDefaultProtocol) {
                    mDialogPrograss.show();
                    mBindingDefaultProtocol = new BindingDefaultProtocol(UpdateWechatBindActivity.this, mDatas.get(mSelectedPostion).getOpenId(), mDatas.get(mSelectedPostion).getId(), new BaseModel.OnResultListener<Boolean>() {
                        @Override
                        public void onResultListener(Boolean response) {
                            if(response){
                                new LoginByWechatProtocol(UpdateWechatBindActivity.this, mDatas.get(mSelectedPostion).getOpenId(), new BaseModel.OnResultListener<Object>() {
                                    @Override
                                    public void onResultListener(Object response) {
                                        mDialogPrograss.dismiss();
                                    }

                                    @Override
                                    public void onFailureListener(int code, String error) {
                                        mDialogPrograss.dismiss();

                                    }
                                }).getResult();
                            }
//                            new GetTokenProtocol(UpdateWechatBindActivity.this, new BaseModel.OnResultListener<Boolean>() {
//                                @Override
//                                public void onResultListener(Boolean response) {
//
//                                    new LoginByWechatProtocol(UpdateWechatBindActivity.this, mDatas.get(mSelectedPostion).getOpenId(), new BaseModel.OnResultListener<Object>() {
//                                        @Override
//                                        public void onResultListener(Object response) {
//                                            mDialogPrograss.dismiss();
//                                        }
//
//                                        @Override
//                                        public void onFailureListener(int code, String error) {
//                                            mDialogPrograss.dismiss();
//
//                                        }
//                                    }).getResult();
//
//                                }
//
//                                @Override
//                                public void onFailureListener(int code, String error) {
//
//                                }
//                            }).getUserTokenWX(mDatas.get(mSelectedPostion).getId()+"", mDatas.get(mSelectedPostion).getOpenId());
                            mBindingDefaultProtocol = null;
                        }

                        @Override
                        public void onFailureListener(int code, String error) {
                            mBindingDefaultProtocol = null;
                        }
                    });
                    mBindingDefaultProtocol.postRequest();
                }
            }
        });
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_update_bind_wechat;
    }

    @Override
    public String setPagerName() {
        return null;
    }

    @Override
    public void onSelectedListener(int position) {
        mSelectedPostion = position;
        for (int i = 0; i < mDatas.size(); i++) {
            if (position == i) {
                mDatas.get(i).setSelected(true);
            } else {
                mDatas.get(i).setSelected(false);
            }
        }
        mAdapter.notifyDataSetChanged();
    }
}
