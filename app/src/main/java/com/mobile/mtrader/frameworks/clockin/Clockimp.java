package com.mobile.mtrader.frameworks.clockin;


import com.mobile.mtrader.model.ModelAttendant;
import com.mobile.mtrader.repo.RealmService;
import com.mobile.mtrader.repo.RepoService;
import com.mobile.mtrader.ui.DepotClokingActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class Clockimp implements ClockContract.ClockPresenter {


    private RepoService repoService;
    private RealmService realmService;
    private ClockContract.ClockView mView;
    private DepotClokingActivity depotClokingActivity;


    @Inject
    public Clockimp(DepotClokingActivity depotClokingActivity, RepoService repoService, RealmService realmService,
                    ClockContract.ClockView mView) {
        this.repoService = repoService;
        this.realmService = realmService;
        this.mView = mView;
        this.depotClokingActivity = depotClokingActivity;
    }


    @Override
    public void loadClockOutData() {

        String[] arr = realmService.depotGeoCordAndDepotWaiver().split("\\~");
        double lat = Double.parseDouble(arr[0]);
        double lng = Double.parseDouble(arr[1]);
       // String depotwaiver  = arr[2].toString();

        String cDates = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String cTime = new SimpleDateFormat("HH:mm:ss").format(new Date());
        String cmsg = "Resumption already taken";

        String cLag = "0.0";
        String cLng = "1.0";

        enterRoster(realmService.getUserId(), 1, cDates, cTime, cLag,cLng, cmsg);

    }

    public void enterRoster(int userid, int taskid, String cdates, String ctimes, String clat, String clng , String cmsg) {

        mView.showProgressDialog();
        repoService.getUserRoster(userid, taskid, cdates, ctimes, clat, clng, cmsg)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<ModelAttendant>>() {
                    @Override
                    public void onSubscribe(Disposable d) {}

                    @Override
                    public void onNext(Response<ModelAttendant> response) {
                        ModelAttendant roster = response.body();
                        if (response.code() == 200) {
                            if (roster.status == 200) {
                                realmService.updateClockin(
                                        taskid,
                                        ctimes
                                );
                                mView.showMsg(roster.msg, "Successful", "Ok");
                            } else {
                                mView.showMsg(roster.msg, "Notification Message", "Close");
                            }
                        } else {
                            mView.showMsg("Can't reach the server", "Network Error", "Ok");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showMsg(e.getMessage(), "Network Model Error", "Ok");
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
