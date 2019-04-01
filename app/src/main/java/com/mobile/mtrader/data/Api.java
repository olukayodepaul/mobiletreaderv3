package com.mobile.mtrader.data;


import com.mobile.mtrader.model.ModelEmployees;
import com.mobile.mtrader.model.ModelAttendant;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    @POST("/mobiletrader/login")
    Observable<Response<ModelEmployees>> getUserLogin(
            @Query("username") String username,
            @Query("password") String password,
            @Query("imei") String imei
    );

    @POST("/mobiletrader/roster")
    Observable<Response<ModelAttendant>> getUserRoster(
            @Query("userid") int userid,
            @Query("taskid") int taskid,
            @Query("dates") String dates,
            @Query("times") String times,
            @Query("lat") String lat,
            @Query("lng") String lng,
            @Query("rmsg") String rmsg
    );
}
