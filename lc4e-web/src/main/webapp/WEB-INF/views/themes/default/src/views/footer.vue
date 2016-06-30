<template>
    <div id="footer" class="ui inverted black footer vertical segment" :class="{ 'fixed' : fixed }">
        <div class="container">
            <div class="ui stackable inverted divided relaxed grid">
                <div class="eight wide column">
                    <h3 class="ui inverted header">
                        {{siteName}} {{version}}
                    </h3>

                    <p>Designed By ZhuXi. Run with Tomcat8. Deploy:Jenkins.</p>

                    <p>Framework:Jfinal 2.1. UI:Semantic UI. Rendered:Jetbrick 2.x</p>

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
                        <a class="item" href="/" target="_blank">ZhuXi</a>
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
    module.exports = {
        name: 'footer-view',
        data: function () {
            return {
                siteName: this.$root.$data.siteName,
                version: this.$root.$data.version
            }
        }
    }
</script>
