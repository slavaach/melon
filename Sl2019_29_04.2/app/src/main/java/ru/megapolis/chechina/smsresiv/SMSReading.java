package ru.megapolis.chechina.smsresiv;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.Date;

import ru.megapolis.chechina.smsresiv.activity.MelonSMS;


public class SMSReading {

                     public static MelonSMS onReceiveAll(String smska, String send) {
                                 MelonSMS melon = new MelonSMS();
                         String aCatsL[] = smska.split(" ");  // по пробелу
                         String str1 = aCatsL[0];
                         //String str2 = "";
                         if(str1.contains("VISA")){
                                 melon =   onReceiveViza( smska, send);
                         }
                         else{
                             melon =   onReceivePerevod( smska, send);
                         }
                                 return melon;
                     }

    

    public static MelonSMS onReceiveViza(String smska, String send) {
            MelonSMS melon = new MelonSMS();


             //equalsIgnoreCase("зачисление")
            //String Money = bankMoney.getText().toString();

            melon.dayI = new Date();

            switch (send) {
                case "900":
                    Log.e("PROFILE", "Сбер банк");
                    melon.bank = "Сбер банк";
                    break;
                case "+79163435607":
                    Log.e("PROFILE", "Мама");
                    melon.bank = "Мама";
                    break;
                case "+79152774474":
                    Log.e("PROFILE", "я");
                    melon.bank = "я";
                    break;
                default:
                    Log.e("PROFILE", "Не известно от кого");
                    melon.bank = "Не известно от кого";
            }

            String aCats[] = smska.split(" ");
            //bankMoney.setText(aCats[3]); // а эту строку я сам придумал )) и работать
            melon.summa = Integer.parseInt(aCats[3].replaceAll("[\\D]", ""));

            int n = 0;
            for (String i : aCats) {
                if (i.equalsIgnoreCase("Баланс:")) {
                    break;
                }
                n = n + 1;
            }
            //remains.setText(aCats[n + 1]);
            melon.balance = Integer.parseInt(aCats[n + 1].replaceAll("[\\D]", ""));


            switch (aCats[4]) {
                case "PYATEROCHKA":
                    melon.magazin = "Пятерочка";
                    break;
                case "К&В":
                    melon.magazin = "Красное и Белое";
                    break;

            }


            return melon;
        }

    public static MelonSMS onReceivePerevod(String smska, String send) {
        MelonSMS melon = new MelonSMS();


        //equalsIgnoreCase("зачисление")
        //String Money = bankMoney.getText().toString();

        melon.dayI = new Date();

        switch (send) {
            case "900":
                Log.e("PROFILE", "Сбер банк");
                melon.bank = "Сбер банк";
                break;
            case "+79163435607":
                Log.e("PROFILE", "Мама");
                melon.bank = "Мама";
                break;
            case "+79152774474":
                Log.e("PROFILE", "я");
                melon.bank = "я";
                break;
            default:
                Log.e("PROFILE", "Не известно от кого");
                melon.bank = "Не известно от кого";
        }

        String aCats[] = smska.split("\\s");
        //bankMoney.setText(aCats[3]); // а эту строку я сам придумал )) и работать
        melon.summa = Integer.parseInt(aCats[1].replaceAll("[\\D]", ""));

        int n = 0;
        for (String i : aCats) {
            if (i.equalsIgnoreCase("VISA3255:")) {
                break;
            }
            n = n + 1;
        }
        //remains.setText(aCats[n + 1]);
        melon.balance = Integer.parseInt(aCats[n + 1].replaceAll("[\\D]", ""));

//        String iiA = "faasd";
////        infoTextView.setText(iiA.replace("и", "о"));

        melon.magazin = (aCats[3]) + " " + (aCats[4]) + " " + (aCats[5]);


//        switch (aCats[4]) {
//            case "PYATEROCHKA":
//                melon.magazin = "Пятерочка";
//                break;
//            case "К&В":
//                melon.magazin = "Красное и Белое";
//                break;
//
//        }


        return melon;
    }

}