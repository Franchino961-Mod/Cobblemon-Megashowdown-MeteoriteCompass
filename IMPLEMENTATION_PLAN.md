# Implementation Plan: Meteorite Compass
## Cobblemon MegaShowdown Integration - Meteorite Locator

**Version**: 1.0.0  
**Target Minecraft**: 1.21.1  
**Mod Loaders**: Fabric + NeoForge (Multi-loader)  
**Dependency**: CobblemonMegaShowdown (required)  
**Date**: February 25, 2026

---

## 📋 Project Overview

### Vision
Create a custom compass item that helps players locate the two meteorite structures from CobblemonMegaShowdown:
- **Megaroid** (deep underground meteorite, Y -32 to -20)
- **Mega Site** (shallow meteorite, Y -19 to 5)

### Core Gameplay Loop
1. **Craft Meteorite Compass** using meteorite materials
2. **Activate** by right-clicking (searches for nearest meteorite)
3. **Follow the needle** to the meteorite location
4. **Find structure** and collect valuable meteorite blocks/ores
5. **Cooldown period** before next search (configurable)

---

## 🎯 Technical Architecture

### 1. Custom Compass Item

#### **MeteoriteCompass.java**
```java
public class MeteoriteCompass extends Item {
    - Extends net.minecraft.world.item.Item
    - Custom right-click behavior (InteractionResultHolder<ItemStack>)
    - NBT data storage for target coordinates
    - Cooldown system (player.getCooldowns())
    - Particle effects on successful structure location
}
```

**NBT Structure**:
```json
{
  "meteorite_compass": {
    "target_pos": [x, y, z],
    "structure_type": "megaroid" | "mega_site",
    "search_timestamp": long,
    "found": boolean
  }
}
```

#### **CompassAngleProperty.java**
```java
public class CompassAngleProperty implements ItemPropertyFunction {
    - Calculates angle between player and target
    - Returns float 0.0-1.0 for compass needle rotation
    - Handles dimension checks
    - Falls back to spawn point if no target
}
```

### 2. Structure Location Logic

#### **MeteoriteStructureFinder.java**
```java
public class MeteoriteStructureFinder {
    - Uses ServerLevel.structureManager()
    - Searches for "mega_showdown:megaroid"
    - Searches for "mega_showdown:mega_site"
    - Configurable search radius (default: 5000 blocks)
    - Returns nearest structure BlockPos
    - Caching system to reduce performance impact
}
```

**Search Algorithm**:
1. Get player position
2. Query StructureManager for registered structures
3. Search in expanding square pattern (chunked)
4. Cache results per-player with expiration
5. Return nearest unvisited meteorite (optional tracking)

### 3. Configuration System

#### **meteorite_compass.json**
```json
{
  "search_radius": 5000,
  "cooldown_seconds": 30,
  "show_distance_hud": true,
  "particle_effects": true,
  "sound_effects": true,
  "structure_priority": "nearest",
  "track_visited_structures": true,
  "require_overworld": true
}
```

### 4. Crafting Recipe

#### **Option A: Single Compass (Recommended)**
```
[ Mega Meteoroid Block ]
[Compass] [Mega Stone]
[ Mega Meteoroid Block ]
```
Finds nearest meteorite of any type.

#### **Option B: Dual Compass System**
**Megaroid Compass**:
```
[Mega Meteoroid Radiated Block]
[Compass] [Keystone]
[Mega Meteoroid Radiated Block]
```

**Mega Site Compass**:
```
[Deoxys Meteorite]
[Compass] [Mega Stone]
[Deoxys Meteorite]
```

### 5. Visual & Audio Feedback

#### **Particle Effects**
- **Search Initiated**: Sparkle particles around player
- **Structure Found**: Directional particle beam
- **Near Target (<100 blocks)**: Pulsing particles from compass
- **At Target (<20 blocks)**: Intense particle burst

#### **Sound Effects**
- **Activation**: `item.lodestone_compass.lock` (existing vanilla)
- **Search Success**: Custom "meteorite_ping" sound
- **Near Target**: Increasing frequency beeps
- **Failed Search**: Subtle failure sound

#### **HUD Display** (Optional)
When holding compass, show overlay:
```
Meteorite Compass
Target: Megaroid
Distance: ~234 blocks
Direction: ↗ NE
Cooldown: Ready
```

### 6. Advanced Features (Phase 2)

#### **Visited Structure Tracking**
- Store found meteorite positions in player persistent data
- Compass skips already-visited structures
- `/meteoritecompass reset` command to clear history

#### **Distance-Based Needle Color**
- **Red**: >2000 blocks (very far)
- **Orange**: 1000-2000 blocks (far)
- **Yellow**: 500-1000 blocks (medium)
- **Green**: 100-500 blocks (close)
- **Cyan**: <100 blocks (very close)

#### **Structure Type Indicator**
- Glowing enchantment effect:
  - **Purple glow**: Tracking Megaroid
  - **Blue glow**: Tracking Mega Site

