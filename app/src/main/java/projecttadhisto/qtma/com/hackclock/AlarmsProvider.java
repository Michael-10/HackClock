package projecttadhisto.qtma.com.hackclock;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

public class AlarmsProvider extends ContentProvider {

    private static final String AUTHORITY = "projecttadhisto.qtma.com.hackclock.alarmsprovider";
    private static final String BASE_PATH = "notes";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH );

    // Constant to identify the requested operation
    private static final int ALARMS = 1;
    private static final int ALARMS_ID = 2;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private SQLiteDatabase db;

    static {
        uriMatcher.addURI(AUTHORITY, BASE_PATH, ALARMS);
        uriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", ALARMS_ID);
    }

    @Override
    public boolean onCreate() {
        SQLiteDB helper = new SQLiteDB(getContext());
        db = helper.getReadableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String selection, String[] strings1, String s1) {
        // Tells you if the uri matches the NOTES_ID uri, you only want a single row from the database
        if (uriMatcher.match(uri) == ALARMS_ID) {
            selection = SQLiteDB.ALARM_ID + "=" + uri.getLastPathSegment();
        }
        return db.query(SQLiteDB.TABLE_ALARMS, SQLiteDB.ALL_COLUMNS,
                selection, null, null, null, SQLiteDB.ALARM_CREATED + " DESC");
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        long id = db.insert(SQLiteDB.TABLE_ALARMS,
                null, contentValues);
        return Uri.parse(BASE_PATH + "/" + id);    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return db.delete(SQLiteDB.TABLE_ALARMS, s, strings);
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return db.update(SQLiteDB.TABLE_ALARMS, contentValues, s, strings);
    }
}
