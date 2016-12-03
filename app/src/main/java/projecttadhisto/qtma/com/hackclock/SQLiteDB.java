package projecttadhisto.qtma.com.hackclock;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// class to create the sqlite database
public class SQLiteDB extends SQLiteOpenHelper {

    // database name and version
    // name needs .db
    private static final String DATABASE_NAME = "notes.db";
    private static final int DATABASE_VERSION = 1;

    // database table and columns
    // id will be primary key and content provider will expect it to have "_" in front
    public static final String TABLE_ALARMS = "alarms";
    public static final String ALARM_ID = "_id";
    public static final String ALARM_HOUR = "alarmHour";
    public static final String ALARM_MINUTE = "alarmMinute";
    public static final String ALARM_AM_PM = "alarmAmPm";
    public static final String ALARM_CREATED = "alarmCreated";

    public static final String[] ALL_COLUMNS =
            { ALARM_ID, ALARM_HOUR, ALARM_MINUTE, ALARM_AM_PM, ALARM_CREATED };

    // SQL to create table
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_ALARMS + " (" +
                    ALARM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ALARM_HOUR + " INTEGER, " +
                    ALARM_MINUTE + " INTEGER, " +
                    ALARM_AM_PM + " TEXT, " +
                    ALARM_CREATED + " TEXT default CURRENT_TIMESTAMP" +
                    ")";

    // constructor
    public SQLiteDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // called the first time this class is instantiated
    // creates database structure if it does not exist yet
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_CREATE);
    }

    // called when database version is changed and
    // user opens app first time after database updated
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ALARMS);
        onCreate(sqLiteDatabase);
    }
}