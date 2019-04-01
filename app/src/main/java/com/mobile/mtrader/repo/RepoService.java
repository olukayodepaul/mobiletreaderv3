package com.mobile.mtrader.repo;



import com.mobile.mtrader.model.MoveDataToServer;
import com.mobile.mtrader.model.ModelAttendant;
import com.mobile.mtrader.model.ModelEmployees;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RepoService {

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

    @POST("/mobiletrader/order")
    Observable<Response<MoveDataToServer>> moveDataToServer(
            @Body List<MoveDataToServer> user


    );
}

