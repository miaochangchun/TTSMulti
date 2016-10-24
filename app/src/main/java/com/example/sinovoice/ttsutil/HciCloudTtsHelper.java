package com.example.sinovoice.ttsutil;

import android.content.Context;

import com.sinovoice.hcicloudsdk.android.tts.player.TTSPlayer;
import com.sinovoice.hcicloudsdk.common.tts.TtsConfig;
import com.sinovoice.hcicloudsdk.common.tts.TtsInitParam;
import com.sinovoice.hcicloudsdk.player.TTSCommonPlayer;
import com.sinovoice.hcicloudsdk.player.TTSPlayerListener;

/**
 * Created by miaochangchun on 2016/10/24.
 */
public class HciCloudTtsHelper {
    private static HciCloudTtsHelper mHciCloudTtsHelper;
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

    public void initTtsPlayer(Context context){
        mTtsPlayer = new TTSPlayer();
        String strConfig = getTtsInitParam(context);
        mTtsPlayer.init(strConfig, new TTSEventProcess());
    }

    /**
     * 开始播放
     * @param text  需要播放的文本
     * @param capkey    发音人选择
     */
    public void playTtsPlayer(String text, String capkey) {
        String strConfig = getTtsSynthConfig(capkey);
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
     * 合成配置参数
     * @param capkey    发音人选择
     * @return
     */
    private String getTtsSynthConfig(String capkey) {
        TtsConfig ttsConfig = new TtsConfig();
        ttsConfig.addParam(TtsConfig.SessionConfig.PARAM_KEY_CAP_KEY, capkey);
        ttsConfig.addParam(TtsConfig.BasicConfig.PARAM_KEY_AUDIO_FORMAT, "pcm16k16bit");
        ttsConfig.addParam(TtsConfig.BasicConfig.PARAM_KEY_SPEED, "5");
        return  ttsConfig.getStringConfig();
    }

    /**
     * 获取TTS的参数配置
     * @param context
     * @return
     */
    private String getTtsInitParam(Context context) {
        TtsInitParam ttsInitParam = new TtsInitParam();
        String dataPath = context.getFilesDir().getAbsolutePath().replace("files", "lib");
        ttsInitParam.addParam(TtsInitParam.PARAM_KEY_DATA_PATH, dataPath);
        ttsInitParam.addParam(TtsInitParam.PARAM_KEY_FILE_FLAG, "android_so");
        ttsInitParam.addParam(TtsInitParam.PARAM_KEY_INIT_CAP_KEYS, "");
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
    private class TTSEventProcess implements TTSPlayerListener{

        @Override
        public void onPlayerEventStateChange(TTSCommonPlayer.PlayerEvent playerEvent) {

        }

        @Override
        public void onPlayerEventProgressChange(TTSCommonPlayer.PlayerEvent playerEvent, int i, int i1) {

        }

        @Override
        public void onPlayerEventPlayerError(TTSCommonPlayer.PlayerEvent playerEvent, int i) {

        }
    }
}
