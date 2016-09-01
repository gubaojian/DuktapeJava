var os = require('os');
var gulp = require('gulp');
var exec = require('sync-exec');


gulp.task('default', ['showIp', 'watch', 'convertJS']);



gulp.task('showIp', function(){
	var IPv4, hostName;
	for(var i=0;i<os.networkInterfaces().en0.length;i++){
	    if(os.networkInterfaces().en0[i].family=='IPv4'){
	        IPv4=os.networkInterfaces().en0[i].address;
	    }
	}
	console.log('local IP: '+ IPv4);
	console.log('server: http://' + IPv4 + ":8888/build/index.js");

});

gulp.task('convertJS', function () {

});

gulp.task('watch', function () {
	var watcher = gulp.watch('dist/*.js');
	watcher.on('change', function(event) {
		var path = event.path;
		var paths = path.split("/");
		var name = paths[paths.length -1];
		var cmd  = "java -jar tools/JSTransformer.jar " + path + " build/" + name;
		exec(cmd);
	  console.log('execute  ' + cmd  + ' success ');
		return false;
	});
});