#### **Crafting Variations**
- **Damaged Compass** (10% drop from mobs near meteorites)
- **Rechargeable** with Mega Meteoroid Blocks
- **Upgradeable** to increase search radius

---

## 📦 File Structure

```
common/
├── src/main/java/
│   └── com/meteorite/compass/
│       ├── MeteoriteCompassMod.java (main entry)
│       ├── item/
│       │   ├── MeteoriteCompass.java
│       │   └── ModItems.java (registry)
│       ├── structure/
│       │   ├── MeteoriteStructureFinder.java
│       │   └── StructureCache.java
│       ├── client/
│       │   ├── CompassAngleProperty.java
│       │   ├── CompassHUD.java (optional)
│       │   └── ParticleHandler.java
│       ├── config/
│       │   └── CompassConfig.java
│       ├── networking/
│       │   ├── CompassSyncPacket.java
│       │   └── StructureFoundPacket.java
│       └── util/
│           └── CompassUtil.java
│
└── src/main/resources/
    ├── assets/meteorite_compass/
    │   ├── models/item/
    │   │   └── meteorite_compass.json
    │   ├── textures/item/
    │   │   └── meteorite_compass.png
    │   ├── sounds/
    │   │   └── meteorite_ping.ogg
    │   └── lang/
    │       ├── en_us.json
    │       └── it_it.json
    │
    └── data/meteorite_compass/
        ├── recipe/
        │   └── meteorite_compass.json
        └── tags/
            └── items/
                └── meteorite_materials.json
```

---

## 🔧 Implementation Phases

### **Phase 1: Core Functionality** (v1.0.0) - 2-3 days
- [x] Project setup (multi-loader architecture)
- [ ] Basic compass item creation
- [ ] Structure finder logic
- [ ] NBT target storage
- [ ] Compass needle animation (ItemPropertyFunction)
- [ ] Crafting recipe
- [ ] Configuration file
- [ ] Basic testing

### **Phase 2: Polish & UX** (v1.1.0) - 1-2 days
- [ ] Particle effects
- [ ] Sound effects
- [ ] Cooldown system
- [ ] Distance-based feedback
- [ ] Localization (EN, IT, ES, FR, DE, PT-BR)
- [ ] Error handling (structure not found)

### **Phase 3: Advanced Features** (v1.2.0) - 2-3 days
- [ ] Visited structure tracking
- [ ] Command system (`/meteoritecompass`)
- [ ] HUD overlay
- [ ] Distance color coding
- [ ] Structure type indicator (glow effects)
- [ ] JEI/REI integration

### **Phase 4: Optimization** (v1.3.0) - 1 day
- [ ] Structure search caching
- [ ] Performance profiling
- [ ] Memory optimization
- [ ] Multiplayer stress testing
- [ ] Config validation

---

## 🧪 Testing Strategy

### Unit Tests
- ✅ Structure finder returns valid coordinates
- ✅ Compass needle calculates correct angle
- ✅ NBT data persists across world reloads
- ✅ Cooldown system enforces timing
- ✅ Configuration loads correctly

### Integration Tests
- ✅ Compass works with CobblemonMegaShowdown structures
- ✅ Crafting recipe produces correct item
- ✅ Networking syncs between client/server
- ✅ Multiple players use compass simultaneously
- ✅ Works across different biomes/dimensions

### Manual Tests
- [ ] Find Megaroid meteorite (Y -32 to -20)
- [ ] Find Mega Site meteorite (Y -19 to 5)
- [ ] Test in new vs pre-generated chunks
- [ ] Test with no meteorites in range
- [ ] Test cooldown behavior
- [ ] Test particle/sound effects
- [ ] Test HUD display
- [ ] Test in multiplayer environment

---

## ⚠️ Known Challenges & Solutions

### Challenge 1: Performance Impact
**Problem**: Structure searches are expensive operations  
**Solution**: 
- Implement aggressive caching (5-minute expiration)
- Limit search radius (configurable, default 5000 blocks)
- Use chunked search pattern (stop early if found)
- Cooldown prevents spam searches

### Challenge 2: Already Generated Chunks
**Problem**: Only finds meteorites in already-generated chunks  
**Solution**:
- Clear messaging: "No meteorite found within X blocks"
- Suggest exploring new areas
- Command to expand search radius temporarily

### Challenge 3: Visited Meteorites
**Problem**: Compass may point to depleted meteorites  
**Solution**:
- Optional visited structure tracking (per-player)
- Command to reset tracking: `/meteoritecompass reset`
- Config option to disable tracking

### Challenge 4: Multiplayer Sync
**Problem**: Target coordinates need client/server sync  
**Solution**:
- Custom network packet for structure found event
- NBT data component handles persistence
- Client-side prediction for smooth needle movement

### Challenge 5: Cross-Mod Compatibility
**Problem**: MegaShowdown must be installed  
**Solution**:
- Hard dependency in fabric.mod.json/mods.toml
- Runtime check for structure registration
- Graceful error if structures not found

---

## 📊 Performance Targets

