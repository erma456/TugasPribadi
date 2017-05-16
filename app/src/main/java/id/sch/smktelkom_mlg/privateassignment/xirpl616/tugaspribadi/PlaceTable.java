package id.sch.smktelkom_mlg.privateassignment.xirpl616.tugaspribadi;

/**
 * Created by Khofi Muffin on 15/05/2017.
 */
import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class PlaceTable extends Table {

    public static final String NAME = "tmovie";

    public static final String[] COLNAME =
            new String[]{"TITLE", "FOTO"};
    public static final String[] COLTYPE = new String[]{"TEXT", "TEXT"};

    public static List<OfflineListItem> ITEMS = new ArrayList<OfflineListItem>();

    public static String getSQLCreate() {
        return getSQLCreateParam(NAME, COLNAME, COLTYPE);
    }

    public static String getSQLDrop() {
        return getSQLDropParam(NAME);
    }

    private static ContentValues getValues(OfflineListItem place) {
        ContentValues values = new ContentValues();
        values.put(COLNAME[0], place.head);

        values.put(COLNAME[1], place.imageUrl);
        return values;
    }

    public static void getAll(DatabaseHandler db) {
        ITEMS.clear();
        Cursor cursor = db.getAll(NAME);

        if (cursor != null && cursor.getCount() > 0) {
            do {
                OfflineListItem place = new OfflineListItem(cursor.getString(0), cursor.getString(1));
                ITEMS.add(place);
            } while (cursor.moveToNext());
        }
    }

    public static void getPlaceWhereJudul(DatabaseHandler db, String title) {
        ITEMS.clear();
        String query = "SELECT " + NAME + "." + COLNAME[0] + ", " + NAME + "." + COLNAME[1] +
                ", " +
                " FROM " + NAME +
                " WHERE " + NAME + "." + COLNAME[0] + "=?";
        Cursor cursor = db.getWhere(query, new String[]{title});
        if (cursor != null && cursor.getCount() > 0) {
            do {
                OfflineListItem place = new OfflineListItem(cursor.getString(0), cursor.getString(1)
                );
                ITEMS.add(place);
            } while (cursor.moveToNext());
        }
    }

    public static void getPlaceLike(DatabaseHandler db, String text) {
        ITEMS.clear();
        String query = "SELECT * FROM " + NAME +
                " WHERE " + NAME + "." + COLNAME[0] + " LIKE ?" +
                " OR " + NAME + "." + COLNAME[1];
        Cursor cursor = db.getWhere(query,
                new String[]{"%" + text + "%", "%" + text + "%"});
        if (cursor != null && cursor.getCount() > 0) {
            do {
                OfflineListItem place =
                        new OfflineListItem(cursor.getString(0), cursor.getString(1));
                ITEMS.add(place);
            } while (cursor.moveToNext());
        }
    }

    public static boolean isEmpty(DatabaseHandler db) {
        return !db.isExist(NAME, null, null, null);
    }

    private static boolean isExist(DatabaseHandler db, String colName, String value) {
        return db.isExist(NAME, new String[]{colName}, colName + "=?", new String[]{value});
    }

    public static boolean isExistPlace(DatabaseHandler db, String title) {
        return isExist(db, COLNAME[0], title);
    }

    public static void add(DatabaseHandler db, OfflineListItem place) {
        db.add(NAME, getValues(place));
    }

    public static void updateWhereJudul(DatabaseHandler db, String title, OfflineListItem place) {
        db.update(NAME, getValues(place), COLNAME[0] + "=?", new String[]{title});
    }

    private static void delete(DatabaseHandler db, String colName, String value) {
        db.delete(NAME, colName + "=?", new String[]{value});
    }

    public static void delete(DatabaseHandler db, OfflineListItem place) {
        delete(db, COLNAME[0], place.head);
    }

}