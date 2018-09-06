package com.example.phamngocan.testmediaapp.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.phamngocan.testmediaapp.animation.CustomAnimation;
import com.example.phamngocan.testmediaapp.R;

public class DiscFragment extends Fragment {
    ImageView imageView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_frag1,container,false );
        imageView = view.findViewById(R.id.disc);

        CustomAnimation.setAnim(imageView);
        return view;
    }
}
