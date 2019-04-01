package com.mobile.mtrader.frameworks.login;

import android.text.TextUtils;

import com.mobile.mtrader.model.ModelEmployees;
import com.mobile.mtrader.repo.RealmService;
import com.mobile.mtrader.repo.RepoService;
import com.mobile.mtrader.ui.MainActivity;
import com.mobile.mtrader.util.AppUtil;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class Loginimp implements LoginContract.LoginPresenter {

    private RepoService repoService;
    private RealmService realmService;
    private LoginContract.LoginView mView;
    private Picasso picasso;
    private MainActivity mainActivity;


    @Inject
    public Loginimp(MainActivity mainActivity, RepoService repoService, RealmService realmService,
                    LoginContract.LoginView mView, Picasso picasso) {
        this.repoService = repoService;
        this.realmService = realmService;
        this.mView = mView;
        this.picasso = picasso;
        this.mainActivity = mainActivity;
    }

    @Override
    public void loadSigninData(String username, String password, String imei) {

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            mView.showError("Please enter password and username", "Error ", "Ok");
        } else if (!AppUtil.checkConnection(mainActivity)) {
            mView.showError("Check your internet connection", "Internet Error", "Ok");
        } else {

            mView.showProgressDialog();
            repoService.getUserLogin(username, password, imei)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Response<ModelEmployees>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(Response<ModelEmployees> response) {

                            ModelEmployees loginModel = response.body();

                            if(response.code()==200){

                                if (loginModel.status == 200) {
                                    realmService.setUserModule(loginModel);
                                    ///mView.showComplete();
                                } else{
                                   // mView.showError( loginModel.msg, "Error Message","Ok");
                                }

                            }else{
                                mView.showError("Can't reach the server", "Network Error","Ok");
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            mView.showError( e.getMessage(), "Network Model Error", "Ok");
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }
}
