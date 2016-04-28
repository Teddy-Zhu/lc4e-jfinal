<template xmlns:v-on="http://www.w3.org/1999/xhtml">
    <div class="item topic" v-for="topic in topics" track-by="$index" v-cloak style="display: none">
        <div class="ui user picture hidden-mb">
            <div class="ui fluid tiny image hidden-mb">
                <img :src="topic.imageUrl" data-title="{{topic.popUp.title}}"
                     data-content="{{topic.popUp.content}}"/>
            </div>
        </div>
        <div class="content" v-link="topic.articleUrl">
            <a class="header larger" v-link="topic.articleUrl">
                {{topic.articleTitle}}
            </a>
            <div class="extra">
                <a class="ui blue label" v-on:click.stop>
                    {{topic.user}}
                </a>
                <a class="ui teal label" v-on:click.stop>
                    {{topic.category}}
                </a>

                <div class="ui transparent label" v-on:click.stop>
                    <i class="calendar icon"></i>
                    {{topic.publishTime}}
                </div>
                <div class="ui transparent label" v-on:click.stop>
                    <i class="comments outline icon"></i>
                    {{topic.comments}}
                </div>
                <div class="ui transparent label" v-on:click.stop>
                    <i class="comment icon"></i>
                    <a class="ui label">
                        {{topic.lastCommentUser}}
                    </a>
                </div>
                <div class="topicQuickTools" v-on:click.stop>
                    <a class="ui circular topicQuickTool label"><i class="ui yellow star icon"></i></a>
                    <a class="ui circular topicQuickTool topicSetting bottom left dropdown pointing label">
                        <i class="ui red setting icon"></i>

                        <div class="menu">
                            <div class="item">Move</div>
                            <div class="item">Delete</div>
                            <div class="item">Edit</div>
                        </div>
                    </a>
                </div>
            </div>
        </div>
        <div class="ui red top right attached label status" v-if="topic.statusText && topic.statusText[0]">
            {{topic.statusText[0]}}
        </div>
    </div>
</template>

<script>
    module.exports = {
        name: 'topic-list',
        props: {
            topics: {
                type: Array,
                default: []
            },
            page: {
                type: Number,
                required: true
            },
            animate: {
                type: String,
                default: 'fade up'
            },
            duration: {
                type: Number,
                default: 200
            },
            interval: {
                type: Number,
                default: 60
            }
        },
        watch: {
            topics: function (val, oldVal) {
                if (val.length !== 0) {
                    var that = this;
                    that.$nextTick(function () {
                        $('>.item', that.$el.parentNode).transition({
                            animation: that.animate + ' in',
                            duration: that.duration,
                            interval: that.interval
                        });
                        $('.topicSetting', that.$el.parentNode).dropdown();
                        $('>.item .ui.fluid.image img', that.$el.parentNode).popup();
                    })
                }
            }
        }
    }
</script>