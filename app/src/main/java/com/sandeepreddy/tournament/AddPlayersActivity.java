package com.sandeepreddy.tournament;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.sandeepreddy.tournament.adapters.AddPlayersRecyclerView;
import com.sandeepreddy.tournament.db.DaoSession;
import com.sandeepreddy.tournament.db.Game;
import com.sandeepreddy.tournament.db.GameDao;
import com.sandeepreddy.tournament.db.GameUserMapping;
import com.sandeepreddy.tournament.db.GameUserMappingDao;
import com.sandeepreddy.tournament.db.User;
import com.sandeepreddy.tournament.db.UserDao;
import com.sandeepreddy.tournament.generic.BaseAppCompactCollapsingToolBarActivity;
import com.sandeepreddy.tournament.utils.listeners.ClickListener;
import com.sandeepreddy.tournament.utils.listeners.RecyclerTouchListener;
import com.sandeepreddy.tournament.utils.ThemeColor;

import org.greenrobot.greendao.query.WhereCondition;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AddPlayersActivity extends BaseAppCompactCollapsingToolBarActivity implements SearchView.OnQueryTextListener {
    @BindView(R.id.addPlayersRecyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.done_button)
    Button doneButton;

    private List<User> mFriendList = new ArrayList<>(), friendList = new ArrayList<>();
    private AddPlayersRecyclerView mAdapter;
    private DaoSession daoSession;
    private Game game;
    private long tournamentId = -1;

    private static List<User> filter(List<User> models, String query) {
        final String lowerCaseQuery = query.toLowerCase();

        final List<User> filteredModelList = new ArrayList<>();
        for (User model : models) {
            final String text = model.getName().toLowerCase();
            if (text.contains(lowerCaseQuery)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_players);

        daoSession = TournamentApplication.getDaoSession();

        Intent intent = getIntent();

        if (intent != null && intent.getExtras() != null) {
            tournamentId = intent.getExtras().getLong("tournamentId");
        }

        GameDao gameDao = daoSession.getGameDao();

        WhereCondition propertyCondition = new WhereCondition.PropertyCondition(GameDao.Properties.Id, " = " + tournamentId);
        game = gameDao.queryBuilder().where(propertyCondition).unique();
        UserDao userDao = daoSession.getUserDao();
        collapsingToolbarLayout.setTitle(game.getName());
        toolbar.setTitle(game.getName());

        setToolBarBackgroundResource(ThemeColor.valueOf(game.getColor()).getPrimaryColorId());

        friendList = userDao.queryBuilder().orderDesc(UserDao.Properties.Pinned).orderAsc(UserDao.Properties.Name).list();
        mFriendList = friendList;

        mAdapter = new AddPlayersRecyclerView(mFriendList, game.getUserList(), getApplication());

        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(AddPlayersActivity.this, recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (mAdapter.toggleSelection(position)) {
                    Animation fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
                    view.findViewById(R.id.userSelected).startAnimation(fab_open);
                } else {
                    Animation fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
                    view.findViewById(R.id.userSelected).startAnimation(fab_close);
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    @OnClick(R.id.done_button)
    void onDoneClick() {
        UserDao userDao = daoSession.getUserDao();
        GameUserMappingDao gameUserMappingDao = daoSession.getGameUserMappingDao();
        List<GameUserMapping> gameUserMappings = new ArrayList<>();
        List<GameUserMapping> unSelectedUserMappings = new ArrayList<>();
        for (Long selectedUserId : mAdapter.getSelectedUserIds()) {
            GameUserMapping gameUserMapping = new GameUserMapping();
            gameUserMapping.setGameId(game.getId());
            gameUserMapping.setUserId(selectedUserId);

            gameUserMappings.add(gameUserMapping);

            User user = userDao.load(selectedUserId);
            user.setPinned(true);

            userDao.update(user);
        }

        for (Long selectedUserId : mAdapter.getUnSelectedUserIds()) {
            GameUserMapping gameUserMapping =
                    gameUserMappingDao
                            .queryBuilder()
                            .where(
                                    GameUserMappingDao.Properties.GameId.eq(game.getId()),
                                    GameUserMappingDao.Properties.UserId.eq(selectedUserId))
                            .unique();

            unSelectedUserMappings.add(gameUserMapping);
        }

        gameUserMappingDao.insertOrReplaceInTx(gameUserMappings);

        gameUserMappingDao.deleteInTx(unSelectedUserMappings);

        setResult(RESULT_OK);

        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_players, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(this);
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appBarLayout.setExpanded(false, true);
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextChange(String query) {
        mFriendList = filter(friendList, query);
        mAdapter.updateList(mFriendList);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
}
