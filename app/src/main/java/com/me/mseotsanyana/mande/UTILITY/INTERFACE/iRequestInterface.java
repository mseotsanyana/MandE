package com.me.mseotsanyana.mande.UTILITY.INTERFACE;

import com.me.mseotsanyana.mande.UTILITY.DAL.cUserRequest;
import com.me.mseotsanyana.mande.UTILITY.DAL.cUserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface iRequestInterface {

    @POST("index.php")
    Call<cUserResponse> operation(@Body cUserRequest request);

}
