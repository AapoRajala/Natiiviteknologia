
### Viikko 4
Navigointi on tapa siirtyä ruudusta toiseen ohjelmassa.
NavController on "ohjain" joka ohjaa siirtymiset ruutujen välillä.
NavHost taas sisältää kaikki Screenit / ruudut.

Kaksisuuntainen navigointi

Miten MVVM ja navigointi yhdistyvät (yksi ViewModel kahdelle screenille).

-Viewmodel on NavHostin tasolla ja molemmat ruudut käyttävät samaa dataa.

Miten CalendarScreen on toteutettu?

Tehtävät ryhmitellään päivämäärien mukaan, päivän alle listataan tehtävät.


### Viikko 3
Model sisältää lähtökohtaisesti sovelluksen datan, View kansio sisältää lähtökohtaisesti UI toteutusta.

MVVM on selkeämpi, reaktiivinen UI ja testattavuus on helpompi.

### StateFlow
Sisältää nykyiset arvot ja on käynnissä koko ajan.
Stateflow takaa turvallisen käytön kun muisti vuodoille ei ole mahdollisuutta.



### Viikko 2

Viewmodel toimii vaikka näyttö kääntyisi, tämä myös osaa erottaa UI ja logiikan keskenään.
Remember toimii vain jos  Composable on muistissa.

