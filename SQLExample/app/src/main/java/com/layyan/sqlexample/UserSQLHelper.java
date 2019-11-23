package com.layyan.sqlexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.layyan.sqlexample.UserContract.User;

import java.util.ArrayList;
import java.util.List;

public class UserSQLHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "users.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + User.TABLE_NAME + "(" + User.COLUMN_PHONE + " TEXT," + User.COLUMN_NAME + " TEXT," + User.COLUMN_EMAIL + " TEXT)";
    private static final String SQL_DROP_TABLE_ENTRIES = "DROP TABLE IF EXISTS " + User.TABLE_NAME;


    private String[] allColumn = {"ROWID", User.COLUMN_NAME,User.COLUMN_PHONE ,User.COLUMN_EMAIL};

    public UserSQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {         // This
        // database is only a cache for online data, so its upgrade policy is         // to
        // simply to discard the data and start over

        db.execSQL(SQL_DROP_TABLE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void insertUser(UserRecord userRecord) {
        //Prepare record
        ContentValues values = new ContentValues();
        values.put(User.COLUMN_PHONE,
                userRecord.getPhone());
        values.put(User.COLUMN_NAME, userRecord.getName());
        values.put(User.COLUMN_EMAIL, userRecord.getEmail());

        //Insert a row
        SQLiteDatabase database = this.getWritableDatabase();
        database.insert(User.TABLE_NAME, null, values);

        //Close database connection
        database.close();
    }

    public void deleteUser(String id){
        SQLiteDatabase database = this.getWritableDatabase();
        String deleteWhereStatement = allColumn[0] + " = \'" + id +"\'";
        database.delete(User.TABLE_NAME,deleteWhereStatement,null);
        database.close();
    }

    public void updateUser(UserRecord userRecord){
        SQLiteDatabase database = this.getWritableDatabase();
        String updateWhereStatement = allColumn[0] + "=" + userRecord.getId();
        ContentValues updateValue = new ContentValues();
        updateValue.put(User.COLUMN_PHONE, userRecord.getPhone());
        updateValue.put(User.COLUMN_NAME, userRecord.getName());
        updateValue.put(User.COLUMN_EMAIL, userRecord.getEmail());
        database.update(User.TABLE_NAME, updateValue ,updateWhereStatement, null);
    }

    //Get all records
    public List<UserRecord> getAllUsers() {
        List<UserRecord> records = new ArrayList<UserRecord>();

        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.query(User.TABLE_NAME, allColumn, null, null, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            UserRecord userRecord = new UserRecord();
            userRecord.setId(cursor.getString(0));
            userRecord.setName(cursor.getString(1));
            userRecord.setPhone(cursor.getString(2));
            userRecord.setEmail(cursor.getString(3));
            records.add(userRecord);
            cursor.moveToNext();
        }

        return records;
    }

}
