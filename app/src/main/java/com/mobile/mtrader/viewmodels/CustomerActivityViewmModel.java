package com.mobile.mtrader.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.mobile.mtrader.data.AllTablesStructures.AllRepCustomers;
import com.mobile.mtrader.data.AllTablesStructures.UserSpinners;
import com.mobile.mtrader.data.DataRepository;
import com.mobile.mtrader.model.ModelAttendant;
import com.mobile.mtrader.util.FileUtils;

import java.io.File;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;


public class CustomerActivityViewmModel extends ViewModel {

    private DataRepository repository;

    AllRepCustomers allRepCustomers;

    CompositeDisposable disposable = new CompositeDisposable();

    private MutableLiveData<String> broadcast = new MutableLiveData<>();

    public CustomerActivityViewmModel(DataRepository repository) {
        this.repository = repository;
    }

    public MutableLiveData<String> BroadCastResponse() {
        return broadcast;
    }

    public LiveData<List<AllRepCustomers>> getAllCustomersList() {
        return repository.getAllCustomersList();
    }

    public LiveData<List<UserSpinners>> getGroupUserSpinners(int sep) {
        return repository.getGroupUserSpinners(sep);
    }

    public Single<AllRepCustomers> getIndividualCustomerProfiles(int id) {
        return repository.getIndividualCustomerProfiles(id).map(
                profile -> {
                    allRepCustomers = profile;
                    return profile;
                }
        );
    }

    public void mapOutlet(Context context, String custname, String contactname, String phoneno, String address, int outlet_class_id,
                          int outlet_language_id, int outlet_type_id, int userid, String photoPath) {

        RequestBody mcustname = RequestBody.create(MediaType.parse("text/plain"), custname);
        RequestBody mcontactname = RequestBody.create(MediaType.parse("text/plain"), contactname);
        RequestBody mphoneno = RequestBody.create(MediaType.parse("text/plain"), phoneno);
        RequestBody maddress = RequestBody.create(MediaType.parse("text/plain"), address);
        RequestBody moutlet_class_id = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(outlet_class_id));
        RequestBody moutlet_language_id = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(outlet_language_id));
        RequestBody moutlet_type_id = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(outlet_type_id));
        RequestBody muserid = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(userid));

        //Multipart image
        //Uri url = Uri.fromFile(new File(photoPath));
        File file = FileUtils.getFile(context, Uri.fromFile(new File(photoPath))); //new File(url.getPath());
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("image", file.getName(), requestBody);

        Log.e("paths", "----------------" + file);

         repository.mapOutlet(mcustname,mcontactname,mphoneno,maddress,moutlet_class_id,moutlet_language_id,moutlet_type_id,muserid,fileToUpload)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<ModelAttendant>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<ModelAttendant> response) {
                        ModelAttendant res = response.body();
                        Log.e("pathss", "----------------" + res.status);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("pathss", "----------------" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public void reSetUserProfile(String outletName, String contactName, String address, Long phone,
                                 int outlet_class_id, int outlet_language_id, int outlet_type_id, int custno, double lat, double lng) {

        repository.reSetCustomerProfile(outletName, contactName, address, phone, outlet_class_id, outlet_language_id, outlet_type_id, custno, lat, lng)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<ModelAttendant>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(Response<ModelAttendant> response) {

                        ModelAttendant res = response.body();

                        if (response != null && response.isSuccessful() && response.body() != null && response.code() == 200) {

                            if(res.status==200){
                                updateLocalDb(outletName, contactName, address, phone, outlet_class_id, outlet_language_id, outlet_type_id, custno, lat, lng);
                            }else{
                                broadcast.postValue("400~"+res.msg);
                            }

                        } else {
                            broadcast.postValue("400~Connection Error, Please try again");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        broadcast.postValue("400~Channel throwable error");
                    }
                });
    }

    public void updateLocalDb(String outletName, String contactName, String address, Long phone,
                              int outlet_class_id, int outlet_language_id, int outlet_type_id, int custno, double lat, double lng) {

        Completable.fromAction(()->repository.updateMultipleCustomers(outletName, address,contactName, phone, outlet_class_id, outlet_language_id, outlet_type_id, lat, lng, custno ))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onComplete() {
                        Completable.fromAction(()->repository.updateLocalCustomers(outletName, Double.toString(lat), Double.toString(lng), custno))
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new CompletableObserver() {
                                    @Override
                                    public void onSubscribe(Disposable d) {
                                        disposable.add(d);
                                    }

                                    @Override
                                    public void onComplete() {
                                        broadcast.postValue("200~");
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        broadcast.postValue("400~Channel throwable error");
                                    }
                                });
                    }
                    @Override
                    public void onError(Throwable e) {
                        broadcast.postValue("400~Channel throwable error");
                    }
                });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }

}
