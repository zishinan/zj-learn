package com.zj.learn.app;

import java.util.*;

/**
 * @author Mr. xi.yang<br/>
 * @version V1.0 <br/>
 * @description: 数独计算 <br/>
 * @date 2017-09-04 上午 11:01 <br/>
 */
public class Sudoku {
    static Cell[][] sudoku;

    public static void main(String[] args) throws Exception {
        String[] sudokuString = new String[]{
                "7xx xxx 3xx",
                "x9x xxx 1xx",
                "x64 xxx xxx",
                "xxx xx8 x73",
                "xxx xx9 xxx",
                "51x x7x x2x",
                "xx9 4xx xx5",
                "xxx 36x 7xx",
                "xx3 x52 xxx"
        };

        sudoku = initFromString(sudokuString);
        printSudoku();

        processAll();

        System.out.println("==========================");
        printSudoku();
    }

    private static void processAll() throws Exception {
        if(isAllRight()){
            return;
        }
        firstClean();
        sencondClean();
    }

    /**
     * 第一遍数据清洗
     * 将确定值的同行、列、块可能数据规整
     */
    private static void firstClean() throws Exception {
        for (int i = 0; i < sudoku.length; i++) {
            for (int j = 0; j < sudoku[i].length; j++) {
                Cell cell = sudoku[i][j];
                if(cell.isOk()){
                    processByCell(cell);
                }
            }
        }
    }

    /**
     * 第二遍数据清洗
     * TODO 获取所有组合
     *
     */
    private static void sencondClean() throws Exception {
//        行和列
        for (int i = 0; i < 9; i++) {
            List<Cell> cells1 = new ArrayList<>();
            List<Cell> cells2 = new ArrayList<>();
            for (int j = 0; j < 9; j++) {
                Cell temp1 = sudoku[i][j];
                Cell temp2 = sudoku[j][i];
                if(!temp1.isOk()){
                    cells1.add(temp1);
                }
                if (!temp2.isOk()) {
                    cells2.add(temp2);
                }
            }
            doProceesCells(cells1);
            doProceesCells(cells2);
        }
//        块
        for (int i = 0; i < 3; i = i+3) {
            for (int j = 0; j < 3; j = j+3) {
                List<Cell> cells = new ArrayList<>();
                for (int k = 0; k < 3; k++) {
                    for (int l = 0; l < 3; l++) {
                        Cell cell = sudoku[i+k][j+l];
                        if(!cell.isOk()){
                            cells.add(cell);
                        }
                    }
                }
                doProceesCells(cells);
            }
        }
    }

