package com.zhangku.qukandian.dialog;

import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseDialog;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.interfaces.DialogOnDismissListener;

/**
 * 创建者          xuzhida
 * 创建日期        2018/6/12
 * 新手指引
 */
public class DialogNewPeopleGuide extends BaseDialog {
    public final static int imgForTask1[] = {
            R.mipmap.newpeople_task_guide_1
            , R.mipmap.newpeople_task_guide_2
            , R.mipmap.newpeople_task_guide_4
    };
//    public final static int imgForTask2[] = {
//            R.mipmap.newpeople_task_guide_3
//    };
//    private int index = 0;
//    private int Ids[];
    private int mImageId;
    private DialogOnDismissListener mListener;

    public DialogNewPeopleGuide(Context context, int ImageId, DialogOnDismissListener listener) {
        super(context, R.style.zhangku_dialog);
        mImageId = ImageId;
//        this.Ids = UserManager.getInst().getQukandianBean().isShowMask() ? imgForTask1 : imgForTask2;
        mListener = listener;
    }

    @Override
    protected int centerViewId() {
        return R.layout.dialog_imageview;
    }

    @Override
    protected void initView() {
        final ImageView imageview = findViewById(R.id.imageview);
        imageview.setImageResource(mImageId);
        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        setToBottom();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if(mListener!=null) mListener.onDismissListener();
    }

    @Override
    protected void release() {

    }

    @Override
    protected void setPosition() {
        Window window = this.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = Config.SCREEN_WIDTH;
        params.height = Config.SCREEN_HEIGHT;
        window.setWindowAnimations(R.style.nullpopupAnimation);
        window.setAttributes(params);
    }
}
