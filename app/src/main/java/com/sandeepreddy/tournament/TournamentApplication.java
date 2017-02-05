package com.sandeepreddy.tournament;

import android.app.Application;

import com.facebook.appevents.AppEventsLogger;
import com.sandeepreddy.tournament.db.DaoMaster;
import com.sandeepreddy.tournament.db.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created by sandeepreddy on 24/1/17.
 */
public class TournamentApplication extends Application {
    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize the SDK before executing any other operations,
        AppEventsLogger.activateApp(this);

        // do this once, for example in your Application class
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, getResources().getBoolean(R.bool.encrypted) ? "tournament-db-encrypted" : "tournament-db");
        Database db = getResources().getBoolean(R.bool.encrypted) ? helper.getEncryptedWritableDb(getString(R.string.db_secret_key)) : helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }
}
