package com.codahale.metrics.sigar.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;	

public interface SigarRemoteService extends Remote{
	
    public SigarRMIInfo getRuntime() throws RemoteException;//内存 M 网络 kb/s
    
}
