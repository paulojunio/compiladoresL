sseg SEGMENT STACK ;início seg. pilha
byte 4000h DUP(?) ;dimensiona pilha
sseg ENDS ;fim seg. pilha
dseg SEGMENT PUBLIC ;início seg. dados
byte 4000h DUP(?) ;temporários
byte 20 DUP(?); caracter vetor declarado na posicao: 16384 Simbolo: luigi
byte 0x5F; caracter declarado na posicao: 16404 Simbolo:tanguinha
sword 20 DUP(?); inteiro vetor declarado na posicao: 16405 Simbolo: salaminho
byte 'A'; caracter declarado na posicao: 16445 Simbolo:a
sword 1; inteiro declarado na posicao: 16446 Simbolo:n
sword 4; inteiro declarado na posicao: 16448 Simbolo:j
byte 20 DUP(?); caracter vetor declarado na posicao: 16450 Simbolo: teste
sword 1; inteiro declarado na posicao: 16470 Simbolo:b
sword 5; inteiro declarado na posicao: 16472 Simbolo:d
byte 'C'; caracter declarado na posicao: 16474 Simbolo:e
dseg ENDS ;fim seg. dados
cseg SEGMENT PUBLIC ;início seg. código
ASSUME CS:cseg, DS:dseg
strt: ;início do programa
mov ax, dseg
mov ds, ax
mov ax , 1
mov DS:[0] , ax
mov ax , DS:[16446]
mov bx , DS:[0]
cmp ax , bx
mov ax , DS:[16446]
mov bx , DS:[16470]
cmp ax , bx
mov ax , DS:[16446]
mov bx , DS:[16446]
imul bx
mov DS:[2], ax
mov ax , 'A'
mov DS:[3] , ax
mov ax , 'B'
mov DS:[4] , ax
mov ax , DS:[3]
mov bx , DS:[4]
add ax , bx
mov DS:[5], ax
mov ax , 1
mov DS:[7] , ax
mov ax , DS:[16446]
mov bx , DS:[7]
cmp ax , bx
mov ax , DS:[16446]
mov bx , DS:[16470]
cmp ax , bx
mov ax , DS:[16446]
mov bx , DS:[16446]
imul bx
mov DS:[9], ax
mov ax , 'A'
mov DS:[10] , ax
mov ax , 'B'
mov DS:[11] , ax
mov ax , DS:[10]
mov bx , DS:[11]
add ax , bx
mov DS:[12], ax
mov ax , 1
mov DS:[14] , ax
mov ax , DS:[16446]
mov bx , DS:[14]
cmp ax , bx
mov ax , 'A'
mov DS:[16] , ax
mov ax , 'B'
mov DS:[17] , ax
mov ax , DS:[16]
mov bx , DS:[17]
add ax , bx
mov DS:[18], ax
mov ax , 1
mov DS:[20] , ax
mov ax , DS:[16446]
mov bx , DS:[20]
cmp ax , bx
mov ax , 'A'
mov DS:[22] , ax
mov ax , 'B'
mov DS:[23] , ax
mov ax , DS:[22]
mov bx , DS:[23]
add ax , bx
mov DS:[24], ax
mov ax , 1
mov DS:[26] , ax
mov ax , 10
mov DS:[28] , ax
mov ax , 12
mov DS:[30] , ax
mov ax , DS:[16446]
mov bx , DS:[30]

mov ax , 2
mov DS:[32] , ax
mov ax , DS:[16446]
mov bx , DS:[32]
mov dx , 0
idiv bx
mov ax , dx
mov DS:[34], ax
mov ax , 1
mov DS:[36] , ax
mov ax , 'M'
mov DS:[38] , ax
mov ax , 2
mov DS:[39] , ax
mov ax , 0x41
mov DS:[41] , ax
mov ax , 3
mov DS:[42] , ax
mov ax , 0x43
mov DS:[44] , ax
mov ax , 4
mov DS:[45] , ax
mov ax , 0x4F
mov DS:[47] , ax
mov ax , 5
mov DS:[48] , ax
mov ax , 'N'
mov DS:[50] , ax
mov ax , 6
mov DS:[51] , ax
mov ax , 'H'
mov DS:[53] , ax
mov ax , 7
mov DS:[54] , ax
mov ax , 0x41
mov DS:[56] , ax
mov ax , 8
mov DS:[57] , ax
mov ax , '.'
mov DS:[59] , ax
mov ax , 1
mov DS:[60] , ax
mov ax , 5
mov DS:[62] , ax
mov ax , 2
mov DS:[64] , ax
mov ax , 4
mov DS:[66] , ax
mov ax , DS:[66]
neg ax
mov DS:[68] , ax
mov ax , 3
mov DS:[70] , ax
mov ax , 5
mov DS:[72] , ax
mov ax , 3
mov DS:[74] , ax
mov ax , DS:[72]
mov bx , DS:[74]
add ax , bx
mov DS:[76], ax
mov ax , 4
mov DS:[78] , ax
mov ax , 4
mov DS:[80] , ax
mov ax , 2
mov DS:[82] , ax
mov ax , DS:[80]
mov bx , DS:[82]
mov dx , 0
idiv bx
mov DS:[84], ax
mov ax , 5
mov DS:[86] , ax
mov ax , 4
mov DS:[88] , ax
mov ax , 2
mov DS:[90] , ax
mov ax , DS:[90]
neg ax
mov DS:[92] , ax
mov ax , DS:[88]
mov bx , DS:[92]
imul bx
mov DS:[94], ax
mov ax , 6
mov DS:[96] , ax
mov ax , 10
mov DS:[98] , ax
mov ax , DS:[98]
neg ax
mov DS:[100] , ax
mov ax , 'D'
mov DS:[102] , ax
mov ax , DS:[16445]
mov bx , DS:[102]

mov ax, 16445
neg ax
add ax, 1
mov DS:[103] , ax
mov ax , 1
mov DS:[104] , ax
mov ax , 1
mov DS:[106] , ax
mov di , DS:[106]
add di , 16405
mov ax , DS:[di]
mov DS:[108] , ax
mov ax , 5
mov DS:[109] , ax
mov ax , 1
mov DS:[111] , ax
mov ax , 1
mov DS:[113] , ax
mov di , DS:[113]
add di , 16405
mov ax , DS:[di]
mov DS:[115] , ax
mov ax , 5
mov DS:[116] , ax
mov ax , 1
mov DS:[118] , ax
mov ax , 10
mov DS:[120] , ax
mov ah,4Ch
int 21h
cseg ENDS ;fim seg. código
END strt ;fim programa
