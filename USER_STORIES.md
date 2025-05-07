# User Stories – UnempX

Dieses Dokument enthält die **User Stories** für das UnempX-Projekt, einer Anwendung zur Analyse von **US-Arbeitslosenquoten** zwischen den Jahren **2015** und **2024**. Die zugrundeliegenden Daten stammen aus dem Bureau of Labor Statistics Public Data Query (PDQ) System (https://data.bls.gov/pdq/SurveyOutputServlet). Sie beschreiben die Anforderungen aus Sicht der Anwender und dienen als Grundlage für die Umsetzung.

---

### Story 1: CSV-Import
**Als** Anwender der Arbeitslosendaten
**möchte ich** historische Arbeitslosenquoten aus einer vorgegebenen CSV-Datei importieren können
**um** die Daten im Programm zu visualisieren und Trends der Arbeitslosigkeit darzustellen.

---

### Story 2: Übersicht der Datensätze
**Als** Anwender
**möchte ich** alle importierten Datensätze mit Jahres- und Monatsquoten in einer Tabelle sehen können
**um** schnell zu überprüfen, ob die Arbeitslosenquoten korrekt geladen wurden.

---

### Story 3: Neuer Datensatz hinzufügen
**Als** Anwender
**möchte ich** manuell einen neuen Jahresdatensatz mit zwölf Monatsquoten hinzufügen können
**um** Lücken in der Arbeitslosenstatistik direkt in der Anwendung zu schließen.

---

### Story 4: Datensatz bearbeiten
**Als** Anwender
**möchte ich** einen bestehenden Datensatz bearbeiten können
**um** Tippfehler oder fehlerhafte Monatswerte in den Arbeitslosenquoten zu korrigieren.

---

### Story 5: Datensatz löschen
**Als** Anwender
**möchte ich** einen gesamten Jahresdatensatz aus der Tabelle entfernen können
**um** veraltete oder unbrauchbare Arbeitslosenquoten zu löschen.

---

### Story 6: Statistische Auswertung anzeigen
**Als** Anwender
**möchte ich** Minimum, Maximum und Durchschnitt der Arbeitslosenquoten berechnet sehen
**um** zentrale Kennzahlen zur Entwicklung der Arbeitslosigkeit auf einen Blick zu erhalten.

---

### Story 7: Liniendiagramm der Quoten
**Als** Anwender
**möchte ich** Jahres- und Monatsquoten in einem interaktiven Liniendiagramm darstellen
**um** Ausreißer und saisonale Muster der Arbeitslosentwicklung visuell zu identifizieren.

---

### Story 8: Änderungen persistent speichern
**Als** Anwender
**möchte ich** alle vorgenommenen Änderungen (Import, Hinzufügen, Bearbeiten, Löschen) automatisch in einer externen CSV-Datei speichern
**um** nach einem Neustart die aktuellen Arbeitslosenquoten unverändert wiederverwenden zu können.

---
