package com.teddy.jfinal.plugin.beetl.tag;

import com.jfinal.core.JFinal;
import org.beetl.core.Tag;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by teddy on 2015/8/17.
 */
public class staticIncludeTag extends Tag {

    @Override
    public void render() {
        String path = JFinal.me().getServletContext().getRealPath("/");
        String name = args[0].toString();
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
                    ctx.byteWriter.writeString(line + "\n");
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
