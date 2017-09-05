package com.zj.learn.app.sudoku;

import java.util.*;

/**
 * @author Mr. xi.yang<br/>
 * @version V1.0 <br/>
 * @description: 数独全盘 <br/>
 * @date 2017-09-05 下午 3:34 <br/>
 */
public class Sudoku {
    private static final int OK_NUM = 81;
    private int totalNum;
    private Cell[][] sudoku;
    private Map<Integer,List<Cell>> rows;
    private Map<Integer,List<Cell>> cols;
    private Map<Integer,List<Cell>> blocks;

    public Sudoku(String[] sudokuString){
        initSudoku(initFromString(sudokuString));
    }

    public Sudoku(Cell[][] sudoku) {
        initSudoku(sudoku);
    }

    /**
     * 格式化打印
     */
    public void printSudoku() {
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

    public boolean isAllRight() {
        if(!isOk()){
            return false;
        }
        for (int i = 0; i < 9; i++) {
            if(!isRight(rows.get(i))){
                System.out.println(i+"行验证错误！！！！");
                return false;
            }
            if(!isRight(cols.get(i))){
                System.out.println(i+"列验证错误！！！！");
                return false;
            }
            if(!isRight(blocks.get(i))){
                System.out.println(i+"块验证错误！！！！");
                return false;
            }
        }
        return true;
    }

    private boolean isRight(List<Cell> cells) {
        Set<Integer> set = new HashSet<>();
        for (Cell cell : cells) {
            if(set.contains(cell.getOkVal())){
                return false;
            }
            set.add(cell.getOkVal());
        }
        return true;
    }

    private void initSudoku(Cell[][] sudoku) {
        this.sudoku = sudoku;
        totalNum = 0;
        rows = new HashMap<>();
        cols = new HashMap<>();
        blocks = new HashMap<>();
        for (int i = 0; i < sudoku.length; i++) {
            List<Cell> row = new ArrayList<>();
            List<Cell> col = new ArrayList<>();
            for (int j = 0; j < sudoku[i].length; j++) {
                totalNum = totalNum + sudoku[i][j].getValue().size();
                row.add(sudoku[i][j]);
                col.add(sudoku[j][i]);
            }
            rows.put(i,row);
            cols.put(i,col);
        }
        int index = 0;
        for (int i = 0; i < 9; i = i+3) {
            for (int j = 0; j < 9; j = j+3) {
                List<Cell> cells = new ArrayList<>();
                for (int k = 0; k < 3; k++) {
                    for (int l = 0; l < 3; l++) {
                        Cell cell = sudoku[i+k][j+l];
                        cells.add(cell);
                    }
                }
                blocks.put(index,cells);
                index++;
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
                cells[i][j].setOkVal(Integer.parseInt(""+c));
            }
        }
        return cells;
    }

    public boolean isOk(){
        return OK_NUM == getTotalNum();
    }

    public int getTotalNum() {
        totalNum = 0;
        for (int i = 0; i < sudoku.length; i++) {
            for (int j = 0; j < sudoku[i].length; j++) {
                totalNum = totalNum + sudoku[i][j].getValue().size();
            }
        }
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public Cell[][] getSudoku() {
        return sudoku;
    }

    public void setSudoku(Cell[][] sudoku) {
        this.sudoku = sudoku;
    }

    public Map<Integer, List<Cell>> getRows() {
        return rows;
    }

    public void setRows(Map<Integer, List<Cell>> rows) {
        this.rows = rows;
    }

    public Map<Integer, List<Cell>> getCols() {
        return cols;
    }

    public void setCols(Map<Integer, List<Cell>> cols) {
        this.cols = cols;
    }

    public Map<Integer, List<Cell>> getBlocks() {
        return blocks;
    }

    public void setBlocks(Map<Integer, List<Cell>> blocks) {
        this.blocks = blocks;
    }

    public List<Cell> getRow(Cell cell) {
        return rows.get(cell.getX());
    }

    public List<Cell> getCol(Cell cell) {
        return cols.get(cell.getY());
    }

    public List<Cell> getBlock(Cell cell) {
        int x = cell.getX();
        int y = cell.getY();
        int index = getBlockIndex(x, y);
        return blocks.get(index);
    }

    private int getBlockIndex(int x, int y) {
        int index;
        if(x < 3){
            if(y < 3){
                index = 0;
            }else if (y < 6){
                index = 1;
            }else {
                index = 2;
            }
        }else if(x < 6){
            if(y < 3){
                index = 3;
            }else if (y < 6){
                index = 4;
            }else {
                index = 5;
            }
        }else {
            if(y < 3){
                index = 6;
            }else if (y < 6){
                index = 7;
            }else {
                index = 8;
            }
        }
        return index;
    }
}
