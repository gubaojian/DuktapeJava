
#include "refs.h"
#include <pthread.h>

#define JS_REFS_KEY "__refs"

unsigned int hash(unsigned int x) {
    x = ((x >> 16) ^ x) * 0x45d9f3b;
    x = ((x >> 16) ^ x) * 0x45d9f3b;
    x = (x >> 16) ^ x;
    return x;
}

// Create a global array refs in the heap stash.
void duk_js_ref_setup(duk_context *ctx) {
  duk_push_global_stash(ctx);
  // Create a new array with one `0` at index `0`.
  duk_push_array(ctx);
  duk_push_int(ctx, 0);
  duk_put_prop_index(ctx, -2, 0);
  // Store it as "refs" in the heap stash
  duk_put_prop_string(ctx, -2, JS_REFS_KEY);

  duk_pop(ctx);
}

int duk_js_ref_size(duk_context *ctx){
	 duk_push_global_stash(ctx);
	 duk_get_prop_string(ctx, -1, JS_REFS_KEY);
	 int length = duk_get_length(ctx, -1);
	 duk_pop_2(ctx);
	 return length;
}

// like luaL_ref, but assumes storage in "refs" property of heap stash
int duk_js_ref(duk_context *ctx) {
  int ref;
  if (duk_is_undefined(ctx, -1)) {
    duk_pop(ctx);
    return 0;
  }
  // Get the "refs" array in the heap stash
  duk_push_global_stash(ctx);
  duk_get_prop_string(ctx, -1, JS_REFS_KEY);
  duk_remove(ctx, -2);
 duk_insert(ctx, -2);
 void* pointer = duk_get_heapptr(ctx, -1);
 ref  = labs((intptr_t)pointer)&0x7fffffff;
 DEBUG_LOG("ScriptEngine","duk_js_ref %d pointer %p", ref, pointer);
 while(duk_get_prop_index(ctx, -2, ref)){
     ref = hash(ref)&0x7fffffff;
     duk_pop(ctx);
 }
 duk_pop(ctx);
 duk_put_prop_index(ctx, -2, ref);
 // Remove the refs array from the stack.
 duk_pop(ctx);

  return ref;
}

void duk_js_unref(duk_context *ctx, int ref) {

  if (ref == 0 || ctx == NULL) return;

  DEBUG_LOG("ScriptEngine","duk_js_unref %d ", ref);

  // Get the "refs" array in the heap stash
  duk_push_global_stash(ctx);
  duk_get_prop_string(ctx, -1, JS_REFS_KEY);
  duk_remove(ctx, -2);
  duk_del_prop_index(ctx, -1, ref);
  duk_pop(ctx);
}

void duk_push_js_ref(duk_context *ctx, int ref) {
  if(ctx == NULL){
    return;
  }
  if (ref == 0) {
    duk_push_undefined(ctx);
    return;
  }
  // Get the "refs" array in the heap stash
  duk_push_global_stash(ctx);
  duk_get_prop_string(ctx, -1, JS_REFS_KEY);
  duk_remove(ctx, -2);
  duk_get_prop_index(ctx, -1, ref);
  duk_remove(ctx, -2);
}

void duk_push_js_refs(duk_context *ctx){
  duk_push_global_stash(ctx);
  duk_get_prop_string(ctx, -1, JS_REFS_KEY);
  duk_remove(ctx, -2);
}
