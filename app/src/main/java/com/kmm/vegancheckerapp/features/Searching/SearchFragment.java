package com.kmm.vegancheckerapp.features.Searching;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.kmm.vegancheckerapp.features.Alternatives.AlternativeListActivity;
import com.kmm.vegancheckerapp.features.NetworkReceiver;
import com.kmm.vegancheckerapp.features.NetworkService;
import com.kmm.vegancheckerapp.features.ShowToast;
import com.kmm.vegancheckerapp.utils.IConstants;

import java.util.ArrayList;
import java.util.Objects;

import static com.kmm.vegancheckerapp.R.drawable;
import static com.kmm.vegancheckerapp.R.id;
import static com.kmm.vegancheckerapp.R.layout;
import static com.kmm.vegancheckerapp.R.string;

public class SearchFragment extends Fragment implements View.OnClickListener {
    public SearchFragment() {
    }

    ArrayList<String> veganProducts, foodTypes, alcoholTypes;
    SearchAdapter searchAdapter;
    Button btnFood, btnCosmetics, btnAlcohol;
    private ListView lvFood, lvAlcohol;
    private ImageButton ibFood, ibAlcohol;

    NetworkReceiver myReceiver = new NetworkReceiver();

    AutoCompleteTextView mSearchbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    
        View v = inflater.inflate(layout.fragment_search, container, false);
        Context context = getContext();

        assert context != null;
        btnAlcohol = v.findViewById(id.btnAlcohol);
        btnCosmetics = v.findViewById(id.btnCosmetics);
        btnFood = v.findViewById(id.btnFood);
        lvFood = v.findViewById(id.lvFood);
        lvAlcohol = v.findViewById(id.lvAlcohol);
        ibFood = v.findViewById(id.ibFood);
        ibAlcohol = v.findViewById(id.ibAlcohol);
        mSearchbar = v.findViewById(id.search_bar);

if(IConstants.FOOD_TYPES ==null || IConstants.ALCOHOL_TYPES == null){
   String error = "Database Error";
   foodTypes = new ArrayList<>();
   foodTypes.add(error);
   veganProducts = new ArrayList<>(foodTypes);
   alcoholTypes = new ArrayList<>(foodTypes);

} else{
    foodTypes = new ArrayList<>(IConstants.FOOD_TYPES);
    veganProducts = new ArrayList<>(IConstants.FOOD_TYPES);
    veganProducts.addAll(IConstants.ALCOHOL_TYPES);
    alcoholTypes =  new ArrayList<>(IConstants.ALCOHOL_TYPES);
        }


