package com.zj.learn.app.sudoku;

import java.util.List;

/**
 * @author Mr. xi.yang<br/>
 * @version V1.0 <br/>
 * @description: 排除计算 <br/>
 * @date 2017-09-05 下午 4:40 <br/>
 */
public class SimpleManager extends SudoKuManager {
    @Override
    protected void doCalculate(Sudoku sudoku) {
        Cell[][] cells = sudoku.getSudoku();
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                Cell cell = cells[i][j];
                if(cell.isOk()){
                    processByCell(cell);
                }
            }
        }
    }

    /**
     * 排除cell同行、同列、同块的可能值
     *
     * @param cell
     * @throws Exception
     */
    private void processByCell(Cell cell){
//        行
        List<Cell> rows = sudoku.getRow(cell);
        cleanCellsByCell(cell, rows);
//        列
        List<Cell> cols = sudoku.getCol(cell);
        cleanCellsByCell(cell, cols);
//        块
        List<Cell> block = sudoku.getBlock(cell);
        cleanCellsByCell(cell,block);
    }

    private void cleanCellsByCell(Cell cell, List<Cell> list) {
        for (Cell temp : list) {
            if(temp.isOk()){
                continue;
            }
            temp.removeCellVal(cell);
            if(temp.isOk()){
                processByCell(temp);
            }
        }
    }
}
