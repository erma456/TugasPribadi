package id.sch.smktelkom_mlg.privateassignment.xirpl616.tugaspribadi;


import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class OfflineFragment extends Fragment {
    //private static final String URL_DATA = "https://api.themoviedb.org/3/movie/now_playing?api_key=18e7d23a486ff80cb18ece940e1e7feb";

    public static final String PLACE = "place";
    public static final int REQUEST_CODE_ADD = 88;
    public static final int REQUEST_CODE_EDIT = 99;
    public static DatabaseHandler mDb;
    Context context;
    String mQuery;
    //private RecyclerView.Adapter adapter;
    OfflineAdapter adapter;
    ArrayList<OfflineListItem> mList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewMovie;
    //private RecyclerView.Adapter adaptera;

    public OfflineFragment() {
        // Required empty public constructor
    }

    //    private List<OfflineListItem> listItems;
    public static void initDB(Context context) {
        int dbVersion = 1;
        String dbName = "movies.db";
        mDb = new DatabaseHandler(context, dbName, dbVersion) {

            @Override
            protected void dropTables(SQLiteDatabase db) {
                db.execSQL(PlaceTable.getSQLDrop());
            }

            @Override
            protected void createTables(SQLiteDatabase db) {
                db.execSQL(PlaceTable.getSQLCreate());
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offline, container, false);
        // Inflate the layout for this fragment
//        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        LinearLayoutManager layoutManagera = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mList = new ArrayList<>();
        recyclerViewMovie = (RecyclerView) view.findViewById(R.id.recyclerViewMovie);
        adapter = new OfflineAdapter(mList, getActivity().getApplicationContext());
        recyclerViewMovie.setAdapter(adapter);
        recyclerViewMovie.setLayoutManager(layoutManagera);
        recyclerViewMovie.setHasFixedSize(true);


        // loadRecyclerViewData();
        initDB(getActivity());
        if (PlaceTable.isEmpty(mDb))
            fillDataToDB();

        refreshData(null);

        return view;
    }

//    public void onActivityResult(int requestCode, int resultCode, Intent data)
//    {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_CODE_ADD && resultCode == RESULT_OK)
//        {
//            refreshData(mQuery);
//        }
//        else if (requestCode == REQUEST_CODE_EDIT && resultCode == RESULT_OK)
//        {
//            refreshData(mQuery);
//        }
//    }


    private void fillDataToDB() {
        Resources resources = getResources();//error: The method getResources() is undefined for the type DbAdapter
        String[] arTitle = resources.getStringArray(R.array.tittle);
        TypedArray a = resources.obtainTypedArray(R.array.places_picture);
        String[] arFoto = new String[a.length()];
        for (int i = 0; i < arFoto.length; i++) {
            int id = a.getResourceId(i, 0);
            arFoto[i] = ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                    + resources.getResourcePackageName(id) + '/'
                    + resources.getResourceTypeName(id) + '/'
                    + resources.getResourceEntryName(id);
        }
        a.recycle();
        for (int i = 0; i < arTitle.length; i++) {
            PlaceTable.add(mDb, new OfflineListItem(arTitle[i], arFoto[i]));
        }


    }

    private void refreshData(String query) {
        mList.clear();

        if (query == null || query.isEmpty())
            PlaceTable.getAll(mDb);
        else
            PlaceTable.getPlaceLike(mDb, query);

        mList.addAll(PlaceTable.ITEMS);
        adapter.notifyDataSetChanged();
    }


//    private void loadRecyclerViewData() {
//        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setMessage("Loading data...");
//        progressDialog.show();
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET,
//                URL_DATA,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String s) {
//                        progressDialog.dismiss();
//                        try {
//                            JSONObject jsonObject = new JSONObject(s);
//
//                            //JSONArray array = jsonObject.getJSONObject("data").getJSONArray("results");
//                            JSONArray array = jsonObject.getJSONArray("results");
//                            //JSONArray array2 = jsonObject.getJSONArray("multimedia");
//
//                            for (int i = 0; i < array.length(); i++) {
//                                JSONObject o = array.getJSONObject(i);
//
//                                OfflineListItem item = new OfflineListItem(
//                                        o.getString("poster_path"),
//                                        o.getString("title"),
//                                        o.getString("release_date")
//                                );
//                                listItems.add(item);
//
//                            }
//                            adapter = new OfflineAdapter(listItems, getActivity().getApplicationContext());
//                            recyclerView.setAdapter(adapter);
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError volleyError) {
//                        progressDialog.dismiss();
//                        Toast.makeText(getActivity().getApplicationContext(), volleyError.getMessage(), Toast.LENGTH_LONG).show();
//
//
//                    }
//                });
//
//        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
//        requestQueue.add(stringRequest);
//    }

}