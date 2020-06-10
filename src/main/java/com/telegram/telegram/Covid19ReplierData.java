package com.telegram.telegram;

import java.util.HashMap;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.fasterxml.jackson.databind.*;

public class Covid19ReplierData {

    HashMap<String, Object> responseData = new HashMap<String, Object>();

    HashMap<String, Object> covidData() {

        try {

            OkHttpClient okHttpClient = new OkHttpClient();
            Request request;
            Response response = null;

            okHttpClient = new OkHttpClient();
            request = new Request.Builder()
                    .url("https://covid19-server.chrismichael.now.sh/api/v1/ReportsByCountries/india").get().build();
            response = okHttpClient.newCall(request).execute();
            String data = response.body().string();

            JSONParser jsonparser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonparser.parse(data);
            JSONObject report = (JSONObject) jsonObject.get("report");
            String reportStr = report.toString();

            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode node = objectMapper.readValue(reportStr, JsonNode.class);
            JsonNode activeCases = node.get("active_cases");
            JsonNode innerJson = activeCases.get(0);
            JsonNode currently_infected = innerJson.get("currently_infected_patients");
            JsonNode critical_state = innerJson.get("criticalStates");
            responseData.put("Currently_infected", currently_infected.asLong());
            responseData.put("critical_state", critical_state.asLong());
            responseData.put("Cases", (Long) report.get("cases"));
            responseData.put("Deaths", (Long) report.get("deaths"));
            responseData.put("Recovered", (Long) report.get("recovered"));
            responseData.put("Country", (String) report.get("country"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseData;
    }

}