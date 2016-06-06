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

	var _CommentBox = __webpack_require__(1);

	var _CommentBox2 = _interopRequireDefault(_CommentBox);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	var data = [{ id: 1, author: "Pete Hunt", text: "This is one comment" }, { id: 2, author: "Pete Hunt", text: "This is one comment" }, { id: 3, author: "Pete Hunt", text: "This is one comment" }, { id: 4, author: "Pete Hunt", text: "This is one comment" }, { id: 5, author: "Pete Hunt", text: "This is one comment" }, { id: 6, author: "Pete Hunt", text: "This is one comment" }, { id: 7, author: "Pete Hunt", text: "This is one comment" }, { id: 8, author: "Jordan Walke", text: "This is *another* comment" }];

	for (var i = 0; i < 100; i++) {
	  data.push({ id: 10, author: "Pete Hunt", text: "This is one comment" });
	}

	var LikeButton = React.createClass({
	  displayName: "LikeButton",

	  getInitialState: function getInitialState() {
	    return { liked: false };
	  },
	  handleClick: function handleClick(event) {
	    this.setState({ liked: !this.state.liked });
	  },
	  render: function render() {
	    var text = this.state.liked ? 'like' : 'haven\'t liked';
	    return React.createElement(
	      "p",
	      { onClick: this.handleClick },
	      "You ",
	      text,
	      " this. Click to toggle."
	    );
	  }
	});

	var App = React.createClass({
	  displayName: "App",

	  getInitialState: function getInitialState() {
	    return { data: data };
	  },
	  render: function render() {
	    return React.createElement(
	      "div",
	      { "class": "love" },
	      React.createElement(_CommentBox2.default, { key: "10004444", data: this.state.data }),
	      React.createElement(
	        "h1",
	        null,
	        "非常好的React尝试 + 非常好的React尝试  非常好的React尝试"
	      ),
	      React.createElement(LikeButton, null)
	    );
	  }
	});

	var box = ReactDOM.render(React.createElement(App, null), document.getElementById('content'));

	window.box = box;

	console.log(box);

	setInterval(function () {
	  //var data = box.state.data;
	  //data.push({id: 10, author: "Pete Hunt " + data.length, text: "This is one comment"});
	  //box.setState(data);
	}, 1000);

/***/ },
/* 1 */
/***/ function(module, exports) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	  value: true
	});

	/**
	 *  文档地址： http://facebook.github.io/react/docs/tutorial.html
	 */

	var CommentList = React.createClass({
	  displayName: 'CommentList',

	  render: function render() {
	    return React.createElement(
	      'div',
	      { className: 'commentList' },
	      'Hello, world! I am a CommentList.'
	    );
	  }
	});

	var CommentForm = React.createClass({
	  displayName: 'CommentForm',

	  getInitialState: function getInitialState() {
	    return { author: '', text: '' };
	  },
	  handleAuthorChange: function handleAuthorChange(e) {
	    this.setState({ author: e.target.value });
	  },
	  handleTextChange: function handleTextChange(e) {
	    this.setState({ text: e.target.value });
	  },
	  handleSubmit: function handleSubmit(e) {
	    e.preventDefault();

	    var author = this.state.author.trim();
	    var text = this.state.text.trim();
	    if (!text || !author) {
	      return;
	    }
	    this.props.onCommentSubmit({ author: author, text: text });
	    // TODO: send request to the server
	    this.setState({ author: '', text: '' });
	  },
	  render: function render() {
	    return React.createElement(
	      'form',
	      { className: 'commentForm', onSubmit: this.handleSubmit },
	      React.createElement('input', {
	        type: 'text',
	        placeholder: 'Your name',
	        value: this.state.author,
	        onChange: this.handleAuthorChange
	      }),
	      React.createElement('input', {
	        type: 'text',
	        placeholder: 'Say something...',
	        value: this.state.text,
	        onChange: this.handleTextChange
	      }),
	      React.createElement('input', { type: 'submit', value: 'Post' })
	    );
	  }
	});

	var CommentBox = React.createClass({
	  displayName: 'CommentBox',

	  handleCommentSubmit: function handleCommentSubmit(comment) {
	    // TODO: submit to the server and refresh the list
	    var data = this.state.data;
	    data.push(comment);
	    this.setState(data);
	    alert(JSON.stringify(this.state));
	  },
	  getInitialState: function getInitialState() {
	    return { data: [] };
	  },
	  render: function render() {
	    return React.createElement(
	      'div',
	      { className: 'commentBox' },
	      React.createElement(
	        'h1',
	        null,
	        'Comments'
	      ),
	      React.createElement(CommentList, { data: this.state.data }),
	      React.createElement(CommentForm, { onCommentSubmit: this.handleCommentSubmit })
	    );
	  }
	});

	var Comment = React.createClass({
	  displayName: 'Comment',

	  render: function render() {
	    return React.createElement(
	      'div',
	      { className: 'comment' },
	      React.createElement(
	        'h2',
	        { className: 'commentAuthor' },
	        this.props.author
	      ),
	      this.props.children.toString()
	    );
	  }
	});

	var CommentList = React.createClass({
	  displayName: 'CommentList',

	  render: function render() {

	    var index = 0;
	    var commentNodes = this.props.data.map(function (comment) {
	      ++index;
	      return React.createElement(
	        Comment,
	        { author: comment.author, key: index },
	        comment.text
	      );
	    });

	    return React.createElement(
	      'div',
	      { className: 'commentList' },
	      commentNodes
	    );
	  }
	});

	exports.default = CommentBox;

/***/ }
/******/ ]);