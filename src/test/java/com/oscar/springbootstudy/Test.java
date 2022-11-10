package com.oscar.springbootstudy;

import org.apache.shiro.crypto.hash.Md5Hash;

public class Test {

    public static void main(String[] args) {
        Md5Hash md5Hash = new Md5Hash("1qazxsw2", "salt",3);
        System.out.println(md5Hash);
        System.out.println(md5Hash.toHex());
    }
}
