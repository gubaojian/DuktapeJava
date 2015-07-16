/*
 * utils.c
 *
 *  Created on: 2015年6月26日
 *      Author: jianbai.gbj
 */
#include "utils.h"
#include <string.h>


char* str_malloc_dup(const char* src){
	int size = strlen(src) + 1;
	char* dest = malloc(strlen(src) + 1);
	strcpy(dest, src);
	return dest;
}

void str_free(char* src){
     free(src);
}

void str_repleace_char(char* src, char old_ch, char new_ch){
    char* it = src;
    while(*it){
    	    if(*it == old_ch){
    	    	    *it = new_ch;
    	    }
    	    it++;
    }
}

void str_last_repleace_char(char* src, char old_ch, char new_ch){
	int len = strlen(src);
    char* it = src  + len - 1;
    while(it != src){
    	    if(*it == old_ch){
    	    	    *it = new_ch;
    	    	    return;
    	    }
    	    it--;
    }
    if(*src == old_ch){
    	    *src = new_ch;
    }
}


