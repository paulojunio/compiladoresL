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
sword ?; inteiro declarado na posicao: 16448 Simbolo: h
sword 4; inteiro declarado na posicao: 16450 Simbolo:j
byte 20 DUP(?); caracter vetor declarado na posicao: 16452 Simbolo: teste
sword 1; inteiro declarado na posicao: 16472 Simbolo:b
sword 5; inteiro declarado na posicao: 16474 Simbolo:d
byte 'C'; caracter declarado na posicao: 16476 Simbolo:e
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
cmp ax, bx
jg R3
mov ax, 0
jmp R4
R3:
mov ax, 1
R4:
mov DS:[2], ax
mov ax , DS:[16446]
mov bx , DS:[16472]
cmp ax, bx
jl R5
mov ax, 0
jmp R6
R5:
mov ax, 1
R6:
mov DS:[3], ax
mov ax , DS:[2]
mov bx , DS:[3]
cmp ax, 1
je R7
cmp bx, 1
je R7
jmp R8
R:7
mov ax, 1
jmp R9
R:8
mov ax, 0
R:9
mov DS:[4], ax
mov ax, DS:[4]; comeco if
cmp ax, 1
jne R1; jne if
mov ax , 'A'
mov DS:[0] , ax
mov ax , 'B'
mov DS:[1] , ax
mov ax , 65
mov bx , 66
add ax , bx
mov DS:[2], ax
mov ax, DS:[2]
mov DS:[16445], ax
R1:
R2:
mov ax , 1
mov DS:[3] , ax
mov ax , DS:[16446]
mov bx , DS:[3]
cmp ax, bx
jg R12
mov ax, 0
jmp R13
R12:
mov ax, 1
R13:
mov DS:[5], ax
mov ax , DS:[16446]
mov bx , DS:[16472]
cmp ax, bx
jge R14
mov ax, 0
jmp R15
R14:
mov ax, 1
R15:
mov DS:[6], ax
mov ax , DS:[5]
mov bx , DS:[6]
imul bx
mov DS:[7], ax
mov ax, DS:[7]; comeco if
cmp ax, 1
jne R10; jne if
mov ax , 'A'
mov DS:[0] , ax
mov ax , 'B'
mov DS:[1] , ax
mov ax , 65
mov bx , 66
add ax , bx
mov DS:[2], ax
mov ax, DS:[2]
mov DS:[16445], ax
R10:
R11:
mov ax , 1
mov DS:[3] , ax
mov ax , DS:[16446]
mov bx , DS:[3]
cmp ax, bx
jle R18
mov ax, 0
jmp R19
R18:
mov ax, 1
R19:
mov DS:[5], ax
mov ax, DS:[5]; comeco if
cmp ax, 1
jne R16; jne if
mov ax , 'A'
mov DS:[0] , ax
mov ax , 'B'
mov DS:[1] , ax
mov ax , 65
mov bx , 66
add ax , bx
mov DS:[2], ax
mov ax, DS:[2]
mov DS:[16445], ax
R16:
R17:
mov ax , 1
mov DS:[3] , ax
mov ax , DS:[16446]
mov bx , DS:[3]
cmp ax, bx
jge R22
mov ax, 0
jmp R23
R22:
mov ax, 1
R23:
mov DS:[5], ax
mov ax, DS:[5]; comeco if
cmp ax, 1
jne R20; jne if
mov ax , 'A'
mov DS:[0] , ax
mov ax , 'B'
mov DS:[1] , ax
mov ax , 65
mov bx , 66
add ax , bx
mov DS:[2], ax
mov ax, DS:[2]
mov DS:[16445], ax
R20:
R21:
mov ax , 1
mov DS:[3] , ax
;Inicio for
mov ax, DS:[3]
mov DS:[16446] , ax
;Acabou atribuicao
R24:
mov ax , 10
mov DS:[0] , ax
;Inicio comparacao
mov ax,DS:[16446]
mov bx,DS:[0]
cmp ax, bx
jg R25
mov cx, 1
mov ax , 12
mov DS:[0] , ax
mov ax , DS:[16446]
mov bx , DS:[0]
cmp ax, bx
je R28
mov ax, 0
jmp R29
R28:
mov ax, 1
R29:
mov DS:[2], ax
mov ax, DS:[2]; comeco if
cmp ax, 1
jne R26; jne if
mov ax , 2
mov DS:[0] , ax
mov ax , DS:[16446]
mov bx , DS:[0]
mov dx , 0
idiv bx
mov ax , dx
mov DS:[2], ax
mov ax, DS:[2]
mov DS:[16446], ax
R26:
R27:
add DS:[16446], cx
jmp R24
R25:
mov ax , 1
mov DS:[3] , ax
mov ax , 'M'
mov DS:[5] , ax
mov di, DS:[3] ; foi feito aqui
add di, 16384
mov ax,DS:[5]
mov DS:[di], ax
mov ax , 2
mov DS:[6] , ax
mov ax , 0x41
mov DS:[8] , ax
mov di, DS:[6] ; foi feito aqui
add di, 16384
mov ax,DS:[8]
mov DS:[di], ax
mov ax , 3
mov DS:[9] , ax
mov ax , 0x43
mov DS:[11] , ax
mov di, DS:[9] ; foi feito aqui
add di, 16384
mov ax,DS:[11]
mov DS:[di], ax
mov ax , 4
mov DS:[12] , ax
mov ax , 0x4F
mov DS:[14] , ax
mov di, DS:[12] ; foi feito aqui
add di, 16384
mov ax,DS:[14]
mov DS:[di], ax
mov ax , 5
mov DS:[15] , ax
mov ax , 'N'
mov DS:[17] , ax
mov di, DS:[15] ; foi feito aqui
add di, 16384
mov ax,DS:[17]
mov DS:[di], ax
mov ax , 6
mov DS:[18] , ax
mov ax , 'H'
mov DS:[20] , ax
mov di, DS:[18] ; foi feito aqui
add di, 16384
mov ax,DS:[20]
mov DS:[di], ax
mov ax , 7
mov DS:[21] , ax
mov ax , 0x41
mov DS:[23] , ax
mov di, DS:[21] ; foi feito aqui
add di, 16384
mov ax,DS:[23]
mov DS:[di], ax
mov ax , 8
mov DS:[24] , ax
mov ax , '.'
mov DS:[26] , ax
mov di, DS:[24] ; foi feito aqui
add di, 16384
mov ax,DS:[26]
mov DS:[di], ax
mov ax , 1
mov DS:[27] , ax
mov ax , 5
mov DS:[29] , ax
mov di, DS:[27] ; foi feito aqui
add di, di
add di, 16405
mov ax,DS:[29]
mov DS:[di], ax; foi aqui tb
mov ax , 2
mov DS:[31] , ax
mov ax , 4
mov DS:[33] , ax
mov ax , DS:[33]
neg ax
mov DS:[35] , ax
mov di, DS:[31] ; foi feito aqui
add di, di
add di, 16405
mov ax,DS:[35]
mov DS:[di], ax; foi aqui tb
mov ax , 3
mov DS:[37] , ax
mov ax , 5
mov DS:[39] , ax
mov ax , 3
mov DS:[41] , ax
mov ax , DS:[39]
mov bx , DS:[41]
add ax , bx
mov DS:[43], ax
mov di, DS:[37] ; foi feito aqui
add di, di
add di, 16405
mov ax,DS:[43]
mov DS:[di], ax; foi aqui tb
mov ax , 4
mov DS:[45] , ax
mov ax , 4
mov DS:[47] , ax
mov ax , 2
mov DS:[49] , ax
mov ax , DS:[47]
mov bx , DS:[49]
mov dx , 0
idiv bx
mov DS:[51], ax
mov di, DS:[45] ; foi feito aqui
add di, di
add di, 16405
mov ax,DS:[51]
mov DS:[di], ax; foi aqui tb
mov ax , 5
mov DS:[52] , ax
mov ax , 4
mov DS:[54] , ax
mov ax , 2
mov DS:[56] , ax
mov ax , DS:[56]
neg ax
mov DS:[58] , ax
mov ax , DS:[54]
mov bx , DS:[58]
imul bx
mov DS:[60], ax
mov di, DS:[52] ; foi feito aqui
add di, di
add di, 16405
mov ax,DS:[60]
mov DS:[di], ax; foi aqui tb
mov ax , 6
mov DS:[61] , ax
mov ax , 10
mov DS:[63] , ax
mov ax , DS:[63]
neg ax
mov DS:[65] , ax
mov di, DS:[61] ; foi feito aqui
add di, di
add di, 16405
mov ax,DS:[65]
mov DS:[di], ax; foi aqui tb
mov ax , 'D'
mov DS:[67] , ax
mov ax , DS:[16445]
mov ah, 0
mov bx , DS:[67]
mov bh, 0
cmp ax, bx
je R32
mov ax, 0
jmp R33
R32:
mov ax, 1
R33:
mov DS:[68], ax
mov ax, 68
neg ax
add ax, 1
mov DS:[69] , ax
mov ax, DS:[69]; comeco if
cmp ax, 1
jne R30; jne if
mov ax , 1
mov DS:[0] , ax
mov di, 0
mov cx, 0
mov ax, DS:[0]
cmp ax, 0
jge R34
mov bl, 2Dh
mov DS:[di], bl
add di , 1
neg ax
R34:
mov bx, 10
R35:
add cx, 1
mov dx, 0
idiv bx
push dx
cmp ax, 0
jne R35
R36:
pop dx
add dx, 30h
mov DS:[di] , dl
add di, 1
add cx, -1
cmp cx, 0
jne R36
mov dl, 024h
mov DS:[di], dl
mov dx, 0
mov ah, 09h
int 21h
mov ax , 1
mov DS:[3] , ax
mov di , DS:[3]
add di , di
add di , 16405
mov ax , DS:[di]
mov DS:[5] , ax
mov di, 0
mov cx, 0
mov ax, DS:[5]
cmp ax, 0
jge R37
mov bl, 2Dh
mov DS:[di], bl
add di , 1
neg ax
R37:
mov bx, 10
R38:
add cx, 1
mov dx, 0
idiv bx
push dx
cmp ax, 0
jne R38
R39:
pop dx
add dx, 30h
mov DS:[di] , dl
add di, 1
add cx, -1
cmp cx, 0
jne R39
mov dl, 024h
mov DS:[di], dl
mov dx, 0
mov ah, 09h
int 21h
mov ax , 5
mov DS:[3] , ax
mov di, 0
mov cx, 0
mov ax, DS:[3]
cmp ax, 0
jge R40
mov bl, 2Dh
mov DS:[di], bl
add di , 1
neg ax
R40:
mov bx, 10
R41:
add cx, 1
mov dx, 0
idiv bx
push dx
cmp ax, 0
jne R41
R42:
pop dx
add dx, 30h
mov DS:[di] , dl
add di, 1
add cx, -1
cmp cx, 0
jne R42
mov dl, 024h
mov DS:[di], dl
mov dx, 0
mov ah, 09h
int 21h
mov ax , 1
mov DS:[3] , ax
mov di, 0
mov cx, 0
mov ax, DS:[3]
cmp ax, 0
jge R43
mov bl, 2Dh
mov DS:[di], bl
add di , 1
neg ax
R43:
mov bx, 10
R44:
add cx, 1
mov dx, 0
idiv bx
push dx
cmp ax, 0
jne R44
R45:
pop dx
add dx, 30h
mov DS:[di] , dl
add di, 1
add cx, -1
cmp cx, 0
jne R45
mov dl, 024h
mov DS:[di], dl
mov dx, 0
mov ah, 09h
int 21h
mov ah, 02h
mov dl, 0Dh
int 21h
mov DL, 0Ah
int 21h
mov ax , 1
mov DS:[3] , ax
mov di , DS:[3]
add di , di
add di , 16405
mov ax , DS:[di]
mov DS:[5] , ax
mov di, 0
mov cx, 0
mov ax, DS:[5]
cmp ax, 0
jge R46
mov bl, 2Dh
mov DS:[di], bl
add di , 1
neg ax
R46:
mov bx, 10
R47:
add cx, 1
mov dx, 0
idiv bx
push dx
cmp ax, 0
jne R47
R48:
pop dx
add dx, 30h
mov DS:[di] , dl
add di, 1
add cx, -1
cmp cx, 0
jne R48
mov dl, 024h
mov DS:[di], dl
mov dx, 0
mov ah, 09h
int 21h
mov ah, 02h
mov dl, 0Dh
int 21h
mov DL, 0Ah
int 21h
mov ax , 5
mov DS:[3] , ax
mov di, 0
mov cx, 0
mov ax, DS:[3]
cmp ax, 0
jge R49
mov bl, 2Dh
mov DS:[di], bl
add di , 1
neg ax
R49:
mov bx, 10
R50:
add cx, 1
mov dx, 0
idiv bx
push dx
cmp ax, 0
jne R50
R51:
pop dx
add dx, 30h
mov DS:[di] , dl
add di, 1
add cx, -1
cmp cx, 0
jne R51
mov dl, 024h
mov DS:[di], dl
mov dx, 0
mov ah, 09h
int 21h
mov ah, 02h
mov dl, 0Dh
int 21h
mov DL, 0Ah
int 21h
mov dx, 3
mov al, 0FFh
mov DS:[3], al
mov ah, 0Ah
int 21h
mov ah, 02h
mov dl, 0Dh
int 21h
mov DL, 0Ah
int 21h
mov di, 5
mov ax, 0
mov cx, 10
mov dx, 1
mov bh, 0
mov bl, DS:[di]
cmp bx, 2Dh
jne R52
mov dx, -1
add di, 1
mov bl, DS:[di]
R52:
push dx
mov dx, 0
R53:
cmp bx, 0Dh
je R54
imul cx
add bx, -48
add ax, bx
add di, 1
mov bh, 0
mov bl, DS:[di]
jmp R53
R54:
pop cx
imul cx
mov DS:[16446], ax
mov dx, 257
mov al, 2
mov DS:[257], al
mov ah, 0Ah
int 21h
mov ah, 02h
mov dl, 0Dh
int 21h
mov DL, 0Ah
int 21h
mov di, 259
mov si, 16445
R55:
mov al, DS:[di]
cmp al, 0Dh
je R56
mov DS:[si], al
add di, 1
add si, 1
jmp R55
R56:
mov al, 024h
mov DS:[si], al
R30:
R31:
mov ax , 1
mov DS:[261] , ax
;Inicio for
mov ax, DS:[261]
mov DS:[16446] , ax
;Acabou atribuicao
R57:
mov ax , 10
mov DS:[0] , ax
;Inicio comparacao
mov ax,DS:[16446]
mov bx,DS:[0]
cmp ax, bx
jg R58
mov cx, 1
add DS:[16446], cx
jmp R57
R58:
mov ah,4Ch
int 21h
cseg ENDS ;fim seg. código
END strt ;fim programa
