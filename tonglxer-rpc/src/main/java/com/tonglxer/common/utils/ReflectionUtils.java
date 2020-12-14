package com.tonglxer.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * 反射工具类
 *
 * @Author Tong LinXing
 * @date 2020/12/12
 */
@Slf4j
public class ReflectionUtils {

    /**
     * 通过反射根据class创建对象
     *
     * @param <T> 独享类型
     * @param clazz 需要创建对象的字节码
     * @return 对象实例
     * */
    public static <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            log.error("Get new instance have some error.");
        }
        return null;
    }

    /**
     * 获取当前类的所有公共方法
     *
     * @param clazz 需要获取的类的字节码
     * @return 该类的所有公共方法
     * */
    public static Method[] getPublicMethods(Class clazz) {
        // 不用getMethods()是因为该方法会获取父类的public方法
        // 避免出现未知错误
        Method[] methods = clazz.getDeclaredMethods();
        List<Method> publicMethods = new ArrayList<>();
        for (Method m : methods) {
            if (Modifier.isPublic(m.getModifiers())) {
                publicMethods.add(m);
            }
        }
        // new XXX[0]只是为了指定函数的形参
        // 最终返回的String[]的长度由list存储内容的长度决定
        return publicMethods.toArray(new Method[0]);
    }

    /**
     * 调用指定对象的某一方法
     *
     * @param obj 指定的对象
     * @param method 方法
     * @param args 方法参数
     * */
    public static Object invoke(Object obj,
                                Method method,
                                Object... args) {
        try {
            // 调用原生的invoke方法并返回
            // 若方法是静态方法 则obj传入null
            return method.invoke(obj, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.error("Invoke method have some error.");
        }
        return null;
    }
}
