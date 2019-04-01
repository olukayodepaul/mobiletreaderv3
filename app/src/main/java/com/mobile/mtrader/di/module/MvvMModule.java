package com.mobile.mtrader.di.module;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.persistence.room.Room;
import android.content.Context;
import com.mobile.mtrader.data.Api;
import com.mobile.mtrader.data.DataRepository;
import com.mobile.mtrader.data.DatabaseDaoSQLQuery;
import com.mobile.mtrader.data.DatabaseManager;
import com.mobile.mtrader.di.qualifier.ApplicationContext;
import com.mobile.mtrader.di.scopes.ApplicationScope;
import com.mobile.mtrader.viewmodels.CustomViewModelFactory;
import dagger.Module;
import dagger.Provides;

@Module(includes = {ContextModule.class})
public class MvvMModule {

    private DatabaseManager dManager;

    public MvvMModule(@ApplicationContext Context  application) {
        this.dManager =
                Room.databaseBuilder(application, DatabaseManager.class, "mobile_trader_com_v_three")
                        .build();
    }

    @Provides
    @ApplicationScope
    DataRepository provideDataRepository(DatabaseDaoSQLQuery databaseDaoSQLQuery, Api api ) {
      return new DataRepository(databaseDaoSQLQuery,api);
    }

    @Provides
    @ApplicationScope
    DatabaseDaoSQLQuery providesDbDao(DatabaseManager dbManager) {
        return dbManager.getDataaccess();
    }

    @Provides
    @ApplicationScope
    DatabaseManager providesRoomDatabase() {
        return dManager;
    }

    @Provides
    @ApplicationScope
    ViewModelProvider.Factory provideViewModelFactory(DataRepository repository) {
        return new CustomViewModelFactory(repository);
    }

}
