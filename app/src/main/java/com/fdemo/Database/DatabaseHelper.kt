package info.androidhive.sqlite.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

import java.util.ArrayList



/**
 * Created by Darshan on 24/04/18.
 */

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    val COLUMN_ID = "id"
    val TITLE = "title"
    val DURATION = "duration"
    val ARTIST = "artist"
    val PATH = "path"
    val POSITION = "position"
    val ALBUMID = "albumid"
    val THUMBNAIL = "thumbnail"
    val COLUMN_TIMESTAMP = "timestamp"
    // Select All Query
    // looping through all rows and adding to list
    // close db connection
    // return notes list
   /* fun allNotes(tableName:String): List<Note>
         {
            val notes = ArrayList<Note>()
            val selectQuery = "SELECT  * FROM " + tableName + " ORDER BY " +
                    COLUMN_TIMESTAMP + " DESC"

            val db = this.writableDatabase
            val cursor = db.rawQuery(selectQuery, null)
            if (cursor.moveToFirst()) {
                do {
                    val note = Note()
                    note.setId(cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ID)))
                    note.setNote(cursor.getString(cursor.getColumnIndex(Note.COLUMN_NOTE)))
                    note.setTimestamp(cursor.getString(cursor.getColumnIndex(Note.COLUMN_TIMESTAMP)))

                    notes.add(note)
                } while (cursor.moveToNext())
            }
            db.close()
            return notes
        }*/

    // return count
    /*fun notesCount(tableName:String): Int
         {
            val countQuery = "SELECT  * FROM " + tableName
            val db = this.readableDatabase
            val cursor = db.rawQuery(countQuery, null)

            val count = cursor.count
            cursor.close()
            return count
        }*/

    // Creating Tables
    override fun onCreate(db: SQLiteDatabase) {

        // create notes table
       // db.execSQL(Note.CREATE_TABLE)
    }

    // Upgrading database
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Drop older table if existed
     //   db.execSQL("DROP TABLE IF EXISTS " + Note.TABLE_NAME)

        // Create tables again
     //   onCreate(db)
    }

    fun createTable(tableName:String){
        // Create table SQL query
        val CREATE_TABLE = (
                "CREATE TABLE " + tableName + "("
                        + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + TITLE + " TEXT,"
                        + DURATION + " TEXT,"
                        + ARTIST + " TEXT,"
                        + PATH + " TEXT,"
                        + POSITION + " TEXT,"
                        + ALBUMID + " TEXT,"
                        + THUMBNAIL + " TEXT,"
                        + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                        + ")")
    }

    /*fun insertNote(note: String,tableName:String): Long {
        // get writable database as we want to write data
        val db = this.writableDatabase

        val values = ContentValues()
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(COLUMN_NOTE, note)

        // insert row
        val id = db.insert(tableName, null, values)

        // close db connection
        db.close()

        // return newly inserted row id
        return id
    }*/

    /*fun getNote(id: Long): Note {
        // get readable database as we are not inserting anything
        val db = this.readableDatabase

        val cursor = db.query(Note.TABLE_NAME,
                arrayOf(Note.COLUMN_ID, Note.COLUMN_NOTE, Note.COLUMN_TIMESTAMP),
                Note.COLUMN_ID + "=?",
                arrayOf(id.toString()), null, null, null, null)

        cursor?.moveToFirst()

        // prepare note object
        val note = Note(
                cursor!!.getInt(cursor.getColumnIndex(Note.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_NOTE)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_TIMESTAMP)))

        // close the db connection
        cursor!!.close()

        return note
    }*/

    /*fun updateNote(note: Note): Int {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(Note.COLUMN_NOTE, note.getNote())

        // updating row
        return db.update(Note.TABLE_NAME, values, Note.COLUMN_ID + " = ?",
                arrayOf(String.valueOf(note.getId())))
    }*/

    /*fun deleteNote(note: Note) {
        val db = this.writableDatabase
        db.delete(Note.TABLE_NAME, Note.COLUMN_ID + " = ?",
                arrayOf(String.valueOf(note.getId())))
        db.close()
    }*/

    companion object {

        // Database Version
        private val DATABASE_VERSION = 1

        // Database Name
        private val DATABASE_NAME = "playlist_db"
    }
}
