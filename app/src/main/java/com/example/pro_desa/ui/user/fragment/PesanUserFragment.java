package com.example.pro_desa.ui.user.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pro_desa.R;
import com.example.pro_desa.ui.user.activity.DetailPesanActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PesanUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PesanUserFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PesanUserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PesanUserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PesanUserFragment newInstance(String param1, String param2) {
        PesanUserFragment fragment = new PesanUserFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_pesan_user,
                container, false);

        CardView btn_rv_pesan   = (CardView) rootView.findViewById(R.id.cv_list_pesan);
        btn_rv_pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mv   =   new Intent(getActivity(), DetailPesanActivity.class);
                startActivity(mv);
            }
        });
        return rootView;
    }
}