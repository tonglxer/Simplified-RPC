package com.tonglxer.utils;

import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

/**
 * 反射工具类 单元测试
 *
 * @Author Tong LinXing
 * @date 2020/12/12
 */
public class ReflectionUtilsTest {

    @Test
    public void newInstance() {
        TestClass t = ReflectionUtils.newInstance(TestClass.class);
        assertNotNull(t);
    }

    @Test
    public void getPublicMethods() {
        Method[] methods = ReflectionUtils.getPublicMethods(TestClass.class);
        assertEquals(1, methods.length);
        assertEquals("b", methods[0].getName());
    }

    @Test
    public void invoke() {
        Method[] methods = ReflectionUtils.getPublicMethods(TestClass.class);
        Method b = methods[0];
        TestClass t = ReflectionUtils.newInstance(TestClass.class);
        Object res = ReflectionUtils.invoke(t, b);
        assertEquals("b", res);

    }
}