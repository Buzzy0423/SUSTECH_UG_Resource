mp3_code = {
    'q': '01111',
    'w': '10000',
    'e': '10001',
    'r': '10010',
    't': '10011',
    'y': '10100',
    'u': '10101',

    'a': '01000',
    's': '01001',
    'd': '01010',
    'f': '01011',
    'g': '01100',
    'h': '01101',
    'j': '01110',

    'z': '00001',
    'x': '00010',
    'c': '00011',
    'v': '00100',
    'b': '00101',
    'n': '00110',
    'm': '00111',
    ' ': '00000',
}


with open('mp3.txt', 'w') as f:
    while True:
        a = input()
        for ch in a:
            f.write(mp3_code[ch])
        f.write('00000')
        print(len(a))