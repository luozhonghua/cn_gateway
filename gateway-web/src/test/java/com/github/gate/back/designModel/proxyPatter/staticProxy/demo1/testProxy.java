package com.github.gate.back.designModel.proxyPatter.staticProxy.demo1;

/**
 * Created by luozhonghua on 2018/5/5.
 */
public class testProxy {

    public  static void main(String[]agrs){

        //代理对象
        IUserDao userdao=new UserDaoProxy() ;
        //执行代理
        userdao.save();

    }

}
