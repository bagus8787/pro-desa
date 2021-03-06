package com.example.pro_desa.network;

import com.example.pro_desa.model.ListFile;
import com.example.pro_desa.model.PermohonanSurat;
import com.example.pro_desa.network.response.ArtikelResponse;
import com.example.pro_desa.network.response.BantuanResponse;
import com.example.pro_desa.network.response.BaseResponse;
import com.example.pro_desa.network.response.LogOutResponse;
import com.example.pro_desa.network.response.PengaduanResponse;
import com.example.pro_desa.network.response.PengumumanResponse;
import com.example.pro_desa.network.response.ListPermohonanSuratResponse;
import com.example.pro_desa.network.response.PermohonanSuratResponse;
import com.example.pro_desa.network.response.RegisterResponse;
import com.example.pro_desa.network.response.ResponseDataAwal;
import com.example.pro_desa.network.response.SyaratPermohonanSuratResponse;
import com.example.pro_desa.network.response.UserResponse;
import com.example.pro_desa.network.response.file_response.FileResponse;
import com.example.pro_desa.network.response.file_response.PermohonanFileResponse;
import com.example.pro_desa.network.response.kategori_response.KategoriPengaduanResponse;
import com.example.pro_desa.network.response.region_response.DesaResponse;
import com.example.pro_desa.network.response.region_response.KabupatenResponse;
import com.example.pro_desa.network.response.region_response.KecamatanResponse;
import com.example.pro_desa.network.response.region_response.ProvinsiResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiInterface {
    @Headers({"Accept: application/json"})

//    BASERESPONSE
    @GET("login")
    Call<BaseResponse> baseResponse();

//Login
    @FormUrlEncoded
    @POST("login")
    Call<ResponseDataAwal> postLogin(@Field("email") String email,
                                     @Field("password") String password);

//Register
    @FormUrlEncoded
    @POST("register")
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
            @Field("asal_desa_id") long asal_desa_id
    );

    @Multipart
    @POST("account/update-foto")
    Call<BaseResponse> updateProfil(@Part MultipartBody.Part file);

    //user
    @GET("account/profile/{appDesaCode}/{nik}")
    Call<UserResponse> getUser(@Header("App-Token") String app_token,
                       @Header("ProDesa-Token") String prodesa_token,
                       @Path("appDesaCode") String appdesa_code,
                       @Path("nik") String nik
    );

//    Log Out
    @GET("logout/{id}")
    Call<LogOutResponse> log_out(@Path("id") int id);

//    ListPermohonanSurat
    @GET("list-surat/{appDesaCode}/all")
    Call<ListPermohonanSuratResponse> getPermohonanSuratKategori(
            @Header("App-Token") String app_token,
            @Header("ProDesa-Token") String prodesa_token,
            @Path("appDesaCode") String appdesa_code
    );

//    SyaratPermohonanSurat
    @GET("list-surat/{appDesaCode}/syarat/{id_leter}")
    Call<SyaratPermohonanSuratResponse> getSyaratPermohonanSurat(
            @Header("App-Token") String app_token,
            @Header("ProDesa-Token") String prodesa_token,
            @Path("appDesaCode") String appdesa_code,
            @Path("id_leter") String id_leter
    );

//    Ganti password
    @FormUrlEncoded
    @POST("account/update-password/{nik}")
    Call<BaseResponse> resetPassword (
            @Header("App-Token") String app_token,
            @Header("ProDesa-Token") String prodesa_token,
            @Path("nik") String nik,
            @Field("password") String pasword);

//    Get Artikel
    @GET("artikel/{appDesaCode}/all")
    Call<ArtikelResponse> getArtikel(@Header("app-token") String app_token,
                                     @Header("prodesa-token") String prodesa_token,
                                     @Path("appDesaCode") String appdesa_code
    );

