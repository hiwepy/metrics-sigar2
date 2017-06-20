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

import java.util.Map;

import com.codahale.metrics.sigar.utils.CapacityUtils.Unit;
import com.codahale.metrics.sigar.utils.JVMInfo;

import junit.framework.TestCase;

public class JVMInfo_Test extends TestCase {

	public void testInfo() throws Exception {
		System.out.println("======================info=============================");
		Map<String, Object> infoMap = JVMInfo.info();
		for (String key : infoMap.keySet()) {
			System.out.println(key + " : " + infoMap.get(key));
		}
		
	}

	public void testMemory_MB() throws Exception {
		System.out.println("======================runtime=============================");
		System.out.println(JVMInfo.runtime(Unit.MB));
	}

	/*public void testMemory_KB() throws Exception {
		System.out.println(JVMInfo.runtime(Unit.KB));

	}*/

}
