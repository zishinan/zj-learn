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
                "7xx xxx 3xx",
                "x9x xxx 1xx",
                "x64 xxx xxx",
                "xxx xx8 x73",
                "xxx xx9 xxx",
                "51x x73 x2x",
                "xx9 4xx xx5",
                "xxx 36x 7xx",
                "xx3 x52 xxx"
        };
        Sudoku sudoku = new Sudoku(sudokuString);
        sudoku.printSudoku();
        List<SudoKuManager> managers = ManagerFactory.getManagers();
        int totalNum;
        do {
            totalNum = sudoku.getTotalNum();
            for (SudoKuManager manager : managers) {
                manager.calculate(sudoku);
                if(sudoku.isOk()){
                    break;
                }
                if(totalNum != sudoku.getTotalNum()){
                    break;
                }
            }
        }while (totalNum == sudoku.getTotalNum());
        sudoku.printSudoku();
    }
}
