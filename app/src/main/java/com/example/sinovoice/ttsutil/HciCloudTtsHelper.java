package com.example.sinovoice.ttsutil;

import android.content.Context;
import android.util.Log;

import com.sinovoice.hcicloudsdk.android.tts.player.TTSPlayer;
import com.sinovoice.hcicloudsdk.common.tts.TtsConfig;
import com.sinovoice.hcicloudsdk.common.tts.TtsInitParam;
import com.sinovoice.hcicloudsdk.player.TTSCommonPlayer;
import com.sinovoice.hcicloudsdk.player.TTSPlayerListener;

/**
 * Created by miaochangchun on 2016/11/28.
 */
public class HciCloudTtsHelper {
    private static final String TAG = HciCloudTtsHelper.class.getSimpleName();
    private static HciCloudTtsHelper mHciCloudTtsHelper = null;
    private TTSPlayer mTtsPlayer;

    private HciCloudTtsHelper(){
    }

    /**
     * 获取初始化对象
     * @return
     */
    public static HciCloudTtsHelper getInstance() {
        if (mHciCloudTtsHelper == null) {
            return  new HciCloudTtsHelper();
        }
        return mHciCloudTtsHelper;
    }

    /**
     * 播放器初始化
     * @param context   上下文
     * @param initCapkeys   需要初始化的capkey，可以设置为多个，中间以；间隔
     * @return true播放器初始化成功，false初始化失败
     */
    public boolean initTtsPlayer(Context context, String initCapkeys){
        mTtsPlayer = new TTSPlayer();
        String strConfig = getTtsInitParam(context, initCapkeys);
        mTtsPlayer.init(strConfig, new TTSEventProcess());
        //设置使用AudioFocus机制
        mTtsPlayer.setContext(context);
        //mTtsPlayer.setPlayerRouteFlag(0); 可以再次设置播放器的通道
        Log.d(TAG, "initTtsPlayer Success.");
        if (mTtsPlayer.getPlayerState() == TTSCommonPlayer.PLAYER_STATE_IDLE) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 开始播放
     * @param text  需要播放的文本
     * @param capkey    发音人选择
     */
    public void playTtsPlayer(String text, String capkey) {
        String strConfig = getTtsSynthConfig(capkey);

        Log.d(TAG, "mTtsPlayer = " + mTtsPlayer);
        if (mTtsPlayer.getPlayerState() == TTSCommonPlayer.PLAYER_STATE_PLAYING || mTtsPlayer.getPlayerState() == TTSCommonPlayer.PLAYER_STATE_PAUSE) {
            mTtsPlayer.stop();
        }
        if (mTtsPlayer.getPlayerState() == TTSCommonPlayer.PLAYER_STATE_IDLE) {
            mTtsPlayer.play(text, strConfig);
        }
    }

    /**
     * 暂停播放
     */
    public void pauseTtsPlayer(){
        if (mTtsPlayer.getPlayerState() == TTSCommonPlayer.PLAYER_STATE_PLAYING) {
            mTtsPlayer.pause();
        }
    }

    /**
     * 恢复播放
     */
    public void resumeTtsPlayer(){
        if (mTtsPlayer.getPlayerState() == TTSCommonPlayer.PLAYER_STATE_PAUSE) {
            mTtsPlayer.resume();
        }
    }

    /**
     * 停止播放
     */
    public void stopTtsPlayer(){
        if (mTtsPlayer.canStop()){
            mTtsPlayer.stop();
        }
    }

    /**
     * 公有云配置参数
     * @param capkey
     * @return
     */
    private String getTtsSynthConfig(String capkey) {
        TtsConfig ttsConfig = new TtsConfig();
        ttsConfig.addParam(TtsConfig.SessionConfig.PARAM_KEY_CAP_KEY, capkey);
        ttsConfig.addParam(TtsConfig.BasicConfig.PARAM_KEY_AUDIO_FORMAT, "pcm16k16bit");
        ttsConfig.addParam(TtsConfig.BasicConfig.PARAM_KEY_SPEED, "5");
        ttsConfig.addParam(TtsConfig.EncodeConfig.PARAM_KEY_ENCODE, "speex");
        return  ttsConfig.getStringConfig();
    }

    /**
     * 获取TTS的参数配置
     * @param context
     * @param initCapkeys    需要初始化的所有发音人
     * @return
     */
    private String getTtsInitParam(Context context, String initCapkeys) {
        TtsInitParam ttsInitParam = new TtsInitParam();
        String dataPath = context.getFilesDir().getAbsolutePath().replace("files", "lib");
        ttsInitParam.addParam(TtsInitParam.PARAM_KEY_DATA_PATH, dataPath);
        ttsInitParam.addParam(TtsInitParam.PARAM_KEY_FILE_FLAG, "android_so");
        ttsInitParam.addParam(TtsInitParam.PARAM_KEY_INIT_CAP_KEYS, initCapkeys);
        return ttsInitParam.getStringConfig();
    }

    /**
     * 播放器反初始化
     */
    public void releaseTtsPlayer(){
        if (mTtsPlayer != null) {
            mTtsPlayer.release();
        }
    }

    /**
     * 播放器回调类
     */
    private class TTSEventProcess implements TTSPlayerListener {

        /**
         * 播放器的状态回调
         * @param playerEvent
         */
        @Override
        public void onPlayerEventStateChange(TTSCommonPlayer.PlayerEvent playerEvent) {

        }

        /**
         * 播放器播放进度回调
         * @param playerEvent
         * @param i
         * @param i1
         */
        @Override
        public void onPlayerEventProgressChange(TTSCommonPlayer.PlayerEvent playerEvent, int i, int i1) {

        }

        /**
         * 播放器的错误回调
         * @param playerEvent
         * @param i
         */
        @Override
        public void onPlayerEventPlayerError(TTSCommonPlayer.PlayerEvent playerEvent, int i) {

        }
    }
}
