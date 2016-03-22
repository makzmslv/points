package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import game.db.GameDAO;
import game.db.GameEntity;
import game.db.GameHandResultDAO;
import game.db.GameHandResultEntity;
import game.db.GameResultDAO;
import game.db.GameResultEntity;
import game.db.PlayerDAO;
import game.db.PlayerEntity;
import game.dto.GameDTO;
import game.dto.GameResultDTO;
import game.dto.RoundDetailsDTO;

@Service
public class GameServiceImpl
{
    @Autowired
    private GameDAO gameDAO;

    @Autowired
    private PlayerDAO playerDAO;

    @Autowired
    private GameHandResultDAO gameHandResultDAO;

    @Autowired
    private GameResultDAO gameResultDAO;

    @Autowired
    private Mapper dozerMapper;

    public GameDTO createGame(GameDTO gameInDTO)
    {
        GameEntity gameEntity = createEntityFromDTO(gameInDTO);
        gameEntity = gameDAO.save(gameEntity);

        GameDTO gameDTO = dozerMapper.map(gameEntity, GameDTO.class);
        return gameDTO;
    }

    public List<RoundDetailsDTO> addPoints(List<RoundDetailsDTO> roundDetails)
    {
        GameEntity game = gameDAO.findOne(roundDetails.get(0).getGameId());
        List<GameHandResultEntity> gameHandResults = new ArrayList<GameHandResultEntity>();
        for (RoundDetailsDTO round : roundDetails)
        {
            PlayerEntity player = playerDAO.findOne(round.getPlayerId());
            GameHandResultEntity gameHandResultEntity = new GameHandResultEntity();
            gameHandResultEntity.setGameEntity(game);
            gameHandResultEntity.setPlayerEntity(player);
            gameHandResultEntity.setPoints(round.getPoints());
            gameHandResultDAO.save(gameHandResultEntity);
            gameHandResults.add(gameHandResultEntity);
        }
        return mapListOfEnitiesToDTOs(gameHandResults, RoundDetailsDTO.class);
    }

    public List<GameResultDTO> doTotal(Integer gameId)
    {
        GameEntity game = gameDAO.findOne(gameId);
        List<PlayerEntity> players = gameHandResultDAO.getPlayersInGame(game);
        List<GameResultEntity> gameResults = new ArrayList<GameResultEntity>();
        List<Integer> points = new ArrayList<Integer>();
        for (PlayerEntity player : players)
        {
            int totalPoints = 0;
            List<GameHandResultEntity> gameHandResults = gameHandResultDAO.findByPlayerEntity(player);
            for (GameHandResultEntity handResult : gameHandResults)
            {
                totalPoints = totalPoints + handResult.getPoints();
            }
            GameResultEntity gameResultEntity = new GameResultEntity();
            gameResultEntity.setGameEntity(game);
            gameResultEntity.setPlayerCurrentGameInstanceEntity(player);
            gameResultEntity.setPoints(totalPoints);
            gameResultDAO.save(gameResultEntity);
            gameResults.add(gameResultEntity);
            points.add(totalPoints);
        }
        setPositionsOfPlayers(gameResults, points);
        gameResults = gameResultDAO.save(gameResults);
        return mapListOfEnitiesToDTOs(gameResults, GameResultDTO.class);
    }

    private void setPositionsOfPlayers(List<GameResultEntity> gameResults, List<Integer> points)
    {
        Collections.sort(points);
        for (int i = 0; i < points.size(); i++)
        {
            for (GameResultEntity gameResult : gameResults)
            {
                if (gameResult.getPoints() == points.get(i))
                {
                    gameResult.setPosition(i + 1);
                }
            }
        }
    }

    private GameEntity createEntityFromDTO(GameDTO gameInDTO)
    {
        GameEntity gameEntity = new GameEntity();
        gameEntity.setGameType(gameInDTO.getGameType());
        gameEntity.setNoOfPlayers(gameInDTO.getNoOfPlayers());
        gameEntity.setStatus(1);

        return gameEntity;
    }

    private <FromBean, ToBean> List<ToBean> mapListOfEnitiesToDTOs(List<FromBean> beans, Class<ToBean> clazz)
    {
        List<ToBean> list = new ArrayList<ToBean>();
        for (FromBean bean : beans)
        {
            if (bean != null)
            {
                list.add(dozerMapper.map(bean, clazz));
            }
        }
        return list;
    }
}
