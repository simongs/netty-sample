package com.dasoulte.simons.core;

import com.dasoulte.simons.core.config.ApplicationConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by NHNEnt on 2016-11-08.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
public class DaemonPropertiesTest {

    @Autowired
    private ApplicationConfiguration daemonProperties;

    @Test
    public void test() {
        assertThat(daemonProperties.getPaycoApiHost(), is("https://dev-api-bill.payco.com"));
    }

}