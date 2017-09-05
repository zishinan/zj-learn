package com.zj.learn.app.sudoku;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr. xi.yang<br/>
 * @version V1.0 <br/>
 * @description: 获取manager <br/>
 * @date 2017-09-05 下午 5:21 <br/>
 */
public class ManagerFactory {
    public static List<SudoKuManager> getManagers(){
        List<SudoKuManager> managers = new ArrayList<>();
        managers.add(new SimpleManager());
        managers.add(new EasyProcessManager());
        return managers;
    }
}
