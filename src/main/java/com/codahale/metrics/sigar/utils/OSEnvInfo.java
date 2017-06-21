package com.codahale.metrics.sigar.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.OperatingSystem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.Swap;

import com.codahale.metrics.sigar.utils.CapacityUtils.Unit;

/**
 * 操作系统环境和硬件相关信息
 */
public class OSEnvInfo {
	
	public static final String JVM_MEMORY = "jvm.memory";
	
	
	public static Map<String, Object> info(Sigar sigar){
		
		System.out.println("==========================OperatingSystem=========================");
		
		Map<String, Object> infoMap = new HashMap<String, Object>();
		
        //==========================OperatingSystem=========================

        InetAddress addr = null;
		String ip = "127.0.0.1";
		String hostName = "localhost";
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
		}
		if (null != addr) {
			try {
				ip = addr.getHostAddress();
			} catch (Exception e) {
			}
			try {
				hostName = addr.getHostName();
			} catch (Exception e) {
			}
		}
		infoMap.put("host.ip", ip);// 本地ip地址
		infoMap.put("host.name", hostName);// 本地主机名
        
		  
        Map<String, String> map = System.getenv();
        String userName = map.get("USERNAME");// 获取用户名
        String computerName = map.get("COMPUTERNAME");// 获取计算机名
        String userDomain = map.get("USERDOMAIN");// 获取计算机域名
        
        
        System.out.println("用户名:    " + userName);
        System.out.println("计算机名:    " + computerName);
        System.out.println("计算机域名:    " + userDomain);
        System.out.println("本地IP地址:    " + ip);
        System.out.println("本地主机名:    " + addr.getHostName());
        

        OperatingSystem OS = OperatingSystem.getInstance();
        // 操作系统内核类型如： 386、486、586等x86
        System.out.println("操作系统:    " + OS.getArch());
        System.out.println("操作系统CpuEndian():    " + OS.getCpuEndian());//
        System.out.println("操作系统DataModel():    " + OS.getDataModel());//
        // 系统描述
        System.out.println("操作系统的描述:    " + OS.getDescription());
        // 操作系统类型
        // System.out.println("OS.getName():    " + OS.getName());
        // System.out.println("OS.getPatchLevel():    " + OS.getPatchLevel());//
        // 操作系统的卖主
        System.out.println("操作系统的卖主:    " + OS.getVendor());
        // 卖主名称
        System.out.println("操作系统的卖主名:    " + OS.getVendorCodeName());
        // 操作系统名称
        System.out.println("操作系统名称:    " + OS.getVendorName());
        // 操作系统卖主类型
        System.out.println("操作系统卖主类型:    " + OS.getVendorVersion());
        // 操作系统的版本号
        System.out.println("操作系统的版本号:    " + OS.getVersion());
        
        
		//获取操作系统相关信息  
		Properties props = System.getProperties(); 
		for (OSProperty vm : OSProperty.values()) {
			infoMap.put(vm.getKey(), props.getProperty(vm.getKey()));
		}
		
        return infoMap;
        
	}
	