| Metric | Target | Acceptable | Unacceptable |
|--------|--------|------------|--------------|
| Structure Search Time | <200ms | <500ms | >1000ms |
| Memory per Compass | <5KB | <20KB | >50KB |
| Cache Size (per player) | <10 entries | <50 entries | >100 entries |
| Client FPS Impact | <1% | <5% | >10% |
| Server TPS Impact | <0.1ms/tick | <0.5ms/tick | >1ms/tick |

---

## 🌐 Localization Keys

```json
{
  "item.meteorite_compass.meteorite_compass": "Meteorite Compass",
  "item.meteorite_compass.meteorite_compass.desc": "Locates meteorites from MegaShowdown",
  "meteorite_compass.search.started": "Searching for meteorite...",
  "meteorite_compass.search.found": "Meteorite located! Distance: %s blocks",
  "meteorite_compass.search.not_found": "No meteorite found within %s blocks",
  "meteorite_compass.search.cooldown": "Compass cooldown: %s seconds",
  "meteorite_compass.error.wrong_dimension": "Meteorites only exist in the Overworld",
  "meteorite_compass.error.no_megashowdown": "CobblemonMegaShowdown not installed!",
  "meteorite_compass.tracking.megaroid": "Tracking: Megaroid",
  "meteorite_compass.tracking.mega_site": "Tracking: Mega Site",
  "meteorite_compass.hud.distance": "Distance: ~%s blocks",
  "meteorite_compass.hud.direction": "Direction: %s",
  "meteorite_compass.command.reset": "Meteorite tracking reset!"
}
```

---

## 🔗 Dependencies

### Required
- **Minecraft**: 1.21.1
- **Fabric Loader**: 0.16.9+ (Fabric)
- **NeoForge**: 21.1.x+ (NeoForge)
- **Fabric API**: 0.108.0+ (Fabric only)
- **Architectury API**: 13.0.0+ (multi-loader support)
- **CobblemonMegaShowdown**: 1.0.0+

### Optional
- **JEI** (Just Enough Items): Recipe viewing
- **REI** (Roughly Enough Items): Recipe viewing
- **EMI** (Even More Items): Recipe viewing
- **ModMenu** (Fabric): Config screen access

---

## 📝 Development Notes

### Design Decisions

1. **Single Compass vs Dual Compass**
   - **Decision**: Start with single compass (finds nearest of any type)
   - **Rationale**: Simpler UX, one crafting recipe, less inventory clutter
   - **Future**: Consider dual compass in v2.0 if users request it

2. **Search Radius**
   - **Decision**: Default 5000 blocks, configurable
   - **Rationale**: Balance between findability and performance
   - **Note**: Meteorite spawn spacing is 32-38 chunks, so 5000 blocks covers ~156 chunks

3. **Cooldown System**
   - **Decision**: 30-second default cooldown, configurable
   - **Rationale**: Prevents server lag from repeated searches
   - **Alternative**: Item durability (rejected - too punishing)

4. **Visited Tracking**
   - **Decision**: Optional, disabled by default
   - **Rationale**: Some players want to revisit meteorites, others want fresh finds
   - **Implementation**: Stored in player persistent data component

5. **HUD Display**
   - **Decision**: Optional, enabled by default
   - **Rationale**: Helpful for new players, can be disabled by veterans
   - **Position**: Above hotbar, non-intrusive

### Code Style
- **Naming**: PascalCase for classes, camelCase for methods
- **Comments**: Javadoc for public methods, inline for complex logic
- **Formatting**: 4-space indentation, 120-character line limit
- **Null Safety**: Use `@Nullable` and `@NotNull` annotations

### Git Workflow
- **Branch Strategy**: `main` (stable), `develop` (active development)
- **Commit Convention**: `[Category] Brief description`
  - `[Feature]`, `[Fix]`, `[Refactor]`, `[Docs]`, `[Test]`
- **PR Requirements**: Build passes, no warnings, tested in-game

---

## 🚀 Release Checklist

### v1.0.0 Pre-Release
- [ ] All Phase 1 features implemented
- [ ] No critical bugs
- [ ] Tested with Fabric and NeoForge
- [ ] Tested with CobblemonMegaShowdown installed
- [ ] All localization keys defined
- [ ] README.md complete
- [ ] CHANGELOG.md created
- [ ] CurseForge/Modrinth page prepared

### v1.0.0 Release
- [ ] Community alpha testing (10+ users)
- [ ] All reported bugs addressed
- [ ] Performance targets met
- [ ] Documentation complete (wiki)
- [ ] Video demonstration recorded
- [ ] Upload to CurseForge
- [ ] Upload to Modrinth
- [ ] Announce on Discord/Reddit

---

## 📞 Support & Resources

- **GitHub**: [TBD - repository link]
- **CurseForge**: [TBD - project link]
- **Modrinth**: [TBD - project link]
- **Discord**: [TBD - support channel]
- **Wiki**: [TBD - documentation]

---

## 📄 License

**MIT License** - Free to use, modify, and distribute with attribution.

---

*Last Updated: February 25, 2026*  
*Document Version: 1.0*
