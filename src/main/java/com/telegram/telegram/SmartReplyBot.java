package com.telegram.telegram;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.util.Date;

import java.io.IOException;
import java.lang.System;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.fasterxml.jackson.databind.*;

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
    String welcomemessage = "Welcome to Storm Replier Bot\n\n"
            + "This ChatBot will help to keep a track of COVID-19 cases in INDIA";

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage sendMessage = new SendMessage();

        if (update.getMessage().getText().equals("/start")) {
            sendMessage.setText("Hii " + update.getMessage().getFrom().getUserName() + " \uD83D\uDE4B\u200D♂️,\n\n"
                    + welcomemessage);
            try {
                sendMessage.setChatId(update.getMessage().getChatId());
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        else if (!update.getMessage().getText().equals("/start")) {
            try {

                Long current = (long) 0 ;
                Long critical = (long) 0 ;
                okHttpClient = new OkHttpClient();
                request = new Request.Builder()
                        .url("https://covid19-server.chrismichael.now.sh/api/v1/ReportsByCountries/india").get()
                        .build();
                response = okHttpClient.newCall(request).execute();
                String data = response.body().string();
               


                JSONParser jsonparser = new JSONParser();
                JSONObject jsonObject = (JSONObject) jsonparser.parse(data);
                JSONObject report = (JSONObject) jsonObject.get("report");
                String reportStr = report.toString();

                ObjectMapper objectMapper= new ObjectMapper();
                
                try{
                JsonNode node = objectMapper.readValue( reportStr, JsonNode.class);
                JsonNode activeCases = node.get("active_cases");
                JsonNode innerJson = activeCases.get(0);
                JsonNode currently_infected = innerJson.get("currently_infected_patients");
                JsonNode critical_state = innerJson.get("criticalStates");
                current = currently_infected.asLong();
                critical = critical_state.asLong();}
                catch(IOException e){
                    e.printStackTrace();
                }



                long millis = System.currentTimeMillis();
                Date date = new java.util.Date(millis);

                String country = (String) report.get("country");
                Long cases = (Long) report.get("cases");
                Long deaths = (Long) report.get("deaths");
                Long recovered = (Long) report.get("recovered");
               
               

                String message = "COVID data" + "(As on " + date + ")" + " of " + country.toUpperCase()
                        + " \uD83C\uDDEE\uD83C\uDDF3 :\n" + "Total Cases Reported:" + cases + ",\n" + "Total Deaths:"
                        + deaths + ",\n" + "Total Recovered:" + recovered + ",\n" + "Currently Infected:" + current+",\n" + "Critically Ill:"+critical
                        + ".";

                sendMessage.setChatId(update.getMessage().getChatId());
                sendMessage.setText(message);
                execute(sendMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public String getBotUsername() {
        return "BOT USERNAME";
    }

    @Override
    public String getBotToken() {
        return "BOT TOKEN";
    }

}
