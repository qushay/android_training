package com.test.androidtrainingtest.entity;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Qushay on 30/08/2016.
 */

@DatabaseTable
public class User {
    public static final String TAG_UID = "id";

    @SerializedName("success")
    private boolean success;

    @SerializedName(TAG_UID)
    @DatabaseField(columnName = TAG_UID, id = true)
    private int uid;

    @SerializedName("name")
    @DatabaseField(columnName = "name")
    private String name;

    @SerializedName("email")
    @DatabaseField(columnName = "email")
    private String email;

    @SerializedName("tgl_lahir")
    @DatabaseField(columnName = "tgl_lahir")
    private String tglLahir;

    @SerializedName("tempat_lahir")
    @DatabaseField(columnName = "tempat_lahir")
    private String tempatLahir;

    @SerializedName("alamat")
    @DatabaseField(columnName = "alamat")
    private String alamat;

    @SerializedName("pekerjaan")
    @DatabaseField(columnName = "pekerjaan")
    private String pekerjaan;

    @SerializedName("photo")
    @DatabaseField(columnName = "photo")
    private String photo;

    @SerializedName("nomor")
    @DatabaseField(columnName = "nomor")
    private String nomor;

    @SerializedName("latitude")
    @DatabaseField(columnName = "latitude")
    private Double latitude;

    @SerializedName("longitude")
    @DatabaseField(columnName = "longitude")
    private Double longitude;

    @SerializedName("error_message")
    private String errorMessage;

    public boolean isSuccess() {
        return success;
    }

    public int getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getTglLahir() {
        return tglLahir;
    }

    public String getTempatLahir() {
        return tempatLahir;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getPekerjaan() {
        return pekerjaan;
    }

    public String getPhoto() {
        return photo;
    }

    public String getNomor() {
        return nomor;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
