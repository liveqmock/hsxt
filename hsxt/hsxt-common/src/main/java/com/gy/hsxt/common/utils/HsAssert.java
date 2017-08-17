/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.common.utils;

import com.gy.hsxt.common.constant.IRespCode;
import com.gy.hsxt.common.exception.HsException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;

/**
 * 参数验证工具类
 * <p>参数验证失败 统一抛出{@link HsException}</p>
 * @Package :com.gy.hsxt.common.utils
 * @ClassName : HsAssert
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/9/30 12:03
 * @Version V3.0.0.0
 */
public abstract class HsAssert {

    /**
     * Assert a boolean expression, throwing {@code HsException}
     * if the test result is {@code false}.
     * <pre class="code">Assert.isTrue(i &gt; 0, "The value must be greater than zero");</pre>
     * @param expression a boolean expression
     * @param respCode the exception code to user if the assertion fails
     * @param message the exception message to use if the assertion fails
     * @throws HsException if expression is {@code false}
     */
    public static void isTrue(boolean expression,IRespCode respCode, String message)throws HsException {
        if (!expression) {
            throw new HsException(respCode,message);
        }
    }

    /**
     * Assert a boolean expression, throwing {@code HsException}
     * if the test result is {@code false}.
     * <pre class="code">Assert.isTrue(i &gt; 0);</pre>
     * @param expression a boolean expression
     * @param respCode the exception code to user if the assertion fails
     * @throws HsException if expression is {@code false}
     */
    public static void isTrue(boolean expression,IRespCode respCode) throws HsException {
        isTrue(expression, respCode, "[Assertion failed] - this expression must be true");
    }

    /**
     * Assert that an object is {@code null} .
     * <pre class="code">Assert.isNull(value, "The value must be null");</pre>
     * @param object the object to check
     * @param respCode the exception code to user if the assertion fails
     * @param message the exception message to use if the assertion fails
     * @throws HsException if the object is not {@code null}
     */
    public static void isNull(Object object,IRespCode respCode, String message) throws HsException {
        if (object != null) {
            throw new HsException(respCode,message);
        }
    }

    /**
     * Assert that an object is {@code null} .
     * <pre class="code">Assert.isNull(value);</pre>
     * @param object the object to check
     * @param respCode the exception code to user if the assertion fails
     * @throws HsException if the object is not {@code null}
     */
    public static void isNull(Object object,IRespCode respCode) throws HsException {
        isNull(object, respCode, "[Assertion failed] - the object argument must be null");
    }

    /**
     * Assert that an object is not {@code null} .
     * <pre class="code">Assert.notNull(clazz, "The class must not be null");</pre>
     * @param object the object to check
     * @param respCode the exception code to user if the assertion fails
     * @param message the exception message to use if the assertion fails
     * @throws HsException if the object is {@code null}
     */
    public static void notNull(Object object,IRespCode respCode, String message)throws HsException {
        if (object == null) {
            throw new HsException(respCode,message);
        }
    }

    /**
     * Assert that an object is not {@code null} .
     * <pre class="code">Assert.notNull(clazz);</pre>
     * @param object the object to check
     * @param respCode the exception code to user if the assertion fails
     * @throws HsException if the object is {@code null}
     */
    public static void notNull(Object object,IRespCode respCode)throws HsException {
        notNull(object, respCode, "[Assertion failed] - this argument is required; it must not be null");
    }

    /**
     * Assert that the given String is not empty; that is,
     * it must not be {@code null} and not the empty String.
     * <pre class="code">Assert.hasLength(name, "Name must not be empty");</pre>
     * @param text the String to check
     * @param respCode the exception code to user if the assertion fails
     * @param message the exception message to use if the assertion fails
     * @see StringUtils#hasLength
     */
    public static void hasLength(String text,IRespCode respCode, String message) throws HsException {
        if (!StringUtils.hasLength(text)) {
            throw new HsException(respCode,message);
        }
    }

    /**
     * Assert that the given String is not empty; that is,
     * it must not be {@code null} and not the empty String.
     * <pre class="code">Assert.hasLength(name);</pre>
     * @param text the String to check
     * @param respCode the exception code to user if the assertion fails
     * @see StringUtils#hasLength
     */
    public static void hasLength(String text,IRespCode respCode) throws HsException {
        hasLength(text, respCode, "[Assertion failed] - this String argument must have length; it must not be null or empty");
    }