//    Get Pengaduan
    @GET("pengaduan/{appDesaCode}/all/{nik}")
    Call<PengaduanResponse> getPengaduan(@Header("app-token") String app_token,
                                         @Header("prodesa-token") String prodesa_token,
                                         @Path("appDesaCode") String appdesa_code,
                                         @Path("nik") String nik
    );

    //    Get Bantuan
    @GET("pengaduan/{appDesaCode}/all")
    Call<BantuanResponse> getBantuan(@Header("app-token") String app_token,
                                       @Header("prodesa-token") String prodesa_token,
                                       @Path("appDesaCode") String appdesa_code
    );

    //    Get Pengumuman
    @GET("pengumuman/{appDesaCode}/all-masyarakat/{nik}")
    Call<PengumumanResponse> getPengumuman(@Header("app-token") String app_token,
                                           @Header("prodesa-token") String prodesa_token,
                                           @Path("appDesaCode") String appdesa_code,
                                           @Path("nik") String nik
    );

    //    Get Permohonan Surat
    @GET("permohonan-surat/{appDesaCode}/all/{nik}")
    Call<PermohonanSurat> getPermohonanSurat(@Header("app-token") String app_token,
                                             @Header("prodesa-token") String prodesa_token,
                                             @Path("appDesaCode") String appdesa_code,
                                             @Path("nik") String nik
    );

    //    getProv
    @GET("get-provinsi")
    Call<ProvinsiResponse> getProv();

    //    getKab
    @GET("get-kabupaten/{provinsi_id}")
    Call<KabupatenResponse> getKab(@Path("provinsi_id") String provinsi_id);

    //    getKec
    @GET("get-kecamatan/{kabupaten_id}")
    Call<KecamatanResponse> getKec(@Path("kabupaten_id") String kabupaten_id);

    //    getDesa
    @GET("get-kelurahan/{kecamatan_id}")
    Call<DesaResponse> getDesa(@Path("kecamatan_id") String kecamatan_id);

    //    getKategoriPengaduan
    @GET("pengaduan/{appDesaCode}/kategori")
    Call<KategoriPengaduanResponse> getKategoriPengaduan(@Header("app-token") String app_token,
                                                         @Header("prodesa-token") String prodesa_token,
                                                         @Path("appDesaCode") String appDesaCode);

    //    Get File
    @GET("permohonan-surat/{appDesaCode}/get-file/{nik}")
    Call<PermohonanFileResponse> getFile(@Header("app-token") String app_token,
                                         @Header("prodesa-token") String prodesa_token,
                                         @Path("appDesaCode") String appdesa_code,
                                         @Path("nik") String nik
    );

    @GET("permohonan-surat/{appDesaCode}/get-file/{nik}")
    Call<PermohonanFileResponse> getFile2(@Header("app-token") String app_token,
                            @Header("prodesa-token") String prodesa_token,
                            @Path("appDesaCode") String appdesa_code,
                            @Path("nik") String nik
    );

//    Add Pengaduan
    @Multipart
    @POST("pengaduan/{appDesaCode}/create")
    Call<BaseResponse> addPengaduan (
            @Header("App-Token") String app_token,
            @Header("ProDesa-Token") String prodesa_token,
            @Path("appDesaCode") String appDesaCode,
            @Part("title") RequestBody title,
            @Part("description") RequestBody description,
            @Part MultipartBody.Part foto_pendukung,
            @Part("kategori_pengaduan") RequestBody kategori_pengaduan,
            @Part("nik") RequestBody nik
//            @Part("detail_lokasi") RequestBody detail_lokasi
            );

//    Add Permohonan Surat
    @FormUrlEncoded
    @POST("permohonan-surat/{appDesaCode}/create/{nik}")
    Call<BaseResponse> addPermohonanSurat(
            @Header("App-Token") String app_token,
            @Header("ProDesa-Token") String prodesa_token,
            @Path("appDesaCode") String appDesaCode,
            @Path("nik") String nik,
            @Field("id_surat") String id_surat,
            @Field("keterangan") String keterangan,
            @Field("no_hp_aktif") String no_hp_aktif,
            @Field("syarat") String syarat,
            @Field("keterangan_tambahan") String keterangan_tambahan
    );

//    Cancel Permohonan
    @FormUrlEncoded
    @POST("permohonan-surat/{appDesaCode}/all/{nik}/{id}/rejected")
    Call<BaseResponse> cancelPermohonanSurat(
            @Header("App-Token") String app_token,
            @Header("ProDesa-Token") String prodesa_token,
            @Path("appDesaCode") String appDesaCode,
            @Path("nik") String nik,
            @Path("id") String id,
            @Field("alasan_pembatalan") String alasan_pembatalan
    );

    //    Add Letter
    @Multipart
    @POST("permohonan-surat/{appDesaCode}/add-file/{nik}")
    Call<BaseResponse> addLetter (
            @Header("App-Token") String app_token,
            @Header("ProDesa-Token") String prodesa_token,
            @Path("appDesaCode") String appDesaCode,
            @Path("nik") String nik,
            @Part("id_syarat_surat") RequestBody id_syarat_surat,
            @Part("nama") RequestBody nama,
            @Part MultipartBody.Part file);

//    @GET("api/admin/barang")
//    Call<ArrayList<Barang>> getBarangList(@Header("Authorization") String token, @Query("type") String type);

//    @GET("api/home/transaksi/{id}")
//    Call<PointHistory> getUserPoint(@Header("Authorization") String token, @Field("id") int id);
}
