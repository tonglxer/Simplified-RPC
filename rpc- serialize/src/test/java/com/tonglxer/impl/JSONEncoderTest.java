package com.tonglxer.impl;

import com.tonglxer.Encoder;
import com.tonglxer.TestBean;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Author Tong LinXing
 * @date 2020/12/13
 */
public class JSONEncoderTest {

    @Test
    public void encode() {
        Encoder encoder = new JSONEncoder();

        TestBean bean = new TestBean();
        bean.setAge(24);
        bean.setName("tonglxer");
        byte[] bytes = encoder.encode(bean);

        assertNotNull(bytes);
    }
}