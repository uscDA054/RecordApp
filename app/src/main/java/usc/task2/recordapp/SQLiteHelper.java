package usc.task2.recordapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {

    //  check-in records Table Columns names
    // “Title”, “Place”, “Details”,
    //“Date”, “Location”, “Image”, “Share”.
    private static final String KEY_TITLE = "title";
    private static final String KEY_PLACE = "place";
    private static final String KEY_DETAILS = "details";
    private static final String KEY_DATE = "date";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_LONGITUDE = "longitude";
    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_IMGPATH = "imgpath";

    private static final String TAG = "DbHelper";
    private static final String DATABASE_NAME = "log.db";
    private static final String TABLE_LOG = "logRecord";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase dbase;

    private static String CREATE_LOG_TABLE = "CREATE TABLE " + TABLE_LOG
            + "(" + KEY_TITLE + " TEXT ," + KEY_PLACE + " TEXT," + KEY_DETAILS
            + " TEXT,"  + KEY_DATE + " TEXT," + KEY_LONGITUDE + "TEXT, "+  KEY_LATITUDE + "TEXT, " +  KEY_IMGPATH + "TEXT, " + ")";


    // Login table For Future development
    private static final String TABLE_LOGIN = "login";
    // Login Table Columns names
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";

    private static String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_LOGIN
            + "(" + KEY_USERNAME + " TEXT," + KEY_PASSWORD + " TEXT" + ")";



    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        onUpgrade(dbase,1,2);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String CREATE_LOG_TABLE = "CREATE TABLE " + TABLE_LOG
                    + "(" + KEY_TITLE + " TEXT ," + KEY_PLACE + " TEXT," + KEY_DETAILS
                    + " TEXT,"  + KEY_DATE + " TEXT," + KEY_LONGITUDE + " TEXT, "+  KEY_LATITUDE + " TEXT, " +  KEY_IMGPATH + " TEXT " + ")";
            System.out.println(CREATE_LOG_TABLE);
            db.execSQL(CREATE_LOG_TABLE);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        // Drop older table if existed
        // Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
        //        + newVersion + ", which will destroy all old data");
       // db.execSQL("DROP TABLE IF EXISTS TABLE_LOG");

        //onCreate(db);

    }
    // ---insert a Log Record into the database---
   public long insertLog(Logs logRec) {//String title,String place,String details,String date,String lat,String longt,String imgPath) {
        dbase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, logRec.getTitle()); // srno
        values.put(KEY_PLACE, logRec.getPlace());
        values.put(KEY_DETAILS, logRec.getDetails()); // Agent Code
        values.put(KEY_DATE,logRec.getLogDt());
        values.put(KEY_LONGITUDE,logRec.getLongitude());
        values.put(KEY_LATITUDE,logRec.getLatitude());
        values.put(KEY_IMGPATH,logRec.getImagePath());
        // Inserting Row
        return dbase.insert(TABLE_LOG, null, values);
    }


    //Get all Groceries
    public List<Logs> getAllLogs() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Logs> logList = new ArrayList<>();

                Cursor cursor = db.query(TABLE_LOG, new String[]{
                KEY_TITLE, KEY_PLACE, KEY_DETAILS,KEY_DATE,KEY_LONGITUDE,KEY_LATITUDE,KEY_IMGPATH
                }, null, null, null, null, KEY_TITLE + " DESC");

        if (cursor.moveToFirst()) {
            do {
                Logs logrec = new Logs();
                logrec.setTitle(cursor.getString(cursor.getColumnIndex(KEY_TITLE)));
                logrec.setPlace(cursor.getString(cursor.getColumnIndex(KEY_PLACE)));
                logrec.setDetails(cursor.getString(cursor.getColumnIndex(KEY_DETAILS)));
                logrec.setLogDt(cursor.getString(cursor.getColumnIndex(KEY_DATE)));
                logrec.setLongitude(cursor.getString(cursor.getColumnIndex(KEY_LONGITUDE)));
                logrec.setLatitude(cursor.getString(cursor.getColumnIndex(KEY_LATITUDE)));
                logrec.setImagePath(cursor.getString(cursor.getColumnIndex(KEY_IMGPATH)));

                // Add to the pantryList
                logList.add(logrec);

            } while (cursor.moveToNext());
        }

        return logList;
    }

    //Delete Pantry
    public void deleteLog(String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LOG, KEY_TITLE + " = ?",
                new String[]{title});

        db.close();

    }


    //Get count
    public int getLogCount() {
        String countQuery = "SELECT * FROM " + TABLE_LOG;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery, null);

        return cursor.getCount();
    }


}
