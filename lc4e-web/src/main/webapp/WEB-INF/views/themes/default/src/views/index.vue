<template xmlns:v-on="http://www.w3.org/1999/xhtml">
    <div id="leftContent" class="nine wide column">
        <div id="announcement" class="ui white floating message">
            <div class="item">
                <div class="ui white label">
                    <i class="announcement icon"></i>
                </div>
                <div id="announce" class="ui text shape">
                    <div class="sides">
                        <div class="active ui header side">Did you know? This side starts visible.</div>
                        <div class="ui header side">Help, its another side!</div>
                        <div class="ui header side">This is the last side</div>
                    </div>
                </div>
            </div>
        </div>
        <div id="attachedHeader" class="ui attached floating clearing message" data-area="index">
            <div class="ui left floated breadcrumb basic segment">
                <a class="section">
                    {{siteName}}
                </a>
                <span class="divider">/</span>
                <a class="section">Registration</a>
                <span class="divider">/</span>

                <div class="active section">Personal Information</div>
            </div>
            <div id="sortTopic" class="ui dropdown labeled icon basic button">
                <i class="filter icon"></i>
                <span class="text">Sort</span>

                <div class="menu">
                    <div class="scrolling menu">
                        <template v-if="isLogin">
                            <div class="item" data-value="1">
                                <div class="ui red empty circular label"></div>
                                Order By System
                            </div>
                        </template>
                        <div class="item" data-value="2">
                            <div class="ui blue empty circular label"></div>
                            Order By Date
                        </div>
                        <div class="item" data-value="3">
                            <div class="ui black empty circular label"></div>
                            Order By Last Reply
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="articlelist" class="ui attached fluid raised segment">
            <div id="topicItems" class="ui divided items topiclist" data-page="{{page}}"
                 data-sort="{{sort}}">
                <topic-list :topics="topics" :page="page"></topic-list>
            </div>
        </div>
        <div id="articlebottons" class="ui bottom attached floating message">
            <div id="prePage" v-waves
                 class="ui left floated basic labeled icon button" v-show="page>1"
                 v-on:click="prevPage">
                <i class="angle double left icon"></i>
                Prev
            </div>
            <div id="nextPage" v-waves class="ui right floated basic right labeled icon button" v-on:click="nextPage">
                <i class="angle double right icon"></i>
                Next
            </div>
        </div>
    </div>
    <div id="rightContent" class="three wide column animated fadeInRightTiny">
        <div id="todayHot" class="ui raised segment">
            <h4 class="ui horizontal header divider">
                <i class="bar chart icon"></i> Today HotSpot
            </h4>
            <div class="ui divided items"></div>
        </div>
        <div id="yesterdayHot" class="ui raised segment">
            <h4 class="ui horizontal header divider">
                <i class="bar chart icon"></i> Yesterday HotSpot
            </h4>

            <div class="ui divided items"></div>
        </div>
        <div class="ui vertical rectangle test ad" data-text="Advertisement"></div>
    </div>
</template>
<script type="text/javascript">
    require('../../../../../../themes/default/css/pages/index.css');
    module.exports = {
        name: 'index',
        data: function () {
            return {
                isLogin: this.$root.$data.isLogin,
                siteName: this.$root.$data.siteName,
                themePath: this.$root.$data.themePath,
                topics: []
            }
        },
        props: {
            page: {
                type: Number,
                default: 1
            },
            sort: {
                type: Number,
                default: 2
            }
        },
        components: {
            "topic-list": require('../components/topicList.vue')
        },
        route: {
            data: function (transition) {
                var that = this;
                that.topics = [];
                that.$http.post('/a/all' + "-" + that.sort + "-" + that.page).then(function (response) {
                    transition.next(response.data.data);
                })
            }
        },
        ready: function () {
            var that = this, announce = $('#announce'), sortTopic = $('#sortTopic');
            announce.shape();

            function shapTime() {
                announce.shape('flip down');
                setTimeout(shapTime, 10000);
            };
            shapTime();
            sortTopic.dropdown().dropdown('set selected', that.sort ? that.sort : sortTopic.find('.scrolling.menu>.item:first').attr('data-value'));
            $('#topicItems').find('.topicSetting').dropdown();
        },
        watch: {
            page: function (val, oldVal) {
                if (val != oldVal && val > 0) {
                    var that = this;
                    $.Lc4eLoading({
                        title: "loading articles"
                    });
                    that.topics = [];
                    that.$http.post('/a/all' + "-" + that.sort + "-" + that.page).then(function (response) {
                        that.topics = response.data.data["topics"];
                    });

                    that.$nextTick(function () {

                        $.Lc4eLoading('hide');
                    });
                }
            }
        },
        methods: {
            asas: function () {

            },
            nextPage: function () {
                this.page = this.page + 1;
            },
            prevPage: function () {
                this.page = this.page - 1;
            }
        }
    }
</script>