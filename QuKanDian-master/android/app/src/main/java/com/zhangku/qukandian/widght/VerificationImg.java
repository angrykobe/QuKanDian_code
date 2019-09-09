package com.zhangku.qukandian.widght;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.GetNewVerificationCodeProtocol;
import com.zhangku.qukandian.protocol.GetVerificationCodeProtocol;

/**
 * Created by yuzuoning on 2017/10/19.
 */

public class VerificationImg extends LinearLayout {
    private ImageView mImageView;
    private String mTell = "";
    private GetVerificationCodeProtocol mGetVerificationCodeProtocol;
    private GetNewVerificationCodeProtocol mGetNewVerificationCodeProtocol;
    public VerificationImg(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mImageView = findViewById(R.id.dialog_verification_code_img);

        mImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                getCodeImg(mTell);
            }
        });
    }

    public void getCodeImg(String tell) {
        mTell = tell;
//        if(TextUtils.isEmpty(tell)){
            if (null == mGetVerificationCodeProtocol) {
                mGetVerificationCodeProtocol = new GetVerificationCodeProtocol(getContext(),
                        new BaseModel.OnResultListener<Bitmap>() {
                    @Override
                    public void onResultListener(Bitmap response) {
                        if(null != mImageView){
                            mImageView.setImageBitmap(response);
                        }
                        mGetVerificationCodeProtocol = null;
                    }

                    @Override
                    public void onFailureListener(int code, String error) {
                        mGetVerificationCodeProtocol = null;
                    }
                });
                mGetVerificationCodeProtocol.postRequest();
            }
//        }else {
//            if (null == mGetNewVerificationCodeProtocol) {
//                mGetNewVerificationCodeProtocol = new GetNewVerificationCodeProtocol(getContext(),
//                        tell,new BaseModel.OnResultListener<Bitmap>() {
//                    @Override
//                    public void onResultListener(Bitmap response) {
//                        if(null != mImageView){
//                            mImageView.setImageBitmap(response);
//                        }
//                        mGetNewVerificationCodeProtocol = null;
//                    }
//
//                    @Override
//                    public void onFailureListener(int code, String error) {
//                        mGetNewVerificationCodeProtocol = null;
//                    }
//                });
//                mGetNewVerificationCodeProtocol.postRequest();
//            }
//        }

    }
}
