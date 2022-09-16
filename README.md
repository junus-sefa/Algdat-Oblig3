# Obligatorisk oppgave 3 i Algoritmer og Datastrukturer

Denne oppgaven er en innlevering i Algoritmer og Datastrukturer. 
Oppgaven er levert av følgende student:
* Junus Sefa, S344055, s344055@oslomet.no


# Løsningsbeskrivelse

<h3>Oppgave 1: </h3>
I oppgave 1 kopierte jeg koden rett fra kompendiet (Programkode 5.2 3 a). 
Det jeg endret på av koden var å gi "foreldre" riktig referanse.
Jeg bruker en while-løkke, to pekere -> p og q, der q er forelder til peker p. I tillegg
bruker jeg "comparator", som sammenligner verdiene. 
Vi ønsker at p blir lik "null", og derfor hvis "verdi" er større enn p.verdi går vi til høyre,
og hvis "verdi" er mindre enn p.verdi går den til venstre. Dette skjer helt til p blir "null".
Når p blir null, er q siste node som ble passert. Deretter opprettes en node med q som forelder. 
Og hvis q = null, blir p den nye roten.

<h3>Oppgave 2: </h3>
I oppgave 2 starter jeg med å se om treet er tomt. Om det er tomt returneres 0.
Deretter opprettes en variabel "teller" som blir satt til = null. Så blir det sjekket
om verdi er lik null, isåfall returneres det 0. Derreter lagde jeg en while-løkke, 
som går gjennom treet, hvor teller plusses med 1, for hver gang den kommer over en verdi.
Det som returneres i denne oppgaven er: teller.

<h3>Oppgave 3: </h3>
Jeg hentet inspirasjon fra kompendiet "seksjon “5.1.7 Preorden, inorden og postorden”"
hvor det var snakk om hvilke kriterier som gjelder ved PostOrden.

I "FørstepostOrden" bruker jeg en while løkke som kjører til p ikke lenger er lik null.
I while løkken, sjekkes det om p.venstre ikke er lik, så oppdateres p til p.venstre.
Når det ikke er mulig å gå mer til venstre, sjekker jeg om det er går ann å gå til høyre.
Når det ikke lenger er mulig å gå til høyre eller venstre, returneres p. Og dermed får jeg
første node post orden, med p som rot.

I "NestePostOrden" starter jeg med å se om p.forelder er lik null. Derfom positiv så retunerer jeg null,
ettersom rot er den siste i postorden. Deretter sjekkes det om p er barn venstre eller høyre.
Hvis p er et venstre barn, returnerer jeg p.forelder ettersom den er neste i postorden.
Hvis p er et høyre barn, blir p.forelder neste verdi i postorden. Deretter sjekker jeg om p har,
høyre søsken, og dersom positiv så kjøres førstePostOrden(p.forelder.høyre) for å finne første node.

<h3>Oppgave 4: </h3>
- Finne første verdi i postorden
- Lagre verdien i p
- While-løkke til p != null
- Utførte oppgave.utførtOppgave(p.verdi)
- nestePostorden(p) -> finner neste
- Metoden postordenRecursive lik postOrden, det som var annerledes var at den skulle være recursive
- Beveger seg til høyre om mulig, så til venstre om mulig, hvis ikke så blir oppgaven utført


<h3>Oppgave 5: </h3>
Serialize
- Tar i bruk Deque = hjelpetabell
- Ser om rot != null, hvis den er så legges den i kø
- While-løkke som går til kø er tom
- Oppretter variabel som inneholder verdien forrest i kø (1. rot, 2. rot.venstre-barn 3. rot.høyre-barn)
- Lager en arraylist
- Ser om mellom.venstre != null (om ja, legges verdi i kø)
- Ser om høyre != null (om ja, legges verdi i kø)
- Returner arraylisten

Deserialize
- Lager et tre
- Foreach og leggInn tas i bruk for å legge inn verdiene fra serialize


<h3>Oppgave 6: </h3>
Fjern
I oppgave 6 kopierte jeg fjern-metoden koden rett fra kompendiet.
Det jeg endret på av koden var å gi "foreldre" riktig referanse, og sjekket om
kode ga nullpoint. 
- Sjekker om verdi == null (om ja, returner false, ettsom tre ikke har null-verdier)
- Går gjennom nodene i treet, deretter sammenligner verdiene, og hva som skal bort
- Sjekker om 1)to barn 2)ingen barn 3)kun ett barn, i disse tilfellene fjernes noden

fjernAlle
- Bruker antall(T verdi) og fjern-metoden
- For-løkke som kjører så mange ganger som den er i tre
- Returnerer antall
- Trekker antall noder fjernet fra antall

Nulstille
- Bruker serialize og fjerAlle-metodene
- Foreach-løkke som går gjennom alle verdier i serialize
- fjerner de med fjerAlle-metoden
- Setter Antall == 0
