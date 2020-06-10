package com.telegram.telegram;

import java.util.Date;

import java.lang.System;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class SmartReplyBot extends TelegramLongPollingBot {

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

                Covid19ReplierData covid = new Covid19ReplierData();

                long millis = System.currentTimeMillis();
                Date date = new java.util.Date(millis);

                String message = "COVID data" + "(As on " + date + ")" + " of "
                        + ((String) covid.covidData().get("Country")).toUpperCase() + " \uD83C\uDDEE\uD83C\uDDF3 :\n"
                        + "Total Cases Reported:" + (long) covid.covidData().get("Cases") + ",\n" + "Total Deaths:"
                        + (long) covid.covidData().get("Deaths") + ",\n" + "Total Recovered:"
                        + (long) covid.covidData().get("Recovered") + ",\n" + "Currently Infected:"
                        + (long) covid.covidData().get("critical_state") + ",\n" + "Critically Ill:"
                        + (long) covid.covidData().get("Currently_infected") + ".";

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

        return "BotUserName";
    }

    @Override
    public String getBotToken() {

        return "BotToken";

    }

}
