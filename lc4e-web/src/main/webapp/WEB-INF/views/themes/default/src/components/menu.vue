<template>
    <template v-for="menu in menus">
        <template v-if="size(menu)">
            <div class="ui dropdown link item">
                <i v-if="menu.ICON" v-bind:class="[menu.ICON,'icon']"></i>
                <span class="text">{{menu.NAME}}</span><i class="dropdown icon"></i>

                <div class="menu">
                    <menu-tree :menus="menu.CHILDS"></menu-tree>
                </div>
            </div>
        </template>
        <template v-else>
            <a class="item linked" title="{{menu.NAME}}" v-link="menu.ABBR">
                <i v-bind:class="[menu.ICON,'icon']" v-if="menu.ICON"></i>
                {{menu.NAME}}
            </a>
        </template>
    </template>
</template>

<script>
module.exports = {
    name: 'menu-tree',
    props: {
        menus: {
            type: Array
        }
    },
    methods: {
        size: function (obj) {
            return obj.CHILDS.length > 0;
        }
    }
}
</script>