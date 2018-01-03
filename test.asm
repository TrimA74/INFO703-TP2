DATA SEGMENT
    prixHt DD
    prixTtc DD
DATA ENDS
CODE SEGMENT
    mov eax, 200
    mov prixHt, eax
    mov eax, prixHt
    mov eax, 119
    mul eax, prixHt
    push eax
    mov eax, 100
    pop ebx
    div ebx, eax
    mov eax, ebx
    mov prixTtc, eax
    ; debug
    out eax
CODE ENDS