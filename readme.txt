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


Probleme die noch behoben werden müssen:
- die Anzeige der richtigen Binären Berechnung bei Integer zeigt keine 
	Binärzahlen im Zweikomplement an, sondern stellt bei negativen Zahlen ein
	Minuszeichen voran

- die Anzeige der richtigen Binären Berechnung bei Floating-Point hat
	ebenfalls nur 32-Bit zur Darstellung