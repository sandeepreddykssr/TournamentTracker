package com.sandeepreddy.tournament.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.LongSparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sandeepreddy.tournament.R;
import com.sandeepreddy.tournament.db.User;
import com.sandeepreddy.tournament.utils.ThemeColor;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sandeepreddy on 25/1/17.
 */

public class AddPlayersRecyclerView extends RecyclerView.Adapter<AddPlayersViewHolder> {
    private LongSparseArray<Boolean> selectedItems;
    private Set<Long> unSelectedItems;
    private List<User> list = Collections.emptyList();
    private Context context;

    public AddPlayersRecyclerView(List<User> list, Context context) {
        this.list = list;
        this.context = context;
        selectedItems = new LongSparseArray<>();
        unSelectedItems = new HashSet<>();
        setSelectedItems(new ArrayList<User>());
    }

    public AddPlayersRecyclerView(List<User> list, List<User> selectedUser, Context context) {
        this.list = list;
        this.context = context;
        selectedItems = new LongSparseArray<>();
        unSelectedItems = new HashSet<>();
        setSelectedItems(selectedUser);
    }

    @Override
    public AddPlayersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_player, parent, false);
        return new AddPlayersViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AddPlayersViewHolder holder, int position) {
        Picasso.with(context).load(list.get(position).getImageUrl()).placeholder(ThemeColor.getThemeColor(position).getPrimaryColorId()).into(holder.profilePicture);
        holder.username.setText(list.get(position).getName());
        holder.username.setBackgroundResource(ThemeColor.getThemeColor(position).getTransparentColorId());
        if (selectedItems.get(list.get(position).getId(), false)) {
            holder.userSelected.setVisibility(View.VISIBLE);
            holder.userSelected.setScaleX(.8f);
            holder.userSelected.setScaleY(.8f);
        } else {
            holder.userSelected.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void updateList(List<User> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    // Insert a new item to the RecyclerView on a predefined position
    public void insert(int position, User data) {
        list.add(position, data);
        notifyItemInserted(position);
    }

    // Remove a RecyclerView item containing a specified FBPictureData object
    public void remove(User data) {
        int position = list.indexOf(data);
        list.remove(position);
        notifyItemRemoved(position);
    }

    public boolean toggleSelection(int pos) {
        if (selectedItems.get(list.get(pos).getId(), false)) {
            selectedItems.delete(list.get(pos).getId());
            unSelectedItems.add(list.get(pos).getId());
            return false;
        } else {
            selectedItems.put(list.get(pos).getId(), true);
            unSelectedItems.remove(list.get(pos).getId());
            return true;
        }
    }

    public void clearSelections() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    public List<Long> getSelectedUserIds() {
        List<Long> items =
                new ArrayList<>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); i++) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }

    public Set<Long> getUnSelectedUserIds() {
        return unSelectedItems;
    }

    private void setSelectedItems(List<User> selectedUser) {
        selectedItems = new LongSparseArray<>();
        unSelectedItems = new HashSet<>();
        for (User user : selectedUser) {
            selectedItems.put(user.getId(), true);
        }
    }
}
