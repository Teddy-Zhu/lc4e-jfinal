package com.teddy.lc4e.web.service;

import com.teddy.jfinal.annotation.Cache;
import com.teddy.jfinal.annotation.Service;
import com.teddy.jfinal.common.Const;
import com.teddy.jfinal.tools.SQLTool;
import com.teddy.lc4e.database.model.SysMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by teddy on 2015/8/2.
 */
@Service
public class MenuService {
    public static MenuService service;

    @Cache(cacheName = Const.UIDATA, key = "menus")
    public List<SysMenu> getMenuTree() {
        SysMenu menuTree;
        SQLTool sql = new SQLTool().select(SysMenu.ALL_FIELDS)
                .from(SysMenu.TABLE_NAME).orderByAsc(SysMenu.parentid, SysMenu.order);
        List<SysMenu> allMenus = SysMenu.dao.find(sql);

        if (allMenus == null || allMenus.isEmpty()) {
            return new ArrayList<>();
        }
        menuTree = allMenus.get(0);
        menuTree.put("CHILDS", new ArrayList<SysMenu>());
        allMenus.remove(0);
        getMenu(allMenus, menuTree);
        return menuTree.get("CHILDS");
    }

    private void getMenu(List<SysMenu> allMenus, SysMenu curMenu) {
        List<SysMenu> children = curMenu.get("CHILDS");
        for (int i = 0, len = allMenus.size(); i < len; ) {
            if (allMenus.get(i).getParentId().equals(curMenu.getId())) {
                children.add(allMenus.get(i));
                allMenus.remove(i);
                len--;
            } else {
                break;
            }
        }
        for (SysMenu aChildren : children) {
            aChildren.put("CHILDS", new ArrayList<SysMenu>());
            getMenu(allMenus, aChildren);
        }
    }

}
