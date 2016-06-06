var gulp = require('gulp');
var exec = require('sync-exec');


gulp.task('default', ['watch', 'convertJS']);



gulp.task('convertJS', function () {

});



gulp.task('watch', function () {
	var watcher = gulp.watch('dist/*.js');
	watcher.on('change', function(event) {
		var path = event.path;
		var paths = path.split("/");
		var name = paths[paths.length -1];
		var cmd  = "java -jar tools/CodeConvertor.jar " + path + " build/" + name;
		exec(cmd);
	  console.log('execute  ' + cmd  + ' success ');
		return false;
	});
});
