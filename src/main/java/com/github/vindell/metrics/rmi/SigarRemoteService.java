package com.github.vindell.metrics.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.github.vindell.metrics.SigarRMIInfo;	

public interface SigarRemoteService extends Remote{
	
    public SigarRMIInfo getRuntime() throws RemoteException;//内存 M 网络 kb/s
    
}
