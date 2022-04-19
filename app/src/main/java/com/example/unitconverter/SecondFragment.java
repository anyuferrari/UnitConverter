package com.example.unitconverter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.unitconverter.databinding.FragmentSecondBinding;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    Spinner inputSpinner;
    Spinner outputSpinner;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        inputSpinner = binding.inputUnit;
        outputSpinner = binding.outputUnit;
        return binding.getRoot();

    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String unit = SecondFragmentArgs.fromBundle(getArguments()).getMyArg();
        switch (unit) {
            case "Distance":
                ArrayAdapter<CharSequence> distance = ArrayAdapter.createFromResource(getContext(), R.array.distance, android.R.layout.simple_spinner_item);
                distance.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                inputSpinner.setAdapter(distance);
                outputSpinner.setAdapter(distance);
                setResult(view, distance, "Distance");
                break;
            case "Time":
                ArrayAdapter<CharSequence> time = ArrayAdapter.createFromResource(getContext(), R.array.time, android.R.layout.simple_spinner_item);
                time.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                inputSpinner.setAdapter(time);
                outputSpinner.setAdapter(time);
                setResult(view, time, unit);
                break;
            case "Mass":
                ArrayAdapter<CharSequence> mass = ArrayAdapter.createFromResource(getContext(), R.array.mass, android.R.layout.simple_spinner_item);
                mass.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                inputSpinner.setAdapter(mass);
                outputSpinner.setAdapter(mass);
                setResult(view, mass, unit);
                break;
        }
        binding.buttonSecond.setOnClickListener(view12 -> NavHostFragment.findNavController(SecondFragment.this)
                .navigate(R.id.action_SecondFragment_to_FirstFragment));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    public Double multiplier(String input, String output, String unit) {
        Double result = 0.d;
        switch(unit){
            case "Distance":
                result = distanceMultiplier(input, output);
                break;
            case "Time":
                result = timeMultiplier(input, output);
                break;
            case "Mass":
                result = massMultiplier(input, output);
                break;
        }
        return result;
    }

    public void setResult(View view, ArrayAdapter<CharSequence> adapter, String unit) {
        binding.button.setOnClickListener(view1 -> {
            TextView result = view.getRootView().findViewById(R.id.result);
            EditText input = view.getRootView().findViewById(R.id.input);
            String inputSelection = inputSpinner.getSelectedItem().toString();
            String outputSelection = outputSpinner.getSelectedItem().toString();
            Double conversion = Double.parseDouble(input.getText().toString()) * multiplier(inputSelection, outputSelection, unit);
            if (conversion < 0.1f) {
                NumberFormat numFormat = new DecimalFormat("0.###E0");
                String str = String.format("%1$s %2$s", numFormat.format(conversion), outputSelection);
                result.setText(str);
            } else if (conversion > 1.e6f) {
                NumberFormat numFormat = new DecimalFormat("#.###E0");
                String str = String.format("%1$s %2$s", numFormat.format(conversion), outputSelection);
                result.setText(str);
            } else {
                BigDecimal bd = new BigDecimal(Double.toString(conversion));
                bd = bd.setScale(3, RoundingMode.HALF_UP);
                double n = bd.doubleValue();
                result.setText(String.format("%1$s %2$s", n, outputSelection));
            }
        });
    }

    public Double distanceMultiplier(String input, String output) {
        HashMap<String, Double> distanceMap = new HashMap<>();
        distanceMap.put("micrometres", 1.e-6d);
        distanceMap.put("millimetres", 1.E-3d);
        distanceMap.put("centimetres", 1.E-2d);
        distanceMap.put("metres", 1.d);
        distanceMap.put("kilometres", 1.E3d);
        distanceMap.put("inch", 0.0254d);
        distanceMap.put("foot", 0.3048d);
        distanceMap.put("yard", 0.9144d);
        distanceMap.put("mile", 1609.344d);
        distanceMap.put("nautical mile", 1852d);
        Double first = distanceMap.get(input);
        Double second = distanceMap.get(output);
        return first / second;

    }

    public Double timeMultiplier(String input, String output){
        HashMap<String, Double> timeMap = new HashMap<>();
        timeMap.put("seconds", 1.d);
        timeMap.put("minutes", 60.d);
        timeMap.put("hours", 3600.d);
        timeMap.put("days", 86400.d);
        timeMap.put("years", 3.154e7);
        Double first = timeMap.get(input);
        Double second = timeMap.get(output);
        return first/second;
    }

    public Double massMultiplier(String input, String output){
        HashMap<String, Double> massMap = new HashMap<>();
        massMap.put("milligram", 1e-3d);
        massMap.put("gram", 1.d);
        massMap.put("kilogram", 1e3d);
        massMap.put("ton", 1000000.d);
        massMap.put("ounce", 28.3495d);
        massMap.put("pound", 453.592d);
        Double first = massMap.get(input);
        Double second = massMap.get(output);
        return first/second;
    }


}