package com.samhan.foodtogov10.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.samhan.foodtogov10.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FloatingActionButton btnAddPost;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    ImageView popupPostImage,popupAddBtn;
    TextView popupTitle,popupDescription;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
//        return inflater.inflate(R.layout.fragment_home, container, false);

        View fragmentView=inflater.inflate(R.layout.fragment_home,container,false);
        btnAddPost=fragmentView.findViewById(R.id.btn_addPost);

        btnAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniPopup();
//                Dialog popAddPost = new Dialog(getContext(),android.R.style.Theme_Black_NoTitleBar_Fullscreen);
//                popAddPost.setContentView(R.layout.popup_add_post);
//                popAddPost.show();

            }
        });



        return fragmentView;

    }

    private void iniPopup(){
        Dialog popAddPost = new Dialog(getContext(),android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        popAddPost.setContentView(R.layout.popup_add_post);
        popAddPost.show();


        popupPostImage=popAddPost.findViewById(R.id.popup_img);
        popupTitle=popAddPost.findViewById(R.id.popup_title);
        popupDescription=popAddPost.findViewById(R.id.popup_description);
        popupAddBtn=popAddPost.findViewById(R.id.popup_btn_addPost);

        popupAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "working", Toast.LENGTH_SHORT).show();
            }
        });
    }
}