package com.codahale.metrics.sigar.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.codahale.metrics.sigar.utils.CapacityUtils.Unit;

/**
 * VM 概要,实现 jconsole看到的VM概要信息的获取
 */
public class JVMInfo {

	public static Map<String, Object> info(){
		
		Map<String, Object> infoMap = new HashMap<String, Object>();
		Properties props = System.getProperties(); 
		//JVM属性 
		for (JVMProperty vm : JVMProperty.values()) {
			infoMap.put(vm.getKey(), props.getProperty(vm.getKey()));
		}
		return infoMap;
		
	}
	
	/**
	 * 
	 * @description	： TODO
	 * @author 		： <a href="https://github.com/vindell">vindell</a>
	 * @date 		：2017年6月19日 下午4:29:55
	 * @param unit
	 * @return
	 * @see http://blog.csdn.net/wgw335363240/article/details/8878644
	 */
	public static Map<String, Object> runtime(Unit unit){
		
		Runtime r = Runtime.getRuntime();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		//Java 虚拟机中的内存总量,以字节为单位  
		dataMap.put("jvm.memory.max", CapacityUtils.getCapacityString(r.maxMemory(), unit));// JVM 最大内存
		dataMap.put("jvm.memory.total", CapacityUtils.getCapacityString(r.totalMemory(), unit));// JVM 总内存
		dataMap.put("jvm.memory.used", CapacityUtils.getCapacityString(r.totalMemory() - r.freeMemory(), unit));// JVM已用内存
		dataMap.put("jvm.memory.free", CapacityUtils.getCapacityString(r.freeMemory(), unit));// JVM剩余内存
		dataMap.put("jvm.memory.usage", CapacityUtils.div(r.totalMemory() - r.freeMemory(), r.totalMemory(), 2) );// JVM使用率
		
		return dataMap;
		
	}
	
}
