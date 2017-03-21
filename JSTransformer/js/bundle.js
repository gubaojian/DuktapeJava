/******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};
/******/
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/
/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId])
/******/ 			return installedModules[moduleId].exports;
/******/
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			i: moduleId,
/******/ 			l: false,
/******/ 			exports: {}
/******/ 		};
/******/
/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
/******/
/******/ 		// Flag the module as loaded
/******/ 		module.l = true;
/******/
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/
/******/
/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;
/******/
/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;
/******/
/******/ 	// identity function for calling harmony imports with the correct context
/******/ 	__webpack_require__.i = function(value) { return value; };
/******/
/******/ 	// define getter function for harmony exports
/******/ 	__webpack_require__.d = function(exports, name, getter) {
/******/ 		if(!__webpack_require__.o(exports, name)) {
/******/ 			Object.defineProperty(exports, name, {
/******/ 				configurable: false,
/******/ 				enumerable: true,
/******/ 				get: getter
/******/ 			});
/******/ 		}
/******/ 	};
/******/
/******/ 	// getDefaultExport function for compatibility with non-harmony modules
/******/ 	__webpack_require__.n = function(module) {
/******/ 		var getter = module && module.__esModule ?
/******/ 			function getDefault() { return module['default']; } :
/******/ 			function getModuleExports() { return module; };
/******/ 		__webpack_require__.d(getter, 'a', getter);
/******/ 		return getter;
/******/ 	};
/******/
/******/ 	// Object.prototype.hasOwnProperty.call
/******/ 	__webpack_require__.o = function(object, property) { return Object.prototype.hasOwnProperty.call(object, property); };
/******/
/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "";
/******/
/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(__webpack_require__.s = 2);
/******/ })
/************************************************************************/
/******/ ([
/* 0 */
/***/ (function(module, exports) {

module.exports = "{\n    \"scale1\": {\n        \"name\": \"scale1\",\n        \"fillMode\": \"both\",\n        \"motions\": [\n            {\n                \"name\": \"1\",\n                \"key\": \"scale\",\n                \"duration\": 400,\n                \"fromValue\": \"0\",\n                \"toValue\": \"1\"\n            }\n        ]\n    }\n}"

/***/ }),
/* 1 */
/***/ (function(module, exports) {

module.exports = "<html>\n    <style>\n        .container {\n        width:100%;\n        height:100%;\n        align-items:center;\n        justify-content:center\n        }\n\n        .message {\n        font-size:20;\n        font-weight:bold;\n        color:#3F51B5\n        }\n    </style>\n    <body>\n        <div class=\"container\">\n            <text class=\"message\">Hello, View+ !</text>\n        </div>\n    </body>\n</html>"

/***/ }),
/* 2 */
/***/ (function(module, exports, __webpack_require__) {

var layout =__webpack_require__(1);
var anim = __webpack_require__(0);

var module = {

};

document.write(anim);
document.write(layout);

// Flare.register(module);

/***/ })
/******/ ]);