package com.chumachenko.simpleReddit.data.db

import android.content.Context
import io.realm.Realm
import io.realm.RealmConfiguration

class RealmUtils {
    fun initRealm(context: Context?) {
        Realm.init(context)
        val config = RealmConfiguration.Builder()
            .compactOnLaunch()
            .name("simplereddit.realm")
            .schemaVersion(1)
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(config)
    }
}