        ArrayAdapter foodAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, foodTypes);
        ArrayAdapter alcoholAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, alcoholTypes);
        searchAdapter = new SearchAdapter(getContext(), layout.custom_search, veganProducts);
        mSearchbar.setAdapter(searchAdapter);
        lvFood.setAdapter(foodAdapter);
        lvAlcohol.setAdapter(alcoholAdapter);

        btnCosmetics.setOnClickListener(this);
        btnAlcohol.setOnClickListener(this);
        btnFood.setOnClickListener(this);



        mSearchbar = v.findViewById(id.search_bar);
        mSearchbar.setThreshold(3);


        mSearchbar.addTextChangedListener(new TextWatcher() {
            private boolean shouldAutoComplete = true;

            /*
            Code below is based:
            StackOverflow Answer to Question: "Fetch AutoCompleteTextView suggestions from service in separate thread"
            AmITheRWord,
            https://stackoverflow.com/a/4747593
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                shouldAutoComplete = true;
                if(searchAdapter.isEmpty()){
                    shouldAutoComplete = false;
                } else{
                for (int position = 0; position < searchAdapter.getCount(); position++) {
                    if (!Objects.equals(searchAdapter.getItem(position), s.toString())) {
                        continue;
                    }
                    shouldAutoComplete = false;
                    break;
                }
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (shouldAutoComplete) {
                    new SearchAdapter(getContext(), layout.custom_search, veganProducts);
                }
            }
        }); //END
        mSearchbar.setOnItemClickListener((parent, view, position, id) -> {
            String strType = searchAdapter.getItem(position);
            Intent intent = new Intent(getContext(), AlternativeListActivity.class);
            intent.putExtra("Activity", "Search");
            intent.putExtra("PRODUCT_TYPE", strType);
            String category ="";
            if(alcoholTypes.contains(strType)){
                category ="Alcohol";
            } else{
                category ="Food";
            }
            intent.putExtra("Lottie", category);
            mSearchbar.setText("");
            startActivity(intent);
        });
/*
Code below is based on:
StackOverflow answer to question: "AutoCompleteTextView enter press",
umesh shakya,
https://stackoverflow.com/a/49132750
 */
        mSearchbar.setOnEditorActionListener((v1, actionId, event) -> {
            if ((actionId == EditorInfo.IME_ACTION_DONE)) { //END

                String type = v1.getText().toString().toLowerCase();

                if (!type.isEmpty()) {
                    ArrayList<String> veganPTypes = new ArrayList<>();

                    for (String productType : veganProducts) {
                        if (productType.toLowerCase().equals(type)) {
                            veganPTypes.add(type);
                        }
                    }
                    if (veganPTypes.size() > 0) {
                        Intent intent = new Intent(getContext(), AlternativeListActivity.class);
                        intent.putExtra("PRODUCT_TYPE", type);
                        intent.putExtra("Activity", "Search");
                        String category ="";
                        if(alcoholTypes.contains(type)){
                            category ="Alcohol";
                        } else{
                            category ="Food";
                        }
                        intent.putExtra("Lottie", category);
                        v1.setText("");
                        startActivity(intent);


                    } else {
                        v1.setText("");
                        ShowToast activity = new ShowToast();
                        activity.makeImageToast(getContext(), drawable.ic_error, string.no_results, Toast.LENGTH_LONG);
                        Log.d("SEARCH_FRAGMENT", "onEditorActionListener");
                    }

                } else {
                    ShowToast activity = new ShowToast();
                    activity.makeImageToast(getContext(), drawable.ic_error, string.no_results, Toast.LENGTH_LONG);
                    Log.d("SEARCH_FRAGMENT", "onEditorActionListener");
                    return false;
                }
            }
            return false;
        });


        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        requireContext().registerReceiver(myReceiver, intentFilter);

    }


    @Override
    public void onStop() {
        super.onStop();
        requireContext().unregisterReceiver(myReceiver);
    }


    @Override
    public void onClick(View v) {
        String type = "";
        switch (v.getId()) {
            case (id.btnAlcohol):
                type = "Alcohol";
                break;
            case (id.btnCosmetics):
                type = "Cosmetics";
                break;
            case (id.btnFood):
                type = "Food";
                break;
        }
        if (type.equals("Food")) {

            if(checkInternet(getContext())){
                lvFood.setVisibility(View.VISIBLE);
                ibFood.setVisibility(View.VISIBLE);
            } else{
                lvFood.setVisibility(View.GONE);
                ibFood.setVisibility(View.GONE);


            }




   lvFood.setOnItemClickListener((parent, view, position, id) -> {
                if(foodTypes.size()> 0){
                String productType = foodTypes.get(position);
                Intent intent = new Intent(getContext(), AlternativeListActivity.class);
                intent.putExtra("Activity", "Search");
                intent.putExtra("Lottie", "Food");
                intent.putExtra("PRODUCT_TYPE", productType);
                lvFood.setVisibility(View.GONE);
                ibFood.setVisibility(View.INVISIBLE);
                startActivity(intent);
            } else{
                    ShowToast activity = new ShowToast();
                    activity.makeImageToast(getContext(), drawable.ic_error, string.no_db, Toast.LENGTH_LONG);
                    Log.d("SEARCH_FRAGMENT", "onClick Listener");
            }});

        } else if(type.equals("Alcohol")){
            if(checkInternet(getContext())){
                lvAlcohol.setVisibility(View.VISIBLE);

                ibAlcohol.setVisibility(View.VISIBLE);
            } else{
                lvAlcohol.setVisibility(View.GONE);
                ibAlcohol.setVisibility(View.GONE);


            }
            lvAlcohol.setOnItemClickListener((parent, view, position, id) -> {
                if(alcoholTypes.size()> 0){
                    String productType = alcoholTypes.get(position);
                    Intent intent = new Intent(getContext(), AlternativeListActivity.class);
                    intent.putExtra("Activity", "Search");
                    intent.putExtra("Lottie", "Alcohol");
                    intent.putExtra("PRODUCT_TYPE", productType);
                    lvAlcohol.setVisibility(View.GONE);
                    ibAlcohol.setVisibility(View.INVISIBLE);
                    startActivity(intent);
                } else{
                    ShowToast activity = new ShowToast();
                    activity.makeImageToast(getContext(), drawable.ic_error, string.no_db, Toast.LENGTH_LONG);
                    Log.d("SEARCH_FRAGMENT", "onClick Listener");
                }});

        }

        else {
            ShowToast activity = new ShowToast();
            activity.makeImageToast(getContext(), drawable.ic_error, string.no_results, Toast.LENGTH_LONG);
           Log.d("SEARCH_FRAGMENT", "onClick Listener");

        }

        ibFood.setOnClickListener((view) -> {
            lvFood.setVisibility(View.GONE);
            ibFood.setVisibility(View.INVISIBLE);
        });
        ibAlcohol.setOnClickListener((view)->{
            lvAlcohol.setVisibility(View.GONE);
            ibAlcohol.setVisibility(View.INVISIBLE);
        });


    }






    boolean checkInternet(Context context) {
        NetworkService serviceManager = new NetworkService(context);
        return serviceManager.isNetworkAvailable();
    }










    }






