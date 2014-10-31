Dieses Java Programm ist ein Simulator zur Darstellung der internen
Zahlendarstellung und Berechnungen im Computer.

Die Eingabe eines Thermes erfolgt als Post-Fix Notation (umgekehrte polnische
Notation).

Beispiele:
3 3 + 				<->		3 + 3
23 3 4 * 23 - + 	<->		23 + 3 * 4 - 23
0.3 0.1 - 			<-> 	0.3 - 0.1
2 31 ^ 1 - 			<->		2^31 - 1

Anmerkungen zur Eingabe:
Fließkommazhalen können mit einem Punkt[.] beginnen.
Jede Zahl und jedes Zeichen werden mit einem Leerzeichen getrennt.
Die Potenzrechnung funktioniert nur mit positiven Ganzen Zahlen.
Die Schleifen-Funktionen funktiert nur mit Zwei Zahlen
	Beispiel: 1 0.2 + mit einer Wiederholungsanzahl von 3 ergibt
			1 0.2 + 0.2 + 0.2 + 0.2 +



Fehler und TO-DO:

* BigDecimal binaerausgabe falsch, immer nur noch 32 bit, statt mehr
* BigDecimal Exponential falsch/ungenau, immer nur 1-2 nachkommastellen oder falsches runden
	durch Float64 von jscience Lösung möglich, aber Float 64 wäre das selbe wie double(?)
* Schleife mit 1 100 i
* Kontrollbefehle
* GUI verbessern