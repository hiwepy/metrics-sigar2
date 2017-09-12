package com.codahale.metrics.sigar.rmi.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.codahale.metrics.sigar.rmi.SigarRMIInfo;
import com.codahale.metrics.sigar.rmi.SigarRemoteService;
import com.codahale.metrics.sigar.rmi.SystemRuntime;

@SuppressWarnings("serial")
public class DefaultSigarRemoteServiceImpl extends UnicastRemoteObject implements SigarRemoteService {
	
	// 设置日期格式
	protected SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	protected String address_ip;
	
	public DefaultSigarRemoteServiceImpl(String address_ip) throws RemoteException {
		super();
		this.address_ip = address_ip;
	}

	@Override
	public SigarRMIInfo getRuntime() throws RemoteException {
		System.out.println("调用服务");
		SigarRMIInfo sp = new SigarRMIInfo();
		try {
			
			System.out.println("当前时间：" + df.format(new Date()));
			SystemRuntime sy = new SystemRuntime();
			sp.setMemory_total(sy.memory().getTotal() / 1024L / 1024L);
			System.out.println("总共内存(M)：" + sp.getMemory_total());
			sp.setMemory_uesd(sy.memory().getUsed() / 1024L / 1024L);
			System.out.println("使用内存(M)：" + sp.getMemory_uesd());
			sp.setCpu_combined(sy.cpu().getCombined());
			System.out.println("CPU(%)：" + sp.getCpu_combined());
			float[] net = sy.net(this.address_ip);
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