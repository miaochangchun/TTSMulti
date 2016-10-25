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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (EditText) findViewById(R.id.editText);

        mySpinner = (Spinner) findViewById(R.id.mySpinner);
        multiLanguage = new ArrayList<String>();
        multiLanguage.add("汉语");        //tts.cloud.hui
        multiLanguage.add("英语");        //tts.cloud.kate
        multiLanguage.add("日语");        //tts.cloud.misaki
        multiLanguage.add("韩语");        //tts.cloud.yumi
        multiLanguage.add("德语");        //tts.cloud.anna
        multiLanguage.add("法语");        //tts.cloud.thomas
        multiLanguage.add("西班牙语");      //tts.cloud.violeta
        multiLanguage.add("俄罗斯语");       //tts.cloud.milena
        multiLanguage.add("葡萄牙语");       //tts.cloud.vera
        multiLanguage.add("泰语");           //tts.cloud.narisa
        multiLanguage.add("土耳其语");        //tts.cloud.aylin
        multiLanguage.add("荷兰语");          //tts.cloud.claire
        multiLanguage.add("希腊语");          //tts.cloud.melina
        multiLanguage.add("阿拉伯语");        //tts.cloud.maged
        multiLanguage.add("墨西哥语");        //tts.cloud.javier
        multiLanguage.add("印度尼西亚语");    //tts.cloud.damayanti
        multiLanguage.add("广东话");       //tts.cloud.xiaojie
        multiLanguage.add("维吾尔语");      //tts.cloud.uyghur
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

        mHciCloudSysHelper = HciCloudSysHelper.getInstance();
        mHciCloudTtsHelper = HciCloudTtsHelper.getInstance();
        int errCode = mHciCloudSysHelper.init(this);
        if (errCode != HciErrorCode.HCI_ERR_NONE) {
            Log.e(TAG, "mHciCloudSysHelper.init failed and return " + errCode);
            return;
        }
        mHciCloudTtsHelper.initTtsPlayer(this);
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

    /**
     * Spinner的响应事件是OnItemSelectedListener ,千万不要写出onItemClickListener,否则直接报错
     */
    private class mySpinnerOnItemSelectListener implements Spinner.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0:
                    capkey = "tts.cloud.hui";
                    break;
                case 1:
                    capkey = "tts.cloud.kate";
                    break;
                case 2:
                    capkey = "tts.cloud.misaki";
                    break;
                case 3:
                    capkey = "tts.cloud.yumi";
                    break;
                case 4:
                    capkey = "tts.cloud.anna";
                    break;
                case 5:
                    capkey = "tts.cloud.thomas";
                    break;
                case 6:
                    capkey = "tts.cloud.violeta";
                    break;
                case 7:
                    capkey = "tts.cloud.milena";
                    break;
                case 8:
                    capkey = "tts.cloud.vera";
                    break;
                case 9:
                    capkey = "tts.cloud.narisa";
                    break;
                case 10:
                    capkey = "tts.cloud.aylin";
                    break;
                case 11:
                    capkey = "tts.cloud.claire";
                    break;
                case 12:
                    capkey = "tts.cloud.melina";
                    break;
                case 13:
                    capkey = "tts.cloud.maged";
                    break;
                case 14:
                    capkey = "tts.cloud.javier";
                    break;
                case 15:
                    capkey = "tts.cloud.damayanti";
                    break;
                case 16:
                    capkey = "tts.cloud.xiaojie";
                    break;
                case 17:
                    capkey = "tts.cloud.uyghur";
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            capkey = "tts.cloud.hui";
        }
    }
}
