package com.example.sinovoice.ttsmulti;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sinovoice.ttsutil.ConfigUtil;
import com.example.sinovoice.ttsutil.HciCloudSysHelper;
import com.example.sinovoice.ttsutil.HciCloudTtsHelper;
import com.sinovoice.hcicloudsdk.common.HciErrorCode;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = MainActivity.class.getSimpleName();
    private EditText text;
    private Button btnPlay;
    private Button btnPause;
    private Button btnResume;
    private Button btnStop;
    private Spinner mySpinner;
    private List<String> multiLanguage;
    private ArrayAdapter<String> arr_adapter;
    private HciCloudSysHelper mHciCloudSysHelper;
    private HciCloudTtsHelper mHciCloudTtsHelper;
    private String capkey;
    //把所有需要使用的能力都给初始化了
    private String initCapkeys = ConfigUtil.CAP_KEY_TTS_CLOUD_WANGJING + ";" +
            ConfigUtil.CAP_KEY_TTS_CLOUD_SERENA + ";" +
            ConfigUtil.CAP_KEY_TTS_CLOUD_KYOKO + ";" +
            ConfigUtil.CAP_KEY_TTS_CLOUD_NARAE + ";" +
            ConfigUtil.CAP_KEY_TTS_CLOUD_ANNA + ";" +
            ConfigUtil.CAP_KEY_TTS_CLOUD_THOMAS + ";" +
            ConfigUtil.CAP_KEY_TTS_CLOUD_MONICA + ";" +
            ConfigUtil.CAP_KEY_TTS_CLOUD_MILENA + ";" +
            ConfigUtil.CAP_KEY_TTS_CLOUD_VERA + ";" +
            ConfigUtil.CAP_KEY_TTS_CLOUD_NARISA + ";" +
            ConfigUtil.CAP_KEY_TTS_CLOUD_AYLIN + ";" +
            ConfigUtil.CAP_KEY_TTS_CLOUD_CLAIRE + ";" +
            ConfigUtil.CAP_KEY_TTS_CLOUD_MELINA + ";" +
            ConfigUtil.CAP_KEY_TTS_CLOUD_MAGED + ";" +
            ConfigUtil.CAP_KEY_TTS_CLOUD_JAVIER + ";" +
            ConfigUtil.CAP_KEY_TTS_CLOUD_DAMAYANTI + ";" +
            ConfigUtil.CAP_KEY_TTS_CLOUD_XIAOJIE + ";" +
            ConfigUtil.CAP_KEY_TTS_CLOUD_UYGHUR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initView();
        initSinovoice();
    }

    private void initView() {
        text = (EditText) findViewById(R.id.editText);

        mySpinner = (Spinner) findViewById(R.id.mySpinner);
        multiLanguage = new ArrayList<String>();
        multiLanguage.add("汉语");
        multiLanguage.add("英语");
        multiLanguage.add("日语");
        multiLanguage.add("韩语");
        multiLanguage.add("德语");
        multiLanguage.add("法语");
        multiLanguage.add("西班牙语");
        multiLanguage.add("俄罗斯语");
        multiLanguage.add("葡萄牙语");
        multiLanguage.add("泰语");
        multiLanguage.add("土耳其语");
        multiLanguage.add("荷兰语");
        multiLanguage.add("希腊语");
        multiLanguage.add("阿拉伯语");
        multiLanguage.add("墨西哥语");
        multiLanguage.add("印度尼西亚语");
        multiLanguage.add("广东话");
        multiLanguage.add("维吾尔语");
        //适配器
        arr_adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, multiLanguage);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        mySpinner.setAdapter(arr_adapter);
        //设置监听事件
        mySpinner.setOnItemSelectedListener(new mySpinnerOnItemSelectListener());

        //按钮初始化和设置监听
        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnPause = (Button) findViewById(R.id.btnPause);
        btnResume = (Button) findViewById(R.id.btnResume);
        btnStop = (Button) findViewById(R.id.btnStop);

        btnPlay.setOnClickListener(this);
        btnPause.setOnClickListener(this);
        btnResume.setOnClickListener(this);
        btnStop.setOnClickListener(this);
    }

    /**
     * 初始化灵云系统和播放器
     */
    private void initSinovoice() {
        mHciCloudSysHelper = HciCloudSysHelper.getInstance();
        mHciCloudTtsHelper = HciCloudTtsHelper.getInstance();
        int errorCode = mHciCloudSysHelper.init(this);
        if (errorCode != HciErrorCode.HCI_ERR_NONE) {
            Toast.makeText(this, "系统初始化失败，错误码=" + errorCode, Toast.LENGTH_SHORT).show();
            return;
        }
        boolean bool = mHciCloudTtsHelper.initTtsPlayer(this, initCapkeys);
        if (bool == false) {
            Toast.makeText(this, "播放器初始化失败", Toast.LENGTH_SHORT).show();
            return;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPlay:
                mHciCloudTtsHelper.playTtsPlayer(text.getText().toString(), capkey);
                break;
            case R.id.btnPause:
                mHciCloudTtsHelper.pauseTtsPlayer();
                break;
            case R.id.btnResume:
                mHciCloudTtsHelper.resumeTtsPlayer();
                break;
            case R.id.btnStop:
                mHciCloudTtsHelper.stopTtsPlayer();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        if (mHciCloudTtsHelper != null) {
            mHciCloudTtsHelper.releaseTtsPlayer();
        }
        if (mHciCloudSysHelper != null) {
            mHciCloudSysHelper.release();
        }
        super.onDestroy();
    }

    /**
     * Spinner的响应事件是OnItemSelectedListener ,千万不要写出onItemClickListener,否则直接报错
     */
    private class mySpinnerOnItemSelectListener implements Spinner.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0:
                    capkey = ConfigUtil.CAP_KEY_TTS_CLOUD_WANGJING;
                    break;
                case 1:
                    capkey = ConfigUtil.CAP_KEY_TTS_CLOUD_SERENA;
                    break;
                case 2:
                    capkey = ConfigUtil.CAP_KEY_TTS_CLOUD_KYOKO;
                    break;
                case 3:
                    capkey = ConfigUtil.CAP_KEY_TTS_CLOUD_NARAE;
                    break;
                case 4:
                    capkey = ConfigUtil.CAP_KEY_TTS_CLOUD_ANNA;
                    break;
                case 5:
                    capkey = ConfigUtil.CAP_KEY_TTS_CLOUD_THOMAS;
                    break;
                case 6:
                    capkey = ConfigUtil.CAP_KEY_TTS_CLOUD_MONICA;
                    break;
                case 7:
                    capkey = ConfigUtil.CAP_KEY_TTS_CLOUD_MILENA;
                    break;
                case 8:
                    capkey = ConfigUtil.CAP_KEY_TTS_CLOUD_VERA;
                    break;
                case 9:
                    capkey = ConfigUtil.CAP_KEY_TTS_CLOUD_NARISA;
                    break;
                case 10:
                    capkey = ConfigUtil.CAP_KEY_TTS_CLOUD_AYLIN;
                    break;
                case 11:
                    capkey = ConfigUtil.CAP_KEY_TTS_CLOUD_CLAIRE;
                    break;
                case 12:
                    capkey = ConfigUtil.CAP_KEY_TTS_CLOUD_MELINA;
                    break;
                case 13:
                    capkey = ConfigUtil.CAP_KEY_TTS_CLOUD_MAGED;
                    break;
                case 14:
                    capkey = ConfigUtil.CAP_KEY_TTS_CLOUD_JAVIER;
                    break;
                case 15:
                    capkey = ConfigUtil.CAP_KEY_TTS_CLOUD_DAMAYANTI;
                    break;
                case 16:
                    capkey = ConfigUtil.CAP_KEY_TTS_CLOUD_XIAOJIE;
                    break;
                case 17:
                    capkey = ConfigUtil.CAP_KEY_TTS_CLOUD_UYGHUR;
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            capkey = ConfigUtil.CAP_KEY_TTS_CLOUD_WANGJING;
        }
    }
}
