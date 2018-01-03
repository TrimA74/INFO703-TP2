DATA SEGMENT
    prixHt DD
    prixTtc DD
DATA ENDS
CODE SEGMENT
    mov eax, 200
    mov prixHt, eax

    mov eax, prixHt
    push eax
    mov eax,5
    push eax
    pop eax
    pop ebx
    add ebx, eax
    out ebx
    push ebx
    mov eax,25
    push eax
    mov eax,32
    push eax
    pop eax
    pop ebx
    sub ebx, eax
    out ebx
    push ebx
    pop eax
    pop ebx
    mul ebx, eax
    out ebx
    push ebx
    mov eax, 100
    push eax
    pop eax
    pop ebx
    div ebx, eax
    push ebx
    pop eax
    mov prixTtc, eax

    ; debug
    out eax
CODE ENDS