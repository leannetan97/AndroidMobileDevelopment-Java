package com.layyan.inputcontrolnew;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalAlarmDetails extends Fragment {


    public PersonalAlarmDetails() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal_alarm_details, container, false);
        View location = (View) view.findViewById(R.id.layout_row_alarm_name);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("S");
                openChangePersonalAlarmNameDialog(v);
            }
        });
        return view;
    }

    public void openChangePersonalAlarmNameDialog(View view) {
        DialogWithTitle changeAlarmNameDialog = new DialogWithTitle();
        Bundle args = new Bundle();
        args.putString("DialogTitle", "Alarm Name");
        args.putString("ValidButton", "SAVE");
        args.putString("InvalidButton", "DISCARD");
        changeAlarmNameDialog.setArguments(args);
        changeAlarmNameDialog.show(getActivity().getSupportFragmentManager(), "change alarm name dialog");
    }

}
