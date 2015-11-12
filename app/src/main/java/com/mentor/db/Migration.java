package com.mentor.db;

import io.realm.Realm;
import io.realm.RealmMigration;

/**
 * Created by Joel on 12/11/2015.
 */
public class Migration implements RealmMigration {
    // increment this if schema changed
    public static final long SCHEMA_VERSION = 0;

    @Override
    public long execute(Realm realm, long version) {

        //if (version == 0) {
        //    // migrate to 1
        //
        //    // write migration code here
        //
        //    version++;
        //}

        if (version != SCHEMA_VERSION) {
            throw new RuntimeException("unexpected scheme version. expected: " + SCHEMA_VERSION + ", actual: " + version);
        }

        return version;
    }
}