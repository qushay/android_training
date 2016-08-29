package com.test.androidtrainingtest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.test.androidtrainingtest.R;

/**
 * Created by Qushay on 30/08/2016.
 */
public class DaftarPertanyaanAdapter extends BaseAdapter {

    private final Context mContext;
    LayoutInflater inflater;
    private String[] mPertanyaan;
    private int[] mJumlah;

    public DaftarPertanyaanAdapter(Context context, String[] pertayaan, int[] jumlah) {
        this.mContext = context;
        this.inflater = LayoutInflater.from(this.mContext);
        this.mPertanyaan = pertayaan;
        this.mJumlah = jumlah;
    }

    @Override
    public int getCount() {
        return mPertanyaan.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder mViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_pertanyaan, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        String pertanyaan = mPertanyaan[position];
        int jumlah = mJumlah[position];

        mViewHolder.tvTitle.setText(pertanyaan);
        mViewHolder.tvJumlah.setText(String.valueOf(jumlah));

        return convertView;
    }

    private class MyViewHolder {
        TextView tvTitle, tvJumlah;
        public MyViewHolder(View item) {
            tvTitle = (TextView) item.findViewById(R.id.tvTitle);
            tvJumlah = (TextView) item.findViewById(R.id.tvJumlah);
        }
    }
}
