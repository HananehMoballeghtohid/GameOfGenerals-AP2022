package Board;
import Player.Player;
import Player.BlackPlayer;
import Player.WhitePlayer;
import Pieces.Color;
import Pieces.Piece;
import java.util.HashMap;
import java.util.Map;

public class Builder {

    Map<Integer, Piece> boardConfig;
    Color nextMoveMaker;

    public Builder() {
        this.boardConfig = new HashMap<>();
    }

    public Builder setPiece(final Piece piece) {
        this.boardConfig.put(piece.getPiecePosition(), piece);
        return this;
    }

    public void setMoveMaker(final Color nextMoveMaker) {
        this.nextMoveMaker = nextMoveMaker;
    }

    public Board build() {
        return new Board(this);
    }
    public Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer) {
        if (nextMoveMaker == Color.black) {
            return blackPlayer;
        } else {
            return whitePlayer;
        }
    }
}
