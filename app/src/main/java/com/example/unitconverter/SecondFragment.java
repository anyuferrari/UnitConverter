package com.example.unitconverter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
                setResult(view, unit);
                break;
            case "Time":
                ArrayAdapter<CharSequence> time = ArrayAdapter.createFromResource(getContext(), R.array.time, android.R.layout.simple_spinner_item);
                time.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                inputSpinner.setAdapter(time);
                outputSpinner.setAdapter(time);
                setResult(view, unit);
                break;
            case "Mass":
                ArrayAdapter<CharSequence> mass = ArrayAdapter.createFromResource(getContext(), R.array.mass, android.R.layout.simple_spinner_item);
                mass.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                inputSpinner.setAdapter(mass);
                outputSpinner.setAdapter(mass);
                setResult(view, unit);
                break;
            case "Temperature":
                ArrayAdapter<CharSequence> temp = ArrayAdapter.createFromResource(getContext(), R.array.temp, android.R.layout.simple_spinner_item);
                temp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                inputSpinner.setAdapter(temp);
                outputSpinner.setAdapter(temp);
                TextView result = view.getRootView().findViewById(R.id.result);
                TextView unitText = view.getRootView().findViewById(R.id.unit_text);
                EditText input = view.getRootView().findViewById(R.id.input);
                unitText.setText(unit);
                binding.button.setOnClickListener(view1 -> {
                            String inputSelection = inputSpinner.getSelectedItem().toString();
                            String outputSelection = outputSpinner.getSelectedItem().toString();
                            Double answer = tempMultiplier(inputSelection, outputSelection, Double.parseDouble(input.getText().toString()));
                            BigDecimal bd = new BigDecimal(Double.toString(answer));
                            bd = bd.setScale(3, RoundingMode.HALF_UP);
                            double n = bd.doubleValue();
                            result.setText(String.format("%1$s %2$s", n, outputSelection));
                        }
                );
                break;
            case "Energy":
                ArrayAdapter<CharSequence> energy = ArrayAdapter.createFromResource(getContext(), R.array.energy, android.R.layout.simple_spinner_item);
                energy.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                inputSpinner.setAdapter(energy);
                outputSpinner.setAdapter(energy);
                setResult(view, unit);
                break;
            case "Velocity":
                ArrayAdapter<CharSequence> velocity = ArrayAdapter.createFromResource(getContext(), R.array.velocity, android.R.layout.simple_spinner_item);
                velocity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                inputSpinner.setAdapter(velocity);
                outputSpinner.setAdapter(velocity);
                setResult(view, unit);
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
        switch (unit) {
            case "Distance":
                result = distanceMultiplier(input, output);
                break;
            case "Time":
                result = timeMultiplier(input, output);
                break;
            case "Mass":
                result = massMultiplier(input, output);
                break;
            case "Energy":
                result = energyMultiplier(input, output);
                break;
            case "Velocity":
                result = velocityMultiplier(input, output);
        }
        return result;
    }

    public void setResult(View view, String unit) {
        TextView unitText = view.getRootView().findViewById(R.id.unit_text);
        unitText.setText(unit);
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

    public Double timeMultiplier(String input, String output) {
        HashMap<String, Double> timeMap = new HashMap<>();
        timeMap.put("seconds", 1.d);
        timeMap.put("minutes", 60.d);
        timeMap.put("hours", 3600.d);
        timeMap.put("days", 86400.d);
        timeMap.put("years", 3.154e7);
        Double first = timeMap.get(input);
        Double second = timeMap.get(output);
        return first / second;
    }

    public Double massMultiplier(String input, String output) {
        HashMap<String, Double> massMap = new HashMap<>();
        massMap.put("milligram", 1e-3d);
        massMap.put("gram", 1.d);
        massMap.put("kilogram", 1e3d);
        massMap.put("ton", 1000000.d);
        massMap.put("ounce", 28.3495d);
        massMap.put("pound", 453.592d);
        Double first = massMap.get(input);
        Double second = massMap.get(output);
        return first / second;
    }

    public Double energyMultiplier(String input, String output) {
        HashMap<String, Double> energyMap = new HashMap<>();
        energyMap.put("Joules", 1.d);
        energyMap.put("calories", 4.184d);
        energyMap.put("kcal", 4.184e3d);
        energyMap.put("kiloWatts hour", 3.6e6d);
        energyMap.put("btu", 1055.06d);
        Double first = energyMap.get(input);
        Double second = energyMap.get(output);
        return first / second;
    }

    public Double velocityMultiplier(String input, String output) {
        HashMap<String, Double> velocityMap = new HashMap<>();
        velocityMap.put("m/s", 1.d);
        velocityMap.put("km/h", 0.277778d);
        velocityMap.put("mph", 0.44704d);
        velocityMap.put("ft/s", 0.3048d);
        velocityMap.put("knot", 0.514444d);
        Double first = velocityMap.get(input);
        Double second = velocityMap.get(output);
        return first / second;

    }

    public Double tempMultiplier(String inputUnit, String outputUnit, Double magnitude) {
        double result = 0d;
        switch (inputUnit) {
            case "Celsius":
                if (magnitude < -273.15d) {
                    Toast toast = Toast.makeText(getActivity(), R.string.low_temp, Toast.LENGTH_LONG);
                    toast.show();
                    break;
                }
                switch (outputUnit) {
                    case "Celsius":
                        result = magnitude;
                        break;
                    case "Fahrenheit":
                        Double fraction = 9d / 5d;
                        result = magnitude * fraction + 32d;
                        break;
                    case "Kelvin":
                        result = magnitude + 273.15d;
                        break;
                }
                break;
            case "Fahrenheit":
                if (magnitude < -459.67d) {
                    Toast toast = Toast.makeText(getActivity(), R.string.low_temp, Toast.LENGTH_LONG);
                    toast.show();
                    break;
                }
                switch (outputUnit) {
                    case "Celsius":
                        double fraction = 5d / 9d;
                        result = (magnitude - 32d) * fraction;
                        break;
                    case "Fahrenheit":
                        result = magnitude;
                        break;
                    case "Kelvin":
                        double inverseFraction = 5d / 9d;
                        result = 273.15 + (magnitude - 32d) * inverseFraction;
                        break;
                }
                break;
            case "Kelvin":
                if (magnitude < 0) {
                    Toast toast = Toast.makeText(getActivity(), R.string.low_temp, Toast.LENGTH_LONG);
                    toast.show();
                    break;
                }
                switch (outputUnit) {
                    case "Celsius":
                        result = magnitude - 273.15d;
                        break;
                    case "Fahrenheit":
                        double fraction = 9d / 5d;
                        result = (magnitude - 273.15d) * fraction + 32d;
                        break;
                    case "Kelvin":
                        result = magnitude;
                        break;
                }

        }
        return result;
    }


}