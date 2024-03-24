package org.snakeLadder;

import org.snakeLadder.models.Cell;
import org.snakeLadder.models.CellState;
import org.snakeLadder.models.UserPiece;

import java.io.IOException;
import java.util.Properties;

public class SnakeAndLadderGame implements Game {

    Cell[] board  ;
    UserPiece[] userPieces ;

    private void setupBoard(){
        Properties gameConfig = new Properties();
        try {
            gameConfig.load(SnakeAndLadderGame.class.getResourceAsStream("/gameConfig.properties"));
            String snakePositionString = gameConfig.getProperty("snake_points") ;
            String ladderPositionString = gameConfig.getProperty("ladder_points") ;
            String[] snakePoints = snakePositionString.split(",");
            String[] ladderPoints = ladderPositionString.split(",");
            board =  new Cell[101] ;
            for (int i = 1; i< 101; i++){
                board[i] = new Cell(i, -1, CellState.EMPTY);
            }
            //setup snakes
            for(int i=0; i< snakePoints.length; i+=2){
                int start = Integer.parseInt(snakePoints[i]) ;
                int end = Integer.parseInt(snakePoints[i + 1]) ;
                board[start].setState(CellState.SNAKE);
                board[start].setNextPosition(end);
            }

            //setup ladders
            for(int i=0; i< ladderPoints.length; i+=2){
                int start = Integer.parseInt(ladderPoints[i]) ;
                int end = Integer.parseInt(ladderPoints[i + 1]) ;
                board[start].setState(CellState.LADDER);
                board[start].setNextPosition(end);
            }
            System.out.println("Board setup complete.");
        } catch (IOException e) {
            System.out.println("Cannot setup the Board. Unable to load game config.");
            throw new RuntimeException(e);
        }
    }
    public SnakeAndLadderGame(int playerCount){
        this.board = new Cell[101] ;
        setupBoard() ;
    }

    @Override
    public void startGame() {

    }
}
