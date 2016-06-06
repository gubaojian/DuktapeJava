/******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};

/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {

/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId])
/******/ 			return installedModules[moduleId].exports;

/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			exports: {},
/******/ 			id: moduleId,
/******/ 			loaded: false
/******/ 		};

/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);

/******/ 		// Flag the module as loaded
/******/ 		module.loaded = true;

/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}


/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;

/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;

/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "";

/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(0);
/******/ })
/************************************************************************/
/******/ ([
/* 0 */
/***/ function(module, exports, __webpack_require__) {

	"use strict";

	var a = "hello world";
	var demoXml = __webpack_require__(1);

	console.log(a);

	console.log(demoXml);

/***/ },
/* 1 */
/***/ function(module, exports) {

	module.exports = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<View\n    y =\"20\"\n    width=\"320\"\n    height=\"2000\"\n    screenUnit=\"320\">\n\n\n    <View\n        width=\"320\"\n        height=\"600\">\n        <ImageView width=\"300\"\n            height=\"108\"\n            x=\"12\"\n            imageUrl=\"http://images.apple.com/cn/itunespromos/itunes-this-week/images/2015/10/e5bb7c30-90cc-9363-d830-138583dd01b8/flowcase_680_260_1x.jpg\" />\n\n\n        <EditText\n            width=\"300\"\n            height=\"40\"\n            y=\"120\"\n            x=\"10\"\n            borderWidth=\"2\"\n            borderColor=\"#666666\"\n            cornerRadius=\"20\"\n            requestFocus=\"false\"/>\n\n\n        <EditText\n            width=\"300\"\n            height=\"80\"\n            y=\"168\"\n            x=\"10\"\n            background=\"#88FFFF\"\n            numberOfLines=\"4\"\n            requestFocus=\"false\"\n            textAlign=\"left\"\n            verticalAlign=\"top\"/>\n        <Button\n            width=\"300\"\n            height=\"100\"\n            y=\"260\"\n            x=\"10\"\n            background=\"#444433\"\n            selectBackground=\"#989898\"\n            text=\"无微不至\"\n            textColor=\"#FFFFFF\"\n            textSize=\"24\"/>\n\n\n        <View\n            width=\"72\"\n            height=\"200\"\n            y=\"372\"\n            x=\"8\">\n            <ImageView\n                width=\"64\"\n                height=\"64\"\n                y=\"8\"\n                x=\"8\"\n                imageUrl=\"http://images.apple.com/cn/itunespromos/itunes-this-week/images/2015/10/e5bb7c30-90cc-9363-d830-138583dd01b8/flowcase_680_260_1x.jpg\"\n                />\n            <TextView\n                width=\"64\"\n                height=\"24\"\n                y=\"72\"\n                x=\"8\"\n                text=\"网商\"\n                textSize=\"14\"/>\n            <TextView\n                width=\"64\"\n                height=\"24\"\n                y=\"90\"\n                x=\"8\"\n                text=\"无微不至\"\n                textColor=\"#888888\"\n                textSize=\"12\"/>\n        </View>\n\n\n        <View\n            width=\"72\"\n            height=\"200\"\n            y=\"372\"\n            x=\"84\">\n            <ImageView\n                width=\"64\"\n                height=\"64\"\n                y=\"8\"\n                x=\"8\"\n                imageUrl=\"http://images.apple.com/cn/itunespromos/itunes-this-week/images/2015/10/e5bb7c30-90cc-9363-d830-138583dd01b8/flowcase_680_260_1x.jpg\"\n                />\n            <TextView\n                width=\"64\"\n                height=\"24\"\n                y=\"72\"\n                x=\"8\"\n                text=\"支付宝\"\n                textSize=\"14\"/>\n            <TextView\n                width=\"64\"\n                height=\"24\"\n                y=\"90\"\n                x=\"8\"\n                text=\"想你所想\"\n                textColor=\"#888888\"\n                textSize=\"12\"/>\n        </View>\n\n\n        <View\n            width=\"72\"\n            height=\"200\"\n            y=\"372\"\n            x=\"160\">\n            <ImageView\n                width=\"64\"\n                height=\"64\"\n                y=\"8\"\n                x=\"8\"\n                imageUrl=\"http://images.apple.com/cn/itunespromos/itunes-this-week/images/2015/10/e5bb7c30-90cc-9363-d830-138583dd01b8/flowcase_680_260_1x.jpg\"\n                />\n            <TextView\n                width=\"64\"\n                height=\"24\"\n                y=\"72\"\n                x=\"8\"\n                text=\"淘宝\"\n                textSize=\"14\"/>\n            <TextView\n                width=\"64\"\n                height=\"24\"\n                y=\"90\"\n                x=\"8\"\n                text=\"购物无敌\"\n                textColor=\"#888888\"\n                textSize=\"12\"/>\n        </View>\n\n\n\n\n        <View\n            width=\"72\"\n            height=\"200\"\n            y=\"372\"\n            x=\"236\">\n            <ImageView\n                width=\"64\"\n                height=\"64\"\n                y=\"8\"\n                x=\"8\"\n                imageUrl=\"http://images.apple.com/cn/itunespromos/itunes-this-week/images/2015/10/e5bb7c30-90cc-9363-d830-138583dd01b8/flowcase_680_260_1x.jpg\"\n                />\n            <TextView\n                width=\"64\"\n                height=\"24\"\n                y=\"72\"\n                x=\"8\"\n                text=\"微信\"\n                textSize=\"16\"/>\n            <TextView\n                width=\"64\"\n                height=\"24\"\n                y=\"90\"\n                x=\"8\"\n                text=\"沟通无敌\"\n                textColor=\"#888888\"\n                textSize=\"12\"/>\n        </View>\n    </View>\n\n\n    <View\n        width=\"320\"\n        height=\"600\"\n        y=\"600\">\n        <ImageView width=\"300\"\n            height=\"108\"\n            x=\"12\"\n            imageUrl=\"http://images.apple.com/cn/itunespromos/itunes-this-week/images/2015/10/e5bb7c30-90cc-9363-d830-138583dd01b8/flowcase_680_260_1x.jpg\"\n            />\n        <EditText\n            width=\"300\"\n            height=\"40\"\n            y=\"120\"\n            x=\"10\"\n            background=\"#666666\"\n            requestFocus=\"false\"/>\n\n        <EditText\n            width=\"300\"\n            height=\"80\"\n            y=\"168\"\n            x=\"10\"\n            borderWidth=\"2\"\n            background=\"#88FFFF\"\n            numberOfLines=\"4\"\n            requestFocus=\"false\"\n            textAlign=\"left\"\n            verticalAlign=\"top\"/>\n        <Button\n            width=\"300\"\n            height=\"100\"\n            y=\"260\"\n            x=\"10\"\n            background=\"#444433\"\n            selectBackground=\"#989898\"\n            text=\"无微不至\"\n            textColor=\"#FFFFFF\"\n            textSize=\"24\"/>\n\n\n        <View\n            width=\"72\"\n            height=\"200\"\n            y=\"372\"\n            x=\"8\">\n            <ImageView\n                width=\"64\"\n                height=\"64\"\n                y=\"8\"\n                x=\"8\"\n                imageUrl=\"http://images.apple.com/cn/itunespromos/itunes-this-week/images/2015/10/e5bb7c30-90cc-9363-d830-138583dd01b8/flowcase_680_260_1x.jpg\"\n                />\n            <TextView\n                width=\"64\"\n                height=\"24\"\n                y=\"72\"\n                x=\"8\"\n                text=\"网商\"\n                textSize=\"14\"/>\n            <TextView\n                width=\"64\"\n                height=\"24\"\n                y=\"90\"\n                x=\"8\"\n                text=\"无微不至\"\n                textColor=\"#888888\"\n                textSize=\"12\"/>\n        </View>\n\n\n        <View\n            width=\"72\"\n            height=\"200\"\n            y=\"372\"\n            x=\"84\">\n            <ImageView\n                width=\"64\"\n                height=\"64\"\n                y=\"8\"\n                x=\"8\"\n                imageUrl=\"http://images.apple.com/cn/itunespromos/itunes-this-week/images/2015/10/e5bb7c30-90cc-9363-d830-138583dd01b8/flowcase_680_260_1x.jpg\"\n                />\n            <TextView\n                width=\"64\"\n                height=\"24\"\n                y=\"72\"\n                x=\"8\"\n                text=\"支付宝\"\n                textSize=\"14\"/>\n            <TextView\n                width=\"64\"\n                height=\"24\"\n                y=\"90\"\n                x=\"8\"\n                text=\"想你所想\"\n                textColor=\"#888888\"\n                textSize=\"12\"/>\n        </View>\n\n\n        <View\n            width=\"72\"\n            height=\"200\"\n            y=\"372\"\n            x=\"160\">\n            <ImageView\n                width=\"64\"\n                height=\"64\"\n                y=\"8\"\n                x=\"8\"\n                imageUrl=\"http://images.apple.com/cn/itunespromos/itunes-this-week/images/2015/10/e5bb7c30-90cc-9363-d830-138583dd01b8/flowcase_680_260_1x.jpg\"\n                />\n            <TextView\n                width=\"64\"\n                height=\"24\"\n                y=\"72\"\n                x=\"8\"\n                text=\"淘宝\"\n                textSize=\"14\"/>\n            <TextView\n                width=\"64\"\n                height=\"24\"\n                y=\"90\"\n                x=\"8\"\n                text=\"购物无敌\"\n                textColor=\"#888888\"\n                textSize=\"12\"/>\n        </View>\n\n\n        <View\n            width=\"72\"\n            height=\"200\"\n            y=\"372\"\n            x=\"236\">\n            <ImageView\n                width=\"64\"\n                height=\"64\"\n                y=\"8\"\n                x=\"8\"\n                imageUrl=\"http://images.apple.com/cn/itunespromos/itunes-this-week/images/2015/10/e5bb7c30-90cc-9363-d830-138583dd01b8/flowcase_680_260_1x.jpg\"\n                />\n            <TextView\n                width=\"64\"\n                height=\"24\"\n                y=\"72\"\n                x=\"8\"\n                text=\"微信\"\n                textSize=\"16\"/>\n            <TextView\n                width=\"64\"\n                height=\"24\"\n                y=\"90\"\n                x=\"8\"\n                text=\"沟通无敌\"\n                textColor=\"#888888\"\n                textSize=\"12\"/>\n        </View>\n\n    </View>\n\n\n    <HScrollView\n        width=\"320\"\n        height=\"200\"\n        y=\"1200\"\n        xmlUrl=\"/horizontal_imgs.xml\"/>\n\n\n    <WebView\n     y=\"1420\"\n     width=\"320\"\n     height=\"480\"\n     url=\"https://www.baidu.com\"\n     background=\"#433333\"></WebView>\n\n\n\n\n</View>\n"

/***/ }
/******/ ]);