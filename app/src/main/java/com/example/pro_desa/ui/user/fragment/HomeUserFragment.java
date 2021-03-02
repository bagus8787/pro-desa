package com.example.pro_desa.ui.user.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pro_desa.CobaNotif;
import com.example.pro_desa.Myapp;
import com.example.pro_desa.R;
import com.example.pro_desa.adapter.AdapterListArtikel;
import com.example.pro_desa.model.Artikel;
import com.example.pro_desa.model.User;
import com.example.pro_desa.network.ApiClient;
import com.example.pro_desa.network.ApiInterface;
import com.example.pro_desa.repository.ArtikelRepository;
import com.example.pro_desa.repository.PengumumanRepository;
import com.example.pro_desa.ui.user.activity.ArtikelActivity;
import com.example.pro_desa.ui.user.activity.BantuanActivity;
import com.example.pro_desa.ui.user.activity.DetailArtikelActivity;
import com.example.pro_desa.ui.user.activity.PengaduanActivity;
import com.example.pro_desa.ui.user.activity.ProfilActivity;
import com.example.pro_desa.utils.SharedPrefManager;
import com.example.pro_desa.viewmodels.ArtikelViewModel;
import com.example.pro_desa.viewmodels.BantuanViewModel;
import com.example.pro_desa.viewmodels.PengaduanViewModel;
import com.example.pro_desa.viewmodels.PengumumanViewModel;
import com.example.pro_desa.viewmodels.ProfileViewModel;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Timer;

import static com.example.pro_desa.Myapp.getContext;

public class HomeUserFragment extends Fragment implements View.OnClickListener {

    private AdapterListArtikel adapterListArtikel;
    public SharedPrefManager sharedPrefManager;
    public ApiInterface apiInterface;
    Context context;

    ArtikelViewModel artikelViewModel;
    ArtikelRepository artikelRepository;

    RecyclerView recyclerView;

    public HomeUserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstance){
        super.onCreate(savedInstance);
        adapterListArtikel = new AdapterListArtikel(getContext());

        sharedPrefManager = new SharedPrefManager(Myapp.getContext());

        artikelViewModel = ViewModelProviders.of(this).get(ArtikelViewModel.class);
        artikelViewModel.init();
        artikelViewModel.getArtikelLiveData().observe(this, new Observer<ArrayList<Artikel>>() {
            @Override
            public void onChanged(ArrayList<Artikel> artikels) {
                if (adapterListArtikel != null){
                    adapterListArtikel.setArtikels(artikels);
                    autoScroll();
//                    Log.d("logadapter", String.valueOf(adapterListArtikel));
                }
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home_user,
                container, false);

        ShimmerFrameLayout shimmerFrameLayout = rootView.findViewById(R.id.shimmer_view_container);
        shimmerFrameLayout.startShimmer();

        artikelRepository = new ArtikelRepository();

        TextView tv_name    = rootView.findViewById(R.id.txt_nama);

        SwipeRefreshLayout swipeRefreshLayout = rootView.findViewById(R.id.refresh_ly);

        recyclerView  = rootView.findViewById(R.id.rv_berita);
        CircularImageView img_profil    = rootView.findViewById(R.id.icon_profil) ;
        LinearLayout ln_art = rootView.findViewById(R.id.ln_art);
        LinearLayout ln_ps  = rootView.findViewById(R.id.ln_ps);
        LinearLayout ln_pd  = rootView.findViewById(R.id.ln_pd);
        LinearLayout ln_pb  = rootView.findViewById(R.id.ln_pb);

        Picasso.with(getContext())
                .load(sharedPrefManager.getSpAvatar())
                .fit().centerCrop()
                .error(R.drawable.user_no)
                .into(img_profil);

        tv_name.setText(sharedPrefManager.getSpNama());

        ln_art.setOnClickListener(this);
        ln_ps.setOnClickListener(this);
        ln_pd.setOnClickListener(this);
        ln_pb.setOnClickListener(this);
        img_profil.setOnClickListener(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapterListArtikel);

//        shimmerFrameLayout.stopShimmer();
//        shimmerFrameLayout.setVisibility(View.GONE);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);

                shimmerFrameLayout.startShimmer();
//                shimmerFrameLayout.setVisibility(View.GONE);
            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reloadArtikelList();
//        autoScroll();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                reloadArtikelList();
//            }
//        }, 10000);
    }

    @Override
    public void onResume(){
        super.onResume();
        artikelViewModel.getArtikel();
//        bantuanViewModel.getBantuan();
//        pengumumanViewModel.getPengumuman();
//        pengaduanViewModel.getPengaduan();
//        autoScroll();
    }

    public void reloadArtikelList() {
        artikelViewModel.getArtikel();
        artikelRepository.getArtikel();
    }

    public void autoScroll(){
        int speedScroll = 0;
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            int count = 0;
            int total = adapterListArtikel.getItemCount();

            @Override
            public void run() {
                if(count == adapterListArtikel.getItemCount()){
                    count = 0;
                    recyclerView.smoothScrollToPosition(++count);
                    Log.d("scroaall", String.valueOf(count));
                    Log.d("totaaal", String.valueOf(total));

                    handler.postDelayed(this,100000);
                }
                else {
                    if(count < adapterListArtikel.getItemCount()){
                        recyclerView.smoothScrollToPosition(++count);
                        Log.d("scroll", String.valueOf(count));
                        Log.d("total", String.valueOf(total));

                        handler.postDelayed(this,50000);
                    }else {
                        count = 0;
//                        recyclerView.smoothScrollToPosition(1);
//                        Log.d("scrollaa", String.valueOf(count));
                    }
                }

//                if(count == adapterListArtikel.getItemCount())
//                    count = 0;
//                else {
//                    if(count < adapterListArtikel.getItemCount()){
//                        recyclerView.smoothScrollToPosition(++count);
//                        Log.d("scroll", String.valueOf(count));
//                        Log.d("total", String.valueOf(total));
//
//
//                        handler.postDelayed(this,10000);
//                    }else {
//                        count = 0;
////                        recyclerView.smoothScrollToPosition(1);
////                        Log.d("scrollaa", String.valueOf(count));
//                    }
//                }
            }
        };
        handler.postDelayed(runnable,10000);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ln_art:
                Intent art  = new Intent(getActivity(), ArtikelActivity.class);
                art.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(art);
                break;

            case R.id.ln_pd:
                Intent pd   = new Intent(getActivity(), PengaduanActivity.class);
                startActivity(pd);
                break;

            case R.id.ln_pb:
                Intent pb   = new Intent(getActivity(), BantuanActivity.class);
                startActivity(pb);
                break;

            case R.id.ln_ps:
                FragmentManager fm = getFragmentManager();
                assert fm != null;
                FragmentTransaction ft = fm.beginTransaction();
                HistorySuratUserFragment llf = new HistorySuratUserFragment();
                ft.replace(R.id.layout_container, llf);
                ft.commit();
                break;

            case R.id.icon_profil:
                Intent icon_profil  = new Intent(getActivity(), ProfilActivity.class);
                startActivity(icon_profil);
                break;

//            case R.id.icon_profil:
//                Intent icon_profil  = new Intent(getActivity(), ProfilActivity.class);
//                startActivity(icon_profil);
//                break;
        }
    }
}