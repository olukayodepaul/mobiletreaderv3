package com.mobile.mtrader.frameworks.sku;

import com.mobile.mtrader.adapter.SkuAdapter;
import com.mobile.mtrader.repo.RealmService;
import com.mobile.mtrader.repo.RepoService;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

public class SkuImpl implements SkuContract.skuPresenter {

    private RepoService repoService;
    private RealmService realmService;
    private SkuContract.skuView mView;
    private Picasso picasso;
    private SkuAdapter skuAdapter;

    @Inject
    public SkuImpl(SkuAdapter skuAdapter, RepoService repoService, RealmService realmService,
                   SkuContract.skuView mView, Picasso picasso) {
        this.repoService = repoService;
        this.realmService = realmService;
        this.mView = mView;
        this.picasso = picasso;
        this.skuAdapter = skuAdapter;
    }


}
