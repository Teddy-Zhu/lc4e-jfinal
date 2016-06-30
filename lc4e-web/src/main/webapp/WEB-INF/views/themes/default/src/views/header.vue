<template>
    <div style="height: 5.286em;" v-show="fixed"></div>
    <div id="menu" class="ui menu" v-bind:class="{ 'expended': menuExpened ,'fixed' : fixed }">
        <div class="column">
            <div class="hidden-pc">
                <a class="item linked"> <i class="content icon"></i> Menus
                </a>
            </div>
            <div class="allmenus">
                <div class="left menu">
                    <img class="logo ui image item hidden-mb" :src="themePath + '/images/logo.png'"/>
                    <menu-tree :menus="menus"></menu-tree>
                </div>
                <div class="right menu" v-bind:class="{ 'float': rightMenuFloat }">
                    <div class="item">
                        <div class="ui icon input">
                            <input id="searchSite" type="text" placeholder="Search..."
                                   v-on:focus="expend" v-on:blur="collapse" :class="{ 'expended' : expended }"/> <i
                                class="search link icon"></i>
                        </div>
                    </div>
                    <template v-if="isLogin">
                        <div id="userItem" class="item" v-show="enable">
                            <img class="ui headered linked image" :src="themePath+'/images/wireframe/image.png'"/>

                            <div id="userCardPop" class="ui flowing popup">
                                <div id="userCard" class="ui card">
                                    <div class="content">
                                        <div class="centered aligned header">
                                            Teddy
                                        </div>
                                        <div class="ui clearing divider"></div>
                                        <div class="description">
                                            <div class="ui divided items">
                                                <div class="item">
                                                    <i class="comments outline icon"></i> Comments <a
                                                        class="ui right floated label"> 11 </a>
                                                </div>
                                                <div class="item">
                                                    <i class="diamond icon"></i> Diamonds <a
                                                        class="ui right floated label">
                                                    111 </a>
                                                </div>
                                                <div class="item">
                                                    <i class="mail outline icon"></i> Messages <a
                                                        class="ui right floated label">
                                                    2111 </a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="extra content">
								<span class="left floated"> <i class="users icon"></i> Follows <a
                                        class="ui transparent circular label"> 10 </a>
								</span> <span class="right floated"> <i class="star icon"></i> Favorites <a
                                            class="ui transparent circular label"> 5 </a>
								</span>
                                    </div>
                                    <div class="ui two  bottom attached buttons">
                                        <div class="ui primary button">
                                            <i class="setting icon"></i> Settings
                                        </div>
                                        <div class="or"></div>
                                        <div class="ui button" onclick="$.lc4e.signOut()">
                                            <i class="sign out icon"></i>
                                            Sign Out
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </template>
                    <template v-else>
                        <div class="ui item animated fade button" v-link="'/SignUp'" v-show="enable">
                            <div class="visible content">Sign Up</div>
                            <div class="hidden content">
                                <i class="add user icon"></i>
                            </div>
                        </div>
                        <div class="ui item animated button" v-link="'/SignIn'" v-show="enable">
                            <div class="visible content">Sign In</div>
                            <div class="hidden content">
                                <i class="user icon"></i>
                            </div>
                        </div>
                    </template>
                    <div id="expendHeader" class="ui item hidden-mb" v-show="enable" v-on:click="menuExpend">
                        <div class="ui linked label">
                            <i class="maximize icon"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>


<script>
    module.exports = {
        name: "header-view",
        data: function () {
            return {
                themePath: this.$root.$data.themePath,
                menus: this.$root.$data.menus,
                fixed: false
            }
        },
        props: {
            menuExpened: {
                type: Boolean,
                default: false
            },
            rightMenuFloat: {
                type: Boolean,
                default: false
            },
            expended: {
                type: Boolean,
                default: false
            }
        },
        ready: function () {
            var that = this;
            var $menu = $('#menu'), $floatTool = $('#floatTools');

            $menu.find('.ui.dropdown.item').dropdown({
                action: "nothing",
                transition: "horizontal flip",
                on: 'click'
            });

            $('html').visibility({
                offset: -1,
                observeChanges: false,
                once: false,
                continuous: false,
                onTopPassed: function () {
                    $.requestAnimationFrame(function () {
                        that.fixed = true;
                        clearTimeout($floatTool.data('timer'));
                        $floatTool.data('timer', setTimeout(function () {
                            $floatTool.transition('fade left in');
                        }, 500));
                    })
                },
                onTopPassedReverse: function () {
                    $.requestAnimationFrame(function () {
                        that.fixed = false;
                        clearTimeout($floatTool.data('timer'));
                        $floatTool.data('timer', setTimeout(function () {
                            $floatTool.transition('fade left out');
                        }, 500));
                    });
                }
            });

            $('#userItem').find('img.ui.image').popup({
                position: 'bottom center',
                transition: "horizontal flip",
                popup: $('#userCardPop'),
                exclusive: false,
                hideOnScroll: false,
                on: 'click',
                closable: true,
                onVisible: function (model) {
                    if ($(window).width() <= 768)
                        $(model).popup('reposition');
                }
            });
        },
        methods: {
            expend: function (e) {
                this.expended = true;
            },
            collapse: function (e) {
                this.expended = false;
            },
            menuExpend: function (e) {
                var that = this;
                that.menuExpened = !that.menuExpened;
                if (that.menuExpened) {
                    that.rightMenuFloat = true;
                } else {
                    $('#menu').one('transitionend webkitTransitionEnd MSTransitionEnd oTransitionEnd', function () {
                        that.rightMenuFloat = false;
                    });
                }
            }
        },
        computed: {
            enable: function () {
                return this.menuExpened || !this.expended;
            }
        },
        components: {
            "menu-tree": require('../components/menu.vue')
        }
    }
</script>
