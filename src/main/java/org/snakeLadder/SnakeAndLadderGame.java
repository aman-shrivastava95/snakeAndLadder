package org.snakeLadder;

import org.snakeLadder.models.Cell;
import org.snakeLadder.models.CellState;
import org.snakeLadder.models.Dice;
import org.snakeLadder.models.UserPiece;

import java.io.IOException;
import java.util.Properties;
import java.util.Random;

public class SnakeAndLadderGame implements Game {

    private final int size = 100; 
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
            board =  new Cell[size] ;
            for (int i = 1; i< size; i++){
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
    
    private void setupPlayers(int playerCount){
        if(playerCount > 4){
            System.out.println("Player count cannot be more than 4");
        } 
        userPieces = new UserPiece[playerCount] ;
        for (int i =1 ; i<= playerCount ;i++ ){
            userPieces[i-1] = new UserPiece(0,"Player-"+i);
        }
    }
    
    public SnakeAndLadderGame(int playerCount){
        this.board = new Cell[size] ;
        setupBoard() ;
        setupPlayers(playerCount) ;
    }

    private boolean checkForWinner(int position){
        return position == size ;
    }

    private int getPositionOnSnakeOrLadder(int currentPosition){
        Cell cell = board[currentPosition] ;
        int nextPostion = currentPosition ;
        if(cell.getState().equals(CellState.SNAKE) || cell.getState().equals(CellState.LADDER)){
            nextPostion  = cell.getNextPosition();
        }
        return nextPostion ;
    }

    @Override
    public void startGame() {
        Random random =  new Random() ;
        Dice dice =  new Dice(6, random) ;
        int currentPlayer = random.nextInt(4) ;
        while(true){
            int steps = dice.rollDice() ;
            UserPiece currentPiece = userPieces[currentPlayer] ;
            System.out.println(currentPiece.getPlayer() + " played, dice roll is "+ steps);
            int currentPosition = currentPiece.getPosition() ;
            int nextPosition = currentPiece.getPosition() + steps ;
            if (nextPosition > size){
                // player staying at current position, if overshooting the finish
                currentPlayer = (currentPlayer + 1)%4 ;
                continue;
            }
            if(checkForWinner(nextPosition)){
                System.out.println(currentPiece.getPlayer() + " won the game!!!");
                break ;
            }
            nextPosition = getPositionOnSnakeOrLadder(nextPosition);
            currentPiece.setPosition(nextPosition);
            System.out.println(currentPiece.getPlayer() + " moved from " + currentPosition + " to " + currentPiece.getPosition()  );
            currentPlayer = (currentPlayer + 1)%4 ;
        }
    }
}
