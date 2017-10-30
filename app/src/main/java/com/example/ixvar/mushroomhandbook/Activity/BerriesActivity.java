package com.example.ixvar.mushroomhandbook.Activity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ixvar.mushroomhandbook.Model.Berrie;
import com.example.ixvar.mushroomhandbook.R;
import com.example.ixvar.mushroomhandbook.Adapter.ProductAdapter;
import com.example.ixvar.mushroomhandbook.BD.DatabaseHelper;
import com.example.ixvar.mushroomhandbook.Model.Product;

import java.util.ArrayList;
import java.util.List;

public class BerriesActivity extends AppCompatActivity {

    private static final String TAG = BerriesActivity.class.getSimpleName();

    private SQLiteDatabase db;
    private Cursor userCursor;
    private DatabaseHelper handbookDatabaseHelper;

    private List<Berrie> berries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bd_product);

        handbookDatabaseHelper = new DatabaseHelper(getApplicationContext());




        ImageView imageViewHeader = (ImageView) findViewById(R.id.htab_header);
        imageViewHeader.setImageResource(R.drawable.berries_header);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.htab_toolbar);
        setSupportActionBar(toolbar);



        if (getSupportActionBar() != null) getSupportActionBar().setTitle(R.string.buttonBerries);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final ViewPager viewPager = (ViewPager) findViewById(R.id.htab_viewpager);
        setupViewPager(viewPager);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.htab_tabs);
        tabLayout.setupWithViewPager(viewPager);

        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.htab_collapse_toolbar);



        try {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.berries_header); //header
            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                @SuppressWarnings("ResourceType")
                @Override
                public void onGenerated(Palette palette) {

                    int vibrantColor = palette.getVibrantColor(R.color.colorPrimary);
                    int vibrantDarkColor = palette.getDarkVibrantColor(R.color.colorPrimaryDark);
                    collapsingToolbarLayout.setContentScrimColor(vibrantColor);
                    collapsingToolbarLayout.setStatusBarScrimColor(vibrantDarkColor);
                }
            });

        } catch (Exception e) {
            // if Bitmap fetch fails, fallback to primary colors
            Log.e(TAG, "onCreate: failed to create bitmap from background", e.fillInStackTrace());

            collapsingToolbarLayout.setContentScrimColor(
                    ContextCompat.getColor(this, R.color.colorPrimary)
            );
            collapsingToolbarLayout.setStatusBarScrimColor(
                    ContextCompat.getColor(this, R.color.colorPrimaryDark)
            );
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());
                Log.d(TAG, "onTabSelected: pos: " + tab.getPosition());

                switch (tab.getPosition()) {
                    case 0:
                        // TODO: 31/03/17
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }





    //=---------------------------------------

    String getColorBerrie(int idColor)
    {
        Cursor cursorColor;
        String color = "";

        cursorColor = db.query (handbookDatabaseHelper.TABLE_BERRIES_COLOR,
                new String[] {handbookDatabaseHelper.COLUMN_BERRIES_COLOR_NAME},
                "_id = ?",
                new String[] {Integer.toString(idColor)},
                null, null,null);

        if (cursorColor.moveToFirst()) {
            color = cursorColor.getString(0);
        }

        cursorColor.close();

        return color;
    }

    String getTypeBerrie(int idType)
    {
        Cursor cursorType;
        String type = "";

        cursorType = db.query (handbookDatabaseHelper.TABLE_BERRIES_TYPE,
                new String[] {handbookDatabaseHelper.COLUMN_BERRIES_TYPE_NAME},
                "_id = ?",
                new String[] {Integer.toString(idType)},
                null, null,null);

        if (cursorType.moveToFirst()) {
            type = cursorType.getString(0);
        }

        cursorType.close();

        return type;
    }

    String getSizeBerrie(int idSize)
    {
        Cursor cursorSize;
        String size = "";

        cursorSize = db.query (handbookDatabaseHelper.TABLE_BERRIES_SIZE,
                new String[] {handbookDatabaseHelper.COLUMN_BERRIES_SIZE_NAME},
                "_id = ?",
                new String[] {Integer.toString(idSize)},
                null, null,null);

        if (cursorSize.moveToFirst()) {
            size = cursorSize.getString(0);
        }

        cursorSize.close();

        return size;
    }

    String getSeasonsBerrie(int idBerrie)
    {
        Cursor cursorSeason;
        List<Integer> idSeasons = new ArrayList<>();

        String seasons = "";

        cursorSeason = db.query (handbookDatabaseHelper.TABLE_ID_BERRIE__ID_SEASON,
                new String[] {handbookDatabaseHelper.COLUMN_ID_SEASON},
                "id_berrie = ?",
                new String[] {Integer.toString(idBerrie)},
                null, null,null);

        while (cursorSeason.moveToNext()) {
            idSeasons.add(cursorSeason.getInt(0));
        }



        for(Integer idSeason : idSeasons){
            cursorSeason = db.query (handbookDatabaseHelper.TABLE_SEASONS,
                    new String[] {handbookDatabaseHelper.COLUMN_SEASONS_NAME},
                    "_id = ?",
                    new String[] {Integer.toString(idSeason)},
                    null, null,null);

            if (cursorSeason.moveToFirst()) {
                seasons += cursorSeason.getString(0) + " ";
            }
        }


        cursorSeason.close();

        return seasons;
    }

    int[] getPicturesBerrie(int idBerrie)
    {
        Cursor cursorPictures;
        int pictures[] = new int[4];

        cursorPictures = db.query (handbookDatabaseHelper.TABLE_BERRIE_PICTURES,
                new String[] {handbookDatabaseHelper.COLUMN_BERRIE_PICTURES_ID_BERRIE},
                "_id = ?",
                new String[] {Integer.toString(idBerrie)},
                null, null,null);

        int i = 0;
        while (cursorPictures.moveToNext()) {
            pictures[i++] = cursorPictures.getInt(0);
        }

        cursorPictures.close();

        return pictures;
    }


    //=---------------------------------------

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

      try {
            db = handbookDatabaseHelper.getReadableDatabase();
            userCursor =  db.rawQuery("select * from " + handbookDatabaseHelper.TABLE_BERRIES_TYPE, null);
            while (userCursor.moveToNext()) {



                adapter.addFrag(new DummyFragment( ContextCompat.getColor(this, R.color.bg_light_blue),getProducts(userCursor.getInt(0))), userCursor.getString(1) );

            }

        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }



        viewPager.setAdapter(adapter);
    }

    List<Product> getProducts(int idType)
    {
        Cursor cursor;

        List<Product> products;
        products = new ArrayList<>();

        cursor =  db.rawQuery("select * from " + handbookDatabaseHelper.TABLE_BERRIES + " WHERE " + handbookDatabaseHelper.COLUMN_BERRIES_TYPE + " = " + idType , null);



        while (cursor.moveToNext()) {
            products.add(new Product(cursor.getInt(0),cursor.getString(1),cursor.getString(2),R.drawable.spring));
        }




       // products.add(new Product(3,"Seco5ndName","Mo,gg,Meow",R.drawable.spring));

        cursor.close();

        return products;
    }






    private static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }



    public static class DummyFragment extends Fragment {


        int color;
        List<Product> products;


        public DummyFragment() {
        }

        @SuppressLint("ValidFragment")
        public DummyFragment(int color,List<Product> products) {
            this.color = color;
            this.products = products;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.dummy_fragment, container, false);

            final FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.dummyfrag_bg);
            frameLayout.setBackgroundColor(color);

            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.dummyfrag_scrollableview);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setHasFixedSize(true);

//------------------===============----------------------


            //!!!!!!!!!!!!!!!! ЗАменить на ягодную хуйню

            ProductAdapter adapter = new ProductAdapter(products);
            recyclerView.setAdapter(adapter);




           /* products.add(new Product(1,"FirstName","Moloko,mamka,gg,Meow",R.drawable.header));
            products.add(new Product(2,"SecondName","Moloko,mamka,gg,Meow",R.drawable.autumn));
            products.add(new Product(3,"Seco5ndName","Mo,gg,Meow",R.drawable.spring));
            products.add(new Product(4,"Second4Name","Moloko,mamka,gg,Meow",R.drawable.summer));
            products.add(new Product(5,"SecondN4ame","gg,Meow",R.drawable.winter));*/

            return view;
        }
    }












    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bd_poduct, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userCursor.close();
        db.close();
    }
}