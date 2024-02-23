package ru.serujimir.mediationapp.Parsing;

import java.util.ArrayList;

public class QuotesList {

    public QuotesList(String success, ArrayList<QuotesListData> data) {
        this.success = success;
        this.data = data;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public ArrayList<QuotesListData> getData() {
        return data;
    }

    public void setData(ArrayList<QuotesListData> data) {
        this.data = data;
    }

    String success;
    ArrayList<QuotesListData> data;
}
