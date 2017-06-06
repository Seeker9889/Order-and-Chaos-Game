/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamespace;

/**
 *
 * @author SBG
 */
public class GameState {
    
    //Variable declaration
    public enum CellState { X, O, EMPTY };
    public enum GState {CHAOS_WIN, ORDER_WIN, SAME};
    //The visual grid is divided into a virtual grid
    public CellState[][] Grid;
    
    //Initiate a blank virtual grid
    public GameState(int side) {
        //Allows a change in grid size
        Grid=new CellState[side][side];
        
        //Set initial state of all cells to EMPTY
        clearGrid();
    }
    
    //MIGHT NOT BE WORKING
    //Set all cells to EMPTY
    public final void clearGrid() {
        for (CellState[] cRow : Grid) {
            for (CellState cell: cRow) {
                cell=CellState.EMPTY;
            }
        }
    }
    
    //PUBLIC STATE CHANGE
    //Public access to cells
    public GState changeState(int row, int col, CellState newState) {
        GState currentState;
        changeCell(row, col, newState);
        
        //Determine current game state
        currentState=checkGameEnd(Grid, row, col);
        return currentState;
    }
    
    //PUBLIC ACCESSORS
    public CellState getCellState(int row, int col) {
        return Grid[row][col];
    }
    
    //PRIVATE PROCESSING
    //Change state of a cell
    private void changeCell(int row, int col, CellState newState) {
        Grid[row][col]=newState;
    }
    
    //Determine current game state
    private GState checkGameEnd(CellState[][] checkG, int cRow, int cCol) {
        //A state of SAME is assumed; i.e. it is assumed that no one won
        GState state=GState.SAME;
        if (didOrderWin(checkG, cRow, cCol)) {
            state=GState.ORDER_WIN;
        } else if(didChaosWin(checkG, cRow, cCol)) {
            state=GState.CHAOS_WIN;
        }
        return state;
    }
    
    //Check in each direction from a given cell for an Order win
    private boolean didOrderWin(CellState[][] checkG, int cRow, int cCol) {
        boolean orderWin=false;
        CellState current=getCellState(cRow, cCol);
        //The number of matching states in a row
        int sameInRow=1;
        //Magic number 5 is the default number of the same Xs or Os Order needs to win
        int targetSameInRow=5;
        
        //Set variables to keep track of currently checked position
        int r=cRow;
        int c=cCol;
        
        //Check top-left
        while (--r>=0 && --c>=0) {
            if (getCellState(r, c)==current) {
                sameInRow+=1;
            } else {
                r=-1;
                c-=1;
            }
        }
        
        //Check top
        if (sameInRow<targetSameInRow) {
            sameInRow=1;
            r=cRow;
            c=cCol;
        
            while (--r>=0) {
                if (getCellState(r, c)==current) {
                    sameInRow+=1;
                } else {
                    r=-1;
                }
            }
        }
        
        //Check top-right
        if (sameInRow<targetSameInRow) {
            sameInRow=1;
            r=cRow;
            c=cCol;
            
            while (--r>=0 && ++c<checkG[r].length) {
                    if (getCellState(r, c)==current) {
                        sameInRow+=1;
                    } else {
                        r=-1;
                    }
                }
        }
        
        //Check right
        if (sameInRow<targetSameInRow) {
            sameInRow=1;
            r=cRow;
            c=cCol;
            
            while (++c<checkG[r].length) {
                    if (getCellState(r, c)==current) {
                        sameInRow+=1;
                    } else {
                        c=checkG[r].length;
                    }
                }
        }
        
        //Check bot-right
        if (sameInRow<targetSameInRow) {
            sameInRow=1;
            r=cRow;
            c=cCol;
            
            while (++r<checkG.length && ++c<checkG[r].length) {
                    if (getCellState(r, c)==current) {
                        sameInRow+=1;
                    } else {
                        r=checkG.length;
                    }
                }
        }
        
        //Check bot
        if (sameInRow<targetSameInRow) {
            sameInRow=1;
            r=cRow;
            c=cCol;
            
            while (++r<checkG.length) {
                    if (getCellState(r, c)==current) {
                        sameInRow+=1;
                    } else {
                        r=checkG.length;
                    }
                }
        }
        
        //Check bot-left
        if (sameInRow<targetSameInRow) {
            sameInRow=1;
            r=cRow;
            c=cCol;
            
            while (++r<checkG.length && --c>=0) {
                    if (getCellState(r, c)==current) {
                        sameInRow+=1;
                    } else {
                        r=checkG.length;
                    }
                }
        }
        
        //Check left
        if (sameInRow<targetSameInRow) {
            sameInRow=1;
            r=cRow;
            c=cCol;
            
            while (--c>=0) {
                    if (getCellState(r, c)==current) {
                        sameInRow+=1;
                    } else {
                        c=0;
                    }
                }
        }
        
        orderWin=(sameInRow>=targetSameInRow) ? true : false;
        
        return orderWin;
    }
    
    //Check for a Chaos win
    //row and col may allow for a check to see if the game is unwinnable for Order
    private boolean didChaosWin(CellState[][] checkG, int cRow, int cCol) {
        //Assumes Chaos won until an empty cell is found
        boolean chaosWin=true;
        for (CellState[] cR : checkG) {
            for (CellState cell: cR) {
                if(cell!=CellState.X && cell!=CellState.O)
                    chaosWin=false;
            }
        }
        return chaosWin;
    }
}
