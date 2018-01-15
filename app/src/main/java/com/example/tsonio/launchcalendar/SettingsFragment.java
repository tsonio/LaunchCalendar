package com.example.tsonio.launchcalendar;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SettingsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //DB test button
    //Button test;
    TextView text;
    DatabaseUtility db;
    EditText dayField;
    EditText monthField;
    EditText yearField;
    Button submitDateButton;
    String day;
    String month;
    String year;
    String dateToBeInserted;
    TextView startDateOut;


    private OnFragmentInteractionListener mListener;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
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
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        //Initialise database
        db = new DatabaseUtility(getActivity());
        Log.e("DB:", "DB CREATED");

        //Buttons and fields
        submitDateButton = (Button) view.findViewById(R.id.submitButton);
        dayField = (EditText) view.findViewById(R.id.dayInput);
        monthField = (EditText) view.findViewById(R.id.monthInput);
        yearField = (EditText) view.findViewById(R.id.yearInput);

        //Display the most recent start date
        startDateOut = (TextView) view.findViewById(R.id.mostRecent);
        startDateOut.setText("The currently selected start date is " + db.getDay().get(db.getDay().size()-1));

        /* Capture user date input, format it and store it in the database */
        submitDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Get the day month and year values
                day = dayField.getText().toString();
                month = monthField.getText().toString();
                year = yearField.getText().toString();

                //Confirm input
                Log.e("Date to be set is: ", day+month+year);

                //Format them to yyyy-mm-dd and insert them into the database
                dateToBeInserted = year + "-" + month + "-" + day;
                db.insertDay(dateToBeInserted);

                //Check the contents of the database
                Log.e("Most recent date :", db.getDay().get(db.getDay().size()-1));

                //Confirm input
                Log.e("DB", db.getDay().toString());

                //Display the most recent start date
                startDateOut = (TextView) getView().findViewById(R.id.mostRecent);
                startDateOut.setText("The currently selected start date is " + db.getDay().get(db.getDay().size()-1));
            }
        });


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            // Toast.makeText(context, "Upcoming fragment attached", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
