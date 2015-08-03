package com.example.kylej.connectme;

import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.kylej.connectme.fragments.FragMap;
import com.example.kylej.connectme.fragments.ListFrag;
import com.google.android.gms.maps.GoogleMap;
import android.support.v4.app.FragmentActivity;

import android.app.Fragment;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.app.FragmentManager;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Switch;


public class MainActivity extends FragmentActivity
        implements OnMapReadyCallback, View.OnClickListener, FragMap.OnFragmentInteractionListener {

    private static final String TAG_MFRAG = ("map");
    private static final String TAG_LISTFRAG = ("list");

    private FragMap mfrag;
    private Fragment listFrag;
    private FragmentManager fm;
    static LatLng Blacksburg = new LatLng(37.2300, 80.4178);

    private ListView events;
    private Button viewMap;
    private Button viewList;

    private Boolean mapChecked;
    private Boolean listChecked;

    private Switch viewChoice;
    private SeekBar seekBar;
    private int miles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewMap = (Button) findViewById(R.id.view_map);
        viewList = (Button) findViewById(R.id.view_list);
        viewMap.setOnClickListener(this);
        viewList.setOnClickListener(this);

        mapChecked = true;
        listChecked = false;

        miles = 10;
        fm = getFragmentManager();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.addMarker(new MarkerOptions()
                .position(Blacksburg)
                .title("Marker"));
    }

    public void onClick(View v){

        if(v == viewMap && (!mapChecked)){

            mapChecked = true;
            listChecked = false;
            viewMap.setBackgroundColor(getResources().getColor(R.color.Highlight));
            viewList.setBackgroundColor(getResources().getColor(R.color.Gray));
            displayMap();

        }

        if(v == viewList && (!listChecked)){

            mapChecked = false;
            listChecked = true;
            viewList.setBackgroundColor(getResources().getColor(R.color.Highlight));
            viewMap.setBackgroundColor(getResources().getColor(R.color.Gray));
            displayList();
        }
    }


    private void displayList() {
        mfrag = (FragMap) fm.findFragmentByTag(TAG_MFRAG);
        listFrag = fm.findFragmentByTag(TAG_LISTFRAG);
        if(mfrag != null){
            fm.beginTransaction().remove(mfrag).commit();
        }

        if (listFrag == null) {

            listFrag = ListFrag.newInstance("a", "n");
            fm.beginTransaction().add(
                    R.id.container, listFrag, TAG_LISTFRAG).commit();

        }

    }

    private void displayMap() {
        mfrag = (FragMap) fm.findFragmentByTag(TAG_MFRAG);
        listFrag = fm.findFragmentByTag(TAG_LISTFRAG);
        if(listFrag != null){
            fm.beginTransaction().remove(listFrag).commit();
        }

        if(mfrag == null){
            mfrag = FragMap.newInstance("", "");
            fm.beginTransaction().add(
                    R.id.container, mfrag, TAG_MFRAG).commit();

        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
