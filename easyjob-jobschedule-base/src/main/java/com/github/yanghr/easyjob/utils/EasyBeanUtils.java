package com.github.yanghr.easyjob.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;

import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ArafBeanUtils.
 *
 * @version
 * @author
 */
public class EasyBeanUtils extends BeanUtils {
    private static final String TARGET_MUST_NOT_BE_NULL = "Target must not be null";
    private static final String SOURCE_MUST_NOT_BE_NULL = "Source must not be null";

    /**
     * Determine whether the given array is empty: i.e. {@code null} or of zero length.
     *
     * @param array the array to check
     * @return boolean
     */
    public static boolean isEmpty(Object[] array) {
        return (array == null || array.length == 0);
    }

    /**
     * 功能 : 只复制source对象的非空属性到target对象上. list为浅拷贝，只复制相同类型的list
     *
     * @param source 源对象
     * @param target 目标对象
     * @throws BeansException BeansException
     * @return target
     */
    public static <T> T copyNotNullProperties(Object source, T target) {
        if (source == null) {
            return null;
        }
        foreachProperty(source, target);
        return target;
    }

    /**
     * 复制source对象的同名属性到target对象, 并返回target; 对关联的list属性浅拷贝
     *
     * @param source 源对象
     * @param target 目标对象
     * @return target
     */
    public static <T> T copyProperties2(Object source, T target) {
        if (source == null) {
            return null;
        }
        copyProperties(source, target);
        return target;
    }

    /**
     * 复制source对象的同名属性到target对象, 并返回target; 对关联的list属性浅拷贝
     *
     * @param source 源对象
     * @param target 目标对象
     * @param ignoreProperties 不需要拷贝的属性名
     * @return target
     */
    public static <T> T copyProperties2(Object source, T target, String... ignoreProperties) {
        copyProperties(source, target, ignoreProperties);
        return target;
    }

    private static void foreachProperty(Object source, Object target) {
        for (PropertyDescriptor targetPd : getPropertyDescriptors(target.getClass())) {
            if (targetPd.getWriteMethod() != null) {
                copyPropertyForNotEmpty(source, target, targetPd,
                        getPropertyDescriptor(source.getClass(), targetPd.getName()));
            }
        }
    }

    private static void copyPropertyForNotEmpty(Object source, Object target,
                                                PropertyDescriptor targetPd,
                                                PropertyDescriptor sourcePd) {
        if (sourcePd != null && sourcePd.getReadMethod() != null) {
            try {
                Object value = getSourcePropertyValue(source, sourcePd);
                // 这里判断以下value是否为空 当然这里也能进行一些特殊要求的处理 例如绑定时格式转换等等
                if (value != null) {
                    setTargetPropertyValue(target, targetPd, value);
                }
            } catch (Exception ex) {
                throw new FatalBeanException(
                        String.format("Could not copy properties from source to target. %s",
                                targetPd.getDisplayName()),
                        ex);
            }
        }
    }

