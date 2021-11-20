/*
 * mm-explicit.c - an empty malloc package
 *
 * NOTE TO STUDENTS: Replace this header comment with your own header
 * comment that gives a high level description of your solution.
 *
 * @id : 201601989
 * @name : 김진섭
 */
#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#include "mm.h"
#include "memlib.h"

/* If you want debugging output, use the following macro.  When you hand
 * in, remove the #define DEBUG line. */
#define DEBUG
#ifdef DEBUG
# define dbg_printf(...) printf(__VA_ARGS__)
#else
# define dbg_printf(...)
#endif


/* do not change the following! */
#ifdef DRIVER
/* create aliases for driver tests */
#define malloc mm_malloc
#define free mm_free
#define realloc mm_realloc
#define calloc mm_calloc
#endif /* def DRIVER */

/* single word (4) or double word (8) alignment */
#define ALIGNMENT 8
#define HDRSIZE 4
#define FTRSIZE 4
#define DSIZE 8
#define WSIZE 4
#define CHUNKSIZE (1<<12)
#define MAX(x,y) ((x) > (y) ? (x) : (y) )
#define PACK(size, alloc) ((size)|(alloc))
#define GET(p) (*(unsigned int *)(p))
#define GET8(p) (*(unsigned long *)(p))
#define PUT(p, val) (*(unsigned int *)(p) = (val))
#define PUT8(p, val) (*(unsigned long *)(p) = (val))
#define GET_SIZE(p) (GET(p) & ~0x7)
#define GET_ALLOC(p) (GET(p) & 0x1)
#define HDRP(bp) ((char *)(bp)-WSIZE)
#define FTRP(bp) ((char *)(bp) + GET_SIZE(HDRP(bp))-DSIZE)
#define NEXT_BLKP(bp) ((char *)(bp) + GET_SIZE(((char*)(bp) - WSIZE)))
#define PREV_BLKP(bp) ((char *)(bp) + GET_SIZE(((char*)(bp) - DSIZE)))

#define NEXT_FREEP(bp) ((char *)(bp))
#define PREV_FREEP(bp) ((char *)(bp) + WSIZE)

#define NEXT_FREE_BLKP(bp) ((char *)GET8((char*)(bp)))
#define PREV_FREE_BLKP(bp) ((char *)GET8((char*)(bp + WSIZE)))
/* rounds up to the nearest multiple of ALIGNMENT */
#define ALIGN(p) (((size_t)(p) + (ALIGNMENT-1)) & ~0x7)

inline void *extend_heap(size_t words);
static void *coalesce(void *bp);
static void *heap_root;
static void *epilogue;
static void *free_ptr;
static void *heap_listp;
static void place(void *bp, size_t asize);
static void *find_fit(size_t asize);
/*
 * Initialize: return -1 on error, 0 on success.
 */
int mm_init(void) {
	heap_listp = mem_sbrk(DSIZE+ 4*HDRSIZE);
	if(heap_listp == NULL) return -1;
	free_ptr = heap_listp;
	heap_root = heap_listp;
	PUT(heap_listp, NULL);
	PUT(heap_listp + WSIZE, NULL);
	PUT(heap_listp + 2*WSIZE, 0);
	PUT(heap_listp + 3*WSIZE, PACK(DSIZE,1));
	PUT(heap_listp + 4*WSIZE, PACK(DSIZE,1));
	PUT(heap_listp + 5*WSIZE, PACK(0,1));
	heap_listp += 2*DSIZE;
	epilogue = heap_listp + HDRSIZE;
	if(extend_heap(CHUNKSIZE/WSIZE) == NULL) return -1;
    return 0;
}


/*
 * malloc
 */
void *malloc (size_t size) {
	char *bp;
	unsigned asize;
	unsigned extendsize;
	if(size <= 0) return NULL; //size가 올바르지 않을 때 예외처리
	if(size <= DSIZE){asize = 3*DSIZE;}
	else{asize = DSIZE + ((size + (DSIZE) + (DSIZE-1)) / DSIZE);}

	if((bp = find_fit(asize)) != NULL){
		place(bp, asize);
		return bp;
	}
	extendsize = MAX(asize, CHUNKSIZE);
	if((bp = extend_heap(extendsize/WSIZE)) == NULL) return NULL;
	place(bp, asize);
	 return bp;
}

/*
 * free
 */
void free (void *ptr) {
	if(!ptr) return;
	size_t size = GET_SIZE(HDRP(ptr));
	PUT(HDRP(ptr), PACK(size,0));
	PUT(FTRP(ptr), PACK(size,0));
//	coalesce(ptr);
}

/*
 * realloc - you may want to look at mm-naive.c
 */
void *realloc(void *oldptr, size_t size) {
    return NULL;
}

/*
 * calloc - you may want to look at mm-naive.c
 * This function is not tested by mdriver, but it is
 * needed to run the traces.
 */
void *calloc (size_t nmemb, size_t size) {
    return NULL;
}


/*
 * Return whether the pointer is in the heap.
 * May be useful for debugging.
 */
static int in_heap(const void *p) {
    return p < mem_heap_hi() && p >= mem_heap_lo();
}

/*
 * Return whether the pointer is aligned.
 * May be useful for debugging.
 */
static int aligned(const void *p) {
    return (size_t)ALIGN(p) == (size_t)p;
}

/*
 * mm_checkheap
 */
void mm_checkheap(int verbose) {
}

