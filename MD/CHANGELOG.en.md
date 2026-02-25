# Changelog - Meteorite Compass

All notable changes to the **Meteorite Compass** mod will be documented in this file.

---

## [Unreleased]

### Planned Features (v1.1.0+)
- Visited structure tracking system
- Distance-based needle color coding
- HUD overlay improvements
- Additional languages support
- JEI/REI recipe integration
- Command system enhancements

---

## [1.0.0] - TBD (Initial Release)

### Added
- **Core Functionality**
  - Meteorite Compass item that locates meteorites from CobblemonMegaShowdown
  - Structure finder for "Megaroid" (Y -32 to -20)
  - Structure finder for "Mega Site" (Y -19 to 5)
  - Right-click activation to search for nearest meteorite
  - Compass needle points to located meteorite

- **User Experience**
  - Cooldown system (30 seconds default, configurable)
  - Particle effects when meteorite located
  - Sound effects for search feedback
  - HUD distance display (optional)
  - Chat messages for search status
  
- **Configuration System**
  - `meteorite_compass.json` config file
  - Configurable search radius (default: 5000 blocks)
  - Configurable cooldown duration
  - Toggle for HUD display
  - Toggle for particle effects
  - Toggle for sound effects
  - Option to track visited structures

- **Crafting Recipe**
  - Compass crafted with Mega Meteoroid Blocks and Mega Stone
  - Uses materials from CobblemonMegaShowdown

- **Localization**
  - English (en_us)
  - Italian (it_it)
  - Spanish (es_es)
  - French (fr_fr)
  - German (de_de)
  - Portuguese Brazil (pt_br)

- **Technical Features**
  - Multi-loader support (Fabric + NeoForge)
  - NBT data component for target storage
  - Structure search caching (5-minute expiration)
  - Client-server networking for coordinate sync
  - Performance optimization (<0.5ms server impact)

- **Commands**
  - `/meteoritecompass reset` - Clear visited meteorite tracking
  - `/meteoritecompass info` - Display compass status and target

### Technical Notes
- Uses Minecraft's StructureManager API for structure location
- Implements custom ItemPropertyFunction for compass needle animation
- Compatible with Minecraft 1.21.1
- Requires CobblemonMegaShowdown as dependency
- Built on Architectury API for multi-loader architecture

### Known Limitations
- Only finds meteorites in already-generated chunks
- Works only in Overworld dimension
- Search performance depends on world size and structure density

---

## Development Roadmap

### Phase 1 - Core (v1.0.0) ✅
- [x] Basic compass functionality
- [x] Structure finding logic
- [x] Configuration system
- [x] Multi-loader support
- [x] Localization (6 languages)
- [x] Commands implementation

### Phase 2 - Polish (v1.1.0) 🔄
- [ ] Visited structure tracking
- [ ] Distance-based color coding
- [ ] Enhanced HUD with direction arrows
- [ ] JEI/REI integration
- [ ] Additional particle effects
- [ ] More sound variations

### Phase 3 - Advanced (v1.2.0) 📋
- [ ] Dual compass system (separate for each meteorite type)
- [ ] Compass upgrades (increased range)
- [ ] Rechargeable compass system
- [ ] Structure preview in HUD
- [ ] Waypoint marking system
- [ ] Team-shared compass targets (multiplayer)

### Phase 4 - Integration (v1.3.0) 📋
- [ ] Deep integration with CobblemonMegaShowdown events
- [ ] Custom particle effects matching meteorite types
- [ ] Achievement system
- [ ] Statistics tracking (meteorites found)
- [ ] Datapack support for custom structures

---

*Format: [Version] - Date*  
*Categories: Added, Changed, Fixed, Removed, Technical*
