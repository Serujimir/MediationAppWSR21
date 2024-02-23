package ru.serujimir.mediationapp.Parsing;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface JsonParser {

    @POST("user/login")
    Call<Void> getLogin(@Body Login login);

    @POST("user/login")
    Call<LoginData> getUserInfo(@Body Login login);

    @GET("feelings")
    Call<FeelingsList> getFeelings();

    @GET("quotes")
    Call<QuotesList> getQuotes();
}
