package com.github.yanghr.easyjob.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EasyQueryWrapper<T> extends QueryWrapper<T> {
    private static final long serialVersionUID = 1L;

    public EasyQueryWrapper(T entity) {
        super.setEntity(entity);
        super.initNeed();
    }

    protected String columnToString(String column) {
        return StringUtils.camelToUnderline(column);
    }

    public static <T> EasyQueryWrapper<T> query() {
        return new EasyQueryWrapper();
    }

    public static <T> EasyQueryWrapper<T> query(T entity) {
        return new EasyQueryWrapper(entity);
    }
}
