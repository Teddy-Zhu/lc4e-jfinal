<style>
    [v-cloak] {
        display: none;
    }
</style>
<template>
    <header-view></header-view>
    <div id="content">
        <div id="mainContent" class="ui grid centered">
            <router-view></router-view>
        </div>
    </div>
    <footer-view></footer-view>
</template>

<script>
    require('../../../../../themes/default/css/lc4e/jquery-extend.css');
    require('../../../../../themes/default/js/lc4e/jquery-extend.js');
    module.exports = {
        name: 'app',
        data: function () {
            return preLoadData;
        },
        ready: function () {

            $('#gtop').on('click', function () {
                $.requestAnimationFrame(function () {
                    $('body').animate({scrollTop: 0}, 2000, 'easeOutBounce');
                });
            });

            $('.image').visibility({
                type: 'image',
                transition: 'fade in',
                duration: 700
            });
        },
        components: {
            "header-view": require('./views/header.vue'),
            "footer-view": require('./views/footer.vue')
        }

    }
</script>