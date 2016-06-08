#ifndef DUV_REFS_H
#define DUV_REFS_H

#include "duktape.h"

// Create a global array refs in the heap stash.
void duk_js_ref_setup(duk_context *ctx);
// like luaL_ref, but assumes storage in "refs" property of heap stash
int duk_js_ref(duk_context *ctx);
void duk_push_js_ref(duk_context *ctx, int ref);
void duk_js_unref(duk_context *ctx, int ref);

int duk_js_ref_size(duk_context *ctx);

#endif
