package ru.netology.nmedia.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [PostEntity::class, PrefEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDb : RoomDatabase() {
    abstract val postDao: PostDao
    abstract val prefDao: PrefDao

    companion object {
        @Volatile
        private var instance: AppDb? = null

        fun getInstance(context: Context): AppDb {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context)
                    .also { instance = it }
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context,                AppDb::class.java,                "app.db"
            ).allowMainThreadQueries()
                .addCallback(
                    object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            val msg = db.javaClass
                            Log.d("TAG", "onCreate")
                            Log.d("TAG", "object : Callback $msg")
                            Thread(Runnable { prepopulateDb(getInstance(context)) }).start()
                        }
                    }
   //                 AppDbCallback()
                )
                .build()
        private fun prepopulateDb(db: AppDb) {
            Log.d("TAG", "prepopulateDb")
            val generatorPreset = PrefEntity(1, false)
            db.prefDao.insert(generatorPreset)
            val logMsg = db.prefDao.checkWasClicked().last().contentGeneratorButtonWasClicked
            Log.d("TAG", "fun prepopulateDb $logMsg")
        }

//        private class AppDbCallback : RoomDatabase.Callback() {
//            override fun onCreate(db: SupportSQLiteDatabase) {
//                super.onCreate(db)
//                Log.d("TAG", "onCreate")
//                //db as AppDb
//
//                val msg = db.javaClass
//
//                Log.d("TAG", "$msg")
//
//                instance?.let {
//                    Log.d("TAG", "in let")
//
//                    val generatorPreset = PrefEntity(1, false)
//                    it.prefDao.insert(generatorPreset)
//                }
//            }
//        }
    }
}
