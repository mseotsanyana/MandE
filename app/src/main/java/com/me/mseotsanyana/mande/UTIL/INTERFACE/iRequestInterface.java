package com.me.mseotsanyana.mande.UTIL.INTERFACE;

import com.me.mseotsanyana.mande.UTIL.DAL.cUserRequest;
import com.me.mseotsanyana.mande.UTIL.DAL.cUserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface iRequestInterface {

    @POST("index.php")
    Call<cUserResponse> operation(@Body cUserRequest request);

}
