package ru.serujimir.mediationapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.serujimir.mediationapp.Adapters.FeelingsAdapter;
import ru.serujimir.mediationapp.Adapters.QuotesAdapter;
import ru.serujimir.mediationapp.Parsing.Feeling;
import ru.serujimir.mediationapp.Parsing.FeelingsList;
import ru.serujimir.mediationapp.Parsing.FeelingsListData;
import ru.serujimir.mediationapp.Parsing.JsonParser;
import ru.serujimir.mediationapp.Parsing.Login;
import ru.serujimir.mediationapp.Parsing.LoginData;
import ru.serujimir.mediationapp.Parsing.Quote;
import ru.serujimir.mediationapp.Parsing.QuotesList;
import ru.serujimir.mediationapp.Parsing.QuotesListData;

public class HomeFragment extends Fragment {
    View view;
    RecyclerView rvHomeFeelings, rvHomeQuotes;

    ArrayList<Feeling> feelingArrayList = new ArrayList<>();
    ArrayList<Quote> quoteArrayList = new ArrayList<>();

    FeelingsAdapter feelingsAdapter;
    QuotesAdapter quotesAdapter;
    SharedPreferences sharedPreferences;
    TextView tvHomeHi;

    private String nickName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            nickName = getArguments().getString("nickName");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        init();
        return view;
    }
    private void init() {

        tvHomeHi = view.findViewById(R.id.tvHomeHi);
        tvHomeHi.setText("С возвращением, " + nickName);

        feelingsAdapter = new FeelingsAdapter(getContext(), feelingArrayList);
        quotesAdapter = new QuotesAdapter(getContext(), quoteArrayList);

        rvHomeFeelings = view.findViewById(R.id.rvHomeFeelings);
        rvHomeQuotes = view.findViewById(R.id.rvHomeQuotes);

        rvHomeFeelings.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvHomeQuotes.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        rvHomeFeelings.setAdapter(feelingsAdapter);
        rvHomeQuotes.setAdapter(quotesAdapter);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://mskko2021.mad.hakta.pro/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        JsonParser jsonParser = retrofit.create(JsonParser.class);

        Thread feelingsDataThread = new Thread(new Runnable() {
            @Override
            public void run() {
                getFeelingsData(retrofit, gson, jsonParser);
            }
        });
        Thread quotesDataThread = new Thread(new Runnable() {
            @Override
            public void run() {
                getQuotesData(retrofit, gson, jsonParser);
            }
        });
        feelingsDataThread.start();
        quotesDataThread.start();
    }
    public void getFeelingsData(Retrofit retrofit, Gson gson, JsonParser jsonParser) {

        Call<FeelingsList> feelingsListCall = jsonParser.getFeelings();
        feelingsListCall.enqueue(new Callback<FeelingsList>() {
            @Override
            public void onResponse(Call<FeelingsList> call, Response<FeelingsList> response) {
                if (response.body() != null) {
                    FeelingsList feelingsList = response.body();
                    ArrayList<FeelingsListData> feelingsListData = feelingsList.getData();

                    feelingArrayList.clear();

                    for (int i = 0; i < feelingsListData.size(); i++) {
                        Log.d("TEST", String.valueOf(i));
                        feelingArrayList.add(new Feeling(feelingsListData.get(i).getImage(), feelingsListData.get(i).getTitle()));
                    }
                    if (feelingsAdapter != null) {
                        Log.d("TEST", "Updated!");
                        feelingsAdapter.update();
                    }

                }
            }

            @Override
            public void onFailure(Call<FeelingsList> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("TEST", t.getMessage());
            }
        });
    }
    public void getQuotesData(Retrofit retrofit, Gson gson, JsonParser jsonParser) {
        Call<QuotesList> quotesListCall = jsonParser.getQuotes();
        quotesListCall.enqueue(new Callback<QuotesList>() {
            @Override
            public void onResponse(Call<QuotesList> call, Response<QuotesList> response) {
                if (response.body() != null) {
                    QuotesList quotesList = response.body();
                    ArrayList<QuotesListData> quotesListData = quotesList.getData();

                    for (int i = 0; i < quotesListData.size(); i++) {
                        quoteArrayList.add(new Quote(quotesListData.get(i).getTitle(), quotesListData.get(i).getDescription(), quotesListData.get(i).getImage()));
                    }
                    if (quotesAdapter != null) {
                        quotesAdapter.update();
                    }
                }
            }

            @Override
            public void onFailure(Call<QuotesList> call, Throwable t) {

            }
        });
    }
}