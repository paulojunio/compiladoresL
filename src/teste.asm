sseg SEGMENT STACK ;início seg. pilha
byte 4000h DUP(?) ;dimensiona pilha
sseg ENDS ;fim seg. pilha
dseg SEGMENT PUBLIC ;início seg. dados
byte 4000h DUP(?) ;temporários
byte 'i'; caracter declarado na posicao: 16384 Simbolo:a
sword 1; inteiro declarado na posicao: 16385 Simbolo:n
sword 4; inteiro declarado na posicao: 16387 Simbolo:b
sword 2000 DUP(?); inteiro vetor declarado na posicao: 16389 Simbolo: paulo
byte 'a'; caracter declarado na posicao: 20389 Simbolo:c
dseg ENDS ;fim seg. dados
cseg SEGMENT PUBLIC ;início seg. código
ASSUME CS:cseg, DS:dseg
strt: ;início do programa
mov ax, dseg
mov ds, ax
mov ah,4Ch
int 21h
cseg ENDS ;fim seg. código
END strt ;fim programa