/*그 외 구현*/
static void *coalesce(void *bp){
	size_t prev_alloc = GET_ALLOC(FTRP(PREV_BLKP(bp)));
	size_t next_alloc = GET_ALLOC(HDRP(NEXT_BLKP(bp)));
	size_t size = GET_SIZE(HDRP(bp));

	if(prev_alloc && next_alloc){return;}

	if(prev_alloc && !next_alloc){
		size += GET_SIZE(HDRP(NEXT_BLKP(bp)));
		if(PREV_FREE_BLKP(bp) != NULL) //이전에 연결된 블럭이 있으면
			PUT8(NEXT_FREEP(PREV_FREE_BLKP(bp)), GET8(NEXT_FREEP(bp)));
		else{ //없다면
			PUT8(heap_root, NEXT_FREEP(bp)); //루트와 연결
		}
		if(NEXT_FREE_BLKP(bp) != NULL){
			PUT8(PREV_FREEP(NEXT_FREE_BLKP(bp)), GET8(PREV_FREEP(bp)));
		}
		PUT(HDRP(bp),PACK(size,0));
		PUT(FTRP(bp),PACK(size,0));	
	}
	else if(!prev_alloc && next_alloc){
		size += GET_SIZE(HDRP(PREV_BLKP(bp)));
		if(PREV_FREE_BLKP(PREV_BLKP(bp)) != NULL)
			PUT8(NEXT_FREEP(PREV_FREE_BLKP(PREV_BLKP(bp))), GET8(NEXT_FREEP(PREV_BLKP(bp))));
		else{
			PUT8(heap_root, NEXT_FREEP(PREV_BLKP(bp)));
		}
		if(NEXT_FREE_BLKP(PREV_BLKP(bp)) != NULL)
			PUT8(heap_root, NEXT_FREEP(bp));
		PUT(FTRP(PREV_BLKP(bp)), PACK(size,0));
		PUT(HDRP(PREV_BLKP(bp)), PACK(size,0));
		bp = PREV_BLKP(bp);
	}
	else{
		size += GET_SIZE(HDRP(PREV_BLKP(bp))) + GET_SIZE(FTRP(NEXT_BLKP(bp)));
		if(PREV_FREE_BLKP(PREV_BLKP(bp)) != NULL)
			PUT8(NEXT_FREEP(PREV_FREE_BLKP(PREV_BLKP(bp))), GET8(NEXT_FREEP(PREV_BLKP(bp))));
		else{
			PUT8(heap_root, NEXT_FREEP(PREV_BLKP(bp)));
		}
		if(NEXT_FREE_BLKP(PREV_BLKP(bp)) != NULL)
			PUT8(heap_root, NEXT_FREEP(bp));
		if(PREV_FREE_BLKP(NEXT_BLKP(bp)) != NULL)
			PUT8(NEXT_FREEP(PREV_FREE_BLKP(NEXT_BLKP(bp))), GET8(NEXT_FREEP(NEXT_BLKP(bp))));
		else{
			PUT8(heap_root, NEXT_FREEP(NEXT_BLKP(bp)));
		}
		if(NEXT_FREE_BLKP(NEXT_BLKP(bp)) != NULL)
			PUT8(PREV_FREEP(NEXT_BLKP(NEXT_BLKP(bp))), GET8(PREV_FREEP(bp)));
		PUT(HDRP(PREV_BLKP(bp)), PACK(size, 0));
		PUT(FTRP(NEXT_BLKP(bp)), PACK(size,0));
		bp = PREV_BLKP(bp);
	}
	return bp;
}

inline void *extend_heap(size_t words){
	unsigned *old;
	char *bp;
	unsigned size;
	size = (words % 2) ? (words + 1)*WSIZE : words*WSIZE;
	if((long)(bp = mem_sbrk(size)) < 0) return NULL;

	epilogue = bp + size - HDRSIZE;

	PUT(HDRP(bp), PACK(size,0));
	PUT(FTRP(bp), PACK(size,0));
	PUT(epilogue, PACK(0,1));
//	return coalesce(bp);
}

static void *find_fit(size_t asize){
	void* bp;
	for(bp = heap_root; GET_SIZE(HDRP(bp)) > 0; bp = NEXT_FREE_BLKP(bp)){
		if(asize <= GET_SIZE(HDRP(bp))){
			return bp;
		}
	}
	return NULL;
}

static void place(void *bp, size_t asize){
	size_t csize = GET_SIZE(HDRP(bp));
	void* next;
	if((csize - asize) >= 3*DSIZE){
		PUT(HDRP(bp), PACK(asize,1));
		PUT(FTRP(bp), PACK(asize,1));
		if(PREV_FREE_BLKP(bp) != NULL){
			PUT8(NEXT_FREEP(PREV_FREE_BLKP(bp)), GET8(NEXT_FREEP(bp)));
		}
		else{
			PUT8(heap_root , NEXT_FREEP(bp));
		}
		if(NEXT_FREE_BLKP(bp) != NULL)
			PUT8(PREV_FREEP(NEXT_FREE_BLKP(bp)), GET8(PREV_FREEP(bp)));
		next = NEXT_BLKP(bp);
		PUT(HDRP(next), PACK(csize-asize,0));
		PUT(FTRP(next), PACK(csize-asize,0));
	}
	else{
		PUT(HDRP(bp), PACK(csize,1));
		PUT(FTRP(bp), PACK(csize,1));
		if(PREV_FREE_BLKP(bp) != NULL){
			PUT8(NEXT_FREEP(PREV_FREE_BLKP(bp)), GET8(NEXT_FREEP(bp)));
		}
		else{
			PUT8(heap_root , NEXT_FREEP(bp));
		}
		if(NEXT_FREE_BLKP(bp) != NULL)
			PUT8(PREV_FREEP(NEXT_FREE_BLKP(bp)), GET8(PREV_FREEP(bp)));
	}
}

