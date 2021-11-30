package com.cleardil.sdk.android_example;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.cleardil.sdk.KycModule;
import com.cleardil.sdk.android_example.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private int sdkCode;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.sdkCode = getActivity().getIntent().getIntExtra(KycModule.RESULT_CODE, -1);

        TextView textView = view.findViewById(R.id.textview_first);
        textView.setText(R.string.hello_first_fragment + " " + this.sdkCode);

        binding.buttonAuthenticate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KycModule.builder()
                        .withSdkToken("123456789-azerty-lmnop")
                        .withEnvironment(KycModule.Environment.Demo)
                        .allowIdentityCard()
                        .withTargetActivity(MainActivity.class)
                        .build()
                        .start(getActivity());

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}