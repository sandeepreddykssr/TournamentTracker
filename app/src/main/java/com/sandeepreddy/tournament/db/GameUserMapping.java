package com.sandeepreddy.tournament.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

/**
 * Created by sandeepreddy on 4/2/17.
 */
@Entity(indexes = {
        @Index(value = "gameId, userId", unique = true)
})
public class GameUserMapping {
    @Id
    private Long id;
    private Long gameId;
    private Long userId;

    @Generated(hash = 579327471)
    public GameUserMapping(Long id, Long gameId, Long userId) {
        this.id = id;
        this.gameId = gameId;
        this.userId = userId;
    }

    @Generated(hash = 625230958)
    public GameUserMapping() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGameId() {
        return this.gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