    private static void setTargetPropertyValue(Object target, PropertyDescriptor targetPd,
                                               Object value)
            throws IllegalAccessException, InvocationTargetException, InstantiationException,
            NoSuchMethodException {
        Method writeMethod = targetPd.getWriteMethod();
        if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
            writeMethod.setAccessible(true);
        }
        try {
            writeMethod.invoke(target, value);
        } catch (IllegalArgumentException e) {
            // 类型不匹配时，尝试递归调用对象拷贝
            Method readMethod = targetPd.getReadMethod();
            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                readMethod.setAccessible(true);
            }
            Object o = readMethod.invoke(target);
            if (o == null) {
                o = targetPd.getPropertyType().getConstructor().newInstance();
                writeMethod.invoke(target, o);
            }
            copyNotNullProperties(value, o);
        }
    }

    private static Object getSourcePropertyValue(Object source, PropertyDescriptor sourcePd)
            throws IllegalAccessException, InvocationTargetException {
        Method readMethod = sourcePd.getReadMethod();
        if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
            readMethod.setAccessible(true);
        }
        return readMethod.invoke(source);
    }

    @SuppressWarnings("rawtypes")
    public static Class getCollectionGenericType(Field f) {
        f.setAccessible(true);
        if (f.getType() == List.class) {
            Type genericType = f.getGenericType();
            if (genericType instanceof ParameterizedType) {
                ParameterizedType pt = (ParameterizedType) genericType;
                return (Class<?>) pt.getActualTypeArguments()[0];
            }
        }
        return null;
    }

    /**
     * 拷贝list中的对象的属性值。 如果list长度相同，则按顺序依次拷贝。 如果list长度不同，则自动在目的方生成对象的new
     * isntance，拷贝属性。保证target的list泛型类型，有无参构造函数。
     *
     * @param source
     * @param target
     * @return target
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static List copyListProperties(List<?> source, List target, Class targetClass) {
        Assert.notNull(source, SOURCE_MUST_NOT_BE_NULL);
        Assert.notNull(target, TARGET_MUST_NOT_BE_NULL);
        if (isSimpleProperty(targetClass)) {
            copySimpleProperties(source, target);
            return target;
        }
        try {
            if (source.size() == target.size()) {
                for (int i = 0; i < source.size(); i++) {
                    copyProperties(source.get(i), target.get(i));
                }

            }

            if (source.size() > target.size()) {
                int targetSize = target.size();
                for (int i = 0; i < targetSize; i++) {
                    copyProperties(source.get(i), target.get(i));
                }
                for (int j = targetSize; j < source.size(); j++) {
                    Object targetObject = targetClass.newInstance();
                    copyProperties(source.get(j), targetObject);
                    target.add(targetObject);
                }
            }

            return target;
        } catch (Exception e) {
            throw new FatalBeanException("Could not copy list from source to target", e);
        }

    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static void copySimpleProperties(List<?> source, List target) {
        target.clear();
        for (int i = 0; i < source.size(); i++) {
            target.add(source.get(i));
        }
    }

    /**
     * 功能 : 复制source对象的属性到target对象上, 只复制不为空的对象值。
     * source中的list类型，会按顺序复制属性到target的list中，如果target少，则自动生成目标对象放入list中.
     *
     * @param source 源对象, 原对象为空，则返回空
     * @param target 目标对象
     * @return target
     */
    public static <T> T fullCopyProperties(Object source, T target) {
        if (source == null) {
            return null;
        }
        for (PropertyDescriptor targetPd : getPropertyDescriptors(target.getClass())) {
            if (targetPd.getWriteMethod() != null) {
                copyPropertyForList(source, target, targetPd,
                        getPropertyDescriptor(source.getClass(), targetPd.getName()));
            }
        }
        return target;
    }

    @SuppressWarnings("rawtypes")
    private static void copyPropertyForList(Object source, Object target,
                                            PropertyDescriptor targetPd,
                                            PropertyDescriptor sourcePd) {
        if (sourcePd != null && sourcePd.getReadMethod() != null) {
            try {
                Object value = getSourcePropertyValue(source, sourcePd);
                // 这里判断以下value是否为空 当然这里也能进行一些特殊要求的处理 例如绑定时格式转换等等
                if (value != null) {
                    if (isListAndNotEmpty(value)) {
                        Field f = target.getClass().getDeclaredField(sourcePd.getName());
                        f.setAccessible(true);
                        if (f.get(target) == null) {
                            f.set(target, new ArrayList());
                        }
                        copyListProperties((List) value, (List) f.get(target),
                                getCollectionGenericType(f));
                        return;
                    }
                    // 判断对象的属性，如果是Collection，且为空，则不拷贝。值不为空，才会拷贝
                    setTargetPropertyValue(target, targetPd, value);
                }
            } catch (Exception ex) {
                throw new FatalBeanException(
                        String.format("Could not copy properties from source to target. %s",
                                targetPd.getDisplayName()),
                        ex);
            }
        }
    }

    @SuppressWarnings("rawtypes")
    private static boolean isListAndNotEmpty(Object value) {
        return List.class.isAssignableFrom(value.getClass()) && !((List) value).isEmpty();
    }

}
