package com.kmm.vegancheckerapp.features.Upload.ProductCRUD;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.kmm.vegancheckerapp.R;
import com.kmm.vegancheckerapp.features.Upload.UserUploadsRecyclerViewAdapter;

public class DeleteSwipe extends ItemTouchHelper.SimpleCallback {
    private final Drawable icon;
    private final ColorDrawable background;
    private final Context context;
    private final UserUploadsRecyclerViewAdapter uploadsAdapter;
    private final FragmentActivity activity;


    public DeleteSwipe(FragmentActivity activity, Context context, UserUploadsRecyclerViewAdapter adapter) {
        super(0, ItemTouchHelper.LEFT);
        this.context = context;
        this.activity = activity;
        this.uploadsAdapter = adapter;
        icon = ContextCompat.getDrawable(context, R.drawable.ic_delete_sweep);
        background = new ColorDrawable(Color.RED);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }


    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        DeleteDialogFragment deleteDialogFragment = DeleteDialogFragment.newInstance(context, position, uploadsAdapter, "Create Student");
        deleteDialogFragment.show(activity.getSupportFragmentManager(), "123");
    }
/*
Code below is based on:
StackOverflow Question: "Swipe to delete still shows red background with garbage can, recyclerview not updating",
quietLip,
https://stackoverflow.com/q/58792856
 */
    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY,
                            int actionState, boolean isCurrentlyActive) {

        View itemView = viewHolder.itemView;
        int backgroundCornerOffset = 20;
        int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
        int iconTop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
        int iconBottom = iconTop + icon.getIntrinsicHeight();

        if (dX > 0) {
            int iconLeft = itemView.getLeft() + iconMargin;
            int iconRight = iconLeft + icon.getIntrinsicWidth();
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
            background.setBounds(itemView.getLeft(), itemView.getTop(),
                    itemView.getLeft() + ((int) dX) + backgroundCornerOffset,
                    itemView.getBottom());
        } else if (dX < 0) {
            int iconLeft = itemView.getRight() - iconMargin - icon.getIntrinsicWidth();
            int iconRight = itemView.getRight() - iconMargin;
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
            background.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset,
                    itemView.getTop(), itemView.getRight(), itemView.getBottom());
        }  else {
            background.setBounds(0, 0, 0, 0);
            icon.setBounds(0, 0, 0, 0);
        }
        background.draw(c);
        icon.draw(c);

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY,
                actionState, isCurrentlyActive);
    } //END
}
