package ru.megapolis.chechina.smsresiv.activity;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsMessage;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.text.SimpleDateFormat;
import java.util.Date;

import ru.megapolis.chechina.smsresiv.R;
import ru.megapolis.chechina.smsresiv.SMSReading;

public class AkimActivity  extends AppCompatActivity {
     private static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";


    private TextView smsText;
    private TextView smsSender;
    private TextView bankMoney;
    private TextView remains;
    private TextView name;
    private TextView dataL;
    private TextView magszz;


    private BroadcastReceiver smsReceiver;
    private BroadcastReceiver smsFacebook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akim);

        smsText = (TextView)findViewById(R.id.sms_text);
        smsSender = (TextView)findViewById(R.id.sms_sender);
        bankMoney = (TextView)findViewById(R.id.bank_money);
        remains = (TextView)findViewById(R.id.remains);
        name = (TextView)findViewById(R.id.name);
        dataL = (TextView)findViewById(R.id.dataL);
        magszz = (TextView)findViewById(R.id.magszz);
        //String smska = smsText.getText().toString();
        //String send = smsSender.getText().toString();

        initializeSMSReceiver();
        initializeSMSFacebook();
        registerSMSReceiver();
    }

    private void initializeSMSReceiver(){
        smsReceiver = new BroadcastReceiver(){
            @Override
            public void onReceive(Context context, Intent intent) {
                String appCallId = intent.getStringExtra("com.facebook.platform.protocol.CALL_ID");
                String action = intent.getStringExtra("com.facebook.platform.protocol.PROTOCOL_ACTION");

                if (intent != null && intent.getAction() != null &&
                        ACTION.compareToIgnoreCase(intent.getAction()) == 0) {
                    Object[] pduArray = (Object[]) intent.getExtras().get("pdus");
                    SmsMessage[] messages = new SmsMessage[pduArray.length];
                    for (int i = 0; i < pduArray.length; i++) {
                        messages[i] = SmsMessage.createFromPdu((byte[]) pduArray[i]);
                        smsText.setText(messages[i].getMessageBody());
                        //String smska = messages[i].getMessageBody();
                        smsSender.setText(messages[i].getDisplayOriginatingAddress());
                        MelonSMS melon = SMSReading.onReceiveAll(messages[i].getMessageBody(),messages[i].getDisplayOriginatingAddress());
                        name.setText(melon.bank);
                        magszz.setText(melon.magazin);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
                        dataL.setText(dateFormat.format(melon.dayI));
                        remains.setText(String.valueOf(melon.summa));
                        bankMoney.setText(String.valueOf(melon.balance));

                    }
                }



            }
        };
    }

    private void initializeSMSFacebook(){
        smsFacebook = new BroadcastReceiver(){
            @Override
            public void onReceive(Context context, Intent intent) {
                String appCallId = intent.getStringExtra("com.facebook.platform.protocol.CALL_ID");
                String action = intent.getStringExtra("com.facebook.platform.protocol.PROTOCOL_ACTION");

                if (appCallId != null && action != null) {
                    Bundle extras = intent.getExtras();
                    smsText.setText(extras.toString());

                }



            }
        };
    }

    private void registerSMSReceiver() {
        IntentFilter intentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        intentFilter.setPriority(100);
        registerReceiver(smsReceiver, intentFilter);
        registerReceiver(smsFacebook , new IntentFilter());
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(smsReceiver);
    }
}
