package com.sandeepreddy.tournament.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

/**
 * Created by sandeepreddy on 4/2/17.
 */
@Entity(indexes = {
        @Index(value = "playId, playScoreId", unique = true)
})
public class PlayScoreMapping {
    @Id
    private Long id;
    private Long playId;
    private Long playScoreId;

    @Generated(hash = 1145203450)
    public PlayScoreMapping(Long id, Long playId, Long playScoreId) {
        this.id = id;
        this.playId = playId;
        this.playScoreId = playScoreId;
    }

    @Generated(hash = 1089805567)
    public PlayScoreMapping() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlayId() {
        return this.playId;
    }

    public void setPlayId(Long playId) {
        this.playId = playId;
    }

    public Long getPlayScoreId() {
        return this.playScoreId;
    }

    public void setPlayScoreId(Long playScoreId) {
        this.playScoreId = playScoreId;
    }
}
