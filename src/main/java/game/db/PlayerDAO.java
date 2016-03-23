package game.db;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerDAO extends JpaRepository<PlayerEntity, Integer>
{
    public PlayerEntity findByUsernameAndVerified(String username, Boolean isVerified);

    public PlayerEntity findByUsername(String username);

    public PlayerEntity findByName(String name);

}
