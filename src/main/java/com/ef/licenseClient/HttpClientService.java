package com.ef.licenseClient;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.util.concurrent.TimeUnit;

public class HttpClientService {

    OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(1,TimeUnit.MINUTES)
            .writeTimeout(1,TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .build();

    String resBody = "";

    public Object getProductStatus(long productId, String licenseManagerUrl){


        String url = licenseManagerUrl+"/license-manager/license";

        Request request = new Request.Builder()
                .url(url+"?productId="+productId)
                .method("GET", null)
                .build();

        try{
            Response response = client.newCall(request).execute();
            if(response.code() == 200){
                resBody = response.body().string();
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return resBody;
    }
}
