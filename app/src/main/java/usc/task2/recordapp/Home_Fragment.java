package usc.task2.recordapp;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import static android.content.DialogInterface.*;


public class Home_Fragment extends Fragment {

    private RecyclerView recyclerView;
    private RViewAdapter recyclerViewAdapter;
    private List<Logs> pantryList;
    private List<Logs> listItems;
    private SQLiteHelper db;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    private EditText groceryItem;
    private EditText quantity;
    private Button saveButton;
    public Home_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        db = new SQLiteHelper(getContext());
        recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerViewID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        pantryList = new ArrayList<>();
        listItems = new ArrayList<>();

        // Get items from database
        pantryList = db.getAllLogs();
if(pantryList.size()>0) {
    for (Logs c : pantryList) {
        Logs pantry = new Logs();
        pantry.setTitle(c.getTitle());
        pantry.setPlace("Place: " + c.getPlace());
        pantry.setDetails(c.getDetails());
        pantry.setLongitude("Longitude: " + c.getLongitude());
        pantry.setLatitude("Latitude: " + c.getLatitude());

        listItems.add(pantry);

    }

    recyclerViewAdapter = new RViewAdapter(getActivity(), listItems);
    recyclerView.setAdapter(recyclerViewAdapter);
    recyclerViewAdapter.notifyDataSetChanged();
}
else
{
    Snackbar.make(getView(), "Log Information Not Available, Add Now!", Snackbar.LENGTH_LONG).show();
}

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


}
