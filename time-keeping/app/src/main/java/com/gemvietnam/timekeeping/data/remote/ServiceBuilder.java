package com.gemvietnam.timekeeping.data.remote;

import com.gemvietnam.timekeeping.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ServiceBuilder {
    private static Retrofit sInstance;
    private static InternalService sService;
    public static final String endpoint = "http://datuet.esy.es/";

    private static Retrofit getRetrofit(String endpoint) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if(BuildConfig.DEBUG){
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }

        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .addInterceptor(new Interceptor() {
                    // User agent default
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        // Set original User agent
                        Request original = chain.request();

                        // User Agent
                        String userAgent = System.getProperty("http.agent");
                        Request request = original.newBuilder()
//                                .header("User-Agent", userAgent)
//                                .header("Content-Type", "application/json")
                                .method(original.method(), original.body())
                                .build();


                        return chain.proceed(request);
                    }
                })
                .build();
        GsonBuilder builder = new GsonBuilder()
                .registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                            throws JsonParseException {
                        return new Date(json.getAsJsonPrimitive().getAsLong());
                    }
                });

        Gson gSon = builder.setLenient().create();
        if (sInstance == null) {
            sInstance = new Retrofit.Builder()
                    .baseUrl(endpoint)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gSon))
                    .build();
        }
        return sInstance;
    }

    public static InternalService getService() {
        if (sService == null) {
            sService = getRetrofit(endpoint).create(InternalService.class);
        }
        return sService;
    }

    public static InternalService getService(String endpoint) {
        if (sService == null) {
            sService = getRetrofit(endpoint).create(InternalService.class);
        }
        return sService;
    }
}
