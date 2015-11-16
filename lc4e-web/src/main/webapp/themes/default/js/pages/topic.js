/**
 * Created by teddy on 2015/10/13.
 */
$(function () {
    $.lc4e.topic = {
        ready: function () {
            $.lc4e.topic.run();
            $.lc4e.topic.bindEvent();
        },
        run: function () {

        },
        bindEvent: function () {
            var $topicArea = $('#topic');
            $topicArea.find('.ui.card .image').dimmer({
                on: 'hover'
            });
            $('#topicOperateButton').dropdown();
            if ($('#userCardPop').length > 0) {
                var editor = editormd("userCommentTextArea", {
                    path: "/themes/default/js/plugins/editor.md/lib/",
                    height: 400,
                    watch: false,
                    emoji: true,
                    autoFocus: false,
                    placeholder: 'input your reply!',
                    editorTheme: 'base16-light',
                    toolbarIcons: function () {
                        return ['undo', 'redo', 'bold', 'del', 'italic', 'quote', 'uppercase', 'h1', 'h2', 'h3', 'h4', 'h5',
                            'h6', "list-ul", "list-ol", 'hr', 'link', "reference-link", 'image', 'code', "preformatted-text", "code-block", 'table', 'datetime', 'emoji', "html-entities", 'pagebreak', 'watch']
                    }
                });
            }

        }
    };
    $.lc4e.topic.ready();
})
;