package com.cpinto.gamecatalog.db.couchlite

import com.couchbase.lite.Database
import com.couchbase.lite.DatabaseConfiguration

object CouchDatabase {

    const val COUCH_DB = "gamesCatalog"
    private var database: Database? = null
    val config = DatabaseConfiguration()
    fun getInstance(): Database =
        if (database == null) {
            Database(COUCH_DB, config)
        } else {
            database!!
        }
}