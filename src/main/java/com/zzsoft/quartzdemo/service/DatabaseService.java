package com.zzsoft.quartzdemo.service;

import org.springframework.stereotype.Service;

@Service
public class DatabaseService {


    public void backup(String id){
        System.out.println("数据库备份... id:"+id);
    }

}
