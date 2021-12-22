package com.example.myscheduler;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper{

    public DatabaseHelper(@Nullable Context context) {
        super(context, "scheduler.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createCategoriesTable = "CREATE TABLE CATEGORIES_TABLE(CATEGORY_ID INTEGER PRIMARY KEY AUTOINCREMENT, CATEGORY_NAME TEXT,CATEGORY_PRIORITY INT, CATEGORY_ACTIVE BOOL)";
        String createNotesTable = "CREATE TABLE NOTES_TABLE(NOTE_ID INTEGER PRIMARY KEY AUTOINCREMENT, NOTE_TITLE TEXT,NOTE_DESCRIPTION VARCHAR(200000), NOTE_DATE DATE)";
        String createTasksTable = "CREATE TABLE TASKS_TABLE(TASK_ID INTEGER PRIMARY KEY AUTOINCREMENT, TASK_NAME TEXT, TASK_DATE DATE, TASK_ACTIVE BOOL,TASK_CATEGORY INTEGER, FOREIGN KEY (TASK_CATEGORY) REFERENCES CATEGORIES_TABLE(CATEGORY_ID));";
        sqLiteDatabase.execSQL(createCategoriesTable);
        sqLiteDatabase.execSQL(createNotesTable);
        sqLiteDatabase.execSQL(createTasksTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean insertCategory(String strCategoryName, int intPriority, boolean boolIsActive )
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("CATEGORY_NAME", strCategoryName);
        contentValues.put("CATEGORY_PRIORITY", intPriority);
        contentValues.put("CATEGORY_ACTIVE", boolIsActive);
        long id = db.insert("CATEGORIES_TABLE", null , contentValues);
        return true;
    }

    public boolean insertTask(String strTaskName, int intCategoryId, boolean boolIsActive, String dateTaskDate){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TASK_NAME", strTaskName);
        contentValues.put("TASK_CATEGORY", intCategoryId);
        contentValues.put("TASK_ACTIVE", boolIsActive);
        contentValues.put("TASK_DATE", dateTaskDate);
        long id = db.insert("TASKS_TABLE", null , contentValues);
        return true;
    }

    public boolean insertNote(String strNoteName, String strNoteDescription, Date dateNoteDate )
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("NOTE_TITLE", strNoteName);
        contentValues.put("NOTE_DESCRIPTION", strNoteDescription);
        contentValues.put("NOTE_DATE", String.valueOf(dateNoteDate));
        long id = db.insert("NOTES_TABLE", null , contentValues);
        return true;
    }


    public ArrayList<DatabaseStructure.Category> getAllCategories()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"CATEGORY_ID","CATEGORY_NAME","CATEGORY_PRIORITY","CATEGORY_ACTIVE"};
        Cursor cursor =db.query("CATEGORIES_TABLE",columns,null,null,null,null,"CATEGORY_PRIORITY");
        ArrayList<DatabaseStructure.Category> lstCategories = new ArrayList<DatabaseStructure.Category>();

        while (cursor.moveToNext())
        {
            DatabaseStructure.Category objCategory = new DatabaseStructure.Category();
            objCategory.CATEGORY_ID =cursor.getInt(0);
            objCategory.CATEGORY_NAME=cursor.getString(1);
            objCategory.CATEGORY_PRIORITY =cursor.getInt(2);
            objCategory.CATEGORY_ACTIVE=cursor.getInt(3) == 1? true: false;
            lstCategories.add(objCategory);
        }

        cursor.close();
        db.close();
        return lstCategories;
    }

    public ArrayList<DatabaseStructure.Task> getTodayTask()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"TASK_ID","TASK_NAME","TASK_CATEGORY","TASK_DATE", "TASK_ACTIVE"};
        Cursor cursor =db.query("TASKS_TABLE",columns,"TASK_DATE = DATE('now')",null,null,null,"TASK_DATE");
        ArrayList<DatabaseStructure.Task> lstTask = new ArrayList<DatabaseStructure.Task>();

        while (cursor.moveToNext())
        {
            DatabaseStructure.Task objTask = new DatabaseStructure.Task();
            objTask.TASK_ID =cursor.getInt(0);
            objTask.TASK_NAME=cursor.getString(1);
            objTask.TASK_CATEGORY =cursor.getInt(2);
            objTask.TASK_DATE=new Date(cursor.getLong(3));
            objTask.TASK_ACTIVE=cursor.getInt(4) == 1? true: false;
            lstTask.add(objTask);
        }
        cursor.close();
        db.close();
        return lstTask;
    }

    public ArrayList<DatabaseStructure.Task> getOtherTask()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"TASK_ID","TASK_NAME","TASK_CATEGORY","TASK_DATE", "TASK_ACTIVE"};
        Cursor cursor =db.query("TASKS_TABLE",columns,"TASK_DATE != DATE('now') OR TASK_DATE != DATE('now', '+1 day')",null,null,null,"TASK_DATE");
        ArrayList<DatabaseStructure.Task> lstTask = new ArrayList<DatabaseStructure.Task>();

        while (cursor.moveToNext())
        {
            DatabaseStructure.Task objTask = new DatabaseStructure.Task();
            objTask.TASK_ID =cursor.getInt(0);
            objTask.TASK_NAME=cursor.getString(1);
            objTask.TASK_CATEGORY =cursor.getInt(2);
            objTask.TASK_DATE=new Date(cursor.getLong(3));
            objTask.TASK_ACTIVE=cursor.getInt(4) == 1? true: false;
            lstTask.add(objTask);
        }
        cursor.close();
        db.close();
        return lstTask;
    }

    public ArrayList<DatabaseStructure.Task> getTomorrowTask()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"TASK_ID","TASK_NAME","TASK_CATEGORY","TASK_DATE", "TASK_ACTIVE"};
        Cursor cursor =db.query("TASKS_TABLE",columns,"TASK_DATE = DATE('now', '+1 day')",null,null,null,"TASK_DATE");
        ArrayList<DatabaseStructure.Task> lstTask = new ArrayList<DatabaseStructure.Task>();

        while (cursor.moveToNext())
        {
            DatabaseStructure.Task objTask = new DatabaseStructure.Task();
            objTask.TASK_ID =cursor.getInt(0);
            objTask.TASK_NAME=cursor.getString(1);
            objTask.TASK_CATEGORY =cursor.getInt(2);
            objTask.TASK_DATE=new Date(cursor.getLong(3));
            objTask.TASK_ACTIVE=cursor.getInt(4) == 1? true: false;
            lstTask.add(objTask);
        }
        cursor.close();
        db.close();
        return lstTask;
    }


    public ArrayList<DatabaseStructure.Note> getAllNotes()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"NOTE_ID","NOTE_TITLE","NOTE_DESCRIPTION","NOTE_DATE"};
        Cursor cursor =db.query("NOTES_TABLE",columns,null,null,null,null,"NOTE_DATE");
        ArrayList<DatabaseStructure.Note> lstNotes = new ArrayList<DatabaseStructure.Note>();

        while (cursor.moveToNext())
        {
            DatabaseStructure.Note objNote = new DatabaseStructure.Note();
            objNote.NOTE_ID =cursor.getInt(0);
            objNote.NOTE_TITLE=cursor.getString(1);
            objNote.NOTE_DESCRIPTION =cursor.getString(2);
            objNote.NOTE_DATE=new Date(cursor.getLong(3));
            lstNotes.add(objNote);
        }

        cursor.close();
        db.close();
        return lstNotes;
    }

    public ArrayList<DatabaseStructure.Category> searchCategories(String strSearchKeyword){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"CATEGORY_ID","CATEGORY_NAME","CATEGORY_PRIORITY","CATEGORY_ACTIVE"};
        Cursor cursor =db.query("CATEGORIES_TABLE",columns,"CATEGORY_NAME LIKE ?", new String[]{"%"+strSearchKeyword+"%"},null,null,"CATEGORY_PRIORITY");
        ArrayList<DatabaseStructure.Category> lstCategories = new ArrayList<DatabaseStructure.Category>();

        while (cursor.moveToNext())
        {
            DatabaseStructure.Category objCategory = new DatabaseStructure.Category();
            objCategory.CATEGORY_ID =cursor.getInt(0);
            objCategory.CATEGORY_NAME=cursor.getString(1);
            objCategory.CATEGORY_PRIORITY =cursor.getInt(2);
            objCategory.CATEGORY_ACTIVE=cursor.getInt(3) == 1? true: false;
            lstCategories.add(objCategory);
        }
        cursor.close();
        db.close();
        return lstCategories;
    }

    public ArrayList<DatabaseStructure.Note> searchNotes(String strSearchKeyword){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"NOTE_ID","NOTE_TITLE","NOTE_DESCRIPTION","NOTE_DATE"};
        Cursor cursor =db.query("NOTES_TABLE",columns,"NOTE_TITLE LIKE ?", new String[]{"%"+strSearchKeyword+"%"},null,null,"NOTE_DATE");
        ArrayList<DatabaseStructure.Note> lstNotes = new ArrayList<DatabaseStructure.Note>();

        while (cursor.moveToNext())
        {
            DatabaseStructure.Note objNote = new DatabaseStructure.Note();
            objNote.NOTE_ID =cursor.getInt(0);
            objNote.NOTE_TITLE=cursor.getString(1);
            objNote.NOTE_DESCRIPTION =cursor.getString(2);
            objNote.NOTE_DATE=new Date(cursor.getLong(3));
            lstNotes.add(objNote);
        }
        cursor.close();
        db.close();
        return lstNotes;
    }

    public ArrayList<DatabaseStructure.Task> searchTasks(String strSearchKeyword){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"TASK_ID","TASK_NAME","TASK_CATEGORY","TASK_DATE", "TASK_ACTIVE"};
        Cursor cursor =db.query("TASKS_TABLE",columns,"TASK_NAME LIKE ?", new String[]{"%"+strSearchKeyword+"%"},null,null,"TASK_DATE");
        ArrayList<DatabaseStructure.Task> lstTasks = new ArrayList<DatabaseStructure.Task>();

        while (cursor.moveToNext())
        {
            DatabaseStructure.Task objTask = new DatabaseStructure.Task();
            objTask.TASK_ID =cursor.getInt(0);
            objTask.TASK_NAME=cursor.getString(1);
            objTask.TASK_CATEGORY =cursor.getInt(2);
            objTask.TASK_DATE=new Date(cursor.getLong(3));
            objTask.TASK_ACTIVE = cursor.getInt(4) == 1? true: false;
            lstTasks.add(objTask);
        }
        cursor.close();
        db.close();
        return lstTasks;
    }

    public ArrayList<String> getTaskCategories()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"CATEGORY_ID","CATEGORY_NAME","CATEGORY_PRIORITY","CATEGORY_ACTIVE"};
        Cursor cursor =db.query("CATEGORIES_TABLE",columns,null,null,null,null,"CATEGORY_PRIORITY");
        ArrayList<String> lstCategories = new ArrayList<String>();

        while (cursor.moveToNext())
        {
            lstCategories.add(cursor.getString(1));
        }

        cursor.close();
        db.close();
        return lstCategories;
    }

    public boolean updateTask(int TASK_ID, boolean boolIsActive){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TASK_ACTIVE", boolIsActive);
        long id = db.update("TASKS_TABLE" , contentValues, "TASK_ID = ?", new String[]{""+TASK_ID});
        db.close();
        return true;
    }
}