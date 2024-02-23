package ru.serujimir.mediationapp.Parsing;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FeelingsList {
    public FeelingsList(String success, ArrayList<FeelingsListData> data) {
        this.success = success;
        this.data = data;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public ArrayList<FeelingsListData> getData() {
        return data;
    }

    public void setData(ArrayList<FeelingsListData> data) {
        this.data = data;
    }

    String success;
    ArrayList<FeelingsListData> data;
}
