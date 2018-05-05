package com.github.gate.back.designModel.proxyPatter.staticProxy.demo2;

/**
 * Created by luozhonghua on 2018/5/3.
 */
public class ProxyPatternDemo {
    public static void main(String[] args) {


        // 代理对象，需要维护一个目标对象
        Image image = new ProxyImage("test_10mb.jpg");

        //图像将从磁盘加载
        image.display();
        System.out.println("");
        //图像将无法从磁盘加载
        image.display();
    }
}
