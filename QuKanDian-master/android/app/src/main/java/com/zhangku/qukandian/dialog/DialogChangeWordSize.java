package com.zhangku.qukandian.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioGroup;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseDialog;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

/**
 * 创建者          xuzhida
 * 创建日期        2018/8/23
 * 你不注释一下？
 */
public class DialogChangeWordSize extends BaseDialog {

    private ChangeWordSizeDao listener;
    private int size;

    public DialogChangeWordSize(Context context) {
        super(context, R.style.zhangku_dialog);
    }

    @Override
    protected int centerViewId() {
        return R.layout.dialog_change_word_size;
    }

    @Override
    protected void initView() {
        RadioGroup radioGroup = findViewById(R.id.radioGroup);

        size = UserSharedPreferences.getInstance().getInt(Constants.ART_DETAIL_WORD_SIZE, 2);
        switch (size){
            case 1:
                radioGroup.check(R.id.small);
                break;
            case 2:
                radioGroup.check(R.id.middle);break;
            case 3:
                radioGroup.check(R.id.big);break;
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.small:
                        size = 1;
                        break;
                    case R.id.middle:
                        size = 2;
                        break;
                    case R.id.big:
                        size = 3;
                        break;
                    default:
                        size = 2;
                }

                if (listener != null) listener.changeSize(size);
            }
        });
        findViewById(R.id.dismiss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserSharedPreferences.getInstance().putInt(Constants.ART_DETAIL_WORD_SIZE,size);
                listener.onSave();
                dismiss();
            }
        });
    }



    @Override
    protected void release() {

    }

    @Override
    public void dismiss() {
        if (listener != null) {
            listener.changeSize( UserSharedPreferences.getInstance().getInt(Constants.ART_DETAIL_WORD_SIZE, 2));
        }

        super.dismiss();
    }

    @Override
    public void setCanceledOnTouchOutside(boolean cancel) {
        super.setCanceledOnTouchOutside(cancel);
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

    public void setCheckList(ChangeWordSizeDao listener) {
        this.listener = listener;
    }

    public interface ChangeWordSizeDao {
        //1 small TextSize.SMALLER 2 middle TextSize.NORMAL 3 big TextSize.LARGER
        void changeSize(int size);
        void onSave();
    }
}
