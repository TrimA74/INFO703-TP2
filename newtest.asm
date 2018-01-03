DATA SEGMENT
	 a DD
	 b DD
	 aux DD
	 a DD
	 b DD
DATA ENDS
CODE SEGMENT
mov eax,5
push eax
pop eax
mov a, eax
mov eax,5
push eax
pop eax
mov b, eax
debut_while_0:
mov eax,0
push eax
mov eax,b
push eax
pop eax
pop ebx
sub ebx, eax
jl vrai_1
push 0
jmp fin_1 
vrai_1:
push 1
fin_1:
pop eax
jz fin_0
mov eax,a
push eax
mov eax,b
push eax
pop eax
pop ebx
mov ecx, ebx
div ecx, eax
mul ecx, eax
sub ebx, ecx
push ebx
pop eax
mov aux, eax
mov eax,b
push eax
pop eax
mov a, eax
mov eax,aux
push eax
pop eax
mov b, eax
jmp debut_while_0
fin_0:
mov eax,a
out eax
CODE ENDS
