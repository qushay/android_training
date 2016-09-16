package com.test.androidtrainingtest.fragment;

/**
 * Created by Qushay on 15/09/2016.
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.test.androidtrainingtest.R;
import com.test.androidtrainingtest.entity.User;

public class OneFragment extends Fragment{

    TextView tvNama, tvTglLahir, tvTempatLahir, tvAlamat, tvPekerjaan;
    Button btCall, btSms;
    String mNumber;
    private User mUser;

    public OneFragment() {
        // Required empty public constructor
    }

    public static OneFragment newInstance(User user) {
        OneFragment fragment = new OneFragment();
        fragment.mUser = user;
        fragment.mNumber = user.getNomor();

        return fragment;
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

        btCall = (Button) view.findViewById(R.id.call);
        btSms = (Button) view.findViewById(R.id.sms);

        tvNama = (TextView) view.findViewById(R.id.nama);
        tvTglLahir = (TextView) view.findViewById(R.id.tgl_lahir);
        tvTempatLahir = (TextView) view.findViewById(R.id.tempat_lahir);
        tvAlamat = (TextView) view.findViewById(R.id.alamat);
        tvPekerjaan = (TextView) view.findViewById(R.id.pekerjaan);

        btCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.fromParts("tel", mNumber, null));
                startActivity(intent);
            }
        });

        btSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.fromParts("sms", mNumber, null));
                startActivity(intent);
            }
        });

        tvNama.setText(Html.fromHtml("Nama <b>"+mUser.getName()+"</b>"));
        tvTglLahir.setText(Html.fromHtml("Tgl Lahir <b>"+mUser.getTglLahir()+"</b>"));
        tvTempatLahir.setText(Html.fromHtml("Tempat Lahir <b>"+mUser.getTempatLahir()+"</b>"));
        tvAlamat.setText(Html.fromHtml("Alamat <b>"+mUser.getAlamat()+"</b>"));
        tvPekerjaan.setText(Html.fromHtml("Pekerjaan <b>"+mUser.getPekerjaan()+"</b>"));

        return view;
    }

}