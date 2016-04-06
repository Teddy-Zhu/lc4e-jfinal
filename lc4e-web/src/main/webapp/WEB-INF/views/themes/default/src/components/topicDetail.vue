<template>
    <template v-for="n in number">
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
                    UserName Posted: 2015-12-12
                    <a class="ui right float label">#{{n}}
                    </a>
                </div>
            </div>

            <div class="ui attached raised segments">
                <article class="ui fluid segment topic content">
                    <a href="http://www.apple.com">Safari 5 released</a><br/>
                    7 Jun 2010. Just after the announcement of the new iPhone 4 at WWDC,
                    Apple announced the release of Safari 5 for Windows and Mac......
                </article>
            </div>

            <div class="ui bottom attached grey clearing message">
                <div class="ui right float buttons">
                    <div class="ui primary button">
                        <i class="reply icon"></i>
                        reply
                    </div>
                </div>
            </div>
        </div>
    </template>
</template>
<script>
    module.exports = {
        name: 'topic-detail',
        props: {
            number: {
                type: Number,
                default: 10
            }
        }
    }
</script>