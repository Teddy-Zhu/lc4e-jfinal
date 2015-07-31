package com.teddy.jfinal.interfaces;

import com.jfinal.plugin.activerecord.Model;
import com.teddy.jfinal.tools.DBTool;

/**
 * Created by teddy on 2015/7/28.
 */
public abstract class DBModel<M extends DBModel> extends Model<M> {
    private static final long serialVersionUID = -101956006715745792L;

    public M enhancer() {
        return DBTool.auto((M) this);
    }
}
