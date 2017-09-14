package com.zj.learn.app.sudoku.manager;

import com.zj.learn.app.sudoku.Cell;
import com.zj.learn.app.sudoku.SudoKuManager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Mr. xi.yang<br/>
 * @version V1.0 <br/>
 * @description: 简单过滤，将已确定的排除 <br/>
 * @date 2017-09-05 下午 4:40 <br/>
 */
public class SimpleManager extends SudoKuManager {

    @Override
    protected void doCalculate(List<Cell> cells) {
        Set<Integer> set = new HashSet<>();
        for (Cell cell : cells) {
            if(cell.getX() == 2 && cell.getY() == 0){
                System.out.println("==");
            }
            if(cell.isOk()){
                set.add(cell.getOkVal());
            }
        }
        for (Cell cell : cells) {
            if(cell.getX() == 2 && cell.getY() == 0){
                System.out.println("==");
            }
            if(cell.isOk()){
                continue;
            }
            cell.removeVals(set);
        }
    }
}
