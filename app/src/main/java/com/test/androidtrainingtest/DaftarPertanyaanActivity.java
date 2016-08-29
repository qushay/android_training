package com.test.androidtrainingtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.test.androidtrainingtest.adapter.DaftarPertanyaanAdapter;

public class DaftarPertanyaanActivity extends AppCompatActivity {

    GridView mGridView;
    String[] mTitle = {"Title 1","Title 2","Title 3","Title 4","Title 5",
            "Title 6","Title 7","Title 8","Title 9","Title 10"};
    int[] mJumlah = {3,6,2,5,7,7,2,3,5,5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_pertanyaan);
        mGridView = (GridView) findViewById(R.id.gridView);
        DaftarPertanyaanAdapter adapter = new DaftarPertanyaanAdapter(this,mTitle,mJumlah);
        mGridView.setAdapter(adapter);
        mGridView.setOnItemClickListener(new ListviewAction());
    }

    class ListviewAction implements ListView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Toast.makeText(DaftarPertanyaanActivity.this
                    ,mTitle[i]
                    ,Toast.LENGTH_SHORT)
                    .show();
        }
    }

}
