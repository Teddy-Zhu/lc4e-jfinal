package com.teddy.lc4e.core.web.service;

import com.teddy.jfinal.annotation.Cache;
import com.teddy.jfinal.annotation.Service;
import com.teddy.jfinal.common.Const;
import com.teddy.lc4e.core.database.mapping.T_Sys_Menu;
import com.teddy.lc4e.core.database.model.Sys_Menu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by teddy on 2015/8/2.
 */
@Service
public class MenuService {
    public static MenuService service;

    @Cache(cacheName = Const.UIDATA, key = "menus")
    public List<Sys_Menu> getMenuTree() {
        StringBuffer sql = new StringBuffer();
        Sys_Menu menuTree;
        sql.append("select ").append(T_Sys_Menu.ALL_FIELDS).append(" From ")
                .append(T_Sys_Menu.TABLE_NAME).append(" ORDER BY ").append(T_Sys_Menu.PARENTID)
                .append(" ASC ,").append(T_Sys_Menu.ORDER).append(" ASC");
        List<Sys_Menu> allMenus = Sys_Menu.dao.find(sql.toString());

        if (allMenus == null || allMenus.isEmpty()) {
            return new ArrayList<>();
        }
        menuTree = allMenus.get(0);
        menuTree.put("CHILDS", new ArrayList<Sys_Menu>());
        allMenus.remove(0);
        getMenu(allMenus, menuTree);
        return menuTree.get("CHILDS");
    }

    private void getMenu(List<Sys_Menu> allMenus, Sys_Menu curMenu) {
        List<Sys_Menu> children = curMenu.get("CHILDS");
        for (int i = 0, len = allMenus.size(); i < len; ) {
            if (allMenus.get(i).getInt(T_Sys_Menu.parentId).equals(curMenu.getInt(T_Sys_Menu.id))) {
                children.add(allMenus.get(i));
                allMenus.remove(i);
                len--;
            } else {
                i++;
            }
        }
        for (int i = 0, len = children.size(); i < len; i++) {
            children.get(i).put("CHILDS", new ArrayList<Sys_Menu>());
            getMenu(allMenus, children.get(i));
        }
    }

}
