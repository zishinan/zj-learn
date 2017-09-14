package com.zj.learn.app.sudoku;

import java.util.List;

/**
 * @author Mr. xi.yang<br/>
 * @version V1.0 <br/>
 * @description: 抽象处理类 <br/>
 * @date 2017-09-05 下午 4:31 <br/>
 */
public abstract class SudoKuManager {
    public void calculate(Sudoku sudoku){
        for (int i = 0; i < 9; i++) {
            if(!allOk(sudoku.getRows().get(i))){
                doCalculate(sudoku.getRows().get(i));
            }
            if(!allOk(sudoku.getCols().get(i))){
                doCalculate(sudoku.getCols().get(i));
            }
            if(!allOk(sudoku.getBlocks().get(i))){
                doCalculate(sudoku.getBlocks().get(i));
            }
        }
    }

    private boolean allOk(List<Cell> cells) {
        for (Cell cell : cells) {
            if(!cell.isOk()){
                return false;
            }
        }
        return true;
    }

    protected abstract void doCalculate(List<Cell> cells);
}
