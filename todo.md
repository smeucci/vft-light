TODOS
=====

- cambiare myFourCCcode(box)
- cambiare da == a equals per le stringhe
- fare classe astratta per BoxParser
- fare classe astratta per stsdUnderBox e classe derivata stsdUnderBoxMP4
- fare package utils (countOccurrence, removeSquareBrackets)
- aggiunger error message nei catch
- contains in parser, inutile l'or poichè converte le stringhe in lowercase


Migliorie libreria VFT
======================

- fare una versione light della libreria che implementi: estrazione xml da video, costruzione albero, config file, draw, funzioni di visita.
- riscrivere tutto a partire dal livello più basso (parser->tree->color->draw), curando sovrattutto l'interfaccia più che il core.
- pulizia codice (es. parametri passati inutili, variabli che si possono rimuovere, guardie in più, ottimizzare righe di codice, uniformare codice).
- aggiungere all'eseguibile da terminale funzionalità della prima versione: parser di un video, dato un xml disegnarlo.
- commentare e documentare meglio il codice.
- strutturare meglio il codice per essere usato come una libreria con più facilità.

ISSUES
======

- RISOLTO - parserMOV, nel box mvhd i valori sono corretti ma i nomi degli attributi sono sbagliati (compatibleBrand_i del box ftyp al posto dei corretti)
- parserMOV, nel box minf->stbl->stsd->mp4a l'attributo boxes è pieno invece che vuoto; inoltre tale campo si ripete poco dopo, stavolta nella forma attesa. Fra le due ripetizioni ci sono alcuni boxes vuoti "chan" e "wave". Il primo box mp4a ha il tag di chiusura col nome, mentre quello atteso no.
In realtà era corretto questo funzionamento. Dipendeva dall'uso precedente di countOccurrences per cercare "{". La guarda era per n == 1, ma in questo caso n era uguale a due. Usando contains il problema è risolto perchè ritorna true se anche n fosse uguale a 2.
Rimangono comunque degli errori di parsing: solo una parte viene presa e le stringhe non stanno dentro boxes. Splittare prima ; e poi ,
Errore riga 80 in separateNameValue.

[A, u, d, i, o, S, a, m, p, l, e, E, n, t, r, y, {, b, y, t, e, s, P, e, r, S, a, m, p, l, e, =, 0, ,,  , b, y, t, e, s, P, e, r, F, r, a, m, e, =, 0, ,,  , b, y, t, e, s, P, e, r, P, a, c, k, e, t, =, 0, ,,  , s, a, m, p, l, e, s, P, e, r, P, a, c, k, e, t, =, 0, ,,  , p, a, c, k, e, t, S, i, z, e, =, 0, ,,  , c, o, m, p, r, e, s, s, i, o, n, I, d, =, 8, 7, 0, 4, ,,  , s, o, u, n, d, V, e, r, s, i, o, n, =, 2, 5, 9, 7, 1, ,,  , s, a, m, p, l, e, R, a, t, e, =, 1, 1, 5, 2, ,,  , s, a, m, p, l, e, S, i, z, e, =, 3, 2, 8, 9, 6, ,,  , c, h, a, n, n, e, l, C, o, u, n, t, =, 8, 9, 6, ,,  , b, o, x, e, s, =, [, ], }]


AudioSampleEntry{bytesPerSample=2, bytesPerFrame=2, bytesPerPacket=2, samplesPerPacket=1024, packetSize=0, compressionId=65534, soundVersion=1, sampleRate=44100, sampleSize=16, channelCount=1, boxes=[com.coremedia.iso.boxes.UnknownBox@47ef968d, AppleWaveBox[OriginalFormatBox[dataFormat=mp4a];AudioSampleEntry{bytesPerSample=0, bytesPerFrame=0, bytesPerPacket=0, samplesPerPacket=0, packetSize=0, compressionId=8704, soundVersion=25971, sampleRate=1152, sampleSize=32896, channelCount=896, boxes=[]};com.googlecode.mp4parser.boxes.mp4.ESDescriptorBox@1;com.coremedia.iso.boxes.UnknownBox@1877ab81]]}


