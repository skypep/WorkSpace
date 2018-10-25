package com.toro.helper.utils.okhttp;

import com.toro.helper.utils.OnHttpDataUpdateListener;
import com.toro.helper.utils.okhttp.mutifile.listener.impl.UIProgressListener;
import com.toro.helper.utils.okhttp.mutifile.utils.OKHttpUtils;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Create By liujia
 * on 2018/10/24.
 **/
public class OkHttp {

    public static final MediaType JSON = MediaType.parse("application/json");

    public static String doPost(String url, JSONObject obj) {
        try{
            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(JSON, obj.toString());
            Request request = new Request.Builder()
                    .header("Content-Type","application/json")
                    .url(url)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            } else {

            }
        } catch (Exception e) {

        }
        return "";
    }

    public static String doTokenPost(String url, JSONObject obj,String token) {
        try{
            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(JSON, obj.toString());
            Request request = new Request.Builder()
                    .addHeader("Authorization", token)
                    .addHeader("Content-Type","application/json")
                    .url(url)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            } else {

            }
        } catch (Exception e) {

        }
        return "";
    }

    public static void upLoadFiles(final int tag, String url, ArrayList<String>fileNames, String token, UIProgressListener progressListener, final OnHttpDataUpdateListener listener) {
        OKHttpUtils.doToroUploadFilesRequest(getToroOkHttpClient(token),url, fileNames, progressListener, new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                listener.bindData(tag,e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                listener.bindData(tag,response.body().string());
            }
        });
    }

    public static void upLoadFile(final int tag, String url, String fileName, String token, UIProgressListener progressListener, final OnHttpDataUpdateListener listener) {
        OKHttpUtils.doToroUploadFileRequest(getToroOkHttpClient(token),url, fileName, progressListener, new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                listener.bindData(tag,e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                listener.bindData(tag,response.body().string());
            }
        });
    }

    /**
     * 创建一个OkHttpClient的对象的单例
     *
     * @return
     */
    private static OkHttpClient getToroOkHttpClient(final String token) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                //设置连接超时等属性,不设置可能会报异常
                .connectTimeout(5000, TimeUnit.SECONDS)
                .readTimeout(5000, TimeUnit.SECONDS)
                .writeTimeout(5000, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("Content-Type","application/json")
                                .addHeader("Authorization", token).build();
                        return chain.proceed(request);
                    }
                });

        OkHttpClient client = builder.build();
        return client;
    }

    /**
     * 无进度
     * @param url
     * @param fileNames
     */
    public static void upLoadFile(final int tag,String url, ArrayList<String> fileNames,String token,final OnHttpDataUpdateListener listener){
        OkHttpClient okHttpClient = getToroOkHttpClient(token);
        Call call = okHttpClient.newCall(getRequest(url,fileNames)) ;
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.bindData(tag,e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                listener.bindData(tag,response.body().string());
            }
        });
    }

    public static void postFile(final int tag,final String url,final String fileName,final String token,final OnHttpDataUpdateListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = getToroOkHttpClient(token);
                    File file = new File(fileName);
                    RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
                    RequestBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("files", "image.png", fileBody)
                            .build();
                    Request request = new Request.Builder()
                            .url(url)
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseStr = response.body().string();
                    listener.bindData(tag,responseStr);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }



    private static RequestBody getRequestBody(ArrayList<String> fileNames) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (int i = 0; i < fileNames.size(); i++) {
            File file = new File(fileNames.get(i));
            builder.addFormDataPart(
                    "files",
                    file.getName(),
                    RequestBody.create(MediaType.parse("application/octet-stream"), file) //创建RequestBody，把上传的文件放入
            );
        }
        return builder.build();
    }

    private static Request getRequest(String url, ArrayList<String> fileNames) {
        Request.Builder builder = new Request.Builder();
        builder.url(url)
                .post(getRequestBody(fileNames));
        return builder.build();
    }

}
