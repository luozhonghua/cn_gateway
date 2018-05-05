package com.github.gate.back.designModel.proxyPatter.dynamicProxy;

/**
 * Created by luozhonghua on 2018/5/5.
 * JDK动态代理,实现接口才行
 */
public class testDynamic {

    public static void main(String[] args) {
        IUserDao target=new UserDao();

        System.out.println("target class:"+target.getClass());

        IUserDao Proxy = (IUserDao)new ProxyFactory(target).getProxyInstance();
        System.out.println("Proxy class:"+Proxy.getClass());

        Proxy.save();


    }


}
