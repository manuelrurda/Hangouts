package com.example.hangouts.homeScreen.hangoutCreation;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.hangouts.R;
import com.example.hangouts.databinding.FragmentCreateHangoutBinding;
import com.example.hangouts.homeScreen.HangoutDetailsFragment;
import com.example.hangouts.homeScreen.utils.DateTimeUtil;
import com.example.hangouts.homeScreen.hangoutCreation.CreateHangoutViewModel.Errors;
import com.example.hangouts.models.Hangout;

import java.util.Calendar;
import java.util.Date;


public class CreateHangoutFragment extends Fragment {

    public static final String TAG = "CreateHanogutFragment";
    private FragmentCreateHangoutBinding binding;

    private final Calendar calendar = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TimePickerDialog.OnTimeSetListener timeSetListener;

    private CreateHangoutViewModel viewModel;

    public CreateHangoutFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreateHangoutBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                viewModel.setHangoutDate(calendar.getTime());
            }
        };
        timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                viewModel.setHangoutTime(calendar.getTime());
            }
        };
        viewModel = new ViewModelProvider(requireActivity()).get(CreateHangoutViewModel.class);
        viewModel.hangoutLocationDecoded.observe(requireActivity(), this::setLocationText);
        viewModel.hangoutDate.observe(requireActivity(), this::updateDateFieldText);
        viewModel.hangoutTime.observe(requireActivity(), this::updateTimeFieldText);
        viewModel.errors.observe(requireActivity(), this::handleError);
        viewModel.newHangout.observe(requireActivity(), this::onHangoutCreated);
        binding.itCreateFragmentDate.setOnClickListener(this::onDateFieldClick);
        binding.itCreateFragmentTime.setOnClickListener(this::onTimeFieldClick);
        binding.btnCreateFragmentCreate.setOnClickListener(this::onCreateClick);
    }

    private void handleError(Errors errors) {
        switch (errors){
            case ERROR_LOCATION_DECODING_FAILED:
                Toast.makeText(getContext(), "Failed to decode location", Toast.LENGTH_SHORT).show();
                break;
            case ERROR_ALL_FIELDS_REQUIRED:
                Toast.makeText(getContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                break;
            case ERROR_SAVING_HANGOUT:
                Toast.makeText(getContext(), "Network Error", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void onHangoutCreated(Hangout hangout) {
        goHangoutDetailsFragment(hangout);
    }

    private void goHangoutDetailsFragment(Hangout hangout) {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.homeFragmentContainer, HangoutDetailsFragment.newInstance(hangout))
                .addToBackStack("")
                .commit();
    }

    private void setLocationText(String locationText) {
        binding.tvCreateFragmentLocation.setText(locationText);
    }

    private void updateDateFieldText(Date date) {
        binding.itCreateFragmentDate.setText(DateTimeUtil.getDateString(date));
    }

    private void updateTimeFieldText(Date date) {
        binding.itCreateFragmentTime.setText(DateTimeUtil.getTimeString(date));
    }

    private void onDateFieldClick(View view) {
        new DatePickerDialog(getContext(), dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void onTimeFieldClick(View view) {
        new TimePickerDialog(getContext(), timeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), true).show();
    }

    private void onCreateClick(View view) {
        viewModel.setHangoutAlias(binding.itCreateFragmentAlias.getText().toString());
        viewModel.onCreateClick();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}