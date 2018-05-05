package com.github.gate.back.designModel.proxyPatter.staticProxy.demo2;

/**
 * Created by luozhonghua on 2018/5/3.
 */
public class ProxyImage implements Image {
    private RealImage realImage;
    private String fileName;

    public ProxyImage(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void display() {
        if (realImage == null) {  //key--第一次
            realImage = new RealImage(fileName);
        }
        realImage.display();//第二次
    }
}
