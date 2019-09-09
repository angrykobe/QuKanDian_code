package com.zhangku.qukandian.widght;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.utils.DisplayUtils;
import com.zhangku.qukandian.utils.LayoutInflaterUtils;


public class ActionBarLayout extends LinearLayout {

    private TextView mTitleTv;
    private ImageView mBackIcon;
    private LinearLayout mMenuLayout;
    private LinearLayout mLayout;

    private OnActionBarMenuAction mAction;
    private SparseArray<View> mMenuItems;

    public ActionBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mMenuItems = new SparseArray<View>();
        setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
//        ExitActivityObserver.getInst().addExitActivityObserverAction(context, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView(this);
    }

    private void initView(View converView) {
        mLayout = (LinearLayout) converView.findViewById(R.id.layout_title_layout);
        mTitleTv = (TextView) converView.findViewById(R.id.layout_title_title);
        mBackIcon = (ImageView) converView.findViewById(R.id.layout_title_back);
        mMenuLayout = (LinearLayout) converView.findViewById(R.id.layout_title_menu);
    }

    public void setThemColor(int color){
        mLayout.setBackgroundColor(color);
    }

    public void setTitle(CharSequence text) {
        mTitleTv.setText(text);
    }

    public void setTitle(int resId) {
        setTitle(getResources().getString(resId));
    }

    public void setOnBackListener(OnClickListener clickListener) {
        mBackIcon.setVisibility(clickListener == null ? View.INVISIBLE : View.VISIBLE);
        if (mBackIcon != null) {
            mBackIcon.setOnClickListener(clickListener);
            updateTitleMargin();//更新title的Margin
        }
    }


    public void showBackIcon(int visibility) {
        mBackIcon.setVisibility(visibility);
        updateTitleMargin();//更新title的Margin
    }

    public interface OnSearchMenuAction {
        void onSearchAction(String keyWord);
    }

    public void addMenu(int... resIds) {
        for (int i = 0; i < resIds.length; i++) {
            int resId = resIds[i];
            addMenu(resId);
        }
    }

    public View addMenu(int resId) {
        View view = null;
        try {//如果为文字按钮
            String name = getResources().getResourceTypeName(resId);
            if (name.equals("string")) {
                String text = getResources().getString(resId);
                view = addTextMenu(text, resId);
            } else if (name.equals("drawable") || name.equals("mipmap")) {
                Drawable drawable = getResources().getDrawable(resId);
                view = addDrawableMenu(drawable, resId);
            }
            updateTitleMargin();//更新title的Margin
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    public void setTitleColor(int color) {
        mTitleTv.setTextColor(color);
    }

    public void addMenu(final int viewId, View view) {
        mMenuItems.put(viewId, view);
        mMenuLayout.addView(view);
        view.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mAction != null) {
                    mAction.onMenuAction(viewId);
                }
            }
        });
        updateTitleMargin();//更新title的Margin
    }

    private View addTextMenu(String text, final int menuId) {
        View menuLayout = LayoutInflaterUtils.inflateView(getContext(), R.layout.layout_menu_text);
        mMenuItems.put(menuId, menuLayout);
        mMenuLayout.addView(menuLayout);
        menuLayout.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mAction != null) {
                    mAction.onMenuAction(menuId);
                }
            }
        });

        TextView mMenuItemView = (TextView) menuLayout.findViewById(R.id.layout_menu_text);
        mMenuItemView.setText(text);
        updateTitleMargin();//更新title的Margin
        return mMenuItemView;
    }

    private View addDrawableMenu(Drawable drawable, final int menuId) {
        View menuLayout = LayoutInflaterUtils.inflateView(getContext(), R.layout.layout_menu_icon);
        mMenuItems.put(menuId, menuLayout);
        mMenuLayout.addView(menuLayout);
        menuLayout.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mAction != null) {
                    mAction.onMenuAction(menuId);
                }
            }
        });

        ImageView mMenuIcon = (ImageView) menuLayout.findViewById(R.id.layout_menu_icon);
        mMenuIcon.setImageDrawable(drawable);
        updateTitleMargin();//更新title的Margin
        return mMenuIcon;
    }

    public void setOnActionBarMenuAction(OnActionBarMenuAction mAction) {
        this.mAction = mAction;
    }

    public interface OnActionBarMenuAction {

        /**
         * @param menuId 从左往右
         */
        void onMenuAction(int menuId);
    }

    public void attachToActivity(Activity activity) {
        ViewGroup viewGroup = (ViewGroup) activity.findViewById(android.R.id.content);
        View contentView = viewGroup.getChildAt(0);
        viewGroup.removeView(contentView);
        attachView(contentView);
        viewGroup.addView(this);
    }

    public void attachView(View view) {
        addView(view);
    }

//    @Override
//    public void onActivityDestory() {
//        setOnClickListener(null);
//        mTitleTv = null;
//        mBackIcon = null;
//        if (mMenuLayout != null) {
//            mMenuLayout.removeAllViews();
//            mMenuLayout = null;
//        }
//
//        if (mMenuItems != null) {
//            mMenuItems.clear();
//            mMenuItems = null;
//        }
//        mAction = null;
//    }

    public void updateMenuItem(int dstId, int resId) {
        View menuLayout = mMenuItems.get(dstId);
        mMenuLayout.removeView(menuLayout);
        mMenuItems.remove(dstId);
        if (mMenuItems.get(resId) == null) {
            addMenu(resId);
        }
    }

    public void removeMenuItem(int resId) {
        View menuLayout = mMenuItems.get(resId);
        mMenuLayout.removeView(menuLayout);
        mMenuItems.remove(resId);
    }

    public void removeMenuItem(int... dstId) {
        for (int i = 0; i < dstId.length; i++) {
            View menuLayout = mMenuItems.get(dstId[i]);
            mMenuLayout.removeView(menuLayout);
            mMenuItems.remove(dstId[i]);
        }
        updateTitleMargin();//更新title的Margin
    }

    private void updateTitleMargin() {//根据菜单个数的不同，更新title的margin
        int margin = 0;

        if ((mBackIcon != null) && (mBackIcon.getVisibility() == View.VISIBLE)) {
            margin = DisplayUtils.dip2px(getContext(), 48);
        }
        if (mMenuLayout != null) {
            int childCount = mMenuLayout.getChildCount();
            if (childCount > 0) {
                margin = DisplayUtils.dip2px(getContext(), childCount * 48);
            }
        }

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(margin, 0, margin, 0);
        mTitleTv.setLayoutParams(layoutParams);
    }

}
