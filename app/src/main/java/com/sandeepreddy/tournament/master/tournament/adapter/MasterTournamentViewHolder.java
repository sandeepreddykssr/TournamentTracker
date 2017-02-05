package com.sandeepreddy.tournament.master.tournament.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.sandeepreddy.tournament.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sandeepreddy on 25/1/17.
 */
public class MasterTournamentViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.cardView)
    CardView cardView;

    @BindView(R.id.name)
    TextView name;

    public MasterTournamentViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
