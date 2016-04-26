<template xmlns:v-on="http://www.w3.org/1999/xhtml">
    <div class="ui basic padding clearing segment flipInY animated">
        <h2 class="ui center aligned icon header">
            <i class="circular users icon  animated allAnimation" :class="{  'scaleSpin': iconSpin }"
               v-on:mouseenter="addScalaSpin" v-on:mouseout="removeScalaSpin"></i>
            Join in {{siteName}}
        </h2>
        <div id="signUpForm" class="ui form attached segment" observe-on="blur" data-url="/member/signup"
             data-loading="">
            <div class="inline fields">
                <div class="four wide field">
                    <label class="fieldName">UserName</label>
                </div>
                <div class="ten wide field">
                    <div class="ui icon input">
                        <input id="user.name" class="fieldValue" name="user.name" type="text"
                               placeholder="login name"
                               data-rules="[{type:'minLength[4]'},{type:'maxLength[12]'},{type:'remote[/su/user]'}]"/>
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
                               placeholder="password" data-rules="[{type:'minLength[6]'},{type:'maxLength[20]'}]"/>
                        <i class="eye icon link" v-on:mousedown="changeVisible" v-on:mouseup="changeInvisible"></i>
                    </div>
                </div>
            </div>
            <div class="inline fields">
                <div class="four wide field">
                    <label class="fieldName">Repeat Pass</label>
                </div>
                <div class="ten wide field">
                    <div class="ui icon input">
                        <input id="user.repassword"
                               name="user.repassword"
                               class="fieldValue"
                               :type="passwordInputType"
                               placeholder="repeat password"
                               data-ignore="true"
                               data-rules="[{type:'match[user.password]'}]"/>
                        <i class="eye icon link" v-on:mousedown="changeVisible" v-on:mouseup="changeInvisible"></i>
                    </div>
                </div>
            </div>
            <div class="inline fields">
                <div class="four wide field">
                    <label class="fieldName">Nick</label>
                </div>
                <div class="ten wide field">
                    <div class="ui icon input">
                        <input id="user.nick" name="user.nick" class="fieldValue" type="text"
                               placeholder="your nick" data-rules="[{type:'minLength[4]'},{type:'maxLength[12]'}]"/>
                        <i class="detective icon"></i>
                    </div>
                </div>
            </div>
            <div class="inline fields">
                <div class="four wide field">
                    <label class="fieldName">Email</label>
                </div>
                <div class="ten wide field">
                    <div class="ui icon input">
                        <input id="user.mail" name="user.mail" class="fieldValue" type="text"
                               placeholder="your email"
                               data-rules="[{type:'regExp[/\\b(^[\'_A-Za-z0-9-]+(\\.[\'_A-Za-z0-9-]+)*@([A-Za-z0-9-])+(\\.[A-Za-z0-9-]+)*((\\.[A-Za-z0-9]{2,})|(\\.[A-Za-z0-9]{2,}\\.[A-Za-z0-9]{2,}))$)\\b/]'}]"/>
                        <i class="at icon"></i>
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
                               data-rules="[{type:'exactLength[4]'}]"/>
                    </div>
                </div>
                <div class="four wide field">
                    <img id="captchaimg" :src="'/captcha?rand=timeLine' + timeLine" v-on:click="changeImg">
                </div>
            </div>
            <h4 class="ui horizontal divider header" v-if="!SimpleRegister">
                <div id="extendDes" class="linked" v-on:click="extendDescription"><i class="icon"
                                                                                     :class="{ 'add': !extendDesPanel ,'minus': extendDesPanel}"></i>
                    Description(optional)
                </div>
            </h4>
            <div id="extendPanel" style="display: none;" v-if="!SimpleRegister">
                <div class="inline fields">
                    <div class="four wide field">
                        <label class="fieldName">Phone</label>
                    </div>
                    <div class="ten wide field">
                        <div class="ui icon input">
                            <input id="extend.phoneNumber" name="extend.phoneNumber" class="fieldValue" type="text"
                                   placeholder="phone"
                                   data-rules="[{type:'regExp[/(^1[3|4|5|8|7]\\d{9}$)/]'}]" data-optional="true"/>
                            <i class="phone icon"></i>
                        </div>
                    </div>
                </div>
                <div class="inline fields">
                    <div class="four wide field">
                        <label class="fieldName">BirthDay</label>
                    </div>
                    <div class="ten wide field">
                        <div class="ui icon input">
                            <input id="extend.birth" name="user.birth" class="fieldValue" type="text"
                                   placeholder="birth day,it should be true" data-optional="true"
                                   data-rules="[{type:'regExp[/(^(\\d{4})-(0\\d{1}|1[0-2])-(0\\d{1}|[12]\\d{1}|3[01])$)/]'}]"/>
                            <i class="birthday icon"></i>
                        </div>
                    </div>
                </div>
                <div class="inline fields">
                    <div class="four wide field">
                        <label class="fieldName">Sign</label>
                    </div>
                    <div class="ten wide field">
                        <textarea id="extend.sign" name="user.sign" class="fieldValue" type="text"
                                  placeholder="personal sign" data-rules="[]" data-optional="true"></textarea>
                    </div>
                </div>
            </div>
            <div id="signUpAttached" class="inline fields operatepanel">
                <div class="sixteen wide field ui centered grid">
                    <div class="ui buttons">
                        <button class="ui labeled icon lc4eSubmit primary button">
                            <i class="add user icon"></i>
                            SignIn
                        </button>
                        <div class="or"></div>
                        <button class="ui right labeled icon button lc4eReset">
                            <i class="refresh icon"></i>
                            Reset
                        </button>
                    </div>
                </div>
            </div>
            <div class="ui error message"></div>
        </div>
        <div class="ui bottom attached positive message">
            <i class="warning icon"></i>
            Remember your account information , it's important
        </div>
    </div>

</template>
<style>

</style>
<script>
    module.exports = {
        name: 'SignUp',
        props: {
            iconSpin: {
                type: Boolean,
                default: false
            },
            extendDesPanel: {
                type: Boolean,
                default: false
            },
            Captcha: {
                type: Boolean,
                default: false
            },
            SimpleRegister: {
                type: Boolean,
                default: true
            },
            passwordInputType: {
                type: String,
                default: 'password'
            }
        },
        data: function () {
            return {
                isLogin: this.$root.$data.isLogin,
                siteName: this.$root.$data.siteName
            };
        },
        watch: {
            SimpleRegister: function (val, oldVal) {
                if (val) {
                    $('#signUpForm').Lc4eForm({
                        success: function () {
                            window.location.href = "/";
                        },
                        error: function () {
                        },
                        complete: function () {
                        }
                    });
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
            },
            extendDescription: function (e) {
                $('#extendPanel').transition('horizontal flip');
                this.extendDesPanel = !this.extendDesPanel;
            }
        },
        route: {
            data: function (transition) {
                this.$http.post('/SignUp').then(function (response) {
                    transition.next(response.data.data);
                })
            }
        },
        ready: function () {

            $('#extend\\.birth').Lc4eDateTimePicker({
                dayOfWeekStart: 1,
                timepicker: false,
                format: 'yyyy-MM-dd',
                showApplyButton: true
            });
        }
    }
</script>