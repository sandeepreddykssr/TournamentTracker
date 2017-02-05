package com.sandeepreddy.tournament;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.sandeepreddy.tournament.adapters.TournamentPositionRecyclerView;
import com.sandeepreddy.tournament.db.DaoSession;
import com.sandeepreddy.tournament.db.User;
import com.sandeepreddy.tournament.db.UserDao;
import com.sandeepreddy.tournament.generic.BaseAppCompactToolBarActivity;
import com.sandeepreddy.tournament.utils.listeners.ClickListener;
import com.sandeepreddy.tournament.utils.listeners.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TournamentActivity extends BaseAppCompactToolBarActivity {

    private static final String TAG = TournamentActivity.class.getSimpleName();

    @BindView(R.id.playerRecyclerView)
    RecyclerView recyclerView;

    private DaoSession daoSession;
    private UserDao userDao;
    private TournamentPositionRecyclerView mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament);

        daoSession = TournamentApplication.getDaoSession();
        userDao = daoSession.getUserDao();
        Intent intent = getIntent();
        ArrayList<Long> selectedUserIds = new ArrayList<>();
        if (intent != null && intent.getExtras() != null) {
            selectedUserIds = (ArrayList<Long>) intent.getExtras().getSerializable(getString(R.string.selectedUsers));
        }
        Log.d(TAG, selectedUserIds.size() + "");

        List<User> userList = new ArrayList<>();
        for (Long selectedUserId : selectedUserIds) {
            userList.add(userDao.load(selectedUserId));
        }
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        mAdapter = new TournamentPositionRecyclerView(userList, getApplication(), recyclerView);

        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(TournamentActivity.this, recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                mAdapter.toggleSelection(position);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    @OnClick(R.id.next_button)
    void nextButtonClick () {
        mAdapter.saveScores();
    }
}
