package com.github.vindell.metrics.sigar;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.github.vindell.metrics.sigar.CpuMetrics.CpuTime;

public class CpuMetricsTest extends CheckSigarLoadsOk {
    private CpuMetrics cm;

    @Before
    public void setUp() {
        cm = SigarMetrics.getInstance().cpu();
    }

    @Test
    public void cpuCoreCountIsGreaterThanZero() throws Exception {
        assertThat(cm.totalCoreCount(), is(greaterThan(0)));
    }
    
    @Test
    public void coreCountIsAtLeastPhysicalCpuCount() throws Exception {
        assertThat(cm.totalCoreCount(), is(greaterThanOrEqualTo(cm.physicalCpuCount())));
    }

    @Test
    public void lengthOfCpuListMatchesCoreCount() throws Exception {
        assertThat(cm.cpus().size(), is(equalTo(cm.totalCoreCount())));
    }

    @Test
    public void cpuTimesAddUpToApproximatelyOne() throws Exception {
        CpuTime t = cm.cpus().get(0);
        assertThat(t.user() + t.sys() + t.nice() + t.waiting() + t.idle() + t.irq(), 
                is(closeTo(1.0, 0.05)));
    }
}
