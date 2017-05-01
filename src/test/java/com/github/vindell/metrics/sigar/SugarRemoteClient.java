package com.github.vindell.metrics.sigar;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import com.github.vindell.metrics.SigarRMIInfo;
import com.github.vindell.metrics.rmi.SigarRemoteService;

public class SugarRemoteClient {

	public static void main(String args[]){
        try {
        	SigarRemoteService rhello = (SigarRemoteService)Naming.lookup("rmi://"+args[0]+":8828/systemstatus");
            SigarRMIInfo ssp = rhello.getRuntime();
            System.out.println(ssp.getMemory_uesd());
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NotBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
	
}
