package com.github.yanghr.easyjob.utils;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EasyStringUtils extends StringUtils {
    public static final char UNDERLINE = '_';

    private EasyStringUtils() {
    }

    /**
     * Check whether the given String is empty.
     * <p>
     * This method accepts any Object as an argument, comparing it to {@code null} and the empty
     * String. As a consequence, this method will never return {@code true} for a non-null
     * non-String object.
     * <p>
     * The Object signature is useful for general attribute handling code that commonly deals with
     * Strings but generally has to iterate over Objects since attributes may e.g. be primitive
     * value objects as well.
     *
     * @param str the candidate String
     * @since 3.2.1
     */
    public static boolean isEmpty(Object str) {
        return (str == null || "".equals(str));
    }

    /**
     * Check that the given CharSequence is neither {@code null} nor of length 0. Note: Will return
     * {@code true} for a CharSequence that purely consists of whitespace.
     * <p>
     *
     * <pre>
     * StringUtils.hasLength(null) = false
     * StringUtils.hasLength("") = false
     * StringUtils.hasLength(" ") = true
     * StringUtils.hasLength("Hello") = true
     * </pre>
     *
     * @param str the CharSequence to check (may be {@code null})
     * @return {@code true} if the CharSequence is not null and has length
     * @see # hasText(String)
     */
    public static boolean hasLength(CharSequence str) {
        return (str != null && str.length() > 0);
    }

    /**
     * Check that the given String is neither {@code null} nor of length 0. Note: Will return
     * {@code true} for a String that purely consists of whitespace.
     *
     * @param str the String to check (may be {@code null})
     * @return {@code true} if the String is not null and has length
     * @see #hasLength(CharSequence)
     */
    public static boolean hasLength(String str) {
        return hasLength((CharSequence) str);
    }

    /**
     * Check whether the given CharSequence has actual text. More specifically, returns {@code true}
     * if the string not {@code null}, its length is greater than 0, and it contains at least one
     * non-whitespace character.
     * <p>
     *
     * <pre>
     * StringUtils.hasText(null) = false
     * StringUtils.hasText("") = false
     * StringUtils.hasText(" ") = false
     * StringUtils.hasText("12345") = true
     * StringUtils.hasText(" 12345 ") = true
     * </pre>
     *
     * @param str the CharSequence to check (may be {@code null})
     * @return {@code true} if the CharSequence is not {@code null}, its length is greater than 0,
     *         and it does not contain whitespace only
     * @see Character#isWhitespace
     */
    public static boolean hasText(CharSequence str) {
        if (!hasLength(str)) {
            return false;
        }
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Convenience method to return a Collection as a delimited (e.g. CSV) String. E.g. useful for
     * {@code toString()} implementations.
     *
     * @param coll the Collection to display
     * @param delim the delimiter to use (probably a ",")
     * @param prefix the String to start each element with
     * @param suffix the String to end each element with
     * @return the delimited String
     */
    public static String collectionToDelimitedString(Collection<?> coll, String delim,
                                                     String prefix, String suffix) {
        if (CollectionUtils.isEmpty(coll)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Iterator<?> it = coll.iterator();
        while (it.hasNext()) {
            sb.append(prefix).append(it.next()).append(suffix);
            if (it.hasNext()) {
                sb.append(delim);
            }
        }
        return sb.toString();
    }

    /**
     * Convenience method to return a Collection as a delimited (e.g. CSV) String. E.g. useful for
     * {@code toString()} implementations.
     *
     * @param coll the Collection to display
     * @param delim the delimiter to use (probably a ",")
     * @return the delimited String
     */
    public static String collectionToDelimitedString(Collection<?> coll, String delim) {
        return collectionToDelimitedString(coll, delim, "", "");
    }

    /**
     * Convenience method to return a Collection as a CSV String. E.g. useful for {@code toString()}
     * implementations.
     *
     * @param coll the Collection to display
     * @return the delimited String
     */
    public static String collectionToCommaDelimitedString(Collection<?> coll) {
        return collectionToDelimitedString(coll, ",");
    }

    public static String formatNull(Object str) {
        if (EasyStringUtils.isEmpty(str)) {
            return "";
        }
        return str.toString();
    }

    /**
     * 获取系统默认字符集.
     *
     * @return
     */
    public static String systemDefaultEncoding() {
        return System.getProperty("sun.jnu.encoding");
    }

    /**
     * 字符串长度
     *
     * @param name
     * @param encoding
     * @return
     * @throws Exception
     */
    public static int getStringLength(String name, String encoding) {
        try {
            if(isEmpty(name)) {
                return 0;
            }

            // 系统默认为utf-8时，直接返回长度.
            if("UTF-8".equals(systemDefaultEncoding())) {
                return name.toString().getBytes(encoding).length;
            }

            return (new String(name.toString().getBytes(systemDefaultEncoding()), encoding).getBytes(encoding)).length;
        } catch (Exception ex) {
            return name.getBytes().length;
        }
    }

    /**
     * <p>
     * 首字母转换小写
     * </p>
     *
     * @param param 需要转换的字符串
     * @return 转换好的字符串
     */
    public static String firstToLowerCase(String param) {
        if (isEmpty(param)) {
            return "";
        }
        return param.substring(0, 1).toLowerCase() + param.substring(1);
    }

    /**
     * camel类型的string，转换成underline类型.
     * @param param MasAdm -> mas_adm
     * @return String
     */
    public static String camelToUnderline(String param) {
        if (isBlank(param)) {
            return EMPTY;
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c) && i > 0) {
                sb.append(UNDERLINE);
            }
            sb.append(Character.toLowerCase(c));
        }
        return sb.toString();
    }

    /**
     * underline类型的转换为camel
     * @param param mas_adm -> MasAdm
     * @return String
     */
    public static String underlineToCamel(String param) {
        if (isBlank(param)) {
            return EMPTY;
        }
        String temp = param.toLowerCase();
        int len = temp.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = temp.charAt(i);
            if (c == UNDERLINE) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(temp.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 判断是否都是数字
     * @param str String
     * @return boolean
     */
    public static boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

    /**
     * 判断该字符串是否为中文
     * @param string
     * @return boolean
     */
    public static boolean isChinese(String string){
        int n = 0;
        for(int i = 0; i < string.length(); i++) {
            n = (int)string.charAt(i);
            if(!(19968 <= n && n <40869)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 比较字符串，为了sonar中的Security - Unsafe hash equals
     * An attacker might be able to detect the value of the secret hash due to the exposure of comparison timing.
     * When the functions Arrays.equals() or String.equals() are called, they will exited earlier if less bytes are matched.
     * @param str1 String
     * @param str2 String
     * @return str1,str2都为空，则返回true；其余情况则比较equals
     */
    public static boolean isEquals(String str1, String str2) {
        if(str1 == null && str2 == null) {
            return true;
        }

        if(str1 != null) {
            return str1.equals(str2);
        }

        return false;
    }
}
