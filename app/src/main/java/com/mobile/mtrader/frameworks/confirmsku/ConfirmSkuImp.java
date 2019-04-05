package com.mobile.mtrader.frameworks.confirmsku;

import com.mobile.mtrader.model.DataBridge;
import com.mobile.mtrader.repo.RealmService;
import com.mobile.mtrader.repo.RepoService;
import com.mobile.mtrader.ui.ConfirmSales;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ConfirmSkuImp implements ConfirmSkuContract.ConfirmPresenter {

    private RepoService repoService;
    private RealmService realmService;
    private ConfirmSkuContract.ConfirmView mView;
    private Picasso picasso;
    private ConfirmSales confirmSales;
    List<DataBridge> list;
    DataBridge mapList;

    @Inject
    public ConfirmSkuImp(ConfirmSales confirmSales, RepoService repoService, RealmService realmService,
                         ConfirmSkuContract.ConfirmView mView, Picasso picasso) {
        this.repoService = repoService;
        this.realmService = realmService;
        this.mView = mView;
        this.picasso = picasso;
        this.confirmSales = confirmSales;
    }

    @Override
    public void pushDataToSever( String networkAccess) {

        if(networkAccess.equals("1")){
            saveEntryToPhone();
        }else{
            pushToServer();
        }
    }


    public void pushToServer(){

        list = new ArrayList<>();
        String uuid = UUID.randomUUID().toString();
        for (int i = 0; i < realmService.getSalesEntriesData().size(); i++) {
            mapList = new DataBridge(
                    realmService.getSalesEntriesData().get(i).getEntrydate(),
                    realmService.getSalesEntriesData().get(i).product_name,
                    realmService.getSalesEntriesData().get(i).product_code,
                    realmService.getSalesEntriesData().get(i).customer_id,
                    realmService.getUserId(),
                    realmService.getSalesEntriesData().get(i).separator,
                    realmService.getSalesEntriesData().get(i).order,
                    realmService.getSalesEntriesData().get(i).inventory,
                    realmService.getSalesEntriesData().get(i).pricing,
                    realmService.getSalesEntriesData().get(i).status,
                    realmService.getSalesEntriesData().get(i).rollprice,
                    realmService.getSalesEntriesData().get(i).packprice,
                    realmService.getSalesEntriesData().get(i).separatorname,
                    "0.00000",
                    "0.11111",
                    "00:00:67",
                    uuid,
                    "Open"
            );
            list.add(mapList);
        }

        mView.showProgressDialog();
        repoService.moveDataToServer(list)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<DataBridge>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<DataBridge> response) {
                        /*MoveDataToServer moveDataToServer = response.body();
                        if(response.code()==200 || moveDataToServer.rstatus==200) {
                            for(int i = 0 ; i < moveDataToServer.push.size() ; i++){
                                realmService.updateServerPush(
                                        moveDataToServer.push.get(i).customerid,
                                        moveDataToServer.push.get(i).skucode,
                                        moveDataToServer.push.get(i).qtypack,
                                        moveDataToServer.push.get(i).qtyroll,
                                        moveDataToServer.push.get(i).entrytype
                                );
                            }
                            mView.showError("Sales Entry push to server", "Successful", "Close");
                        }else{
                            mView.showProgressDialog();
                            realmService.updateServerUnPush();
                            mView.showError("Sales Entry Save to phone due to Internet or network problem", "Save to phone", "Close");
                        }
                        */
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showProgressDialog();
                        realmService.updateServerUnPush();
                        mView.showError("Sales Entry Save to phone due to Internet or network problem", "Save to phone", "Close");
                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }


    public void saveEntryToPhone(){
        mView.showProgressDialog();
        realmService.updateServerUnPush();
        mView.showError("Sales Entry Save to phone due to Internet or network problem", "Save to phone", "Close");
    }
}

