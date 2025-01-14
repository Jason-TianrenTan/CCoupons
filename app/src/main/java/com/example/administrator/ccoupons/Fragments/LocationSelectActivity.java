package com.example.administrator.ccoupons.Fragments;

import android.content.Context;
<<<<<<< HEAD
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.*;
import android.os.Message;
=======
import android.os.Bundle;
<<<<<<< HEAD
>>>>>>> ttr
=======
import android.os.Handler;
>>>>>>> Czj
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
<<<<<<< HEAD
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
=======
import android.widget.EditText;
>>>>>>> ttr
import android.widget.TextView;

import com.example.administrator.ccoupons.Data.GlobalConfig;
import com.example.administrator.ccoupons.Events.CouponListEvent;
import com.example.administrator.ccoupons.Events.SelectLocationEvent;
import com.example.administrator.ccoupons.R;
import com.example.administrator.ccoupons.Register.RegisterIdentifyActivity;
import com.example.administrator.ccoupons.Tools.LocationGet;
<<<<<<< HEAD
import com.example.administrator.ccoupons.Tools.PixelUtils;
<<<<<<< HEAD
import com.example.administrator.ccoupons.UI.CustomDialog;
import com.example.administrator.ccoupons.UI.CustomLoader;
=======
>>>>>>> ttr
=======
import com.example.administrator.ccoupons.Tools.PixelUtils.PixelUtils;
>>>>>>> Czj
import com.example.administrator.ccoupons.UI.QuickIndexBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LocationSelectActivity extends AppCompatActivity {


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @BindView(R.id.input_search)
    EditText inputSearch;
    @BindView(R.id.location_select_toolbar)
    Toolbar toolbar;
    @BindView(R.id.location_select_textview)
    TextView locationText;
    @BindView(R.id.current_gps_cardview)
    CardView gpsCardView;
    @BindView(R.id.popular_city_recyclerview)
    RecyclerView popularCityRecyclerview;
    @BindView(R.id.citylist_recyclerview)
    RecyclerView citylistRecyclerview;
    @BindView(R.id.location_nestscrollview)
    NestedScrollView locationNestscrollview;
    @BindView(R.id.location_sideindexbar)
    QuickIndexBar locationSideindexbar;

    private String location = null;
    private LocationGet locationFetchr;
    private ArrayList<String> cityList = new ArrayList<>();
    private ArrayList<String> pop_cityList = new ArrayList<>();
    private RecyclerView popCityRecyclerView, recyclerView;
    private int[] CharIndex = new int[26];

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventCall(SelectLocationEvent locEvent) {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_select);
        ButterKnife.bind(this);
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String localCity = getIntent().getStringExtra("location");
        if (localCity != null) {
            location = localCity;
            locationText.setText(localCity);
        } else {
            locationFetchr = new LocationGet(this, locationText);
            locationFetchr.requestLocation();
        }


        cityList = getCityList();
        final LocationAdapter adapter = new LocationAdapter(cityList);
        recyclerView = (RecyclerView) findViewById(R.id.citylist_recyclerview);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setItemPrefetchEnabled(true);
        layoutManager.setInitialPrefetchItemCount(GlobalConfig.Cities.cityList.length + 1);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
        recyclerView.addItemDecoration(dividerItemDecoration);

        final NestedScrollView scrollView = (NestedScrollView) findViewById(R.id.location_nestscrollview);
        gpsCardView = (CardView) findViewById(R.id.current_gps_cardview);

        pop_cityList = getPopularCityList();
        popCityRecyclerView = (RecyclerView) findViewById(R.id.popular_city_recyclerview);
        PCityAdapter pAdapter = new PCityAdapter(pop_cityList);
        popCityRecyclerView.setAdapter(pAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        popCityRecyclerView.setLayoutManager(gridLayoutManager);
        QuickIndexBar indexBar = (QuickIndexBar) findViewById(R.id.location_sideindexbar);
        indexBar.setOnLetterChangeListener(new QuickIndexBar.OnLetterChangeListener() {
            @Override
            public void onLetterChange(String letter) {
                if (letter.length() == 1) {
                    char[] arr = letter.toCharArray();
                    char ch = arr[0];
                    int index = ch - 'A';
                    scroll(scrollView, index);
                }
                else {
                    scrollView.smoothScrollTo(0, 0);
                }
            }

            @Override
            public void onRelease() {

            }
        });
    }


    /**
     * Scroll to index
     * @param scrollView current scrollview activated
     * @param index target index
     */
    private void scroll(NestedScrollView scrollView, int index) {
        int ViewHeight = popCityRecyclerView.getHeight() + gpsCardView.getHeight();
        int y = ViewHeight + CharIndex[index] * PixelUtils.dp2px(this, 45) + PixelUtils.dp2px(this, CharIndex[index]);
        scrollView.smoothScrollTo(0, y);
    }


<<<<<<< HEAD

    private void startCountDown() {
        customLoader = new CustomLoader(5, handler, this);
        customLoader.setLoaderListener(new CustomLoader.CustomLoaderListener() {
            @Override
            public void onTimeChanged() {

            }

            @Override
            public void onTimeFinish() {
                Message msg = new Message();
                msg.what = MessageType.LOCATION_FAILED;
                handler.sendMessage(msg);
            }

        });
        customLoader.start();
    }


=======
    /**
     * return list of popular cities
     * @return
     */
>>>>>>> ttr
    private ArrayList<String> getPopularCityList() {
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < GlobalConfig.Cities.popCityList.length; i++) {
            String str = GlobalConfig.Cities.popCityList[i];
            arrayList.add(str);
        }
        return arrayList;
    }


    /**
     * return city list
     * @return
     */
    private ArrayList<String> getCityList() {

        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < GlobalConfig.Cities.cityList.length; i++) {
            String str = GlobalConfig.Cities.cityList[i];
            if (str.length() == 1) {//是字母
                char ch = str.charAt(0);
                CharIndex[ch - 'A'] = i;

            }
            arrayList.add(str);
        }
        return arrayList;
    }


    //Popular Cities Adapter
    public class PCityAdapter extends RecyclerView.Adapter<PCityAdapter.PCityViewHolder> {

        private Context mContext;
        private ArrayList<String> mCityList;

        public class PCityViewHolder extends RecyclerView.ViewHolder {
            TextView textView;
            CardView rootView;

            public PCityViewHolder(View view) {
                super(view);
                rootView = (CardView) view;
                textView = (TextView) view.findViewById(R.id.pop_city_textview);
            }
        }


        public PCityAdapter(ArrayList<String> cList) {
            this.mCityList = cList;
        }


        @Override
        public PCityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (mContext == null) {
                mContext = parent.getContext();
            }
            View view = LayoutInflater.from(mContext).inflate(R.layout.popular_city_item, parent, false);
            final PCityViewHolder holder = new PCityViewHolder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(PCityViewHolder holder, int position) {
            String Location = mCityList.get(position);
            holder.textView.setText(Location);
        }

        @Override
        public int getItemCount() {
            return mCityList.size();
        }
    }


}
