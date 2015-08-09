package com.teddy.jfinal.interfaces;

import com.jfinal.plugin.activerecord.Model;
import com.teddy.jfinal.tools.CustomTool;

/**
 * Created by teddy on 2015/7/28.
 */
public abstract class DBModel<M extends DBModel> extends Model<M> {
    private static final long serialVersionUID = -101956006715745792L;

    @SuppressWarnings("unchecked")
    public M enhancer() {
        return CustomTool.auto((M) this);
    }

    @SuppressWarnings("unchecked")
    public M custom() {
        return CustomTool.custom((M) this);
    }

    @SuppressWarnings("unchecked")
    public M transaction() {
        return CustomTool.transaction((M) this);
    }
}
