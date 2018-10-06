package me.akshanshjain.kwik.Fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import me.akshanshjain.kwik.Interfaces.OnFragmentInteractionListener;
import me.akshanshjain.kwik.R;

public class WhenPlanFragment extends Fragment {

    private TextView customDate, decideLater;
    private LinearLayout tonightContainer, tomorrowContainer;
    private ImageView nextButton;

    private OnFragmentInteractionListener interactionListener;

    private String customDateTime;

    /*
    Mandatory constructor for instantiating the fragment.
    */
    public WhenPlanFragment() {
    }

    /*
    Attaching the interaction listener to the fragment.
    */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            interactionListener = (OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    /*
    Inflating the fragment layout and performs the required operations or functions.
    */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        //Inflating the layout from the XML.
        View view = inflater.inflate(R.layout.fragment_when_plan, container, false);

        /*
        Referencing the views from the XML layout.
        */
        customDate = view.findViewById(R.id.custom_date_when);
        decideLater = view.findViewById(R.id.decide_later_when);

        tonightContainer = view.findViewById(R.id.tonight_option_container);
        tomorrowContainer = view.findViewById(R.id.tomorrow_option_container);

        nextButton = view.findViewById(R.id.next_button_when_fragment);

        tonightContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                String date = simpleDateFormat.format(new Date());

                viewOnClick(date + "\n" + "20:00");
            }
        });

        tomorrowContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date dt = new Date();
                Calendar c = Calendar.getInstance();
                c.setTime(dt);
                c.add(Calendar.DATE, 1);
                dt = c.getTime();

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                String tomorrow = simpleDateFormat.format(dt);

                viewOnClick(tomorrow + "\n" + "18:00");
            }
        });

        customDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker();
            }
        });

        decideLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewOnClick(getString(R.string.decide_later));
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(customDate.getText().toString())) {
                    if (!customDate.getText().toString().equals(getString(R.string.custom_date))) {
                        viewOnClick(customDate.getText().toString());
                    } else {
                        viewOnClick(getString(R.string.decide_later));
                    }
                }
            }
        });

        return view;
    }

    /*
    This function is called when the view items are clicked and thus corresponding data is passed into it.
    This data will be available in the activity for use.
    */
    private void viewOnClick(String data) {
        if (interactionListener != null) {
            interactionListener.onFragmentInteraction(data);
        }
    }

    /*
    This function is called when the user selects a custom date time for the event.
    */
    private void datePicker() {

        /*
        Initially we get the calendar instance and we declare the variables.
        */
        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        /*
        Setting up the time picker dialog.
        This will be called after the date has been selected.
        */
        final TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        String time = String.format("%02d:%02d", hour, minute);
                        customDateTime += "\n" + time;

                        //Setting the final date and time to the text view.
                        customDate.setText(customDateTime);
                    }
                }, hour, minute, true);

        /*
        Asking about the date to the event creator first.
        We use the in-built date picker dialog.
        */
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        customDateTime = String.format("%02d-%02d-%02d", day, month + 1, year);

                        //After the date has been selected we call the time picker dialog.
                        timePickerDialog.show();
                    }
                }, year, month, day);

        datePickerDialog.show();
    }
}