    /**
     * Assert that the given String has valid text content; that is, it must not
     * be {@code null} and must contain at least one non-whitespace character.
     * <pre class="code">Assert.hasText(name, "'name' must not be empty");</pre>
     * @param text the String to check
     * @param respCode the exception code to user if the assertion fails
     * @param message the exception message to use if the assertion fails
     * @see StringUtils#hasText
     */
    public static void hasText(String text,IRespCode respCode, String message) throws HsException {
        if (!StringUtils.hasText(text)) {
            throw new HsException(respCode,message);
        }
    }

    /**
     * Assert that the given String has valid text content; that is, it must not
     * be {@code null} and must contain at least one non-whitespace character.
     * <pre class="code">Assert.hasText(name, "'name' must not be empty");</pre>
     * @param text the String to check
     * @param respCode the exception code to user if the assertion fails
     * @see StringUtils#hasText
     */
    public static void hasText(String text,IRespCode respCode) throws HsException {
        hasText(text, respCode, "[Assertion failed] - this String argument must have text; it must not be null, empty, or blank");
    }

    /**
     * Assert that the given text does not contain the given substring.
     * <pre class="code">Assert.doesNotContain(name, "rod", "Name must not contain 'rod'");</pre>
     * @param textToSearch the text to search
     * @param substring the substring to find within the text
     * @param respCode the exception code to user if the assertion fails
     * @param message the exception message to use if the assertion fails
     */
    public static void doesNotContain(String textToSearch, String substring, IRespCode respCode,String message) throws HsException {
        if (StringUtils.hasLength(textToSearch) && StringUtils.hasLength(substring) &&textToSearch.contains(substring)) {
            throw new HsException(respCode,message);
        }
    }

    /**
     * Assert that the given text does not contain the given substring.
     * <pre class="code">Assert.doesNotContain(name, "rod");</pre>
     * @param textToSearch the text to search
     * @param substring the substring to find within the text
     * @param respCode the exception code to user if the assertion fails
     */
    public static void doesNotContain(String textToSearch, String substring,IRespCode respCode) throws HsException {
        doesNotContain(textToSearch, substring, respCode, "[Assertion failed] - this String argument must not contain the substring [" + substring + "]");
    }


    /**
     * Assert that an array has elements; that is, it must not be
     * {@code null} and must have at least one element.
     * <pre class="code">Assert.notEmpty(array, "The array must have elements");</pre>
     * @param array the array to check
     * @param respCode the exception code to user if the assertion fails
     * @param message the exception message to use if the assertion fails
     * @throws HsException if the object array is {@code null} or has no elements
     */
    public static void notEmpty(Object[] array, IRespCode respCode,String message) throws HsException {
        if (ObjectUtils.isEmpty(array)) {
            throw new HsException(respCode,message);
        }
    }

    /**
     * Assert that an array has elements; that is, it must not be
     * {@code null} and must have at least one element.
     * <pre class="code">Assert.notEmpty(array);</pre>
     * @param array the array to check
     * @param respCode the exception code to user if the assertion fails
     * @throws HsException if the object array is {@code null} or has no elements
     */
    public static void notEmpty(Object[] array,IRespCode respCode) throws HsException {
        notEmpty(array, respCode, "[Assertion failed] - this array must not be empty: it must contain at least 1 element");
    }

    /**
     * Assert that an array has no null elements.
     * Note: Does not complain if the array is empty!
     * <pre class="code">Assert.noNullElements(array, "The array must have non-null elements");</pre>
     * @param array the array to check
     * @param respCode the exception code to user if the assertion fails
     * @param message the exception message to use if the assertion fails
     * @throws HsException if the object array contains a {@code null} element
     */
    public static void noNullElements(Object[] array,IRespCode respCode, String message) throws HsException {
        if (array != null) {
            for (Object element : array) {
                if (element == null) {
                    throw new HsException(respCode,message);
                }
            }
        }
    }

    /**
     * Assert that an array has no null elements.
     * Note: Does not complain if the array is empty!
     * <pre class="code">Assert.noNullElements(array);</pre>
     * @param array the array to check
     * @param respCode the exception code to user if the assertion fails
     * @throws HsException if the object array contains a {@code null} element
     */
    public static void noNullElements(Object[] array,IRespCode respCode) throws HsException {
        noNullElements(array, respCode, "[Assertion failed] - this array must not contain any null elements");
    }

    /**
     * Assert that a collection has elements; that is, it must not be
     * {@code null} and must have at least one element.
     * <pre class="code">Assert.notEmpty(collection, "Collection must have elements");</pre>
     * @param collection the collection to check
     * @param respCode the exception code to user if the assertion fails
     * @param message the exception message to use if the assertion fails
     * @throws HsException if the collection is {@code null} or has no elements
     */
    public static void notEmpty(Collection<?> collection,IRespCode respCode,String message) throws HsException {
        if (CollectionUtils.isEmpty(collection)) {
            throw new HsException(respCode,message);
        }
    }

