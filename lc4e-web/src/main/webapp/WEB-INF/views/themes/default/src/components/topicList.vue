<template>
    <div class="item topic" v-for="topic in topics" track-by="$index" transition="fade"
         stagger="85">
        <div class="ui user picture">
            <div class="ui fluid tiny image hidden-mb">
                <img :src="topic.imageUrl" data-title="{{topic.popUp.title}}"
                     data-content="{{topic.popUp.content}}"/>
            </div>
        </div>
        <div class="content">
            <a class="header larger" href="{{topic.articleUrl}}">
                {{topic.articleTitle}}
            </a>
            <div class="extra">
                <a class="ui blue label">
                    {{topic.user}}
                </a>
                <a class="ui teal label">
                    {{topic.category}}
                </a>

                <div class="ui transparent label">
                    <i class="calendar icon"></i>
                    {{topic.publishTime}}
                </div>
                <div class="ui transparent label">
                    <i class="comments outline icon"></i>
                    {{topic.comments}}
                </div>
                <div class="ui transparent label">
                    <i class="comment icon"></i>
                    <a class="ui label">
                        {{topic.lastCommentUser}}
                    </a>
                </div>
                <div class="topicQuickTools">
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
                type: Array
            },
            page: {
                type: Number
            }
        }
    }
</script>