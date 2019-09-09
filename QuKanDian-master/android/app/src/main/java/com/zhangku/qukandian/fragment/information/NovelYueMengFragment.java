package com.zhangku.qukandian.fragment.information;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iBookStar.views.YmConfig;
import com.iBookStar.views.YmWebView;
import com.zhangku.qukandian.BaseNew.BaseFra;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.manager.UserManager;

/**
 * 2019年5月21日14:02:55
 * ljs
 * 阅盟小说
 */
public class NovelYueMengFragment extends BaseFra {

    private YmWebView webView;
    @Override
    protected void loadData(Context context) {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.view_yuemeng_webview;
    }

    @Override
    protected void initViews(View convertView) {
        webView = convertView.findViewById(R.id.wv);
        //配置落地页TitleBar的背景颜色和文字颜色
        YmConfig.setTitleBarColors(0xffffffff, 0xff000000);
        //设置第三方自定义头
//        YmConfig.setCustomReadHeader(makeCustomHeader());
        YmConfig.setOutUserId(UserManager.getInst().getUserBeam().getId() + "");			//对接金币必须事先调用此接口
        //出现容器和h5页面上事件冲突时，通知h5
        webView.disableSwiperTouch();
        webView.openBookStore();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        webView.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //清楚第三方自定义头
//        YmConfig.setCustomReadHeader(null);
    }

    private View makeCustomHeader(){
        LinearLayout ll = new LinearLayout(getActivity());
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setBackgroundColor(0);
        ll.setPadding(getResources().getDimensionPixelOffset(R.dimen.custom_header_padding_h), getResources().getDimensionPixelOffset(R.dimen.custom_header_padding_v), getResources().getDimensionPixelOffset(R.dimen.custom_header_padding_h), getResources().getDimensionPixelOffset(R.dimen.custom_header_padding_v));
        TextView tv = new TextView(getActivity());
        tv.setTextSize(18f);
        tv.setTextColor(0xff000000);
        tv.setText("77路");
        ll.addView(tv);
        tv = new TextView(getActivity());
        tv.setTextSize(18f);
        tv.setTextColor(0xff000000);
        tv.setText("06:50 / 准点率80%");
        ll.addView(tv);
        return ll;
    }
}
