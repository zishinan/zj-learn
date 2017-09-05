package com.zj.learn.app.sudoku;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mr. xi.yang<br/>
 * @version V1.0 <br/>
 * @description: 第二次过滤 <br/>
 * @date 2017-09-05 下午 5:39 <br/>
 */
public class EasyProcessManager extends SudoKuManager {
    @Override
    protected void doCalculate(Sudoku sudoku) {
        //        行和列
        for (int i = 0; i < 9; i++) {
            List<Cell> cells1 = sudoku.getRows().get(i);
            List<Cell> cells2 = sudoku.getCols().get(i);
            List<Cell> cells3 = sudoku.getBlocks().get(i);
            doProceesCells(cells1);
            doProceesCells(cells2);
            doProceesCells(cells3);
        }
    }

    /**
     * 思路是将一个数组未确定的可能值组合，当组合的值的个数小于等于组合的位置个数时，该组的其他位置可以排除组合值
     * 比如:有三个位置都是【5,7,9】，那么其他位置就可以排除【5,7,9】
     * @param cells 只包含未确定的组合
     * @throws Exception
     */
    private static void doProceesCells(List<Cell> cells){
//        唯一可能值确定
        Map<Integer,Integer> map = new HashMap<>();
        for (Cell cell : cells) {
            if(cell.isOk()){
                continue;
            }
            incMapValue(map,cell);
        }
        for (Integer key : map.keySet()) {
            if(map.get(key) == 1){
                for (Cell cell : cells) {
                    if(cell.getValue().contains(key)){
                        cell.setOkVal(key);
                    }
                }
            }
        }
//        相同的值排除
        Map<String,Integer> countMap = new HashMap<>();
        for (Cell cell : cells) {
            if(cell.isOk()){
                continue;
            }
            String key = cell.toString();
            if(countMap.containsKey(key)){
                countMap.put(key,countMap.get(key)+1);
            }else {
                countMap.put(key,1);
            }
        }
        for (String key : countMap.keySet()) {
            int count = countMap.get(key);
            Cell cell = getCountCell(cells,key);
            if(null == cell){
                continue;
            }
            if(cell.getValue().size() == count){
                for (Cell temp : cells) {
                    if(key.equals(temp.toString())){
                        continue;
                    }
                    temp.removeCellVal(cell);
                }
            }
        }
    }

    private static Cell getCountCell(List<Cell> cells, String key) {
        for (Cell cell : cells) {
            if(key.equals(cell.toString())){
                return cell;
            }
        }
        return null;
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
