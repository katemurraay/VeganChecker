package com.kmm.vegancheckerapp.features.Upload;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.kmm.vegancheckerapp.R;
import com.kmm.vegancheckerapp.features.LoaderManager;
import com.kmm.vegancheckerapp.features.NetworkService;
import com.kmm.vegancheckerapp.features.ShowToast;
import com.kmm.vegancheckerapp.features.Upload.ProductCRUD.DeleteSwipe;
import com.kmm.vegancheckerapp.features.Upload.ProductCRUD.UploadActivity;
import com.kmm.vegancheckerapp.model.Product;
import com.kmm.vegancheckerapp.utils.IConstants;

import java.util.ArrayList;

public class UserFragment extends Fragment implements androidx.loader.app.LoaderManager.LoaderCallbacks<ArrayList<Product>> {
   private RecyclerView rcUploads;
 LottieAnimationView lottieAnimationView;

  DeleteSwipe itemTouchHelperCallback;
   UserUploadsRecyclerViewAdapter adapter;
   LayoutInflater inflater;
    View v;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
      v = inflater.inflate(R.layout.fragment_user, container, false);
        this.inflater = inflater;
        Toolbar toolbar =   v.findViewById(R.id.tbUser);
        rcUploads = v.findViewById(R.id.rcUserUploads);
        lottieAnimationView = v.findViewById(R.id.lottieUserUploads);

if(checkInternet(getContext())) {
    androidx.loader.app.LoaderManager.getInstance(this).initLoader(IConstants.USERUPLOADLOADERID, null, this);
} else{
    lottieAnimationView.setVisibility(View.GONE);
    ShowToast toast = new ShowToast();
    toast.makeImageToast(getContext(), R.drawable.ic_wifi_off, R.string.no_wifi, Toast.LENGTH_LONG);
}
        ((AppCompatActivity)requireActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);


        return v;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_upload, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add_upload) {
            if (IConstants.PRODUCT_BARCODES != null) {
                Intent intent = new Intent(getActivity(), UploadActivity.class);
                intent.putExtra("BARCODE", IConstants.PRODUCT_BARCODES);
                startActivity(intent);
            } else {

                ShowToast toast = new ShowToast();
                toast.makeImageToast(getContext(), R.drawable.ic_error, R.string.no_db, Toast.LENGTH_LONG);
            }}


            return super.onOptionsItemSelected(item);

    }

    @NonNull
    @Override
    public Loader<ArrayList<Product>> onCreateLoader(int id, @Nullable Bundle args) {

        return new LoaderManager.UserUploadLoader(requireContext());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Product>> loader, ArrayList<Product> data) {
           lottieAnimationView.setVisibility(View.GONE);


            IConstants.USER_UPLOADS = new ArrayList<>(data);
            adapter = new UserUploadsRecyclerViewAdapter(getContext(), IConstants.USER_UPLOADS);
            rcUploads.setAdapter(adapter);
            rcUploads.setHasFixedSize(true);
            itemTouchHelperCallback = new DeleteSwipe(getActivity(), getContext(), adapter);
            new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(rcUploads);

            rcUploads.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));


    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Product>> loader) {

    }
    boolean checkInternet(Context context) {
        NetworkService serviceManager = new NetworkService(context);
        return serviceManager.isNetworkAvailable();
    }


}


