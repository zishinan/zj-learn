package com.zj.learn.app.sudoku;

/**
 * @author Mr. xi.yang<br/>
 * @version V1.0 <br/>
 * @description: 抽象处理类 <br/>
 * @date 2017-09-05 下午 4:31 <br/>
 */
public abstract class SudoKuManager {
    protected Sudoku sudoku;
    public void calculate(Sudoku sudoku){
        this.sudoku = sudoku;
        int totalNum;
        do {
            totalNum = sudoku.getTotalNum();
            doCalculate(sudoku);
        }while (totalNum == sudoku.getTotalNum());
    }
    protected abstract void doCalculate(Sudoku sudoku);
}
