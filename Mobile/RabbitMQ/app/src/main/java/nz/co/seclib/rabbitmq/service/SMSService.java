package nz.co.seclib.rabbitmq.service;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

import nz.co.seclib.rabbitmq.vo.MsmVo;

public class SMSService {

    private Context context;

    SMSService(Context context){
        this.context = context;
    }

    public boolean SendMessage(String sIn){

        if(sIn.equals("")) return false;
        MsmVo msmVo =  JSONObject.parseObject(sIn, MsmVo.class);

        if(msmVo == null) return false;
        if(msmVo.phone.equals("")) return false;
        String sMessage = "";
        if(msmVo.templateCode != null){
            sMessage = "Authentication code: " + msmVo.templateCode + " , The code will be expired in 60s.";
            mySendMessage(msmVo.phone, sMessage);
            return true;
        }

        sMessage = JSONObject.toJSONString(msmVo.param);
        sMessage = sMessage + (new Date()).toString();
        mySendMessage(msmVo.phone, sMessage);
        return true;
    }

    public boolean mySendMessage(String sPhoneNumber, String sMessage)
    {
        try {
            String sHint1 = "SMS_SENT";
            String sHint2 = "SMS_DELIVERED";

            PendingIntent sentPI = PendingIntent.getBroadcast(context, 0,
                    new Intent(sHint1), 0);

            //---when the SMS has been sent---
            context.registerReceiver(new BroadcastReceiver(){
                @Override
                public void onReceive(Context arg0, Intent arg1) {
                    if(getResultCode() == Activity.RESULT_OK)
                    {
                        Toast.makeText(context, "SMS sent.",
                                Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(context, "SMS could not be sent",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }, new IntentFilter(sHint1));

            PendingIntent deliveredPI = PendingIntent.getBroadcast(context, 0,
                    new Intent(sHint2), 0);

            //---when the SMS has been sent---
            context.registerReceiver(new BroadcastReceiver(){
                @Override
                public void onReceive(Context arg0, Intent arg1) {
                    if(getResultCode() == Activity.RESULT_OK)
                    {
                        Toast.makeText(context, "SMS delivered.",
                                Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(context, "SMS could not be delivered.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }, new IntentFilter(sHint2));

            SmsManager sms = SmsManager.getDefault();

            //sMessage sometimes is too long and phone will refuse to send it.
            //sms.sendTextMessage(sPhoneNumber, null, sMessage, sentPI, deliveredPI);

            ArrayList<String> parts = sms.divideMessage(sMessage);
            ArrayList<PendingIntent> sendList = new ArrayList<>();
            sendList.add(sentPI);
            ArrayList<PendingIntent> deliverList = new ArrayList<>();
            deliverList.add(deliveredPI);

            sms.sendMultipartTextMessage(sPhoneNumber, null, parts, sendList, deliverList);

        }catch (Exception e) {
            System.console().printf("%s",e.toString());
            return false;
        }

        return true;
    }

}
