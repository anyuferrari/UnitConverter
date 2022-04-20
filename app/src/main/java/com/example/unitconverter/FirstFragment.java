package com.example.unitconverter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.unitconverter.databinding.FragmentFirstBinding;


public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    Button distButton;
    Button timeButton;
    Button massButton;
    Button tempButton;
    Button energyButton;
    Button velocityButton;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        distButton = binding.buttonDistance;
        timeButton = binding.buttonTime;
        massButton = binding.buttonMass;
        tempButton = binding.buttonTemperature;
        energyButton = binding.buttonEnergy;
        velocityButton = binding.buttonVelocity;
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonDistance.setOnClickListener(view1 -> {
            String unit = distButton.getText().toString();
            FirstFragmentDirections.ActionFirstFragmentToSecondFragment action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(unit);
            NavHostFragment.findNavController(FirstFragment.this)
                    .navigate(action);
        });

        binding.buttonTime.setOnClickListener(view1 -> {
            String unit = timeButton.getText().toString();
            FirstFragmentDirections.ActionFirstFragmentToSecondFragment action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(unit);
            NavHostFragment.findNavController(FirstFragment.this).navigate(action);
        });

        binding.buttonMass.setOnClickListener(view1 -> {
            String unit = massButton.getText().toString();
            FirstFragmentDirections.ActionFirstFragmentToSecondFragment action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(unit);
            NavHostFragment.findNavController(FirstFragment.this)
                    .navigate(action);
        });

        binding.buttonTemperature.setOnClickListener(view1 -> {
            String unit = tempButton.getText().toString();
            FirstFragmentDirections.ActionFirstFragmentToSecondFragment action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(unit);
            NavHostFragment.findNavController(FirstFragment.this)
                    .navigate(action);
        });

        binding.buttonEnergy.setOnClickListener(view1 -> {
            String unit = energyButton.getText().toString();
            FirstFragmentDirections.ActionFirstFragmentToSecondFragment action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(unit);
            NavHostFragment.findNavController(FirstFragment.this)
                    .navigate(action);
        });

        binding.buttonVelocity.setOnClickListener(view1 -> {
            String unit = velocityButton.getText().toString();
            FirstFragmentDirections.ActionFirstFragmentToSecondFragment action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(unit);
            NavHostFragment.findNavController(FirstFragment.this)
                    .navigate(action);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}