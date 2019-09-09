package com.zhangku.qukandian.activitys.task;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.cmcm.cmgame.CmGameSdk;
import com.cmcm.cmgame.GameView;
import com.cmcm.cmgame.IAppCallback;
import com.cmcm.cmgame.IGamePlayTimeCallback;
import com.zhangku.qukandian.R;

public class QuBaoGameActivity extends AppCompatActivity implements IAppCallback, IGamePlayTimeCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qubao_game);

        int apilevel = Build.VERSION.SDK_INT;

        // 目前只支持anrdoid 5.0或以上
        if  (apilevel < 21) {
            Toast.makeText(this, "不支持低版本，仅支持android 5.0或以上版本!", Toast.LENGTH_LONG).show();
        }

        GameView gameTabsClassifyView = (GameView) findViewById(R.id.gameView);
        gameTabsClassifyView.inflate(this);
        // 初始化小游戏 sdk 的账号数据，用于存储游戏内部的用户数据，
        // 为避免数据异常，这个方法建议在小游戏列表页面展现前（可以是二级页面）才调用
        CmGameSdk.INSTANCE.initCmGameAccount();
        CmGameSdk.INSTANCE.setGameClickCallback(this);
        CmGameSdk.INSTANCE.setGamePlayTimeCallback(this);
    }

    /**
     * @param gameName 点击游戏名
     *        gameID 游戏ID
     */
    @Override
    public void gameClickCallback(String gameName, String gameID) {
        Log.d("cmgamesdk_Main2Activity", gameID + "----" + gameName );
    }

    /**
     * @param bVideoComplete 是否播放完成
     */
    @Override
    public void gameADRewardVideoCallBack(boolean bVideoComplete) {
        Log.d("cmgamesdk_Main2Activity", "bVideoComplete : " + bVideoComplete );
    }

    /**
     * @param playTimeInSeconds 玩游戏时长，单位为秒
     */
    @Override
    public void gamePlayTimeCallback(String gameId, int playTimeInSeconds) {
        Log.e("cmgamesdk_Main2Activity", "play game ：" + gameId + "playTimeInSeconds : " + playTimeInSeconds);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        CmGameSdk.INSTANCE.removeGameClickCallback();
        // 由于 sdk 内部实现了一些状态通知，需要在页面结束展示的时机将其结束，避免内存泄漏
        CmGameSdk.onPageDestroy();
    }
}
