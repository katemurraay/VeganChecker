package com.kmm.vegancheckerapp.features.Upload.ProductCRUD;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.kmm.vegancheckerapp.R;
import com.kmm.vegancheckerapp.features.ShowToast;
import com.kmm.vegancheckerapp.features.Upload.UserUploadsRecyclerViewAdapter;
import com.kmm.vegancheckerapp.model.Product;
import com.kmm.vegancheckerapp.utils.IConstants;

import java.util.Objects;

public class DeleteDialogFragment extends DialogFragment implements View.OnClickListener, LoaderManager.LoaderCallbacks<Integer> {
UserUploadsRecyclerViewAdapter adapter;
int position;
Context context;
    Product product;
    View view;
    public DeleteDialogFragment(Context context, UserUploadsRecyclerViewAdapter adapter, int position){
        this.adapter = adapter;
        this.context = context;
        this.position = position;

    }

    public static DeleteDialogFragment newInstance(Context context, int position, UserUploadsRecyclerViewAdapter adapter, String title){
        DeleteDialogFragment deleteDialogFragment = new DeleteDialogFragment(context, adapter, position);
        Bundle args = new Bundle();
        args.putString("title", title);
        deleteDialogFragment.setArguments(args);


     deleteDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
      return deleteDialogFragment;


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       view = inflater.inflate(R.layout.dialog_fragment_delete, container, false);
        Button btnBack = view.findViewById(R.id.btnBack);
        Button btnDelete = view.findViewById(R.id.btnDelete);
        TextView tvTitle = view.findViewById(R.id.tvDialogTitle);


        product = IConstants.USER_UPLOADS.get(position);
        String strTitle = "Are you sure you want to delete " + product.getProductBrand() + " " + product.getProductName() + "?";
        tvTitle.setText(strTitle);
        btnBack.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
        case R.id.btnBack:
            Objects.requireNonNull(getDialog()).dismiss();
            adapter.notifyDataSetChanged();
        break;
        case R.id.btnDelete:
            if(checkInternetConnection()) {
                androidx.loader.app.LoaderManager.getInstance(this).initLoader(IConstants.DELETEPRODUCTLOADERID, null, this);
            } else{
                ShowToast toast = new ShowToast();
                toast.makeImageToast(getContext(), R.drawable.ic_wifi_off, R.string.no_wifi, Toast.LENGTH_LONG);
            }
            break;

        }}

    public boolean checkInternetConnection() {

        ConnectivityManager connectivityManager = (ConnectivityManager) (requireActivity()).getSystemService(Context.CONNECTIVITY_SERVICE);


        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
    }// END
    @NonNull
    @Override
    public Loader<Integer> onCreateLoader(int id, @Nullable Bundle args) {

    return new com.kmm.vegancheckerapp.features.LoaderManager.DeleteProductLoader((context), product);

    }

    @Override
    public void onLoadFinished(@NonNull Loader<Integer> loader, Integer data) {
    ShowToast toast = new ShowToast();
        if(data == 1){
            toast.makeImageToast(getContext(), R.drawable.ic_check, R.string.delete_product, Toast.LENGTH_LONG);
            IConstants.USER_UPLOADS.remove(product);
            IConstants.PRODUCT_BARCODES.remove(product.getBarcode());
            adapter.notifyItemRemoved(position);
            adapter.notifyItemRangeChanged(position, IConstants.USER_UPLOADS.size());
            Objects.requireNonNull(getDialog()).dismiss();

        } else{
            toast.makeImageToast(getContext(), R.drawable.ic_error, R.string.no_db, Toast.LENGTH_LONG);
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Integer> loader) {

    }
}
