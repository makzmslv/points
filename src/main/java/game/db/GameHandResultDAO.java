package game.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GameHandResultDAO extends JpaRepository<GameHandResultEntity, Integer>
{
    public List<GameHandResultEntity> findByPlayerEntity(PlayerEntity player);

    public List<GameHandResultEntity> findByGameEntity(GameEntity game);

    @Query(value = "SELECT DISTINCT gameHandResult.playerEntity FROM GameHandResultEntity gameHandResult WHERE gameHandResult.gameEntity = :game")
    public List<PlayerEntity> getPlayersInGame(@Param("game") GameEntity game);
}
