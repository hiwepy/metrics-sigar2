package com.github.vindell.metrics.sigar;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;

import org.junit.Test;

public class UlimitMetricsTest extends CheckSigarLoadsOk {

    @Test
    public void openFilesLimitIsGreaterThanZero() throws Exception {
        // skip this test on Windows platforms
        assumeThat(System.getProperty("os.name").toLowerCase(), not(containsString("windows")));

        assertThat(SigarMetrics.getInstance().ulimit().ulimit().openFiles(), is(greaterThan(0L)));
    }

}
