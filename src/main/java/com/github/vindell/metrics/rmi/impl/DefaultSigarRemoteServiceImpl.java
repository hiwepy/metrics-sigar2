package com.github.vindell.metrics.rmi.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.github.vindell.metrics.SigarRMIInfo;
import com.github.vindell.metrics.rmi.SigarRemoteService;
import com.github.vindell.metrics.rmi.SystemRuntime;

public class DefaultSigarRemoteServiceImpl extends UnicastRemoteObject implements SigarRemoteService {
	
	public DefaultSigarRemoteServiceImpl() throws RemoteException {
		super();
	}

	@Override
	public SigarRMIInfo getRuntime() throws RemoteException {
		System.out.println("调用服务");
		SigarRMIInfo sp = new SigarRMIInfo();
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			System.out.println("当前时间：" + df.format(new Date()));
			SystemRuntime sy = new SystemRuntime();
			sp.setMemory_total(sy.memory().getTotal() / 1024L / 1024L);
			System.out.println("总共内存(M)：" + sp.getMemory_total());
			sp.setMemory_uesd(sy.memory().getUsed() / 1024L / 1024L);
			System.out.println("使用内存(M)：" + sp.getMemory_uesd());
			sp.setCpu_combined(sy.cpu().getCombined());
			System.out.println("CPU(%)：" + sp.getCpu_combined());
			float[] net = sy.net(main.ip);
			sp.setRx_speed(net[0]);
			System.out.println("下载(kb/s)：" + sp.getRx_speed());
			sp.setTx_speed(net[1]);
			System.out.println("上传(kb/s)：" + sp.getTx_speed());
		} catch (Exception e) {
			System.out.println(e);
		}
		return sp;
	}

}