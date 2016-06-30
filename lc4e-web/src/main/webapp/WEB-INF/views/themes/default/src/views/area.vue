<template xmlns:v-on="http://www.w3.org/1999/xhtml">
    <div class="ui teal  basic row column segment">
        <div class="ui large inverted statistic">
            <div class="value">
                <i class="info circle icon"></i> <br/>

                <div id="areaName" class="value" v-html="curArea" v-on:mouseover.stop="spin"></div>
            </div>
            <div id="areaDescription" class="label">
                Fill out the form below to sign-up for a new account
            </div>
        </div>
    </div>
    <div id="topicList" class="twelve wide column">
        <div id="areaSummery" class="ui attached floating message">
            <div class="ui row no padded clearing basic segment">
                <div class="ui left floated breadcrumb basic segment">
                    <a class="section" v-html="siteName"></a>
                    <span class="divider">/</span>

                    <div class="active section" v-html="curArea"></div>
                </div>
                <div id="areaLabel" class="ui labels">
                    <a class="ui tag mini label">New</a>
                    <a class="ui red mini tag label">Upcoming</a>
                    <a class="ui teal mini tag label">Featured</a>
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
            <div class="ui header">
                <div class="ui three statistics">
                    <div class="statistic">
                        <div class="value">
                            22
                        </div>
                        <div class="label">
                            Stars
                        </div>
                    </div>
                    <div class="statistic">
                        <div class="value">
                            31,200
                        </div>
                        <div class="label">
                            Topics
                        </div>
                    </div>
                    <div class="statistic">
                        <div class="value">
                            22
                        </div>
                        <div class="label">
                            Comments
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="topicItemsArea"
             class="ui divided items topiclist no padded attached raised segment"
             data-page="{{page}}"
             data-sort="{{topicSort}}">
            <topic-list :topics="topics" :page="page"></topic-list>
        </div>
        <div id="articlebottons" class="ui bottom clearing floating attached message">
            <div id="prePage" v-waves class="ui left floated basic labeled icon button" v-show="page>1"
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
</template>

<script>
    require('../../../../../../themes/default/css/pages/area.css');
    module.exports = {
        name: "area",
        data: function () {
            return {
                params: {},
                isLogin: this.$root.$data.isLogin,
                siteName: this.$root.$data.siteName,
                topics: [],
                curArea: '',
                showTopics: false
            };
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
        route: {
            data: function (transition) {
                var that = this;
                that.topics = [];
                that.$http.post('/a/' + that.area + "-" + that.sort + "-" + that.page).then(function (response) {
                    transition.next(response.data.data);
                })
            }
        },
        ready: function () {
            var that = this;
            var $topicItems = $('#topicItems'), $sortTopic = $('#sortTopic');
            $topicItems.find('.topicSetting').dropdown();
            $sortTopic.dropdown().dropdown('set selected', that.sort ? that.sort : $sortTopic.find('.scrolling.menu>.item:first').attr('data-value'))
        },
        watch: {
            page: function (val, oldVal) {
                if (val != oldVal && val > 0) {
                    var that = this;
                    $.Lc4eLoading({
                        title: "loading articles"
                    });
                    that.$router.go("/a/" + that.area + "-" + that.sort + "-" + that.page);
                    that.$nextTick(function () {
                        $.Lc4eLoading('hide');
                        $('body').animate({scrollTop: 0}, 500, 'easeOutQuad');
                    });
                }
            }
        },
        components: {
            "topic-list": require('../components/topicList.vue')
        },
        methods: {
            spin: function (e) {
                var $areaName = $(e.target);
                if (!$areaName.hasClass("animated")) {
                    $areaName.addClass('animated flip').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function () {
                        $areaName.removeClass('animated flip');
                    })
                }
            },
            nextPage: function () {
                this.page = this.page + 1;
            },
            prevPage: function () {
                this.page = this.page - 1;
            }
        },
        computed: {
            area: function () {
                return this.$route.params.area.split('-')[0];
            }
        }
    }
</script>