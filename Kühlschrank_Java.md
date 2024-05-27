# Rezepteverwaltung
---

## Aufgabe:
Entwickle eine Webapplikation (BackEnd: Java, Quarkus, FrontEnd: ReactJS) welche Rezepte, Zutaten und einen Vorrat verwalten kann!

## Randbedingungen:
V - Ein Rezept hat immer mehrere Zutaten in unterschiedlichen Mengen und ist für eine bestimmte Anzahl von Personen
V - Zutaten haben immer eine Einheit (z.B. Kartoffeln = kg, Milch = ml)
V - Rezepte haben keine Arbeitsanweisungen ( ;-) ) 
V - Rezepte können ein Foto haben 
V - Jeder Anwender hat seinen eigenen Vorrat (Benutzerkonten) 

## Funktionen:
V - Eigenen Vorrat eingeben und bearbeiten
V - Eigene Rezepte eingeben, bearbeiten, löschen
V - Zutaten anlegen, bearbeiten, löschen (Zutaten sind global und für alle Anwender identisch)

V - Rezeptsuche: Suche von Rezepten anhand von Namen und/oder Zutaten (z.B. Suche nach "Tomate" -> Tomatensuppe (Name und Zutat) und Bolognese (Zutat))
V - Kochplanung: Wähle Rezept aus, gebe an für wie viele Personen und die Anwendung sagt dir welche Zutaten du brauchst und welche Mengen
V - Einkaufsplanung: wie Kochplanung, aber die Anwendung berücksichtigt den angelegten Vorrat und gibt nur noch aus was fehlt und somit eingekauft werden muss
V - KeineAhnungModus: Die Anwendung schlägt aufgrund des Vorrats mögliche Rezepte vor (Sortierung: Rezepte mit dem meisten Vorrat stehen am Anfang der Liste, Rezepte mit nur einer Zutat aus dem Vorrat am Ende)
V - Zubereitung: Ein Rezept kann zubereitet werden, d.h. Auswahl des Rezeptes, Angabe der Personenanzahl --> Ausgabe des Rezeptes mit korrekten Mengen + Einkaufsliste und der Vorrat wird automatisch angepasst (nur das was weggenommen wird, neu eingekaufte Sachen müssen manuell im Vorrat hinzugefügt werden)

### Zusatz:
- Alle Listen sollen druckbar sein (Einkaufsliste, Kochliste, Vorschlagsliste beim KeineAhnungModus)
- Ein Rezept ist druckbar
- Vorratsübersicht ist druckbar
V - Rezepte können als "öffentlich" markiert werden, dann können andere Anwender dieses Rezept sehen, aber nicht bearbeiten oder löschen


## BEISPIEL:

Zutaten:
Zwiebeln (Stück)
Kartofflen (Kg)
Milch (ml)
Salz (Prise)
Nudeln (g)

Rezept:
Tomatensuppe
4 Personen
1 Tomaten (kg)
3 Salz (Prise)
500 Nudeln (g)
1 Zwiebeln (Stück)

Vorrat:
0,5 Tomaten (kg)
100 Salz (Prise)
250 Nudeln (g)