    /**
     * 思路是将一个数组未确定的可能值组合，当组合的值的个数小于等于组合的位置个数时，该组的其他位置可以排除组合值
     * 比如:有三个位置都是【5,7,9】，那么其他位置就可以排除【5,7,9】
     * @param cells 只包含未确定的组合
     * @throws Exception
     */
    private static void doProceesCells(List<Cell> cells) throws Exception {
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
                        processAll();
                    }
                }
            }
        }
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
                    if(temp.isOk()){
                        processAll();
                    }
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


    /**
     * 排除cell同行、同列、同块的可能值
     * @param cell
     * @throws Exception
     */
    private static void processByCell(Cell cell){
        if(!cell.isOk()){
            return;
        }
//        行
        List<Cell> rows = getRows(cell);
        cleanCellsByCell(cell, rows);
//        列
        List<Cell> cols = getCols(cell);
        cleanCellsByCell(cell, cols);
//        块
        List<Cell> block = getBlock(cell);
        cleanCellsByCell(cell,block);
    }

    private static void cleanCellsByCell(Cell cell, List<Cell> list) {
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

    private static List<Cell> getRows(Cell cell) {
        List<Cell> cells = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            cells.add(sudoku[cell.getX()][i]);
        }
        return cells;
    }

    private static List<Cell> getCols(Cell cell) {
        List<Cell> cells = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            cells.add(sudoku[i][cell.getY()]);
        }
        return cells;
    }

    private static List<Cell> getBlock(Cell cell) {
        List<Cell> cells = new ArrayList<>();
        int x = cell.getX();
        int y = cell.getY();
        int minx = getMinInt(x);
        int maxx = getMaxInt(x);
        int miny = getMinInt(y);
        int maxy = getMaxInt(y);
        for (int i = minx; i < maxx; i++) {
            for (int j = miny; j < maxy; j++) {
                cells.add(sudoku[i][j]);
            }
        }
        return cells;
    }

    private static int getMaxInt(int i) {
        if(i < 3){
            return 3;
        }
        if(i < 6){
            return 6;
        }
        return 9;
    }

    private static int getMinInt(int i) {
        if(i < 3){
            return 0;
        }
        if(i < 6){
            return 3;
        }
        return 6;
    }

    /**
     * 格式化打印
     */
    private static void printSudoku() {
        if(!isAllRight()){
            System.out.println("===============数据校验错误==================");
        }else {
            System.out.println("===============完成数独运算==================");
        }
        for (int i = 0; i < sudoku.length; i++) {
            for (int j = 0; j < sudoku[i].length; j++) {
                System.out.print(sudoku[i][j].toString());
                if((j+1)%3 == 0){
                    System.out.print("\t||\t");
                }
            }
            System.out.println();
            if((i+1)%3 == 0){
                System.out.println("================================================");
            }
        }
    }


    /**
     * 初始化数组
     * @param sudokuString
     * @return
     */
    private static Cell[][] initFromString(String[] sudokuString) {
        Cell[][] cells = new Cell[9][9];
        for (int i = 0; i < sudokuString.length; i++) {
            char[] row = sudokuString[i].replace(" ","").toCharArray();
            for (int j = 0; j < row.length; j++) {
                cells[i][j] = new Cell(i,j);
                char c = row[j];
                if('x' == c){
                    continue;
                }
                Set<Integer> value = new HashSet<>();
                value.add(Integer.parseInt(""+c));
                cells[i][j].setValue(value);
            }
        }
        return cells;
    }

    private static boolean isAllRight() {
        if(!isAllOk()){
            return false;
        }
        //        行和列
        for (int i = 0; i < 9; i++) {
            List<Cell> cells1 = new ArrayList<>();
            List<Cell> cells2 = new ArrayList<>();
            for (int j = 0; j < 9; j++) {
                Cell temp1 = sudoku[i][j];
                Cell temp2 = sudoku[j][i];
                cells1.add(temp1);
                cells2.add(temp2);
            }
            if(!isRight(cells1)){
                System.out.println(i+"行验证错误！！！！");
                return false;
            }
            if(!isRight(cells2)){
                System.out.println(i+"列验证错误！！！！");
                return false;
            }
        }
//        块
        for (int i = 0; i < 3; i = i+3) {
            for (int j = 0; j < 3; j = j+3) {
                List<Cell> cells = new ArrayList<>();
                for (int k = 0; k < 3; k++) {
                    for (int l = 0; l < 3; l++) {
                        Cell cell = sudoku[i+k][j+l];
                        cells.add(cell);
                    }
                }
                if(!isRight(cells)){
                    System.out.println("["+i+","+j+"]块验证错误！！！！");
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isRight(List<Cell> cells) {
        Set<Integer> set = new HashSet<>();
        for (Cell cell : cells) {
            if(set.contains(cell.getOkVal())){
                return false;
            }
            set.add(cell.getOkVal());
        }
        return true;
    }

    private static boolean isAllOk() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(!sudoku[i][j].isOk()){
                    return false;
                }
            }
        }
        return true;
    }
}

class Cell{
    private Set<Integer> value;
    private int x;
    private int y;

    public Cell(int x, int y) {
        Set<Integer> val = new HashSet<>();
        for (int i = 0; i < 9; i++) {
            val.add(i+1);
        }
        this.value = val;
        this.x = x;
        this.y = y;
    }

    public Set<Integer> getValue() {
        return value;
    }

    public void setValue(Set<Integer> value) {
        this.value = value;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isOk(){
        return this.value.size() == 1;
    }

    public void removeCellVal(Cell cell){
        for (Integer integer : cell.getValue()) {
            this.getValue().remove(integer);
        }
    }

    public int getOkVal(){
        for (Integer integer : this.value) {
            return integer;
        }
        return 0;
    }

    public void setOkVal(int val){
        Set<Integer> set = new HashSet<>();
        set.add(val);
        this.value = set;
    }

    public boolean equals(Cell cell) {
        if(cell.getX() == getX() && cell.getY() == getY()){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return ""+value;
    }
}
