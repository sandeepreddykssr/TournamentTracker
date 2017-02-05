package com.sandeepreddy.tournament.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END

/**
 * Entity mapped to table "PLAY_SCORE".
 */
@Entity(active = true)
public class PlayScore {

    @Id(autoincrement = true)
    private Long id;
    private int score;

    @ToOne
    private User user;

    @ToOne
    private Play play;

    @ToOne
    private Tournament tournament;

    @ToOne
    private Game game;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 388817980)
    private transient PlayScoreDao myDao;

    @Generated(hash = 352469486)
    public PlayScore(Long id, int score) {
        this.id = id;
        this.score = score;
    }

    @Generated(hash = 1511536379)
    public PlayScore() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Generated(hash = 2122020312)
    private transient boolean user__refreshed;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 2090372739)
    public User getUser() {
        if (user != null || !user__refreshed) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UserDao targetDao = daoSession.getUserDao();
            targetDao.refresh(user);
            user__refreshed = true;
        }
        return user;
    }

    /** To-one relationship, returned entity is not refreshed and may carry only the PK property. */
    @Generated(hash = 1689829570)
    public User peakUser() {
        return user;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 937749514)
    public void setUser(User user) {
        synchronized (this) {
            this.user = user;
            user__refreshed = true;
        }
    }

    @Generated(hash = 1286519889)
    private transient boolean play__refreshed;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1966249360)
    public Play getPlay() {
        if (play != null || !play__refreshed) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PlayDao targetDao = daoSession.getPlayDao();
            targetDao.refresh(play);
            play__refreshed = true;
        }
        return play;
    }

    /** To-one relationship, returned entity is not refreshed and may carry only the PK property. */
    @Generated(hash = 1283462484)
    public Play peakPlay() {
        return play;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1663684408)
    public void setPlay(Play play) {
        synchronized (this) {
            this.play = play;
            play__refreshed = true;
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
    @Generated(hash = 770406100)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getPlayScoreDao() : null;
    }
}