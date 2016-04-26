<template xmlns:v-on="http://www.w3.org/1999/xhtml" xmlns:v-form="http://www.w3.org/1999/xhtml">
    <div class="ui basic padding clearing segment flipInY animated">
        <h2 class="ui center aligned icon header">
            <i class="circular massive home icon animated allAnimation" :class="{  'scaleSpin': iconSpin }"
               v-on:mouseenter="addScalaSpin" v-on:mouseout="removeScalaSpin"></i>
            Sign in {{siteName}},Welcome Back!
        </h2>
        <div id="signInForm" class="ui form attached segment" observe-on="blur" data-url="/member/signin"
             data-loading="true">
            <div class="inline fields">
                <div class="four wide field">
                    <label class="fieldName">UserName</label>
                </div>
                <div class="ten wide field">
                    <div class="ui icon input">
                        <input id="user.name" class="fieldValue" name="user.name" type="text"
                               placeholder="your login name" data-rules="[{type:'empty'},{type:'minLength[4]'}]"/>
                        <i class="user icon"></i>
                    </div>
                </div>
            </div>
            <div class="inline fields">
                <div class="four wide field">
                    <label class="fieldName">Password</label>
                </div>
                <div class="ten wide field">
                    <div class="ui icon input">
                        <input id="user.password" name="user.password" class="fieldValue" :type="passwordInputType"
                               placeholder="your password" data-rules="[{type:'empty'},{type:'minLength[6]'}]"/>
                        <i class="eye icon link" v-on:mousedown="changeVisible" v-on:mouseup="changeInvisible"></i>
                    </div>
                </div>
            </div>
            <div class="inline fields" v-if="Captcha">
                <div class="four wide field">
                    <label class="fieldName">Verification Code</label>
                </div>
                <div class="six wide field">
                    <div class="ui input">
                        <input id="captcha" name="captcha" class="fieldValue" type="text"
                               data-rules="[{type:'empty'},{type:'exactLength[4]'}]"/>
                    </div>
                </div>
                <div class="four wide field">
                    <img id="captchaimg" :src="'/captcha?rand=timeLine' + timeLine" v-on:click="changeImg">
                </div>
            </div>
            <div class="inline fields">
                <div class="four wide field">
                    <label class="fieldName">Remember</label>
                </div>
                <div class="ten wide field">
                    <div class="ui toggle checkbox">
                        <input id="rememberMe" name="rememberMe" type="checkbox" class="fieldValue"
                               data-rules="[{type:'empty'}]">
                        <label>One Month</label>
                    </div>
                </div>
            </div>

            <div class="inline fields operatepanel">
                <div class="sixteen wide field ui centered grid">
                    <div class="ui buttons">
                        <button class="ui lc4eSubmit primary button">SignIn</button>
                        <div class="or"></div>
                        <button class="ui button lc4eReset">Reset</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="ui bottom attached warning message">
            <i class="warning icon"></i>
            <a>Lost password? </a> <a>Lost username?</a><a>Lost everyThing</a>
        </div>
    </div>
</template>
<script>
    module.exports = {
        name: 'SignIn',
        props: {
            iconSpin: {
                type: Boolean,
                default: false
            },
            passwordInputType: {
                type: String,
                default: 'password'
            },
            Captcha: {
                type: Boolean,
                default: false
            }
        },
        data: function () {
            return {
                siteName: this.$root.$data.siteName
            };
        },
        route: {
            data: function (transition) {
                this.$http.post('/SignIn').then(function (response) {
                    transition.next(response.data.data);
                })
            }
        },
        watch: {
            Captcha: function (val, oldVal) {
                if (val) {
                    $('#signInForm').Lc4eForm();
                }
            }
        },
        computed: {
            timeLine: function () {
                return new Date().getTime();
            }
        },
        methods: {
            changeImg: function (e) {
                var $captchaimg = $(e.target);
                if (!$captchaimg.transition('is animating')) {
                    $captchaimg.transition({
                        animation: 'vertical flip out',
                        onComplete: function () {
                            $captchaimg.attr('src', '/captcha?rand=' + new Date().getTime())
                                    .transition({
                                        animation: 'vertical flip in',
                                        displayType: false
                                    });
                        },
                        duration: '500ms',
                        displayType: false
                    })
                }
            },
            changeVisible: function (e) {
                this.passwordInputType = 'text';
            },
            changeInvisible: function (e) {
                this.passwordInputType = 'password';
            },
            addScalaSpin: function (e) {
                this.iconSpin = true;
            },
            removeScalaSpin: function (e) {
                this.iconSpin = false;
            }
        },

        ready: function () {


        }
    }
</script>