package com.packg.easynotes.RoomDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.packg.easynotes.Elements.TextNote
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [TextNote::class], version = 1, exportSchema = false)
abstract class NotesRoomDatabase : RoomDatabase() {

    abstract fun textNoteDao(): TextNoteDao

    private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabaseOnCreate(database.textNoteDao())
                }
            }
        }

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabaseOnOpen(database.textNoteDao())
                }
            }
        }

        suspend fun populateDatabaseOnCreate(textNoteDao: TextNoteDao) {
            // Delete all content here.
            textNoteDao.deleteAll()
            // Add sample words.
            var note = TextNote(name = "Welcome", text = "With EasyNotes you can manage your life much easier!")
            textNoteDao.insert(note)
        }

        suspend fun populateDatabaseOnOpen(textNoteDao: TextNoteDao) {
            // Delete all content here.
            //textNoteDao.deleteAll()
            // Add sample words.
            //var note = TextNote(name = "Welcome", text = "With EasyNotes you can manage your life much easier!")
            //textNoteDao.insert(note)
        }
    }

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: NotesRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): NotesRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotesRoomDatabase::class.java,
                    "notes_database"
                )
                    .addCallback(WordDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}