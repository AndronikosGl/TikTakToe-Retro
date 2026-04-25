package TikTakToeRetro;
public class StrategyFactory {
    public enum stype{
        RULEBASED,
        MINIMAX,
        HEURISTIC
    }
    public static MoveStrategy create(stype choice){
        switch(choice){
            case RULEBASED:
                return new RuleBasedStrategy();
            case MINIMAX:
                return new MinimaxStrategy();
            case HEURISTIC:
                return new HeuristicStrategy();
            default:
                throw new IllegalArgumentException("Invalid strategy");
        }
    }
}