    /**
     * Assert that a collection has elements; that is, it must not be
     * {@code null} and must have at least one element.
     * <pre class="code">Assert.notEmpty(collection, "Collection must have elements");</pre>
     * @param collection the collection to check
     * @param respCode the exception code to user if the assertion fails
     * @throws HsException if the collection is {@code null} or has no elements
     */
    public static void notEmpty(Collection<?> collection,IRespCode respCode) throws HsException {
        notEmpty(collection,respCode,
                "[Assertion failed] - this collection must not be empty: it must contain at least 1 element");
    }

    /**
     * Assert that a Map has entries; that is, it must not be {@code null}
     * and must have at least one entry.
     * <pre class="code">Assert.notEmpty(map, "Map must have entries");</pre>
     * @param map the map to check
     * @param respCode the exception code to user if the assertion fails
     * @param message the exception message to use if the assertion fails
     * @throws HsException if the map is {@code null} or has no entries
     */
    public static void notEmpty(Map map,IRespCode respCode, String message) throws HsException {
        if (CollectionUtils.isEmpty(map)) {
            throw new HsException(respCode,message);
        }
    }

    /**
     * Assert that a Map has entries; that is, it must not be {@code null}
     * and must have at least one entry.
     * <pre class="code">Assert.notEmpty(map);</pre>
     * @param map the map to check
     * @param respCode the exception code to user if the assertion fails
     * @throws HsException if the map is {@code null} or has no entries
     */
    public static void notEmpty(Map map,IRespCode respCode) throws HsException {
        notEmpty(map, respCode, "[Assertion failed] - this map must not be empty; it must contain at least one entry");
    }


    /**
     * Assert that the provided object is an instance of the provided class.
     * <pre class="code">Assert.instanceOf(Foo.class, foo);</pre>
     * @param clazz the required class
     * @param obj the object to check
     * @param respCode the exception code to user if the assertion fails
     * @throws HsException if the object is not an instance of clazz
     * @see Class#isInstance
     */
    public static void isInstanceOf(Class<?> clazz, Object obj,IRespCode respCode) throws HsException {
        isInstanceOf(clazz, obj, respCode, "");
    }

    /**
     * Assert that the provided object is an instance of the provided class.
     * <pre class="code">Assert.instanceOf(Foo.class, foo);</pre>
     * @param type the type to check against
     * @param obj the object to check
     * @param respCode the exception code to user if the assertion fails
     * @param message a message which will be prepended to the message produced by
     * the function itself, and which may be used to provide context. It should
     * normally end in a ": " or ". " so that the function generate message looks
     * ok when prepended to it.
     * @throws HsException if the object is not an instance of clazz
     * @see Class#isInstance
     */
    public static void isInstanceOf(Class<?> type, Object obj,IRespCode respCode, String message) throws HsException {
        notNull(type,respCode, "Type to check against must not be null");
        if (!type.isInstance(obj)) {
            throw new HsException(respCode,
                    (StringUtils.hasLength(message) ? message + " " : "") +
                            "Object of class [" + (obj != null ? obj.getClass().getName() : "null") +
                            "] must be an instance of " + type);
        }
    }

    /**
     * Assert that {@code superType.isAssignableFrom(subType)} is {@code true}.
     * <pre class="code">Assert.isAssignable(Number.class, myClass);</pre>
     * @param superType the super type to check
     * @param subType the sub type to check
     * @param respCode the exception code to user if the assertion fails
     * @throws HsException if the classes are not assignable
     */
    public static void isAssignable(Class<?> superType, Class<?> subType,IRespCode respCode) throws HsException {
        isAssignable(superType, subType,respCode,"");
    }

    /**
     * Assert that {@code superType.isAssignableFrom(subType)} is {@code true}.
     * <pre class="code">Assert.isAssignable(Number.class, myClass);</pre>
     * @param superType the super type to check against
     * @param subType the sub type to check
     * @param respCode the exception code to user if the assertion fails
     * @param message a message which will be prepended to the message produced by
     * the function itself, and which may be used to provide context. It should
     * normally end in a ": " or ". " so that the function generate message looks
     * ok when prepended to it.
     * @throws HsException if the classes are not assignable
     */
    public static void isAssignable(Class<?> superType, Class<?> subType, IRespCode respCode,String message) throws HsException {
        notNull(superType, respCode, "Type to check against must not be null");
        if (subType == null || !superType.isAssignableFrom(subType)) {
            throw new HsException(respCode,message + subType + " is not assignable to " + superType);
        }
    }

}
