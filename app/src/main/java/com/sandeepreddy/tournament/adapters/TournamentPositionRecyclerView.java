package com.sandeepreddy.tournament.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.sandeepreddy.tournament.R;
import com.sandeepreddy.tournament.db.User;
import com.sandeepreddy.tournament.utils.ThemeColor;
import com.sandeepreddy.tournament.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sandeepreddy on 25/1/17.
 */
public class TournamentPositionRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int USER = 0;
    private static final int GRAPH = 1;

    private List<Long> selectedItems;
    private List<User> list = Collections.emptyList();
    private Map<Long, List<Integer>> scores = new HashMap<>(), cumulativeScores = new HashMap<>();
    private Context context;

    public TournamentPositionRecyclerView(List<User> list, Context context, RecyclerView mRecyclerView) {
        this.list = list;
        this.context = context;
        selectedItems = new ArrayList<>();

        final GridLayoutManager layoutManager = (GridLayoutManager) (mRecyclerView.getLayoutManager());
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (getItemViewType(position) == GRAPH) ? layoutManager.getSpanCount() : 1;
            }
        });

    }


    @Override
    public int getItemViewType(int position) {
        if (position < list.size()) {
            return USER;
        } else {
            return GRAPH;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        switch (viewType) {
            case GRAPH:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.line_chart_layout, parent, false);
                return new TournamentGraphViewHolder(v);
            default:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_players, parent, false);
                return new TournamentPositionViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case USER: {
                TournamentPositionViewHolder tournamentPositionViewHolder = (TournamentPositionViewHolder) holder;
                User user = list.get(position);
                Picasso.with(context).load(user.getImageUrl()).placeholder(ThemeColor.getThemeColor(position).getPrimaryColorId()).into(tournamentPositionViewHolder.profilePicture);
                tournamentPositionViewHolder.username.setText(user.getName());
                tournamentPositionViewHolder.usernameBackground.setBackgroundResource(ThemeColor.getThemeColor(position).getTransparentColorId());
                tournamentPositionViewHolder.userscoreBackground.setBackgroundResource(R.color.white_ee);
                int userPosition = selectedItems.indexOf(user.getId());
                if (userPosition >= 0) {
                    tournamentPositionViewHolder.positionText.setVisibility(View.VISIBLE);
                    tournamentPositionViewHolder.positionText.setText((userPosition + 1) + "");
                } else {
                    tournamentPositionViewHolder.positionText.setVisibility(View.INVISIBLE);
                }
                if (cumulativeScores.get(user.getId()) != null) {
                    Integer cumulativeScore = cumulativeScores.get(user.getId()).get(cumulativeScores.get(user.getId()).size() - 1);
                    Integer maxScore = cumulativeScores.get(user.getId()).size() * list.size();
                    tournamentPositionViewHolder.userscore.setText(cumulativeScore + "");
                    Utils.setWeight(tournamentPositionViewHolder.userscoreBackground, maxScore - cumulativeScore);
                    Utils.setWeight(tournamentPositionViewHolder.usernameBackground, cumulativeScore);
                }
            }
            break;
            case GRAPH: {
                TournamentGraphViewHolder tournamentGraphViewHolder = (TournamentGraphViewHolder) holder;
                setData(tournamentGraphViewHolder.lineChart);
            }
            break;

        }
    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
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

    public void toggleSelection(int pos) {
        if (pos < list.size()) {
            if (selectedItems.contains(list.get(pos).getId())) {
                selectedItems.remove(list.get(pos).getId());
            } else {
                selectedItems.add(list.get(pos).getId());
            }
            notifyDataSetChanged();
        }
    }

    public boolean saveScores() {
        if (selectedItems.size() != list.size()) {
            return false;
        }
        for (int i = 0; i < selectedItems.size(); i++) {
            Long selectedItem = selectedItems.get(i);
            if (!scores.containsKey(selectedItem)) {
                scores.put(selectedItem, new ArrayList<Integer>());
                cumulativeScores.put(selectedItem, new ArrayList<Integer>());
            }
            int score = selectedItems.size() - i;
            scores.get(selectedItem).add(score);
            int cumulativeScore =
                    (cumulativeScores.get(selectedItem).size() > 0
                            ? cumulativeScores.get(selectedItem).get(cumulativeScores.get(selectedItem).size() - 1)
                            : 0)
                            + score;
            cumulativeScores.get(selectedItem).add(cumulativeScore);
        }
        selectedItems.clear();
        notifyDataSetChanged();
        return true;
    }

    public void clearSelections() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    public List<Long> getSelectedUserPositions() {
        return selectedItems;
    }

    private void setData(LineChart mChart) {
        LineData lineData;

        if (mChart.getData() == null) {
            lineData = new LineData();
            mChart.setData(lineData);
        } else {
            lineData = mChart.getData();
        }

        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            ArrayList<Entry> userScore = new ArrayList<>();
            userScore.add(new Entry(0,0));

            if (scores.containsKey(user.getId())) {
                int cumulativeScore = 0;
                for (int j = 0; j < scores.get(user.getId()).size(); j++) {
                    cumulativeScore += scores.get(user.getId()).get(j);
                    userScore.add(new Entry(j + 1, cumulativeScore));
                }

                LineDataSet set;
                if (lineData.getDataSetCount() > i) {
                    set = (LineDataSet) mChart.getData().getDataSetByIndex(i);
                    set.setValues(userScore);
                } else {

                    set = new LineDataSet(userScore, user.getName());

                    set.setAxisDependency(YAxis.AxisDependency.LEFT);
                    set.setColor(Utils.getColor(context, ThemeColor.getThemeColor(i).getPrimaryColorId()));
                    set.setCircleColor(Utils.getColor(context, ThemeColor.getThemeColor(i).getTransparentColorId()));
                    set.setLineWidth(2f);
                    set.setCircleRadius(3f);
                    set.setFillAlpha(65);
                    set.setFillColor(Utils.getColor(context, ThemeColor.getThemeColor(i).getPrimaryColorId()));
                    set.setHighLightColor(Color.rgb(244, 117, 117));
                    set.setDrawCircleHole(false);

                    lineData.addDataSet(set);
                }
            }
        }
        XAxis xAxis = mChart.getXAxis();
        xAxis.setTextSize(11f);
        xAxis.setAxisMinimum(0);
        xAxis.setAxisMaximum(10);
        xAxis.setTextColor(ColorTemplate.getHoloBlue());
        xAxis.setDrawGridLines(true);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularityEnabled(true);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        leftAxis.setAxisMaximum(40f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawZeroLine(false);
        rightAxis.setGranularityEnabled(false);

        Description description = new Description();
        description.setText("Cumulative Score");
        mChart.setDescription(description);
        mChart.invalidate();
        mChart.getData().notifyDataChanged();
        mChart.notifyDataSetChanged();
    }
}
