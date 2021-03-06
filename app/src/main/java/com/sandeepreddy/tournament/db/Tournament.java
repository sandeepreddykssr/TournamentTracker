package com.sandeepreddy.tournament.db;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinProperty;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.List;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END

/**
 * Entity mapped to table "TOURNAMENT".
 */
@Entity(active = true)
public class Tournament {

    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private String name;
    private java.util.Date date;

    @ToOne
    private Game game;

    @ToMany(joinProperties = {
        @JoinProperty(name = "id", referencedName = "id")
    })
    private List<Play> playList;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 2147405735)
    private transient TournamentDao myDao;

    @Generated(hash = 36248200)
    public Tournament(Long id, @NotNull String name, java.util.Date date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }

    @Generated(hash = 1734752485)
    public Tournament() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public java.util.Date getDate() {
        return this.date;
    }

    public void setDate(java.util.Date date) {
        this.date = date;
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
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1090160933)
    public List<Play> getPlayList() {
        if (playList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PlayDao targetDao = daoSession.getPlayDao();
            List<Play> playListNew = targetDao._queryTournament_PlayList(id);
            synchronized (this) {
                if (playList == null) {
                    playList = playListNew;
                }
            }
        }
        return playList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 640270382)
    public synchronized void resetPlayList() {
        playList = null;
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
    @Generated(hash = 1122561857)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTournamentDao() : null;
    }

}
