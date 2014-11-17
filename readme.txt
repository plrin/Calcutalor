Dieses Java Programm ist ein Simulator zur Darstellung der internen
Zahlendarstellung und Berechnungen im Computer.

Die Eingabe eines Thermes erfolgt als Post-Fix Notation (umgekehrte polnische
Notation).

Beispiele:
3 3 + 			<->	3 + 3
23 3 4 * 23 - +		<->		23 + 3 * 4 - 23
0.3 0.1 - 		<-> 	0.3 - 0.1
2 31 ^ 1 - 		<->	2^31 - 1

Anmerkungen zur Eingabe:
Fließkommazhalen können mit einem Punkt[.] beginnen.
Jede Zahl und jedes Zeichen werden mit einem Leerzeichen getrennt.
Die Potenzrechnung funktioniert nur mit positiven Ganzen Zahlen.
Die Schleifen-Funktionen funktiert nur mit Zwei Zahlen
	Beispiel: 1 0.2 + mit einer Wiederholungsanzahl von 3 ergibt
			1 0.2 + 0.2 + 0.2 + 0.2 +




* GUI verbessern

/* Stack Instructions */

// DUPLICATE (DUP) - duplicates top stack entry

// DROP (DROP) - drop top stack entry

// STORE (STO) - store previous number in array (storage) in the x postion
	0 1 STO => store number 0 in index 1

// RECALL (RCL) - recall number of the position from array (storage) and push into stack
	3 RCL => recall number from array index 3 and push into stack

// IF EQUAL (IFEQ) - check if the top two numbers are equal, if so, then jump x numbers back of the string array

// IF GREATER THAN (IFGR) - check if the top number is greater than the second from the stack, if so, then jump x numbers back of the string array