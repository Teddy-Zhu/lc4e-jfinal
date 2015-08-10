package com.teddy.jfinal.tools.generate;

import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.teddy.jfinal.common.Const;
import com.teddy.jfinal.common.Dict;
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

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {


        Prop prop = PropKit.use(Const.CONFIG_FILE, "utf-8");

        url = prop.get(Dict.DATABASE_URL);
        username = prop.get(Dict.DATABASE_USERNAME);
        password = prop.get(Dict.DATABASE_PASSWORD);

        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(url, username, password);

        List<DatabaseModel> list = new ArrayList<>();
        ResultSet rs = con.getMetaData().getTables(null, null, "", new String[]{"TABLE", "VIEW"});
        while (rs.next()) {
            list.add(new DatabaseModel(rs.getString("TABLE_NAME"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
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

        String header = "/**\n" +
                " * Created by lc4e Tool on " + new SimpleDateFormat("yy/MM/dd").format(new Date()) + ".\n" +
                " */\n";
        //Generate Mapping
        for (DatabaseModel model : list) {
            String className = StringTool.toUpperCaseFirstOne(model.getTableName(), "_");
            LOGGER.info("[start]:" + model.getTableName());
            File file = new File("E:\\Desktop\\Table\\mapping");

            if (!file.exists()) {
                file.mkdirs();
            }
            file = new File("E:\\Desktop\\Table\\mapping\\T_" + className + ".java");
            if (file.exists())
                file.delete();

            file.createNewFile();
            FileOutputStream out = new FileOutputStream(file, true);

            StringBuffer sb = new StringBuffer();
            sb.append(header);
            sb.append("public class T_" + className + " {\n");
            for (int i = 0, len = model.getFields().size(); i < len; i++) {
                sb.append(model.getRemarks().get(i));
                sb.append("    public static final String " + model.getFields().get(i).toUpperCase() + " = \"" + model.getTableName() + "." + model.getFields().get(i) + "\";\n\n");
                sb.append("    public static final String " + model.getFields().get(i) + " = \"" + model.getFields().get(i).toUpperCase() + "\";\n\n");
            }
            sb.append("    public static final String ALL_FIELDS = \"" + model.getTableName() + ".*\";\n\n");
            sb.append("    public static final String TABLE_NAME = \"" + model.getTableName() + "\";\n\n");

            sb.append("}");
            out.write(sb.toString().getBytes("utf-8"));
            out.close();
        }

        //Generate Model
        String imports = "import com.teddy.jfinal.annotation.Model;\n" +
                "import com.teddy.jfinal.interfaces.DBModel;\n\n";

        for (DatabaseModel model : list) {
            LOGGER.info("[start]:" + model.getTableName());
            String className = StringTool.toUpperCaseFirstOne(model.getTableName(), "_");
            File file = new File("E:\\Desktop\\Table\\model");

            if (!file.exists()) {
                file.mkdirs();
            }
            file = new File("E:\\Desktop\\Table\\model\\" + className + ".java");
            if (file.exists())
                file.delete();

            file.createNewFile();
            FileOutputStream out = new FileOutputStream(file, true);

            StringBuffer sb = new StringBuffer();
            sb.append(imports).append(header).append("@Model(value = \"" + model.getTableName() + "\"" + (model.getPks().size() > 0 ? ", pk = {\"" + StringTool.join(model.getPks(), ",") + "\"}" : "") + ")\n");
            //sb.append(imports).append(header).append("@Model\n");

            sb.append("public class " + className + " extends DBModel<" + className + "> {\n");

            sb.append("    public static final " + className + " dao = new " + className + "();\n\n");
//
//            for (String pk : model.getPks()) {
//                sb.append("    public " + className + " get" + className + "By" + StringTool.toUpperCaseFirstOne(pk) + " () " + ";\n\n");
//
//            }

            sb.append("}");
            out.write(sb.toString().getBytes("utf-8"));
            out.close();
        }
    }
}
