package com.test.androidtrainingtest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.gson.Gson;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.test.androidtrainingtest.datasource.DatabaseHelper;
import com.test.androidtrainingtest.datasource.RestClient;
import com.test.androidtrainingtest.entity.User;
import com.test.androidtrainingtest.fragment.OneFragment;
import com.test.androidtrainingtest.fragment.TwoFragment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = ProfileActivity.class.getSimpleName();

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private RoundedImageView ivPhoto;

    private User mUser;

    private int TAG_CAMERA_REQUEST = 1;

    final Gson gson = new Gson();
    private DatabaseHelper databaseHelper = null;

    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this,DatabaseHelper.class);
        }
        return databaseHelper;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int uid = extras.getInt(User.TAG_UID);
            try {
                final Dao<User, Integer> userDao = getHelper().getUserDao();
                mUser = userDao.queryForId(uid);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        ivPhoto = (RoundedImageView) findViewById(R.id.profile_photo);
        ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCamera();
            }
        });
        Picasso picasso = new Picasso.Builder(this)
                .build();
        picasso.setIndicatorsEnabled(true);
        picasso.load(RestClient.IMAGE_URL + mUser.getPhoto())
                .placeholder(R.drawable.placeholder)
                .into(ivPhoto);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.profile_tab);
        tabLayout.setupWithViewPager(viewPager);

    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OneFragment().newInstance(mUser), "Profil");
        adapter.addFragment(new TwoFragment().newInstance(mUser), "Lokasi");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
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

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void startCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI.getPath());
        startActivityForResult(intent, TAG_CAMERA_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TAG_CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap mphoto = (Bitmap) data.getExtras().get("data");
            ivPhoto.setImageBitmap(mphoto);
        }
    }
}