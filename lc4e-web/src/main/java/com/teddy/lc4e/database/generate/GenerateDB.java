package com.teddy.lc4e.database.generate;

import com.jfinal.kit.PathKit;
import com.jfinal.kit.Prop;
import com.jfinal.plugin.activerecord.generator.Generator;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.teddy.jfinal.common.Const;
import com.teddy.jfinal.common.Dict;
import com.teddy.jfinal.tools.PropTool;

import java.io.IOException;
import java.sql.*;

/**
 * generate db table mappers for mysql
 * Created by teddy on 2015/7/25.
 */
public class GenerateDB {

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
        PropTool plugin = new PropTool(new Prop(Const.CONFIG_FILE,com.jfinal.core.Const.DEFAULT_ENCODING).getProperties());
        plugin.start();
        C3p0Plugin c3p0Plugin = new C3p0Plugin(plugin.getValue(Dict.DATABASE_URL), plugin.getValue(Dict.DATABASE_USERNAME), plugin.getValue(Dict.DATABASE_PASSWORD));
        c3p0Plugin.start();
        //new GenerateDB().generate();
        // base model 所使用的包名
        String baseModelPkg = "com.teddy.lc4e.database.model.base";
        // base model 文件保存路径
        String baseModelDir = PathKit.getWebRootPath() + "/src/main/java/com/teddy/lc4e/database/model/base";
        // model 所使用的包名
        String modelPkg = "com.teddy.lc4e.database.model";
        // model 文件保存路径
        String modelDir = baseModelDir + "/..";
        Generator gernerator = new Generator(c3p0Plugin.getDataSource(), new BaseModelGenerator(baseModelPkg, baseModelDir) , new ModelGenerator(modelPkg, baseModelPkg, modelDir));
        gernerator.generate();
    }

}
