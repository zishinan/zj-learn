package com.zj.learn.app.sudoku;

import java.util.List;

/**
 * @author Mr. xi.yang<br/>
 * @version V1.0 <br/>
 * @description: 入口 <br/>
 * @date 2017-09-05 下午 4:12 <br/>
 */
public class Client {
    public static void main(String[] args) {
        String[] sudokuString = new String[]{
                "x9x x2x 7xx",
                "x8x 1x4 xx6",
                "xxx 5xx xxx",
                "xx3 xx6 x4x",
                "xxx xx9 2xx",
                "4xx xxx xxx",
                "x2x xxx xxx",
                "576 xxx 4x1",
                "xxx x8x xx3"
        };
        Sudoku sudoku = new Sudoku(sudokuString);
        List<SudoKuManager> managers = ManagerFactory.getManagers();
        while (true){
            sudoku.printSudoku();
            int totalNum = sudoku.getTotalNum();
            for (SudoKuManager manager : managers) {
                manager.calculate(sudoku);
                if(sudoku.isOk()){
                    break;
                }
                if(totalNum != sudoku.getTotalNum()){
                    break;
                }
            }
            if(totalNum == sudoku.getTotalNum()){
                break;
            }
        }
        sudoku.printSudoku();
    }
}
