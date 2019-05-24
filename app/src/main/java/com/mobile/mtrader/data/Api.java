package com.mobile.mtrader.data;


import com.mobile.mtrader.model.DataBridge;
import com.mobile.mtrader.model.ModelEmployees;
import com.mobile.mtrader.model.ModelAttendant;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    @POST("/mobiletrader/login")
    Observable<Response<ModelEmployees>> getUserLogin (
            @Query("username") String username,
            @Query("password") String password,
            @Query("imei") String imei
    );

    @POST("/mobiletrader/roster")
    Observable<Response<ModelAttendant>> getUserRoster (
            @Query("userid") int userid,
            @Query("taskid") int taskid,
            @Query("dates") String dates,
            @Query("times") String times,
            @Query("lat") String lat,
            @Query("lng") String lng,
            @Query("rmsg") String rmsg
    );

    @POST("/mobiletrader/order")
    Observable<Response<DataBridge>> moveDataToServer (
            @Body List<DataBridge> salesEntries
    );

    @POST("/mobiletrader/customerprofile")
    Single<Response<ModelAttendant>> reSetCustomerProfile (
            @Query("outletname") String  outletname,
            @Query("contactname") String contactname,
            @Query("address") String address,
            @Query("phone") Long phone,
            @Query("outlet_class_id") int outlet_class_id,
            @Query("outlet_language_id") int outlet_language_id,
            @Query("outlet_type_id") int outlet_type_id,
            @Query("custno") int custno,
            @Query("lat") double lat,
            @Query("lng") double lng
    );

    @POST("/mobiletrader/outletclose")
    Single<Response<ModelAttendant>> setOutletClose (
            @Query("userid") int  userid,
            @Query("urno") String urno,
            @Query("arivaltime") String arivaltime
    );
}
