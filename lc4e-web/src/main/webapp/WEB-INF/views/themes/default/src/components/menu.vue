<template xmlns:v-bind="http://www.w3.org/1999/xhtml">
    <template v-for="menu in menus">
        <div class="ui dropdown link item" v-if="menu.CHILDS.length > 0">
            <i v-if="menu.ICON" v-bind:class="[menu.ICON,'icon']"></i>
            <span class="text">{{menu.NAME}}</span><i class="dropdown icon"></i>

            <div class="menu">
                <menu-tree :menus="menu.CHILDS"></menu-tree>
            </div>
        </div>
        <a class="item linked" title="{{menu.NAME}}" v-link="menu.ABBR" v-else>
            <i v-bind:class="[menu.ICON,'icon']" v-if="menu.ICON"></i>
            {{menu.NAME}}
        </a>
    </template>
</template>

<script>
    module.exports = {
        name: 'menu-tree',
        props: {
            menus: {
                type: Array
            }
        }
    }
</script>