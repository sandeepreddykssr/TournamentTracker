package com.sandeepreddy.tournament.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

/**
 * Created by sandeepreddy on 4/2/17.
 */
@Entity(indexes = {
        @Index(value = "gameId, tournamentId", unique = true)
})
public class GameTournamentMapping {
    @Id
    private Long id;
    private Long gameId;
    private Long tournamentId;

    @Generated(hash = 720272108)
    public GameTournamentMapping(Long id, Long gameId, Long tournamentId) {
        this.id = id;
        this.gameId = gameId;
        this.tournamentId = tournamentId;
    }

    @Generated(hash = 1620224736)
    public GameTournamentMapping() {
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

    public Long getTournamentId() {
        return this.tournamentId;
    }

    public void setTournamentId(Long tournamentId) {
        this.tournamentId = tournamentId;
    }
}
