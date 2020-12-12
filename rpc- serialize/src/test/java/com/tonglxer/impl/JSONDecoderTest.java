package com.tonglxer.impl;

import com.tonglxer.Decoder;
import com.tonglxer.Encoder;
import com.tonglxer.TestBean;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Author Tong LinXing
 * @date 2020/12/13
 */
public class JSONDecoderTest {

    @Test
    public void decode() {
        Encoder encoder = new JSONEncoder();

        TestBean bean = new TestBean();
        bean.setAge(24);
        bean.setName("tonglxer");
        byte[] bytes = encoder.encode(bean);

        Decoder decoder = new JSONDecoder();
        TestBean deBean = decoder.decode(bytes, TestBean.class);

        assertEquals(bean.getName(), deBean.getName());
        assertEquals(bean.getAge(), deBean.getAge());
    }
}