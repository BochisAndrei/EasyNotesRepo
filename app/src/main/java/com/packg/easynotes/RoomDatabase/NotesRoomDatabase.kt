package com.packg.easynotes.RoomDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.packg.easynotes.Elements.TextNote
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(TextNote::class), version = 1, exportSchema = false)
abstract class NotesRoomDatabase : RoomDatabase() {

    abstract fun textNoteDao(): TextNoteDao

    private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.textNoteDao())
                }
            }
        }

        suspend fun populateDatabase(textNoteDao: TextNoteDao) {
            // Delete all content here.
            textNoteDao.deleteAll()

            // Add sample words.
            var note = TextNote(name = "First", text = "Description")
            textNoteDao.insert(note)
            note = TextNote(name = "Second", text = "Description2")
            textNoteDao.insert(note)

            // TODO: Add your own words!
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