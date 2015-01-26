Dieses Java Programm ist ein Simulator zur Darstellung der internen
Zahlendarstellung und Berechnungen im Computer.

Die Eingabe eines Thermes erfolgt als Post-Fix Notation (umgekehrte polnische
Notation).

Beispiele:
3 3 + 			<->	3 + 3
23 3 4 * 23 - +		<->	23 + 3 * 4 - 23
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

// IF EQUAL (A B C IFEQ) - check if the top two numbers (A B) are equal, if so, then jump C numbers back of the string array

// IF GREATER THAN (IFGR) - check if the top number is greater than the second from the stack, if so, then jump x numbers back of the string array



================================================================================

Bugfixes:

1. Beim Einfügen ist mir ab und zu das User Interface
auseinandergefallen - siehe Anhang.

	\\2. Wie kann man eine Zahl wie "1.3E15" eingeben?  Ist mir nicht gelungen.

3. Wenn man das Fenster des Programms vergrößert, wäre es ganz gut,
wenn dann auch die inneren Fenster entsprechend größer würden.  Dann
würde nämlich alles ohne horizontale Scrollbalken auf den Screen
passen.

4. Ich hatte das folgende, nicht ganz korrekte, Programm eingegeben:
"0 2 sto 1 1 sto 1 rcl 1 + dup dup 1 sto 5 26 ifeq 2 rcl + 2 sto 0 0 3
ifeq 2 rcl".  Dadurch gerät das Programm in eine Endlosschleife von
Fehlermeldungen, die man nur dadurch beenden kann, dass man es über
das Betriebssystem killt.

	5. Dieses Programm soll eine arithmetische Summe bilden: "0 2 sto 0 1 sto 1 rcl 1 + dup dup 1 sto 5 26 ifeq 2 rcl + 2 sto 0 0 6 ifeq 2 rcl 0 +".  Das macht es zwar auch, aber...

	=> 0 2 sto 0 1 sto 1 rcl 1 + dup dup 1 sto 5 26 ifeq 2 rcl + 2 sto 0 0 6 ifeq 2 rcl +

	5.1. ...ich bekomme immer die Fehlermeldung "Rechenzeichen fehlt".

	5.2. ...auf dem Stack steht am Ende die Summe, die ich haben will, aber im Fenster "richtiges Ergebnis" steht etwas anderes.









