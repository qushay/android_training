package com.test.androidtrainingtest.datasource;

/**
 * Created by Jefriyanto on 11/07/16.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public class RestClient {
    public static final String BASE_URL = "http://192.168.0.16";
//    public static final String BASE_URL = "http://10.0.2.2";
    public static final String IMAGE_URL = BASE_URL + "/android/training/images/";

    private static Retrofit retrofit;
    public static <T> T create(Class<T> service) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        if (retrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(logging).connectTimeout(15, TimeUnit.SECONDS);

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(httpClient.build())
                    .build();
        }
        return retrofit.create(service);
    }

    public interface LoginApi {

        @POST("/android/training/login.php")
        @FormUrlEncoded
        Call<JsonObject> login(
                @Field("email") String email,
                @Field("password") String password
        );

    }

    public interface QuestionApi {

        @GET("/android/training/question_list.php")
        Call<JsonObject> questionList();

    }



}

