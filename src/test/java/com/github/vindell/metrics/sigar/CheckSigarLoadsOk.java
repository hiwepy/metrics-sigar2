package com.github.vindell.metrics.sigar;

import static org.junit.Assume.assumeNoException;

import org.hyperic.sigar.Sigar;
import org.junit.BeforeClass;

import junit.framework.TestCase;

/**
 * Created: 12/05/07 17:28
 *
 * @author chris
 */
public abstract class CheckSigarLoadsOk extends TestCase {

    @BeforeClass
    public static final void canLoadSigarCheck() {
        try {
            Sigar.load();
        } catch (Throwable e) {
            assumeNoException(e);
        }
    }

}
