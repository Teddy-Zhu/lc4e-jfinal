package com.teddy.lc4e.database.generate;

import com.jfinal.kit.PathKit;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.generator.Generator;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.teddy.jfinal.common.Const;
import com.teddy.jfinal.common.Dict;
import com.teddy.jfinal.plugin.PropPlugin;
import com.teddy.jfinal.tools.StringTool;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * generate db table mappers for mysql
 * Created by teddy on 2015/7/25.
 */
public class GenerateDB {
    private static final Logger LOGGER = Logger.getLogger(GenerateDB.class);
    private static String url;
    private static String username;
    private static String password;
    private static Connection con = null;


    private void generate() throws ClassNotFoundException, SQLException, IOException {
        Prop prop = PropKit.use(Const.CONFIG_FILE, "utf-8");

        url = prop.get(Dict.DATABASE_URL);
        username = prop.get(Dict.DATABASE_USERNAME);
        password = prop.get(Dict.DATABASE_PASSWORD);

        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(url, username, password);

        List<DatabaseModel> list = new ArrayList<DatabaseModel>();
        ResultSet rs = con.getMetaData().getTables(null, null, "", new String[]{"TABLE", "VIEW"});
        while (rs.next()) {
            list.add(new DatabaseModel(rs.getString("TABLE_NAME"), new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>()));
        }
        DatabaseMetaData metaData = con.getMetaData();
        for (DatabaseModel model : list) {

            ResultSet columns = metaData.getColumns(null, "%", model.tableName, "%");
            while (columns.next()) {
                model.getFields().add(columns.getString("COLUMN_NAME"));
                model.getRemarks().add("    /**\n" + "     * \n" + "     * Type:" + columns.getString("TYPE_NAME") + "\n" + "     * Remarks:" + columns.getString("REMARKS") + "\n" + "     */" + "\n");
            }
            ResultSet pkRSet = metaData.getPrimaryKeys(null, null, model.getTableName());
            while (pkRSet.next()) {
                model.getPks().add(pkRSet.getString("COLUMN_NAME"));
            }
        }
        //Generate Model
        String imports = "import com.teddy.jfinal.annotation.Model;\n" +
                "import com.teddy.jfinal.interfaces.DBModel;\n\n";
        String header = "/**\n" +
                " * Created by lc4e Tool on " + new SimpleDateFormat("yy/MM/dd").format(new Date()) + ".\n" +
                " */\n";
        //Generate Mapping
        for (DatabaseModel model : list) {
            String className = StringTool.toUpperCaseFirstOne(model.getTableName(), "_");
            LOGGER.info("[start]:" + model.getTableName());
            File file = new File("./Model");

            if (!file.exists()) {
                file.mkdirs();
            }
            file = new File("./Model/" + className + ".java");
            if (file.exists())
                file.delete();

            file.createNewFile();
            FileOutputStream out = new FileOutputStream(file, true);

            StringBuffer sb = new StringBuffer();
            sb.append(header);
            sb.append(imports).append(header).append("@Model(value = \"" + model.getTableName() + "\"" + (model.getPks().size() > 0 ? ", pk = {\"" + StringTool.join(model.getPks(), ",") + "\"}" : "") + ")\n");

            sb.append("public class " + className + " extends DBModel<" + className + "> {\n");

            sb.append("    public static final " + className + " dao = new " + className + "();\n\n");
//
            for (int i = 0, len = model.getFields().size(); i < len; i++) {
                sb.append(model.getRemarks().get(i));
                sb.append("    public static final String F_").append(model.getFields().get(i).toUpperCase()).append(" = \"").append(model.getTableName()).append(".").append(model.getFields().get(i)).append("\";\n\n");
                sb.append("    public static final String S_").append(model.getFields().get(i).toUpperCase()).append(" = \"").append(model.getFields().get(i).toUpperCase()).append("\";\n\n");
            }
            sb.append("    public static final String ALL_FIELDS = \"").append(model.getTableName()).append(".*\";\n\n");
            sb.append("    public static final String TABLE_NAME = \"").append(model.getTableName()).append("\";\n\n");

            sb.append("}");
            out.write(sb.toString().getBytes("utf-8"));
            out.close();
        }

    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
        PropPlugin plugin = new PropPlugin(new Prop(Const.CONFIG_FILE,com.jfinal.core.Const.DEFAULT_ENCODING).getProperties());
        plugin.start();
        C3p0Plugin c3p0Plugin = new C3p0Plugin(PropPlugin.getValue(Dict.DATABASE_URL), PropPlugin.getValue(Dict.DATABASE_USERNAME), PropPlugin.getValue(Dict.DATABASE_PASSWORD));
        c3p0Plugin.start();
        //new GenerateDB().generate();
        // base model 所使用的包名
        String baseModelPkg = "model.base";
        // base model 文件保存路径
        String baseModelDir = PathKit.getWebRootPath() + "/../src/com/main/java/com/teddy/lc4e/database/base";
        // model 所使用的包名
        String modelPkg = "model";
        // model 文件保存路径
        String modelDir = baseModelDir + "/..";
        Generator gernerator = new Generator(c3p0Plugin.getDataSource(), baseModelPkg, baseModelDir,
                modelPkg, modelDir);
        gernerator.generate();
    }

}
