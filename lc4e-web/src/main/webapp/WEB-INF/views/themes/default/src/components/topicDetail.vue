<template>
    <div class="row" v-for="(index, comment) in comments" style="display: none;">
        <div class="two wide user info column">
            <div class="ui stackable centered card">
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
            </div>
        </div>
        <div class="fourteen wide user content column">
            <div class="ui comment title attached message">
                <div class="ui basic comment clearing title segment row">
                    {{comment.UserName}} Posted: 2015-12-12
                    <a class="ui right float label">#{{index}}
                    </a>
                </div>
            </div>

            <div class="ui attached raised segments">
                <article class="ui fluid segment topic content">
                    {{comment.body}}
                </article>
            </div>

            <div class="ui bottom attached grey clearing message">
                <div class="ui right float buttons">
                    <div class="ui basic primary button">
                        <i class="reply icon"></i>
                        reply
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
    module.exports = {
        name: 'topic-detail',
        props: {
            comments: {
                type: Array,
                required: true,
                default: []
            },
            animate: {
                type: String,
                default: 'fade left'
            },
            duration: {
                type: Number,
                default: 300
            },
            interval: {
                type: Number,
                default: 60
            }
        },
        watch: {
            comments: function (val, oldVal) {
                if (val.length !== 0) {
                    var that = this;
                    that.$nextTick(function () {
                        $('>.row', that.$el.parentNode).transition({
                            animation: that.animate + ' in',
                            duration: that.duration,
                            interval: that.interval
                        });
                    })
                }
            }
        }
    }
</script>