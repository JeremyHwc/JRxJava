package com.tencent.rxjava2.chapter7.lesson1;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * com.tencent.rxjava2.chapter7.lesson1.NetworkService
 *
 * @author SXDSF
 * @date 2017/11/29 上午11:48
 * @desc 网络服务
 */

public class NetworkService {

    private NetworkInterface mService;

    private NetworkService() {
        final OkHttpClient client = new OkHttpClient();
        GsonBuilder gsonBuilder = new GsonBuilder();
        Type type = new TypeToken<String>() {
        }.getType();
        Gson gson = gsonBuilder.registerTypeAdapter(type, new StringDeserializer()).create();
        Retrofit rest = new Retrofit.Builder().
                addConverterFactory(GsonConverterFactory.create(gson)).
                addCallAdapterFactory(RxJava2CallAdapterFactory.create()).
                client(client).
                baseUrl("http://192.168.1.106:3000").
                build();
        mService = rest.create(NetworkInterface.class);
    }

    private static class InstanceHolder {
        private static final NetworkService instance = new NetworkService();
    }

    public static NetworkInterface getInterface() {
        return InstanceHolder.instance.mService;
    }

    private class StringDeserializer implements JsonDeserializer<String> {
        @Override
        public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String result = null;
            if (json != null) {
                JsonObject obj = json.getAsJsonObject();
                if (obj != null) {
                    result = obj.toString();
                }
            }
            return result;
        }
    }

    public static final class NetworkTransformer<T> implements ObservableTransformer<T, T> {
        @Override
        public ObservableSource<T> apply(Observable<T> upstream) {
            return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        }
    }
}
