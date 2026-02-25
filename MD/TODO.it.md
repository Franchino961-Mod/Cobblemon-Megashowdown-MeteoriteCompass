# TODO List - Meteorite Compass
**Versione**: 1.0.0-dev  
**Ultimo Aggiornamento**: 25 Febbraio 2026  
**Release Target**: TBD

---

## 🎯 Stato Rapido

**Fase Corrente**: Pianificazione e Documentazione  
**Progresso**: 0% (0/45 task completati)  
**Blocchi**: Nessuno  
**Prossimo Milestone**: v1.0.0 Release Iniziale

---

## 📊 Fasi di Sviluppo

### **Fase 1: Funzionalità Core (v1.0.0)** - Priorità: 🔴 CRITICA

#### Setup Progetto
- [ ] Creare struttura progetto multi-loader (Architectury)
- [ ] Configurare file build Gradle (common, fabric, neoforge)
- [ ] Configurare dipendenze (Fabric API, NeoForge, Architectury)
- [ ] Aggiungere CobblemonMegaShowdown come dipendenza
- [ ] Configurare metadata mod (fabric.mod.json, mods.toml)
- [ ] Configurare version control (repository Git)
- [ ] Creare workspace di sviluppo

#### Implementazione Item Bussola
- [ ] Creare classe item `MeteoriteCompass.java`
- [ ] Implementare handler interazione click destro
- [ ] Aggiungere componente dati NBT per storage target
- [ ] Creare sistema cooldown usando player cooldowns
- [ ] Aggiungere messaggi chat per feedback utente
- [ ] Implementare gestione errori (dimensione sbagliata, nessuna struttura trovata)
- [ ] Creare texture item (meteorite_compass.png)
- [ ] Creare JSON modello item

#### Logica Ricerca Strutture
- [ ] Creare classe utility `MeteoriteStructureFinder.java`
- [ ] Implementare integrazione API StructureManager
- [ ] Aggiungere ricerca struttura "mega_showdown:megaroid"
- [ ] Aggiungere ricerca struttura "mega_showdown:mega_site"
- [ ] Implementare raggio di ricerca configurabile
- [ ] Aggiungere pattern ricerca a chunk (ottimizzazione performance)
- [ ] Creare sistema caching risultati struttura
- [ ] Aggiungere validazione dimensione (solo Overworld)

#### Animazione Ago Bussola
- [ ] Creare `CompassAngleProperty.java` ItemPropertyFunction
- [ ] Calcolare angolo tra giocatore e target
- [ ] Gestire casi limite (nessun target, dimensione diversa)
- [ ] Registrare property all'item bussola
- [ ] Testare rotazione ago in-game
- [ ] Ottimizzare performance lato client

#### Ricetta di Crafting
- [ ] Progettare ricetta crafting bilanciata
- [ ] Creare file JSON ricetta
- [ ] Aggiungere sblocco ricetta via advancement (opzionale)
- [ ] Testare ricetta in-game
- [ ] Aggiungere hint compatibilità JEI/REI/EMI

#### Sistema di Configurazione
- [ ] Creare classe configurazione `CompassConfig.java`
- [ ] Implementare caricamento file config JSON
- [ ] Aggiungere opzione `search_radius` (default: 5000)
- [ ] Aggiungere opzione `cooldown_seconds` (default: 30)
- [ ] Aggiungere toggle booleani (HUD, particelle, suoni)
- [ ] Aggiungere opzione `track_visited_structures`
- [ ] Testare hot-reload config
- [ ] Aggiungere validazione config e defaults

#### Testing e Bug Fix
- [ ] Testare bussola in modalità survival
- [ ] Testare con CobblemonMegaShowdown installato
- [ ] Testare in ambiente multiplayer
- [ ] Correggere bug critici
- [ ] Profiling performance
- [ ] Controlli memory leak

---

### **Fase 2: Polish e UX (v1.1.0)** - Priorità: 🟡 ALTA

#### Effetti Visivi
- [ ] Implementare effetti particelle all'inizio ricerca
- [ ] Aggiungere raggio particelle direzionale quando struttura trovata
- [ ] Creare particelle pulsanti vicino al target (<100 blocchi)
- [ ] Aggiungere burst particelle alla posizione target (<20 blocchi)
- [ ] Rendere particelle configurabili (opzione disable)
- [ ] Ottimizzare performance rendering particelle

