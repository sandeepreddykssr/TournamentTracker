package com.sandeepreddy.tournament.adapters;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sandeepreddy.tournament.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sandeepreddy on 25/1/17.
 */
class AddPlayersViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.profile_picture)
    ImageView profilePicture;

    @BindView(R.id.username)
    TextView username;

    @BindView(R.id.userSelected)
    FloatingActionButton userSelected;

    public AddPlayersViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
