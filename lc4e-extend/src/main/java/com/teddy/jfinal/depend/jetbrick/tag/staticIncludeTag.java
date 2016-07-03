package com.teddy.jfinal.depend.jetbrick.tag;

import com.jfinal.core.JFinal;
import jetbrick.template.runtime.JetTagContext;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by teddy on 2015/8/17.
 */
public class staticIncludeTag  {

    public static void staticInclude(JetTagContext ctx, String name) {
        String path = JFinal.me().getServletContext().getRealPath("/");
        try {
            File file = new File(path + name);
            if (!file.exists()) {
                return;
            }
            BufferedReader br = null;
            String line;
            try {
                br = new BufferedReader(new FileReader(file));
                while ((line = br.readLine()) != null) {
                    ctx.getWriter().print(line + "\n");
                }
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            } finally {
                if (br != null) {
                    br.close();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