#### Effetti Sonori
- [ ] Aggiungere suono attivazione (riusa vanilla lodestone_compass.lock)
- [ ] Creare effetto sonoro custom "meteorite_ping"
- [ ] Implementare beep basato su prossimità (più vicino = più veloce)
- [ ] Aggiungere suono sottile di fallimento quando nessuna struttura trovata
- [ ] Rendere suoni configurabili (opzione disable)
- [ ] Testare bilanciamento volume suoni

#### Display HUD
- [ ] Creare renderer overlay `CompassHUD.java`
- [ ] Mostrare tipo meteorite (Megaroid/Mega Site)
- [ ] Mostrare distanza stimata al target
- [ ] Aggiungere indicatore direzione (N, NE, E, ecc.)
- [ ] Mostrare timer cooldown
- [ ] Rendere HUD attivabile/disattivabile via config
- [ ] Testare HUD in diverse risoluzioni schermo

#### Localizzazione
- [ ] Creare chiavi traduzione nei file lang
- [ ] Tradurre in Italiano (it_it.json)
- [ ] Tradurre in Spagnolo (es_es.json)
- [ ] Tradurre in Francese (fr_fr.json)
- [ ] Tradurre in Tedesco (de_de.json)
- [ ] Tradurre in Portoghese Brasiliano (pt_br.json)
- [ ] Testare tutte le lingue in-game

#### Comandi
- [ ] Implementare comando `/meteoritecompass reset`
- [ ] Implementare comando `/meteoritecompass info`
- [ ] Aggiungere controlli permessi (livello operatore)
- [ ] Creare messaggi uso comando
- [ ] Aggiungere tab-completion comandi
- [ ] Testare comandi in multiplayer

---

### **Fase 3: Funzionalità Avanzate (v1.2.0)** - Priorità: 🟢 MEDIA

#### Tracking Strutture Visitate
- [ ] Creare componente dati persistenti giocatore
- [ ] Salvare coordinate meteoriti visitati
- [ ] Saltare strutture già visitate nella ricerca
- [ ] Aggiungere `/meteoritecompass reset` per cancellare cronologia
- [ ] Rendere tracking opzionale via config
- [ ] Testare tracking attraverso reload mondo

#### Codifica Colore Basata su Distanza
- [ ] Implementare colore bagliore ago per distanza
- [ ] Rosso: >2000 blocchi (molto lontano)
- [ ] Arancione: 1000-2000 blocchi (lontano)
- [ ] Giallo: 500-1000 blocchi (medio)
- [ ] Verde: 100-500 blocchi (vicino)
- [ ] Ciano: <100 blocchi (molto vicino)
- [ ] Creare varianti texture o shader

#### Indicatore Tipo Struttura
- [ ] Aggiungere bagliore viola per tracking Megaroid
- [ ] Aggiungere bagliore blu per tracking Mega Site
- [ ] Implementare effetto incantesimo luminoso
- [ ] Rendere bagliore sottile e non distraente
- [ ] Testare visibilità bagliore in diverse illuminazioni

#### Integrazione Ricette
- [ ] Aggiungere categoria ricetta JEI
- [ ] Aggiungere categoria ricetta REI
- [ ] Aggiungere categoria ricetta EMI
- [ ] Mostrare funzionalità bussola nel tooltip ricetta
- [ ] Testare integrazione con tutte e tre le mod

---

### **Fase 4: Ottimizzazione e Release (v1.3.0)** - Priorità: 🔵 BASSA

#### Ottimizzazione Performance
- [ ] Profilare performance ricerca strutture
- [ ] Ottimizzare sistema caching
- [ ] Ridurre footprint memoria
- [ ] Minimizzare pacchetti client-server
- [ ] Testare con 100+ giocatori online
- [ ] Ottimizzare rendering particelle

#### Documentazione
- [ ] Completare README.md con screenshot
- [ ] Scrivere pagine wiki dettagliate
- [ ] Creare video tutorial
- [ ] Documentare tutte le opzioni config
- [ ] Scrivere guida integrazione modpack
- [ ] Creare sezione FAQ

