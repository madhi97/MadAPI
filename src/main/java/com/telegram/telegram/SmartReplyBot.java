package com.telegram.telegram;


import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class SmartReplyBot extends TelegramLongPollingBot {
    OkHttpClient okHttpClient = new OkHttpClient();
    Request request;
    Response response = null;
    JSONObject jsonobject;
    JSONParser parser = new JSONParser();
    String welcomemessage = "Welcome to Storm Replier Bot\n\n" + "Get Message with command /";

    @Override
    public void onUpdateReceived(Update update){
        SendMessage sendMessage = new SendMessage();

        if(update.getMessage().getText().equals("/start"))
        {
            sendMessage.setText("Hii "+ update.getMessage().getFrom().getUserName() + " \uD83D\uDE4B\u200D♂️,\n\n" +welcomemessage);
            try {
                sendMessage.setChatId(update.getMessage().getChatId());
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        else if (!update.getMessage().getText().equals("/start") )
        {
            try
            {
               /* okHttpClient = new OkHttpClient();
                request=new Request.Builder()
                        .url("https://official-joke-api.appspot.com/jokes/programming/random")
                        .get()
                        .build();
                response = okHttpClient.newCall(request).execute();
                String data = response.body().string();
               // jsonObject = (JSONObject)parser.parse(data);
                JSONArray jsonArray = (JSONArray)parser.parse(data);
                System.out.println(jsonArray.get(0));
                JSONObject jokejsonobject = (JSONObject)jsonArray.get(0);*/
                String sendJoke = "Hii ";

                sendMessage.setChatId(update.getMessage().getChatId());
                sendMessage.setText(sendJoke);
                execute(sendMessage);
            }
            catch (Exception e){ e.printStackTrace();}
        }

    }

    @Override
    public String getBotUsername() {
        return "Stormreplier_bot";
    }
    @Override
    public String getBotToken() {
        return "1072704004:AAFTiQiqaIMQ-5-tzJIoYr73VcbjLLP3V3g"; 
    }


    
}