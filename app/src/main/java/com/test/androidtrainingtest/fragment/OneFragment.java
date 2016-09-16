package com.test.androidtrainingtest.fragment;

/**
 * Created by Qushay on 15/09/2016.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.androidtrainingtest.R;

public class OneFragment extends Fragment{

    TextView tvNama, tvTglLahir, tvTempatLahir, tvAlamat, tvPekerjaan;

    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        tvNama = (TextView) view.findViewById(R.id.nama);
        tvTglLahir = (TextView) view.findViewById(R.id.tgl_lahir);
        tvTempatLahir = (TextView) view.findViewById(R.id.tempat_lahir);
        tvAlamat = (TextView) view.findViewById(R.id.alamat);
        tvPekerjaan = (TextView) view.findViewById(R.id.pekerjaan);

        tvNama.setText(Html.fromHtml("Nama <b>Don Juans</b>"));
        tvTglLahir.setText(Html.fromHtml("Tgl Lahir <b>31 Febuari 1945</b>"));
        tvTempatLahir.setText(Html.fromHtml("Tempat Lahir <b>Jakarta</b>"));
        tvAlamat.setText(Html.fromHtml("Alamat <b>Jl. Merdeka Barat No 1, Jakarta</b>"));
        tvPekerjaan.setText(Html.fromHtml("Pekerjaan <b>Programmer</b>"));

        return view;
    }

}