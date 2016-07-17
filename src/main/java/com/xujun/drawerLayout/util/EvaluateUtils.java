package com.xujun.drawerLayout.util;

/**
 * @ explain:
 * @ author：xujun on 2016/7/16 19:02
 * @ email：gdutxiaoxu@163.com
 */
public class EvaluateUtils {
    /**
     * 估值器
     *
     * @param fraction
     * @param startValue
     * @param endValue
     * @return
     */
    public static Float evaluate(float fraction, Number startValue, Number endValue) {
        float startFloat = startValue.floatValue();
        return startFloat + fraction * (endValue.floatValue() - startFloat);
    }
}
