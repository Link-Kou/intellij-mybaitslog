package com.linkkou.mybatis.log;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MyBatisLogRmi extends Remote {

    public String log(String name) throws RemoteException;

}