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

- provare unica funzione ricorsiva con tanti if (getType) sullo stesso livello. [IMPORTANTE]
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
