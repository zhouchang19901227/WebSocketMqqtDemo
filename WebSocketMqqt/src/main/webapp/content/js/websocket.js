

/**
 * websocket通信脚本库
 * 
 * 
 * @创建人 zc
 * @创建时间 2017年3月1日 上午9:37:05
 * @修改人
 * @修改时间
 */
HtWebSocket = (function() {

	var url = "http://localhost:8081/WebSocketMqqt/gps/sockjs/test";
	var sock;
	
	/**
	 * 初始化websocket
	 */
	var init = function() {

		sock = new SockJS(url);
		//sock = new WebSocket("ws://" + window.location.host + ctx + "/gps/realtime");

		sock.onopen = function() {
			!console || console.log("WebSocket connection opened！");
			setInterval(function() {
				sock.send("ping");
			}, 30000);
		};

		sock.onclose = function() {
			//!options.onclose || options.onclose();
			!console || console.log("WebSocket connection closed！");
		};

		sock.onmessage = function(message) {
			console.log(message , message.data);
			//!options.onmessage || options.onmessage(message);
			!console || console.log(message.data);
		};
        
		return sock;
	}
	
	return {
		init : function() {
			try {
				return init();
			}
			catch(e) {
			}
		},
		send : function(topic) {
			try {
				sock.send(topic);
			}
			catch(e) {
			}
		}
	};

})();