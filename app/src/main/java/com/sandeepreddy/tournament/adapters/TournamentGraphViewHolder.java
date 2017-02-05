package com.sandeepreddy.tournament.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.mikephil.charting.charts.LineChart;
import com.sandeepreddy.tournament.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sandeepreddy on 25/1/17.
 */
class TournamentGraphViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.chart)
    LineChart lineChart;

    public TournamentGraphViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
