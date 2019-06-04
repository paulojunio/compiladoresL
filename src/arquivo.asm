sseg SEGMENT STACK ;início seg. pilha
byte 4000h DUP(?) ;dimensiona pilha
sseg ENDS ;fim seg. pilha
dseg SEGMENT PUBLIC ;início seg. dados
byte 4000h DUP(?) ;temporários
sword ?; inteiro declarado na posicao: 16384 Simbolo: n
sword ?; inteiro declarado na posicao: 16386 Simbolo: a
sword ?; inteiro declarado na posicao: 16388 Simbolo: b
sword ?; inteiro declarado na posicao: 16390 Simbolo: c
byte ?; caracter sem valor declarado na posicao: 16392 Simbolo: g
byte 2 DUP(?); caracter vetor declarado na posicao: 16393 Simbolo: p
sword 5 DUP(?); inteiro vetor declarado na posicao: 16395 Simbolo: k
dseg ENDS ;fim seg. dados
cseg SEGMENT PUBLIC ;início seg. código
ASSUME CS:cseg, DS:dseg
strt: ;início do programa
mov ax, dseg
mov ds, ax
mov ax , 4
mov DS:[0] , ax
mov ax , 2
mov DS:[2] , ax
mov ah,4Ch
int 21h
cseg ENDS ;fim seg. código
END strt ;fim programa
