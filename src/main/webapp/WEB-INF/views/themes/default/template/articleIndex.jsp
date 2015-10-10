<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="item">
	<div class="ui fluid tiny image hidden-mb">
		<div class="ui teal ribbon label">{#user#}</div>
		<img src="{#imageUrl#}" data-title="{# popUp.title #}" data-content="{# popUp.content #}">
	</div>
	<div class="content">
		<a class="header"><i class="red protect icon"></i>{# articleTitle #}</a>
		<div class="extra">
			<a class="ui blue label hidden-pc">{#user#}</a> <a class="ui label">{# category #}</a>
			<div class="ui transparent label">
				<i class="calendar icon"></i>{#publishTime#}
			</div>
			<div class="ui transparent label">
				<i class="comments outline icon"></i>{#comments#}
			</div>

			<div class="ui transparent label">
				<i class="comment icon"></i>Last Reply
			</div>
			<a class="ui label">{# lastCommentUser #}</a>
		</div>
	</div>
</div>
