# Changelog - Meteorite Compass

Tutti i cambiamenti notevoli alla mod **Meteorite Compass** saranno documentati in questo file.

---

## [Non Rilasciato]

### Funzionalità Pianificate (v1.1.0+)
- Sistema di tracking strutture visitate
- Codifica colore ago basata su distanza
- Miglioramenti overlay HUD
- Supporto lingue aggiuntive
- Integrazione ricette JEI/REI
- Miglioramenti sistema comandi

---

## [1.0.0] - TBD (Release Iniziale)

### Aggiunto
- **Funzionalità Core**
  - Item Bussola Meteorite che localizza meteoriti da CobblemonMegaShowdown
  - Ricerca strutture per "Megaroid" (Y -32 a -20)
  - Ricerca strutture per "Mega Site" (Y -19 a 5)
  - Attivazione click destro per cercare meteorite più vicino
  - Ago bussola punta al meteorite localizzato

- **Esperienza Utente**
  - Sistema di cooldown (30 secondi default, configurabile)
  - Effetti particelle quando meteorite localizzato
  - Effetti sonori per feedback ricerca
  - Display HUD distanza (opzionale)
  - Messaggi chat per stato ricerca
  
- **Sistema di Configurazione**
  - File config `meteorite_compass.json`
  - Raggio di ricerca configurabile (default: 5000 blocchi)
  - Durata cooldown configurabile
  - Toggle per display HUD
  - Toggle per effetti particelle
  - Toggle per effetti sonori
  - Opzione per tracking strutture visitate

- **Ricetta di Crafting**
  - Bussola craftata con Blocchi Meteoroide Mega e Mega Stone
  - Usa materiali da CobblemonMegaShowdown

- **Localizzazione**
  - Inglese (en_us)
  - Italiano (it_it)
  - Spagnolo (es_es)
  - Francese (fr_fr)
  - Tedesco (de_de)
  - Portoghese Brasiliano (pt_br)

- **Funzionalità Tecniche**
  - Supporto multi-loader (Fabric + NeoForge)
  - Componente dati NBT per storage target
  - Caching ricerca strutture (scadenza 5 minuti)
  - Networking client-server per sync coordinate
  - Ottimizzazione performance (<0.5ms impatto server)

- **Comandi**
  - `/meteoritecompass reset` - Cancella tracking meteoriti visitati
  - `/meteoritecompass info` - Mostra stato bussola e target

### Note Tecniche
- Usa API StructureManager di Minecraft per localizzazione strutture
- Implementa ItemPropertyFunction personalizzata per animazione ago bussola
- Compatibile con Minecraft 1.21.1
- Richiede CobblemonMegaShowdown come dipendenza
- Costruito su Architectury API per architettura multi-loader

### Limitazioni Note
- Trova solo meteoriti in chunk già generati
- Funziona solo nella dimensione Overworld
- Performance ricerca dipende da dimensione mondo e densità strutture

---

## Roadmap Sviluppo

### Fase 1 - Core (v1.0.0) ✅
- [x] Funzionalità base bussola
- [x] Logica ricerca strutture
- [x] Sistema configurazione
- [x] Supporto multi-loader
- [x] Localizzazione (6 lingue)
- [x] Implementazione comandi

### Fase 2 - Polish (v1.1.0) 🔄
- [ ] Tracking strutture visitate
- [ ] Codifica colore basata su distanza
- [ ] HUD migliorato con frecce direzione
- [ ] Integrazione JEI/REI
- [ ] Effetti particelle aggiuntivi
- [ ] Più variazioni sonore

### Fase 3 - Avanzato (v1.2.0) 📋
- [ ] Sistema bussola duale (separata per ogni tipo meteorite)
- [ ] Upgrade bussola (raggio aumentato)
- [ ] Sistema bussola ricaricabile
- [ ] Anteprima struttura nell'HUD
- [ ] Sistema marcatori waypoint
- [ ] Target bussola condivisi team (multiplayer)

### Fase 4 - Integrazione (v1.3.0) 📋
- [ ] Integrazione profonda con eventi CobblemonMegaShowdown
- [ ] Effetti particelle personalizzati corrispondenti tipi meteorite
- [ ] Sistema achievement
- [ ] Tracking statistiche (meteoriti trovati)
- [ ] Supporto datapack per strutture personalizzate

---

*Formato: [Versione] - Data*  
*Categorie: Aggiunto, Modificato, Corretto, Rimosso, Tecnico*
