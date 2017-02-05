package com.sandeepreddy.tournament;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.sandeepreddy.tournament.adapters.AddPlayersRecyclerView;
import com.sandeepreddy.tournament.db.DaoSession;
import com.sandeepreddy.tournament.db.Game;
import com.sandeepreddy.tournament.db.GameDao;
import com.sandeepreddy.tournament.db.GameUserMapping;
import com.sandeepreddy.tournament.db.User;
import com.sandeepreddy.tournament.generic.BaseAppCompactCollapsingToolBarActivity;
import com.sandeepreddy.tournament.utils.listeners.ClickListener;
import com.sandeepreddy.tournament.utils.listeners.RecyclerTouchListener;
import com.sandeepreddy.tournament.utils.ThemeColor;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PlayersActivity extends BaseAppCompactCollapsingToolBarActivity {

    private static final int ADD_USER = 10001;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.play_fab)
    FloatingActionButton playFAB;
    @BindView(R.id.addMe)
    FloatingActionButton addPlayers;
    @BindView(R.id.addMeText)
    View addPlayersTextView;
    @BindView(R.id.addFBPlayers)
    FloatingActionButton addFBPlayers;
    @BindView(R.id.addFBPlayersText)
    View addFBPlayersTextView;
    @BindView(R.id.playerRecyclerView)
    RecyclerView recyclerView;

    private AddPlayersRecyclerView mAdapter;
    private long tournamentId = -1;
    private boolean isFabOpen = false, isFabPlayOpen = false;
    private Animation fab_open, fab_close, fab_open_text_view, fab_close_text_view, rotate_forward, rotate_backward;
    private DaoSession daoSession;
    private Game game;
    private GameDao gameDao;
    private List<User> friendList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);

        daoSession = TournamentApplication.getDaoSession();

        Intent intent = getIntent();

        if (intent != null && intent.getExtras() != null) {
            tournamentId = intent.getExtras().getLong("tournamentId");
        }

        gameDao = daoSession.getGameDao();

        game = gameDao.load(tournamentId);
        collapsingToolbarLayout.setTitle(game.getName());
        toolbar.setTitle(game.getName());

        setToolBarBackgroundResource(ThemeColor.valueOf(game.getColor()).getPrimaryColorId());

        initAnim();

        friendList = game.getUserList();
        mAdapter = new AddPlayersRecyclerView(game.getUserList(), getApplication());

        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(PlayersActivity.this, recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (mAdapter.toggleSelection(position)) {
                    Animation fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
                    view.findViewById(R.id.userSelected).startAnimation(fab_open);
                } else {
                    Animation fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
                    view.findViewById(R.id.userSelected).startAnimation(fab_close);
                }

                updateFab();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void initAnim() {
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_open_text_view = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fab_close_text_view = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
    }

    private void updateFab() {
        if (mAdapter.getSelectedItemCount() > 0) {
            if (!isFabPlayOpen) {
                playFAB.startAnimation(fab_open);
                fab.startAnimation(fab_close);
                playFAB.setClickable(true);
                fab.setClickable(false);
                isFabPlayOpen = true;
            }
        } else {
            playFAB.startAnimation(fab_close);
            fab.startAnimation(fab_open);
            playFAB.setClickable(false);
            fab.setClickable(true);
            isFabPlayOpen = false;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @OnClick(R.id.fab)
    void animateFab() {
        if (isFabOpen) {
            fab.startAnimation(rotate_backward);
            addPlayers.startAnimation(fab_close);
            addFBPlayers.startAnimation(fab_close);
            addPlayersTextView.startAnimation(fab_close_text_view);
            addFBPlayersTextView.startAnimation(fab_close_text_view);
            addPlayers.setClickable(false);
            addFBPlayers.setClickable(false);
            isFabOpen = false;
        } else {
            fab.startAnimation(rotate_forward);
            addPlayers.startAnimation(fab_open);
            addFBPlayers.startAnimation(fab_open);
            addPlayersTextView.startAnimation(fab_open_text_view);
            addFBPlayersTextView.startAnimation(fab_open_text_view);
            addPlayers.setClickable(true);
            addFBPlayers.setClickable(true);
            isFabOpen = true;
        }
    }

    @OnClick(R.id.play_fab)
    void onPlayClick() {
        Intent intent = new Intent(this, TournamentActivity.class);
        intent.putExtra(getString(R.string.selectedUsers), new ArrayList<>(mAdapter.getSelectedUserIds()));
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ADD_USER:
                updateGame();
        }
    }

    @OnClick(R.id.addFBPlayers)
    void addFBPlayers() {
        Intent intent = new Intent(this, AddPlayersActivity.class);
        intent.putExtra("tournamentId", game.getId());
        startActivityForResult(intent, ADD_USER);
    }

    @OnClick(R.id.addMe)
    void addMe() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        long id = sharedPreferences.getLong(getString(R.string.userId), 0);

        GameUserMapping userMapping = new GameUserMapping();
        userMapping.setUserId(id);
        userMapping.setGameId(game.getId());

        daoSession.getGameUserMappingDao().insertOrReplace(userMapping);
        updateGame();
    }

    private void updateGame() {
        game = gameDao.load(tournamentId);
        game.resetUserList();
        mAdapter.updateList(game.getUserList());
    }
}