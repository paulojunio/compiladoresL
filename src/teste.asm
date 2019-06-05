sseg SEGMENT STACK ;início seg. pilha
byte 4000h DUP(?) ;dimensiona pilha
sseg ENDS ;fim seg. pilha
dseg SEGMENT PUBLIC ;início seg. dados
byte 4000h DUP(?) ;temporários
sword 0; inteiro declarado na posicao: 16384 Simbolo:deus
byte 'a'; caracter declarado na posicao: 16386 Simbolo:jesus
byte 10 DUP(?); caracter vetor declarado na posicao: 16387 Simbolo: moises
sword ?; inteiro declarado na posicao: 16397 Simbolo: n
sword ?; inteiro declarado na posicao: 16399 Simbolo: b
byte ?; caracter sem valor declarado na posicao: 16401 Simbolo: a
dseg ENDS ;fim seg. dados
cseg SEGMENT PUBLIC ;início seg. código
ASSUME CS:cseg, DS:dseg
strt: ;início do programa
mov ax, dseg
mov ds, ax
dseg SEGMENT PUBLIC ; declarando string
byte "True$" ; string
dseg ENDS ; fim da string
dseg SEGMENT PUBLIC ; declarando string
byte "True$" ; string
dseg ENDS ; fim da string
mov di,16402
mov si,16407
mov ax, 1
R3:
mov bx, DS:[di]
mov bh, 0
mov cx, DS:[si]
mov ch, 0
cmp bx, cx
jne R4
cmp bx, 024h
je R5
cmp cx, 024h
je R5
add di, 1
add si, 1
jmp R3
R4:
mov ax, 0
R5:
mov DS:[0], ax
mov ax, DS:[0]; comeco if
cmp ax, 1
jne R1; jne if
dseg SEGMENT PUBLIC ; declarando string
byte "Tudo certo$" ; string
dseg ENDS ; fim da string
mov dx, 0; comeco string
mov ah, 09h
int 21h
mov ah, 02h
mov dl, 0Dh
int 21h
mov DL, 0Ah
int 21h
R1:
R2:
dseg SEGMENT PUBLIC ; declarando string
byte "Tudo certo$" ; string
dseg ENDS ; fim da string
mov dx, 11; comeco string
mov ah, 09h
int 21h
mov ah, 02h
mov dl, 0Dh
int 21h
mov DL, 0Ah
int 21h
mov dx, 0
mov al, 11
mov DS:[0], al
mov ah, 0Ah
int 21h
mov ah, 02h
mov dl, 0Dh
int 21h
mov DL, 0Ah
int 21h
mov di, 2
mov si, 16387
R6:
mov al, DS:[di]
cmp al, 0Dh
je R7
mov DS:[si], al
add di, 1
add si, 1
jmp R6
R7:
mov al, 024h
mov DS:[si], al
dseg SEGMENT PUBLIC ; declarando string
byte "Max$" ; string
dseg ENDS ; fim da string
mov di,16387
mov si,22
mov ax, 1
R10:
mov bx, DS:[di]
mov bh, 0
mov cx, DS:[si]
mov ch, 0
cmp bx, cx
jne R11
cmp bx, 024h
je R12
cmp cx, 024h
je R12
add di, 1
add si, 1
jmp R10
R11:
mov ax, 0
R12:
mov DS:[13], ax
mov ax, DS:[13]; comeco if
cmp ax, 1
jne R8; jne if
dseg SEGMENT PUBLIC ; declarando string
byte "Fedido$" ; string
dseg ENDS ; fim da string
mov dx, 0; comeco string
mov ah, 09h
int 21h
mov ah, 02h
mov dl, 0Dh
int 21h
mov DL, 0Ah
int 21h
R8:
R9:
mov ah,4Ch
int 21h
cseg ENDS ;fim seg. código
END strt ;fim programa
