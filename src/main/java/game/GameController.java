package game;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
    public void addRoundPoints(@RequestBody List<RoundDetailsDTO> roundDetails)
    {
        gameService.addRoundPoints(roundDetails);
        return;
    }

    @RequestMapping(value = "/total", method = RequestMethod.POST)
    @ResponseBody
    public void totalPoints(@RequestBody Integer gameId)
    {
        gameService.doTotal(gameId);
        return;
    }

    @RequestMapping(value = "/game/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<RoundDetailsDTO> getRoundsDetails(@PathVariable Integer id)
    {
        return gameService.getRounds(id);
    }

    @RequestMapping(value = "/result/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<GameResultDTO> getResults(@PathVariable Integer id)
    {
        return gameService.getResults(id);
    }
}
