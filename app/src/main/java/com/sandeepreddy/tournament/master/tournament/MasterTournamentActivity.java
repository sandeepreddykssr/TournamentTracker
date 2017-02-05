package com.sandeepreddy.tournament.master.tournament;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.sandeepreddy.tournament.PlayersActivity;
import com.sandeepreddy.tournament.R;
import com.sandeepreddy.tournament.TournamentApplication;
import com.sandeepreddy.tournament.db.DaoSession;
import com.sandeepreddy.tournament.db.Game;
import com.sandeepreddy.tournament.generic.BaseAppCompactToolBarActivity;
import com.sandeepreddy.tournament.master.tournament.adapter.MasterTournamentRecyclerView;
import com.sandeepreddy.tournament.utils.listeners.ClickListener;
import com.sandeepreddy.tournament.utils.listeners.RecyclerTouchListener;
import com.sandeepreddy.tournament.utils.ThemeColor;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.sandeepreddy.tournament.R.id.fab;

public class MasterTournamentActivity extends BaseAppCompactToolBarActivity {

    private DaoSession daoSession;
    private MasterTournamentRecyclerView mAdapter;

    @BindView(R.id.masterTournamentRecyclerView)
    RecyclerView recyclerView;
    @BindView(fab)
    FloatingActionButton addTournamentFAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_tournament);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        daoSession = TournamentApplication.getDaoSession();

        ButterKnife.bind(this);

        List<Game> games = daoSession.getGameDao().loadAll();
        mAdapter = new MasterTournamentRecyclerView(games, this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(MasterTournamentActivity.this, PlayersActivity.class);
                Game item = mAdapter.getItem(position);

                intent.putExtra("tournamentId", item.getId());

                Pair<View, String> p1 = Pair.create(view.findViewById(R.id.cardView), getString(R.string.app_bar_background_transition_name));

                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(MasterTournamentActivity.this, p1);

                ActivityCompat.startActivity(MasterTournamentActivity.this, intent, options.toBundle());

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    @OnClick(R.id.fab)
    void addTournament() {
        LayoutInflater inflater = this.getLayoutInflater();
        final View alertDialogView = inflater.inflate(R.layout.dialog_add_tournament, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(alertDialogView);
        alertDialogBuilder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                EditText editText = (EditText) alertDialogView.findViewById(R.id.input_tournament_name);
                Game game = new Game();
                game.setName(editText.getText().toString());
                game.setColor(ThemeColor.getRandomThemeColor().name());

                daoSession.getGameDao().insert(game);

                mAdapter.updateList(daoSession.getGameDao().loadAll());
            }
        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }
}