public static Map<String, Object> memory(Sigar sigar) {
		
		Map<String, Object> monitorMap = new HashMap<String, Object>();
		try {
			
			Runtime r = Runtime.getRuntime();
			
			monitorMap.put(JVM_MEMORY + ".total", r.totalMemory() );// java总内存
			monitorMap.put(JVM_MEMORY + ".used", r.totalMemory());// JVM使用内存
			monitorMap.put(JVM_MEMORY + ".free", r.freeMemory());// JVM剩余内存
			monitorMap.put(JVM_MEMORY + ".usage", CapacityUtils.div(r.totalMemory() - r.freeMemory(), r.totalMemory(), 2));// JVM使用率

			Mem mem = sigar.getMem();
			// 内存总量
			monitorMap.put("os.ram.total",mem.getTotal());// 内存总量
			monitorMap.put("os.ram.used", mem.getUsed());// 当前内存使用量
			monitorMap.put("os.ram.free", mem.getFree());// 当前内存剩余量
			monitorMap.put("os.ram.usage", CapacityUtils.div(mem.getUsed(), mem.getTotal(), 2));// 内存使用率

			Swap swap = sigar.getSwap();
			// 交换区总量
			monitorMap.put("os.swap.total", swap.getTotal());
			// 当前交换区使用量
			monitorMap.put("os.swap.used", swap.getUsed());
			// 当前交换区剩余量
			monitorMap.put("os.swap.free", swap.getFree());
			monitorMap.put("os.swap.usage", CapacityUtils.div(swap.getUsed(), swap.getTotal(), 2));

		} catch (Exception e) {
		}
		return monitorMap;
	}
	
	public static Map<String, Double> usage(Sigar sigar) {
		
		Map<String, Double> monitorMap = new HashMap<String, Double>();
		
		try {
			Runtime r = Runtime.getRuntime();
			monitorMap.put(JVM_MEMORY + ".usage", CapacityUtils.div(r.totalMemory()-r.freeMemory(), r.totalMemory(), 2));// JVM使用率

			Mem mem = sigar.getMem();
			// 内存总量
			monitorMap.put("os.ram.usage", CapacityUtils.div(mem.getUsed(), mem.getTotal(), 2));// 内存使用率
			
 			List<Map<String, Object>> cpu = cpuInfos(sigar);
			double b = 0.0;
			for (Map<String, Object> m : cpu) {
				b += Double.valueOf(m.get("os.cpu.total")+"");
			}
			// cpu使用率
			monitorMap.put("os.cpu.usage", CapacityUtils.div(b, cpu.size(), 2));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return monitorMap;
	}

	/**
	 * 
	 * 	Cpu(s)表示的是cpu信息。各个值的意思是：
	 *	us: user cpu time (or) % CPU time spent in user space
	 *	sy: system cpu time (or) % CPU time spent in kernel space
	 *	ni: user nice cpu time (or) % CPU time spent on low priority processes
	 *	id: idle cpu time (or) % CPU time spent idle
	 *	wa: io wait cpu time (or) % CPU time spent in wait (on disk)
	 *	hi: hardware irq (or) % CPU time spent servicing/handling hardware interrupts
	 *	si: software irq (or) % CPU time spent servicing/handling software interrupts
	 *	st: steal time - - % CPU time in involuntary wait by virtual cpu while hypervisor is servicing another processor (or) % CPU time stolen from a virtual machine
	 *	 
	 *	翻译一下：
	 *	us：用户态使用的cpu时间比
	 *	sy：系统态使用的cpu时间比
	 *	ni：用做nice加权的进程分配的用户态cpu时间比
	 *	id：空闲的cpu时间比
	 *	wa：cpu等待磁盘写入完成时间
	 *	hi：硬中断消耗时间
	 *	si：软中断消耗时间
	 *	st：虚拟机偷取时间
	 * 
	 * @description	： TODO
	 * @author 		： <a href="https://github.com/vindell">vindell</a>
	 * @date 		：2017年6月20日 下午5:28:55
	 * @param sigar
	 * @return
	 */
	public static List<Map<String, Object>> cpuInfos(Sigar sigar) {
		List<Map<String, Object>> monitorMaps = new ArrayList<Map<String, Object>>();
		try {
			CpuPerc cpuList[] = sigar.getCpuPercList();
			for (CpuPerc cpuPerc : cpuList) {
				Map<String, Object> monitorMap = new HashMap<String, Object>();
				monitorMap.put("os.cpu.irq", cpuPerc.getIrq());// 硬中断消耗时间
				monitorMap.put("os.cpu.softIrq", cpuPerc.getSoftIrq());// 软中断消耗时间
				monitorMap.put("os.cpu.stolen", cpuPerc.getStolen());// 虚拟机偷取时间
				monitorMap.put("os.cpu.nice", cpuPerc.getNice()); //用做nice加权的进程分配的用户态cpu时间比
				monitorMap.put("os.cpu.user",  cpuPerc.getUser());// 用户使用率
				monitorMap.put("os.cpu.system",  cpuPerc.getSys());// 系统使用率
				monitorMap.put("os.cpu.wait",  cpuPerc.getWait());// 当前等待率
				monitorMap.put("os.cpu.idle",  cpuPerc.getIdle());// 当前空闲率
				monitorMap.put("os.cpu.total", cpuPerc.getCombined());// 总的使用率
				monitorMaps.add(monitorMap);
			}
		} catch (Exception e) {
		}
		return monitorMaps;
	}
 

	public static List<Map<String, Object>> diskInfos(Sigar sigar) throws Exception {
		List<Map<String, Object>> monitorMaps = new ArrayList<Map<String, Object>>();
		FileSystem fslist[] = sigar.getFileSystemList();
		for (int i = 0; i < fslist.length; i++) {
			Map<String, Object> monitorMap = new HashMap<String, Object>();
			FileSystem fs = fslist[i];
			// 文件系统类型名，比如本地硬盘、光驱、网络文件系统等
			FileSystemUsage usage = sigar.getFileSystemUsage(fs.getDirName());
			switch (fs.getType()) {
				case 0: // TYPE_UNKNOWN ：未知
					break;
				case 1: // TYPE_NONE
					break;
				case 2: // TYPE_LOCAL_DISK : 本地硬盘
	
					monitorMap.put("os.disk.name", fs.getDevName());// 系统盘名称
					monitorMap.put("os.disk.type", fs.getSysTypeName());// 盘类型
					monitorMap.put("os.disk.options", fs.getOptions()); //读写权限
					monitorMap.put("os.disk.flags", fs.getFlags());	
					// 文件系统总大小
					monitorMap.put("os.disk.total", usage.getTotal());
					// 文件系统剩余大小
					monitorMap.put("os.disk.free", usage.getFree());
					// 文件系统已经使用量
					monitorMap.put("os.disk.used", usage.getUsed());
					// 文件系统资源的利用率
					monitorMap.put("os.disk.usage", usage.getUsePercent() * 100D);// 内存使用率
					monitorMaps.add(monitorMap);
					break;
				case 3:// TYPE_NETWORK ：网络
					break;
				case 4:// TYPE_RAM_DISK ：闪存
					break;
				case 5:// TYPE_CDROM ：光驱
					break;
				case 6:// TYPE_SWAP ：页面交换
					break;
			}
		}
		return monitorMaps;
	}
	
	
}
