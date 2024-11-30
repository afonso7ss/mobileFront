package com.example.teste03;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit;
//    private static final String BASE_URL = "https://backendmobile-production-2816.up.railway.app/";
    private static final String BASE_URL = "http://10.0.2.2:3000/";
    private static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            // Configuração do interceptador para log
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);  // Loga tudo (cabeçalhos, corpo e resposta)

            // Configuração do OkHttpClient com o interceptador
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build();

            // Criação da instância Retrofit com o cliente configurado
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)  // Adiciona o OkHttpClient configurado
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static ApiService getApiService() {
        return getRetrofitInstance().create(ApiService.class);
    }
}
