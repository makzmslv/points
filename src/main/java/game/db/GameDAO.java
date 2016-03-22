package game.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GameDAO extends JpaRepository<GameEntity, Integer>
{
    public List<GameEntity> findByStatusIn(List<Integer> status);
}
