package com.zj.learn.app.sudoku;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Mr. xi.yang<br/>
 * @version V1.0 <br/>
 * @description: 每个格的对象 <br/>
 * @date 2017-09-05 下午 3:22 <br/>
 */
public class Cell {
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
    public void removeVals(Set<Integer> nums){
        for (Integer integer : nums) {
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

    /**
     * 位置相同即为统一格点
     * @param cell
     * @return
     */
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
