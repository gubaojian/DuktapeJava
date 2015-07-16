/*
 * utils.h
 *
 *  Created on: 2015年6月26日
 *      Author: jianbai.gbj
 */

#ifndef UTILS_H_
#define UTILS_H_

char* str_malloc_dup(const char* src);

void str_free(char* src);

void str_repleace_char(char* src, char old_ch, char new_ch);

void str_last_repleace_char(char* src, char old_ch, char new_ch);

#endif /* UTILS_H_ */


/**
 *
 *
inline jvalue* malloc_jvalue_array(int length)
{
  return malloc(length * sizeof(jvalue));
}

inline void free_jvalue_array(jvalue* values)
{
   free(values);
}
 *
 *
 *
int adder(duk_context *ctx) {
	int i;
	int n = duk_get_top(ctx);
	double res = 0.0;

	for (i = 0; i < n; i++) {
		res += duk_to_number(ctx, i);
	}
	duk_push_number(ctx, res);
	return 1;
}*/
