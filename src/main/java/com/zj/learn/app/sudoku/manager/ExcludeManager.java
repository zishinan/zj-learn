//package com.zj.learn.app.sudoku.manager;
//
//import com.zj.learn.app.sudoku.Cell;
//import com.zj.learn.app.sudoku.SudoKuManager;
//import com.zj.learn.app.sudoku.Sudoku;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
///**
// * @author Mr. xi.yang<br/>
// * @version V1.0 <br/>
// * @description: 第三次过滤，根据集合个数处理 <br/>
// * @date 2017-09-05 下午 5:39 <br/>
// */
//public class ExcludeManager extends SudoKuManager {
//    @Override
//    protected void doCalculate(Sudoku sudoku) {
//        //        行和列
//        for (int i = 0; i < 9; i++) {
//            List<Cell> cells1 = sudoku.getRows().get(i);
//            List<Cell> cells2 = sudoku.getCols().get(i);
//            List<Cell> cells3 = sudoku.getBlocks().get(i);
//            excludeProceesCells(cells1);
//            excludeProceesCells(cells2);
//            excludeProceesCells(cells3);
//        }
//    }
//
//    /**
//     * 思路是将一个数组未确定的可能值组合，当组合的值的个数小于等于组合的位置个数时，该组的其他位置可以排除组合值
//     * 比如:有三个位置都是【5,7,9】，那么其他位置就可以排除【5,7,9】
//     * @param cells 只包含未确定的组合
//     * @throws Exception
//     */
//    private static void excludeProceesCells(List<Cell> cells){
//        for (int n = 2; n < cells.size(); n++) {
//            List<List<Cell>> lists = getAllListCells(cells,n);
//            for (List<Cell> list : lists) {
//                Set<Integer> nums = new HashSet<>();
//                for (Cell cell : list) {
//                    for (Integer integer : cell.getValue()) {
//                        nums.add(integer.intValue());
//                    }
//                }
//                if(nums.size() <= n){
//                    for (Cell cell : list) {
//                        if(cell.getValue().size() > n){
//                            cell.removeVals(nums);
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    private static List<List<Cell>> getAllListCells(List<Cell> cells, int n) {
//        List<Cell> realAfter = new ArrayList<>();
//        for (Cell cell : cells) {
//            if(cell.getValue().size() > n || cell.isOk()){
//                continue;
//            }
//            realAfter.add(cell);
//        }
//        List<List<Cell>> result = new ArrayList<>();
//        for (int i = 0; i <= realAfter.size()-n; i++) {
//            List<Cell> temp = new ArrayList<>();
//            for (int j = i; j < i+n; j++) {
//                temp.add(realAfter.get(j));
//            }
//            result.add(temp);
//        }
//        return result;
//    }
//
//}
