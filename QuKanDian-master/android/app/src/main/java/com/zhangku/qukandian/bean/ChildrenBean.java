package com.zhangku.qukandian.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yuzuoning on 2017/9/14.
 */

public class ChildrenBean implements Parcelable {
    /**
     * userId : 10045
     * nickName : 余祚宁
     * avatarUrl : http://qutout.oss-cn-shanghai.aliyuncs.com/user/avatar/2/img_10045.jpg?t=ad89db9c92e84ada88b4bb193771baa6
     * postId : 121119
     * parentId : 18
     * children : []
     * content : 太对了
     * likeNum : 0
     * creationTime : 2017-09-14T10:23:22.633
     * creationTimeMemo : 4秒前
     * curLevel : 2
     * id : 19
     */

    private int userId;
    private String nickName;
    private String avatarUrl;
    private int postId;
    private int parentId;
    private String content;
    private int likeNum;
    private String creationTime;
    private String creationTimeMemo;
    private int curLevel;
    private int id;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getCreationTimeMemo() {
        return creationTimeMemo;
    }

    public void setCreationTimeMemo(String creationTimeMemo) {
        this.creationTimeMemo = creationTimeMemo;
    }

    public int getCurLevel() {
        return curLevel;
    }

    public void setCurLevel(int curLevel) {
        this.curLevel = curLevel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.userId);
        dest.writeString(this.nickName);
        dest.writeString(this.avatarUrl);
        dest.writeInt(this.postId);
        dest.writeInt(this.parentId);
        dest.writeString(this.content);
        dest.writeInt(this.likeNum);
        dest.writeString(this.creationTime);
        dest.writeString(this.creationTimeMemo);
        dest.writeInt(this.curLevel);
        dest.writeInt(this.id);
    }

    public ChildrenBean() {
    }

    protected ChildrenBean(Parcel in) {
        this.userId = in.readInt();
        this.nickName = in.readString();
        this.avatarUrl = in.readString();
        this.postId = in.readInt();
        this.parentId = in.readInt();
        this.content = in.readString();
        this.likeNum = in.readInt();
        this.creationTime = in.readString();
        this.creationTimeMemo = in.readString();
        this.curLevel = in.readInt();
        this.id = in.readInt();
    }

    public static final Parcelable.Creator<ChildrenBean> CREATOR = new Parcelable.Creator<ChildrenBean>() {
        @Override
        public ChildrenBean createFromParcel(Parcel source) {
            return new ChildrenBean(source);
        }

        @Override
        public ChildrenBean[] newArray(int size) {
            return new ChildrenBean[size];
        }
    };
}
