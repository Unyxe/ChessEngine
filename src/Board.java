import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Objects;

public class Board {
    public Piece[][] pieces = new Piece[8][8];
    public int turn;

    public boolean[][] castlingRights;
    public int[] enPassantPos;

    public int halfMoveCount;
    public int fullMoveCount;


    public Board(){
        ImportFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq 0 0");
    }

    public Board(String FEN){
        ImportFEN(FEN);
    }

    public void ImportFEN(String fen){
        String[] fields = fen.split(" ");
        String pos = fields[0];
        String turn = fields[1];
        String castlingRights = fields[2];
        int p = 0;
        String en_passant;
        int halfMoveCount;
        int fullMoveCount;
        try{
            Integer.parseInt(fields[3]);
        }catch(NumberFormatException e){
            en_passant = fields[3];
            enPassantPos = moveNotationToPos(en_passant);
            p++;
        }
        halfMoveCount = Integer.parseInt(fields[3+p]);
        fullMoveCount = Integer.parseInt(fields[4+p]);
        this.halfMoveCount = halfMoveCount;
        this.fullMoveCount = fullMoveCount;
        this.turn = Objects.equals(turn, "w")?0:1;
        this.castlingRights = new boolean[][]{{false, false}, {false, false}};
        for(int i = 0; i < castlingRights.length();i++){
            String r = castlingRights.charAt(i)+"";
            boolean is_black = (r.equals(r.toLowerCase()));
            if(r.equalsIgnoreCase("k")){
                this.castlingRights[is_black?1:0][1] = true;
            } else{
                this.castlingRights[is_black?1:0][0] = true;
            }
        }


        int[] pointer = {0, 0};

        Dictionary<String, Piece.PIECE_TYPE> p_types= new Hashtable<>();
        p_types.put("k", Piece.PIECE_TYPE.King);
        p_types.put("q", Piece.PIECE_TYPE.Queen);
        p_types.put("b", Piece.PIECE_TYPE.Bishop);
        p_types.put("n", Piece.PIECE_TYPE.Knight);
        p_types.put("r", Piece.PIECE_TYPE.Rook);
        p_types.put("p", Piece.PIECE_TYPE.Pawn);


        for(int i =0; i < pos.length();i++){
            String piece = pos.charAt(i) + "";
            if(Objects.equals(piece, "/")){
                pointer[1]++;
                pointer[0] = 0;
                continue;
            }
            try {
                int num = Integer.parseInt(piece);
                pointer[0] += num;
                continue;
            }catch(NumberFormatException ignored){}

            Piece.PIECE_TYPE pieceType = p_types.get(piece.toLowerCase());
            boolean is_black = (piece.equals(piece.toLowerCase()));
            pieces[pointer[1]][pointer[0]] = new Piece(pieceType, is_black?1:0);
            pointer[0]++;
        }
    }

    public int[] moveNotationToPos(String move){
        String file = move.charAt(0)+"";
        int rank = Integer.parseInt(move.charAt(1)+"");
        return new int[]{"abcdefgh".indexOf(file), 8-rank};
    }

    public void DisplayBoardConsole(){
        Dictionary<Piece.PIECE_TYPE,String> p_types= new Hashtable<>();
        p_types.put(Piece.PIECE_TYPE.King,  "k");
        p_types.put(Piece.PIECE_TYPE.Queen, "q");
        p_types.put(Piece.PIECE_TYPE.Bishop,"b");
        p_types.put(Piece.PIECE_TYPE.Knight,"n");
        p_types.put(Piece.PIECE_TYPE.Rook,  "r");
        p_types.put(Piece.PIECE_TYPE.Pawn,  "p");
        for(int i = 0; i < 8;i++){
            for(int j = 0; j < 8;j++){
                String piece_d = " ";
                Piece piece = pieces[i][j];
                if(piece != null){
                    //System.out.println(piece.pieceColour);
                    piece_d = p_types.get(piece.pieceType);
                    if(piece.pieceColour == 0){
                        piece_d = piece_d.toUpperCase();
                    }
                }
                System.out.print(piece_d+"|");
            }
            System.out.println();
        }
    }
}
