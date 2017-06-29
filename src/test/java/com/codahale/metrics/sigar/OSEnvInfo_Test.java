/*
 * Copyright (c) 2010-2020, vindell (https://github.com/vindell).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.codahale.metrics.sigar;

import java.util.List;
import java.util.Map;

import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarFactory;

import com.codahale.metrics.sigar.utils.OSEnvInfo;

import junit.framework.TestCase;

public class OSEnvInfo_Test extends TestCase {

	public void testInfo() throws Exception {
		System.out.println("======================Info=============================");
		Sigar sigar = (Sigar) SigarFactory.newSigar();
		Map<String, Object> infoMap = OSEnvInfo.info(sigar);
		for (String key : infoMap.keySet()) {
			System.out.println(key + " : " + infoMap.get(key));
		}
		
	}
	
	public void testMemory() throws Exception {
		System.out.println("======================memory=============================");
		Sigar sigar = (Sigar) SigarFactory.newSigar();
		Map<String, Object> infoMap = OSEnvInfo.memory(sigar);
		for (String key : infoMap.keySet()) {
			System.out.println(key + " : " + infoMap.get(key));
		}
		
	}
	
	public void testUsage() throws Exception {
		System.out.println("======================usage=============================");
		Sigar sigar = (Sigar) SigarFactory.newSigar();
		Map<String, Double> infoMap = OSEnvInfo.usage(sigar);
		for (String key : infoMap.keySet()) {
			System.out.println(key + " : " + CpuPerc.format(infoMap.get(key)));
		}
		
	}
	
	public void testCpuInfos() throws Exception {
		System.out.println("======================CpuInfos=============================");
		Sigar sigar = (Sigar) SigarFactory.newSigar();
		List<Map<String, Object>> infoMap = OSEnvInfo.cpuInfos(sigar);
		for (Map<String, Object> map : infoMap) {
			for (String key : map.keySet()) {
				System.out.println(key + " : " + map.get(key));
			}
			System.out.println("===================================================");
		}
	}
	
	public void testDiskInfos() throws Exception {
		System.out.println("======================DiskInfos=============================");
		Sigar sigar = (Sigar) SigarFactory.newSigar();
		List<Map<String, Object>> infoMap = OSEnvInfo.diskInfos(sigar);
		for (Map<String, Object> map : infoMap) {
			for (String key : map.keySet()) {
				System.out.println(key + " : " + map.get(key));
			}
			System.out.println("===================================================");
		}
	}
	
	
}
