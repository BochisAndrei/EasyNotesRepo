package com.packg.easynotes.RoomDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.packg.easynotes.Elements.CheckBoxNote
import com.packg.easynotes.Elements.CrossNote
import com.packg.easynotes.Elements.Folder
import com.packg.easynotes.Elements.TextNote
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [TextNote::class, CrossNote::class, Folder::class, CheckBoxNote::class], version = 2, exportSchema = false)
abstract class NotesRoomDatabase : RoomDatabase() {

    abstract fun textNoteDao(): TextNoteDao
    abstract fun crossNoteDao(): CrossNoteDao
    abstract fun folderDao(): FolderDao
    abstract fun checkBoxDao(): CheckBoxDao

    private class NoteDatabaseCallback(
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
                    populateDatabaseOnOpen(database.textNoteDao(), database.crossNoteDao(), database.folderDao())
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

        suspend fun populateDatabaseOnOpen(textNoteDao: TextNoteDao, crossNoteDao : CrossNoteDao, folderDao: FolderDao) {
            // Delete all content here.
            //textNoteDao.deleteAll()
            //crossNoteDao.deleteAll()
            //folderDao.deleteAll()
            //var crossNote = CrossNote("SomeCrossNote")
            //crossNoteDao.insert(crossNote)
            //var folder = Folder("cevaFolder",1)
            //folderDao.insert(folder)
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
                    .addCallback(NoteDatabaseCallback(scope))
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}