#### Testing Community
- [ ] Release alpha a 10+ tester
- [ ] Raccogliere report bug
- [ ] Correggere problemi segnalati
- [ ] Release beta a pubblico più ampio
- [ ] Passaggio finale correzione bug

#### Preparazione Release
- [ ] Creare pagina progetto CurseForge
- [ ] Creare pagina progetto Modrinth
- [ ] Caricare screenshot mod
- [ ] Scrivere descrizione mod accattivante
- [ ] Configurare sistema changelog
- [ ] Preparare post annuncio
- [ ] Caricare release v1.0.0

---

## 🚧 Problemi Noti

| Problema | Priorità | Stato | Note |
|----------|----------|-------|------|
| Nessuno ancora | - | - | Sviluppo non iniziato |

---

## 💡 Idee Future (Backlog)

### Suggerimenti Community
- Tier upgrade bussola (bronzo → ferro → diamante)
- Bussola ricaricabile (consumare blocchi meteorite)
- Target bussola condivisi team (multiplayer)
- Anteprima struttura nell'HUD (minimappa 3D)
- Integrazione con mod mappe (JourneyMap, Xaero's)
- Sistema achievement per scoperte meteoriti
- Tracking statistiche (meteoriti trovati per giocatore)

### Possibilità di Integrazione
- **Cobblemon**: Usare Abra/Alakazam per teleport a meteorite
- **Create**: Decorazione puntatore bussola meccanico
- **Applied Energistics**: Spatial IO per chunk meteoriti
- **Botania**: Bussola alimentata a mana con raggio illimitato

### Funzionalità Avanzate
- Sistema bussola duale (Bussola Megaroid + Bussola Mega Site)
- Bussola può rilevare tipi specifici minerale meteorite
- Sistema waypoint (marcare multipli meteoriti)
- Rete bussole (condividere scoperte con amici)
- Eventi meteoriti basati su tempo (piogge meteore spawnano strutture temporanee)

---

## 📝 Note di Sviluppo

### Log Decisioni Design
- **2026-02-25**: Deciso bussola singola (non duale) per semplicità v1.0.0
- **2026-02-25**: Raggio ricerca default 5000 blocchi (bilanciamento trovabilità vs performance)
- **2026-02-25**: Sistema cooldown scelto invece durabilità (meno punitivo)
- **2026-02-25**: Tracking visitati disabilitato default (scelta giocatore)

### Sfide Tecniche
- **Performance Ricerca Strutture**: Mitigato con caching e cooldown
- **Sync Multiplayer**: Risolto con pacchetti network custom
- **Dipendenza Cross-Mod**: Dipendenza hard su CobblemonMegaShowdown
- **Generazione Chunk**: Può trovare solo strutture in chunk caricati (limitazione documentata)

### Standard Qualità Codice
- Tutti i metodi pubblici devono avere Javadoc
- Unit test per logica critica (structure finder, calcolatore angolo)
- Benchmark performance per operazioni ricerca
- Null-safety con `@Nullable` e `@NotNull`
- Code review richiesto per funzionalità maggiori

---

## 🏁 Checklist Release (v1.0.0)

### Pre-Release
- [ ] Tutti i task Fase 1 completati
- [ ] Nessun bug critico
- [ ] Testato su Fabric 1.21.1
- [ ] Testato su NeoForge 1.21.1
- [ ] File config funziona correttamente
- [ ] Localizzazione completa (6 lingue)
- [ ] Target performance raggiunti (<0.5ms impatto server)

### Documentazione
- [ ] README.md completo
- [ ] CHANGELOG.md aggiornato
- [ ] Tooltip in-game corretti
- [ ] Comandi documentati
- [ ] Opzioni config spiegate

### Testing Community
- [ ] Test alpha con 10+ utenti
- [ ] Tutti i bug segnalati corretti
- [ ] Test beta con 50+ utenti
- [ ] Feedback positivo raccolto

### Release
- [ ] Versione bump a 1.0.0
- [ ] Artifact build generati (Fabric + NeoForge)
- [ ] Pagina CurseForge creata
- [ ] Pagina Modrinth creata
- [ ] Release GitHub creata
- [ ] Annuncio postato (Discord, Reddit, Forum)

---

*Documento mantenuto da: [Il Tuo Nome]*  
*Prossima Revisione: 1 Marzo 2026*
