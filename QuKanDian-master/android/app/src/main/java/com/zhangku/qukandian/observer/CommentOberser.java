package com.zhangku.qukandian.observer;

import java.util.ArrayList;

/**
 * Created by yuzuoning on 2017/9/14.
 */

public class CommentOberser {
    public static final int TYPE_PRAISE = 0;
    public static final int TYPE_COMMENT = 1;

    private CommentOberser() {
    }

    private ArrayList<OnCommentListener> mOnCommentListeners = new ArrayList<>();
    private static CommentOberser mCommentOberser = null;

    public static CommentOberser getInstance() {
        if (null == mCommentOberser) {
            synchronized (CommentOberser.class) {
                if (null == mCommentOberser) {
                    mCommentOberser = new CommentOberser();
                }
            }
        }
        return mCommentOberser;
    }

    public void addOnCommentListener(OnCommentListener onCommentListener) {
        if (!mOnCommentListeners.contains(onCommentListener)) {
            mOnCommentListeners.add(onCommentListener);
        }
    }

    public void removeOnCommentListener(OnCommentListener onCommentListener) {
        if (mOnCommentListeners.contains(onCommentListener)) {
            mOnCommentListeners.remove(onCommentListener);
        }
    }

    public void notifys(int type) {
        for (OnCommentListener o : mOnCommentListeners) {
            if (null != o) {
                o.onCommentListener(type);
            }
        }
    }

    public interface OnCommentListener {
        void onCommentListener(int type);
    }

}
