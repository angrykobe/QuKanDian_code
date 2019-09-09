package com.zhangku.qukandian.BaseNew;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.zhangku.qukandian.R;

import java.io.Serializable;

/**
 * 创建者          xuzhida
 * 创建日期        2018/8/20
 * 你不注释一下？
 */
public class BaseListAct extends BaseAct implements View.OnClickListener {
    public static final String FRAGMENT = "fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initViews() {

        findViewById(R.id.baseBackIV).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView titleTV = findViewById(R.id.baseTitleTV);
        titleTV.setText(""+setTitle());

        Fragment fragment = (Fragment) getIntent().getSerializableExtra(FRAGMENT);
        Bundle bundle = getIntent().getBundleExtra("bundle");
        fragment.setArguments(bundle);
        replaceFragment(fragment,false);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.ac_base_title;
    }

    @Override
    protected String setTitle() {
        return null;
    }

    @Override
    protected void myOnClick(View v) {

    }


    public static void start(Context context, Fragment fragment) {
        Intent intent = new Intent(context, BaseListAct.class);
        Bundle bundle = new Bundle();
        bundle.putBundle("bundle",fragment.getArguments());
        bundle.putSerializable(BaseListAct.FRAGMENT, (Serializable) fragment);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    private void replaceFragment(Fragment fragment, boolean addToBackStack) {
        if (fragment == null) return;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.baseLLayout, fragment);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
    }
}
