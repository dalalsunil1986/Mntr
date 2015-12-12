package com.mentor.db;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmMigration;

/**
 * Created by Joel on 12/11/2015.
 */
public class Migration implements RealmMigration {
    // increment this if schema changed
    public static final long SCHEMA_VERSION = 0;


    @Override
    public void migrate(DynamicRealm dynamicRealm, long l, long l1) {

    }
}