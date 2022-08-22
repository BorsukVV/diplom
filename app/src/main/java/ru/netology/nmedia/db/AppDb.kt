package ru.netology.nmedia.db

import android.content.Context
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

        private const val PREF_WAS_GENERATED_ID = 1
        private const val PREF_WAS_GENERATED_PROPERTY = false

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
                context,
                AppDb::class.java,
                "app.db"
            ).allowMainThreadQueries()
                .addCallback(
                    object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
//                            val msg = db.javaClass
//                            Log.d("TAG", "onCreate")
//                            Log.d("TAG", "object : Callback $msg")
                            Thread({ prepopulateDb(getInstance(context)) }).start()
                        }
                    }
                )
                .build()

        private fun prepopulateDb(db: AppDb) {
//            Log.d("TAG", "prepopulateDb")

            val generatorPreset = PrefEntity(PREF_WAS_GENERATED_ID, PREF_WAS_GENERATED_PROPERTY)
            db.prefDao.insert(generatorPreset)
//            val logMsg =
//                db.prefDao.checkWasClicked().value?.last()?.contentGeneratorButtonWasClicked
//            Log.d("TAG", "fun prepopulateDb $logMsg")
        }
    }
}
