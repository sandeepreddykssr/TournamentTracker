package com.sandeepreddy.tournament.db;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinEntity;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.List;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END

/**
 * Entity mapped to table "PLAY".
 */
@Entity(active = true)
public class Play {

    @Id(autoincrement = true)
    private Long id;
    private int position;

    @ToOne
    private Game game;

    @ToMany
    @JoinEntity(
            entity = PlayScoreMapping.class,
            sourceProperty = "playId",
            targetProperty = "playScoreId"
    )
    private List<PlayScore> gameScoreList;

    @ToOne
    private Tournament tournament;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1885420305)
    private transient PlayDao myDao;

    @Generated(hash = 422752093)
    public Play(Long id, int position) {
        this.id = id;
        this.position = position;
    }

    @Generated(hash = 1596271462)
    public Play() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Generated(hash = 376066354)
    private transient boolean game__refreshed;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1583764240)
    public Game getGame() {
        if (game != null || !game__refreshed) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            GameDao targetDao = daoSession.getGameDao();
            targetDao.refresh(game);
            game__refreshed = true;
        }
        return game;
    }

    /** To-one relationship, returned entity is not refreshed and may carry only the PK property. */
    @Generated(hash = 583080738)
    public Game peakGame() {
        return game;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1040488606)
    public void setGame(Game game) {
        synchronized (this) {
            this.game = game;
            game__refreshed = true;
        }
    }

    @Generated(hash = 1687874533)
    private transient boolean tournament__refreshed;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1710116072)
    public Tournament getTournament() {
        if (tournament != null || !tournament__refreshed) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TournamentDao targetDao = daoSession.getTournamentDao();
            targetDao.refresh(tournament);
            tournament__refreshed = true;
        }
        return tournament;
    }

    /** To-one relationship, returned entity is not refreshed and may carry only the PK property. */
    @Generated(hash = 55117973)
    public Tournament peakTournament() {
        return tournament;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 696385401)
    public void setTournament(Tournament tournament) {
        synchronized (this) {
            this.tournament = tournament;
            tournament__refreshed = true;
        }
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 778984746)
    public List<PlayScore> getGameScoreList() {
        if (gameScoreList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PlayScoreDao targetDao = daoSession.getPlayScoreDao();
            List<PlayScore> gameScoreListNew = targetDao
                    ._queryPlay_GameScoreList(id);
            synchronized (this) {
                if (gameScoreList == null) {
                    gameScoreList = gameScoreListNew;
                }
            }
        }
        return gameScoreList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1879093301)
    public synchronized void resetGameScoreList() {
        gameScoreList = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 780012150)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getPlayDao() : null;
    }

}
