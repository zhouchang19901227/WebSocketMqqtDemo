String.prototype.format = function() {
	if (arguments.length == 0)
        return null;
    var str = this;
    for (var i = 0; i < arguments.length; i++) {
        var re = new RegExp('\\{' + i + '\\}', 'gm');
        str = str.replace(re, arguments[i]);
    }
    return str;
};

	var subscribeStr = {
		GPS   : "OBD/RT/{0}/GPS",
		STATE : "OBD/RT/{0}/STATE",
		ALARM : "OBD/RT/{0}/ALARM",
	};
	/**
	 * websocket通信
	 */
var websocket = {
		//实时监控订阅初始化 
		init : function() {
			HtWebSocket.init({
				onmessage : function(message) {
					alert(message);
					if(!message.data) return;
					dataArr = eval("(" + message.data + ")");
				
				}
			});
			websocketLoaded = true;
		},
		//获取通用订阅消息串
		getSubscribeTopics : function(id) {
			//var id = car.deviceSn, topicsArr=[];
			if(id) {
				topicsArr.push(subscribeStr.GPS.format(id));
				topicsArr.push(subscribeStr.STATE.format(id));
				topicsArr.push(subscribeStr.ALARM.format(id));
				return topicsArr.join(";");
			}
		},
		//通用订阅
		subscribe : function(car) {
			var topics = this.getSubscribeTopics(car);
			!topics || HtWebSocket.send("Subscribe:" + topics);
		},
		//通用取消订阅
		unsubscribe : function(car) {
			var topics = this.getSubscribeTopics(car);
			!topics || HtWebSocket.send("Unsubscribe:" + topics);
		}
	};