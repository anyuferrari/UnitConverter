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
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.distance, android.R.layout.simple_spinner_item);
        if (unit.equals("Distance")) {
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            inputSpinner.setAdapter(adapter);
            outputSpinner.setAdapter(adapter);
            binding.button.setOnClickListener(view1 -> {
                String inputSelection = inputSpinner.getSelectedItem().toString();
                String outputSelection = outputSpinner.getSelectedItem().toString();
                setResult(view1, inputSelection, outputSelection);

            });


        }
        binding.buttonSecond.setOnClickListener(view12 -> NavHostFragment.findNavController(SecondFragment.this)
                .navigate(R.id.action_SecondFragment_to_FirstFragment));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public Double distanceMultiplier(String input, String output) {
        HashMap<String, Double> distanceMap = new HashMap<>();
        distanceMap.put("micrometres", 1.e-6d);
        distanceMap.put("millimetres", 1.E-3d);
        distanceMap.put("centimetres", 1.E-2d);
        distanceMap.put("metres", 1.d);
        distanceMap.put("kilometres", 1.E3d);
        Double first = distanceMap.get(input);
        Double second = distanceMap.get(output);

        return first / second;

    }

    public void setResult(View view, String inputSelection, String outputSelection) {
        TextView result = view.getRootView().findViewById(R.id.result);
        EditText input = view.getRootView().findViewById(R.id.input);

        Double conversion = Double.parseDouble(input.getText().toString()) * distanceMultiplier(inputSelection, outputSelection);
        if (conversion < 1f) {
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
    }


}