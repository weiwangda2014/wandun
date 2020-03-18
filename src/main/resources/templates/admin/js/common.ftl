/*
 * Copyright 2005-2015 wandun.net. All rights reserved.
 * Support: http://www.wandun.net
 * License: http://www.wandun.net/license
 * 
 * JavaScript - Common
 * Version: 4.0
 */

var wandun = {
	base: "${base}",
	locale: "${locale}",
	theme: "${theme}"
};

var setting = {
	priceScale: ${setting.priceScale},
	priceRoundType: "${setting.priceRoundType}",
	currencySign: "${setting.currencySign}",
	currencyUnit: "${setting.currencyUnit}",
	uploadMaxSize: ${setting.uploadMaxSize},
	uploadImageExtension: "${setting.uploadImageExtension}",
	uploadMediaExtension: "${setting.uploadMediaExtension}",
	uploadFileExtension: "${setting.uploadFileExtension}"
};

var messages = {
	"admin.message.success": "${message("admin.message.success")}",
	"admin.message.error": "${message("admin.message.error")}",
	"admin.dialog.ok": "${message("admin.dialog.ok")}",
	"admin.dialog.cancel": "${message("admin.dialog.cancel")}",
	"admin.dialog.deleteConfirm": "${message("admin.dialog.deleteConfirm")}",
	"admin.dialog.clearConfirm": "${message("admin.dialog.clearConfirm")}"
};

// 添加Cookie
function addCookie(name, value, options) {
	if (arguments.length > 1 && name != null) {
		if (options == null) {
			options = {};
		}
		if (value == null) {
			options.expires = -1;
		}
		if (typeof options.expires == "number") {
			var time = options.expires;
			var expires = options.expires = new Date();
			expires.setTime(expires.getTime() + time * 1000);
		}
		if (options.path == null) {
			options.path = "${setting.cookiePath}";
		}
		if (options.domain == null) {
			options.domain = "${setting.cookieDomain}";
		}
		document.cookie = encodeURIComponent(String(name)) + "=" + encodeURIComponent(String(value)) + (options.expires != null ? "; expires=" + options.expires.toUTCString() : "") + (options.path != "" ? "; path=" + options.path : "") + (options.domain != "" ? "; domain=" + options.domain : "") + (options.secure != null ? "; secure" : "");
	}
}

// 获取Cookie
function getCookie(name) {
	if (name != null) {
		var value = new RegExp("(?:^|; )" + encodeURIComponent(String(name)) + "=([^;]*)").exec(document.cookie);
		return value ? decodeURIComponent(value[1]) : null;
	}
}

// 移除Cookie
function removeCookie(name, options) {
	addCookie(name, null, options);
}

// Html转义
function escapeHtml(str) {
	return str.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;');
}

// 字符串缩略
function abbreviate(str, width, ellipsis) {
	if ($.trim(str) == "" || width == null) {
		return str;
	}
	var i = 0;
	for (var strWidth = 0; i < str.length; i++) {
		strWidth = /^[\u4e00-\u9fa5\ufe30-\uffa0]$/.test(str.charAt(i)) ? strWidth + 2 : strWidth + 1;
		if (strWidth >= width) {
			break;
		}
	}
	return ellipsis != null && i < str.length - 1 ? str.substring(0, i) + ellipsis : str.substring(0, i);
}

