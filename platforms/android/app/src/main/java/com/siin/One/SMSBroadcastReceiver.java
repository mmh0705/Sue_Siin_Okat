package com.siin.One;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SMSBroadcastReceiver extends BroadcastReceiver {
  @Override
  public void onReceive(Context context, Intent intent) {
    if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
      Bundle bundle = intent.getExtras();
      SmsMessage[] msgs = null;
      String msg_from;
      if(bundle != null){
        try {
          Object[] pdus = (Object[]) bundle.get("pdus");
          msgs = new SmsMessage[pdus.length];
          for(int i = 0; i<msgs.length; i++){
            msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
            msg_from = msgs[i].getOriginatingAddress();
            String msgBody = msgs[i].getMessageBody();

            Intent serviceIntent = new Intent(context, popUpService.class);
            serviceIntent.putExtra("text", msgBody);
            serviceIntent.putExtra("number", msg_from);
            context.startService(serviceIntent);


                        /*BufferedWriter bw = new BufferedWriter(new FileWriter("../../../assets/www/text/text.txt",true));
                        bw.write(msg_from+" "+msgBody);
                        bw.close();*/

//                        try{
//                            BufferedReader br = new BufferedReader(new FileReader(getFilesDir()+"text.txt"));
//                            String readStr = "";
//                            String str = null;
//                            while(((str = br.readLine()) != null)){
//                                readStr += str +"\n";
//                            }
//                            br.close();
//
//                            Toast.makeText(context, readStr.substring(0, readStr.length()-1), Toast.LENGTH_SHORT).show();
//                        }catch (FileNotFoundException e){
//                            e.printStackTrace();
//                            Toast.makeText(context, "File not Found", Toast.LENGTH_SHORT).show();
//                        }catch (IOException e) {
//                            e.printStackTrace();
//                        }

            //Toast.makeText(context, "hello world", Toast.LENGTH_SHORT).show();
          }
        }catch (Exception e){
          e.printStackTrace();
        }
      }
    }
  }
}
