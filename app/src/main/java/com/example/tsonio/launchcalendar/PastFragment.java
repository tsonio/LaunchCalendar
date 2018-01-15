package com.example.tsonio.launchcalendar;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.util.StringTokenizer;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PastFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PastFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PastFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    //Fields for the web service
    Button reqBut, jsonBut;
    MyHttpRequest task, task2;
    TextView output;
    ImageView image;
    JSONObject jarr;
    String startDate;
    String finalUrl;
    DatabaseUtility db;


    public PastFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PastFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PastFragment newInstance(String param1, String param2) {
        PastFragment fragment = new PastFragment();
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


        //Initialise a link to our database
        db = new DatabaseUtility(getActivity());

        //Read the stored date value in the database
        //to use for querying the API
        startDate = db.getDay().get(db.getDay().size()-1);

        //Build the final URL to query the API
        //Initialise a link to our database
        db = new DatabaseUtility(getActivity());

        //Read the stored date value in the database
        //to use for querying the API
        finalUrl = "https://launchlibrary.net/1.3/launch/" + startDate;

        //This loop needs to run in order to be able
        //to load the  JSON with a single tap of the LOAD button
        String str;
        NodeList n;
        for(int c = 0; c < 20; c++) {
            if (task == null) {
                task = new MyHttpRequest();
                task.execute(finalUrl);
            }

        }/////////////////
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_past, container, false);

        //Find the output text view
        output = (TextView) view.findViewById(R.id.pastOutput);
        output.setPadding(25,25,25,25);

        //Image
        image  = (ImageView) view.findViewById(R.id.pastImage);
        image.setPadding(25,25,25,25);
        image.setAlpha(0.5f);

        //Button
        jsonBut = (Button) view.findViewById(R.id.jsonBut);
        jsonBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str;
                NodeList n;
                //String urll = "https://launchlibrary.net/1.3/launch/"+startDate;

                if (task == null) {
                    task = new MyHttpRequest();
                    task.execute(finalUrl);
                }

                //Get the JSON returned form the API, extract the required data
                //and display the formatted output
                if (v == reqBut) {
                    str = task.getReturnEntry();
                    output.setText(str);
                    output.setMovementMethod(new ScrollingMovementMethod());
                }else if (v == jsonBut) {

                    try {
                        jarr = task.getResultAsJSON2();

                        if (jarr==null) {
                            output.setText ("Still loading JSON");
                            return;
                        }

                        //Output string
                        str = "";

                        //Hide the button and the image
                        jsonBut.setVisibility(View.GONE);
                        image.setAlpha(0.05f);


                        JSONArray launches = jarr.getJSONArray("launches");

                        for (int i = 0; i < launches.length(); i++) {

                            JSONObject test = launches.getJSONObject(i);
                            JSONArray pad = test.getJSONObject("location").getJSONArray("pads");
                            JSONObject pad1 = test.getJSONObject("location");

                            //Split a string with the mission name and LV
                            //into two separate ones
                            String lvPlusMission = test.getString("name");
                            StringTokenizer total = new StringTokenizer(lvPlusMission, "|");
                            String lv = total.nextToken();// launch vehicle used
                            String mission = total.nextToken();// mission name

                            //Build the final string to be displayed
                            str += "Mission: " + mission + "\n";
                            str += "Provider: " + test.getJSONObject("lsp").getString("name") + "\n";
                            str += "Launch Vehicle: " + lv + "\n";
                            str += "Location: " + pad1.getString("name") + "\n";
                            str += "Pad: " + pad.getJSONObject(0).getString("name") + "\n";
                            str += "NET: " + test.getString("net") + "\n" + "\n";
                            str += "________________________________________________________________" + "\n" + "\n";

                        }

                        //Display the final string a TextView and make it scrollable
                        output.setText(str);
                        output.setMovementMethod(new ScrollingMovementMethod());


                    }
                    catch (JSONException ex) {
                        output.setText("Some horror: " + ex.getMessage());
                    }

                }
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
           // Toast.makeText(context, "Past fragment attached", Toast.LENGTH_SHORT).show();
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
