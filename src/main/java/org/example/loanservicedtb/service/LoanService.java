package org.example.loanservicedtb.service;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import okhttp3.*;
import org.example.loanservicedtb.dtos.ErrorRESPONSE;
import org.example.loanservicedtb.dtos.LoanTokenRESPONSE;
import org.example.loanservicedtb.dtos.NewCustomerOriginationRESPONSE;
import org.example.loanservicedtb.dtos.NewCustomerREQUEST;
import org.example.loanservicedtb.utils.CustomOkHttp3Client;
import org.example.loanservicedtb.utils.LoanConstants;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@AllArgsConstructor
public class LoanService {

    private final CustomOkHttp3Client customOkHttp3Client;
    private Gson gson = new Gson();

    public Object createNewCustomer(NewCustomerREQUEST newCustomerRequest) throws Exception {

        try(Response response = customOkHttp3Client.sendPostRequest(LoanConstants.LOAN_SERVICE+LoanConstants.NEW_CUSTOMER,
                newCustomerRequest, getCustomHeaders(getToken()))){
            if(response.code() == 200){
                String responseBody = response.body().string();
                System.out.println(responseBody);
                NewCustomerOriginationRESPONSE newCustomerOriginationResponse = gson.fromJson(responseBody, NewCustomerOriginationRESPONSE.class);
                return newCustomerOriginationResponse;
            } else if (response.code() == 409) {
                System.out.println(response.code());
                String responseBody = response.body().string();
                return gson.fromJson(responseBody, ErrorRESPONSE.class);
            }
            String responseBody = response.body().string();
            NewCustomerOriginationRESPONSE newCustomerOriginationResponse = gson.fromJson(responseBody, NewCustomerOriginationRESPONSE.class);
            return newCustomerOriginationResponse;
        }catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
    }

    private static class OAuthClientBimaMoKenyaUG{//todo: implement token expiration policy
        private static final String URL = "https://uat.dtbafrica.com/api/auth/token";
        //        private static final String CLIENT_ID = "2";
//        private static final String CLIENT_SECRET = "inRQROGmy4Jh9QRm3DPYvVaDplRsTX0qmKRP9oT3";

        private static final String GRANT_TYPE = "client_credentials";


        private static OkHttpClient client = new OkHttpClient();
        private static final Gson gson = new Gson();
        private static LoanTokenRESPONSE currentToken = null;//todo: for token expiry check

        public static LoanTokenRESPONSE getDigitalToken() throws IOException {
            RequestBody formBody = new FormBody.Builder()
                    .add("grant_type", GRANT_TYPE)
//                    .add("client_id", CLIENT_ID)
//                    .add("client_secret", CLIENT_SECRET)
                    .build();

            Request request = new Request.Builder()
                    .header("Authorization", "Basic OTUzN2E4NmMtYmJjMi00ZTc5LThhN2ItNTIyZjQwYTNhNmY5OlltWHRLdmFLdjhEYUU3VXl0UjlvakRIUGM1a2gy")
                    .url(URL)
                    .post(formBody)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                String responseBody = response.body().string();
                System.out.println(responseBody);
                LoanTokenRESPONSE tzoAuthClientResponse = gson.fromJson(responseBody, LoanTokenRESPONSE.class);
                System.out.println(tzoAuthClientResponse.toString());
                System.out.println(tzoAuthClientResponse.getAccess_token().toString());
                return new LoanTokenRESPONSE(
                        tzoAuthClientResponse.getAccess_token(),
                        tzoAuthClientResponse.getToken_type(),
                        tzoAuthClientResponse.getExpires_in()
                );
            }
        }

        public static String getValidToken() throws IOException {//todo: for token expiry impl
            if (currentToken == null || currentToken.isExpired()) {
                currentToken = getDigitalToken();
            }
            return currentToken.getAccess_token();
        }
    }

    public String getToken() throws IOException {
        return OAuthClientBimaMoKenyaUG.getValidToken();
    }

    private Headers getCustomHeaders(String token) throws Exception {
        return new Headers.Builder()
                .add("cache-control", "no-cache")
                .add("content-type", "application/json")
                .add("accept", "application/json")
                .add("Authorization", "Bearer " + token)
                .build();
    }
}
