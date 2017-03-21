(function(modules) {
  var installedModules = {};
  function __webpack_require__(moduleId) {
    if (installedModules[moduleId]) 
    return installedModules[moduleId].exports;
    var module = installedModules[moduleId] = {i: moduleId, l: false, exports: {}};
    modules[moduleId].__c("call", module.__g("exports"), module, module.__g("exports"), __webpack_require__);
    module.__s("l", true);
    return module.exports;
  }
  __webpack_require__.__s("m", modules);
  __webpack_require__.__s("c", installedModules);
  __webpack_require__.__s("i", function(value) {
  return value;
});
  __webpack_require__.__s("d", function(exports, name, getter) {
  if (!__webpack_require__.__c("o", exports, name)) 
  {
    Object.__c("defineProperty", exports, name, {configurable: false, enumerable: true, get: getter});
  }
});
  __webpack_require__.__s("n", function(module) {
  var getter = module && module.__esModule ? function getDefault() {
  return module['default'];
} : function getModuleExports() {
  return module;
};
  __webpack_require__.__c("d", getter, 'a', getter);
  return getter;
});
  __webpack_require__.__s("o", function(object, property) {
  return Object.__g("prototype").__g("hasOwnProperty").__c("call", object, property);
});
  __webpack_require__.__s("p", "");
  return __webpack_require__(__webpack_require__.__g("s") = 2);
})([(function(module, exports) {
  module.__s("exports", "{\n    \"scale1\": {\n        \"name\": \"scale1\",\n        \"fillMode\": \"both\",\n        \"motions\": [\n            {\n                \"name\": \"1\",\n                \"key\": \"scale\",\n                \"duration\": 400,\n                \"fromValue\": \"0\",\n                \"toValue\": \"1\"\n            }\n        ]\n    }\n}");
}), (function(module, exports) {
  module.__s("exports", "<html>\n    <style>\n        .container {\n        width:100%;\n        height:100%;\n        align-items:center;\n        justify-content:center\n        }\n\n        .message {\n        font-size:20;\n        font-weight:bold;\n        color:#3F51B5\n        }\n    </style>\n    <body>\n        <div class=\"container\">\n            <text class=\"message\">Hello, View+ !</text>\n        </div>\n    </body>\n</html>");
}), (function(module, exports, __webpack_require__) {
  var layout = __webpack_require__(1);
  var anim = __webpack_require__(0);
  var module = {};
  document.__c("write", anim);
  document.__c("write", layout);
})]);
