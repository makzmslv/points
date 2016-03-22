package game.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "GAME_HAND_RESULT")
public class GameHandResultEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "REF_GAME")
    private GameEntity gameEntity;

    @OneToOne
    @JoinColumn(name = "REF_PLAYER")
    private PlayerEntity playerEntity;

    @Column(name = "POINTS")
    private Integer points;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public GameEntity getGameEntity()
    {
        return gameEntity;
    }

    public void setGameEntity(GameEntity gameEntity)
    {
        this.gameEntity = gameEntity;
    }

    public PlayerEntity getPlayerEntity()
    {
        return playerEntity;
    }

    public void setPlayerEntity(PlayerEntity playerEntity)
    {
        this.playerEntity = playerEntity;
    }

    public Integer getPoints()
    {
        return points;
    }

    public void setPoints(Integer points)
    {
        this.points = points;
    }

}
