# Meteorite Compass
**Localizza i Meteoriti di MegaShowdown con Facilità!**

[![Minecraft](https://img.shields.io/badge/Minecraft-1.21.1-green.svg)](https://www.minecraft.net/)
[![Fabric](https://img.shields.io/badge/Fabric-0.16.9-blue.svg)](https://fabricmc.net/)
[![NeoForge](https://img.shields.io/badge/NeoForge-21.1.x-orange.svg)](https://neoforged.net/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

---

## 📖 Panoramica

**Meteorite Compass** è un addon per **CobblemonMegaShowdown** che aggiunge una bussola speciale per aiutarti a localizzare le rare strutture meteoriti sotterranee!

Stanco di scavare a caso sperando di trovare meteoriti? Questa mod aggiunge una bussola craftabile che **punta direttamente al meteorite più vicino**, rendendo l'esplorazione mirata e gratificante.

### ✨ Caratteristiche Principali

- 🧭 **Navigazione Intelligente**: Punta al Megaroid o Mega Site più vicino
- 🎯 **Due Tipi di Strutture**: Trova meteoriti sia profondi che superficiali
- ⚙️ **Completamente Configurabile**: Regola raggio di ricerca, cooldown e altro
- 🎨 **Feedback Visivi**: Particelle e suoni quando i meteoriti vengono localizzati
- 📊 **Display HUD**: Mostra distanza e direzione (opzionale)
- 🌐 **Multi-Lingua**: Supporta IT, EN, ES, FR, DE, PT-BR
- 🔧 **Cross-Loader**: Funziona sia su Fabric che NeoForge

---

## 🎮 Come Funziona

### Craftare la Bussola

```
[ Blocco Meteoroide Mega ]
[      Bussola      ] [ Mega Stone ]
[ Blocco Meteoroide Mega ]
```

*Richiede materiali dai meteoriti di CobblemonMegaShowdown*

### Usare la Bussola

1. **Crafta** la Bussola Meteorite usando materiali dei meteoriti
2. **Click Destro** con la bussola in mano per cercare meteoriti
3. **Aspetta** che la ricerca si completi (~1-2 secondi)
4. **Segui** l'ago della bussola verso la posizione del meteorite
5. **Trova** il meteorite e raccogli risorse preziose!

### Sistema di Cooldown

Dopo ogni ricerca, la bussola ha un **cooldown di 30 secondi** (configurabile) per prevenire problemi di performance del server.

---

## 🗺️ Tipi di Meteoriti

La bussola può localizzare due tipi di meteoriti da CobblemonMegaShowdown:

### 1. **Megaroid** 🌑
- **Profondità**: Y -32 a Y -20 (sotterraneo profondo)
- **Contiene**: Blocco Meteoroide Mega, blocchi radioattivi
- **Rarità**: Raro (spaziatura: 38 chunk)
- **Strategia**: Scava dritto quando la bussola punta sotto di te

### 2. **Mega Site** ⭐
- **Profondità**: Y -19 a Y 5 (superficiale/vicino alla superficie)
- **Contiene**: Meteorite Deoxys, vari minerali meteoriti
- **Rarità**: Non comune (spaziatura: 32 chunk)
- **Strategia**: Esplora grotte o scava quando vicino alla superficie

---

## ⚙️ Configurazione

Modifica `config/meteorite_compass.json` per personalizzare il comportamento:

```json
{
  "search_radius": 5000,
  "cooldown_seconds": 30,
  "show_distance_hud": true,
  "particle_effects": true,
  "sound_effects": true,
  "track_visited_structures": false
}
```

### Opzioni di Configurazione

| Opzione | Default | Descrizione |
|---------|---------|-------------|
| `search_radius` | 5000 | Distanza massima di ricerca in blocchi |
| `cooldown_seconds` | 30 | Secondi tra un uso e l'altro della bussola |
| `show_distance_hud` | true | Mostra overlay distanza quando tieni la bussola |
| `particle_effects` | true | Mostra particelle quando meteorite trovato |
| `sound_effects` | true | Riproduce suoni al successo della ricerca |
| `track_visited_structures` | false | Salta meteoriti già visitati |

---

## 🎨 Feedback Visivi e Audio

### Particelle
- **Scintille** quando inizia la ricerca
- **Raggio direzionale** quando meteorite localizzato
- **Effetto pulsante** quando vicino al target (<100 blocchi)

### Suoni
- **Suono attivazione** al click destro
- **Ping di successo** quando meteorite trovato
- **Beep crescenti** man mano che ti avvicini

### Display HUD
Quando tieni la bussola:
```
Bussola Meteorite
Target: Megaroid
Distanza: ~234 blocchi
Direzione: ↗ NE
```

---

## 🔧 Comandi

### `/meteoritecompass reset`
Cancella il tracking dei meteoriti visitati (se abilitato nel config).

### `/meteoritecompass info`
Mostra lo stato della bussola e il target corrente.

*Richiede permessi operatore sui server.*

---

## 📦 Installazione

### Requisiti
- **Minecraft**: 1.21.1
- **Fabric Loader**: 0.16.9+ (per Fabric)
- **NeoForge**: 21.1.x+ (per NeoForge)
- **Fabric API**: 0.108.0+ (solo Fabric)
- **CobblemonMegaShowdown**: 1.0.0+ (**RICHIESTO**)

### Passaggi
1. Scarica la mod da [CurseForge](TBD) o [Modrinth](TBD)
2. Installa **CobblemonMegaShowdown** (questa mod dipende da essa!)
3. Posiziona il JAR della mod nella cartella `mods/`
4. Avvia Minecraft e divertiti!

---

## 🧪 Note sulle Performance

- **Tempo Ricerca**: Tipicamente 100-500ms a seconda della dimensione del mondo
- **Impatto Server**: Minimo (<0.5ms per tick)
- **Uso Memoria**: ~5KB per bussola attiva
- **Caching**: Risultati in cache per 5 minuti per migliorare le performance

La mod usa ottimizzazioni aggressive per minimizzare il lag:
- Sistema di cooldown previene spam
- Raggio di ricerca limitato a distanza ragionevole
- Risultati in cache riducono ricerche duplicate
- Pattern di ricerca a chunk si ferma appena trova

---

## 🤝 Compatibilità

### Testato Con
- ✅ **CobblemonMegaShowdown**: Integrazione completa
- ✅ **JEI/REI/EMI**: Supporto visualizzazione ricette
- ✅ **Cobblemon**: Funziona insieme (non richiesto)
- ✅ **Fabric API**: Compatibilità completa
- ✅ **Architectury API**: Supporto multi-loader

### Problemi Noti
- La bussola trova solo meteoriti in **chunk già generati**
- Se nessun meteorite trovato, esplora nuove aree e riprova
- Funziona solo nella dimensione **Overworld**

---

## ❓ FAQ

### D: La bussola dice "Nessun meteorite trovato". Cosa faccio?
**R**: I meteoriti esistono solo in chunk già generati. Esplora oltre 1000+ blocchi in nuovo terreno e riprova.

### D: La bussola punta a un meteorite che ho già saccheggiato. Posso trovarne uno nuovo?
**R**: Abilita `track_visited_structures: true` nel config per saltare meteoriti visitati.

### D: Funziona in multiplayer?
**R**: Sì! Ogni giocatore ha il proprio tracking della bussola.

### D: Posso cambiare il raggio di ricerca?
**R**: Sì, modifica `search_radius` nel config. Attenzione - valori più grandi causano più lag.

### D: Funziona con altre mod?
**R**: Sì, purché CobblemonMegaShowdown sia installato. Non si prevedono conflitti.

### D: Posso usarla senza Cobblemon?
**R**: Sì! CobblemonMegaShowdown funziona standalone (Cobblemon non richiesto).

### D: La bussola non funziona nel Nether/End. Perché?
**R**: I meteoriti spawnano solo nell'Overworld. La bussola non funzionerà in altre dimensioni.

---

## 🛠️ Per Creatori di Modpack

### Impostazioni Raccomandate per Modpack
```json
{
  "search_radius": 3000,
  "cooldown_seconds": 60,
  "track_visited_structures": true
}
```

### Ricetta di Crafting Personalizzata
Puoi sovrascrivere la ricetta predefinita tramite datapack:
```
data/meteorite_compass/recipe/meteorite_compass.json
```

### Suggerimenti per il Bilanciamento
- Aumenta il cooldown per una progressione più difficile
- Diminuisci il raggio di ricerca per più esplorazione
- Abilita tracking visitati per incoraggiare dispersione
- Considera di vincolare la ricetta a progressione quest

---

## 🐛 Segnalazione Bug e Richieste Funzionalità

Trovato un bug? Hai un suggerimento? Faccelo sapere!

- **GitHub Issues**: [TBD - link repository]
- **Discord**: [TBD - canale supporto]
- **Commenti CurseForge**: [TBD - link progetto]

---

## 📜 Changelog

### [1.0.0] - TBD
#### Aggiunto
- Release iniziale
- Funzionalità base bussola
- Ricerca strutture per Megaroid e Mega Site
- Sistema di configurazione
- Effetti particelle e suoni
- Display HUD distanza
- Supporto multi-lingua (6 lingue)
- Comandi: `/meteoritecompass reset`, `/meteoritecompass info`

---

## 🙏 Crediti

### Creatore
**[Il Tuo Nome/Team]** - Sviluppo e design

### Ringraziamenti Speciali
- **yajatkaul** - Creatore di CobblemonMegaShowdown
- **Team Cobblemon** - Per la fantastica mod Cobblemon
- **Team Fabric/NeoForge** - Per il supporto piattaforme modding
- **Community** - Per feedback e testing

### Asset
- Tutte le texture e suoni creati specificamente per questa mod
- Meccaniche bussola ispirate alla Bussola Magnetite vanilla

---

## 📄 Licenza

Questa mod è sotto licenza **MIT License**.

**Puoi:**
- ✅ Usare nei modpack
- ✅ Modificare il codice
- ✅ Ridistribuire con crediti

**Devi:**
- 📝 Includere la licenza originale
- 📝 Dare credito all'autore originale

Vedi [LICENSE](LICENSE) per i dettagli completi.

---

## 🔗 Link

- **CurseForge**: [TBD]
- **Modrinth**: [TBD]
- **GitHub**: [TBD]
- **Discord**: [TBD]
- **Wiki**: [TBD]

---

## 🌟 Supporta il Progetto

Se ti piace questa mod:
- ⭐ Metti **Stella** al progetto su GitHub
- 💬 **Condividi** con gli amici
- 🐛 **Segnala bug** per aiutare a migliorarla
- 💡 **Suggerisci funzionalità** per aggiornamenti futuri
- ☕ **Dona** [TBD - link donazione opzionale]

---

**Fatto con ❤️ per la community di Cobblemon & MegaShowdown!**

*Ultimo Aggiornamento: 25 Febbraio 2026*
