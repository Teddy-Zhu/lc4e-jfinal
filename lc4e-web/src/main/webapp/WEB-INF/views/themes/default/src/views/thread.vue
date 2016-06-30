<template>
    <div id="topicMaster" class="ui orange basic row one column segment">
        <div class="ui aligned center column">
            <div class="ui inverted statistic">
                <div class="topic title value">
                    Topic Title
                </div>
            </div>
        </div>
        <div class="ui aligned center column">
            <div class="ui large inverted statistic">
                <div class="ui middle aligned selection list topic time">
                    <div class="item">
                        <i class="send icon"></i>

                        <div class="content">
                            <div class="header">Posted: 2015-12-12</div>
                        </div>
                    </div>
                    <div class="item">
                        <i class="edit icon"></i>

                        <div class="content">
                            <div class="header">Last Modified: 2015-12-12</div>
                        </div>
                    </div>
                    <div class="item">
                        <i class="comment icon"></i>

                        <div class="content">
                            <div class="header">Last Commented:2015-12-12</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="topic" class="eleven wide column">
        <div id="mainArea" class="ui grid">
            <div class="three wide user info column overlay">

                <div class="ui fluid card">
                    <a class="ui blurring dimmable image">
                        <div class="ui dimmer">
                            <div class="content">
                                <div class="center">
                                    <div class="ui user operate vertical buttons">
                                        <div class="ui inverted yellow button">Star</div>
                                        <div class="ui inverted green button">Friend</div>
                                        <div class="ui inverted red button">Block</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <img src="/themes/default/images/avatar/steve.jpg">
                    </a>

                    <div class="content">
                        <a class="header">Daniel Louise</a>
                    </div>
                </div>

            </div>

            <div class="thirteen wide user content column">

                <div class="ui attached raised segments">
                    <article class="ui fluid segment topic content">
                        <a href="http://www.apple.com">Safari 5 released</a><br/>
                        7 Jun 2010. Just after the announcement of the new iPhone 4 at WWDC,
                        Apple announced the release of Safari 5 for Windows and Mac......
                    </article>
                </div>

                <div class="ui bottom attached clearing message">
                    <div class="ui left float labels">
                        <a class="ui tag label">New</a>
                        <a class="ui red tag label">Upcoming</a>
                        <a class="ui teal tag label">Featured</a>
                    </div>
                    <div id="topicOperateButton" class="ui right float icon top pointing dropdown button">
                        <i class="caret down icon"></i>

                        <div class="menu">
                            <div class="item">move</div>
                            <div class="item">hide</div>
                        </div>
                    </div>
                    <div class="ui right float buttons">
                        <div class="ui button">
                            <i class="folder icon"></i>
                            collect
                        </div>
                        <div class="ui red button">
                            <i class="hide icon"></i>
                            block
                        </div>
                        <div class="ui button">
                            <i class="reply icon"></i>
                            reply
                        </div>
                    </div>

                </div>
            </div>
        </div>
        <div id="commentArea" class="ui grid">
            <topic-detail :comments="comments"></topic-detail>
        </div>
        <div id="replyArea" class="ui grid" v-if="isLogin">
            <div class="one wide column word">
                <label>Reply</label>
            </div>
            <div class="fifteen wide column ui form">
                <div id="userCommentTextArea"></div>
            </div>
        </div>
    </div>
</template>

<script>
    require('../../../../../../themes/default/css/pages/topic.css');
    module.exports = {
        name: 'thread',
        data: function () {
            return {
                isLogin: this.$root.$data.isLogin,
                comments: []
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
        route: {
            data: function (transition) {
                var comments = [{
                    UserName: 'aaa',
                    body: '<a href="http://www.apple.com">Safari 5 released</a><br/>' +
                    '7 Jun 2010. Just after the announcement of the new iPhone 4 at WWDC,' +
                    'Apple announced the release of Safari 5 for Windows and Mac......'
                }, {
                    UserName: 'aaa',
                    body: '<a href="http://www.apple.com">Safari 5 released</a><br/>' +
                    '7 Jun 2010. Just after the announcement of the new iPhone 4 at WWDC,' +
                    'Apple announced the release of Safari 5 for Windows and Mac......'
                }, {
                    UserName: 'aaa',
                    body: '<a href="http://www.apple.com">Safari 5 released</a><br/>' +
                    '7 Jun 2010. Just after the announcement of the new iPhone 4 at WWDC,' +
                    'Apple announced the release of Safari 5 for Windows and Mac......'
                }, {
                    UserName: 'aaa',
                    body: '<a href="http://www.apple.com">Safari 5 released</a><br/>' +
                    '7 Jun 2010. Just after the announcement of the new iPhone 4 at WWDC,' +
                    'Apple announced the release of Safari 5 for Windows and Mac......'
                }, {
                    UserName: 'aaa',
                    body: '<a href="http://www.apple.com">Safari 5 released</a><br/>' +
                    '7 Jun 2010. Just after the announcement of the new iPhone 4 at WWDC,' +
                    'Apple announced the release of Safari 5 for Windows and Mac......'
                }, {
                    UserName: 'aaa',
                    body: '<a href="http://www.apple.com">Safari 5 released</a><br/>' +
                    '7 Jun 2010. Just after the announcement of the new iPhone 4 at WWDC,' +
                    'Apple announced the release of Safari 5 for Windows and Mac......'
                }, {
                    UserName: 'aaa',
                    body: '<a href="http://www.apple.com">Safari 5 released</a><br/>' +
                    '7 Jun 2010. Just after the announcement of the new iPhone 4 at WWDC,' +
                    'Apple announced the release of Safari 5 for Windows and Mac......'
                }, {
                    UserName: 'aaa',
                    body: '<a href="http://www.apple.com">Safari 5 released</a><br/>' +
                    '7 Jun 2010. Just after the announcement of the new iPhone 4 at WWDC,' +
                    'Apple announced the release of Safari 5 for Windows and Mac......'
                }, {
                    UserName: 'aaa',
                    body: '<a href="http://www.apple.com">Safari 5 released</a><br/>' +
                    '7 Jun 2010. Just after the announcement of the new iPhone 4 at WWDC,' +
                    'Apple announced the release of Safari 5 for Windows and Mac......'
                }];
                this.comments = comments;
                this.$nextTick(function () {
                    var $topicArea = $('#topic');
                    $topicArea.find('.ui.card .image').dimmer({
                        on: 'hover'
                    });
                    $('#mainArea').find('.overlay').visibility({
                        type: 'fixed',
                        offset: 80
                    });
                    $('#topicOperateButton').dropdown();
                });
//                this.$http.post('/t/' + this.$route.params.thread + "-" + this.sort + "-" + this.page).then(function (response) {
//                    console.log(response.data.data);
//                    transition.next(response.data.data);
//                })
            }
        },
        components: {
            "topic-detail": require('../components/topicDetail.vue')
        },
        ready: function () {

        }
    }
</script>