// 货币格式化
function currency(value, showSign, showUnit) {
	if (value != null) {
		[#if setting.priceRoundType == "roundUp"]
			var price = (Math.ceil(value * Math.pow(10, ${setting.priceScale})) / Math.pow(10, ${setting.priceScale})).toFixed(${setting.priceScale});
		[#elseif setting.priceRoundType == "roundDown"]
			var price = (Math.floor(value * Math.pow(10, ${setting.priceScale})) / Math.pow(10, ${setting.priceScale})).toFixed(${setting.priceScale});
		[#else]
			var price = (Math.round(value * Math.pow(10, ${setting.priceScale})) / Math.pow(10, ${setting.priceScale})).toFixed(${setting.priceScale});
		[/#if]
		if (showSign) {
			price = '${setting.currencySign}' + price;
		}
		if (showUnit) {
			price += '${setting.currencyUnit}';
		}
		return price;
	}
}

// 多语言
function message(code) {
	if (code != null) {
		var content = messages[code] != null ? messages[code] : code;
		if (arguments.length == 1) {
			return content;
		} else {
			if ($.isArray(arguments[1])) {
				$.each(arguments[1], function(i, n) {
					content = content.replace(new RegExp("\\{" + i + "\\}", "g"), n);
				});
				return content;
			} else {
				$.each(Array.prototype.slice.apply(arguments).slice(1), function(i, n) {
					content = content.replace(new RegExp("\\{" + i + "\\}", "g"), n);
				});
				return content;
			}
		}
	}
}

(function($) {

	var zIndex = 100;
	
	// 消息框
	var $message;
	var messageTimer;
	$.message = function() {
		var message = {};
		if ($.isPlainObject(arguments[0])) {
			message = arguments[0];
		} else if (typeof arguments[0] === "string" && typeof arguments[1] === "string") {
			message.type = arguments[0];
			message.content = arguments[1];
		} else {
			return false;
		}
		
		if (message.type == null || message.content == null) {
			return false;
		}
		
		if ($message == null) {
			$message = $('<div class="xxMessage"><div class="messageContent message' + escapeHtml(message.type) + 'Icon"><\/div><\/div>');
			if (!window.XMLHttpRequest) {
				$message.append('<iframe class="messageIframe"><\/iframe>');
			}
			$message.appendTo("body");
		}
		
		$message.children("div").removeClass("messagewarnIcon messageerrorIcon messagesuccessIcon").addClass("message" + message.type + "Icon").html(message.content);
		$message.css({"margin-left": - parseInt($message.outerWidth() / 2), "z-index": zIndex ++}).show();
		
		clearTimeout(messageTimer);
		messageTimer = setTimeout(function() {
			$message.hide();
		}, 3000);
		return $message;
	};
	
	// 对话框
	$.dialog = function(options) {
		var settings = {
			width: 320,
			height: "auto",
			modal: true,
			ok: '${message("admin.dialog.ok")}',
			cancel: '${message("admin.dialog.cancel")}',
			onShow: null,
			onClose: null,
			onOk: null,
			onCancel: null
		};
		$.extend(settings, options);
		
		if (settings.content == null) {
			return false;
		}
		
		var $dialog = $('<div class="xxDialog"><\/div>');
		var $dialogTitle;
		var $dialogClose = $('<div class="dialogClose"><\/div>').appendTo($dialog);
		var $dialogContent;
		var $dialogBottom;
		var $dialogOk;
		var $dialogCancel;
		var $dialogOverlay;
		if (settings.title != null) {
			$dialogTitle = $('<div class="dialogTitle"><\/div>').appendTo($dialog);
		}
		if (settings.type != null) {
			$dialogContent = $('<div class="dialogContent dialog' + escapeHtml(settings.type) + 'Icon"><\/div>').appendTo($dialog);
		} else {
			$dialogContent = $('<div class="dialogContent"><\/div>').appendTo($dialog);
		}
		if (settings.ok != null || settings.cancel != null) {
			$dialogBottom = $('<div class="dialogBottom"><\/div>').appendTo($dialog);
		}
		if (settings.ok != null) {
			$dialogOk = $('<input type="button" class="button" value="' + escapeHtml(settings.ok) + '" \/>').appendTo($dialogBottom);
		}
		if (settings.cancel != null) {
			$dialogCancel = $('<input type="button" class="button" value="' + escapeHtml(settings.cancel) + '" \/>').appendTo($dialogBottom);
		}
		if (!window.XMLHttpRequest) {
			$dialog.append('<iframe class="dialogIframe"><\/iframe>');
		}
		$dialog.appendTo("body");
		if (settings.modal) {
			$dialogOverlay = $('<div class="dialogOverlay"><\/div>').insertAfter($dialog);
		}
		
		var dragStart = {};
		var dragging = false;
		if (settings.title != null) {
			$dialogTitle.text(settings.title);
		}
		$dialogContent.html(settings.content);
		$dialog.css({"width": settings.width, "height": settings.height, "margin-left": - parseInt(settings.width / 2), "z-index": zIndex ++});
		dialogShow();
		
		if ($dialogTitle != null) {
			$dialogTitle.mousedown(function(event) {
				$dialog.css({"z-index": zIndex ++});
				var offset = $dialog.offset();
				dragStart.pageX = event.pageX;
				dragStart.pageY = event.pageY;
				dragStart.left = offset.left;
				dragStart.top = offset.top;
				dragging = true;
				return false;
			}).mouseup(function() {
				dragging = false;
			});
			
			$(document).mousemove(function(event) {
				if (dragging) {
					$dialog.offset({"left": dragStart.left + event.pageX - dragStart.pageX, "top": dragStart.top + event.pageY - dragStart.pageY});
					return false;
				}
			}).mouseup(function() {
				dragging = false;
			});
		}
		
		if ($dialogClose != null) {
			$dialogClose.click(function() {
				dialogClose();
				return false;
			});
		}
		
		if ($dialogOk != null) {
			$dialogOk.click(function() {
				if (settings.onOk && typeof settings.onOk == "function") {
					if (settings.onOk($dialog) != false) {
						dialogClose();
					}
				} else {
					dialogClose();
				}
				return false;
			});
		}
		
		if ($dialogCancel != null) {
			$dialogCancel.click(function() {
				if (settings.onCancel && typeof settings.onCancel == "function") {
					if (settings.onCancel($dialog) != false) {
						dialogClose();
					}
				} else {
					dialogClose();
				}
				return false;
			});
		}
		
		function dialogShow() {
			if (settings.onShow && typeof settings.onShow == "function") {
				if (settings.onShow($dialog) != false) {
					$dialog.show();
					$dialogOverlay.show();
				}
			} else {
				$dialog.show();
				$dialogOverlay.show();
			}
		}
		
		function dialogClose() {
			if (settings.onClose && typeof settings.onClose == "function") {
				if (settings.onClose($dialog) != false) {
					$dialogOverlay.remove();
					$dialog.remove();
				}
			} else {
				$dialogOverlay.remove();
				$dialog.remove();
			}
		}
		return $dialog;
	};
	
	$.fn.extend({
		// 文件上传
		uploader: function(options) {
			var settings = {
				url: '${base}/admin/file/upload.jhtml',
				fileType: "image",
				fileName: "file",
				data: {},
				maxSize: ${setting.uploadMaxSize},
				extensions: null,
				before: null,
				complete: null
			};
			$.extend(settings, options);
			
			if (settings.extensions == null) {
				switch(settings.fileType) {
					case "media":
						settings.extensions = '${setting.uploadMediaExtension}';
						break;
					case "file":
						settings.extensions = '${setting.uploadFileExtension}';
						break;
					default:
						settings.extensions = '${setting.uploadImageExtension}';
				}
			}
			
			var $progressBar = $('<div class="progressBar"><\/div>').appendTo("body");
			return this.each(function() {
				var element = this;
				var $element = $(element);
				
				var webUploader = WebUploader.create({
					swf: '${base}/resources/admin/flash/webuploader.swf',
					server: settings.url + (settings.url.indexOf('?') < 0 ? '?' : '&') + 'fileType=' + settings.fileType + '&token=' + getCookie("token"),
					pick: {
						id: element,
						multiple: false
					},
					fileVal: settings.fileName,
					formData: settings.data,
					fileSingleSizeLimit: settings.maxSize * 1024 * 1024,
					accept: {
						extensions: settings.extensions
					},
					fileNumLimit: 1,
					auto: true
				}).on('beforeFileQueued', function(file) {
					if ($.isFunction(settings.before) && settings.before.call(element, file) === false) {
						return false;
					}
					if ($.trim(settings.extensions) == '') {
						this.trigger('error', 'Q_TYPE_DENIED');
						return false;
					}
					this.reset();
					$progressBar.show();
				}).on('uploadProgress', function(file, percentage) {
					$progressBar.width(percentage * 100 + '%');
				}).on('uploadAccept', function(file, data) {
					$progressBar.fadeOut("slow", function() {
						$progressBar.width(0);
					});
					if (data.message.type != 'success') {
						$.message(data.message);
						return false;
					}
					$element.prev("input:text").val(data.url);
					if ($.isFunction(settings.complete)) {
						settings.complete.call(element, file, data);
					}
				}).on('error', function(type) {
					switch(type) {
						case "F_EXCEED_SIZE":
							$.message("warn", "${message("admin.upload.sizeInvalid")}");
							break;
						case "Q_TYPE_DENIED":
							$.message("warn", "${message("admin.upload.typeInvalid")}");
							break;
						default:
							$.message("warn", "${message("admin.upload.error")}");
					}
				});
				
				$element.mouseover(function() {
					webUploader.refresh();
				});
			});
		},
		
		// 编辑器
		editor: function(options) {
			window.UEDITOR_CONFIG = {
				UEDITOR_HOME_URL: '${base}/resources/admin/ueditor/',
				serverUrl: '${base}/admin/file/upload.jhtml',
				imageActionName: "uploadImage",
				imageFieldName: "file",
				imageMaxSize: ${setting.uploadMaxSize } * 1024 * 1024,
				imageAllowFiles: [[#list setting.uploadImageExtensions as extension]'.${extension}'[#if extension_has_next], [/#if][/#list]],
				imageUrlPrefix: "",
				imagePathFormat: "",
				imageCompressEnable: false,
				imageCompressBorder: 1600,
				imageInsertAlign: "none",
				videoActionName: "uploadMedia",
				videoFieldName: "file",
				videoMaxSize: ${setting.uploadMaxSize} * 1024 * 1024,
				videoAllowFiles: [[#list setting.uploadMediaExtensions as extension]'.${extension}'[#if extension_has_next], [/#if][/#list]],
				videoUrlPrefix: "",
				videoPathFormat: "",
				fileActionName: "uploadFile",
				fileFieldName: "file",
				fileMaxSize: ${setting.uploadMaxSize} * 1024 * 1024,
				fileAllowFiles: [[#list setting.uploadFileExtensions as extension]'.${extension}'[#if extension_has_next], [/#if][/#list]],
				fileUrlPrefix: "",
				filePathFormat: "",
				toolbars: [[
					'fullscreen', 'source', '|',
					'undo', 'redo', '|',
					'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|',
					'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
					'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
					'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
					'directionalityltr', 'directionalityrtl', 'indent', '|',
					'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|',
					'touppercase', 'tolowercase', '|',
					'link', 'unlink', 'anchor', '|',
					'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
					'insertimage', 'insertvideo', 'attachment', 'map', 'insertframe', 'pagebreak', '|',
					'horizontal', 'date', 'time', 'spechars', '|',
					'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows', 'splittocols', '|',
					'print', 'preview', 'searchreplace', 'drafts'
				]],
				lang: '${locale}',
				iframeCssUrl: null,
				pageBreakTag: 'wandun_page_break_tag',
				wordCount: false
			};
			
			UE.Editor.prototype.getActionUrl = function(action) {
				var serverUrl = this.getOpt('serverUrl');
				switch(action) {
					case "uploadImage":
						return serverUrl + (serverUrl.indexOf('?') < 0 ? '?' : '&') + 'fileType=image';
					case "uploadMedia":
						return serverUrl + (serverUrl.indexOf('?') < 0 ? '?' : '&') + 'fileType=media';
					case "uploadFile":
						return serverUrl + (serverUrl.indexOf('?') < 0 ? '?' : '&') + 'fileType=file';
				}
				return null;
			};
			
			UE.Editor.prototype.loadServerConfig = function() {
				this._serverConfigLoaded = true;
			};
			
			return this.each(function() {
				var element = this;
				var $element = $(element);
				
				UE.getEditor($element.attr("id"), options).ready(function() {
					this.execCommand("serverparam", {
						token: getCookie("token")
					});
				});
			});
		}
	
	});

})(jQuery);

$().ready(function() {

	// AJAX全局设置
	$.ajaxSetup({
		traditional: true
	});
	
	// 令牌
	$(document).ajaxSend(function(event, request, settings) {
		if (!settings.crossDomain && settings.type != null && settings.type.toLowerCase() == "post") {
			var token = getCookie("token");
			if (token != null) {
				request.setRequestHeader("token", token);
			}
		}
	});
	
	// 令牌
	$("form").submit(function() {
		var $this = $(this);
		if ($this.attr("method") != null && $this.attr("method").toLowerCase() == "post" && $this.find("input[name='token']").size() == 0) {
			var token = getCookie("token");
			if (token != null) {
				$this.append('<input type="hidden" name="token" value="' + token + '" \/>');
			}
		}
	});
	
	// 状态
	$(document).ajaxComplete(function(event, request, settings) {
		var tokenStatus = request.getResponseHeader("tokenStatus");
		var validateStatus = request.getResponseHeader("validateStatus");
		var loginStatus = request.getResponseHeader("loginStatus");
		if (tokenStatus == "accessDenied") {
			var token = getCookie("token");
			if (token != null) {
				$.extend(settings, {
					global: false,
					headers: {token: token}
				});
				$.ajax(settings);
			}
		} else if (validateStatus == "accessDenied") {
			$.message("warn", "${message("admin.validate.illegal")}");
		} else if (loginStatus == "accessDenied") {
			$.message("warn", "${message("admin.login.accessDenied")}");
			setTimeout(function() {
				location.reload(true);
			}, 2000);
		} else if (loginStatus == "unauthorized") {
			$.message("warn", "${message("admin.unauthorized.message")}");
		}
	});

});

// 验证
if ($.validator != null) {

	$.extend($.validator.messages, {
		required: '${message("admin.validate.required")}',
		email: '${message("admin.validate.email")}',
		url: '${message("admin.validate.url")}',
		date: '${message("admin.validate.date")}',
		dateISO: '${message("admin.validate.dateISO")}',
		pointcard: '${message("admin.validate.pointcard")}',
		number: '${message("admin.validate.number")}',
		digits: '${message("admin.validate.digits")}',
		minlength: $.validator.format('${message("admin.validate.minlength")}'),
		maxlength: $.validator.format('${message("admin.validate.maxlength")}'),
		rangelength: $.validator.format('${message("admin.validate.rangelength")}'),
		min: $.validator.format('${message("admin.validate.min")}'),
		max: $.validator.format('${message("admin.validate.max")}'),
		range: $.validator.format('${message("admin.validate.range")}'),
		accept: '${message("admin.validate.accept")}',
		equalTo: '${message("admin.validate.equalTo")}',
		remote: '${message("admin.validate.remote")}',
		integer: '${message("admin.validate.integer")}',
		positive: '${message("admin.validate.positive")}',
		negative: '${message("admin.validate.negative")}',
		decimal: '${message("admin.validate.decimal")}',
		pattern: '${message("admin.validate.pattern")}',
		extension: '${message("admin.validate.extension")}'
	});
	
	$.validator.setDefaults({
		errorClass: "fieldError",
		ignore: ".ignore",
		ignoreTitle: true,
		errorPlacement: function(error, element) {
			var fieldSet = element.closest("span.fieldSet");
			if (fieldSet.size() > 0) {
				error.appendTo(fieldSet);
			} else {
				error.insertAfter(element);
			}
		},
		submitHandler: function(form) {
			$(form).find("input:submit").prop("disabled", true);
			form.submit();
		}
	});

}