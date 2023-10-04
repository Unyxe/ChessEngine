public class Piece {
    public enum PIECE_TYPE{
        King,
        Queen,
        Bishop,
        Knight,
        Rook,
        Pawn
    }

    public PIECE_TYPE pieceType;
    public int pieceColour;

    public Piece(PIECE_TYPE pieceType, int pieceColour){
        this.pieceType = pieceType;
        this.pieceColour = pieceColour;
    }
}
