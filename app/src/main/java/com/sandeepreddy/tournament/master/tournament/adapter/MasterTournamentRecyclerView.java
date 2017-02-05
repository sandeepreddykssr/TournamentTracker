package com.sandeepreddy.tournament.master.tournament.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sandeepreddy.tournament.R;
import com.sandeepreddy.tournament.db.Game;
import com.sandeepreddy.tournament.utils.Utils;
import com.sandeepreddy.tournament.utils.ThemeColor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by sandeepreddy on 25/1/17.
 */
public class MasterTournamentRecyclerView extends RecyclerView.Adapter<MasterTournamentViewHolder> {
    private SparseBooleanArray selectedItems;
    private List<Game> list = Collections.emptyList();
    private Context context;

    public MasterTournamentRecyclerView(List<Game> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MasterTournamentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tournament, parent, false);
        return new MasterTournamentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MasterTournamentViewHolder holder, int position) {
        holder.cardView.setCardBackgroundColor(Utils.getColor(context, ThemeColor.valueOf(list.get(position).getColor()).getPrimaryColorId()));
        holder.name.setText(list.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public Game getItem(int position) {
        return list.get(position);
    }
    public void updateList(List<Game> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    // Insert a new item to the RecyclerView on a predefined position
    public void insert(int position, Game data) {
        list.add(position, data);
        notifyItemInserted(position);
    }

    // Remove a RecyclerView item containing a specified FBPictureData object
    public void remove(Game data) {
        int position = list.indexOf(data);
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void toggleSelection(int pos) {
        if (selectedItems.get(pos, false)) {
            selectedItems.delete(pos);
        } else {
            selectedItems.put(pos, true);
        }
        notifyItemChanged(pos);
    }

    public void clearSelections() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    public List<Integer> getSelectedItems() {
        List<Integer> items =
                new ArrayList<Integer>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); i++) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }

}
