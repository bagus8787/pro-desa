package com.example.pro_desa.network;

import com.example.pro_desa.model.Pengumuman;
import com.example.pro_desa.network.response.ArtikelResponse;
import com.example.pro_desa.network.response.BantuanResponse;
import com.example.pro_desa.network.response.BaseResponse;
import com.example.pro_desa.network.response.LogOutResponse;
import com.example.pro_desa.network.response.PengaduanResponse;
import com.example.pro_desa.network.response.PengumumanResponse;
import com.example.pro_desa.network.response.RegisterResponse;
import com.example.pro_desa.network.response.ResponseDataAwal;
import com.example.pro_desa.network.response.UserResponse;
import com.example.pro_desa.network.response.region_response.DesaResponse;
import com.example.pro_desa.network.response.region_response.KabupatenResponse;
import com.example.pro_desa.network.response.region_response.KecamatanResponse;
import com.example.pro_desa.network.response.region_response.ProvinsiResponse;
import com.google.gson.annotations.Expose;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {
    @Headers({"Accept: application/json"})

//    BASERESPONSE
    @GET("api/login")
    Call<BaseResponse> baseResponse();


    @FormUrlEncoded
    @POST("api/login")
    Call<ResponseDataAwal> postLogin(@Field("email") String email,
                                     @Field("password") String password);

    @FormUrlEncoded
    @POST("api/register")
    Call<RegisterResponse> postRegister(
            @Field("nik") String nik,
            @Field("fullname") String fullname,
            @Field("email") String email,
            @Field("password") String password,
            @Field("provinsi_id") Integer provinsi_id,
            @Field("kota_id") Integer kota_id,
            @Field("kec_id") Integer kec_id,
            @Field("desa_id") long desa_id,
            @Field("asal_provinsi_id") Integer asal_provinsi_id,
            @Field("asal_kota_id") Integer asal_kota_id,
            @Field("asal_kec_id") Integer asal_kec_id,
            @Field("asal_desa_id") long asal_desa_id);

    //user
    @GET("api/account/profile/{appDesaCode}/{nik}")
    Call<UserResponse> getUser(@Header("App-Token") String app_token,
                       @Header("ProDesa-Token") String prodesa_token,
                       @Path("appDesaCode") String appdesa_code,
                       @Path("nik") String nik
    );

//    Log Out
    @GET("api/logout/{id}")
    Call<LogOutResponse> log_out(@Path("id") int id);

//    Get Artikel
    @GET("api/artikel/{appDesaCode}/all")
    Call<ArtikelResponse> getArtikel(@Header("app-token") String app_token,
                                     @Header("prodesa-token") String prodesa_token,
                                     @Path("appDesaCode") String appdesa_code
    );

//    Get Pengaduan
    @GET("api/pengaduan/{appDesaCode}/all")
    Call<PengaduanResponse> getPengaduan(@Header("app-token") String app_token,
                                         @Header("prodesa-token") String prodesa_token,
                                         @Path("appDesaCode") String appdesa_code
    );

    //    Get Bantuan
    @GET("api/pengaduan/{appDesaCode}/all")
    Call<BantuanResponse> getBantuan(@Header("app-token") String app_token,
                                       @Header("prodesa-token") String prodesa_token,
                                       @Path("appDesaCode") String appdesa_code
    );

//    http://222.124.168.221:8500/demo/prodesa/api/pengumuman/3403042009/all-masyarakat/3502082208970002
    //    Get Pengumuman
    @GET("api/pengumuman/{appDesaCode}/all-masyarakat/{nik}")
    Call<PengumumanResponse> getPengumuman(@Header("app-token") String app_token,
                                           @Header("prodesa-token") String prodesa_token,
                                           @Path("appDesaCode") String appdesa_code,
                                           @Path("nik") String nik
    );

    //    getProv
    @GET("api/get-provinsi")
    Call<ProvinsiResponse> getProv();

    //    getKab
    @GET("api/get-kabupaten/{provinsi_id}")
    Call<KabupatenResponse> getKab(@Path("provinsi_id") String provinsi_id);

    //    getKec
    @GET("api/get-kecamatan/{kabupaten_id}")
    Call<KecamatanResponse> getKec(@Path("kabupaten_id") String kabupaten_id);

    //    getDesa
    @GET("api/get-kelurahan/{kecamatan_id}")
    Call<DesaResponse> getDesa(@Path("kecamatan_id") String kecamatan_id);


//    @GET("api/admin/barang")
//    Call<ArrayList<Barang>> getBarangList(@Header("Authorization") String token, @Query("type") String type);

//    @GET("api/home/transaksi/{id}")
//    Call<PointHistory> getUserPoint(@Header("Authorization") String token, @Field("id") int id);
}
