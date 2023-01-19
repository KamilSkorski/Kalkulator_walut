package com.example.kalkulator_walut;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class Parser
{
    private final Gson gson = new Gson();

    public ArrayList<Currency> parseData(HttpResponse<String> response)
    {
        String body = response.body();
        String bodyToConvert = body.substring(73, body.length()-2);
        Type currencyListType = new TypeToken<ArrayList<Currency>>(){}.getType();
        ArrayList<Currency> listCurrency = gson.fromJson(bodyToConvert, currencyListType);
        return listCurrency;
    }

}
