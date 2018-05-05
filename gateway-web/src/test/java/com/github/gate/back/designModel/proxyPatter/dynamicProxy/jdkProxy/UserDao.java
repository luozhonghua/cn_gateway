package com.github.gate.back.designModel.proxyPatter.dynamicProxy;

/**
 * Created by luozhonghua on 2018/5/5.
 */
public class UserDao  implements  IUserDao{
    @Override
    public void save() {
        System.out.println("模拟： 保存用户！");
    }
    @Override
    public void find() {
        System.out.println("查询");
    }
}
