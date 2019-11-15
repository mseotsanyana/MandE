package com.me.mseotsanyana.mande.INTERFACE;

import com.me.mseotsanyana.mande.PPMER.DAL.cUserRequest;
import com.me.mseotsanyana.mande.PPMER.DAL.cUserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface iRequestInterface {

    @POST("index.php")
    Call<cUserResponse> operation(@Body cUserRequest request);

}
