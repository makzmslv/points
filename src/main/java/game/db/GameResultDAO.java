package game.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GameResultDAO extends JpaRepository<GameResultEntity, Integer>
{
    public GameResultEntity findByGameEntityAndPosition(GameEntity gameEntity, Integer position);

    public List<GameResultEntity> findByGameEntityOrderByPositionAsc(GameEntity gameEntity);
}
