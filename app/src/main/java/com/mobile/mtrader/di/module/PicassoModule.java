package com.mobile.mtrader.di.module;

import android.content.Context;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.mobile.mtrader.di.qualifier.ApplicationContext;
import com.mobile.mtrader.di.scopes.ApplicationScope;
import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module(includes = {ContextModule.class, RetrofitModule.class})
public class PicassoModule {

    @Provides
    @ApplicationScope
    public Picasso picasso(@ApplicationContext Context context, OkHttp3Downloader okHttp3Downloader) {
        return new Picasso.Builder(context)
                .downloader(okHttp3Downloader)
                .build();
    }

    @Provides
    @ApplicationScope
    public OkHttp3Downloader okHttp3Downloader(OkHttpClient okHttpClient) {
        return new OkHttp3Downloader(okHttpClient);
    }

}
