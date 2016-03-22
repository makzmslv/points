package game;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import game.dto.GameDTO;
import game.dto.GameResultDTO;
import game.dto.RoundDetailsDTO;

@Controller
public class GameController
{
    @Autowired
    private GameServiceImpl gameService;

    @RequestMapping(value = "/game", method = RequestMethod.POST)
    @ResponseBody
    public GameDTO createGame(@RequestBody GameDTO gameInDTO)
    {
        return gameService.createGame(gameInDTO);
    }

    @RequestMapping(value = "/round", method = RequestMethod.POST)
    @ResponseBody
    public List<RoundDetailsDTO> addRoundPoints(@RequestBody List<RoundDetailsDTO> roundDetails)
    {
        return gameService.addPoints(roundDetails);
    }

    @RequestMapping(value = "/total", method = RequestMethod.POST)
    @ResponseBody
    public List<GameResultDTO> totalPoints(@RequestBody Integer gameId)
    {
        return gameService.doTotal(gameId);
    }
}
