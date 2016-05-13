<style xmlns:v-on="http://www.w3.org/1999/xhtml" xmlns:v-on="http://www.w3.org/1999/xhtml">
    [v-cloak] {
        display: none;
    }
</style>
<template>
    <div id="menu" class="ui menu" v-bind:class="{ 'expended': menuExpened }">
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
                                   v-on:focus="expend" v-on:blur="collapse"/> <i
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
    <div id="content">
        <div id="mainContent" class="ui grid centered">
            <router-view></router-view>
        </div>
    </div>
    <div id="footer" class="ui inverted black footer vertical segment">
        <div class="container">
            <div class="ui stackable inverted divided relaxed grid">
                <div class="eight wide column">
                    <h3 class="ui inverted header">
                        {{siteName}} {{version}}
                    </h3>

                    <p>Designed By ZhuXi. Run with Tomcat8. Deploy:Jenkins.</p>

                    <p>Framework:Jfinal 2.O. UI:Semantic UI. Rendered:Jetbrick 2.x</p>

                    <form action="https://www.paypal.com/cgi-bin/webscr" method="post" target="_top"
                          style="display: inline;">
                        <input type="hidden" name="cmd" value="_s-xclick"> <input type="hidden" name="hosted_button_id"
                                                                                  value="7ZAF2Q8DBZAQL">
                        <button type="submit" class="ui teal button">Donate Semantic</button>
                    </form>
                    <div class="ui labeled button" tabindex="0">
                        <div class="ui red button">
                            <i class="heart icon"></i> Stars
                        </div>
                        <a class="ui basic red left pointing label">
                            1,048
                        </a>
                    </div>
                    <div class="ui labeled button" tabindex="0">
                        <div class="ui basic blue button">
                            <i class="fork icon"></i> Forks
                        </div>
                        <a class="ui basic left pointing blue label">
                            1,048
                        </a>
                    </div>
                </div>
                <div class="four wide column">
                    <h5 class="ui teal inverted header">Contributers</h5>

                    <div class="ui inverted link list">
                        <a class="item" href="http://www.eicoding.com/" target="_blank">ZhuXi</a>
                    </div>
                </div>
                <div class="four wide column">
                    <h5 class="ui teal inverted header">LC4E Network</h5>

                    <div class="ui inverted link list">
                        <a class="item"
                           href="https://www.linode.com/?r=9a43d7ae15699c5363209820309d8d6c68509f78">Linode</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    require('../../../../../themes/default/css/lc4e/jquery-extend.css');
    require('../../../../../themes/default/js/lc4e/jquery-extend.js');
    module.exports = {
        name: 'app',
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
        data: function () {
            return preLoadData;
        },
        ready: function () {
            $.lc4e.common.ready();
        },
        components: {
            "menu-tree": require('./components/menu.vue')
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
        }

    }
</script>