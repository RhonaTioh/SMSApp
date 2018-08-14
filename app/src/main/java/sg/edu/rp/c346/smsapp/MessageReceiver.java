package sg.edu.rp.c346.smsapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by 17010318 on 14/8/2018.
 */

public class MessageReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //SMS messages are retrieved from intent's extra using the key "pdus"
        Bundle bundle = intent.getExtras();
        try{
            if(bundle != null){
                Object[] pdusObj = (Object[]) bundle.get("pdus");
                SmsMessage currentMessage;
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    String format = bundle.getString("format");
                    currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[0], format);
                }else{
                    currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[0]);
                }
                //obtain the originating phone number(sender's number)
                String senderNum = currentMessage.getDisplayOriginatingAddress();
                //obtain message body
                String message = currentMessage.getDisplayMessageBody();
                //Display in Toast
                Toast.makeText(context, "You received a message from " + senderNum + ": \n" + message, Toast.LENGTH_LONG).show();
            }
        } catch (Exception e){
            Log.e("smsReceiver" , "Error: " + e);
        }
    }
}
