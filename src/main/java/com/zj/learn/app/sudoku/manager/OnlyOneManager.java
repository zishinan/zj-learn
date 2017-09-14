package com.zj.learn.app.sudoku.manager;

import com.zj.learn.app.sudoku.Cell;
import com.zj.learn.app.sudoku.SudoKuManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mr. xi.yang<br/>
 * @version V1.0 <br/>
 * @description: 第二次过滤，将只有一个的确定下来 <br/>
 * @date 2017-09-05 下午 5:39 <br/>
 */
public class OnlyOneManager extends SudoKuManager {

    @Override
    protected void doCalculate(List<Cell> cells) {
        //        唯一可能值确定
        Map<Integer,Integer> map = new HashMap<>();
        for (Cell cell : cells) {
            if(cell.isOk()){
                continue;
            }
            incMapValue(map,cell);
        }
        for (Integer key : map.keySet()) {
            if(map.get(key) != 1){
                continue;
            }
            for (Cell cell : cells) {
                if(cell.isOk()){
                    continue;
                }
                if(cell.getValue().contains(key)){
                    cell.setOkVal(key);
                }
            }
        }
    }

    private static void incMapValue(Map<Integer, Integer> map, Cell temp) {
        for (Integer key : temp.getValue()) {
            if(map.containsKey(key)){
                map.put(key,map.get(key)+1);
            }else {
                map.put(key,1);
            }
        }
    }


}
